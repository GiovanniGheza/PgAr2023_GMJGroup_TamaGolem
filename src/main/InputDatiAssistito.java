package main;

public abstract class InputDatiAssistito extends InputDatiGhz{
	
	public String inputNome() {
		return leggiStringa("Inserisci il nome del giocatore:");
	}
	
	public int[] inputPietreDaInserire(int maxPietreIngerite, int maxPietreNellaScorta) {
		int pietreScelte[] = new int[maxPietreIngerite];
		int pietreGiàInserite[] = new int[maxPietreIngerite];
		
		for(int i = 0; i < maxPietreIngerite; i++) {
			pietreScelte[i]
					= leggiIntero("Inserira una delle pietre che si vuole usare (Ancora "
							+ (maxPietreIngerite - i), 0, maxPietreNellaScorta);
		}
		
		return pietreScelte;
	}
}