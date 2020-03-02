package model.data_structures;

public interface ILinkedStack <K> {

	
	public Nodo <K> pop () throws noExisteObjetoException;
	public boolean isEmpty ();
	public int size();
	public Nodo <K> darUltimo() throws noExisteObjetoException;
	public void push(Nodo<K> item);

	
}