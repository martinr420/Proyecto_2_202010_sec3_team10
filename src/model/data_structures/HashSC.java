package model.data_structures;

public class HashSC<Key ,Value> {

	private static final int INIT_CAPACITY = 4;

	private int n;                                // number of key-value pairs
	private int m;                                // hash table size
	private SequentialSearchST<Key, Value>[] st;  // array of linked-list symbol tables
	private int cantidadResize;
	private Value primero;
	private Value ultimo;
	private int tamanoInicial;
	private int tamanoFinal;

	/**
	 * Initializes an empty symbol table.
	 */
	

	/**
	 * Initializes an empty symbol table with {@code m} chains.
	 * @param m the initial number of chains
	 */
	public HashSC(int m) {
		this.m = m;
		st = (SequentialSearchST<Key, Value>[]) new SequentialSearchST[m];
		for (int i = 0; i < m; i++)
			st[i] = new SequentialSearchST<Key, Value>();
		cantidadResize = 0;
		primero = null;
		ultimo = null;
		tamanoInicial = m;
		tamanoFinal = m;
	} 

	// resize the hash table to have the given number of chains,
	// rehashing all of the keys
	private void resize() 
	{
		int temp1 = m;
		m*=2;
		while(!esPrimo(m))
		{
			m++;
		}
		int chains = m;
		HashSC<Key, Value> temp = new HashSC<Key, Value>(chains);
		for (int i = 0; i < temp1; i++) {
			for (Key key : st[i].keys()) {
				temp.put(key, st[i].get(key));
			}
		}
		this.m  = temp.m;
		this.n  = temp.n;
		this.st = temp.st;
		cantidadResize++;
		tamanoFinal = m;
	}

	// hash value between 0 and m-1
	private int hash(Key key) {
		return (key.hashCode() & 0x7fffffff) % m;
	} 

	/**
	 * Returns the number of key-value pairs in this symbol table.
	 *
	 * @return the number of key-value pairs in this symbol table
	 */
	public int size() {
		return n;
	} 

	/**
	 * Returns true if this symbol table is empty.
	 *
	 * @return {@code true} if this symbol table is empty;
	 *         {@code false} otherwise
	 */
	public boolean isEmpty() {
		return size() == 0;
	}

	/**
	 * Returns true if this symbol table contains the specified key.
	 *
	 * @param  key the key
	 * @return {@code true} if this symbol table contains {@code key};
	 *         {@code false} otherwise
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public boolean contains(Key key) {
		if (key == null) throw new IllegalArgumentException("argument to contains() is null");
		return get(key) != null;
	} 

	/**
	 * Returns the value associated with the specified key in this symbol table.
	 *
	 * @param  key the key
	 * @return the value associated with {@code key} in the symbol table;
	 *         {@code null} if no such value
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public Value get(Key key) {
		if (key == null) throw new IllegalArgumentException("argument to get() is null");
		
		int i = hash(key);
		return st[i].get(key);
	} 

	/**
	 * Inserts the specified key-value pair into the symbol table, overwriting the old 
	 * value with the new value if the symbol table already contains the specified key.
	 * Deletes the specified key (and its associated value) from this symbol table
	 * if the specified value is {@code null}.
	 *
	 * @param  key the key
	 * @param  val the value
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public void put(Key key, Value val) {
		if (key == null) throw new IllegalArgumentException("first argument to put() is null");
		if (val == null)
		{
			delete(key);
			return;
		}
		// double table size if average length of list >= 10
		if ((double)n/(double)m >= 0.5)
			{
			resize();
			}

		if(n == 0)
		{
			primero = val;
			ultimo = val;
		}
		else
		{
			ultimo = val;
		}
		int i = hash(key);
		if (!st[i].contains(key)) n++;
		st[i].put(key, val);
	} 

	/**
	 * Removes the specified key and its associated value from this symbol table     
	 * (if the key is in this symbol table).    
	 *
	 * @param  key the key
	 * @throws IllegalArgumentException if {@code key} is {@code null}
	 */
	public Void delete(Key key)
	{

		if (key == null)
		{
			throw new IllegalArgumentException("argument to delete() is null");
		}

		int i = hash(key);
		if (st[i].contains(key)) n--;
		st[i].delete(key);
		
		
		return null;

		

	} 

	// return keys in symbol table as an Iterable
	public Iterable<Key> keys() {
		LinkedQueue<Key> queue = new LinkedQueue<Key>();
		for (int i = 0; i < m; i++) {
			for (Key key : st[i].keys())
				queue.enqueue(key);
		}
		return queue;
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
	
	public int cantidadResize()
	{
		return cantidadResize;
	}
	public double factorCarga()
	{
		return (double)n / (double)m;
	}
	public Value primero()
	{
		return primero;
	}
	public Value ultimo()
	{
		return ultimo;
	}
	public int tamanoInicial()
	{
		return tamanoInicial;
	}
	public int tamanoFinal()
	{
		return tamanoFinal;
	}
}



