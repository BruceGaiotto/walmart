package br.com.walmart.service;

import java.util.Calendar;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import br.com.walmart.Rota;
import br.com.walmart.Trecho;
import br.com.walmart.bo.RotaBO;
import br.com.walmart.dao.TrechoDaoImplMemory;

@Component
@Configuration
@PropertySource("/properties/config.properties")
public class RoteirizadorService {

    protected static final Logger logger = LoggerFactory.getLogger(RoteirizadorService.class);

    @Autowired
    private Environment environment;
    
    @Bean
    public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	return new PropertySourcesPlaceholderConfigurer();
    }

    // @Autowired
    private TrechoDaoImplMemory trechoDao = new TrechoDaoImplMemory();

    public Rota calculaRota(String origem, String destino) {

	this.configuraRota(destino);

	Trecho trechoRota = new Trecho(origem, destino);
	List<Trecho> trechos = this.trechoDao.findAll();
	/**
	 * Verifica se a rota solicitada para calculo (origem/destino) eh uma das rotas armazenadas.
	 */
	if (trechos.contains(trechoRota)) {
	    /**
	     * Considerando que os trechos sao unicos, desta maneira nao havera dois indices.
	     */
	    Trecho trechoSolucao = trechos.get(trechos.indexOf(trechoRota));
	    RotaBO.setDistanciaMelhorSolucao(trechoSolucao.getDistancia());
	    RotaBO.setCaminhoMelhorSolucao(trechoRota.getOrigem() + trechoRota.getDestino());
	}

	/**
	 * Procura por rotas mais curtas, mesmo que passem por mais pontos.
	 */
	RotaBO rotaBO = new RotaBO();
	rotaBO.setOrigemProximoTrecho(origem);
	//Rota.getThreadsEmExecucao().add(origem);
	//rota.run();
	Thread thread = new Thread(rotaBO, origem);
	thread.start();
	RotaBO.getThreadsEmExecucao().add(thread.getName());
	this.aguardaExecucao();
	
	Rota solucao = new Rota();
	solucao.setCaminhoPercorrido(RotaBO.getCaminhoMelhorSolucao());
	solucao.setDistanciaPercorrida(RotaBO.getDistanciaMelhorSolucao());
	
	RotaBO.setDefaultConfigurations();
	
	return solucao;
    }
    
    /**
     * Aguarda enquanto houver Threads em execucao, ou o tempo de execucao ainda nao atingiu o limite maximo configurado.<br>
     * Neste caso, o cálculo é suspenso.
     */
    private void aguardaExecucao() {
	long timeInMillisBefore = Calendar.getInstance().getTimeInMillis();
	long tempoMaximoCalculo = Long.parseLong(this.environment.getProperty("tempo.maximo.calculo.rota").trim());
	
	while(!RotaBO.getThreadsEmExecucao().isEmpty() && !isTempoExpirado(timeInMillisBefore, tempoMaximoCalculo));
    }
     
    /**
     * Verifica se passou um determinado tempo em milissegundos (<b>tempoMaximoCalculo</b>) 
     * a partir de um dado momento definido pelo parametro <b>timiInMillisBefore</b>, também em milissegundos.
     */
    private boolean isTempoExpirado(long timiInMillisBefore, long tempoMaximoCalculo) {
	return (timiInMillisBefore + tempoMaximoCalculo) < Calendar.getInstance().getTimeInMillis();
    }

    /**
     * Configuracao inicial para atributos compartilhados da {@link RotaBO}.
     */
    private void configuraRota(final String destino) {
	Integer numeroMaximoTrechosRotaFinal = null;
	try {
	    numeroMaximoTrechosRotaFinal = Integer.parseInt(environment.getProperty("numero.maximo.trechos.rota.final").trim());
	} catch (NumberFormatException e) {
	    numeroMaximoTrechosRotaFinal = Integer.MAX_VALUE;
	}
	
	RotaBO.setNumeroMaximoTrechosRotaFinal(numeroMaximoTrechosRotaFinal);
	RotaBO.setDestinoSolicitado(destino);
	RotaBO.setTrechoDao(this.trechoDao);
	RotaBO.setThreadsEmExecucao(Collections.synchronizedSet(new HashSet<String>()));
    }

}
