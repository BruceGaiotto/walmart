package br.com.walmart;

import java.util.ArrayList;
import java.util.List;

import br.com.walmart.dao.TrechoDaoImplMemory;

public class Rota extends ArrayList<Trecho> implements Runnable {
    
    private static final long serialVersionUID = 4312981990264159895L;
    /**
     * Atributo a ser compartilhado entre todas as Threads a fim de comparacao e gatilho para a nao continuidade das proximas Threads.
     */
    private static Integer distanciaSolucao = Integer.MAX_VALUE;
    /**
     * Destino final do percurso solicitado.
     */
    private static String destinoSolicitado;
    
    private static TrechoDaoImplMemory trechoDao;
    
    private Integer distanciaPercorrida = 0;
    private String caminhoPercorrido = "";
    /**
     * Origem para continuidade da rota. Não representa a origem do percurso solicitado.
     */
    private String origem;
    

    @Override
    public void run() {
	//ListIterator<Trecho> listIterator = this.listIterator();
	
	List<Trecho> trechosOrigem = Rota.trechoDao.findTrechoByOrigem(this.origem);
	for (Trecho trecho : trechosOrigem) {
	   if(this.distanciaPercorrida < Rota.distanciaSolucao && trecho.getDistancia() < Rota.distanciaSolucao) {
		Rota rota = (Rota)this.clone();
    	    	//while(listIterator.hasNext()) {
    	    	//    rota.add(listIterator.next());
    	    	//}
    	    	rota.add(trecho);
    	    	rota.setOrigem(trecho.getDestino());
    	    	
    	    	if(Rota.destinoSolicitado.equals(trecho.getDestino())) {
    	    	    Rota.setDistanciaSolucao(rota.distanciaPercorrida);
    	    	    System.out.println("Solução encontrada: " + rota);
    	    	} else {
    	    	    Thread thread = new Thread(rota, rota.getCaminhoPercorrido());
	    	    thread.start();
    	    	}
	    } 
	}
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

    public static synchronized Integer getDistanciaSolucao() {
        return distanciaSolucao;
    }

    public static synchronized void setDistanciaSolucao(Integer distanciaSolucao) {
        if(distanciaSolucao < Rota.distanciaSolucao) {
            Rota.distanciaSolucao = distanciaSolucao;
        }
    }

    public synchronized String getOrigem() {
        return origem;
    }

    public synchronized void setOrigem(String origem) {
        this.origem = origem;
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
    
}
