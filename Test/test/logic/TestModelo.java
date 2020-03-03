package test.logic;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import model.data_structures.noExisteObjetoException;
import model.logic.Model;

public class TestModelo {
	
	private Model modelo;
	private static int CAPACIDAD=100;
	
	@Before
	public void setUp1() {
		modelo= new Model();
	}

	public void setUp2() throws noExisteObjetoException {
		for(int i =0; i< CAPACIDAD;i++){
			//modelo.cargarDatos();
		}
	}

	@Test
	public void testModelo() {
		assertTrue(modelo!=null);
		assertEquals(0, modelo.darTamano());  // Modelo con 0 elementos presentes.
	}

	@Test
	public void testDarTamano() {
		// TODO
	}

	@Test
	public void testAgregar() {
		// TODO Completar la prueba
	}

	@Test
	public void testBuscar() throws noExisteObjetoException {
		setUp2();
		// TODO Completar la prueba
	}

	@Test
	public void testEliminar() throws noExisteObjetoException {
		setUp2();
		// TODO Completar la prueba
		
	}

}
