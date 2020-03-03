package model.logic;

public class CodigoInfraccion implements Comparable<CodigoInfraccion> {
	private String codigo;
	
	private int numeroComparendos1;
	
	private int numeroComparendos2;
	
	public CodigoInfraccion(String codigo) {
		numeroComparendos1 = 0;
		numeroComparendos2 = 0;
		this.codigo = codigo;
	}
	
	public String darCodigoInfraccion() {
		return codigo;
	}
	
	public void incrementarNumeroComparendos() {
		numeroComparendos1++;
	}
	
	public void incrementarNumeroComparendos2() {
		numeroComparendos2++;
	}
	
	public int darNumeroComparendos() {
		return numeroComparendos1;
	}
	
	public int darNumeroComparendos2() {
		return numeroComparendos1;
	}
	
	@Override
	public boolean equals(Object obj) {
		// TODO Auto-generated method stub
		return obj instanceof CodigoInfraccion && ((CodigoInfraccion) obj).codigo.equals(codigo);
	}

	@Override
	public int compareTo(CodigoInfraccion o) {
		// TODO Auto-generated method stub
		return codigo.compareTo(o.codigo);
	}
}
