package com.app.cliente.infra.tela;

import com.app.cliente.infra.tela.model.bean.IBean;

public interface ITela<B extends IBean> {

	public void montaCabecalho(ContextoTela contexto, B bean);

	public void montaCorpo(ContextoTela contexto, B bean);

	public void montaRodape(ContextoTela contexto, B bean);
}
