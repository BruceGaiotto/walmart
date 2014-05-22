package br.com.walmart;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Rota é uma lista de trechos percorridos de um ponto origem a um ponto destino.
 *  
 */
public class Rota extends ArrayList<Trecho> implements Serializable {

    private static final long serialVersionUID = 4312981990264159895L;
    
    /**
     * Distancia percorrida por esta rota desde o ponto inicial de origem ate o ponto atual de destino.<br>
     * Substitui a soma das distancias dos trechos desta lista.
     */
    protected Integer distanciaPercorrida = 0;
    /**
     * Todos os trechos percorridos por esta rota ate entao. Exemplo: ABBDDE<br>
     * Substitui a lista de trechos desta lista.
     */
    protected String caminhoPercorrido = "";
    
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
	if(!"".equals(this.caminhoPercorrido))
	    return "Caminho: " + this.caminhoPercorrido + ". Distancia: " + this.distanciaPercorrida;
	else
	    return "Caminho não encontrado";
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

}
