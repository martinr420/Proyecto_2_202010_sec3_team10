package model.data_structures;

public class ArregloDinamico<K> implements IArregloDinamico
{
	 
	 private int tamanoMax;
	 /**
	  * Numero de elementos presentes en el arreglo (de forma compacta desde la posicion 0)
	  */
	 private int tamanoAct;
	 /**
	  * Arreglo de elementos de tamaNo maximo
	  */
	 private K elementos[ ];


	public ArregloDinamico( int max )
	{
		elementos = (K[]) new Object[max];
		tamanoMax = max;
		tamanoAct = 0;
	}
	@Override
	public int darCapacidad() {
		// TODO Auto-generated method stub
		return tamanoMax;
	}

	@Override
	public int darTamano() {
		// TODO Auto-generated method stub
		return tamanoAct;
	}

	@Override
	public Object darElemento(int i) {
		// TODO Auto-generated method stub
		return elementos[i];
	}

	@Override
	public void agregar(Object dato) {
		
		if ( tamanoAct == tamanoMax )
		{  // caso de arreglo lleno (aumentar tamaNo)
			tamanoMax = 2 * tamanoMax;
			Object [ ] copia = elementos;
			elementos = (K[]) new Object[tamanoMax];
			for ( int i = 0; i < tamanoAct; i++)
			{
				elementos[i] = (K) copia[i];
			} 
			System.out.println("Arreglo lleno: " + tamanoAct + " - Arreglo duplicado: " + tamanoMax);
		}	
		elementos[tamanoAct] = (K) dato;
		tamanoAct++;
		// TODO Auto-generated method stub

	}

	@Override
	public Object buscar(Object dato) {K elDato = null;

	for(int i = 0; i < elementos.length; i++)
	{
		if(elementos[i] == dato)
		{
			elDato = elementos[i];
		}

	}
	// TODO implementar
	// Recomendacion: Usar el criterio de comparacion natural (metodo compareTo()) definido en Strings.
	return elDato;
	}

	@Override
	public Object eliminar(Object dato) 
	{
		K elDato = null;

	for(int i = 0; i < elementos.length; i++)
	{
		if(elementos[i] == dato)
		{
			elDato = elementos[i];
			elementos[i] = null;
		}

	}
	// TODO implementar
	// Recomendacion: Usar el criterio de comparacion natural (metodo compareTo()) definido en Strings.
	return elDato;
	}

}

