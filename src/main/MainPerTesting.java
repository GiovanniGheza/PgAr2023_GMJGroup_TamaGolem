package main;

public class MainPerTesting {

	public static void main(String[] args) {
		String elementi[] = {"Terra", "Aria", "Fuoco", "Acqua"};
		Equilibrio myEquilibrio = new Equilibrio(elementi);
		Giocatore giocatoreA = new Giocatore("Pino");
		Giocatore giocatoreB = new Giocatore("Joe Mama");
		
		Partita myPartita = new Partita(giocatoreA, giocatoreB, myEquilibrio);
		
		System.out.println(myPartita.eseguiSetUp());
		
		int pietreSceltaDaA[] = {0,3};
		int pietreSceltaDaB[] = {0,2};
		
		System.out.println(myPartita.getStringaPietreDisponibili());
		System.out.println(myPartita.generaTamaGolem("A", pietreSceltaDaA));
		System.out.println(myPartita.getStringaPietreDisponibili());
		System.out.println(myPartita.generaTamaGolem("B", pietreSceltaDaB));
		
		System.out.println(myPartita.getStringaStatoDelGioco());
		
		for(int i = 0; i < 10; i++) {
			System.out.println(myPartita.eseguiTurno());
		}
		
		myPartita.forzaSconfitta("A");
		
		System.out.println(myPartita.eseguiFinePartita());
		
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