package main;

public class MainTamagolem {

	//gli elementi del nostro universo
	static final String ELEMENTI[] = {"Trentino", "Oliva", "Mango", "Zolfo", "Salnitro"};
	//costanti stringa da stampare
	private static final String STRINGA_VUOTA = "";
	//private static final String ATTEZIONE_SET_UGUALE = "ATTENZIONE: Non puoi usare un set di pietre identico a quello avversario, reinserisci.";
	private static final String CIAO_CIAO = "\n\nCiao ciao :D";
	private static final String SECONDO_GIOCATORE = "secondo giocatore: ";
	private static final String PRIMO_GIOCATORE = "primo giocatore: ";
	//costandi che indicano i giocatori
	private static final String B = "B";
	private static final String A = "A";
	
	public static void main(String[] args) {
		
		//costruisco l'equilibrio
		Equilibrio myEquilibrio = new Equilibrio(ELEMENTI);
		
		//chiedo i nomi dei giocatori
		String nomeA = InputDatiAssistito.inputNome(PRIMO_GIOCATORE, STRINGA_VUOTA),
				nomeB = InputDatiAssistito.inputNome(SECONDO_GIOCATORE, nomeA);
		
		//costruisco i giocatori
		Giocatore giocatoreA = new Giocatore(nomeA);
		Giocatore giocatoreB = new Giocatore(nomeB);
		
		//costruisco la partita
		Partita myPartita = new Partita(giocatoreA, giocatoreB, myEquilibrio);
		
		//Do il benvenuto ai giocatori dando info sul gioco, i nomi degli elementi,
		//il numero di golem e quante pietre ingoiano
		System.out.println(myPartita.getStringaInfoGioco());
		
		//while del programma, continua finche' il giocatore non vuole piu' giocare
		while(true) {
			//eseguo il setUp della partita e stampo la stringa che mi restituisce
			System.out.println(myPartita.eseguiSetUp());
			
			//generazione primo tamagolem di A
			InputDatiAssistito.parteGenerazioneGolem(myPartita, A);

			//generazione primo tamagolem di B
			InputDatiAssistito.parteGenerazioneGolem(myPartita, B);
			
			//while dei turni, continua finche' un giocatore non perde
			while(true) {
				//stampo cosa e' successo nel turno
				System.out.println(myPartita.eseguiTurno());
				
				//controllo se uno dei tamagolem e' morto
				if(myPartita.checkTamagolemMorti()) {
					
					//perdo il giocatore con il tamagolem morto
					String codiceGiocatoreConTamagolemMorto = myPartita.getCodiceGiocatoreConTamagolemMorto();
					//diminuisco il numero di tamagolem nella sua squadra
					myPartita.diminuisciTamagoleRimanenti(codiceGiocatoreConTamagolemMorto);
					//stampo lo stato del gioco
					System.out.println(myPartita.getStringaStatoDelGioco());
					
					//controllo se la partita e' finita
					if(myPartita.checkFinePartita()) {
						break;
					}
					
					//rigenero il tamagolem morto
					InputDatiAssistito.parteGenerazioneGolem(myPartita, codiceGiocatoreConTamagolemMorto);
				}
			}
			
			//sono fuori dal ciclo dei turni quindi la partita e' finita
			//stampo il fine partita
			System.out.println(myPartita.eseguiFinePartita());
			
			//chiedo se i giocatori vogliono rigiocare
			if(!InputDatiAssistito.chiediDiGiocareDiNuovo())
				break;
		}
		
		//saluto i giocatori
		System.out.println(CIAO_CIAO);
	}
}