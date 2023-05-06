package main;

import java.util.*;

public class Partita {
	
	private static final String LE_PIETRE_DISPONIBILI = "Le pietre disponibili:\n\n";
	private static final String CONTRO_UNA_PIETRA_DI = " contro una pietra di ";
	private static final String TURNO = "Turno: ";
	private static final String HA_PERSO = " ha perso";
	private static final String TAMAGOLEM_RIMANENTI = "Tamagolem rimanenti: ";
	private static final String PARENTESI = ") ";
	private static final String SEPARATORE = "------";
	private static final String PIETRE_UTILIZZATE = "Pietre utilizzate: ";
	private static final String A_CAPO = "\n";
	private static final String HP_CON_UNA_PIETRA_DI = "HP usando una pietra di ";
	private static final String HA_FERITO_IL_TAMAGOLEM_NEMICO_DI = " ha ferito il tamagolem nemico di ";
	private static final String HP_GOLEM = "HP del tamagolem ";
	private static final String A = "A", B = "B";
	private static final String FRASE_INIZIO_PARTE1 = "Inizia il giocatore ";
	private static final String FRASE_INIZIO_PARTE2 = " come giocatore A.";
	
	//il numero di tamagolem con cui ogni giocatore inizia
	private int maxTamagolemPerGiocatore;
	//il numero di pietre che ogni tamagolem può mangiare
	private int maxPietreIngerite;
	//il numero delle pietre per singolo elemento nella scorta comune
	private int pietrePerElemento;
	//il numero delle pietre nella scorta comune
	private int maxPietreNellaScorta;
	
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
		int numeroElementi = equilibrio.getNumeroElementi();

		//calcolo del numero di pietre che ogni tamagolem può mangiare
		maxPietreIngerite = (Math.floorDiv(numeroElementi + 1, 3) + 1);
		//calcolo dei tamagolem per giocatore
		maxTamagolemPerGiocatore
			= (Math.floorDiv((numeroElementi - 1)*(numeroElementi - 2), 2 * maxPietreIngerite) + 1);
		//calcolo delle pietre totali nella scorta
		maxPietreNellaScorta
			= (Math.floorDiv(2 * maxTamagolemPerGiocatore * maxPietreIngerite, numeroElementi) + 1) * numeroElementi;
		//calcolo del numero di pietre per elemento nella scorta comune
		pietrePerElemento
			= (Math.floorDiv(2 * maxTamagolemPerGiocatore * maxPietreIngerite, numeroElementi) + 1);
		
		//setto le pietre a disposizione
		for(int i = 0; i < numeroElementi; i++)
			for(int j = 0; j < pietrePerElemento; j++)
				this.pietreADisposizione.add(new Pietra(equilibrio.getElementiDiEquilibrio(i)));
		
		//setto l'equilibrio
		this.equilibrio = equilibrio;
		
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
		
		pietreADisposizione.removeAll(pietreDaDareAlTamaGolem);
		
		//creazione del nuovo tamagolem
		TamaGolem nuovoTamagolem = new TamaGolem(pietreDaDareAlTamaGolem, maxPietreIngerite);
		
		//do il tamagolem al giocatore
		giocatori.get(giocatore).setTamaGolemInCampo(nuovoTamagolem);
		
		//controlla se i due tamagolem usano le stesse pietre
		if(nuovoTamagolem.usaLeStessePietre(giocatori.get((giocatore == A) ? B: A).getTamaGolemInCampo())) {
			giocatori.get(giocatore).removeTamagolem();
			return false;
		}
		
		//la costruzione è andata a buon fine :)
		return true;
	}
	
	/**
	 * esegue la fase di setup
	 * @return la frase di inizio partita, ovvero "Inizia il giocatore [nome_giocatore] come giocatore A"
	 */
	public String eseguiSetUp() {
		//generazione equilibrio
		generaEquilibrio();
		
		//scelta del giocatore iniziale
		//tiro a caso un numero, se esce negativo inverto i due giocatori
		if(GeneratoreNumeriCasuali.estraiIntero(-2, 2) < 0) {
			Giocatore temporaneo = giocatori.get(A);
			giocatori.put(A, giocatori.get(B));
			giocatori.put(B, temporaneo);
		}
		
		return FRASE_INIZIO_PARTE1 + giocatori.get(A).getNome() + FRASE_INIZIO_PARTE2;
	}
	
	/**
	 * esegue il turno, attenzione: esegue solo la parte in cui i tamagolem combattono, non c'è il controllo sulla morte dei tamagolem ne la loro eventuale rigenerazione
	 * @return la frase che descrive cosa è successo nel turno
	 */
	public String eseguiTurno() {
		//nuovo turno 
		turno++;
		//frase che mi dice cosa è accaduto in questo turno
		StringBuffer fraseDiFineTurno = new StringBuffer(TURNO + turno + A_CAPO + A_CAPO);
		
		//per dafault il danneggiante è il giocatoreA e il danneggiato B
		String giocatoreDanneggiante = A, giocatoreDanneggiato = B;
		String elementoUsatoDannegiante = giocatori.get(giocatoreDanneggiante).getTamaGolemInCampo().usaPietra();
		String elementoUsatoDanneggiato = giocatori.get(giocatoreDanneggiato).getTamaGolemInCampo().usaPietra();
		
		int potenzaDellAttacco = equilibrio.getPotenzaTraElementi(elementoUsatoDannegiante, elementoUsatoDanneggiato);
		
		if(potenzaDellAttacco == 0) {
			//se ho la potenza d'attacco zero non dovrei fare niente ma il problema si presenta quando i due
			//tamagolem hanno le stesse pietre sputate nello stesso ordine.
			//Nel caso stessePietre-stessoOrdine andrei in un ciclo infinito
			//TODO: fare qualcosa a riguardo
		}
		
		if(potenzaDellAttacco < 0) {
			//se la potenza dell'attacco è minore di zero significa che
			//B danneggia A del modulo di potenzaDellAttacco danni
			giocatoreDanneggiante = B;
			giocatoreDanneggiato = A;
			//e devo scambiare le pietre utilizzate
			String temporaneo = elementoUsatoDannegiante;
			elementoUsatoDannegiante = elementoUsatoDanneggiato;
			elementoUsatoDanneggiato = temporaneo;
		} else if(potenzaDellAttacco > 0){
			//se la potenza dell'attacco è maggiore di zero significa che
			//A danneggia B di potenzaDellAttacco danni
			//questa è la situazione di defaul quindi nn faccio niente
		} else {
		}
		
		//ora che ho controllato chi attacca chi la potenza posso prenderla positiva
		potenzaDellAttacco = Math.abs(potenzaDellAttacco);
		giocatori.get(giocatoreDanneggiante).danneggiaTamagolem(potenzaDellAttacco);
		fraseDiFineTurno.append(giocatori.get(giocatoreDanneggiato).getNome()
				+ HA_FERITO_IL_TAMAGOLEM_NEMICO_DI
				+ potenzaDellAttacco
				+ HP_CON_UNA_PIETRA_DI
				+ elementoUsatoDannegiante
				+ CONTRO_UNA_PIETRA_DI
				+ elementoUsatoDanneggiato);
		
		fraseDiFineTurno.append(A_CAPO);
		fraseDiFineTurno.append(SEPARATORE);
		
		return fraseDiFineTurno.toString();
	}
	
	public String eseguiFinePartita() {
		if(checkFinePartita()) {
			if(!giocatori.get(A).haAncoraTamaGolemVivi()) {
				return giocatori.get(A).getNome() + HA_PERSO;
			} else {
				return giocatori.get(B).getNome() + HA_PERSO;
			}
		}
		return "";
	}
	
	public String getEquilibrioAsString() {
		return equilibrio.toString();
	}
	
	public String getStringaPietreDisponibili() {
		StringBuffer listaPietre = new StringBuffer(LE_PIETRE_DISPONIBILI);
		
		for(int i = 0; i < pietreADisposizione.size(); i++) {
			listaPietre.append(i + PARENTESI + pietreADisposizione.get(i).getElemento() + A_CAPO);
		}
		
		return listaPietre.toString();
	}
	
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
			fraseStatoDelGioco.append(PIETRE_UTILIZZATE + giocatori.get(giocatore).getStringaPietreMostrate().toString());
			
			fraseStatoDelGioco.append(A_CAPO);
			fraseStatoDelGioco.append(SEPARATORE);
			fraseStatoDelGioco.append(A_CAPO);
		}
		
		return fraseStatoDelGioco.toString();
	}
	
	
	
	/**
	 * @return the maxTamagolemPerGiocatore
	 */
	public int getMaxTamagolemPerGiocatore() {
		return maxTamagolemPerGiocatore;
	}

	/**
	 * @return the maxPietreIngerite
	 */
	public int getMaxPietreIngerite() {
		return maxPietreIngerite;
	}

	/**
	 * @return the pietrePerElemento
	 */
	public int getPietrePerElemento() {
		return pietrePerElemento;
	}

	/**
	 * @return the maxPietreNellaScorta
	 */
	public int getMaxPietreNellaScorta() {
		return maxPietreNellaScorta;
	}

	/**
	 * @return the turno
	 */
	public int getTurno() {
		return turno;
	}

	public boolean checkFinePartita() {
		return !giocatori.get(A).haAncoraTamaGolemVivi() || !giocatori.get(B).haAncoraTamaGolemVivi();
	}
	
	public boolean checkTamagolemMorti() {
		return giocatori.get(A).isTamaGolemVivo() || giocatori.get(B).isTamaGolemVivo();
	}
	
	public void forzaSconfitta(String giocatore) {
		if(giocatore == A || giocatore == B)
			giocatori.get(giocatore).forzaSconfitta();
	}
}