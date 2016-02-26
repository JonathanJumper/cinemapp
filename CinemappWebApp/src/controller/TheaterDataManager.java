package controller;

import java.util.ArrayList;

import model.Theater;

public class TheaterDataManager {
	
	public ArrayList<Theater> theaters = new ArrayList<Theater>();
	
	public TheaterDataManager (){}
	
	private void fillTheatersBogota(){
		
		theaters.add(new Theater("Cine Tonalá", "668c2afdb977b652", "4.62244779", "-74.06571776", 
				"Carrera 6 No. 35 - 37, Barrio La Merced", "(1) 4798687", "bogota" ,"http://www.cinetonala.co/"));
		theaters.add(new Theater("Cine Colombia - Multiplex Embajador","8707be250d74bc06",
				"4.60927", "-74.06931", "Calle 24 No. 6-01", "(1) 4042463", "bogota" , "http://www.cinecolombia.com/cine-colombia-embajador"));
		theaters.add(new Theater("Cinépolis Calima", "761e10400fa080e3", 
				"4.618331", "-74.085912", "Avenida Calle 19 No. 28 - 80 - Local C-28", "(1) 7561429", "bogota", "http://www.cinepolis.com.co/_CARTELERA/cartelera.aspx?ic=87#Cine442"));
		theaters.add(new Theater("Royal Films - Multicine San Martín", "78ca5819d19904f9", 
				"4.618997", "-74.06792537", "Carrera 7a No. 32-16, Centro Comercial San Martín", "(1) 7456484", "bogota", "http://www.royal-films.com/cartelera_ciudad_sala.php?sala=Multicine%20San%20Martin&ciudad=Bogota"));
		theaters.add(new Theater("Cine Colombia - Multiplex Centro Mayor","5c3954b3d65bef6f", 
				"4.59288599", "-74.12429863", "Calle 38 A Sur - No. 34 D-50", "(1) 4042463" , "bogota", "http://www.cinecolombia.com/cine-colombia-centro-mayor"));
		theaters.add(new Theater("Cine Colombia - Multiplex Las Américas", "3189f25a3ae1378a", 
				"4.61914609", "-74.1368689", "Carrera 71D No. 4-40 Sur", "(1) 4042463", "bogota", "http://www.cinecolombia.com/cine-colombia-las-americas"));
		theaters.add(new Theater("Cine Colombia - Multiplex Gran Estación", "ace3c0f1ba6f3e43", 
				"4.64792", "-74.101945", "Centro Comercial Gran Estación, Avenida Calle 26 No. 62-47", "(1) 4042463", "bogota", "http://www.cinecolombia.com/cine-colombia-gran-estacion"));
		theaters.add(new Theater("Cinemateca Distrital", "5a4ec27d8bd4aaeb", 
				"4.60868", "-74.07067", "Carrera 7 No 22 - 79" ,"(1) 2810797", "bogota", "http://www.idartes.gov.co/index.php/escenarios/cinemateca-distrital"));
		theaters.add(new Theater("Procinal - Salitre Plaza", "da2a55cda39c9891",
				"4.65323917", "-74.10987573", "Local 307, Carrera 68B No. 40-39", "(1) 5921650", "bogota" ,"http://www.procinal.com.co/salas/salitre"));
		theaters.add(new Theater("Procinal - Plaza de las Américas", "674e270c1d9a32e2",
				"4.61879046", "-74.13459353", "Local 2508/2511, Transversal 71D No. 6-94 Sur", "(1) 5921650", "bogota", "http://www.procinal.com.co/salas/americas"));
		
		theaters.add(new Theater("Cine Colombia - Multiplex Galerías", "e791bef9eb20563d", 
				"4.64273058", "-74.07443884", "Calle 54 No. 25-81 Local 2168", "(1) 4042463" , "bogota", "http://www.cinecolombia.com/cine-colombia-galerias"));
		theaters.add(new Theater("Cinemark Atlantis Plaza", "4b40aef31d3f3083", 
				"4.66618198", "-74.05592233", "4to Piso, Centro Comercial Atlantis Plaza, Calle 81 No. 13-05", "(1) 2266555", "bogota", "http://www.cinemark.com.co/newface/teatros.aspx?current=teatros"));
		theaters.add(new Theater("Cine Colombia - Multiplex Andino", "30d145613624c837",
				"4.66685565", "-74.05309528", "Carrera 12 No. 82-02" , "(1) 4042463", "bogota", "http://www.cinecolombia.com/cine-colombia-andino"));
		theaters.add(new Theater("Cine Colombia - Multiplex Avenida Chile", "16ce2ea377b0c7ba",
				"4.65710643", "-74.05789887", "Calle 72 No. 10-34", "(1) 4042463", "bogota", "http://www.cinecolombia.com/cine-colombia-avenida-chile"));
		theaters.add(new Theater("Cine Colombia - Multiplex Metrópolis", "8f3d2bb2d7df7389",
				"4.680273", "-74.082517", "Centro Comercial Metrópolis, Avenida 68 No. 75A-50", "(1) 4042463", "bogota", "http://www.cinecolombia.com/cine-colombia-metropolis"));
		theaters.add(new Theater("Cine Colombia - Multiplex Titán Plaza", "3640b6696fa3a65b",
				"4.694708", "-74.086188", "Carrera 72 No. 80 - 94, Centro Comercial Titán Plaza", "(1) 4042463", "bogota", "http://www.cinecolombia.com/cine-colombia-titan-plaza"));
		theaters.add(new Theater("Procinal - Diver Plaza Álamos", "cc1ce66c467f5063",
				"4.70076202", "-74.11505016", "Local 301, Transversal 96 No. 70A-85", "(1) 5921650", "bogota", "http://www.procinal.com.co/salas/diver-plaza-alamos"));
		theaters.add(new Theater("Cinépolis Los Hayuelos", "bf1ca08e67af8d3a",
				"4.6642507", "-74.130084", "Centro Comercial Hayuelos, Calle 20 No. 82-52 Occidente", "(1) 3546085", "bogota", "http://www.cinepolis.com.co/_CARTELERA/cartelera.aspx?ic=87#Cine341"));
		
		theaters.add(new Theater("Procinal - Tunal", "bb9fbad8bac1663f",
				"4.57749865", "-74.13071389", "Local 2149, Calle 47B No. 24B-33 Sur", "(1) 5921650", "bogota", "http://www.procinal.com.co/salas/tunal"));
		theaters.add(new Theater("Royal Films - Multicine Altavista", "9d434f67fbfe29c9",
				"4.5321863", "-74.11849536", "Avenida Caracas y en frente al Portal de Usme, Centro Comercial Altavista", "(1) 3545640", "bogota", "http://www.royal-films.com/cartelera_ciudad_sala.php?sala=Multicine%20Altavista&ciudad=Bogota"));
		theaters.add(new Theater("Cine Colombia - Multiplex Unicentro", "73afe775f3828e62",
				"4.70255027", "-74.04092864", "Carrera 15 No. 123-30",  "(1) 4042463", "bogota", "http://www.cinecolombia.com/cine-colombia-unicentro"));
		theaters.add(new Theater("Cinemark CAFAM Floresta", "70543c5f2f6f0583",
				"4.68673434", "-74.07372408", "Transversal 48 No. 94-97 Local 2717", "(1) 2266555", "bogota", "http://www.cinemark.com.co/newface/teatros.aspx?current=teatros"));
		//Biblioteca Luis Ángel Arango
		theaters.add(new Theater("Cinemanía", "3cc5d92c9eefe6a6",
				"4.67830152", "-74.05026506", "93 y Chicó, Carrera 14 No. 93A-85", "(1) 6210122", "bogota", "http://www.cinemania.com.co/"));
		theaters.add(new Theater("Procinal - Bulevar", "3cf075ccdf79cc61", 
				"4.71220608", "-74.07143519", "Local 2123/2124 - Centro Comercial Bulevar Niza, Carrera 52 No. 125A-59", "(1) 5921650", "bogota" , "http://www.procinal.com.co/salas/bulevar"));
		theaters.add(new Theater("Cinema Paraíso", "ae9e89fdf4b1478a",
				"4.69639143", "-74.02987518", "Usaquén, Carrera 6 No. 119B-56", "(1) 2133756", "bogota", "http://www.cinemaparaiso.com.co/"));
		//Fundación Gilberto Alzate Avendaño
		theaters.add(new Theater("Cine Colombia - Calle 100", "2400c138a28b0a45",
				"4.68773552", "-74.06439389", "Transversal 55 No. 98A-66", "(1) 4042463", "bogota", "http://www.cinecolombia.com/cine-colombia-calle-100"));
		
		theaters.add(new Theater("Procinal - Tintal Plaza", "2c46dea791cc4071",
				"4.642208", "-74.156109", "Local 301, Carrera 86 No. 6-37", "(1) 5921650", "bogota", "http://www.procinal.com.co/salas/tintal-plaza"));
		theaters.add(new Theater("IMAX Plaza de las Américas", "d760183dc6d17f8b",
				"4.61945621" , "-74.13542587", "Transversal 71 D No. 6-94, Centro Comercial Plaza de las Américas", "(1) 5921650", "bogota", "http://procinal.com.co/salas/imax-plaza-de-las-americas"));
		theaters.add(new Theater("Cinemark Trebolis el Porvenir", "30d84d42329c27f2",
				"4.63924522", "-74.18371082", "Carrera 95 A No. 49 C - 80 Sur Local 2-07, Centro Comercial Trebolis", "(1) 2266555", "bogota" , "http://www.cinemark.com.co/newface/teatros.aspx?current=teatros"));
		theaters.add(new Theater("Procinal - Centro Suba", "11dba0fae7cdb86b",
				"4.73737912", "-74.08529377", "Local 15-102, Calle 140 No. 91-19", "(1) 5921650", "bogota", "http://www.procinal.com.co/salas/centro-suba"));
		theaters.add(new Theater("Cine Colombia - Santa Barbara", "6f0f6ca062ced5c6",
				"4.69294526", "-74.03229656", "Calle 114 No. 6A-92", "(1) 4042463", "bogota", "http://www.cinecolombia.com/cine-colombia-santa-barbara"));
		theaters.add(new Theater("Cine Domo Maloka", "c375814a926683ed",
				"4.655545", "-74.109072","Carrera 68D - Calle 43 A - Salitre", "(1) 4272707", "bogota" , "http://www.maloka.org/index.php/experiencias-maloka-cti/49-cine-domo-y-cine-3d-maloka"));
		//Alianza Francesa - Sede Centro
		//Biblioteca Pública La Peña
		//Teatro México - Jorge Enrique Molina
		//Teatro Jorge Eliécer Gaitán
		
		theaters.add(new Theater("Biblioteca el Tunal", "7bf5bc24be114adc", 
				"4.57207202", "-74.12990537", "El Tunal Oriental, Calle 48B Sur No. 21-13", "(1) 7698737", "bogota", "http://www.biblored.gov.co/biblioteca-tunal"));
		
		theaters.add(new Theater("Biblioteca Virgilio Barco", "3eb4f3ea40a4dae2",
				"4.65638453", "-74.08801076", "Parque Simón Bolívar - Teusaquillo, Avenida-Carrera 48 No. 61-50", "(1) 3158890", "bogota", "http://www.biblored.gov.co/biblioteca-virgilio"));
		
		theaters.add(new Theater("Biblioteca el Tintal", "c9e5beaafc6fb76c",
				"4.643278", "-74.155191", "Tintala, Avenida Ciudad de Cali 6 C - 09", "(1) 4528974", "bogota", "http://www.biblored.gov.co/biblioteca-tintal"));
		theaters.add(new Theater("Jardín Botánico José Celestino Mutis", "c9e5beaafc6fb76c",
				"4.667885", "-74.099770", "Avenida Calle 63 No. 68-95", "(1) 4377060", "bogota", "http://www.jbb.gov.co/jardin/"));
	}
	
	private void fillTheatersCali(){
		
		theaters.add(new Theater("Cine Colombia - Multiplex Palmetto", "83fe0d6034cdb0d", "3.412272", "-76.540686", 
				"Centro Comercial Palmetto Plaza, Calle 9 No. 48-81", "(2) 6442463", "cali" ,"http://www.cinecolombia.com/cine-colombia-palmetto"));
		theaters.add(new Theater("Cine Colombia - Multiplex Cosmocentro", "da95c70a939167df", "3.413536", "-76.547445", 
				"Centro Comercial Cosmocentro, Calle 5 No. 50-103", "(2) 6442463", "cali" ,"http://www.cinecolombia.com/cine-colombia-cosmocentro"));
		theaters.add(new Theater("Cine Colombia - Multiplex Río Cauca", "524db638dbbf96b5", "3.441868", "-76.479934", 
				"Parque Comercial Río Cauca, Calle 75 A N° 20 - 81", "(2) 6442463", "cali" ,"http://www.cinecolombia.com/cine-colombia-rio-cauca"));
		theaters.add(new Theater("Cinépolis Limonar", "f8a96b624912d60e", "3.394459", "-76.544485", 
				"Calle 5 No. 69-03 - Local 304", "(2) 489 7667", "cali" ,"http://www.cinepolis.com.co/_CARTELERA/cartelera.aspx?ic=104"));
		theaters.add(new Theater("Cine Colombia - Multiplex Chipichape", "f03d5e6dcaf38102", "3.475586", "-76.527828", 
				"Centro Comercial Chipichape, Avenida 6A Norte N° 37N-25", "(2) 6442463", "cali" ,"http://www.cinecolombia.com/cine-colombia-chipichape"));
		
	}
	
	public ArrayList<Theater> getTheaters(){
		fillTheatersBogota();
		fillTheatersCali();
		return theaters;
	}
}
