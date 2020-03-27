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

        System.out.println( " ||" +  " 0. Cargar datos   "  + "||");
        System.out.println( " ||" +  " 1. Obtener M comparendos mayor gravedad"  + " ||");
        System.out.println( " ||" + " 2. Consultar Comparendos por mes y dia de la semana " + " ||");
        System.out.println("||" + "3. Buscar comparendos fecha hora localidad  ||");
        System.out.println("||" + "4. buscar los M comparendos mas cercanos a la estacion de policia ||");
        System.out.println("|| 5. Bucar los comparendos medio dete, clase vehiculo, tipo servi, y localidad  ||");
        System.out.println("|| 6. buscar comparendos latitud y tipo vehiculo  ||");
        System.out.println("|| 7. Visualizar datos ASCII  ||");
        System.out.println("|| 8. Costo tiempos de espera.  ||");
        System.out.println("|| 10. Costo tiempos de espera nuevo sistema  ||");
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
    public void displayOp0Menu(String mensaje)
    {
        System.out.println( "====== Cargar datos ======");
        System.out.println(mensaje);
        System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");
        

    }

    

    // -------------------------------------------------------------
    // Option 1
    // -------------------------------------------------------------

    /**
     * Print option 1 menu.
     */
    public void displayOp1Menu() {
        System.out.println( "====== Obtener Comparendos COn mayor gravedad  ======");
        System.out.println("digite el Numero de comparendos");
        

    }

    /**
     * Print option 1 data.
     */
    public void displayOp1Data(String comparendos) 
    {
        System.out.println("Los comparendos son" );
        System.out.println(comparendos);
        System.out.println( "======================\n");

    }

    // -------------------------------------------------------------
    // Option 2
    // -------------------------------------------------------------

    /**
     * Print option 2 menu.
     */
    public void displayOp2Menu() {
        System.out.println( "====== Buscar comparendos mes y dia  ======");
        System.out.println("ingrese el numero del mes");
       
    }
    public void displayOp21Menu()
    {
    	 System.out.println("Digite el dia de la semana");
         
    }
    
    
    /**
     * Print option 2 data.
     */
    public void displayOp2Data(String comparendos, long total) {

        System.out.println( "La cantidad de comparendos es: " + total);
        
        System.out.println(comparendos);
       
        System.out.println("======================\n");

    }
    
    public void displayOp3Menu()
    {
    	System.out.println("===== Buscar Comparendos fecha y hora =====");
    	System.out.println("Digite el limite inferior en formato (YYYY/MM/DD-HH:MM:ss)");
    }
    public void displayOp31Menu()
    {
    	System.out.println("digite el limite superior en el mismo formato (YYYY/MM/DD-HH:MM:ss)");
    }
    
    public void displayOp32Menu()
    {
    	System.out.println("digite la localidad");
    }
    public void displayOp3Data(String comparendos)
    {
    	System.out.println("Los comparendos son: ");
    	System.out.println(comparendos);
    }
    public void displayOp4Menu()
    {
    	System.out.println("======= dar m Comparendos mas cercanos a la policia ==========");
    	System.out.println("digite la cantidad");
    }
    
    public void displayOp4Data(String comparendos)
    {
    	System.out.println("Los comparendos son: ");
    	System.out.println(comparendos);
    }
    
    public void displayOp5Menu()
    {
    	System.out.println("========= dar comparendos por deteccion, clase vehiculo, tipo Servicio y localidad");
    	System.out.println("Digite el medio de deteccion");
    }
    public void displayOp51Menu()
    {
    	System.out.println("Digite la clase del vehiculo");
    }
    
    public void displayOp52Menu()
    {
    	System.out.println("Digite el tipo de servicio");
    }
    public void displayOp53Menu()
    {
    	System.out.println("Digite la localidad");
    }
    
    public void displayDataOp5(String dete, String clase, String servicio, String localidad, String comparendos)
    {
    	System.out.println("los comparendos con los datos: " + dete +" "+ clase +" "+ servicio +" "+localidad);
    	System.out.println(comparendos);
    }
    
    public void displayOpMenu6()
    {
    	System.out.println("==================== dar comparendos por latitud y tipo servicio ====================");
    	System.out.println("Digite la latitud minima");
    }
    public void displayOpMenu61()
    {
    	System.out.println("Digite la latitud maxima");
    }
    
    public void displayOpMenu62()
    {
    	System.out.println("Digite el tipo de servicio");
    }
    public void displayOpData6(double latMin, double latMax, String servi, String comparendos)
    {
    	System.out.println("los comparendos con las siguientes caracteristicas: " + latMin + " " + latMax + " " + servi + " son: " );
    	System.out.println(comparendos);
    }
    
    public void dispayOpMenu7()
    {
    	System.out.println("Tabla ASCII");
    	System.out.println("digite el intervalo de dias");
    }
    public void displayOp7Data(int dias, String tabla)
    {
    	System.out.println("La tabla con el intervalo de dias " + dias +" es:");
    	System.out.println(tabla);
    }
    
    public void displayOp8Menu(String tabla)
    {
    	System.out.println("============ costo tiempo ==============");
    	System.out.println(tabla);
    }
    
    public void displayOp9Menu(String tabla)
    {
    	System.out.println("============= Costo tiempos de espera ==================");
    	System.out.println(tabla);
    }
    public void  displayOp10Menu(String comenrtario)
    {
    	System.out.println("================== Comentarios sistema =========================");
    	System.out.println(comenrtario);
    }
    
   
}
