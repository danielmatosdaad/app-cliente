package com.app.cliente.infra.controller.negocial;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import javax.enterprise.context.SessionScoped;

@SessionScoped
public class ContextoNegocial implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Map<String, Object> dadosNegocio;

	public ContextoNegocial() {

		this.dadosNegocio = new HashMap<String, Object>();
	}

	public void put(String id, Object valor) {
		this.dadosNegocio.put(id, valor);
	}

	public Object get(String id, Object valor) {
		return this.dadosNegocio.get(id);
	}
}
