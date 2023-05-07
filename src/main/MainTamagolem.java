package main;

public class MainTamagolem {

	private static final String ATTEZIONE_SET_UGUALE = "ATTENZIONE: Non puoi usare un set di pietre identico a quello avversario, reinserisci.";
	private static final String CIAO_CIAO = "\n\nCiao ciao :D";
	private static final String SECONDO_GIOCATORE = "secondo giocatore: ";
	private static final String PRIMO_GIOCATORE = "primo giocatore: ";
	private static final String B = "B";
	private static final String A = "A";
	static final String elementi[] = {"Cri", "Oliva", "Coral"};
	
	public static void main(String[] args) {
		
		Equilibrio myEquilibrio = new Equilibrio(elementi);
		
		String nomeA = InputDatiAssistito.inputNome(PRIMO_GIOCATORE, ""),
				nomeB = InputDatiAssistito.inputNome(SECONDO_GIOCATORE, nomeA);
		
		Giocatore giocatoreA = new Giocatore(nomeA);
		Giocatore giocatoreB = new Giocatore(nomeB);
		
		Partita myPartita = new Partita(giocatoreA, giocatoreB, myEquilibrio);
		
		//while del programma, continua finche' il giocatore non vuole pi� gocare
		while(true) {
			System.out.println(myPartita.eseguiSetUp());
			
			//TODO: mettere un piccolo menu per decidere cosa fare
			
			//generazione primo tamagolem di A
			parteGenerazioneGolem(myPartita, A);

			//generazione primo tamagolem di B
			parteGenerazioneGolem(myPartita, B);
			
			//while dei turni, continua finche' un giocatore non perde
			while(true) {
				//stampo cosa e' successo nel turno
				System.out.println(myPartita.eseguiTurno());
				
				//controllo se uno dei tamagolem e' morto
				if(myPartita.checkTamagolemMorti()) {
					
					//perdo il giocatore con il tamagolem morto
					String codiceGiocatoreConTamagolemMorto = myPartita.getCodiceGiocatoreConTamagolemMorto();
					//diminuisco il numero di tamagolem nella sua squadra
					myPartita.getGiocatore(codiceGiocatoreConTamagolemMorto).diminuisciTamagoleRimanenti();
					//stampo lo stato del gioco
					System.out.println(myPartita.getStringaStatoDelGioco());
					
					//controllo se la partita e' finita
					if(myPartita.checkFinePartita()) {
						break;
					}
					
					//generazione tamagolem
					parteGenerazioneGolem(myPartita, codiceGiocatoreConTamagolemMorto);
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
	
	/**
	 * metodo per generare il golem e controllare che non finisca in stallo con l'altro golem.
	 * Se vi chiedete come mai questo metodo non si trovi nella classe InputDatiAssistito la motivazione sta nel
	 * fatto che questo ritaglio di codice contiene sia metodi di InputDatiAssistito che Partita e 
	 * il mio volere era di separare in modo netto gli input (classe InputDatiAssistito) dall'elaborazione dati (classe Partita)
	 * avendo il main come uico punto di contatto.
	 * @param myPartita - la partita in corso
	 * @param codiceGiocatore - il giocatore a cui devo dare il golem
	 */
	private static void parteGenerazioneGolem(Partita myPartita, String codiceGiocatore) {
		//la partita non e' finita, quindi devo mandare in campo un nuovo tamagolem
		//stampo le pietre disponibili
		System.out.println(myPartita.getStringaPietreDisponibili());
		//prendo i numeri delle pietre ingerite
		int pietreDaIngerire[] = InputDatiAssistito.inputPietreDaInserire(myPartita.getGiocatore(codiceGiocatore).getNome(), myPartita.getMaxPietreIngerite(), myPartita.getNumeroPietreNellaScorta());
		
		//se rischio di avere uno stallo tra golem riprendo le pietre da ingerire
		while(!myPartita.generaTamaGolem(codiceGiocatore, pietreDaIngerire)) {
			//avverto dello stallo
			System.out.println(ATTEZIONE_SET_UGUALE);
			//ristampo le pietre disponibili
			System.out.println(myPartita.getStringaPietreDisponibili());
			//riprendo i numeri delle pietre ingerite
			pietreDaIngerire = InputDatiAssistito.inputPietreDaInserire(myPartita.getGiocatore(codiceGiocatore).getNome(), myPartita.getMaxPietreIngerite(), myPartita.getNumeroPietreNellaScorta());
		}
	}
}