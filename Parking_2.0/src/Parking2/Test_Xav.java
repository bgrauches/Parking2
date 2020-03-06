package Parking2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test_Xav {

	public static void main(String[] args) throws Exception  {

		final int PLACES_NDISC=50;
		final int PLACES_DISC=3;
		final int MATRICULES_FITXER=16;
		double nota=0;
		String[] matricules={"3121GPS","4121GPS","5121GPS","6121GPS","7121GPS","8121GPS","9121GPS","4156BCF",
				"3122GPS","4122GPS","5122GPS","6122GPS","7122GPS","8122GPS","9122GPS","4152BCF"};
		
		// comprova constructor
		System.out.println("***********************************************************");
		System.out.println("***** Comprovam constructor i mètode getPlacesLliures *****");
		System.out.println("***********************************************************");
		Parking2_metods aparcament=new Parking2_metods(PLACES_NDISC,PLACES_DISC);
		
		System.out.println("Places No disc: "+aparcament.getPlacesLliures(TipusPlacesParking.No_Discapacitat));
		System.out.println("Places disc: "+aparcament.getPlacesLliures(TipusPlacesParking.Discapacitat));
		if ((aparcament.getPlacesLliures(TipusPlacesParking.No_Discapacitat)==PLACES_NDISC) &&
				(aparcament.getPlacesLliures(TipusPlacesParking.Discapacitat)==PLACES_DISC)) {
				System.out.println("CONSTRUCTOR CORRECTE +1");
				System.out.println("Mètode getPlacesLliures CORRECTE +1");
				nota++;
				nota++;
		}
		else 
			System.out.println("CONSTRUCTOR INCORRECTE");
		
		// Comprova entrar cotxe i places lliures (disc i no_disc)
		System.out.println();
		System.out.println("***********************************************************");
		System.out.println("***** Comprovam entrar cotxe i places lliures         *****");
		System.out.println("***********************************************************");
		System.out.println();
		TipusPlacesParking tipus=TipusPlacesParking.No_Discapacitat;
		boolean garrulo=false;
		int  intents=0;
		for (int i=0;i<2;i++) {
			// Comprova entrar cotxe i places lliures
			int plasa=0;
			int plasa2=0;
			try {
				if (tipus.equals(TipusPlacesParking.No_Discapacitat)) {
					plasa = aparcament.entraCotxe("1234ZZZ");			
				} else {
					plasa = aparcament.entraCotxeDiscapacitat("1234ZZZ");
				}
			} catch (Exception e1) {
				//System.out.println(e1.getMessage());
				garrulo=true;
				i--;
				if (tipus.equals(TipusPlacesParking.No_Discapacitat)) {
					aparcament.surtCotxeDiscapacitats("1234ZZZ");		
				} else {
					aparcament.surtCotxe("1234ZZZ");
				}
			}
			try {
				if (tipus.equals(TipusPlacesParking.No_Discapacitat)) {
					plasa2 = aparcament.entraCotxe("8888YYY");		
				} else {
					plasa2 = aparcament.entraCotxeDiscapacitat("8888YYY");
				}
			} catch (Exception e1) {
				//System.out.println(e1.getMessage());
				garrulo=true;
				i--;
				if (tipus.equals(TipusPlacesParking.No_Discapacitat)) {
					aparcament.surtCotxeDiscapacitats("8888YYY");		
				} else {
					aparcament.surtCotxe("8888YYY");
				}
			}
			
			//System.out.println("Cotxe aparcat "+tipus+" a plaça:"+plasa);
			//System.out.println("Cotxe aparcat "+tipus+" a plaça:"+plasa2);
			
			if ((plasa>=0) && (plasa2>=0) && (aparcament.getPlacesLliures(tipus)==PLACES_NDISC-2) && !(garrulo)) {
				System.out.println("Métode entrar cotxe i places "+tipus+" lliure CORRECTE +2");
				nota++;
				nota++;
			}
			else if (aparcament.getPlacesLliures(tipus)==PLACES_NDISC-2 && !(garrulo)) {
				System.out.println("Métode llegir matricules parcialment INCORRECTE +0.9");
				nota=nota+0.9;
				nota++;
			}
			
			// Comprova places ocupades 		


			if (aparcament.getPlacesOcupades(tipus)==2 && !(garrulo)) {
				System.out.println("Métode places ocupades "+tipus+" CORRECTE +1");
				nota++;
			}
			
			// Comprova surt cotxe
			if (!(garrulo)) {
				try {
					if (tipus.equals(TipusPlacesParking.No_Discapacitat )) {
						aparcament.surtCotxe("1234ZZZ");		
					} else {
						aparcament.surtCotxeDiscapacitats("1234ZZZ");
					}				
				} catch (Exception e1) {
		
					System.out.println(e1.getMessage());
				}
				try {
					if (tipus.equals(TipusPlacesParking.No_Discapacitat)) {
						aparcament.surtCotxe("8888YYY");
					} else {
						aparcament.surtCotxeDiscapacitats("8888YYY");
					}				
				} catch (Exception e1) {
		
					System.out.println(e1.getMessage());
				}
			}
			if (aparcament.getPlacesOcupades(tipus)==0 && !(garrulo)) {
				System.out.println("Métode surt cotxe "+tipus+" CORRECTE +1");
				nota++;
			}
			if (!(garrulo))
				tipus=TipusPlacesParking.Discapacitat;
			garrulo=false;
			intents++;
			if (intents>100) {
				System.out.println("Ha entrat en bucle de garrulos. Torna executar");
				System.exit(0);
			}
		}
		
		// Comprova sortir cotxe incorrecte
		System.out.println();
		System.out.println("***********************************************************");
		System.out.println("***** Comprovam sortir cotxe incorrecte               *****");
		System.out.println("***********************************************************");
		System.out.println();
		try {
			aparcament.surtCotxe("5555ABC");
		} catch (Exception e) {
			if (e.getMessage().equals("El cotxe no és al parking.")) {
				System.out.println("Métode cotxe no existeix CORRECTE +1");
				nota++;
			}
				
		}
		
		// Comprova matricula incorrecte/files/
		System.out.println();
		System.out.println("***********************************************************");
		System.out.println("***** Comprovam matricula incorrecte                  *****");
		System.out.println("***********************************************************");
		System.out.println();
		try {
			aparcament.entraCotxe("1ABC");
		} catch (Exception e) {
			System.out.println(e.getMessage());
			if (e.getMessage().equals("ALERTA =====> Matrícula incorrecte.")) {
				System.out.println("Métode matricula incorrecta CORRECTE +1");
				nota++;
			}
				
		}		
		// Comprova 85%
		System.out.println();
		System.out.println("***********************************************************");
		System.out.println("***** Comprovam 85% capacitat                         *****");
		System.out.println("***********************************************************");
		System.out.println();
		int nombre=0;
		List<String> llista_cotxes_entrats = new ArrayList<String>();
		while (((aparcament.getPlacesOcupades(TipusPlacesParking.Discapacitat)*100/PLACES_DISC)<85) && (nombre<100)) {
			System.out.println(aparcament.getPlacesOcupades(TipusPlacesParking.Discapacitat)*100/PLACES_DISC);
			try {
				aparcament.entraCotxeDiscapacitat(matricules[nombre]);
				llista_cotxes_entrats.add(matricules[nombre]);
			} catch (Exception e) {
				if (e.getMessage().equals("ALERTA =====> Ocupació de places per discapacitats supera el 85%.")) {
					System.out.println("Métode ocupació 85% CORRECTE +1");
					nota++;
				}
			}
			nombre++;
		}
		//Buidam parking pel següent test
		for (int iterador=0;iterador<=llista_cotxes_entrats.size();iterador++) {
			aparcament.surtCotxeDiscapacitats(matricules[iterador]);
		}
			
		// Comprova garrulo
		System.out.println();
		System.out.println("***********************************************************");
		System.out.println("***** Comprovam garrulo                               *****");
		System.out.println("***********************************************************");
		System.out.println();
		boolean condicio=true;
		int iteracio=0;
		while (condicio && iteracio<=100) {
			try {
				aparcament.entraCotxe("9944GPS");
				//System.out.println("ALERTA =====> Garrulo".substring(0, 21));
			} catch (Exception e) {
				if (e.getMessage().substring(0, 21).equals("ALERTA =====> Garrulo")) {
					condicio=false;
					System.out.println("Métode garrulo CORRECTE +1");
					aparcament.surtCotxeDiscapacitats("9944GPS");
					nota++;
				}
			}
			try {
				aparcament.surtCotxe("9944GPS");
			} catch (Exception e) {

				//e.printStackTrace();
			}
			//System.out.print(iteracio+" ");
			iteracio++;
		}
		System.out.println();
		
		// Comprova llegir matricules
		System.out.println();
		System.out.println("***********************************************************");
		System.out.println("***** Comprovam llegir matricules                     *****");
		System.out.println("***********************************************************");
		System.out.println();
		boolean acabat=false;
		System.out.println("Places ocupades no discapacitat: "+aparcament.getPlacesOcupades(TipusPlacesParking.No_Discapacitat));
		try {
			//System.out.println(ClasseTestParking1920.class.getClassLoader().getResource("").getPath());
			URL pathurl=Test_Xav.class.getResource("matricules"); 
			String path=pathurl.getPath();
			aparcament.llegirMatricules(path);
		} catch (Exception e1) {

			System.out.println(e1.getMessage());
			System.out.println(Test_Xav.class.getClassLoader().getResource("").getPath());
		}
		System.out.println("Places ocupades no discapacitat: "+aparcament.getPlacesOcupades(TipusPlacesParking.No_Discapacitat));
		if (aparcament.getPlacesOcupades(TipusPlacesParking.No_Discapacitat)==MATRICULES_FITXER) { 
				System.out.println("Métode llegir matricules CORRECTE +1");
				nota++;
		}
		else 
			System.out.println("Métode llegir matricules INCORRECTE");
		
		System.out.println("************");
		System.out.println("NOTA : "+nota);


	}

}
