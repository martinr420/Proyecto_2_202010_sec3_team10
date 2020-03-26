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
import model.data_structures.HashLP;
import model.data_structures.HashSC;
import model.data_structures.ILinkedQueue;
import model.data_structures.LinkedQueue;
import sun.util.resources.cldr.en.CalendarData_en_AS;
import model.data_structures.MaxHeapCP;
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
	private HashSC hash;
	private MaxHeapCP heap;
	private AVLTreeST arbol;
	



	/**
	 * Constructor del modelo del mundo con capacidad predefinida
	 */
	public Model()
	{
		hash = new HashSC<>(10);
		heap = new MaxHeapCP();
		arbol = new AVLTreeST();


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
				String fechaConcatenadita = anio + "-" + mes + dia + " " + hora + ":" + min + ":" + seg; 
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

				Gravedad grav = new Gravedad(tipoServicio, infraccion);


			} //llave for grande

		}//llave try
		catch (IOException e) 
		{
			e.printStackTrace();
		} catch (ParseException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
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
		Multa laMulta = new Multa(-1, null, "", "", "", "", "","", null);

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
		for(Multa multa: datos) {
			if(multa.getId() == pID) return multa;
		}
		return null;
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
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try {
			Date fechaReal1 = sdf.parse(fecha1);
			Date fechaReal2 = sdf.parse(fecha2);
			IArregloDinamico<Multa> listaMultas = new ArregloDinamico<Multa>(100); 
			for(Multa multa: datos) {
				if(multa.getFecha().equals(fechaReal1) || multa.getFecha().equals(fechaReal2)) {
					listaMultas.agregar(multa);
				}
			}
			IArregloDinamico<CodigoInfraccion> codigos = new ArregloDinamico<CodigoInfraccion>(100);
			for(Multa multa: listaMultas) {
				CodigoInfraccion codigo = null;
				for(int i = 0; i < codigos.darTamano() && codigo == null; i++) {
					if(codigos.darElemento(i).darCodigoInfraccion().equals(multa.infraccion)) {
						codigo = codigos.darElemento(i);
						codigo.incrementarNumeroComparendos();
					}
				}
				if(codigo == null) {
					codigo = new CodigoInfraccion(multa.infraccion);
					codigos.agregar(codigo);
				}
				if(multa.getFecha().equals(fechaReal1)) {
					codigo.incrementarNumeroComparendos();
				} else {
					codigo.incrementarNumeroComparendos2();
				}
			}
			ordenarArreglo(codigos, 0, codigos.darTamano() - 1);
			System.out.println("Infraccion  | " + fecha1 + "  | " + fecha2);
			for(CodigoInfraccion codigo : codigos) {
				System.out.println(codigo.darCodigoInfraccion() + "   | " + codigo.darNumeroComparendos() + "     | " + codigo.darNumeroComparendos2());
			}
			//
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			System.out.println("Por favor ingrese las fechas con el formato pedido >:v");
			e.printStackTrace();
		}
	}


	public void darMultasComparacionTipoServicio() {
		IArregloDinamico<Multa> listaMultas = new ArregloDinamico<Multa>(100); 
		for(Multa multa: datos) {
			if(multa.getServicio().equals("Particular") || multa.getServicio().equals("Público")) {
				listaMultas.agregar(multa);
			}
		}
		IArregloDinamico<CodigoInfraccion> codigos = new ArregloDinamico<CodigoInfraccion>(100);
		for(Multa multa: listaMultas) {
			CodigoInfraccion codigo = null;
			for(int i = 0; i < codigos.darTamano() && codigo == null; i++) {
				if(codigos.darElemento(i).darCodigoInfraccion().equals(multa.infraccion))
					codigo = codigos.darElemento(i); 
			}
			if(codigo == null) {
				codigo = new CodigoInfraccion(multa.infraccion);
				codigos.agregar(codigo);

			}
			if(multa.getServicio().equals("Particular")) {
				codigo.incrementarNumeroComparendos();
			} else {
				codigo.incrementarNumeroComparendos2();
			}
		}
		ordenarArreglo(codigos, 0, codigos.darTamano() - 1);
		System.out.println("Infraccion  | " + "Particular" + "  | " + "Publico");
		for(CodigoInfraccion codigo : codigos) {
			System.out.println(codigo.darCodigoInfraccion() + "   | " + codigo.darNumeroComparendos() + "     | " + codigo.darNumeroComparendos2());
		}
	}

	public void comparendosEntreFechas(String fechaInicial, String fechaFinal, String localidad) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		try
		{
			Date fechaReal1 = sdf.parse(fechaInicial);
			Date fechaReal2 = sdf.parse(fechaFinal);
			IArregloDinamico<Multa> listaMultas = new ArregloDinamico<Multa>(100); 
			for(Multa multa: datos) {
				if(multa.getLocalidad().equals(localidad) && multa.getFecha().compareTo(fechaReal1) > 0 && multa.getFecha().compareTo(fechaReal2) < 0) {
					listaMultas.agregar(multa);
				}
			}

			IArregloDinamico<CodigoInfraccion> codigos = new ArregloDinamico<CodigoInfraccion>(100);
			for(Multa multa: listaMultas) {
				CodigoInfraccion codigo = null;
				for(int i = 0; i < codigos.darTamano() && codigo == null; i++) {
					if(codigos.darElemento(i).darCodigoInfraccion().equals(multa.infraccion))
						codigo = codigos.darElemento(i); 
				}
				if(codigo == null) {
					codigo = new CodigoInfraccion(multa.infraccion);
					codigos.agregar(codigo);

				}
				if(multa.getLocalidad().equals(localidad) && multa.getFecha().compareTo(fechaReal1) > 0 || multa.getFecha().compareTo(fechaReal2) < 0) {
					codigo.incrementarNumeroComparendos();
				}
			}
			ordenarArreglo(codigos, 0, codigos.darTamano() - 1);
			System.out.println("Infraccion  | " + "#Comparendos");
			for(CodigoInfraccion codigo : codigos) {
				System.out.println(codigo.darCodigoInfraccion() + "   | " + codigo.darNumeroComparendos() );
			}
		}
		catch(ParseException pse)
		{
			pse.printStackTrace();
		}
	}
	
    public void darMultasOrdenadasN (String n, String fechaInicial, String fechaFinal)
    {
    	int elN = Integer.parseInt(n);
		
		IArregloDinamico<Multa> listaMultas = new ArregloDinamico<Multa>(100);
		for (Multa laMulta: datos)
		{
			
		
			if(laMulta.estaDentroDeFecha(fechaInicial, fechaFinal)) {
				listaMultas.agregar(laMulta);
				
		
    	}
		}
			IArregloDinamico<CodigoInfraccion> codigos = new ArregloDinamico<CodigoInfraccion>(100);
			for(Multa multa: listaMultas) {
				CodigoInfraccion codigo = null;
				for(int i = 0; i < codigos.darTamano() && codigo == null; i++) {
					if(codigos.darElemento(i).darCodigoInfraccion().equals(multa.infraccion))
						codigo = codigos.darElemento(i); 
				}
				if(codigo == null) {
					codigo = new CodigoInfraccion(multa.infraccion);
					codigos.agregar(codigo);

				}
				if(multa.estaDentroDeFecha(fechaInicial, fechaFinal) ){
					codigo.incrementarNumeroComparendos();
				}
			}
			
			ordenarArregloPorNumeroComparendos(codigos, 0, codigos.darTamano() - 1);
			System.out.println("Infraccion  | " + "#Comparendos");
			for(int i = 0; i < elN; i++) 
			{
				CodigoInfraccion codigo = codigos.darElemento(i);
				System.out.println(codigo.darCodigoInfraccion() + "   | " + codigo.darNumeroComparendos() );
			}
		
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

	public <K extends Comparable<K>> void ordenarArreglo(IArregloDinamico<K> ordenar, int low, int high) {
		if(low < high) {
			int pi = partition(ordenar, low, high);
			ordenarArreglo(ordenar, low, pi-1);
			ordenarArreglo(ordenar, pi+1, high);
		}
	}
	
	public <K extends Comparable<K>> void ordenarArregloPorNumeroComparendos(IArregloDinamico<CodigoInfraccion> ordenar, int low, int high) {
		if(low < high) {
			int pi = partition2(ordenar, low, high);
			ordenarArreglo(ordenar, low, pi-1);
			ordenarArreglo(ordenar, pi+1, high);
		}
	}

	private <K extends Comparable <K>> int partition(IArregloDinamico<K> ordenar, int low, int high) {
		K pivot = ordenar.darElemento(high);  
		int i = (low-1); 
		for (int j=low; j<high; j++) 
		{ 
			 
			if (ordenar.darElemento(j).compareTo(pivot) < 0) 
			{ 
				i++; 

				
				K temp = ordenar.darElemento(i); 

				ordenar.modificar(ordenar.darElemento(j), i);
				ordenar.modificar(temp, j);
			} 
		} 

		K temp = ordenar.darElemento(i+1);
		ordenar.modificar(ordenar.darElemento(high), i+1);
		ordenar.modificar(temp, high);

		return i+1; 
	}
	
	private <K extends Comparable <K>> int partition2(IArregloDinamico<CodigoInfraccion> ordenar, int low, int high) 
	{
		int pivote = ordenar.darElemento(high).darNumeroComparendos();  
		int i = (low-1); 
		for (int j=low; j<high; j++) 
		{ 
			 
			if (ordenar.darElemento(j).darNumeroComparendos() < pivote) 
			{ 
				i++; 

				
				CodigoInfraccion temp = ordenar.darElemento(i); 

				ordenar.modificar(ordenar.darElemento(j), i);
				ordenar.modificar(temp, j);
			} 
		} 

		CodigoInfraccion temp = ordenar.darElemento(i+1);
		ordenar.modificar(ordenar.darElemento(high), i+1);
		ordenar.modificar(temp, high);

		return i+1; 
	}




}//llave clase
