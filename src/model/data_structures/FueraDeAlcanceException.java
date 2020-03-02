package model.data_structures;

public class FueraDeAlcanceException extends Exception {
	
	public FueraDeAlcanceException(int pPosicion, int tamano )
	{
		super("fuera del alcanze el numero" + pPosicion +  "supera el tamaño: " + tamano);
	}

}
