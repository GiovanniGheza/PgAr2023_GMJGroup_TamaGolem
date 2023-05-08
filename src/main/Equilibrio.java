package main;

import java.util.*;

/**
 * Questa classe rappresenta l'equilibrio dell'universo.
 * @author Giovanni Gheza
 *
 */
public class Equilibrio {

	

	//il massimo in modulo estraibile quando si genera una potenza
	public static int MAX_POTENZA_ESTRAIBILE = 7;

	//costanti utili nel toString
	public static String INFO_LETTURA_TABELLA 
				= "Nella Tabella sono rapresentate le potenze esercitate dagli elementi della verticale "
				+ "sugli elementi dell'orizzontale";
	public static String DIVISORE_ORIZZONTALE = "-";
	public static String DIVISORE_VERTICALE = "|";
	public static String A_CAPO = "\n";
	public static String SPAZIO = " ";
	//costanti per il format della string
	private static final String S = "s";
	private static final String PERCENTUALE = "%";
	
	
	//lunghezza caselle, deve essere uguale alla lunghezza del nome dell'elemento dal nome piu' lungo
	//il valore di default e' 8
	public static int lunghezzaCaselle = 8;
	//come le stringe devono essere formattate
	public static String formatDellaCasella = PERCENTUALE + lunghezzaCaselle + S;
	
	//tabella che contiene le potenze degli elementi,
	//la mappa interna rappresenta le righe della tabella,
	//la prima stringa da sinistra indica in che riga sono
	//la seconda stringa indica invece la colonna
	private Map<String, Map<String, Integer>> equilibrio = new HashMap<>();
	
	//tutti i nomi degli elementi conservati in un array di string
	private String[] elementiDiEquilibrio;
	
	//il numero di elementi nell'universo
	private int numeroElementi;
	
	//il numero delle potenze per elemento
	//(ovvero numeroElementi-1, non considerando la potenza tra elementi uguali)
	private int numeroPotenzePerElemento;

	/**
	 * Costruisce un Equilibrio dati gli elementi su cui costruirlo
	 * @param elementi - gli elementi del nostro universo
	 */
	Equilibrio(String elementi[]){
		//generazione riga
		Map<String, Integer> colonnaEquilibrio = new HashMap<>();
		
		lunghezzaCaselle = elementi[0].length();
		
		for(String elemento: elementi) {
			//inizialmente mette tutte le potenze a zero
			colonnaEquilibrio.put(elemento, 0);
			
			//controllo quale � il nome dell'elemento pi� lungo
			if(elemento.length() >= lunghezzaCaselle)
				lunghezzaCaselle = elemento.length();
		}

		//riempimento colonne
		for(String elemento: elementi) {
			equilibrio.put(elemento, new HashMap<>(colonnaEquilibrio));
		}

		
		formatDellaCasella = PERCENTUALE + lunghezzaCaselle + S;
		elementiDiEquilibrio = elementi;
		numeroElementi = elementi.length;
		numeroPotenzePerElemento = numeroElementi - 1;
	}
	
	/**
	 * resetta tutte le potenze a zero
	 */
	public void resetEquilibrio() {
		for(String elementoEsterno: elementiDiEquilibrio) {
			for(String elementoInterno: elementiDiEquilibrio) {
				equilibrio.get(elementoEsterno).put(elementoInterno, 0);
			}
		}
	}

	/**
	 * genera tutte le potenze tra gli elementi
	 */
	public void generaEquilibrio() {
		
		//resetto a zero l'equilibrio
		resetEquilibrio();
		
		for(String elementoEsterno: elementiDiEquilibrio) {
			//quante potenze devo ancora estrarre
			//serve per controlli dopo
			int numeroPotenzeAncoraDaEstrarre = numeroPotenzePerElemento;
			
			int sommaPotenze = 0;

			//visto che le chiavi sono identiche sia fuori che dentro, posso coppiare l'array delle chiavi esterne
			String[] chiaviDiEquilibrioInterno = elementiDiEquilibrio;
			
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

	/**
	 * ritorna l'equilibrio come è conservato nella classe, ovvero come mappa di mappe di interi
	 * @return equilibrio come mappa di mappe di interi
	 */
	public Map<String, Map<String, Integer>> getEquilibrio() {
		return equilibrio;
	}

	/**
	 * ritorna tutte le potenze di un elemento
	 * @param elemento - l'elemento di cui si vogliono sapere tutte le potenze
	 * @return la mappa delle potenze se l'elemento richiesto esiste, null altrimenti
	 */
	public Map<String, Integer> getPotenzeDiUnElemento(String elemento) {
		if(equilibrio.containsKey(elemento))
			return equilibrio.get(elemento);
		return null;
	}

	/**
	 * ritorna la potenza del primo elemento verso il secondo, se si ottiene un numero negativo significa che è il secondo elemento che vince sul primo
	 * @param primoElemento - l'elemento di cui voglio sapere la potenza contro il secondo
	 * @param secondoElemento  - l'elemento di cui voglio sapere la potenza dal primo
	 * @return la potenza tra gli elementi se gli elementi esistono, Integer.MIN_VALUE altrimenti
	 */
	public int getPotenzaTraElementi(String primoElemento, String secondoElemento) {
		if(equilibrio.containsKey(primoElemento) 
				&& equilibrio.get(primoElemento).containsKey(secondoElemento))
			return equilibrio.get(primoElemento).get(secondoElemento);

		return Integer.MIN_VALUE;
	}

	/**
	 * @return i nomi di tutti gli elementi
	 */
	public String[] getElementiDiEquilibrio() {
		return elementiDiEquilibrio;
	}
	
	/**
	 * @param i - la posizione dell'elemento
	 * @return il nome dell'eemento voluto
	 */
	public String getElementoDiEquilibrio(int i) {
		return elementiDiEquilibrio[i];
	}
	
	/**
	 * metodo che crea un elenco degli elementi dell'equilibrio
	 * @return la string con gli elementi
	 */
	public String getEementiDiEquilibrioComeElenco() {
		StringBuffer elencoElementi = new StringBuffer();
		for(String elemento: getElementiDiEquilibrio()) {
			elencoElementi.append(DIVISORE_ORIZZONTALE + elemento + A_CAPO);
		}
		
		return elencoElementi.toString();
	}

	public int getNumeroElementi() {
		return numeroElementi;
	}

	public int getNumeroPotenzePerElemento() {
		return numeroPotenzePerElemento;
	}
	
	/**
	 * overload del metodo toString
	 * @return l'equilibrio sottoforma di una tabella contenuta in una stringa
	 */
	public String toString() {
		//determinazione lunghezza divisore orizzontale
		StringBuffer divisoreOrizzontale = new StringBuffer();
		
		for(int i = 0; i < (lunghezzaCaselle + 1) * (numeroElementi + 1); i++)
			divisoreOrizzontale.append(DIVISORE_ORIZZONTALE);
		
		StringBuffer tabella = new StringBuffer();
		
		//aggiungo un po' di spazio
		tabella.append(A_CAPO);
		
		//scrittura la cella vuota in alto a sinistra della tabella
		tabella.append(String.format(formatDellaCasella,SPAZIO));
		tabella.append(DIVISORE_VERTICALE);
		
		//scrittura prima riga con i nomi degli elementi
		for(String elemento: elementiDiEquilibrio) {
			tabella.append(String.format(formatDellaCasella,elemento));
			tabella.append(DIVISORE_VERTICALE);
		}
		
		//scrittura prima riga di orizzontale
		tabella.append(A_CAPO);
		tabella.append(divisoreOrizzontale);
		tabella.append(A_CAPO);
		
		for(String elementoEsterno: elementiDiEquilibrio) {
			//scrittura dei nomi sulla prima colonna
			tabella.append(String.format(formatDellaCasella,elementoEsterno));
			tabella.append(DIVISORE_VERTICALE);
			//scrittura delle potenze
			for(String elementoInterno: elementiDiEquilibrio) {
				//scrittura della potenza
				tabella.append(
						String.format(formatDellaCasella, equilibrio.get(elementoEsterno).get(elementoInterno)));
				tabella.append(DIVISORE_VERTICALE);
			}
			//scrittura riga orizzontale tra le righe degli elementi
			tabella.append(A_CAPO);
			tabella.append(divisoreOrizzontale);
			tabella.append(A_CAPO);
		}
		
		//aggiungo un po' di spazio
		tabella.append(A_CAPO);
		tabella.append(divisoreOrizzontale);
		tabella.append(A_CAPO);
		tabella.append(INFO_LETTURA_TABELLA);
		tabella.append(A_CAPO);
		tabella.append(divisoreOrizzontale);
		tabella.append(A_CAPO);

		return tabella.toString();
	}
}