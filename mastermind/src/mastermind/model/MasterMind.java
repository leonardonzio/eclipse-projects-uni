package mastermind.model;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class MasterMind {

	private Combinazione segreta;
	private int size;
	
	public MasterMind(int lunghezzaCodice) {
		this.segreta = new Combinazione(lunghezzaCodice);
		this.size = lunghezzaCodice;
		sorteggiaCombinazione(this.segreta);
	}
	
	protected void sorteggiaCombinazione(Combinazione segreta) {
		Random nRand = new Random();
		
		for (int i = 0; i < size; i++) {
			int randomIndex = nRand.nextInt(PioloDiGioco.values().length - 1) + 1; // da 1 a 6
			segreta.setPiolo(i, PioloDiGioco.values()[randomIndex]);
		}
	}

	public Risposta calcolaRisposta(Combinazione tentativo) {		
		int neri = 0;
		for (int i=0; i < tentativo.dim(); i++) {
			if (tentativo.getPiolo(i).equals(segreta.getPiolo(i)))
				neri++;
		}
		
		var occorrenzeSeg = getOccorrenze(this.segreta);
		var occorrenzeTent = getOccorrenze(tentativo);
		
		int totali = 0;
		for (PioloDiGioco p : PioloDiGioco.values()) {
			totali += Math.min(occorrenzeSeg.get(p), occorrenzeTent.get(p));
		}			
		var res = new Risposta(this.size);
		
		// inserisco i neri
		for (int i = 0; i < neri; i++) {
			res.setPiolo(i, PioloRisposta.NERO);
		}
		
		// inserisco i bianchi
		for (int i = neri; i < totali; i++) {
			res.setPiolo(i, PioloRisposta.BIANCO);
		}
		
		return res;
	}
	
	
	public String combinazioneSegreta() {
		return segreta.toString();
	}
	
	private int quantiNeri (Combinazione tentativo) {
		int neri = 0;
		for (int i = 0; i < tentativo.dim();) {
			PioloDiGioco pioloSeg = this.segreta.getPiolo(i);
			PioloDiGioco pioloTent = tentativo.getPiolo(i);
			if(pioloSeg.equals(pioloTent)) {
				neri++;
			}
		}
		
		return neri;
	}
	
	private Map<PioloDiGioco, Integer> getOccorrenze(Combinazione tentativo) {
		var occorrenze = new HashMap<PioloDiGioco, Integer>();
		
		for (PioloDiGioco p : PioloDiGioco.values()) {
			occorrenze.put(p, 0);
		}
		
		for (PioloDiGioco p : tentativo.getCombinazione()) {
			Integer frequenza = occorrenze.get(p);
			occorrenze.put(p, frequenza + 1);
		}
		return occorrenze;

		/* vecchia soluzione
		int size = PioloDiGioco.values().length;
		int[] arrOcc = new int[size];
		Arrays.fill(arrOcc, 0);

		// ciclo tentativo
		for (int i = 0; i < tentativo.dim(); i++) {
			PioloDiGioco pioloAttuale = tentativo.getPiolo(i);
			
			// ciclo arrOcc
			for (int j = 0; j < size; j++) {
				if(pioloAttuale.ordinal() == j) {
					arrOcc[j]++;
				}
			}
		}
		
		return arrOcc;
		*/
		
		///////////////////
		
		
		/* soluzione prof:


		int[] arrayOccorrenze = new int[PioloDiGioco.values().length];
		for (int i=0; i<PioloDiGioco.values().length; i++)
			arrayOccorrenze[i] = 0;
		for (int i=0; i<tentativo.dim(); i++) {
			PioloDiGioco pioloAttuale = tentativo.getPiolo(i);
			int frequenzaAttuale = arrayOccorrenze[pioloAttuale.ordinal()];
			arrayOccorrenze[pioloAttuale.ordinal()] = frequenzaAttuale+1;
		}
		return arrayOccorrenze;
		*/
	}
}
