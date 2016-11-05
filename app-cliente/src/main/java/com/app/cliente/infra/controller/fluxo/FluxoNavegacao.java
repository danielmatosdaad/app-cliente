package com.app.cliente.infra.controller.fluxo;

import javax.enterprise.inject.Any;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.inject.Named;

import com.app.cliente.infra.controller.negocial.ContextoNegocial;
import com.app.cliente.infra.controller.negocial.FuncionalidadeQualificador;
import com.app.cliente.infra.controller.negocial.IControladorNegocial;
import com.app.cliente.infra.controller.tela.IControladorTela;
import com.app.cliente.infra.tela.ContextoTela;

@Named
public class FluxoNavegacao implements INavegacao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	@Any
	private Instance<IControladorNegocial> controladorNegocial;

	@Inject
	@Any
	private Instance<IControladorTela> controladorTela;



	public void nevegar(ContextoTela contextoTela,Requisicao rf) {

		executarControladores(contextoTela,rf);
	}


	public void executarControladores(ContextoTela contextoTela,Requisicao rf) {

	
		System.out.println("Preparando para executar controlador negocial");
		ContextoNegocial contextoNegocial = null;
		IControladorNegocial controladorNegocialEspecificoFuncionalidade = delegateControladorNegocial(rf);

		if (controladorNegocialEspecificoFuncionalidade != null) {

			contextoNegocial = controladorNegocialEspecificoFuncionalidade
					.executar(contextoTela);
		}

		System.out.println("Preparando para executar controlador tela");
		IControladorTela controladorTelaEspecificoFuncionalidade = delegateControladorTela(rf);

		if (controladorTelaEspecificoFuncionalidade != null) {

			if (contextoNegocial != null) {
				controladorTelaEspecificoFuncionalidade.executar(contextoTela,
						contextoNegocial);
			} else {
				controladorTelaEspecificoFuncionalidade.executar(contextoTela,
						contextoNegocial);
			}

		}

	}

	public IControladorNegocial delegateControladorNegocial(int numeroTela,
			int funcionalidade) {

		FuncionalidadeQualificador anotacao = new FuncionalidadeQualificador(
				numeroTela, funcionalidade);
		Instance<IControladorNegocial> delegate = this.controladorNegocial
				.select(anotacao);

		return delegate.isUnsatisfied() ? null : delegate.get();
	}

	public IControladorNegocial delegateControladorNegocial(
			Requisicao requisicaoFuncionalidade) {

		FuncionalidadeQualificador anotacao = new FuncionalidadeQualificador(
				requisicaoFuncionalidade.getNumeroTela(),
				requisicaoFuncionalidade.getNumeroFuncionalidade());
		Instance<IControladorNegocial> delegate = this.controladorNegocial
				.select(anotacao);

		return delegate.isUnsatisfied() ? null : delegate.get();
	}

	public IControladorTela delegateControladorTela(
			Requisicao requisicaoFuncionalidade) {

		FuncionalidadeQualificador anotacao = new FuncionalidadeQualificador(
				requisicaoFuncionalidade.getNumeroTela(),
				requisicaoFuncionalidade.getNumeroFuncionalidade());
		Instance<IControladorTela> delegate = this.controladorTela
				.select(anotacao);

		return delegate.isUnsatisfied() ? null : delegate.get();
	}



}
