package com.app.cliente.infra.controller.fluxo;

import java.io.Serializable;

import javax.inject.Named;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;


@Named
public class Requisicao implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private int numeroFuncionalidade=1;
	private int numeroTela=1;
	private int numeroFuncionalidadeAnteiror;
	private int numeroTelaAnteiror;
	private AcaoFluxo acaoFluxo;
	private AcaoRequisicao acaoRequisicao;
	private String urlProximaPagina;
	
	public String getUrlProximaPagina() {
		return urlProximaPagina;
	}

	public void setUrlProximaPagina(String urlProximaPagina) {
		this.urlProximaPagina = urlProximaPagina;
	}

	public int getNumeroTelaAnteiror() {
		return numeroTelaAnteiror;
	}

	public void setNumeroTelaAnteiror(int numeroTelaAnteiror) {
		this.numeroTelaAnteiror = numeroTelaAnteiror;
	}

	public int getNumeroFuncionalidadeAnteiror() {
		return numeroFuncionalidadeAnteiror;
	}

	public void setNumeroFuncionalidadeAnteiror(int numeroFuncionalidadeAnteiror) {
		this.numeroFuncionalidadeAnteiror = numeroFuncionalidadeAnteiror;
	}

	public int getNumeroFuncionalidade() {
		return numeroFuncionalidade;
	}

	public void setNumeroFuncionalidade(int numeroFuncionalidade) {
		this.numeroFuncionalidade = numeroFuncionalidade;
	}

	public int getNumeroTela() {
		return numeroTela;
	}

	public void setNumeroTela(int numeroTela) {
		this.numeroTela = numeroTela;
	}

	public AcaoFluxo getAcaoFluxo() {
		return acaoFluxo;
	}

	public void setAcaoFluxo(AcaoFluxo acaoFluxo) {
		this.acaoFluxo = acaoFluxo;
	}

	public AcaoRequisicao getAcaoRequisicao() {
		return acaoRequisicao;
	}

	public void setAcaoRequisicao(AcaoRequisicao acaoRequisicao) {
		this.acaoRequisicao = acaoRequisicao;
	}

	public void processAction(ActionEvent event)
			throws AbortProcessingException {
		System.out
				.println("--------------------------RequisicaoFuncionalidade-----------------");
		System.out
				.println("--------------------------processAction----------------");

		System.out.println("Atributos botao acionado: ");
		String acaoRequisicao = (String) event.getComponent().getAttributes()
				.get("acaoRequisicao");
		String acaoFluxo = (String) event.getComponent().getAttributes()
				.get("acaoFluxo");
		String numeroTela = (String) event.getComponent().getAttributes()
				.get("numeroTela");
		String numeroFuncionalidade = (String) event.getComponent()
				.getAttributes().get("numeroFuncionalidade");

		System.out.println("acaoRequisicao: " + acaoRequisicao);
		System.out.println("acaoFluxo: " + acaoFluxo);
		System.out.println("numeroTela: " + numeroTela);
		System.out.println("numeroFuncionalidade: " + numeroFuncionalidade);

		this.setAcaoRequisicao(AcaoRequisicao.getInstacia(acaoRequisicao));
		this.setAcaoFluxo(AcaoFluxo.getInstancia(acaoFluxo));
		this.setNumeroTela(Integer.parseInt(numeroTela));
		this.setNumeroFuncionalidade(Integer.parseInt(numeroFuncionalidade));

	}

	public String montarUrlNevegacao() {

		return "/metadado/componente.xhtml"+"?numeroFuncionalidade=" + regraFuncionalidade() + ";numeroTela="+regraNumeroTela();
	}
	
	
	public int regraFuncionalidade() {

		return this.numeroFuncionalidade == 0 ? 1 : this.numeroFuncionalidade;
	}

	public int regraNumeroTela() {

		return this.numeroTela == 0 ? 1 : this.numeroTela;
	}

}
