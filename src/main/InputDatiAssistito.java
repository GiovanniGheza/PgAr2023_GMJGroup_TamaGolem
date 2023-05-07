package main;

import java.util.ArrayList;

//TODO: estrarre le costanti

public abstract class InputDatiAssistito extends InputDatiGhz{
	
	public static String inputNome(String diChiIlNome, String nomeNonDisponibile) {
		String nome = leggiStringa("Inserisci il nome del " + diChiIlNome);
		while(nome.equals(nomeNonDisponibile)) {
			System.out.print("Nome non disponibile. Renserisci il nome.");
			nome = leggiStringa("Inserisci il nome del " + diChiIlNome);
		}
		
		return nome;
	}
	
	public static int[] inputPietreDaInserire(String nomeGiocatore, int maxPietreIngerite, int numeroPietreNellaScorta) {
		
		int pietreScelte[] = new int[maxPietreIngerite];
		ArrayList<Integer> pietreGiaInserite = new ArrayList<Integer>();
		
		for(int i = 0; i < maxPietreIngerite; i++) {
			pietreScelte[i]
					= leggiIntero(nomeGiocatore + " deve inserire i numeri delle pietre che vuole far ingoiare al tamagolem (Ancora "
							+ (maxPietreIngerite - i) + " pietre ingeribili)", 0, numeroPietreNellaScorta - 1, pietreGiaInserite);
			pietreGiaInserite.add(pietreScelte[i]);
		}
		
		return pietreScelte;
	}
	
	public static boolean chiediDiGiocareDiNuovo() {
		return yesOrNo("\nVolete rigiocare? ");
	}
}