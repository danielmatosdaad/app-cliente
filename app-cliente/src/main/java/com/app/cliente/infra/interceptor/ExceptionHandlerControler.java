package com.app.cliente.infra.interceptor;

import java.io.Serializable;
import javax.faces.context.FacesContext;
import javax.interceptor.AroundInvoke;
import javax.interceptor.Interceptor;
import javax.interceptor.InvocationContext;

import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.exception.TransacaoException;
import br.com.app.smart.business.interfaces.IException;
import br.com.app.smart.business.mensagem.ProvedorMensagemUtil;


@GerenciadorExececao
@Interceptor
public class ExceptionHandlerControler implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private static final String CABECALHO = "Lista causa excessão: \n";
	private static final String INICIO_PRINT_EXCEPTION = "\nINICIO DA IMPRESSAO DA PILHA DE EXCECOES >>>>>>>>>>>>>>>>>>> \n";
	private static final String FIM_PRINT_EXCEPTION = "\nFIM DA IMPRESSAO DA PILHA DE EXCECOES<<<<<<<<<<<<<<<<<<<<<<<<<<<< \n";

	private StringBuffer listaMensagensParaLogger;
	private CodicaoTratamentoExecao throwsException;
	private CodicaoTratamentoExecao clearDataMetadadosContext;
	private String telaSaida;

	@AroundInvoke
	public Object managerException(InvocationContext ctx) throws Exception {
		try {
			init(ctx);
			Object object = ctx.proceed();
			return object;
		} catch (NegocioException bee) {

			return "";
		} catch (InfraEstruturaException te) {
		} catch (TransacaoException iee) {

			return "";
		} catch (Exception e) {

			return "";
		}
		return null;
	}

	private void init(InvocationContext ctx) {
		// recupera a configuracao declarada no uso da exceptionManager
		GerenciadorExececao manager = ctx.getMethod().getAnnotation(GerenciadorExececao.class);

		if (manager.throwsException() != null && manager.throwsException().length > 0) {
			for (CodicaoTratamentoExecao condition : manager.throwsException()) {
				this.throwsException = condition;
				break;
			}
		}

		FacesContext.getCurrentInstance().getExternalContext().getFlash().setKeepMessages(true);
	}

	private String getMensagem(IException e) {
		String mensagemEscolhida = "";

		mensagemEscolhida = ProvedorMensagemUtil.obtemMensagem(e.codigoErro());
		if (mensagemEscolhida == null || mensagemEscolhida.isEmpty()) {
			mensagemEscolhida = e.descricaoErro();
		}
		return mensagemEscolhida;
	}

	/**
	 * Adiciona as mensagens vindas da excessao, para ser printado no logger de
	 * forma unica
	 * 
	 * @param mensagem
	 */
	private void addMensagemParaLogger(String mensagem) {
		getMensagemParaLogger().append("* " + mensagem + "\n");
	}

	/**
	 * Retorna as mensagens concatenadas
	 * 
	 * @return
	 */
	private StringBuffer getMensagemParaLogger() {
		if (listaMensagensParaLogger == null) {
			listaMensagensParaLogger = new StringBuffer();
			listaMensagensParaLogger.append(CABECALHO);
		}
		return this.listaMensagensParaLogger;
	}
}
