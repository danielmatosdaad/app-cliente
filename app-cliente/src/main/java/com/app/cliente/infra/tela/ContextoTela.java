package com.app.cliente.infra.tela;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import javax.enterprise.context.SessionScoped;

@SessionScoped
public class ContextoTela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private DadosTela dadosTela;

	public ContextoTela() {
	}

	public ContextoTela(DadosTela dadosTela) {
		this.dadosTela = dadosTela;
	}

	public DadosTela getDadosTela() {
		return dadosTela;
	}

	public void setDadosTela(DadosTela dadosTela) {
		this.dadosTela = dadosTela;
	}

}
