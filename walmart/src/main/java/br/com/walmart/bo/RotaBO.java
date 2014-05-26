package br.com.walmart.bo;

import java.util.List;
import java.util.Set;

import br.com.walmart.Rota;
import br.com.walmart.Trecho;
import br.com.walmart.dao.TrechoDaoImplMemory;

public class RotaBO extends Rota implements Runnable {

    private static final long serialVersionUID = 7707545200017447641L;
    
    /**
     * Atributo a ser compartilhado entre todas as Threads a fim de comparacao e
     * gatilho para a nao continuidade das proximas Threads.
     */
    private static Integer distanciaMelhorSolucao;
    /**
     * Caminho percorrido na melhor solucao.
     */
    private static String caminhoMelhorSolucao;
    /**
     * Número máximo de pontos que deverá conter a rota final (solução).<br>
     * Utilizado neste caso como mais um ponto de parada para o processo recursivo de busca.
     */
    private static Integer numeroMaximoTrechosRotaFinal;
    /**
     * Destino final do percurso solicitado.
     */
    private static String destinoSolicitado;

    private static TrechoDaoImplMemory trechoDao;
    
    /**
     * Origem do próximo trecho para continuidade da rota.<br>
     * Não representa a origem do percurso solicitado.
     */
    private String origemProximoTrecho;
    /**
     * Conjunto com os nomes das Threads que estao em execucao.<br>
     * Utilizado pela Thread principal para aguardar o processamento de todos os as threads/trechos.
     */
    private static Set<String> threadsEmExecucao;
    
    static {
	RotaBO.setDefaultConfigurations();
    }

    @Override
    public void run() {
	List<Trecho> trechosOrigem = RotaBO.trechoDao.findTrechoByOrigem(this.origemProximoTrecho);
	for (Trecho trecho : trechosOrigem) {
	    if (this.distanciaPercorrida < RotaBO.distanciaMelhorSolucao && trecho.getDistancia() < RotaBO.distanciaMelhorSolucao) {
		RotaBO rotaBO = (RotaBO) this.clone();
		rotaBO.add(trecho);

		if (RotaBO.destinoSolicitado.equals(trecho.getDestino())) {
		    RotaBO.setMelhorSolucaoAteAgora(rotaBO.caminhoPercorrido, rotaBO.distanciaPercorrida);
		} else {
		    /**
		     * Ponto de prosseguimento/parada da recursao!
		     */
		    if (rotaBO.size() < RotaBO.numeroMaximoTrechosRotaFinal) {
			rotaBO.setOrigemProximoTrecho(trecho.getDestino());

			//Rota.getThreadsEmExecucao().add(rota.getCaminhoPercorrido());
			//rota.run();
			Thread thread = new Thread(rotaBO, rotaBO.getCaminhoPercorrido());
			thread.start();
			RotaBO.getThreadsEmExecucao().add(thread.getName());
		    }
		}
	    }
	}
	RotaBO.getThreadsEmExecucao().remove(Thread.currentThread().getName());
	//Rota.getThreadsEmExecucao().remove(this.getCaminhoPercorrido());
    }
    
    public static synchronized Integer getDistanciaMelhorSolucao() {
	return distanciaMelhorSolucao;
    }

    public static synchronized void setMelhorSolucaoAteAgora(String caminhoSolucao, Integer distanciaSolucao) {
	if (distanciaSolucao < RotaBO.distanciaMelhorSolucao) {
	    RotaBO.distanciaMelhorSolucao = distanciaSolucao;
	    RotaBO.caminhoMelhorSolucao = caminhoSolucao;
	}
    }

    public synchronized String getOrigemProximoTrecho() {
	return origemProximoTrecho;
    }

    public synchronized void setOrigemProximoTrecho(String origemProximoTrecho) {
	this.origemProximoTrecho = origemProximoTrecho;
    }

    public static synchronized TrechoDaoImplMemory getTrechoDao() {
	return trechoDao;
    }

    public static synchronized void setTrechoDao(TrechoDaoImplMemory trechoDao) {
	RotaBO.trechoDao = trechoDao;
    }

    public static synchronized String getDestinoSolicitado() {
	return destinoSolicitado;
    }

    public static synchronized void setDestinoSolicitado(String destinoSolicitado) {
	RotaBO.destinoSolicitado = destinoSolicitado;
    }

    public static synchronized void setNumeroMaximoTrechosRotaFinal(Integer numeroMaximoTrechosRotaFinal) {
	RotaBO.numeroMaximoTrechosRotaFinal = numeroMaximoTrechosRotaFinal;
    }

    public static synchronized Set<String> getThreadsEmExecucao() {
	return threadsEmExecucao;
    }

    public static synchronized void setThreadsEmExecucao(Set<String> threadsEmExecucao) {
	RotaBO.threadsEmExecucao = threadsEmExecucao;
    }

    public static synchronized String getCaminhoMelhorSolucao() {
	return caminhoMelhorSolucao;
    }

    public static synchronized void setDefaultConfigurations() {
	RotaBO.caminhoMelhorSolucao = "";
	RotaBO.distanciaMelhorSolucao = Integer.MAX_VALUE;
    }
    
}
