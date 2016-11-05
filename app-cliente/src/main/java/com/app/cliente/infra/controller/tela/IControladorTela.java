package com.app.cliente.infra.controller.tela;

import com.app.cliente.infra.controller.negocial.ContextoNegocial;
import com.app.cliente.infra.tela.ContextoTela;

public interface IControladorTela {

	public void executar(ContextoTela dadosTela, ContextoNegocial contextoNegocial);
	public void executar(ContextoTela dadosTela);
}
