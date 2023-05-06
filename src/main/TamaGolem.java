package main;

public class TamaGolem {
	
	private static final String PUNTI_DI_DOMANDA = "(???),";
	private static final String PARENTESI_APERTA = "(", PARENTESI_CHIUSA = ")";
	public static final int MAX_VITA = 30;
	public static final int MAX_PIETRE_INSERITE = 3;
	
	private int vita = MAX_VITA;
	private Pietra pietre[] = new Pietra[MAX_PIETRE_INSERITE];
	private int pietraDaUsare = 0;
	private int numeroPietreMostrate = 0;
	

	TamaGolem(){
		vita = MAX_VITA;
		pietre = new Pietra[MAX_PIETRE_INSERITE];
		pietraDaUsare = 0;
		numeroPietreMostrate = 0;
	}
	
	TamaGolem(Pietra pietre[]){
		this.pietre = pietre;
	}
	
	//***
	
	public void setVita(int vita) {
		this.vita = vita;
	}
	
	public void setPietraDaUsare(int pietraDaUsare) {
		this.pietraDaUsare = pietraDaUsare;
	}
	
	public void setPietre(Pietra[] pietre) {
		this.pietre = pietre;
	}
	
	//***

	public int getVita() {
		return vita;
	}

	public static int getMAX_VITA() {
		return MAX_VITA;
	}

	public static int getMAX_PIETRE_INSERITE() {
		return MAX_PIETRE_INSERITE;
	}

	public Pietra[] getPietre() {
		return pietre;
	}
	
	public Pietra[] getPietreMostrate() {
		Pietra[] pietreMostrate = new Pietra[MAX_PIETRE_INSERITE];
		
		for(int i = 0; i < MAX_PIETRE_INSERITE; i++) {
			if(i < this.numeroPietreMostrate) {
				pietreMostrate[i] = pietre[i];
			} else {
				pietreMostrate[i] = new Pietra(PUNTI_DI_DOMANDA);
			}
		}
		
		return pietreMostrate;
	}
	
	public String getStringaPietreMostrate() {
		StringBuffer pietreMostrate = new StringBuffer();
		
		for(int i = 0; i < MAX_PIETRE_INSERITE; i++) {
			if(i < this.numeroPietreMostrate) {
				pietreMostrate.append(PARENTESI_APERTA + pietre[i].getElemento() + PARENTESI_CHIUSA);
			} else {
				pietreMostrate.append(PUNTI_DI_DOMANDA);
			}
		}
		
		return pietreMostrate.;
	}
	
	public int getPietraDaUsare() {
		return pietraDaUsare;
	}
	
	//***
	
	public int danneggia(int danno) {
		setVita(getVita() - Math.abs(danno));
		return getVita();
	}
	
	public boolean isVivo() {
		return getVita() > 0;
	}
	
	public String usaPietra() {
		String elementoDellaPietra = pietre[pietraDaUsare].getElemento();
		pietraDaUsare++;
		if(numeroPietreMostrate < MAX_PIETRE_INSERITE)
			numeroPietreMostrate++;
		pietraDaUsare %= MAX_PIETRE_INSERITE;
		return elementoDellaPietra;
	}
	
	//***
	
	/**
	 * questo metodo serve per verificare che due tamagolem USINO (non hanno) le stesse pietre nello stesso momento, se il metodo ritorna vero significa che i tamagolem non si faranno danno durante lo scontro.
	 * Il controllo avviene usando una ad una le pietre usate dai tamagolem e controllare che siano uguali
	 * @param tamagolem - il tamagolem da confrontare
	 * @return true se le pietre sono usate ugualmente, false altrimenti
	 */
	public boolean usaLeStessePietre(TamaGolem tamagolem) {
		if(tamagolem == null)
			return false;
		
		for(int i = 0; i < MAX_PIETRE_INSERITE; i++) {
			if(!this.usaPietra().equals(tamagolem.usaPietra()))
				return false;
		}
		
		return true;
	}
}