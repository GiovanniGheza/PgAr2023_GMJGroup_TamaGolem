package main;

import java.util.ArrayList;

/**
 * Classe con i metodi per prendere in input il nome e quali pietre si vuole far ingerire al golem
 */
public abstract class InputDatiAssistito extends InputDatiGhz{
	
	private static final String DOMANDA_RIGIOCARE = "\nVolete rigiocare? ";
	private static final String PIETRE_INGERIBILI = " pietre ingeribili)";
	private static final String DEVE_INSERIRE_NUMERI_DELLE_PIETRE_DA_INGOIARE = " deve inserire i numeri delle pietre che vuole far ingoiare al tamagolem (Ancora ";
	private static final String NOME_NON_DISPONIBILE_RENSERISCI_IL_NOME = "Nome non disponibile. Renserisci il nome.";
	private static final String INSERISCI_IL_NOME_DEL = "Inserisci il nome del ";
	private static final String ATTEZIONE_SET_UGUALE = "ATTENZIONE: Non puoi usare un set di pietre identico a quello avversario, reinserisci.";

	/**
	 * Assiste l'inserimento del nome di un giocatore
	 * 
	 * @param diChiIlNome - codice del giocatore
	 * @param nomeNonDisponibile - il nome non disponibile, ovvero quello del primo giocatore
	 * @return il nome del giocatore
	 */
	public static String inputNome(String diChiIlNome, String nomeNonDisponibile) {
		String nome = leggiStringa(INSERISCI_IL_NOME_DEL + diChiIlNome);
		while(nome.equals(nomeNonDisponibile)) {
			System.out.print(NOME_NON_DISPONIBILE_RENSERISCI_IL_NOME);
			nome = leggiStringa(INSERISCI_IL_NOME_DEL + diChiIlNome);
		}
		
		return nome;
	}
	
	/**
	 * Assiste l'input delle pietre da far inghiare al tamagolem
	 * 
	 * @param nomeGiocatore - il nome del giocatore che vuole infilare le sue pietre nel tamagolem
	 * @param maxPietreIngerite - il numero di pietre da ingerire
	 * @param numeroPietreNellaScorta - il numero delle pietre rimanenti nella scorta
	 * @return i numeri delle pietre nella scorta sottoforma di un array di int
	 */
	public static int[] inputPietreDaInserire(String nomeGiocatore, int maxPietreIngerite, int numeroPietreNellaScorta) {
		
		int pietreScelte[] = new int[maxPietreIngerite];
		ArrayList<Integer> pietreGiaInserite = new ArrayList<Integer>();
		
		for(int i = 0; i < maxPietreIngerite; i++) {
			pietreScelte[i]
					= leggiIntero(nomeGiocatore + DEVE_INSERIRE_NUMERI_DELLE_PIETRE_DA_INGOIARE
							+ (maxPietreIngerite - i) + PIETRE_INGERIBILI, 0, numeroPietreNellaScorta - 1, pietreGiaInserite);
			pietreGiaInserite.add(pietreScelte[i]);
		}
		
		return pietreScelte;
	}
	
	public static boolean chiediDiGiocareDiNuovo() {
		return yesOrNo(DOMANDA_RIGIOCARE);
	}
	
	/**
	 * metodo per generare il golem e controllare che non finisca in stallo con l'altro golem.
	 * @param myPartita - la partita in corso
	 * @param codiceGiocatore - il giocatore a cui devo dare il golem
	 */
	public static void parteGenerazioneGolem(Partita myPartita, String codiceGiocatore) {
		//la partita non e' finita, quindi devo mandare in campo un nuovo tamagolem
		//stampo le pietre disponibili
		System.out.println(myPartita.getStringaPietreDisponibili());
		//prendo i numeri delle pietre ingerite
		int pietreDaIngerire[] = inputPietreDaInserire(myPartita.getNomeDi(codiceGiocatore), myPartita.getMaxPietreIngerite(), myPartita.getNumeroPietreNellaScorta());
		
		//se rischio di avere uno stallo tra golem riprendo le pietre da ingerire
		while(!myPartita.generaTamaGolem(codiceGiocatore, pietreDaIngerire)) {
			//avverto dello stallo
			System.out.println(ATTEZIONE_SET_UGUALE);
			//ristampo le pietre disponibili
			System.out.println(myPartita.getStringaPietreDisponibili());
			//riprendo i numeri delle pietre ingerite
			pietreDaIngerire = inputPietreDaInserire(myPartita.getNomeDi(codiceGiocatore), myPartita.getMaxPietreIngerite(), myPartita.getNumeroPietreNellaScorta());
		}
	}
}