package test.data_structures;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import junit.framework.TestCase;
import model.data_structures.FueraDeAlcanceException;
import model.data_structures.ListaEncadenada;
import model.data_structures.Nodo;
import model.data_structures.noExisteObjetoException;
import model.logic.Multa;

public class TestListaEnlazada<K> extends TestCase
{

	



	
	     private ListaEncadenada<K> listaEncadenada;
	     
	     @Before
	     private void setUp1()
	     {
	    	 listaEncadenada = new ListaEncadenada<K>();
	    	 
	    	 listaEncadenada.agregarNodoAlFinal(new Nodo(2));
	    	 listaEncadenada.agregarNodoAlFinal(new Nodo(5));
	    	 listaEncadenada.agregarNodoAlFinal(new Nodo(8));
	    	 listaEncadenada.agregarNodoAlFinal(new Nodo(10));
	    	 listaEncadenada.agregarNodoAlFinal(new Nodo(5));
	    	 listaEncadenada.agregarNodoAlFinal(new Nodo(34));
	    	 listaEncadenada.agregarNodoAlFinal(new Nodo(6));
	     }
	     
	     private void setUp2 ()
	     {
	    	 listaEncadenada = new ListaEncadenada<K>();
	    	 
	    	 listaEncadenada.agregarNodoAlFinal(new Nodo(0));
	    	 listaEncadenada.agregarNodoAlFinal(new Nodo(1));
	    			 
	     }
	     
	     @Test
	     public void testDarTamano()
	     {
	    	 setUp1();
	    	 int numero = listaEncadenada.darTamano();
	    	 assertEquals (7,numero);
	    	 
	    	 setUp2();
	    	 int numero2 = listaEncadenada.darTamano();
	    	 assertEquals (2,numero2);
	    	 
	     }
	     
	     @Test
	     public void testDarUltimo()
	     {
	    	 setUp1();
	    	 Nodo<K> nodo = listaEncadenada.darUltimo();
	    	 assertEquals (6,nodo.darGenerico());
	    	 
	    	 setUp2();
	    	 Nodo<K> nodo2 = listaEncadenada.darUltimo();
	    	 assertEquals (1, nodo2.darGenerico());
	    	 
	     }
	     
	     @Test
	     public void testDarNodoEnPos() throws noExisteObjetoException
	     {
	    	 setUp1();
	    	 Nodo<Multa> nodo = listaEncadenada.darNodoEnPos(5);
	    	 assertEquals (34, nodo.darGenerico());
	    	 
	    	 setUp2();
	    	 Nodo<K> nodo2 = listaEncadenada.darNodoEnPos(1);
	    	 assertEquals (1, nodo2.darGenerico());
	     }
	     
	     @Test
	     public void TestEliminarUltimo() throws noExisteObjetoException
	     {
	    	 setUp1();
	    	 Nodo <K> nodo = listaEncadenada.EliminarUltimo();
	    	 assertEquals (6, nodo.darGenerico());
	    	 
	    	 setUp2();
	         Nodo<K> nodo2 = listaEncadenada.EliminarUltimo();
	         assertEquals (1, nodo2.darGenerico());
	         
	         
	     }
	     
	     @Test
	     
	     public void testEliminarPrimero () throws noExisteObjetoException
	     {
	    	 setUp1();
	    	 Nodo <K> nodo = listaEncadenada.EliminarPrimero();
	    	 assertEquals (2, nodo.darGenerico());
	    	 
	    	 setUp2();
	    	 Nodo <K> nodo2 = listaEncadenada.EliminarPrimero();
	    	 assertEquals (0, nodo2.darGenerico());
	    	 
	     }
	     
	     @Test
	     
	     public void testEliminarNodoPos() throws FueraDeAlcanceException, noExisteObjetoException
	     {
	    	 setUp1();
	    	 Nodo<K> nodo = listaEncadenada.EliminarNodoPos(3);
	    	 assertEquals(10, nodo.darGenerico());
	    	 
	    	 setUp2();
	    	 Nodo <K> nodo2 = listaEncadenada.EliminarNodoPos(0);
	    	 assertEquals (0, nodo2.darGenerico());
	    	 
	     }
	     
	     @Test
	     
	     public void testEliminarNodoObj () throws noExisteObjetoException
	     {
	    	 setUp1();
	    	 Nodo<K> nodo = listaEncadenada.EliminarNodoObj(new Nodo (6));
	    	 assertEquals(6, nodo.darGenerico());
	    	 
	    	 setUp2();
	    	 Nodo<K> nodo2 = listaEncadenada.EliminarNodoObj(new Nodo (1));
	    	 assertEquals(1, nodo2.darGenerico());
	     }
	     
	     @Test
	     
	     public void testAgregarNodoInicio ()
	     {
	    	 setUp1();
	    	 Nodo<K> nodo = listaEncadenada.agregarNodoInicio(new Nodo (48));
	    	 assertEquals( 48, nodo.darGenerico());
	    	 
	    	 setUp2();
	    	 Nodo<K> nodo2 = listaEncadenada.agregarNodoInicio(new Nodo (32));
	    	 assertEquals(32, nodo2.darGenerico());
	    	    	 
	    			 
	     }
	     
	     @Test 
	     
	     public void TestAgregarNodoAlFinal()
	     {
	    	 setUp1();
	    	 Nodo <K> nodo = listaEncadenada.agregarNodoAlFinal(new Nodo (12));
	    	 assertEquals(12, nodo.darGenerico());
	    	 
	    	 setUp2();
	    	 Nodo<K> nodo2 = listaEncadenada.agregarNodoAlFinal(new Nodo (2));
	    	 assertEquals(2, nodo2.darGenerico());
	    	 
	    	 
	     }
	     
	     @Test
	     
	     public void testAgregarNodoEnPosicion() throws FueraDeAlcanceException
	     {
	    	 setUp1();
	    	 Nodo<K> nodo = listaEncadenada.agregarNodoEnPosicion(2, new Nodo (4));
	    	 assertEquals(4, nodo.darGenerico());
	    	 
	    	 setUp2();
	    	 Nodo <K> nodo2 = listaEncadenada.agregarNodoEnPosicion(0, new Nodo (5));
	    	 assertEquals(5, nodo2. darGenerico());
	    	 
	    	 
	     }
	     
	     @Test
	     
	     public void testDarPrimero ()
	     {
	    	 setUp1();
	    	 Nodo <K> nodo = listaEncadenada.darPrimero();
	    	 assertEquals(2, nodo.darGenerico());
	    	 
	    	 setUp2();
	    	 Nodo <K> nodo2 = listaEncadenada.darPrimero();
	    	 assertEquals(0, nodo.darGenerico());
	     }
}
