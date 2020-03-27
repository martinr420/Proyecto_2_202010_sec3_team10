package model.logic;

import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import model.data_structures.AVLTreeST;
import model.data_structures.LinearProbingHashST;
import sun.util.resources.cldr.en.CalendarData_en_AS;

import model.data_structures.MaxPQ;
import model.data_structures.Nodo;
import model.data_structures.Queue;
import model.data_structures.SeparateChainingHashST;
import model.data_structures.noExisteObjetoException;

/**
 * Definicion del modelo del mundo
 *
 */
public class Model {
	/**
	 * Atributos del modelo del mundo
	 */
	private Queue<Multa> datos; 
	private SeparateChainingHashST<LlaveMesDIa, Multa> hash2A;
	private MaxPQ<Multa> heap1A;
	private AVLTreeST<LlaveFechaHora, Multa> arbol3A; 
	private MaxPQ<CercanoALaEstacion> heap1B;
	private LinearProbingHashST<Llave2B, Valor2B> hash2B;
	private AVLTreeST<Geo, Multa> arbol3B;

	



	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Model()
	{
		datos = new Queue<Multa>();
		hash2A = new SeparateChainingHashST<LlaveMesDIa, Multa>(100);
		heap1A= new MaxPQ<Multa>(100);
		arbol3A = new AVLTreeST<LlaveFechaHora, Multa>();
		heap1B = new MaxPQ<CercanoALaEstacion>();
		hash2B = new LinearProbingHashST<Llave2B, Valor2B>();
		arbol3B = new AVLTreeST<Geo, Multa>();
		


	}


	private void cargarDatos() throws noExisteObjetoException 
	{
		String path = "./data/comparendos.geojson";
		JsonReader lector;


		try {
			lector = new JsonReader(new FileReader(path));
			JsonElement elem = JsonParser.parseReader(lector);
			JsonObject ja = elem.getAsJsonObject();

			JsonArray features = ja.getAsJsonArray("features");

			for(JsonElement e : features)
			{
				JsonObject propiedades = (JsonObject) e.getAsJsonObject().get("properties");

				long id = propiedades.get("OBJECTID").getAsLong();

				String cadenaFecha = propiedades.get("FECHA_HORA").getAsString();
				String anio = cadenaFecha.substring(0,3);
				String mes = cadenaFecha.substring(5,6);
				String dia = cadenaFecha.substring(8,9);
				String hora = cadenaFecha.substring(11,12);
				String min = cadenaFecha.substring(14,15);
				String seg = cadenaFecha.substring(17,18);
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


			} //llave for grande

		}//llave try
		catch (IOException e) 
		{
			e.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	} 

	private Multa mayorID()
	{
		Multa mayor = new Multa(-1, null, "","","","","","", "", null);
		datos.enqueue(mayor);

		for(int i = 0; i < datos.size(); i++)
		{
			Multa actual = datos.dequeue();
			datos.enqueue(actual);
			if(mayor.getId() < actual.getId())
			{
				mayor = actual;
			}
		}

		return mayor;

	}


	public String darInfoCargaDatos() throws noExisteObjetoException
	{
		String msj = "";

		long inicio = System.currentTimeMillis();
		cargarDatos();
		long fin = System.currentTimeMillis();
		msj += "el tiempo total de carga en milis es de " + (fin - inicio) + "ms <br>";
		msj += "La cantidad de comparendos es de: " + datos.size();
		msj += "El comparendo con el ID mas alto es :" + mayorID().toString();

		return msj;
	}


	public MaxPQ<Multa> mayorGravedad(int m)
	{
		return null;
	}

	public SeparateChainingHashST<LlaveMesDIa, ValorMesDia> mesDia(int pMes, char pDia)
	{
		return null;
	}

	public Iterable<Multa> fechaHoraLoc(Date min, Date max, String localidad)
	{
		return null;
	}

	public MaxPQ<CercanoALaEstacion> comparendosMasCercanos()
	{
		return null;
	}
	
	public Iterable<Multa> reque2B()
	{
		return null;
	}
	
	public AVLTreeST<Geo, Multa> darMultasLatitudMinMax(double min, double max, String vehiculo)
	{
		return null;
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
