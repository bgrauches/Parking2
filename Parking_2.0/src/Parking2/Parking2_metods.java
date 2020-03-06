package Parking2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public class Parking2_metods {
	
	static ArrayList<String> llista_matricules = new ArrayList<String>();
	static ArrayList<String> plases_noDiscp = new ArrayList<String>();
	static ArrayList<String> plases_discapacitats = new ArrayList<String>();
	
	static int p_NoDisc;
	static int p_Disc;
	static int plasesTotals  = p_NoDisc + p_Disc;
	
	public static TipusPlacesParking TipusPlacesParking;
	
	//Constructor on depres posa les places Parking2_metods aparcament=new Parking2_metods(PLACES_NDISC,PLACES_DISC);
	public Parking2_metods(int places_no_discapacitats, int places_discapacitats) {
		this.p_NoDisc = places_no_discapacitats;
		this.p_Disc = places_discapacitats;
	}
	
	public int getPlacesOcupades(TipusPlacesParking tipus) {
		TipusPlacesParking  = tipus;
		
		int places = 0;

	    switch(TipusPlacesParking) {
	    
	    case Discapacitat:
	    	places =  plases_discapacitats.size();
	    	break;
	        
	    case No_Discapacitat:
	    	places = plases_noDiscp.size();
	    	break;
	    }
		return places;
	}
	
	public int getPlacesLliures(TipusPlacesParking tipus) {
		TipusPlacesParking = tipus;
		
		int places = 0;

	    switch(TipusPlacesParking) {
	    
	    case Discapacitat:
	    	places = p_Disc - plases_discapacitats.size();
	    	break;
    
	    case No_Discapacitat:
	    	places = p_NoDisc - plases_noDiscp.size();
	    	break;
	    }
	    return places;
	}
	
	public void llegirMatricules(String path) throws Exception {
		try {
			BufferedReader bf = new BufferedReader(new FileReader(path));	
			String linia;
			while ((linia=bf.readLine()) != null) {
				if(comporovarMatricula(linia)==false) {
					System.out.println("ALERTA =====> Matrícula " + linia + " incorrecte.");
				}
				llista_matricules.add(linia);
			}
			bf.close();
		} catch (Exception e) {
			throw new Exception("ALERTA =====> Fitxer incorrecte o inexistent.");
		}	
	}
	
	public int entraCotxe(String matricula) throws Exception {
		int a = 0;

		boolean matriculaOK = comporovarMatricula(matricula);	
		
		try {
			if(llista_matricules.contains(matricula) == false && matriculaOK == true) {
				enterGarrulo();
				llista_matricules.add(matricula);
				comprovarPercent();
				this.TipusPlacesParking = TipusPlacesParking.No_Discapacitat;
			}
			else {
				throw new Exception();
			}
		}catch(Exception e) {
			System.out.println("El cotxe ja està al parking, no pot entrar.");
		}
		
		return a;
		
	}
	
	public int entraCotxeDiscapacitat(String matricula) throws Exception {
		int b = 0;

		boolean matriculaOK = comporovarMatricula(matricula);
		
			if(llista_matricules.contains(matricula) == false && matriculaOK == true) {
				llista_matricules.add(matricula);
				comprovarPercent();
				this.TipusPlacesParking = TipusPlacesParking.Discapacitat;
			}
			else {
				throw new Exception("El cotxe ja està al parking, no pot entrar.");
			}
			
		return 0;
	}
	
	public void surtCotxe(String matricula) throws Exception {
		boolean matriculaOK = comporovarMatricula(matricula);
		
		if(matriculaOK == true) {
			if(llista_matricules.contains(matricula)) {
				llista_matricules.remove(llista_matricules.indexOf(matricula));
				plases_noDiscp.remove(plases_noDiscp.indexOf(matricula)); //amb llevam la matrícula de la seva posició a l'array
				
			}else {
				throw new Exception("El cotxe no és al parking.");
			}
		}
	}
	
	public void surtCotxeDiscapacitats(String matricula) throws Exception {
		boolean matriculaOK = comporovarMatricula(matricula);
		
		if(matriculaOK == true) {
			if(llista_matricules.contains(matricula)) {
				llista_matricules.remove(llista_matricules.indexOf(matricula));
				//plases_discapacitats.remove(plases_discapacitats.indexOf(matricula)); //en llevam la matrícula de la seva posició a l'array
			}	else {
				throw new Exception("El cotxe no és al parking.");
			}
		}
	}
	
	public void guardarMatricules(String path) throws Exception {
		try {  
		    BufferedWriter bw = new BufferedWriter(new FileWriter(path)); 
			
			for (int i = 0; i < llista_matricules.size() ; i++) {
				bw.write(llista_matricules.get(i) + "\n");
			}
			bw.close();
		} catch (IOException f) {
			throw new Exception("No s'ha pogut escriure al fitxer especificat.");
		}
	}
	
	
	
	//metode per comprovar si matricula donada es vàlida corresponent al patró [0-9]{4}[A-Z]{3}.
	private static boolean comporovarMatricula(String matricula) throws Exception {
		boolean matriculaOK = matricula.matches("[0-9]{4}[A-Z]{3}");
			if(matriculaOK == false) {
				throw new Exception("ALERTA =====> Matrícula incorrecte.");
				}
			return matriculaOK;
		}
	//metode per ficar de manera random un cotxe normal a plaça de discapacitat
	private static void enterGarrulo() throws Exception {
		Random r = new Random();
		int numR = r.nextInt(6)+1;

		if(numR == 1) {
			throw new Exception("ALERTA =====> Garrulo detected!!!");
		}	
	}
	
	private static void comprovarPercent() throws Exception {
		int num_NoDisc = p_NoDisc ;
		int num_Disc = p_Disc ;
		int placesOcupNoDisc = (int) (plases_noDiscp.size()*0.85);
		int placesOcupDisc = (int) (plases_discapacitats.size()*0.85);
		
		if(TipusPlacesParking == TipusPlacesParking.No_Discapacitat) {
			if(placesOcupNoDisc >= num_NoDisc) {
				throw new Exception("ALERTA =====> Ocupació de places per no discapacitats supera el 85%.");
			}
		}
		else {
			if(placesOcupDisc >= num_Disc) {
				throw new Exception("ALERTA =====> Ocupació de places per discapacitats supera el 85%.");
			}
		}
	}
	
}
