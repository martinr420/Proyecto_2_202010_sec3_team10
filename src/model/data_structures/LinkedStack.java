package model.data_structures;

import java.util.Iterator;

public class LinkedStack <K> implements ILinkedStack<K>, Iterable <K>
{

	private Nodo<K> primero;

	private Nodo<K> ultimo;

	private int tamano;

	
	public LinkedStack()
	{
		primero = null;

		ultimo = null;

		tamano = 0;
	}
	
	
	public void push (Nodo <K> item) {
		
		if(primero == null)
		{
			primero = item;
			ultimo = item;

		}
		else
		{
			item.cambiarAnterior(ultimo);
			ultimo.cambiarSiguiente(item);
			ultimo = item;
		}

		tamano++;
	
		
	}

	public Nodo <K> pop() throws noExisteObjetoException
	{
		if(tamano == 0)
		{
			throw new noExisteObjetoException();
		}
		else
		{
			Nodo<K> aEliminar = ultimo;
			
			if(aEliminar.darAnterior() != null)
			{
				
				ultimo = aEliminar.darAnterior();

				ultimo.desconectarSiguiente();
				


			}
			else
			{
			
				ultimo = null;
				primero = null;
				


			}



			tamano--;

			return aEliminar;
		}
	}

	public boolean isEmpty() {

		boolean si = false;
		
		if (tamano == 0)
		{
			si = true;
		}
		
		return si;
	}

	public int size() {

		return tamano;
	}

	public Nodo <K> darUltimo() throws noExisteObjetoException
	{
		if (ultimo == null)
		{
			throw new noExisteObjetoException();
		}
		
		else return ultimo;
	}


	public Iterator<K> iterator() {
		// TODO Auto-generated method stub
		return (Iterator<K>) ultimo;
	}

}