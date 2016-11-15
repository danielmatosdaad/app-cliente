package com.app.cliente.configuracaometadado.controller;

import java.io.Serializable;
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
import br.com.projeto.facelet.bean.ComponenteFacelet;
import br.com.projeto.facelet.bean.Facelet;
import br.com.projeto.facelet.bean.PropriedadeComponenteFacelet;

@SessionScoped
@Named("confCpn")
public class ConfiguraComponenteTela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ContextoConfiguraComponenteTela ctxConfMdo;

	private Map<String, Object> parametros;

	@PostConstruct
	public void init() {

		this.parametros = new LinkedHashMap<String, Object>();
	}

	private void listarValoreRecebidos() {

		System.out.println("Parametros negociais recebido: ");
		for (Iterator<String> iterator = this.parametros.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			System.out.println("chave: " + key + " valor: " + this.parametros.get(key));
		}

	}

	public void buttonSalvarPropriedadeComponente(ActionEvent actionEvent) {

		if (this.ctxConfMdo.getFaceletsEscolhidos() != null || (!this.ctxConfMdo.getFaceletsEscolhidos().isEmpty())) {
			for (Facelet facelet : this.ctxConfMdo.getFaceletsEscolhidos()) {

				for (ComponenteFacelet componente : facelet.getConteudo().getComponentes()) {

					for (PropriedadeComponenteFacelet propriedade : componente.getPropriedades()) {

						String id = facelet.getNomeMetadado().concat("-").concat(componente.getNomeComponente())
								.concat("-").concat(propriedade.getNome()).replace(".xhtml", "");

						Object objeto = this.parametros.get(id);
						if (objeto instanceof String) {

							String novoId = propriedade.getValor();
							if (novoId != null && novoId.contains("#")) {

								novoId = novoId.replace("#{", "");
								novoId = novoId.replace("}", "");
								propriedade.setNome(novoId);
								propriedade.setValor((String) objeto);
							} else {
								propriedade.setValor((String) objeto);
							}

						}

					}
				}
			}

		}

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

	public ContextoConfiguraComponenteTela getCtxConfMdo() {
		return ctxConfMdo;
	}

	public void setCtxConfMdo(ContextoConfiguraComponenteTela ctxConfMdo) {
		this.ctxConfMdo = ctxConfMdo;
	}

}
