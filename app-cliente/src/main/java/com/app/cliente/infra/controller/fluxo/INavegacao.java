package com.app.cliente.infra.controller.fluxo;

import java.io.Serializable;

import com.app.cliente.infra.tela.ContextoTela;

public interface INavegacao extends Serializable{

	public void nevegar(ContextoTela contextoTela,Requisicao rf);
}
