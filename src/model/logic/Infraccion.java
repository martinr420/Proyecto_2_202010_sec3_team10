package model.logic;

import model.data_structures.IListaDoblementeEncadenada;
import model.data_structures.ListaDoblementeEncadenada;

public class Infraccion implements Comparable<Infraccion> {
	
	private String infra;
	
	private int total;

	public Infraccion(String infra) {
		super();
		total = 1;
		
		
	}


	


	public String getInfra() {
		return infra;
	}

	public void setInfra(String infra) {
		this.infra = infra;
	}

		
	
	@Override
	public int compareTo(Infraccion pInfraccion) {
		int num;
		if(infra.compareTo(pInfraccion.getInfra()) > 0)
		{
			num = 1;
		}
		else if(infra.compareTo(pInfraccion.getInfra()) < 0)
		{
			num = -1;
		}
		else
		{
			num = 0;
		}
		
		// TODO Auto-generated method stub
		return num;
	}
	
	public int getTotal()
	{
		return total;
	}
	
	public void sumar()
	{
		total++;
	}
	
	
}
