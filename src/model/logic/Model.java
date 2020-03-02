package model.logic;

import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import model.data_structures.ArregloDinamico;
import model.data_structures.ILinkedQueue;
import model.data_structures.IListaDoblementeEncadenada;
import model.data_structures.LinkedQueue;
import model.data_structures.ListaDoblementeEncadenada;
import model.data_structures.ListaDoblementeEncadenada.IteratorLista;
import model.data_structures.Nodo;
import model.data_structures.noExisteObjetoException;

/**
 * Definicion del modelo del mundo
 *
 */
public class Model {
	/**
	 * Atributos del modelo del mundo
	 */
	private IListaDoblementeEncadenada<Multa> datos;



	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Model()
	{
		datos = new ListaDoblementeEncadenada<Multa>();


	}

	/**
	 * Constructor del modelo del mundo con capacidad dada
	 * @param tamano
	 */
	//	public Modelo(int capacidad)
	//	{
	//		//datos = new ArregloDinamico(capacidad);
	//	}

	public ListaDoblementeEncadenada<Multa> darDatos()
	{
		return (ListaDoblementeEncadenada<Multa>) datos;
	}

	/**
	 * Servicio de consulta de numero de elementos presentes en el modelo 
	 * @return numero de elementos presentes en el modelo
	 */
	public int darTamano()
	{
		return datos.darTamano();
	}

	/**
	 * Requerimiento de agregar dato
	 * @param dato
	 */
	public void agregar(Multa dato)
	{	
		datos.agregarNodoAlFinal(dato);;
	}

	/**
	 * Requerimiento buscar dato
	 * @param dato Dato a buscar
	 * @return dato encontrado
	 * @throws noExisteObjetoException 
	 */
	public int buscarPosicion(Multa dato) throws noExisteObjetoException
	{
		return datos.darPosicionNodo(dato);
	}

	/**
	 * Requerimiento eliminar dato
	 * @param dato Dato a eliminar
	 * @return dato eliminado
	 * @throws noExisteObjetoException 
	 */
	public Multa eliminar(Multa dato) throws noExisteObjetoException
	{
		return datos.EliminarNodoObj(dato);
	}

	private void cargarDatos() throws noExisteObjetoException 
	{
		String path = "./data/comparendos_dei_2018.geojson";
		JsonReader lector;


		try {


			ListaDoblementeEncadenada<Multa> listaMultas = new ListaDoblementeEncadenada<>();


			lector = new JsonReader(new FileReader(path));
			JsonElement elem = JsonParser.parseReader(lector);
			JsonObject ja = elem.getAsJsonObject();

			JsonArray features = ja.getAsJsonArray("features");


			for(JsonElement e : features)
			{



				JsonObject propiedades = (JsonObject) e.getAsJsonObject().get("properties");
				long id = propiedades.get("OBJECTID").getAsLong();
				String fecha = propiedades.get("FECHA_HORA").getAsString();
				String medioDete = propiedades.getAsJsonObject().get("MEDIO_DETE").getAsString();
				String claseVehiculo = propiedades.getAsJsonObject().get("CLASE_VEHI").getAsString();
				String tipoServicio = propiedades.getAsJsonObject().get("TIPO_SERVI").getAsString();
				String infraccion = propiedades.getAsJsonObject().get("INFRACCION").getAsString();
				String descripcion = propiedades.getAsJsonObject().get("DES_INFRAC").getAsString();
				String localidad = propiedades.getAsJsonObject().get("LOCALIDAD").getAsString();


				JsonObject geometry = (JsonObject) e.getAsJsonObject().get("geometry");

				String tipo = geometry.get("type").getAsString();

				double[] listaCoords = new double[3];

				JsonArray coordsJson = geometry.getAsJsonArray("coordinates");




				for(int i = 0; i < coordsJson.size(); i ++)
				{
					listaCoords[i] = coordsJson.get(i).getAsDouble();


				}

				Geo geometria = new Geo(tipo, listaCoords);

				Multa multa = new Multa(id, fecha, medioDete, claseVehiculo, tipoServicio, infraccion, descripcion, localidad, geometria);



				datos.agregarNodoAlFinal(multa);


			} //llave for grande

		}//llave try
		catch (IOException e) 
		{
			e.printStackTrace();
		}



	} //llave metodo


	public void darInfoCargaDatos() throws noExisteObjetoException
	{

		long inicio = System.currentTimeMillis();
		cargarDatos();
		long fin = System.currentTimeMillis();
		System.out.println("el tiempo total de carga en milis es de " + (fin - inicio) + "ms");
		System.out.println("La cantidad de comparendos es de: "+datos.darTamano());
		System.out.println("EL comparendo con el ID mas alto es :" + BuscarComparendoIDMasAlto().toString());
		System.out.println("El minimax es: ");
		System.out.println("longitud minima: " + darMinimax()[0]);
		System.out.println("longitud maxima: " + darMinimax()[1]);
		System.out.println("latitud minima: " + darMinimax()[2]);
		System.out.println("latitud maxima:" + darMinimax()[3]);


	}


	private Multa BuscarComparendoIDMasAlto() 
	{

		IteratorLista lista = (IteratorLista) datos.iterator();
		Multa laMulta = new Multa(-1, "", "", "", "", "", "","", null);

		while(lista.hasNext())
		{
			Multa iterNodo =  (Multa) lista.next();

			if(iterNodo.getId() > laMulta.getId() )
			{
				laMulta = iterNodo;
			}
		}
		return laMulta;
	}

	public Multa buscarComparendoPorId(long pID)
	{
		boolean parar = false;
		Multa laMulta = null;
		Nodo<Multa> actual = (Nodo<Multa>) datos.iterator();
		while(actual.hasNext() && !parar)
		{
			if(actual.darGenerico().getId() == pID)
			{
				parar = true;
				laMulta = actual.darGenerico();
				actual = actual.next();

			}
		}


		return laMulta;
	}


	public double[] darMinimax()
	{
		/*
		 * arreglo [0] = latitud minima 
		 * arreglo [1] = latitud maxima
		 * arreglo [2] = longitud minima 
		 * arreglo [3] = longitud maxima
		 */



		double[] arreglo = new double[4];

		double longitudMin = 180; // inicializada en el valor maximo que puede tener la longitud

		double longitudMax = -180; // inicializada en el valor minimo que puede tener la longitud

		double latitudMin = 90; // inicializada en el valor maximo que puede tener la latitud

		double latitudMax = -90; // inicializada en el valor minimo que puede tener la latitud 




		IteratorLista lista = (IteratorLista) datos.iterator();

		while(lista.hasNext())
		{
			Multa laMulta = (Multa) lista.next();
			double longitud = laMulta.getGeo().getCoordenadas()[0];

			double latitud = laMulta.getGeo().getCoordenadas()[1];
			if(longitud < longitudMin)
			{
				longitudMin = longitud;
			}
			if(longitud > longitudMax)
			{
				longitudMax = longitud;
			}

			if(latitud < latitudMin)
			{
				latitudMin = latitud;
			}
			if(latitud > latitudMax)
			{
				latitudMax = latitud;
			}
		}

		arreglo[0] = longitudMin;
		arreglo[1] = longitudMax;
		arreglo[2] = latitudMin;
		arreglo[3] = latitudMax;




		return arreglo;
	}


	public Multa darComparendoLocalidad(String pLocalidad)
	{
		boolean encontro = false;
		Multa laMulta = null;
		IteratorLista iter = (IteratorLista) datos.iterator();
		while(iter.hasNext() && !encontro)
		{
			Multa actual = (Multa) iter.next();
			if(actual.getLocalidad().compareToIgnoreCase(pLocalidad) == 0)
			{
				encontro = true;
				laMulta = actual;
			}

		}


		return laMulta;
	}

	public ListaDoblementeEncadenada<Multa> darMultaPorFecha(String pFecha)
	{
		ListaDoblementeEncadenada<Multa> arreglo = new ListaDoblementeEncadenada<Multa>();
		IteratorLista iter = (IteratorLista) datos.iterator();

		while(iter.hasNext())
		{
			Multa laMulta = (Multa) iter.next();
			if(laMulta.getFecha().equals(pFecha))
			{
				arreglo.agregarNodoAlFinal(laMulta);
			}
		}
		shellSortFecha(arreglo);



		return arreglo;
	}



	public void shellSortFecha(ListaDoblementeEncadenada<Multa> datos)
	{
		int salto = datos.darTamano()/2;

		while( salto != 0 )
		{
			boolean intercambio = true;

			while(intercambio)
			{ 
				intercambio = false;
				for(int i = salto; i < datos.darTamano(); i++)
				{
					try {
						if(datos.darNodoEnPos(i-salto).compareTo(datos.darNodoEnPos(i)) > 0)
						{ 
							Multa temp = datos.darNodoEnPos(i);
							Multa elNodo =  datos.darNodoEnPos(i - salto);
							datos.cambiarNodoEnPos(i, elNodo);
							datos.cambiarNodoEnPos(i - salto, temp);

							intercambio=true;
						}
					} catch (noExisteObjetoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			salto/=2;
		}
	}




	public void darMultasComparacion(String fecha1, String fecha2)
	{
		ListaDoblementeEncadenada<Multa> listaF1 = darMultaPorFecha(fecha1);
		ordenarPorInfraccion(listaF1);
		IteratorLista iterF1 = (IteratorLista) listaF1.iterator();

		ListaDoblementeEncadenada<Infraccion> listaInfra1 = new ListaDoblementeEncadenada<>();
		Multa laMulta = (Multa) iterF1.next();
		Infraccion infra1 = new Infraccion(laMulta.getInfraccion());
		listaInfra1.agregarNodoAlFinal(infra1);
		while(iterF1.hasNext())
		{

			laMulta = (Multa) iterF1.next();

			if(infra1.getInfra().equals(laMulta.getInfraccion()))
			{
				infra1.sumar();
			}
			else
			{
				Infraccion agregar = new Infraccion(laMulta.getInfraccion());
				listaInfra1.agregarNodoAlFinal(agregar);
				infra1 = agregar;
			}

			ListaDoblementeEncadenada<Multa> listaF2 = darMultaPorFecha(fecha2);
			ordenarPorInfraccion(listaF2);
			IteratorLista iterF2 = (IteratorLista) listaF2.iterator();
			Multa laMulta2 = (Multa) iterF2.next();
			Infraccion infra2 = new Infraccion(laMulta.getInfraccion());


			ListaDoblementeEncadenada<Infraccion> listaInfra2 = new ListaDoblementeEncadenada<>();
			listaInfra2.agregarNodoAlFinal(infra2);

			while(iterF2.hasNext())
			{

				laMulta = (Multa) iterF2.next();

				if(infra2.getInfra().equals(laMulta2.getInfraccion()))
				{
					infra2.sumar();
				}
				else
				{
					Infraccion agregar = new Infraccion(laMulta.getInfraccion());
					listaInfra2.agregarNodoAlFinal(agregar);
					infra2 = agregar;
				}

				System.out.println("Infracción    | "+ fecha1 + " | " + fecha2);

				IteratorLista iteri1 = (IteratorLista) listaInfra1.iterator();
				IteratorLista iteri2 = (IteratorLista) listaInfra2.iterator();


				Infraccion laInfra1 = (Infraccion) iteri1.next();
				Infraccion laInfra2 = (Infraccion) iteri2.next();

				while(iteri1.hasNext() || iteri2.hasNext())
				{

					if(laInfra1.getInfra().equals(laInfra2))
					{
						System.out.println(laInfra1.getInfra() + " |" + laInfra1.getTotal() + " | " + laInfra2.getTotal());
						laInfra1 = (Infraccion) iteri1.next();
						laInfra2 = (Infraccion) iteri2.next();
					}
					else if(laInfra1.getInfra().compareTo(laInfra2.getInfra()) < 0)
					{
						boolean encontro = false;
						while(!encontro || iteri1.hasNext())
						{
							laInfra1 = (Infraccion) iteri1.next();
							if(laInfra1.compareTo(laInfra2) == 0)
							{
								encontro = true;
							}
						}
					}
					else
					{
						boolean encontro = false;
						while(!encontro || iteri2.hasNext())
						{
							laInfra2 = (Infraccion) iteri2.next();
							if(laInfra1.compareTo(laInfra2) == 0)
							{
								encontro = true;
							}
						}

					}
					
					
				}

			}
		}


		;





	}



	public void ordenarPorInfraccion(ListaDoblementeEncadenada<Multa> datos)
	{
		int salto = datos.darTamano()/2;

		while( salto != 0 )
		{
			boolean intercambio = true;

			while(intercambio)
			{ 
				intercambio = false;
				for(int i = salto; i < datos.darTamano(); i++)
				{
					try {
						if(datos.darNodoEnPos(i-salto).compareToInfraccion(datos.darNodoEnPos(i)) > 0)
						{ 
							Multa temp = datos.darNodoEnPos(i);
							Multa elNodo =  datos.darNodoEnPos(i - salto);
							datos.cambiarNodoEnPos(i, elNodo);
							datos.cambiarNodoEnPos(i - salto, temp);

							intercambio=true;
						}
					} catch (noExisteObjetoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			salto/=2;
		}
	}




	public Multa ConsultarComparendoPorInfraccion(String pInfraccion)
	{
		boolean encontro = false;
		IteratorLista iter = (IteratorLista) datos.iterator();
		Multa aRetornar = null;
		
		while(iter.hasNext() && !encontro)
		{
			Multa comparar = (Multa) iter.next();
			if(comparar.getInfraccion().equals(pInfraccion))
			{
				encontro = true;
				aRetornar = comparar;
			}
		}
		
		return aRetornar;
	}
	
	

	public ListaDoblementeEncadenada<Multa> darComparendosPorInfraccion(String pInfraccion)
	{
		ListaDoblementeEncadenada<Multa> laLista = new ListaDoblementeEncadenada<>();
		IteratorLista iter = (IteratorLista) datos.iterator();
		
		while(iter.hasNext())
		{
			Multa laMulta = (Multa) iter.next();
			if(laMulta.getInfraccion().equals(pInfraccion))
			{
				laLista.agregarNodoAlFinal(laMulta);
			}
		}
		

		return laLista;
		
	}

	public ListaDoblementeEncadenada<Localidad> ASCII()
	{
		ListaDoblementeEncadenada<Localidad> localidades = new ListaDoblementeEncadenada<>();
		
		ListaDoblementeEncadenada<Multa> lista = (ListaDoblementeEncadenada<Multa>) datos;
		
		ordenarPorLocalidad(lista);
		
		
		IteratorLista iter = (IteratorLista) lista.iterator();
		
		
		Multa multa = (Multa) iter.next();
		
		Localidad laLoc = new Localidad(multa.getLocalidad());
		localidades.agregarNodoAlFinal(laLoc);
		while(iter.hasNext())
		{
			multa = (Multa) iter.next();
			if(multa.getLocalidad().equals(laLoc.getNombre()))
			{
				laLoc.sumar();
			}
			else
			{
				Localidad aInsertar = new Localidad(multa.getLocalidad());
				
				localidades.agregarNodoAlFinal(aInsertar);
				laLoc = aInsertar;
			}
			
		}
		
		
		
		return localidades;
	}
	
	public void ordenarPorLocalidad(ListaDoblementeEncadenada<Multa> pLista)
	{
		int salto = datos.darTamano()/2;

		while( salto != 0 )
		{
			boolean intercambio = true;

			while(intercambio)
			{ 
				intercambio = false;
				for(int i = salto; i < datos.darTamano(); i++)
				{
					try {
						if(datos.darNodoEnPos(i-salto).compareToLocalidad(datos.darNodoEnPos(i)) > 0)
						{ 
							Multa temp = datos.darNodoEnPos(i);
							Multa elNodo =  datos.darNodoEnPos(i - salto);
							datos.cambiarNodoEnPos(i, elNodo);
							datos.cambiarNodoEnPos(i - salto, temp);

							intercambio = true;
						}
					} 
					catch (noExisteObjetoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

			}
			salto/=2;
		}
	}




}//llave clase
