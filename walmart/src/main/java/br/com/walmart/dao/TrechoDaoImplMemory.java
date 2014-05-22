package br.com.walmart.dao;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import br.com.walmart.Trecho;

/**
 * Implementação temporária (utilizada para testes) do Dao com os dados de {@link Trecho} armazenados em memória.
 *  
 */
public class TrechoDaoImplMemory {
	
	private List<Trecho> trechos;
	
	public TrechoDaoImplMemory() {
		this.trechos = new ArrayList<Trecho>();
		
		trechos.add(new Trecho("A","B",10));
		trechos.add(new Trecho("A","F",1));
		trechos.add(new Trecho("F","G",1));
		trechos.add(new Trecho("G","H",1));
		trechos.add(new Trecho("H","B",1));
		trechos.add(new Trecho("B","D",15));
		trechos.add(new Trecho("A","C",20));
		trechos.add(new Trecho("C","D",30));
		trechos.add(new Trecho("B","E",50));
		trechos.add(new Trecho("D","E",30));
	}

	public List<Trecho> findAll() {
		return this.trechos;
	}
	
	public List<Trecho> findTrechoByOrigem(final String origem) {
		List<Trecho> trechos = new ArrayList<Trecho>();
		
		Iterator<Trecho> itTrecho = this.trechos.iterator();
		while(itTrecho.hasNext()){
			Trecho trecho = itTrecho.next();
			if(origem.equals(trecho.getOrigem())){
				trechos.add(trecho);
			}
		}
		
		return trechos;
	}
	
	public List<Trecho> findTrechoByDestino(final String destino) {
		List<Trecho> trechos = new ArrayList<Trecho>();
		
		Iterator<Trecho> itTrecho = this.trechos.iterator();
		while(itTrecho.hasNext()){
			Trecho trecho = itTrecho.next();
			if(destino.equals(trecho.getDestino())){
				trechos.add(trecho);
			}
		}
		
		return trechos;
	}
	
}
