package controller;

import java.util.InputMismatchException;
import java.util.Scanner;

import model.data_structures.ListaDoblementeEncadenada;
import model.data_structures.ListaDoblementeEncadenada.IteratorLista;
import model.data_structures.noExisteObjetoException;
import model.logic.Localidad;
import model.logic.Model;
import model.logic.Multa;
import view.View;

public class Controller {


	// -------------------------------------------------------------
	// Attributes
	// -------------------------------------------------------------

	/**
	 * A model.
	 */
	private Model modelo;

	/**
	 * A view.
	 */
	private View view;

	// -------------------------------------------------------------
	// Constructor
	// -------------------------------------------------------------

	/**
	 * Creates the project view and the project model
	 */
	public Controller() {
		modelo = new Model();
		view = new View();
	}

	// -------------------------------------------------------------
	// Methods
	// -------------------------------------------------------------

	/**
	 * Prints the user options and updates the view using the model.
	 *
	 * @throws InputMismatchException If the user inputs an incorrect number sequence.
	 * @throws noExisteObjetoException 
	 */
	public void run() throws InputMismatchException, noExisteObjetoException {
		try {
			Scanner reader = new Scanner(System.in);
			boolean end = false;

			while (!end) {
				view.displayMenu();
				
				int option = reader.nextInt();
				switch (option) {

				case 0:
					
					
					modelo.darInfoCargaDatos();

					
					
					
					break;

				case 1:
					// Display option 1
					view.displayOp1Menu();
					String loc = reader.next();
					Multa laMulta = modelo.darComparendoLocalidad(loc);
					
					view.displayOp1Data(laMulta.toString());
					
					
					
					break;

				case 2:
					view.displayOp2Menu();
					String anio = reader.next();
					view.displayOp21Menu();
					String mes = reader.next();
					view.displayOp23menu();
					String dia = reader.next();
					String fecha = anio + "/"  + mes + "/" + dia;
					
					
					ListaDoblementeEncadenada<Multa> lista = modelo.darMultaPorFecha(fecha);
					long tamano = lista.darTamano();
					view.displayOp2Data(fecha, tamano);
					view.preioneOk();
					if(reader.next().compareToIgnoreCase("ok") == 0)
					{
						IteratorLista iter = (IteratorLista) lista.iterator();
						while(iter.hasNext())
						{
							Comparable multa = (Comparable) iter.next();
							System.out.println(multa.toString());
						}
						System.out.println("=================================================================================================");
						
						
					}
					System.out.println("no digito ok :'(");
					
					
					

					break;
					
				case 3:
					view.displayOp3Menu();
					String anio2 = reader.next();
					view.displayOp21Menu();
					String mes2 = reader.next();
					view.displayOp23menu();
					String dia2 = reader.next();
					String fecha2 = anio2 + "/"  + mes2 + "/" + dia2;
					
					view.displayOp31Menu();
					String anio3 = reader.next();
					view.displayOp21Menu();
					String mes3 = reader.next();
					view.displayOp23menu();
					String dia3 = reader.next();
					String fecha3 = anio3 + "/" + mes3 + "/" + dia3;
					
					
					
					
					modelo.darMultasComparacion(fecha2, fecha3);
							
				
					view.displayOp3Menu();
					
					break;
					
					
				case 4:
					view.displayOp4Menu();
					String pInfraccion =reader.next();
					Multa laMulta4 = modelo.ConsultarComparendoPorInfraccion(pInfraccion);
					
					
					view.displayOp4Data(laMulta4.toString());
					break;
					
				case 5:
					view.displayOp5Menu();
					String laInfra = reader.next();
					
					ListaDoblementeEncadenada<Multa> laLista = modelo.darComparendosPorInfraccion(laInfra);
					IteratorLista iter = (IteratorLista) laLista.iterator();
					
					System.out.println("El total de Comparendos es de :" + laLista.darTamano());
					
					System.out.println("Presione ok");
					
					if(reader.next().equals("ok"))
					{
						while(iter.hasNext())
						{
							Multa laMu = (Multa) iter.next();
							System.out.println(laMu.toString());
						}
					}
					
				
					break;
				case 10:
					view.displayOp10Menu();
					
					ListaDoblementeEncadenada<Localidad> localidades = modelo.ASCII();
					System.out.println(localidades.darTamano());
					IteratorLista iterLoc = (IteratorLista) localidades.iterator();
					
					while(iterLoc.hasNext())
					{
						Localidad loquis = (Localidad) iterLoc.next();
						int cantidad = loquis.getCantidadComparendos()/50;
						String asteriscos = "";
						if(cantidad == 0)
						{
							System.out.println(loquis.getNombre() + " | " + "sin comparendos");
						}
						for (int i = 0; i < cantidad; i++)
						{
							asteriscos += "*";
						}
						if((cantidad % 50) > 0)
						{
							asteriscos += "*";
						}
						
						System.out.println(loquis.getNombre() + " | " + asteriscos );
					}

					break;
					// Invalid option
				default:
					view.badOption();
					end = true;
					break;
				}
			}
		} 
		catch (InputMismatchException e)
		{
			run();
		}
	}

}
