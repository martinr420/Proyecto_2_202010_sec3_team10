package model.data_structures;

public interface IMaxColaCP<K extends Comparable<K>>
{
	
	public int size();// number of keys in the priority queue
	public void insert(K key);// insert a key into the priority queue
	public K max() throws noExisteObjetoException;// return the largest key
	K delMax() throws noExisteObjetoException;// return and remove the largest key
	public boolean isEmpty();// is the priority queue empty?
	

}
