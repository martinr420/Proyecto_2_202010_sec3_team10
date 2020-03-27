package model.logic;

public class LlaveMesDIa 
{
	private int mes;
	private char dia;
	
	public LlaveMesDIa(int pMes, char pDia)
	{
		mes = pMes;
		dia = pDia;
	}

	public int getMes() {
		return mes;
	}

	public void setMes(int mes) {
		this.mes = mes;
	}

	public char getDia() {
		return dia;
	}

	public void setDia(char dia) {
		this.dia = dia;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + dia;
		result = prime * result + mes;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		LlaveMesDIa other = (LlaveMesDIa) obj;
		if (dia != other.dia)
			return false;
		if (mes != other.mes)
			return false;
		return true;
	}
	
	
	
}
