package com.app.cliente.infra.controller.fluxo;

import java.util.EnumSet;

public enum AcaoRequisicao {

	SALVAR("salvar"), 
	BUSCAR("buscar"), 
	ATUALIZAR("atualizar"), 
	EXLCUIR("excluir"), 
	LOGIN("login"), 
	TELA_PRINCIPAL("tela_princioal"),
	PAGAMENTO_TITULO("pagamento_titulo");

	private AcaoRequisicao(String valor) {
		this.valor = valor;
	}

	String valor;

	public static AcaoRequisicao getInstacia(String valorAcao) {

		for (AcaoRequisicao acao : EnumSet.allOf(AcaoRequisicao.class)) {
			if (acao.getValor().equalsIgnoreCase(valorAcao))
				return acao;
		}
		return null;
	}

	public String getValor() {
		return valor;
	}

	public void setValor(String valor) {
		this.valor = valor;
	}
}
