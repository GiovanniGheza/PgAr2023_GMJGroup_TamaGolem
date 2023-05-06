package main;

public class MainPerTesting {

	public static void main(String[] args) {
		String elementi[] = {"Terra", "Aria", "Fuoco", "Acqua"};
		Equilibrio myEquilibrio = new Equilibrio(elementi);
		Giocatore giocatoreA = new Giocatore("Pino");
		Giocatore giocatoreB = new Giocatore("Joe Mama");
		
		Partita myPartita = new Partita(giocatoreA, giocatoreB, myEquilibrio);
		
		System.out.println(myPartita.eseguiSetUp());
		
		System.out.println(myPartita.getStringaPietreDisponibili());
		
		int pietreSceltaDaA[] = {0,1,2,3,5,6,7};
		
		System.out.println(myPartita.getStringaPietreDisponibili());
		
		int pietreSceltaDaB[] = {0,1,2,3,5,6,7};
		
		System.out.println(myPartita.generaTamaGolem("A", pietreSceltaDaA)? "funziona": "non finziona");
		System.out.println(myPartita.generaTamaGolem("B", pietreSceltaDaB)? "funziona": "non finziona");
		
		
		
		System.out.println(myPartita.getStringaStatoDelGioco());
		//TODO: correggi il metodo che mi da le pietre utilizzate
		
		/*for(int i = 0; i < 5; i++) {
			myEquilibrio.generaEquilibrio();
			String tabella = myEquilibrio.toString();
			System.out.print("\n\nProva " + i + " \n" + tabella);
		}*/
		
		/*myEquilibrio.generaEquilibrio();
		String tabella = myEquilibrio.toString();
		System.out.println("\n\nProva " + 1 + " \n" + tabella);
		*/
		
		//System.out.println("\nPotenza tra Terra e Acqua: " 
		//+ myEquilibrio.getPotenzaTraElementi("Terra", "Acqua"));
		
		/*for(int i = 0; i < 100; i++) {
			myEquilibrio.generaEquilibrio();
			if(myEquilibrio.checkPerZeriInposizioniSbagliate())
				System.out.print("\n\nProva " + i + " \n" + myEquilibrio.toString());
		}*/
	}
}