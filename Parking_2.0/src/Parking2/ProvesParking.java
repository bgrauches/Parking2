package Parking2;

import java.util.ArrayList;
import java.util.Random;

public class ProvesParking {

	public static void main(String[] args) throws Exception {
		
		ArrayList<String> llista_matricules = new ArrayList<String>();
		llista_matricules.add("1234AAA");
		llista_matricules.add("1234AAB");
		llista_matricules.add("1234AAD");
		llista_matricules.add("1234AAC");
		
		Random r = new Random();
		int numR = r.nextInt(6)+1;

		if(numR == 1) {
			throw new Exception("ALERTA =====> Garrulo detected!!!");
		}
	}

}
