package model.data_structures;

import java.util.Iterator;

public class HashLP<K, V> implements IHashTable<K, V>
{

	private int n; // numero de elementos en el arreglo
	private int m; //tamaño inicial del arreglo
	private K[] keys;
	private V[] vals;
	private V primero;
	private V ultimo;
	private int tamanoInicial;
	private int tamanoFinal;
	int cantidadResize;

	public HashLP(int pM) 
	{
		m = pM;
		keys = (K[]) new Comparable[pM];
		vals = (V[]) new Object[pM];
		n = 0;
		primero = null;
		ultimo = null;
		tamanoInicial = m;
		tamanoFinal = m; 
	}
	@Override
	public void put(K key, V val) 
	{
		int posicion = hash(key);
		double factorCarga = (double) n / (double) m;
		if(factorCarga >= 0.75)
		{
			resize();
		}
		if(keys[posicion] != null)
		{
			while(true)
			{
				if(keys[++posicion] == null)
				{
					break;
				}
			}
		}
		keys[posicion] = key;
		vals[posicion] = val;
		if(n == 0)
		{
			primero = val;
			ultimo = val;
		}
		else
		{
			ultimo = val;
		}
		n++;
	}

	@Override
	public V get(K key) {
		int posicion = hash(key);
		if(keys[posicion] != key)
		{
			boolean encontro = false;
			while(!encontro)
			{
				posicion++;
				if(keys[posicion] == key)
				{
					encontro = true;
				}
				if(keys[posicion] == null)
				{
					break;
				}
			}
		}

		return vals[posicion];
	}

	@Override
	public V delete(K key) 
	{

		int i = hash(key);
		while (!key.equals(keys[i]))
		{
			i = (i + 1) % m;
		}
		keys[i] = null;
		V borrar = vals[i];
		vals[i] = null;
		i = (i + 1) % m;
		while (keys[i] != null) 
		{
			// delete keys[i] an vals[i] and reinsert
			K keyToRehash = keys[i];
			V valToRehash = vals[i];
			keys[i] = null;
			vals[i] = null;
			n--;
			put(keyToRehash, valToRehash);
			i = (i + 1) % m;
		}
		n--;
		return  borrar;
	}



	@Override
	public Iterable<K> keys() 
	{
		LinkedQueue<K> queue = new LinkedQueue<K>();
		for (int i = 0; i < m; i++)
		{
			if (keys[i] != null) 
			{
				queue.enqueue(keys[i]);
			}
		}
		return queue;
	}

	private int hash(K key) 
	{
		return (key.hashCode() & 0x7fffffff) % m;
	}

	private void resize()
	{
		m*=2; //tamano del nuevo arreglo

		while(!esPrimo(m))
		{
			m++; //hasta que sea primo se va a sumar m
		}
		K[] tempKeys = keys; // pasa el arreglo original a un arreglo temporal
		V[] tempVals = vals; // pasa el arreglo original a un arreglo temporal

		keys = (K[]) new Comparable[m]; // crea un nuevo arreglo con el nuevo tamaño
		vals = (V[]) new Object[m]; 

		for(int i = 0; i < tempKeys.length; i++) //va a organizar los nuevos objetos
		{
			if(tempKeys[i] != null)
			{
				K kInsert = tempKeys[i];
				V vInsert = tempVals[i];
				int posicion = hash(kInsert);
				if(keys[posicion] != null)
				{
					while(true)
					{
						if(keys[++posicion] == null)
						{
							break;
						}
					}
				}
				keys[posicion] = kInsert;
				vals[posicion] = vInsert;
			}
		}
		cantidadResize++;
		tamanoFinal = m;
	}

	private boolean esPrimo(int num)
	{
		boolean esPrimo = true;
		int otroNum = 2;
		if(num % 2 == 0 || num < 2 && num >= 0) // verifica si el numero es par
		{
			esPrimo = false;
		}
		while(otroNum <= Math.sqrt(num) && num > 2 && esPrimo) 
		{
			if(num % otroNum == 0  )
			{
				esPrimo = false; 
				break;
			}
			otroNum++;
		}
		return esPrimo;
	}
	private boolean contains(K key) {
		if (key == null) throw new IllegalArgumentException("argument to contains() is null");
		return get(key) != null;
	}

	public int size()
	{
		return n;
	}
	public int cantidadResize()
	{
		return cantidadResize;
	}
	
	public double factorCarga()
	{
		return (double)n / (double) m;
	}
	public V primero()
	{
		return primero;
	}
	public V  ultimo()
	{
		return ultimo;
	}
	public int tamanoFinal()
	{
		return tamanoFinal;
	}
	public int tamanoInicial()
	{
		return tamanoInicial;
	}

}
