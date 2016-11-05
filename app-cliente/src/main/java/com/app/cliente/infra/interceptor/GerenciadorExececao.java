package com.app.cliente.infra.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.enterprise.util.Nonbinding;
import javax.interceptor.InterceptorBinding;

@InterceptorBinding
@Target(value = {ElementType.METHOD,ElementType.TYPE})
@Retention(value = RetentionPolicy.RUNTIME)
public @interface GerenciadorExececao {

	/**
	    * Define se o gerenciador de excecoes deve tratar a excecao lancada e lancar novamente.
	    * 
	    * @return
	    */
	@Nonbinding
	   public CodicaoTratamentoExecao[] throwsException() default {CodicaoTratamentoExecao.NAO};


	   /**
	    * Define se os dados inseridos no contexto metados deve ser limpo, antes da renderizacao da tela
	    * 
	    * @return
	    */
	   @Nonbinding
	   public CodicaoTratamentoExecao[] clearDataMetadadosContext() default {CodicaoTratamentoExecao.NAO};
}
