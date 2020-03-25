package model.data_structures;

import java.util.Iterator;

public class LinkedQueue <K> implements ILinkedQueue<K>, Iterable <K> 

{
	private Nodo<K> primero;

	private Nodo<K> ultimo;

	private int tamano;

	
	public LinkedQueue ()
	{
		primero = null;

		ultimo = null;

		tamano = 0;
	}
	
	
	public void enqueue(K key) {
		Nodo<K> actual = new Nodo<>(key);
		if(primero == null)
		{
			primero = actual;
			ultimo = actual;

		}
		else
		{
			actual.cambiarAnterior(ultimo);
			ultimo.cambiarSiguiente(actual);
			ultimo = actual;
		}

		tamano++;
		
	}

	public K dequeue() throws noExisteObjetoException
	{
		if(tamano == 0)
		{
			throw new noExisteObjetoException();
		}
		else
		{
			Nodo<K> aEliminar = primero;
			
			if(aEliminar.darSiguiente() != null)
			{
				primero = aEliminar.darSiguiente();
				primero.desconectarAnterior();
			}
			else
			{
				ultimo = null;
				primero = null;
			}
			tamano--;

			return aEliminar.darGenerico();
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


	public K darPrimero() throws noExisteObjetoException
	{
		if (primero == null)
		{
			throw new noExisteObjetoException();
		}
		
		else return primero.darGenerico();
	}


	public Iterator<K> iterator() 
	{
		IterQueue iter = new IterQueue();
		// TODO Auto-generated method stub
		return iter;
	}
	private class IterQueue implements Iterator<K>
	{

		private Nodo<K> actual;
		private boolean yaItero;
		
		public IterQueue()
		{
			yaItero = false;
			actual = primero;
		}
		public boolean hasNext() 
		{
			boolean hasNext = false;
			if(!yaItero)
			{
				hasNext = actual != null;
			}
			else
			{
				hasNext = actual.darSiguiente() != null;
			}
		
			return hasNext;
		}

		@Override
		public K next()
		{
			if(yaItero)
			{
				actual = actual.darSiguiente();
			}
			else
			{
				yaItero = true;
			}
			// TODO Auto-generated method stub
			return (K) actual;
		}
		
	}
	
	

}