package com.app.cliente.configuracaometadado.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.app.smart.business.funcionalidade.dto.IdentificadorDTO;
import br.com.app.smart.business.interfaces.IComponenteTelaService;
import br.com.app.smart.business.processoconfiguracao.dto.ResultadoConvercaoComponenteUI;
import br.com.app.smart.business.tela.componente.dto.ComponenteTelaDTO;
import br.com.app.smart.business.tela.componente.dto.CompositeInterfaceDTO;
import br.com.app.smart.business.tela.componente.dto.PropriedadeDTO;

@Named(value = "ctxConfCpnTela")
@SessionScoped
public class ContextoConfiguraComponenteTela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private IComponenteTelaService componenteTelaService;

	private List<CompositeInterfaceDTO> componentesEscolhidos;

	private List<ComponenteTelaDTO> componenteTelaDTOs;

	private List<IdentificadorDTO> identificadores;

	ResultadoConvercaoComponenteUI resultadoConvercaoComponenteUI;

	private String outputComponenteTelaUI;

	@PostConstruct
	public void init() {

		this.componentesEscolhidos = new ArrayList<CompositeInterfaceDTO>();
	}

	public IComponenteTelaService getComponenteTelaService() {
		System.out.println(componenteTelaService);
		return componenteTelaService;
	}

	public void setComponenteTelaService(IComponenteTelaService componenteTelaService) {
		this.componenteTelaService = componenteTelaService;
	}

	public List<CompositeInterfaceDTO> getComponentesEscolhidos() {
		return componentesEscolhidos;
	}

	public void setComponentesEscolhidos(List<CompositeInterfaceDTO> componentesEscolhidos) {
		this.componentesEscolhidos = componentesEscolhidos;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getOutputComponenteTelaUI() {
		return outputComponenteTelaUI;
	}

	public void setOutputComponenteTelaUI(String outputComponenteTelaUI) {
		this.outputComponenteTelaUI = outputComponenteTelaUI;
	}

	public List<ComponenteTelaDTO> getComponenteTelaDTOs() {
		return componenteTelaDTOs;
	}

	public void setComponenteTelaDTOs(List<ComponenteTelaDTO> componenteTelaDTOs) {
		this.componenteTelaDTOs = componenteTelaDTOs;
	}

	public void limpar() {
		if (this.componentesEscolhidos != null) {

			this.componentesEscolhidos.clear();
		}

		if (this.componenteTelaDTOs != null) {
			this.componenteTelaDTOs.clear();
		}

		if (this.outputComponenteTelaUI != null) {

			this.outputComponenteTelaUI = "";
		}

	}

	public List<IdentificadorDTO> getIdentificadores() {
		return identificadores;
	}

	public void setIdentificadores(List<IdentificadorDTO> identificadores) {
		this.identificadores = identificadores;
	}

	public ResultadoConvercaoComponenteUI getResultadoConvercaoComponenteUI() {
		return resultadoConvercaoComponenteUI;
	}

	public void setResultadoConvercaoComponenteUI(ResultadoConvercaoComponenteUI resultadoConvercaoComponenteUI) {
		this.resultadoConvercaoComponenteUI = resultadoConvercaoComponenteUI;
	}

}
