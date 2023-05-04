package main;

import java.util.*;

public class Equilibrio {

	public static int MAX_POTENZA_ESTRAIBILE = 7;

	//costanti utili nel toString
	public static String INFO_LETTURA_TABELLA 
				= "Nella Tabella sono rapresentate le potenze esercitate dagli elementi sulla verticale "
				+ "sugli elementi dell'orizzontale";
	public static String DIVISORE_ORIZZONTALE = "------------------------------------------";
	public static String DIVISORE_VERTICALE = "|";
	public static String A_CAPO = "\n";
	public static String SPAZIO = " ";
	public static String FORMAT_DELLA_STRING = "%6s";
	
	//tabella che contiene le potenze degli elementi,
	//la mappa interna rappresenta le righe della tabella
	//la prima stringa da sinistra indica in che riga sono
	//la seconda stringa indica invece la colonna
	private Map<String, Map<String, Integer>> equilibrio = new HashMap<>();
	private String[] chiaviDiEquilibrio;
	private int numeroElementi, numeroPotenzePerElemento;

	Equilibrio(String elementi[]){
		//generazione riga
		Map<String, Integer> colonnaEquilibrio = new HashMap<>();
		for(String elemento: elementi) {
			//inizialmente mette tutte le potenze a zero
			colonnaEquilibrio.put(elemento, 0);
		}

		//riempimento colonne
		for(String elemento: elementi) {
			equilibrio.put(elemento, new HashMap<>(colonnaEquilibrio));
		}

		chiaviDiEquilibrio = elementi;
		numeroElementi = equilibrio.size();
		numeroPotenzePerElemento = numeroElementi - 1;
	}
	
	public void resetEquilibrio() {
		Set<String> keySetDiEquilibrio = equilibrio.keySet();
		String[] keyDiEquilibrio = keySetDiEquilibrio.toArray(new String[0]);
		String[] keyDiEquilibrioInterno = keyDiEquilibrio;
		
		for(String elementoEsterno: keyDiEquilibrio) {
			for(String elementoInterno: keyDiEquilibrioInterno) {
				equilibrio.get(elementoEsterno).put(elementoInterno, 0);
			}
		}
	}

	public void generaEquilibrio() {
		
		//resetto a zero l'equilibrio
		resetEquilibrio();
		
		for(String elementoEsterno: chiaviDiEquilibrio) {
			//quante potenze devo ancora estrarre
			//serve per controlli dopo
			int numeroPotenzeAncoraDaEstrarre = numeroPotenzePerElemento;
			
			int sommaPotenze = 0;

			//visto che le chiavi sono identiche sia fuori che dentro, posso coppiare l'array delle chiavi esterne
			String[] chiaviDiEquilibrioInterno = chiaviDiEquilibrio;
			
			for(String elementoInterno: chiaviDiEquilibrioInterno) {
				
				//caso dello stesso elemento => metto potenza a zero
				if(elementoEsterno.equals(elementoInterno)) {
					//metto potenza a zero
					equilibrio.get(elementoEsterno).put(elementoInterno, 0);
				} else //caso elementi diversi
				{
					//la potenza che andrà tra i due elementi
					int potenzaDaAggiungere = 0;

					//ultima potenza, dipende dalle altre e non è casuale
					if(numeroPotenzeAncoraDaEstrarre == 1) {
						potenzaDaAggiungere = (-sommaPotenze);
					}
					//caso che gli elementi a cui voglio dare una potenza NON ne hanno una già assegnata
					else if(equilibrio.get(elementoInterno)
							.get(elementoEsterno) == 0) {
						//cicla finchè esce un numero non zero
						//in base ad altri controlli non dovrebbe essere un ciclo infinito, forse
						while(potenzaDaAggiungere == 0) {
							potenzaDaAggiungere = GeneratoreNumeriCasuali.estraiIntero
									(-MAX_POTENZA_ESTRAIBILE, MAX_POTENZA_ESTRAIBILE);
						}
					} else //caso che gli elementi a cui vogli dare una potenza ne hanno già una
					{
						//la potenza dell'elemento interno(che, se sono in questo else, significa che
						//ha gia' una potenza associata all'elemento) e' l'opposto di quello esterno
						potenzaDaAggiungere = -getPotenzaTraElementi(elementoInterno,elementoEsterno);
					}

					//quando sono alla penultima potenza so che non posso avere la somma delle potenze a
					//zero in quanto ciò significherebbe l'obbligo di mettere l'ultima potenza a zero
					//per avere la somma delle potenze finale nulla
					// => se mi trovo in questo caso devo far in modo di avere una somma diversa
					if(numeroPotenzeAncoraDaEstrarre == 2 && (sommaPotenze + potenzaDaAggiungere == 0)) {
						if(potenzaDaAggiungere > 0) {
							potenzaDaAggiungere ++;
						} else {
							potenzaDaAggiungere --;
						}
					}
					
					//aggiungo la potenza
					equilibrio.get(elementoEsterno).put(elementoInterno, potenzaDaAggiungere);
					
					//aggiorno la somma delle potenze
					sommaPotenze += potenzaDaAggiungere;
					
					numeroPotenzeAncoraDaEstrarre --;
				}
			}
		}
	}

	public Map<String, Map<String, Integer>> getEquilibrio() {
		return equilibrio;
	}

	public Map<String, Integer> getPotenzeDiUnElemento(String elemento) {
		if(equilibrio.containsKey(elemento))
			return equilibrio.get(elemento);
		return null;
	}

	public int getPotenzaTraElementi(String primoElemento, String secondoElemento) {
		if(equilibrio.containsKey(primoElemento) 
				&& equilibrio.get(primoElemento).containsKey(secondoElemento))
			return equilibrio.get(primoElemento).get(secondoElemento);

		return 0;
	}

	public String toString() {
		//info lettura della tabella
		StringBuffer tabella = new StringBuffer(INFO_LETTURA_TABELLA);
		
		//aggiungo un po di spazio
		tabella.append(A_CAPO);
		tabella.append(A_CAPO);
		
		//la cella vuota in alto a sinistra della tabella
		tabella.append(String.format(FORMAT_DELLA_STRING,SPAZIO));
		
		
		tabella.append(DIVISORE_VERTICALE);
		
		//scrittura prima riga con i nomi degli elementi
		for(String elemento: chiaviDiEquilibrio) {
			tabella.append(String.format(FORMAT_DELLA_STRING,elemento));
			tabella.append(DIVISORE_VERTICALE);
		}
		
		//prima riga di orizzontale
		tabella.append(A_CAPO);
		tabella.append(DIVISORE_ORIZZONTALE);
		tabella.append(A_CAPO);
		
		for(String elementoEsterno: chiaviDiEquilibrio) {
			//scrittura dei nomi sulla prima colonna
			tabella.append(String.format(FORMAT_DELLA_STRING,elementoEsterno));
			tabella.append(DIVISORE_VERTICALE);
			//scrittura delle potenze
			for(String elementoInterno: chiaviDiEquilibrio) {
				//scrittura della potenza
				tabella.append(
						String.format(FORMAT_DELLA_STRING, equilibrio.get(elementoEsterno).get(elementoInterno)));
				tabella.append(DIVISORE_VERTICALE);
			}
			//riga orizzontale
			tabella.append(A_CAPO);
			tabella.append(DIVISORE_ORIZZONTALE);
			tabella.append(A_CAPO);
		}

		return tabella.toString();
	}
}