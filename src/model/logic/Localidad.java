package model.logic;

public class Localidad {
	
	private String nombre;
	private int cantidadComparendos;
	
	public Localidad(String pNombre )
	{
		nombre = pNombre;
		cantidadComparendos = 1;
				
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public int getCantidadComparendos() {
		return cantidadComparendos;
	}

	public void setCantidadComparendos(int cantidadComparendos) {
		this.cantidadComparendos = cantidadComparendos;
	}
	
	public void sumar()
	{
		cantidadComparendos++;
	}

}
