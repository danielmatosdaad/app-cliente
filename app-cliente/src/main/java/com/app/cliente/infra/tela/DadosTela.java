package com.app.cliente.infra.tela;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.app.cliente.infra.controller.fluxo.Requisicao;

import br.com.projeto.metadado.infra.comum.IdentificadorBean;
import br.com.projeto.metadado.infra.comum.IdentificadorNegocial;

public class DadosTela {

	private HashMap<IdentificadorNegocial, Object> mapParametroNegocial = new LinkedHashMap<IdentificadorNegocial, Object>();
	private HashMap<IdentificadorBean, Object> mapParametroBeanTela = new LinkedHashMap<IdentificadorBean, Object>();
	private Requisicao requisicaoFuncionalidade;

	public DadosTela() {

	}

	public DadosTela(HashMap<IdentificadorNegocial, Object> dadoRecebido,
			HashMap<IdentificadorBean, Object> dadosBeanTela,
			Requisicao requisicaoFuncionalidade) {

		this.mapParametroNegocial = dadoRecebido;
		this.mapParametroBeanTela = dadosBeanTela;
		this.requisicaoFuncionalidade = requisicaoFuncionalidade;

	}

	public DadosTela(HashMap<IdentificadorNegocial, Object> dadoRecebido,
			Requisicao requisicaoFuncionalidade) {

		this.mapParametroNegocial = dadoRecebido;
		this.requisicaoFuncionalidade = requisicaoFuncionalidade;
	}

	public DadosTela(HashMap<IdentificadorNegocial, Object> map) {

		this.mapParametroNegocial = map;
	}

	public HashMap<IdentificadorNegocial, Object> getMapParametroNegocial() {
		return mapParametroNegocial;
	}

	
	public void setMapParametroNegocial(
			HashMap<IdentificadorNegocial, Object> mapParametroNegocial) {
		this.mapParametroNegocial = mapParametroNegocial;
	}

	public HashMap<IdentificadorBean, Object> getMapParametroBeanTela() {
		return mapParametroBeanTela;
	}

	public void setMapParametroBeanTela(
			HashMap<IdentificadorBean, Object> mapParametroBeanTela) {
		this.mapParametroBeanTela = mapParametroBeanTela;
	}

	public Requisicao getRequisicaoFuncionalidade() {
		return requisicaoFuncionalidade;
	}

	public void setRequisicaoFuncionalidade(
			Requisicao requisicaoFuncionalidade) {
		this.requisicaoFuncionalidade = requisicaoFuncionalidade;
	}

	public void adicionaDadoNegocial(IdentificadorNegocial id, Object objeto) {

		this.mapParametroNegocial.put(id, objeto);

	}

	public void removeDadoNegocial(Object objeto) {

		this.mapParametroNegocial.remove(objeto);
	}

	public void removeDadoNegocial(IdentificadorNegocial id) {

		Object obj = this.mapParametroNegocial.get(id);
		this.mapParametroNegocial.remove(obj);
	}

	public void adicionaBean(IdentificadorBean id, Object objeto) {

		this.mapParametroBeanTela.put(id, objeto);

	}

	public void removeBean(Object objeto) {

		this.mapParametroBeanTela.remove(objeto);
	}

	public void removeBean(IdentificadorBean id) {

		Object obj = this.mapParametroBeanTela.get(id);
		this.mapParametroBeanTela.remove(obj);
	}
	
	

}
