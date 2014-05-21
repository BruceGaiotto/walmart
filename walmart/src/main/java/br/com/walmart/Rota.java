package br.com.walmart;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import br.com.walmart.dao.TrechoDaoImplMemory;

public class Rota extends ArrayList<Trecho> implements Runnable {

    private static final long serialVersionUID = 4312981990264159895L;
    /**
     * Atributo a ser compartilhado entre todas as Threads a fim de comparacao e
     * gatilho para a nao continuidade das proximas Threads.
     */
    private static Integer distanciaMelhorSolucao = Integer.MAX_VALUE;
    /**
     * Caminho percorrido na melhor solucao.
     */
    private static String caminhoMelhorSolucao = "";
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

    private Integer distanciaPercorrida = 0;
    private String caminhoPercorrido = "";
    /**
     * Origem do próximo trecho para continuidade da rota.<br>
     * Não representa a origem do percurso solicitado.
     */
    private String origemProximoTrecho;
    /**
     * 
     */
    private static Set<String> threadsEmExecucao;

    @Override
    public void run() {
	List<Trecho> trechosOrigem = Rota.trechoDao.findTrechoByOrigem(this.origemProximoTrecho);
	for (Trecho trecho : trechosOrigem) {
	    if (this.distanciaPercorrida < Rota.distanciaMelhorSolucao && trecho.getDistancia() < Rota.distanciaMelhorSolucao) {
		Rota rota = (Rota) this.clone();
		rota.add(trecho);

		if (Rota.destinoSolicitado.equals(trecho.getDestino())) {
		    Rota.setDistanciaMelhorSolucao(rota.distanciaPercorrida);
		    Rota.setCaminhoMelhorSolucao(rota.caminhoPercorrido);
		} else {
		    /**
		     * Ponto de prosseguimento/parada da recursao!
		     */
		    if (rota.size() < Rota.numeroMaximoTrechosRotaFinal) {
			rota.setOrigemProximoTrecho(trecho.getDestino());
			Thread thread = new Thread(rota, rota.getCaminhoPercorrido());
			thread.start();
			Rota.getThreadsEmExecucao().add(thread.getName());
		    }
		}
	    }
	}
	Rota.getThreadsEmExecucao().remove(Thread.currentThread().getName());
    }

    @Override
    public boolean add(Trecho e) {
	if (e != null) {
	    if (e.getDistancia() != null) {
		this.distanciaPercorrida += e.getDistancia();
	    }
	    if (e.getOrigem() != null && e.getDestino() != null) {
		this.caminhoPercorrido += (e.getOrigem() + e.getDestino());
	    }
	}
	return super.add(e);
    }

    @Override
    public String toString() {
	return "Rota: " + this.caminhoPercorrido + ". Distancia: " + this.distanciaPercorrida;
    }

    public Integer getDistanciaPercorrida() {
	return distanciaPercorrida;
    }

    public void setDistanciaPercorrida(Integer distanciaPercorrida) {
	this.distanciaPercorrida = distanciaPercorrida;
    }

    public String getCaminhoPercorrido() {
	return caminhoPercorrido;
    }

    public void setCaminhoPercorrido(String caminhoPercorrido) {
	this.caminhoPercorrido = caminhoPercorrido;
    }

    public static synchronized Integer getDistanciaMelhorSolucao() {
	return distanciaMelhorSolucao;
    }

    public static synchronized void setDistanciaMelhorSolucao(Integer distanciaSolucao) {
	if (distanciaSolucao < Rota.distanciaMelhorSolucao) {
	    Rota.distanciaMelhorSolucao = distanciaSolucao;
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
	Rota.trechoDao = trechoDao;
    }

    public static synchronized String getDestinoSolicitado() {
	return destinoSolicitado;
    }

    public static synchronized void setDestinoSolicitado(String destinoSolicitado) {
	Rota.destinoSolicitado = destinoSolicitado;
    }

    public static synchronized void setNumeroMaximoTrechosRotaFinal(Integer numeroMaximoTrechosRotaFinal) {
	Rota.numeroMaximoTrechosRotaFinal = numeroMaximoTrechosRotaFinal;
    }

    public static synchronized Set<String> getThreadsEmExecucao() {
        return threadsEmExecucao;
    }

    public static synchronized void setThreadsEmExecucao(Set<String> threadsEmExecucao) {
        Rota.threadsEmExecucao = threadsEmExecucao;
    }

    public static synchronized String getCaminhoMelhorSolucao() {
        return caminhoMelhorSolucao;
    }

    public static synchronized void setCaminhoMelhorSolucao(String caminhoMelhorSolucao) {
        Rota.caminhoMelhorSolucao = caminhoMelhorSolucao;
    }

}
