package com.app.cliente.configuracaometadado.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.funcionalidade.dto.IdentificadorDTO;
import br.com.app.smart.business.tela.componente.dto.ComponenteDTO;
import br.com.app.smart.business.tela.componente.dto.ComponenteTelaDTO;
import br.com.app.smart.business.tela.componente.dto.CompositeInterfaceDTO;
import br.com.app.smart.business.tela.componente.dto.PropriedadeDTO;

@SessionScoped
@Named("confCpn")
public class ConfiguraComponenteTela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ContextoConfiguraComponenteTela contexto;

	private Map<String, Object> parametros;

	public void init() {

		this.parametros = new LinkedHashMap<String, Object>();

		try {
			this.contexto.setComponenteTelaDTOs(this.contexto.getComponenteTelaService()
					.converterCompositeInterfaces(this.contexto.getComponentesEscolhidos()));
		} catch (InfraEstruturaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String irParaPagina(){
		
		init();
		return "/adm/configuraTela/configuraComponente";
	}

	private void listarValoreRecebidos() {

		System.out.println("Parametros negociais recebido: ");
		for (Iterator<String> iterator = this.parametros.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			System.out.println("chave: " + key + " valor: " + this.parametros.get(key));
		}

	}

	public void buttonSalvarPropriedadeComponente(ActionEvent actionEvent) {

		listarValoreRecebidos();

		List<IdentificadorDTO> identificadoresComponenteDTO = new ArrayList<IdentificadorDTO>();

		if (this.contexto.getComponenteTelaDTOs() != null || (!this.contexto.getComponenteTelaDTOs().isEmpty())) {
			for (ComponenteTelaDTO componenteTelaDTO : this.contexto.getComponenteTelaDTOs()) {

				for (ComponenteDTO componenteDTO : componenteTelaDTO.getComponentes()) {

					for (PropriedadeDTO propriedadeDTO : componenteDTO.getPropriedades()) {

						String id = obterIdComponente(componenteDTO, propriedadeDTO);

						Object objeto = this.parametros.get(id);
						if (objeto instanceof String) {

							configurarPropriedade(propriedadeDTO, (String) objeto);
						}

						if (objeto instanceof String || objeto instanceof Number) {

							configurarPropriedade(propriedadeDTO, String.valueOf(objeto));
						}

						if (objeto instanceof IdentificadorDTO) {

							IdentificadorDTO identificador = (IdentificadorDTO) objeto;
							identificadoresComponenteDTO.add(identificador);
							configurarPropriedade(propriedadeDTO, identificador.getValor());

						}

					}
				}

			}

			this.contexto.setIdentificadores(identificadoresComponenteDTO);

		}

	}

	public String obterIdComponente(ComponenteDTO componenteDTO, PropriedadeDTO propriedadeDTO) {
		String id = componenteDTO.getNomeComponente().concat("-").concat(propriedadeDTO.getNome())
				.concat(String.valueOf(componenteDTO.getIdentificador()));
		System.out.println("###########" + id + "###################");
		return id;
	}

	private PropriedadeDTO configurarPropriedade(PropriedadeDTO propriedade, String valor) {

		propriedade.setValor(valor);

		return propriedade;
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public String irPara() {

		return "/adm/configuraTela/visualizaComponente";
	}

	public void limpar(ActionEvent actionEvent) {

		this.parametros = new LinkedHashMap<String, Object>();
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public ContextoConfiguraComponenteTela getContexto() {
		return contexto;
	}

	public void setContexto(ContextoConfiguraComponenteTela contexto) {
		this.contexto = contexto;
	}

}
