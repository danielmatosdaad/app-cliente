package com.app.cliente.infra.controller.fluxo;

import java.util.EnumSet;

public enum AcaoFluxo {

	SAIR("sair"),
	ENTRAR("inicio"),
	INICIO("inicio"),
	VOLTAR_TELA("voltar_tela"), 
	MANTER_TELA("manter_tela"),
	PROXIMA_TELA("proxima_tela"), 
	VOLTAR_FUNCIONALIDADE("voltar_funcionalidade"),
	MANTER_FUNCIONALIDADE("manter_funcionalidade"),
	PROXIMA_FUNCIONALIDADE("proxima_funcionalidade"),
	PROXIMO("PROXIMO");
	

	private AcaoFluxo(String valor) {

		this.valor = valor;

	}

	public static AcaoFluxo getInstancia(String valorFluxo) {

		for (AcaoFluxo acaoFluxo : EnumSet.allOf(AcaoFluxo.class)) {

			if (acaoFluxo.getValor().equalsIgnoreCase(valorFluxo)) {
				return acaoFluxo;
			}
		}
		return null;
	}

	String valor;

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}

}
