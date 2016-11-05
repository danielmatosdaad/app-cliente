package com.app.cliente.infra.controller.negocial;

import com.app.cliente.infra.tela.ContextoTela;

public interface IControladorNegocial {

	public ContextoNegocial executar(ContextoTela contextoTela);
}
