package com.br.app.servidor.web.controlador.navegacao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import com.app.cliente.infra.controller.negocial.DadosNegocial;
import com.app.cliente.infra.tela.DadosTela;

import br.com.projeto.metadado.infra.comum.IdentificadorNegocial;

public class ConversorDados {

	public DadosNegocial converter(DadosTela dadosTela) {
		return new DadosNegocial();
	}

	public DadosTela converter(Map<String, Object> dadosComponente) {

		HashMap<IdentificadorNegocial, Object> map = new LinkedHashMap<IdentificadorNegocial, Object>();
		for (Iterator<String> iterator = dadosComponente.keySet().iterator(); iterator
				.hasNext();) {

			String chave = iterator.next();

			if (chave != null) {

				Object objeto = dadosComponente.get(chave);
				IdentificadorNegocial idNegocial = IdentificadorNegocial
						.getInstancia(chave);
				if (idNegocial.isValorNegocial()) {

					map.put(idNegocial, objeto);
				}
			}
		}
		DadosTela dados = new DadosTela();
		dados.setMapParametroNegocial(map);
		return dados;
	}

}
