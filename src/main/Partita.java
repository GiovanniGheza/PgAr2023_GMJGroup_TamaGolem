package main;

import java.util.*;

public class Partita {
	
	//costanti varie per scrivere le stringhe nei metodi
	private static final String FRASE_BENVENUTO = "\n-------\nBenvenuti ai Combattimenti tra Tamagolem!";
	private static final String VINCE_IL_GIOCATORE_CHE = "Vince il giocatore che riesce a sconfiggere tutti i tamagolem avversari.";
	private static final String PIETRE = " pietre.";
	private static final String TAMAGOLEM_OGNUNO_DEI_QUALI_PUO_INGOIARE = " tamagolem, ognuno dei quali puo' ingoiare ";
	private static final String OGNI_GIOCATORE_HA_A_SUA_DISPOSIZIONE = "Ogni giocatore ha a sua disposizione ";
	private static final String GLI_ELEMENTI_CHE_GOVERNANO_SONO = "Gli elementi che governano l'universo sono:";
	private static final String EQUILIBRIO_DELL_UNIERSO_ERA = "L'Equilibrio dell'unierso era:\n";
	private static final String TITOLO_FINE_PARTITA = "\n-------\nFINE PARTITA!!!\n-------\n";
	private static final String LE_PIETRE_DISPONIBILI = "Le pietre disponibili:\n";
	private static final String CONTRO_UNA_PIETRA_DI = " contro una pietra di ";
	private static final String TURNO = "Turno: ";
	private static final String HA_VINTO = " ha vinto!";
	private static final String TAMAGOLEM_RIMANENTI = "Tamagolem rimanenti: ";
	private static final String PARENTESI = ") ";
	private static final String SEPARATORE = "------";
	private static final String PIETRE_UTILIZZATE = "Pietre utilizzate: ";
	private static final String A_CAPO = "\n";
	private static final String HP_CON_UNA_PIETRA_DI = " HP usando una pietra di ";
	private static final String HA_FERITO_IL_TAMAGOLEM_NEMICO_DI = " ha ferito il tamagolem nemico di ";
	private static final String HP_GOLEM = "HP del tamagolem: ";
	private static final String A = "A", B = "B";
	private static final String FRASE_INIZIO_PARTE1 = "Inizia il giocatore ";
	private static final String FRASE_INIZIO_PARTE2 = " come giocatore A.";
	
	//il numero di tamagolem con cui ogni giocatore inizia
	private int maxTamagolemPerGiocatore;
	//il numero di pietre che ogni tamagolem può mangiare
	private int maxPietreIngerite;
	//il numero delle pietre che posso avere nlla scorta al massimo
	private int maxNumeroPietreNellaScorta;
	//il numero delle pietre per singolo elemento nella scorta comune
	private int pietrePerElemento;
	//il numero delle pietre nella scorta comune
	private int numeroPietreNellaScorta;
	//numero degli elementi nell'equilibrio
	int numeroElementi;
	
	//numero del turno
	private int turno = 0;
	
	//i due giocatori
	private Map<String, Giocatore> giocatori = new HashMap<>();
	
	//l'equilibrio
	private Equilibrio equilibrio;
	
	//le pietre nella scorta comune
	private ArrayList<Pietra> pietreADisposizione = new ArrayList<Pietra>();

	/**
	 * costruttore della partita
	 * @param giocatoreA - il primo giocatore
	 * @param giocatoreB - il secondo giocatore
	 * @param equilibrio - l'equilibrio su cui il gioco si basa
	 */
	public Partita(Giocatore giocatoreA, Giocatore giocatoreB, Equilibrio equilibrio) {
		
		//presa dei numero di elementi
		numeroElementi = equilibrio.getNumeroElementi();

		//calcolo del numero di pietre che ogni tamagolem può mangiare
		maxPietreIngerite = (Math.floorDiv(numeroElementi + 1, 3) + 1);
		//calcolo dei tamagolem per giocatore
		maxTamagolemPerGiocatore
			= (Math.floorDiv((numeroElementi - 1)*(numeroElementi - 2), 2 * maxPietreIngerite) + 1);
		//calolo del massimo numero di pietre nella scorta
		maxNumeroPietreNellaScorta
			= (Math.floorDiv(2 * maxTamagolemPerGiocatore * maxPietreIngerite, numeroElementi) + 1) * numeroElementi;
		//calcolo del numero di pietre per elemento nella scorta comune
		pietrePerElemento
			= (Math.floorDiv(2 * maxTamagolemPerGiocatore * maxPietreIngerite, numeroElementi) + 1);
		
		//setto l'equilibrio
		this.equilibrio = equilibrio;
		
		//setto le pietre a disposizione
		resetScortaPietre();
		
		//setto il numero di tamagolem per giocatore
		giocatoreA.setTamaGolemRimanenti(maxTamagolemPerGiocatore);
		giocatoreB.setTamaGolemRimanenti(maxTamagolemPerGiocatore);
		
		//setto i giocatori
		giocatori.put(A, giocatoreA);
		giocatori.put(B, giocatoreB);
	}
	
	/**
	 * genera l'equilibrio
	 */
	public void generaEquilibrio() {
		equilibrio.generaEquilibrio();
	}
	
	/**
	 * genera un tamagolem e lo da al giocatore richiesto
	 * @param giocatore - giocatore a cui dare il tamagolem
	 * @param pietreScelte - le pietre scelte da dare al tamagolem
	 * @return vero se la generazione è andata a buon fine, falso altrimenti
	 */
	public boolean generaTamaGolem(String giocatore, int pietreScelte[]) {
		//controllo che il giocatore chiamato sia A o B
		if(giocatore != A && giocatore != B)
			return false;
		
		//creazione del set di pietre da far ingoiare
		ArrayList<Pietra> pietreDaDareAlTamaGolem = new ArrayList<Pietra>();
		for(int i = 0; i < maxPietreIngerite; i++) {
			pietreDaDareAlTamaGolem.add(pietreADisposizione.get(pietreScelte[i]));
		}
		
		//creazione del nuovo tamagolem
		TamaGolem nuovoTamagolem = new TamaGolem(pietreDaDareAlTamaGolem, maxPietreIngerite);
		
		//do il tamagolem al giocatore
		giocatori.get(giocatore).setTamaGolemInCampo(nuovoTamagolem);
		
		//controlla se i due tamagolem usano le stesse pietre
		if(nuovoTamagolem.usaLeStessePietre(giocatori.get((giocatore == A) ? B: A).getTamaGolemInCampo())) {
			giocatori.get(giocatore).removeTamagolem();
			return false;
		}
		
		//ora che sono sicuro che le pietre sono dentro il tamagolem le tolgo dalla lista
		pietreADisposizione.removeAll(pietreDaDareAlTamaGolem);
		//e ne diminuisco il numero
		numeroPietreNellaScorta -= maxPietreIngerite;
		//la costruzione e' andata a buon fine :)
		return true;
	}
	
	/**
	 * esegue la fase di setup
	 * @return la frase di inizio partita, ovvero "Inizia il giocatore [nome_giocatore] come giocatore A"
	 */
	public String eseguiSetUp() {
		//generazione equilibrio
		generaEquilibrio();
		//riempio la sorta delle pietre
		resetScortaPietre();
		//tolgo resetto i giocatori
		resetGiocatori();
		
		//scelta del giocatore iniziale
		//tiro a caso un numero, se esce negativo inverto i due giocatori
		if(GeneratoreNumeriCasuali.estraiIntero(-2, 2) < 0) {
			Giocatore temporaneo = giocatori.get(A);
			giocatori.put(A, giocatori.get(B));
			giocatori.put(B, temporaneo);
		}
		
		return A_CAPO + FRASE_INIZIO_PARTE1 + giocatori.get(A).getNome() + FRASE_INIZIO_PARTE2;
	}
	
	/**
	 * esegue il turno, attenzione: esegue solo la parte in cui i tamagolem combattono, non c'e' il controllo sulla morte dei tamagolem ne la loro eventuale rigenerazione
	 * @return la frase che descrive cosa e' successo nel turno
	 */
	public String eseguiTurno() {
		//nuovo turno 
		turno++;
		//frase che mi dice cosa e' accaduto in questo turno
		StringBuffer fraseDiFineTurno = new StringBuffer(TURNO + turno + A_CAPO + A_CAPO);
		
		//per dafault il danneggiante e' il giocatoreA e il danneggiato B
		String giocatoreDanneggiante = A, giocatoreDanneggiato = B;
		String elementoUsatoDanneggiante = giocatori.get(giocatoreDanneggiante).getTamaGolemInCampo().usaPietra();
		String elementoUsatoDanneggiato = giocatori.get(giocatoreDanneggiato).getTamaGolemInCampo().usaPietra();
		
		int potenzaDellAttacco = equilibrio.getPotenzaTraElementi(elementoUsatoDanneggiante, elementoUsatoDanneggiato);
		
		if(potenzaDellAttacco < 0) {
			//se la potenza dell'attacco e' minore di zero significa che
			//B danneggia A del modulo di potenzaDellAttacco danni
			giocatoreDanneggiante = B;
			giocatoreDanneggiato = A;
			//e devo scambiare le pietre utilizzate
			String temporaneo = elementoUsatoDanneggiante;
			elementoUsatoDanneggiante = elementoUsatoDanneggiato;
			elementoUsatoDanneggiato = temporaneo;
		} else{
			//se la potenza dell'attacco e' maggiore di zero significa che
			//A danneggia B di potenzaDellAttacco danni
			//questa è la situazione di defaul quindi nn faccio niente
		}
		
		//ora che ho controllato chi attacca chi la potenza posso prenderla positiva
		potenzaDellAttacco = Math.abs(potenzaDellAttacco);
		giocatori.get(giocatoreDanneggiato).danneggiaTamagolem(potenzaDellAttacco);
		fraseDiFineTurno.append(giocatori.get(giocatoreDanneggiante).getNome()
				+ HA_FERITO_IL_TAMAGOLEM_NEMICO_DI
				+ potenzaDellAttacco
				+ HP_CON_UNA_PIETRA_DI
				+ elementoUsatoDanneggiante
				+ CONTRO_UNA_PIETRA_DI
				+ elementoUsatoDanneggiato);
		
		fraseDiFineTurno.append(A_CAPO);
		fraseDiFineTurno.append(SEPARATORE);
		
		return fraseDiFineTurno.toString();
	}
	
	/**
	 * metodo che esegue il fine partita, ovvero crea una stringa che dice chi ha vinto e qual'era l'equilibrio dell'universo
	 * @return la stringa di ine patita
	 */
	public String eseguiFinePartita() {
		StringBuffer fineDelGioco = new StringBuffer(TITOLO_FINE_PARTITA);
		
		if(checkFinePartita()) {
			if(giocatori.get(A).haAncoraTamaGolemVivi()) {
				fineDelGioco.append(giocatori.get(A).getNome() + HA_VINTO);
			} else {
				fineDelGioco.append(giocatori.get(B).getNome() + HA_VINTO);
			}
		}
		
		fineDelGioco.append(A_CAPO);
		fineDelGioco.append(EQUILIBRIO_DELL_UNIERSO_ERA + getStringEquilibrio());
		
		return fineDelGioco.toString();
	}
	
	/**
	 * da a disposizione la tabella dell'equilibrio
	 * @return la tabella che rappresenta l'equilibrio
	 */
	public String getStringEquilibrio() {
		return equilibrio.toString();
	}
	
	/**
	 * metodo che da sottoforma di elenco le pietre presenti nella scorta
	 * @return l'eleno elle pietre dispnibili
	 */
	public String getStringaPietreDisponibili() {
		StringBuffer listaPietre = new StringBuffer(A_CAPO + LE_PIETRE_DISPONIBILI);
		String elementoPrecedente = "";
		
		for(int i = 0; i < pietreADisposizione.size(); i++) {
			if(elementoPrecedente != pietreADisposizione.get(i).getElemento())
				listaPietre.append(SEPARATORE + A_CAPO);
			listaPietre.append(i + PARENTESI + pietreADisposizione.get(i).getElemento() + A_CAPO);
			elementoPrecedente = pietreADisposizione.get(i).getElemento();
		}
		
		return listaPietre.toString();
	}
	
	/**
	 * metodo che da una stringa con le informazioni sullo stato del gioco, ovvero il turno in cui siamo, i nomi dei giocatori, i loro tamagolem rimanenti,
	 * gli HP dei tamagolem in campo e le pietre utilizzate da ogni tamagolem
	 * @return la string con lo stato del gioco
	 */
	public String getStringaStatoDelGioco() {
		StringBuffer fraseStatoDelGioco = new StringBuffer(TURNO + turno);
		
		fraseStatoDelGioco.append(A_CAPO);
		fraseStatoDelGioco.append(A_CAPO);
		
		String AB[] = {A,B};
		
		for(String giocatore: AB) {
			fraseStatoDelGioco.append(giocatori.get(giocatore).getNome());
			fraseStatoDelGioco.append(A_CAPO);
			fraseStatoDelGioco.append(TAMAGOLEM_RIMANENTI + giocatori.get(giocatore).getTamaGolemRimanenti());
			fraseStatoDelGioco.append(A_CAPO);
			fraseStatoDelGioco.append(HP_GOLEM + giocatori.get(giocatore).getVitaTamagolem());
			fraseStatoDelGioco.append(A_CAPO);
			fraseStatoDelGioco.append(PIETRE_UTILIZZATE + giocatori.get(giocatore).getStringaPietreMostrate());
			
			fraseStatoDelGioco.append(A_CAPO);
			fraseStatoDelGioco.append(SEPARATORE);
			fraseStatoDelGioco.append(A_CAPO);
		}
		
		return fraseStatoDelGioco.toString();
	}
	
	/**
	 * metrodo che da la stringa da stampare quando si avvia il programma, essa da il bevenuto ai giocatori, elenca gli elementi nell'universo, il numero di tamagolem per giocatore
	 * e quante pietre sono ingerite da ogni tamagolem
	 * @return la stringa con informazioni sul gioco
	 */
	public String getStringaInfoGioco() {
		StringBuffer info = new StringBuffer();
		
		info.append(A_CAPO);
		
		info.append(FRASE_BENVENUTO);
		
		info.append(A_CAPO);
		info.append(A_CAPO);
		
		info.append(GLI_ELEMENTI_CHE_GOVERNANO_SONO 
				+ A_CAPO
				+ equilibrio.getEementiDiEquilirioComeElenco()
				+ A_CAPO);
		
		info.append(OGNI_GIOCATORE_HA_A_SUA_DISPOSIZIONE
				+ maxTamagolemPerGiocatore
				+ TAMAGOLEM_OGNUNO_DEI_QUALI_PUO_INGOIARE
				+ maxPietreIngerite
				+ PIETRE
				+ A_CAPO
				+ A_CAPO);
		
		info.append(VINCE_IL_GIOCATORE_CHE
				+ A_CAPO
				+ SEPARATORE + SEPARATORE);
		
		return info.toString();
	}
	
	/**
	 * @return il massimo numero di Tamagolem Per Giocatore
	 */
	public int getMaxTamagolemPerGiocatore() {
		return maxTamagolemPerGiocatore;
	}

	/**
	 * @return il massimo numero di Pietre Ingerite
	 */
	public int getMaxPietreIngerite() {
		return maxPietreIngerite;
	}

	/**
	 * @return le pietrePerElemento
	 */
	public int getPietrePerElemento() {
		return pietrePerElemento;
	}

	/**
	 * @return il maxPietreNellaScorta
	 */
	public int getNumeroPietreNellaScorta() {
		return numeroPietreNellaScorta;
	}

	/**
	 * @return il turno
	 */
	public int getTurno() {
		return turno;
	}

	/**
	 * @return i giocatori
	 */
	public Giocatore getGiocatore(String qualeGiocatore) {
		if(qualeGiocatore == A || qualeGiocatore == B)
			return giocatori.get(qualeGiocatore);
		return null;
	}
	
	/**
	 * metodo per sapere a chi appartiene il tamagolem morto
	 * @return il codice del giocatore cl tamagolem morto o una stringa vuota se ientrambi i tamagolem on vivi
	 */
	public String getCodiceGiocatoreConTamagolemMorto() {
		
		if(!giocatori.get(A).isTamaGolemVivo())
			return A;
		else if(!giocatori.get(B).isTamaGolemVivo())
			return B;
		
		return "";
	}
	
	/**
	 * resetta la scorta delle pietre e il numero delle pietre rimanenti nella scorta
	 */
	public void resetScortaPietre() {
		this.pietreADisposizione.clear();
		//setto le pietre a disposizione
		for(int i = 0; i < numeroElementi; i++)
			for(int j = 0; j < pietrePerElemento; j++)
				this.pietreADisposizione.add(new Pietra(equilibrio.getElementiDiEquilibrio(i)));
		
		//setto il numero delle pietre nella scorta 
		numeroPietreNellaScorta = maxNumeroPietreNellaScorta;
	}
	
	/**
	 * resetta i giocatori ridandogli il numero massimo di tamagolem come tamagolem rimanenti e rimuovendo possibili tamagolem in campo
	 */
	public void resetGiocatori() {
		giocatori.get(A).setTamaGolemRimanenti(maxTamagolemPerGiocatore);
		giocatori.get(B).setTamaGolemRimanenti(maxTamagolemPerGiocatore);
		giocatori.get(A).removeTamagolem();
		giocatori.get(B).removeTamagolem();
	}

	/**
	 * controlla se la partita e' finita, ovvero se uno dei due giocatori non ha pi� tamagolem in vita
	 * @return vero se la partita deve finire, falso altrimenti
	 */
	public boolean checkFinePartita() {
		return !giocatori.get(A).haAncoraTamaGolemVivi() || !giocatori.get(B).haAncoraTamaGolemVivi();
	}
	
	/**
	 * controlla se uno dei giocatori ha un tamagolem morto
	 * @return vero se c'e' un tamaglem morto in campo
	 */
	public boolean checkTamagolemMorti() {
		return !giocatori.get(A).isTamaGolemVivo() || !giocatori.get(B).isTamaGolemVivo();
	}
	
	/**
	 * metodo per debugging o cheattare, forza uno dei giocatori a perdere la partita
	 * @param giocatore - il giocatore da far perdere
	 */
	public void forzaSconfitta(String giocatore) {
		if(giocatore == A || giocatore == B)
			giocatori.get(giocatore).forzaSconfitta();
	}
}