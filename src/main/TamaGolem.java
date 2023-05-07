package main;

import java.util.ArrayList;

public class TamaGolem {
	
	private static final String ELEMENTO_SCONOSCIUTO = "(???),";
	private static final String PARENTESI_APERTA = "(", PARENTESI_CHIUSA = "),";
	public static final int MAX_VITA = 20;
	
	
	private static int maxPietreIngerite;
	private int vita = MAX_VITA;
	private Pietra pietre[] = new Pietra[maxPietreIngerite];
	private int pietraDaUsare = 0;
	private int numeroPietreMostrate = 0;
	
	TamaGolem(int maxPietreIngerite){
		vita = MAX_VITA;
		TamaGolem.maxPietreIngerite = maxPietreIngerite;
		pietre = new Pietra[maxPietreIngerite];
		pietraDaUsare = 0;
		numeroPietreMostrate = 0;
	}
	
	TamaGolem(Pietra pietre[], int maxPietreIngerite){
		TamaGolem.maxPietreIngerite = maxPietreIngerite;
		this.pietre = pietre;
	}
	
	TamaGolem(ArrayList<Pietra> pietre, int maxPietreIngerite){
		TamaGolem.maxPietreIngerite = maxPietreIngerite;
		this.pietre = pietre.toArray(new Pietra[0]);
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
		return maxPietreIngerite;
	}

	public Pietra[] getPietre() {
		return pietre;
	}
	
	public Pietra[] getPietreMostrate() {
		Pietra[] pietreMostrate = new Pietra[maxPietreIngerite];
		
		for(int i = 0; i < maxPietreIngerite; i++) {
			if(i < this.numeroPietreMostrate) {
				pietreMostrate[i] = pietre[i];
			} else {
				pietreMostrate[i] = new Pietra(ELEMENTO_SCONOSCIUTO);
			}
		}
		
		return pietreMostrate;
	}
	
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
	public boolean usaLeStessePietre(TamaGolem tamagolem) {
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