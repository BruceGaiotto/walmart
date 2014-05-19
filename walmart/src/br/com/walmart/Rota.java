package br.com.walmart;
import java.util.ArrayList;

public class Rota extends ArrayList<Trecho> implements Runnable {

	private static final long serialVersionUID = 4312981990264159895L;
	private Boolean finalizada;
	private Integer distanciaPercorrida=0;
	private String caminhoPercorrido="";
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public boolean add(Trecho e) {
		if(e != null) {
			if(e.getDistancia() != null)
				this.distanciaPercorrida += e.getDistancia();
			if(e.getOrigem() != null && e.getDestino() != null)
				this.caminhoPercorrido += (e.getOrigem() + e.getDestino()); 
		}
		return super.add(e);
	}
	
	@Override
	public String toString() {
		return "Rota: " + this.caminhoPercorrido + ". Distancia: " + this.distanciaPercorrida;
	}
	
	public Boolean getFinalizada() {
		return finalizada;
	}

	public void setFinalizada(Boolean finalizada) {
		this.finalizada = finalizada;
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
