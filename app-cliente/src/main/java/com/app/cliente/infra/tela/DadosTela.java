package com.app.cliente.infra.tela;

import java.util.HashMap;
import java.util.LinkedHashMap;

import com.app.cliente.infra.controller.fluxo.Requisicao;

import br.com.app.smart.business.funcionalidade.dto.IdentificadorDTO;

public class DadosTela {

	private HashMap<IdentificadorDTO, Object> mapParametroNegocial = new LinkedHashMap<IdentificadorDTO, Object>();
	private HashMap<IdentificadorDTO, Object> mapParametroBeanTela = new LinkedHashMap<IdentificadorDTO, Object>();
	private Requisicao requisicaoFuncionalidade;

	public DadosTela() {

	}

	public DadosTela(HashMap<IdentificadorDTO, Object> dadoRecebido,
			HashMap<IdentificadorDTO, Object> dadosBeanTela,
			Requisicao requisicaoFuncionalidade) {

		this.mapParametroNegocial = dadoRecebido;
		this.mapParametroBeanTela = dadosBeanTela;
		this.requisicaoFuncionalidade = requisicaoFuncionalidade;

	}

	public DadosTela(HashMap<IdentificadorDTO, Object> dadoRecebido,
			Requisicao requisicaoFuncionalidade) {

		this.mapParametroNegocial = dadoRecebido;
		this.requisicaoFuncionalidade = requisicaoFuncionalidade;
	}

	public DadosTela(HashMap<IdentificadorDTO, Object> map) {

		this.mapParametroNegocial = map;
	}

	public HashMap<IdentificadorDTO, Object> getMapParametroNegocial() {
		return mapParametroNegocial;
	}

	
	public void setMapParametroNegocial(
			HashMap<IdentificadorDTO, Object> mapParametroNegocial) {
		this.mapParametroNegocial = mapParametroNegocial;
	}

	public HashMap<IdentificadorDTO, Object> getMapParametroBeanTela() {
		return mapParametroBeanTela;
	}

	public void setMapParametroBeanTela(
			HashMap<IdentificadorDTO, Object> mapParametroBeanTela) {
		this.mapParametroBeanTela = mapParametroBeanTela;
	}

	public Requisicao getRequisicaoFuncionalidade() {
		return requisicaoFuncionalidade;
	}

	public void setRequisicaoFuncionalidade(
			Requisicao requisicaoFuncionalidade) {
		this.requisicaoFuncionalidade = requisicaoFuncionalidade;
	}

	public void adicionaDadoNegocial(IdentificadorDTO id, Object objeto) {

		this.mapParametroNegocial.put(id, objeto);

	}

	public void removeDadoNegocial(Object objeto) {

		this.mapParametroNegocial.remove(objeto);
	}

	public void removeDadoNegocial(IdentificadorDTO id) {

		Object obj = this.mapParametroNegocial.get(id);
		this.mapParametroNegocial.remove(obj);
	}

	public void adicionaBean(IdentificadorDTO id, Object objeto) {

		this.mapParametroBeanTela.put(id, objeto);

	}

	public void removeBean(Object objeto) {

		this.mapParametroBeanTela.remove(objeto);
	}

	public void removeBean(IdentificadorDTO id) {

		Object obj = this.mapParametroBeanTela.get(id);
		this.mapParametroBeanTela.remove(obj);
	}
	
	

}
