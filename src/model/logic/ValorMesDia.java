package model.logic;

import model.data_structures.Queue;

public class ValorMesDia 
{
	public Queue<Multa> multas;
	
	private int mes;
	
	private char dia;
	
	public ValorMesDia(int pMes, char pDia)
	{
		multas = new Queue<Multa>();
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
	
	

}
