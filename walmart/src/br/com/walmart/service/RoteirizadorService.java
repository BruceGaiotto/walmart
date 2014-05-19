package br.com.walmart.service;

import java.util.List;

import br.com.walmart.Rota;
import br.com.walmart.Trecho;
import br.com.walmart.dao.TrechoDaoImplMemory;

public class RoteirizadorService {
	
	//@Autowired
	private TrechoDaoImplMemory trechoDao = new TrechoDaoImplMemory();

	public Rota calculaRota(Trecho trechoRota){
		Rota rota = new Rota();

		List<Trecho> trechos = this.trechoDao.findAll();
		
		/**
		 * Se a rota a ser calculada ja for um dos trechos existentes, retorna o proprio trecho.
		 * Obs. considerando-se que um trecho eh a menor distancia entre 2 pontos.
		 */
		if(trechos.contains(trechoRota)){
			trechoRota = trechos.get(trechos.indexOf(trechoRota));
			rota.add(trechoRota);
			return rota;
		}
		
		List<Trecho> trechosOrigem = trechoDao.findTrechoByOrigem(trechoRota.getOrigem());
		for(Trecho trecho : trechosOrigem) {
			rota = new Rota();
			rota.add(trecho);
		}
		
		return null;
	}

}
