package main;

import java.util.*;

public class Equilibrio {

	public static int MIN_POTENZA = -5;
	public static int MAX_POTENZA = 5;

	//tabella che contiene le potenze degli elementi, 
	//la string esterna rappresenta gli elementi sulla x,
	//la string interna rappresenta gli elementi sulla y,
	//in altre parole la mappa interna rappresenta le colonne della tabella
	private Map<String, Map<String, Integer>> equilibrio = new HashMap<>();
	private int numeroElementi, numeroPotenzePerElemento;

	Equilibrio(String elementi[]){
		//generazione colonna
		Map<String, Integer> colonnaEquilibrio = new HashMap<>();
		for(String elemento: elementi) {
			//inizialmente mette tutte le potenze a zero
			colonnaEquilibrio.put(elemento, 0);
		}

		//riempimento righe
		for(String elemento: elementi) {
			equilibrio.put(elemento, colonnaEquilibrio);
		}

		numeroElementi = equilibrio.size();
		numeroPotenzePerElemento = numeroElementi - 1;
	}

	public void generaEquilibrio() {

		for(String elementoEsterno: equilibrio.keySet()) {

			//avro bisogno di modificare il range da cui posso estrarre la potenza,
			//quindi prendo le costanti per il range e le conservo in due variabili
			int minPotenza = MIN_POTENZA;
			int maxPotenza = MAX_POTENZA;

			//quante potenze devo ancora estrarre
			//serve per impedire che due potenze venga estratta subito ai massimi
			//lasciando così le altre a 0
			//Es:	range: (-5,5)
			//		potenze: (5,-5,0,0) <- la somma delle potenze è zero ma ho delle potenze tra elementi diversi a zero
			int numeroPotenzeAncoraDaEstrarre = numeroPotenzePerElemento;

			for(String elementoInterno: equilibrio.keySet()) {
				
				//caso dello stesso elemento => metto potenza a zero
				if(elementoEsterno.equals(elementoInterno)) {
					//metto potenza a zero
					equilibrio.get(elementoEsterno).put(elementoInterno, 0);
				} else //caso elementi diversi
				{
					//la potenza che andrà tra i due elementi
					int potenzaDaAggiungere = 0;

					if(numeroPotenzeAncoraDaEstrarre == 1) {
						Set<String> equilibrioSenzaUltimoElemento = equilibrio.keySet();
						equilibrioSenzaUltimoElemento.remove(elementoInterno);
						for(String altriElementi: equilibrioSenzaUltimoElemento)
							potenzaDaAggiungere += getPotenzaTraElementi(elementoEsterno,altriElementi);
						potenzaDaAggiungere = (-potenzaDaAggiungere);
					}
					//caso che gli elementi a cui voglio dare una potenza NON ne hanno una già assegnata
					else if(equilibrio.get(elementoEsterno).get(elementoEsterno) == 0) {
						//cicla finchè esce un numero non zero
						//in base ad altri controlli non dovrebbe essere un ciclo infinito, forse
						while(potenzaDaAggiungere != 0) {
							potenzaDaAggiungere = GeneratoreNumeriCasuali.estraiIntero
									(minPotenza + numeroPotenzeAncoraDaEstrarre
											, maxPotenza - numeroPotenzeAncoraDaEstrarre);
						}
					} else //caso che gli elementi a cui vogli dare una potenza ne hanno già una
					{
						//la potenza dell'elemento interno(che, se sono in questo else, significa che
						//ha gia' una potenza associata all'elemento) e' l'opposto di quello esterno
						potenzaDaAggiungere = -equilibrio.get(elementoInterno).get(elementoEsterno);
					}

					//aggiungo la potenza
					equilibrio.get(elementoEsterno).put(elementoInterno, potenzaDaAggiungere);

					//diminuisco gli estremi del range
					if(potenzaDaAggiungere < 0)
						minPotenza -= potenzaDaAggiungere;
					else if(potenzaDaAggiungere > 0)
						maxPotenza -= potenzaDaAggiungere;
				}

				numeroPotenzeAncoraDaEstrarre --;
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
		StringBuffer tabella = new StringBuffer();

		int lunghezzaNomePiuLungo = 0;
		for(String nome: equilibrio.keySet())
			if(nome.length() > lunghezzaNomePiuLungo)
				lunghezzaNomePiuLungo = nome.length();

		//aggiungo i nomi degli elementi sulla prima linea

		return tabella.toString();
	}
}