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
import java.util.GregorianCalendar;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.sun.org.apache.xerces.internal.impl.io.UTF8Reader;

import model.data_structures.AVLTreeST;
import model.data_structures.IHashTable;
import model.data_structures.IMaxColaCP;
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
	private IMaxColaCP<Multa> heapMayorID;
	private IHashTable<LlaveMesDIa, Queue<Multa>> hash2A;
	private IMaxColaCP<Multa> heap1A;
	private IHashTable<Date, Multa> arbol3A; 
	private IMaxColaCP<Multa> heap1B;
	private IHashTable<Llave2B, Queue<Multa>> hash2B;
	private IHashTable<Double, Multa> arbol3B;





	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Model()
	{
		datos = new Queue<Multa>();

		heapMayorID = new MaxPQ<Multa>(31);

		ComparadorGravedad c = new ComparadorGravedad();
		heap1A= new MaxPQ<Multa>(31, c);

		hash2A = new SeparateChainingHashST<LlaveMesDIa, Queue<Multa>>(31);

		arbol3A = new AVLTreeST<Date, Multa>();

		ComparadorCercanoALaEstacion cc = new ComparadorCercanoALaEstacion();
		heap1B = new MaxPQ<Multa>(31, cc);

		hash2B = new LinearProbingHashST<Llave2B, Queue<Multa>>(31);

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

			
				GregorianCalendar gc = new GregorianCalendar();
				gc.setTime(fecha);
				int diaSemana = gc.get(Calendar.DAY_OF_WEEK);
				int mesNum = gc.get(Calendar.MONTH) + 1;

				LlaveMesDIa llaveMesDia = new LlaveMesDIa(mesNum, diaSemana);

				Queue<Multa> queue1 = hash2A.get(llaveMesDia);
				
				if( queue1 != null)
				{
					queue1.enqueue(multa);
				}
				else
				{
					Queue<Multa> q1 = new Queue<Multa>();
					q1.enqueue(multa);
					hash2A.put(llaveMesDia, q1);
				}
				
				arbol3A.put(fecha, multa);
				
				Llave2B l2b = new Llave2B(medioDete, claseVehiculo, tipoServicio, localidad);
				Queue<Multa> v2b = hash2B.get(l2b);
				if(v2b != null)
				{
					v2b.enqueue(multa);
				}
				else
				{
					Queue<Multa> val2B = new Queue<Multa>();
					val2B.enqueue(multa);
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
		Queue<Multa> v = hash2A.get(llave);

		if(v == null ) 
		{
			return "No hay comparendos con los datos indicados";
		}

		else
		{
			int tam = v.size();
			for(int i = 1; i <= tam; i++)
			{
				Multa multa = v.dequeue();
				msj += i +". " + multa.toString() + "\n" ; 
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
		Queue<Multa> v = hash2A.get(llave);

		if(v == null ) return "No hay comparendos con los datos indicados";
		else
		{
			int tam = v.size();
			for(int i = 1; i <= tam; i++)
			{
				Multa m = v.dequeue();
				msj += i +". " + m.toString() + "\n" ; 
			}
		}
		return msj;

	}

	public String fechaHoraLoc(String min, String max, String localidad) throws ParseException
	{
		String msj = "";
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd-HH:mm:ss");
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
		
		Llave2B llave = new Llave2B(medioDete.toUpperCase(), claseVehi.toUpperCase(), tipoServi, loc.toUpperCase());
		Queue<Multa> val = hash2B.get(llave);
		if(val == null) return "no se encontraron comparendos con esos valores";
		int tam = val.size();
		for(int i = 1; i <= tam; i++)
		{
			msj += val.dequeue().toString() + "\n";
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
