package model.logic;

public class CercanoALaEstacion implements Comparable<CercanoALaEstacion>
{
	private Multa multa;

	public CercanoALaEstacion(Multa multa) {
		super();
		this.multa = multa;
	}

	@Override
	public int compareTo(CercanoALaEstacion o)
	{
		int comp = 0;
		
		return comp;
	}

	public Multa getMulta() {
		return multa;
	}

	public void setMulta(Multa multa) {
		this.multa = multa;
	}
	
	

}
