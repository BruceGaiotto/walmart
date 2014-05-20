package br.com.walmart.service;

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

    public void calculaRota(String origem, String destino) {
	Trecho trechoRota = new Trecho(origem, destino);

	Rota rota = new Rota();
	rota.setOrigem(origem);
	Rota.setDestinoSolicitado(destino);
	Rota.setTrechoDao(new TrechoDaoImplMemory());

	List<Trecho> trechos = this.trechoDao.findAll();
	if (trechos.contains(trechoRota)) {
	    /**
	     * Considerando que os trechos sao unicos, desta maneira nao havera
	     * dois indices.
	     */
	    Trecho trechoSolucao = trechos.get(trechos.indexOf(trechoRota));
	    Rota.setDistanciaSolucao(trechoSolucao.getDistancia());

	    Rota primeiraSolucao = new Rota();
	    primeiraSolucao.add(trechoSolucao);
	    System.out.println("Primeira solução encontrada: " + primeiraSolucao);
	    logger.info("############ INFO");
	}

	/**
	 * Continua procurando por rotas mais curtas ainda que passem por mais
	 * pontos.
	 */
	Thread thread = new Thread(rota, "" + trechoRota);
	thread.start();
    }

}
