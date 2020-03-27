package model.logic;

import model.data_structures.Queue;

public class Valor2B
{
	
	public Queue<Multa> multas;
	
    private String deteccion;
	
	private String vehiculo;
	
	private String servicio;
	
	private String localidad;
	
	public Valor2B()
	{
		multas = new Queue<Multa>();
	}
	
	
}
