package com.app.cliente.configuracaometadado.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.inject.Inject;
import javax.inject.Named;
import br.com.projeto.facelet.bean.Facelet;
import br.com.projeto.metadado.bean.MetaDado;
import br.com.projeto.metadado.regras.IRegrasMetaDado;

@Named(value = "ctxConfCpnTela")
@SessionScoped
public class ContextoConfiguraComponenteTela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private IRegrasMetaDado service;

	private List<Facelet> faceletsEscolhidos;

	private MetaDado metadadGerado;

	@PostConstruct
	public void init() {

		this.faceletsEscolhidos = new ArrayList<Facelet>();
	}

	public IRegrasMetaDado getService() {
		return service;
	}

	public void setService(IRegrasMetaDado service) {
		this.service = service;
	}

	public List<Facelet> getFaceletsEscolhidos() {
		return faceletsEscolhidos;
	}

	public void setFaceletsEscolhidos(List<Facelet> faceletsEscolhidos) {
		this.faceletsEscolhidos = faceletsEscolhidos;
	}

	public MetaDado getMetadadGerado() {
		return metadadGerado;
	}

	public void setMetadadGerado(MetaDado metadadGerado) {
		this.metadadGerado = metadadGerado;
	}

}
