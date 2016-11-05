package com.app.cliente.infra.controller.negocial;

import java.beans.ConstructorProperties;
import javax.enterprise.util.AnnotationLiteral;

import com.app.cliente.infra.controller.annotation.Funcionalidade;

public class FuncionalidadeQualificador extends
		AnnotationLiteral<Funcionalidade> implements Funcionalidade {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final int numeroTela;
	private final int funcionalidade;

	@ConstructorProperties({ "numeroTela", "funcionalidade" })
	public FuncionalidadeQualificador(int numeroTela, int funcionalidade) {

		this.numeroTela = numeroTela;
		this.funcionalidade = funcionalidade;

	}

	public int numeroTela() {
		return numeroTela;
	}

	public int funcionalidade() {
		return funcionalidade;
	}
	
}
