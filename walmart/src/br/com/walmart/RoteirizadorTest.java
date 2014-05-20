package br.com.walmart;

import br.com.walmart.service.RoteirizadorService;

public class RoteirizadorTest {

    // @Autowired
    // private RoteirizadorService service;

    /**
     * @param args
     */
    public static void main(String[] args) {
	RoteirizadorService service = new RoteirizadorService();
	
	service.calculaRota("A", "D");
    }

}
