package main;

import java.util.ArrayList;

/**
 * Questa classe rappresenta il tamagolem
 */
public class TamaGolem {
	
	//costanti per metodi che restituiscono stringhe
	private static final String ELEMENTO_SCONOSCIUTO = "(???),";
	private static final String PARENTESI_APERTA = "(", PARENTESI_CHIUSA = "),";
	
	//la vita massima del golem, scelta arbitraria
	public static final int MAX_VITA = 15;
	
	//il numero massimo di pietre ingerite, non e' una costante perche' deve essere calcolata in base al numero
	//di elementi nell'universo
	private static int maxPietreIngerite;
	//la vita del tamagolem
	private int vita = MAX_VITA;
	//le pietre ingerite dal golem
	private Pietra pietre[] = new Pietra[maxPietreIngerite];
	//il numero della pietra che deve essere utilizzata
	private int pietraDaUsare = 0;
	//quante pietre ha gia' usato e che quindi anche l'avversario sa
	private int numeroPietreMostrate = 0;
	
	/**
	 * costruttore con solo in input solo il numero massimo di pietre ingerite
	 * le pietre ingerite dovranno essere inserite in seguito
	 * @param maxPietreIngerite - il massimo delle pietre ingerite
	 */
	TamaGolem(int maxPietreIngerite){
		vita = MAX_VITA;
		TamaGolem.maxPietreIngerite = maxPietreIngerite;
		pietre = new Pietra[maxPietreIngerite];
		pietraDaUsare = 0;
		numeroPietreMostrate = 0;
	}
	
	/**
	 * construttore con la lista di pietre in input come array di Pietre
	 * @param pietre - le pietre da ingerire
	 * @param maxPietreIngerite - il massimo delle pietre ingerite
	 */
	TamaGolem(Pietra pietre[], int maxPietreIngerite){
		TamaGolem.maxPietreIngerite = maxPietreIngerite;
		this.pietre = pietre;
	}
	
	/**
	 * construttore con la lista di pietre in input come ArrayList di Pietre
	 * @param pietre - le pietre da ingerire
	 * @param maxPietreIngerite - il massimo delle pietre ingerite
	 */
	TamaGolem(ArrayList<Pietra> pietre, int maxPietreIngerite){
		TamaGolem.maxPietreIngerite = maxPietreIngerite;
		this.pietre = pietre.toArray(new Pietra[0]);
	}
	
	//***
	
	/**
	 * setter della vita
	 * @param vita - la vita che si vuol far assumere al tamagolem
	 */
	public void setVita(int vita) {
		this.vita = vita;
	}
	
	/**
	 * setta la pietra che il tamagolem deve usare per attaccare
	 * @param pietraDaUsare - la posizione della pietra da usare
	 */
	public void setPietraDaUsare(int pietraDaUsare) {
		this.pietraDaUsare = pietraDaUsare;
	}
	
	/**
	 * setta l'array delle pietre ingerite
	 * @param pietre - il nuovo array di pietre
	 */
	public void setPietre(Pietra[] pietre) {
		this.pietre = pietre;
	}
	
	//***

	/**
	 * @return la vita rimanente al tamagolem
	 */
	public int getVita() {
		return vita;
	}

	/**
	 * @return il numero massimo di pietre che il golem puo' ingerire
	 */
	public static int getMaxPietreIngerite() {
		return maxPietreIngerite;
	}

	/**
	 * @return l'array delle pietre ingerite
	 */
	public Pietra[] getPietre() {
		return pietre;
	}
	
	/**
	 * metodo che ritorna una stringa con scritti gli elementi delle pietre con la particolarita di scrivere
	 * l'elemento della pietra solo se e' gia' stata usata.
	 * Ritorna solo le pietre utilizzate perche' se voglio mostrare quali pietre ha un giocatore
	 * @return una stringa con gli elementi delle pietre usate fino al momento dell'invocazione
	 */
	public String getStringaPietreMostrate() {
		StringBuffer pietreMostrate = new StringBuffer();
		
		for(int i = 0; i < maxPietreIngerite; i++) {
			if(i < this.numeroPietreMostrate) {
				pietreMostrate.append(PARENTESI_APERTA + pietre[i].getElemento() + PARENTESI_CHIUSA);
			} else {
				pietreMostrate.append(ELEMENTO_SCONOSCIUTO);
			}
		}
		
		return pietreMostrate.deleteCharAt(pietreMostrate.length() - 1).toString();
	}
	
	/**
	 * la pietra che il golem sta per usare
	 * @return la pietra da usare
	 */
	public int getPietraDaUsare() {
		return pietraDaUsare;
	}
	
	//***
	
	/**
	 * metodo che toglie dalla vita il valore richiesto preso positivo
	 * e se il valore della vita e' negativo lo setta a zero.
	 * @param danno - il valore che voglio togliere alla vita
	 * @return la vita rimanente
	 */
	public int danneggia(int danno) {
		setVita(Math.max(getVita() - Math.abs(danno), 0));
		return getVita();
	}
	
	/**
	 * @return vero se il tamagolem e' vivo, falso se e' morto
	 */
	public boolean isVivo() {
		return getVita() > 0;
	}
	
	/**
	 * restituisce l'elemento della pietra che bisogna usare e passa alla pietra dopo
	 * l'elemento ritornato e' quello della pietra PRIMA che la rotazione delle pietre avviene
	 * @return l'elemento della pietra utilizzata
	 */
	public String usaPietra() {
		String elementoDellaPietra = pietre[pietraDaUsare].getElemento();
		pietraDaUsare++;
		if(numeroPietreMostrate < maxPietreIngerite)
			numeroPietreMostrate++;
		pietraDaUsare %= maxPietreIngerite;
		return elementoDellaPietra;
	}
	
	//***
	
	/**
	 * questo metodo serve per verificare che due tamagolem USINO (non hanno) le stesse pietre nello stesso momento, se il metodo ritorna vero significa che i tamagolem non si faranno danno durante lo scontro.
	 * Il controllo avviene usando una ad una le pietre usate dai tamagolem e controllare che siano uguali
	 * @param tamagolem - il tamagolem da confrontare
	 * @return true se le pietre sono usate ugualmente, false altrimenti
	 */
	public boolean usaLeStessePietreDi(TamaGolem tamagolem) {
		if(tamagolem == null)
			return false;
		
		boolean usanoPietreUguali = true;
		
		for(int i = 0; i < maxPietreIngerite; i++) {
			if(!this.usaPietra().equals(tamagolem.usaPietra()))
				usanoPietreUguali = false;
		}
		
		return usanoPietreUguali;
	}
}