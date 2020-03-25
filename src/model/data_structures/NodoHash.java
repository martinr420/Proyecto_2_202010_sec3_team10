package model.data_structures;

import java.util.Iterator;

public class NodoHash<K extends Comparable<K>, V > implements Iterator<NodoHash<K,V>>
{

	private K key;
	private V val;
	private NodoHash<K, V> siguiente;
	
	public NodoHash(K pKey, V pVal)
	{	
		siguiente = null;		
		key = pKey;
		val = pVal;
	}
	
	
	public NodoHash<K,V> darSiguiente()
	{
		return  siguiente;
	}
	
	public void cambiarSiguiente(NodoHash<K,V> pSig)
	{
		siguiente = pSig;
	}
	
	public void desconectarSiguiente()
	{
		siguiente = null;
	}
	
	public K getKey()
	{
		return key;
	}
	
	public V getVal()
	{
		return val;
	}
	public void setVal(V pVal)
	{
		val = pVal;
	}

	@Override
	public boolean hasNext() 
	{
		return this.darSiguiente() != null;
	}


	@Override
	public NodoHash<K,V> next() 
	{
		return siguiente;
	}


	public LinkedQueue<NodoHash<K,V>> keys() 
	{
		LinkedQueue<NodoHash<K, V>> q = new LinkedQueue<NodoHash<K,V>>();
		
		NodoHash<K,V>actual = this;// TODO Auto-generated method stub
		
		while(actual != null)
		{
			q.enqueue(actual);
			actual = actual.darSiguiente();
		}
		return q;
	}	

}
