package main;

public class MainTamagolem {

	static final String elementi[] = {"Cri", "Oliva", "Coral", "Matton", "Etere", "Bruno"};
	
	public static void main(String[] args) {
		
		Equilibrio myEquilibrio = new Equilibrio(elementi);
		
		String nomeA = InputDatiAssistito.inputNome("primo giocatore: ", ""),
				nomeB = InputDatiAssistito.inputNome("secondo giocatore: ", nomeA);
		
		Giocatore giocatoreA = new Giocatore(nomeA);
		Giocatore giocatoreB = new Giocatore(nomeB);
		
		Partita myPartita = new Partita(giocatoreA, giocatoreB, myEquilibrio);
		
		//while del programma, continua finche' il giocatore non vuole più gocare
		while(true) {
			System.out.println(myPartita.eseguiSetUp());
			
			//TODO: mettere questi controlli da un'altra parte
			
			//input delle prime pietre di A
			System.out.println(myPartita.getStringaPietreDisponibili());
			int pietreInizialiDiA[] = InputDatiAssistito.inputPietreDaInserire(myPartita.getGiocatore("A").getNome(), myPartita.getMaxPietreIngerite(), myPartita.getNumeroPietreNellaScorta());
			while(!myPartita.generaTamaGolem("A", pietreInizialiDiA)) {
				System.out.println("ATTENZIONE: Non puoi usare un set di pietre identico a quello avversario, reinserisci.");
				System.out.println(myPartita.getStringaPietreDisponibili());
				pietreInizialiDiA = InputDatiAssistito.inputPietreDaInserire(myPartita.getGiocatore("A").getNome(), myPartita.getMaxPietreIngerite(), myPartita.getNumeroPietreNellaScorta());
			}
			
			//input delle prime pietre di B
			System.out.println(myPartita.getStringaPietreDisponibili());
			int pietreInizialiDiB[] = InputDatiAssistito.inputPietreDaInserire(myPartita.getGiocatore("B").getNome(), myPartita.getMaxPietreIngerite(), myPartita.getNumeroPietreNellaScorta());
			while(!myPartita.generaTamaGolem("B", pietreInizialiDiB)) {
				System.out.println("ATTENZIONE: Non puoi usare un set di pietre identico a quello avversario, reinserisci.");
				System.out.println(myPartita.getStringaPietreDisponibili());
				pietreInizialiDiB = InputDatiAssistito.inputPietreDaInserire(myPartita.getGiocatore("B").getNome(), myPartita.getMaxPietreIngerite(), myPartita.getNumeroPietreNellaScorta());
			}
			
			//while dei turni, continua finche' un giocatore non perde
			while(true) {
				System.out.println(myPartita.eseguiTurno());
				
				if(myPartita.checkTamagolemMorti()) {
					
					String codiceGiocatore = myPartita.getCodiceGiocatoreConTamagolemMorto();
					myPartita.getGiocatore(codiceGiocatore).diminuisciTamagoleRimanenti();
					System.out.println(myPartita.getStringaStatoDelGioco());
					
					if(myPartita.checkFinePartita()) {
						break;
					}
					
					System.out.println(myPartita.getStringaPietreDisponibili());
					int pietreDaIngerire[] = InputDatiAssistito.inputPietreDaInserire(myPartita.getGiocatore(codiceGiocatore).getNome(), myPartita.getMaxPietreIngerite(), myPartita.getNumeroPietreNellaScorta());
					System.out.println(myPartita.generaTamaGolem(codiceGiocatore, pietreDaIngerire));
				}
			}
			System.out.println(myPartita.eseguiFinePartita());
			
			if(!InputDatiAssistito.chiediDiGiocareDiNuovo()) break;
		}
		
		System.out.println("\n\nCiao ciao :D");
	}

}
