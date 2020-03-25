package model.logic;

import java.util.Comparator;

public class Gravedad implements Comparable<Gravedad>
{
	//CONSTANTES
	private final static int PUBLICO = 2;
	private final static int OFICIAL = 1;
	private final static int PARTICULAR = 0;
	
	//ATRIBUTOS
	private int servicio; // primer criterio de gravedad
	private String infraccion;
	
	public Gravedad(String pServicio, String pInfraccion )
	{
		if(pServicio.equals("Particular"))
		{
			servicio = PARTICULAR;
		}
		else if(pServicio.equals("Publíco"))
		{
			servicio = PUBLICO;
		}
		else
		{
			servicio = OFICIAL;
		}
		infraccion = pInfraccion;
	}
	
	public int getServicio()
	{
		return servicio;
	}
	
	public String getInfraccion()
	{
		return infraccion;
	}

	@Override
	public int compareTo(Gravedad o) 
	{
		int comparacion = 0;
		if(servicio > o.getServicio())
		{
			comparacion = 1;
		}
		else if( servicio < o.getServicio())
		{
			comparacion = -1;
		}
		else
		{
			if(infraccion.compareTo(o.getInfraccion()) > 0)
			{
				comparacion = 1;
			}
			else if(infraccion.compareTo(o.getInfraccion()) < 0)
			{
				comparacion = -1;
			}
		}
		return comparacion;
	}



	
	
	

}
