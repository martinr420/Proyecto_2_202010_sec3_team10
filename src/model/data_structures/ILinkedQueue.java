package model.data_structures;

public interface ILinkedQueue <K>
{
	public void enqueue (K item);
	public K dequeue() throws noExisteObjetoException;
	public boolean isEmpty();
	public int size();
	public K darPrimero() throws noExisteObjetoException;	
}
