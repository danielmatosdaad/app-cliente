package com.app.cliente.infra.controller.tela;

import javax.inject.Named;

import com.app.cliente.infra.controller.negocial.ContextoNegocial;
import com.app.cliente.infra.tela.ContextoTela;

@Named
public class ControladorTela {

	protected ContextoTela dadosTela;
	protected ContextoNegocial contextoNegocial;

}
