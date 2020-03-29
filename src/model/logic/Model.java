package model.logic;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.sun.org.apache.xerces.internal.impl.io.UTF8Reader;

import model.data_structures.AVLTreeST;
import model.data_structures.LinearProbingHashST;
import model.data_structures.MaxPQ;
import model.data_structures.Queue;
import model.data_structures.SeparateChainingHashST;
import model.data_structures.noExisteObjetoException;

/**
 * Definicion del modelo del mundo
 *
 */
public class Model
{
	//Constantes
	
	
	/**
	 * Atributos del modelo del mundo
	 */
	private Queue<Multa> datos; 
	private MaxPQ<Multa> heapMayorID;
	private SeparateChainingHashST<LlaveMesDIa, ValorMesDia> hash2A;
	private MaxPQ<Multa> heap1A;
	private AVLTreeST<Date, Multa> arbol3A; 
	private MaxPQ<Multa> heap1B;
	private LinearProbingHashST<Llave2B, Valor2B> hash2B;
	private AVLTreeST<Double, Multa> arbol3B;





	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Model()
	{
		datos = new Queue<Multa>();

		heapMayorID = new MaxPQ<Multa>(31);

		ComparadorGravedad c = new ComparadorGravedad();
		heap1A= new MaxPQ<Multa>(31, c);

		hash2A = new SeparateChainingHashST<LlaveMesDIa, ValorMesDia>(1);

		arbol3A = new AVLTreeST<Date, Multa>();

		ComparadorCercanoALaEstacion cc = new ComparadorCercanoALaEstacion();
		heap1B = new MaxPQ<Multa>(31, cc);

		hash2B = new LinearProbingHashST<Llave2B, Valor2B>(31);

		arbol3B = new AVLTreeST<Double, Multa>();
	}


	private void cargarDatos() throws noExisteObjetoException, FileNotFoundException 
	{
		String path = "./data/comparendos.geojson";
		JsonReader lector;
		InputStream inputstream = new FileInputStream(path);


		try {
			
//			lector = new JsonReader(new FileReader(path));
			lector = new JsonReader(new InputStreamReader(inputstream, "UTF-8"));
			JsonElement elem = JsonParser.parseReader(lector);
			JsonObject ja = elem.getAsJsonObject();

			JsonArray features = ja.getAsJsonArray("features");

			for(JsonElement e : features)
			{
				JsonObject propiedades = (JsonObject) e.getAsJsonObject().get("properties");

				long id = propiedades.get("OBJECTID").getAsLong();

				String cadenaFecha = propiedades.get("FECHA_HORA").getAsString();
				String anio = cadenaFecha.substring(0,4);
				String mes = cadenaFecha.substring(5,7);
				String dia = cadenaFecha.substring(8,10);
				String hora = cadenaFecha.substring(11,13);
				String min = cadenaFecha.substring(14,16);
				String seg = cadenaFecha.substring(17,19);
				String fechaConcatenadita = anio + "-" + mes +"-"+ dia + " " + hora + ":" + min + ":" + seg; 
				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				Date fecha = sdf.parse(fechaConcatenadita);


				String medioDete = propiedades.getAsJsonObject().get("MEDIO_DETECCION").getAsString();

				String claseVehiculo = propiedades.getAsJsonObject().get("CLASE_VEHICULO").getAsString();

				String tipoServicio = propiedades.getAsJsonObject().get("TIPO_SERVICIO").getAsString();

				String infraccion = propiedades.getAsJsonObject().get("INFRACCION").getAsString();

				String descripcion = propiedades.getAsJsonObject().get("DES_INFRACCION").getAsString();

				String localidad = propiedades.getAsJsonObject().get("LOCALIDAD").getAsString();

				String municipio = propiedades.getAsJsonObject().get("MUNICIPIO").getAsString();

				JsonObject geometry = (JsonObject) e.getAsJsonObject().get("geometry");

				String tipo = geometry.get("type").getAsString();


				double[] listaCoords = new double[3];
				JsonArray coordsJson = geometry.getAsJsonArray("coordinates");
				for(int i = 0; i < coordsJson.size(); i ++)
				{
					listaCoords[i] = coordsJson.get(i).getAsDouble();
				}

				Geo geometria = new Geo(tipo, listaCoords);

				Multa multa = new Multa(id, fecha, medioDete, claseVehiculo, tipoServicio, infraccion, descripcion, localidad, municipio, geometria);

				
				heapMayorID.insert(multa);
				datos.enqueue(multa);
				heap1A.insert(multa);
				heap1B.insert(multa);

				Calendar c = Calendar.getInstance();
				int anioC = Integer.parseInt(anio);
				int mesC = Integer.parseInt(mes);
				int diaC = Integer.parseInt(dia);
				Calendar calendario = Calendar.getInstance();
				calendario.set(Calendar.YEAR, anioC);
				calendario.set(Calendar.MONTH, mesC);
				calendario.set(Calendar.DATE, diaC);

				int diaSemana = calendario.get(Calendar.DAY_OF_WEEK);

				LlaveMesDIa llaveMesDia = new LlaveMesDIa(mesC, diaSemana);

				ValorMesDia v = hash2A.get(llaveMesDia);
				
				if( v != null)
				{
					v.multas.enqueue(multa);
				}
				else
				{
					ValorMesDia valor = new ValorMesDia();
					valor.multas.enqueue(multa);
					hash2A.put(llaveMesDia, valor);
				}
				
				arbol3A.put(fecha, multa);
				
				Llave2B l2b = new Llave2B(medioDete, claseVehiculo, tipoServicio, localidad);
				Valor2B v2b = hash2B.get(l2b);
				if(v2b != null)
				{
					v2b.multas.enqueue(multa);
					System.out.println(medioDete +" "+ claseVehiculo +" "+ tipoServicio +" "+ localidad);
				}
				else
				{
					Valor2B val2B = new Valor2B();
					val2B.multas.enqueue(multa);
					hash2B.put(l2b, val2B);
				}
				double lati = geometria.getLatitud();
				arbol3B.put(lati, multa);
			} //llave for grande
			

		}//llave try
		catch (IOException e) 
		{
			System.out.println("No se encontro el archivo de carga");;
		} catch (ParseException e1) 
		
		{
			// TODO Auto-generated catch block
			System.out.println("La fecha no esta en el formato correcto");;
		}
	}


	public String darInfoCargaDatos() throws noExisteObjetoException, FileNotFoundException
	{
		String msj = "";

		long inicio = System.currentTimeMillis();
		cargarDatos();
		long fin = System.currentTimeMillis();
		msj += "El tiempo total de carga en milis es de " + (fin - inicio) + "ms \n";
		msj += "La cantidad de comparendos es de: " + datos.size() +"\n";
		msj += "El comparendo con el ID mas alto es: " + heapMayorID.max().toString() + "\n";

		return msj;
	}


	public String mayorGravedad(int m)
	{
		String msj = "";

		for(int i = 1; i <= m; i++)
		{
			msj += i + "." + heap1A.delMax().toString()+ "\n"; 
		}

		return msj;
	}

	public String mesDia(int pMes, String dia)
	{
		String msj = "";
		LlaveMesDIa llave = new LlaveMesDIa(pMes, dia);
		ValorMesDia v = hash2A.get(llave);

		if(v == null ) 
		{
			return "No hay comparendos con los datos indicados";
		}

		else
		{
			for(int i = 1; i <= v.multas.size(); i++)
			{
				Multa multa = v.multas.dequeue();
				msj += i +". " + multa.toString() + "\n" ; 
				v.multas.enqueue(multa);
			}
		}
		return msj;

	}
	public String mesDia(int pMes, int pDia)
	{
		String dia;
		switch (pDia)
		{
		case 1:
			dia = "D";
			break;
		case 2: 
			dia  = "L";
			break;
		case 3 :
			dia = "M";
			break;
		case 4 : 
			dia = "I";
			break;
		case 5 : 
			dia = "J";
			break;
		case 6 : 
			dia = "V";
			break;
		case 7 : 
			dia = "S";
			break;
		}

		String msj = "";
		LlaveMesDIa llave = new LlaveMesDIa(pMes, pDia);
		ValorMesDia v = hash2A.get(llave);

		if(v == null ) 
		{
			return "No hay comparendos con los datos indicados";
		}

		else
		{
			for(int i = 1; i <= v.multas.size(); i++)
			{
				msj += i +". " + v.multas.dequeue().toString() + "\n" ; 
			}
		}
		return msj;

	}

	public String fechaHoraLoc(String min, String max, String localidad) throws ParseException
	{
		String msj = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date fMin = sdf.parse(min);
		Date fMax = sdf.parse(max);
	
		
		Queue<Date> q = (Queue) arbol3A.keys(fMin, fMax);
		int tam = q.size();
		if(tam == 0) return "no hay comparendos";
		for(int i = 1; i <= tam; i++ )
		{
			Multa m = arbol3A.get(q.dequeue());
			if(m.getLocalidad().compareToIgnoreCase(localidad) == 0 )
			{
				msj += m.toString() + "\n";
			}
		}
		return msj;
	}

	public String comparendosMasCercanos(int m)
	{
		String msj = "";

		for(int i = 1; i <= m; i++)
		{
			msj += i + "." + heap1B.delMax().toString() + "\n";
		}

		return msj;
	}

	public String reque2B(String medioDete, String claseVehi, String tipoServi, String loc)
	{
		String msj = "";
		
		Llave2B llave = new Llave2B(medioDete, claseVehi, tipoServi, loc);
		Valor2B val = hash2B.get(llave);
		if(val == null) return "no se encontraron comparendos con esos valores";
		int tam = val.multas.size();
		for(int i = 1; i <= tam; i++)
		{
			msj += val.multas.dequeue().toString() + "\n";
		}
		
		return msj;
	}

	public String darMultasLatitudMinMax(double min, double max, String vehiculo)
	{
		String msj = "";
		
		Queue<Double> q = (Queue) arbol3B.keys(min, max);
		int tam = q.size();
		for(int i = 1; i <= tam; i++)
		{
			Multa m = arbol3B.get(q.dequeue());
			if(m.getVehiculo().compareToIgnoreCase(vehiculo) == 0)
			{
				msj +=  m.toString() + "\n";
			}
		}
		return msj;
	}

	public void reque1C(int intervalo)
	{

	}

	public void reque2C()
	{

	}

	public void reque1C()
	{

	}

}//llave clase
