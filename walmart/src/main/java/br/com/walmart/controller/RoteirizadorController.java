package br.com.walmart.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import br.com.walmart.service.RoteirizadorService;

@Controller
@RequestMapping(value = "/roteirizador")
public class RoteirizadorController {
    
    @Autowired
    private RoteirizadorService service;
    
    /**
     *  URL para chamada deste serviço, exemplo:
     *		http://localhost:8080/walmart/app/roteirizador/consultaRota/{origem}/{destino} 
     */
    @RequestMapping(value = "/consultaRota/{origem}/{destino}", method = RequestMethod.GET)
    public @ResponseBody String consultaRota(@PathVariable final String origem, @PathVariable final String destino) {
	
	//TODO recuperar o resultado da execucao das Threads
	service.calculaRota(origem, destino);
	
	//TODO formatar retorno de acordo com resultado avaliado
	return "Rest service executado com sucesso";
    }
    

}
