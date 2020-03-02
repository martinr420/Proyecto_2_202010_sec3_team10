package model.data_structures;

import java.util.ArrayList;
import java.util.Iterator;

public class ListaDoblementeEncadenada<K> implements IListaDoblementeEncadenada<K> {

	private Nodo<K> primero;

	private Nodo<K> ultimo;

	private int tamano;

	public ListaDoblementeEncadenada() 
	{

		primero = null;

		ultimo = null;

		tamano = 0;

	}




	@Override
	public Iterator iterator() {
		// TODO Auto-generated method stub
		IteratorLista iterador = new IteratorLista();
		return iterador ;
	}

	@Override
	public K darPrimero() {
		// TODO Auto-generated method stub
		return primero.darGenerico();
	}

	@Override
	public K darUltimo() {
		// TODO Auto-generated method stub
		return ultimo.darGenerico();
	}

	@Override
	public int darTamano() {
		// TODO Auto-generated method stub
		return tamano;
	}

	@Override
	public K darNodoEnPos(int pPosicion) throws noExisteObjetoException {

		Nodo<K> actual = primero;

		int cont = 0;

		while(cont < pPosicion)
		{
			actual = actual.darSiguiente();

			cont++;
		}



		return actual.darGenerico();
	}

	@Override
	public int darPosicionNodo(K pNodo) throws noExisteObjetoException {

		Nodo<K> actual = primero;

		int cont = 0;

		while(actual.darGenerico() != pNodo)
		{
			actual.darSiguiente();
			cont++;
		}


		// TODO Auto-generated method stub
		return cont;

	}

	@Override
	public K EliminarUltimo() throws noExisteObjetoException
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
			return aEliminar.darGenerico();
		}

	}

	@Override
	public K EliminarPrimero() throws noExisteObjetoException 
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
				primero = null;
				ultimo = null;
			}

			tamano--;

			return aEliminar.darGenerico();
		}
	}

	@Override
	public K EliminarNodoPos(int pPosicion) throws FueraDeAlcanceException, noExisteObjetoException {


		if(pPosicion >= tamano || pPosicion < 0)
		{
			throw new FueraDeAlcanceException(pPosicion, tamano);
		}
		else
		{
			if(pPosicion == 0)
			{

				return EliminarPrimero();

			}
			else if(pPosicion == tamano - 1)
			{
				return EliminarUltimo();


			}
			else
			{
				int cont = 0;
				Nodo<K> aEliminar = primero;

				while(cont < pPosicion)
				{
					aEliminar = aEliminar.darSiguiente();
					cont++;
				}

				aEliminar.darAnterior().cambiarSiguiente(aEliminar.darSiguiente());
				aEliminar.darSiguiente().cambiarAnterior(aEliminar.darAnterior());
				tamano--;

				return aEliminar.darGenerico();
			}

		}


	}

	@Override
	public K EliminarNodoObj(K pNodo) throws noExisteObjetoException {
		Nodo<K> aEliminar = primero;



		while(aEliminar.darGenerico() != pNodo )
		{
			if(aEliminar.darSiguiente()== null)
			{
				throw new noExisteObjetoException();
			}
			else
			{
				aEliminar = aEliminar.darSiguiente();
			}

		}

		aEliminar.darAnterior().cambiarSiguiente(aEliminar.darSiguiente());
		aEliminar.darSiguiente().cambiarAnterior(aEliminar.darAnterior());
		tamano--;
		return aEliminar.darGenerico();
	}

	@Override
	public K agregarNodoInicio(K pNodo) {

		Nodo<K> elNodo = new Nodo<K>(pNodo);
		if(primero == null)
		{
			primero = elNodo;
			ultimo = elNodo;
		}
		else
		{
			elNodo.cambiarSiguiente(primero);
			primero.cambiarAnterior(elNodo);

			primero = elNodo;
		}
		tamano++;

		return pNodo;
	}

	@Override
	public K agregarNodoAlFinal(K pNodo) {

		Nodo<K> elNodo = new Nodo<K>(pNodo);
		if(primero == null)
		{
			primero = elNodo;
			ultimo = elNodo;

		}
		else
		{
			elNodo.cambiarAnterior(ultimo);
			ultimo.cambiarSiguiente(elNodo);
			ultimo = elNodo;
		}

		tamano++;
		return pNodo;
	}

	@Override
	public K agregarNodoEnPosicion(int pPosicion, K pNodo) throws FueraDeAlcanceException {
		Nodo<K> elNodo = new Nodo<K>(pNodo);
		if(pPosicion >= tamano)
		{

			throw new FueraDeAlcanceException(pPosicion, pPosicion);

			// TODO Auto-generated method stub
		}
		else
		{
			Nodo<K> actual = primero;

			int cont = 0;
			if(pPosicion == 0)
			{
				agregarNodoInicio(pNodo);
			}

			else if(pPosicion == tamano-1)
			{
				agregarNodoAlFinal(pNodo);
			}

			else
			{
				while(cont < pPosicion-1)
				{

					actual = actual.darSiguiente();


				}



				elNodo.cambiarSiguiente(actual.darSiguiente());
				elNodo.cambiarAnterior(actual);
				actual.darSiguiente().cambiarAnterior(elNodo);
				actual.cambiarSiguiente(elNodo);
				tamano++;





			}


		}
		return pNodo;
	}

	@Override
	public void cambiarNodoEnPos(int pPosicion, K pNodo)
	{
		Nodo<K> actual = primero;

		int cont = 0;

		while(cont < pPosicion)
		{
			actual = actual.darSiguiente();

			cont++;
		}
		Nodo<K> elNodo = new Nodo(pNodo);
		actual = elNodo;

		// TODO Auto-generated method stub



	}

	public class IteratorLista implements Iterator<K>
	{

		private Nodo<K> actual;
		private boolean itero;

		public IteratorLista() 
		{
			itero = false;
			actual = (Nodo<K>) primero;
			// TODO Auto-generated constructor stub
		}
		@Override
		public boolean hasNext() {
			//if(!itero)
			//{
			return actual != null;
			//}

			/*else
			{
				return actual.darSiguiente() != null;
			}*/

		}

		@Override
		public K next() {
			K elemento = actual.darGenerico();
			actual = actual.darSiguiente();
			return elemento;
			/*if(!itero)
			{
				itero = true; 

			}
			else
			{
				actual = actual.darSiguiente(); 
			}
			// TODO Auto-generated method stub
			return actual.darGenerico();
		}
		public void reiniciar()
		{
			itero = false;
			actual = (Nodo<K>) primero;

		}*/

		}

	}

}
