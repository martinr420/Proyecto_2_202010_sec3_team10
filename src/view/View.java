package view;



public class View 
{
    // -------------------------------------------------------------
    // Constants
    // -------------------------------------------------------------

   

    // -------------------------------------------------------------
    // Displays
    // -------------------------------------------------------------

    /**
     * Displays the user menu.
     */
    public void displayMenu() {

        System.out.println(  " **==========================**");
        System.out.println(" ||      ==== MENU ====      ||");

        System.out.println( " ||" +  " 0. Cargar datos   "  + "       ||");
        System.out.println( " ||" +  " 1. Consultar primer comparendo con localidad"  + "        ||");
        System.out.println( " ||" + " 2. Consultar Comparendos de una fecha " + " ||");
        System.out.println("||" + "3. Comparar comparendos por fecha");
        System.out.println("||" + "4. Dar primer Comparendo Infraccion");
        System.out.println("|| 5. darComparendo por Infraccion");
        System.out.println("|| 6. Dar comparendos por tipo de servicio");
        System.out.println("|| 7. Dar comparendos por fecha y localidad");
        System.out.println("|| 10. dar ASCII localidades");
        System.out.println( " **==========================**\n");

        // display hint
        this.displayHint();
        System.out.print("Input -> \n\n" );
    }

    /**
     * Displays an error message to the user if the option selected is invalid.
     */
    public void badOption() {
        System.out.println("\u001B[31m########## \n?Invalid option !! \n########## \u001B[0m");
    }

    /**
     * Print a hint to the user to select an option.
     */
    public void displayHint() {
        System.out.println(
                 "Enter the number corresponding to the option, the press the Return "
                        + "key: (e.g ., 1,2..):\n" );
    }


    // -------------------------------------------------------------
    // Option 0
    // -------------------------------------------------------------

    /**
     * Print option 0 menu.
     */
    public void displayOp0Menu(String mensaje) {
        System.out.println( "====== Cargar datos ======");
        System.out.println(mensaje);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        

    }

    /**
     * Print option 0 data.
     */
    public void displayOp0Data(String pName) {
        System.out.println( "Name set to: " + pName);
        System.out.println("======================\n");

    }

    // -------------------------------------------------------------
    // Option 1
    // -------------------------------------------------------------

    /**
     * Print option 1 menu.
     */
    public void displayOp1Menu() {
        System.out.println( "====== Consultar primer comparendo localidad  ======");
        System.out.println("digite la localidad aqui: ");
        

    }

    /**
     * Print option 1 data.
     */
    public void displayOp1Data(String multa) {
        System.out.println("El comparendo es: " + multa );
        System.out.println( "======================\n");

    }

    // -------------------------------------------------------------
    // Option 2
    // -------------------------------------------------------------

    /**
     * Print option 2 menu.
     */
    public void displayOp2Menu() {
        System.out.println( "====== Dar comparendos por fecha  ======");
        System.out.println("Digite el a�o de la fecha");
       
    }
    public void displayOp21Menu()
    {
    	 System.out.println("Digite el mes ");
         
    }
    public void displayOp23menu()
    {
    	System.out.println("Digite el dia ");
    }
    
    public void preioneOk()
    {
    	System.out.println("digite la palabra ok");
    }

    /**
     * Print option 2 data.
     */
    public void displayOp2Data(String pFecha, long total) {

        System.out.println( "Fecha: "  + pFecha);
        
        System.out.println("Total: " + total);
       
        System.out.println("======================\n");

    }
    
    public void displayOp3Menu()
    {
    	System.out.println("===== Comparar Comparendos =====");
    	System.out.println("Digite el a�o de la fecha");
    }
    public void displayOp31Menu()
    {
    	System.out.println("digite el a�o de la segunda fecha");
    }
    public void displayOp4Menu()
    {
    	System.out.println("======= Dar Primer Comparendo con infraccio");
    	System.out.println("digite la infraccion");
    }
    
    public void displayOp4Data(String pComparendo)
    {
    	System.out.println("El comparendo es: " + pComparendo);
    }
    
    public void displayOp5Menu()
    {
    	System.out.println("============================== dar comparendos por infraccion");
    	System.out.println("Digite la infraccion");
    }
    public void  displayOp10Menu()
    {
    	System.out.println("================== ASCII=========================");
    }
    
    public void diplayOp6Menu()
    {
    	System.out.println("============================== dar comparendos por infraccion");
    }
}
