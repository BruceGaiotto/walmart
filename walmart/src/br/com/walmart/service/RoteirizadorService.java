package br.com.walmart.service;

import java.util.List;

import br.com.walmart.Rota;
import br.com.walmart.Trecho;
import br.com.walmart.dao.TrechoDaoImplMemory;

public class RoteirizadorService {

    // @Autowired
    private TrechoDaoImplMemory trechoDao = new TrechoDaoImplMemory();

    public void calculaRota(String origem, String destino) {
	Trecho trechoRota = new Trecho(origem, destino);

	Rota rota = new Rota();
	rota.setOrigem(origem);
	Rota.setDestinoSolicitado(destino);
	Rota.setTrechoDao(new TrechoDaoImplMemory());

	List<Trecho> trechos = this.trechoDao.findAll();
	if (trechos.contains(trechoRota)) {
	    /**
	     * Considerando que os trechos sao unicos, desta maneira nao havera dois indices.
	     */
	    Trecho trechoSolucao = trechos.get(trechos.indexOf(trechoRota));
	    Rota.setDistanciaSolucao(trechoSolucao.getDistancia());

	    Rota primeiraSolucao = new Rota();
	    primeiraSolucao.add(trechoSolucao);
	    System.out.println("Primeira solução encontrada: " + primeiraSolucao);
	}

	/**
	 * Continua procurando por rotas mais curtas ainda que passem por mais pontos.
	 */
	Thread thread = new Thread(rota,""+trechoRota);
	thread.start();
    }

}
