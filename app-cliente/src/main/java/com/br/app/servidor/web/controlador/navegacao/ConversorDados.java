package com.br.app.servidor.web.controlador.navegacao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.app.cliente.infra.controller.negocial.DadosNegocial;
import com.app.cliente.infra.tela.DadosTela;

import br.com.app.smart.business.funcionalidade.dto.IdentificadorDTO;

public class ConversorDados {

	public DadosNegocial converter(DadosTela dadosTela) {
		return new DadosNegocial();
	}

	public DadosTela converter(Map<String, Object> dadosComponente) {

		DadosTela dados = new DadosTela();
		return dados;
	}

}
