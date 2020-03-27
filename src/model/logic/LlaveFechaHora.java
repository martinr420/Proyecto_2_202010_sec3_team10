package model.logic;

import java.util.Date;

public class LlaveFechaHora implements Comparable<LlaveFechaHora>
{
	private Date fecha;
	
	
	public LlaveFechaHora(Date pFecha)
	{
		fecha = pFecha;		
	}


	@Override
	public int compareTo(LlaveFechaHora o)
	{
		int num = 0;
		
		if(fecha.after(o.fecha)) num = 1;
		
		else if(fecha.before(o.fecha)) num = -1;
		
		return num;
	}


}
