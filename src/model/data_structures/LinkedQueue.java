package model.data_structures;

import java.util.Iterator;

public class LinkedQueue <K> implements ILinkedQueue <K>, Iterable <K> 

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


	public void enqueue(Nodo <K> item) {

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

	public K dequeue() throws noExisteObjetoException
	{
		// TODO Auto-generated method stub

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

		else
		{
			return primero.darGenerico();
		}
	}


	public Iterator<K> iterator() {
		// TODO Auto-generated method stub
		return (Iterator<K>) primero;
	}



}