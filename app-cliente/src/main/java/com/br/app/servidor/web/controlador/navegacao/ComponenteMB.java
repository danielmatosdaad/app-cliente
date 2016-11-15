package com.br.app.servidor.web.controlador.navegacao;

import java.io.Serializable;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.Application;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.inject.Inject;
import javax.inject.Named;
import com.app.cliente.infra.controller.fluxo.INavegacao;
import com.app.cliente.infra.tela.ContextoTela;
import com.app.cliente.infra.tela.DadosTela;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.projeto.metadado.infra.comum.IdentificadorBean;
import br.com.projeto.metadado.infra.comum.IdentificadorNegocial;
import br.com.projeto.metadado.infra.comum.IdentificadorWrapper;
import br.com.projeto.metadado.infra.comum.MetadadoUI;
import br.com.projeto.metadado.infra.comum.dto.ObterMetaDadoDTO;
import br.com.projeto.metadado.infra.interfaces.IComponenteMetadado;

import com.app.cliente.infra.controller.fluxo.*;

/**
 * @author daniel-matos
 * @see Classe responsavel por centralizar os dados de entrada do usuario e o
 *      bean utilizados pelos componentes de tela. Possui um escopo de
 *      requisicao
 */
@Named
@SessionScoped
public class ComponenteMB implements ActionListener, Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * O Atributo parametros tem os dados de submisao de formulario(cliente -
	 * servidor). Ou dados servidor - cliente
	 */
	private Map<String, Object> parametros;

	/**
	 * O Atributo bean tem os dados que serao usados pelos componentes
	 * 
	 */
	private Map<String, Object> bean;;

	/**
	 * O Atributo Requisicao tem os dados das propriedades dos botoes da tela do
	 * usuario.Encapsula a logica que defini qual a acao, qual tela, qual
	 * funcionalidade a serem executadas e mantem o estado anterior da
	 * requisicao fluxo de navegacao
	 */

	private Requisicao requisicao;

	/**
	 * 
	 */
	@Inject
	private INavegacao fluxoNevegacao;

	/**
	 * 
	 */
	@Inject
	private IComponenteMetadado componenteMetadado;

	/**
	 * Construtor sem argumentos, cria o mapeamento dos dados que darao suporte
	 * a requisicao e resposta
	 */
	public ComponenteMB() {


	}
	
	@PostConstruct
	public void init(){
		
		this.parametros = new LinkedHashMap<String, Object>();
		this.bean = new LinkedHashMap<String, Object>();
		this.requisicao = new Requisicao();
	}

	public void gerarView() {

		ObterMetaDadoDTO obterMetaDadoDTO = getEnvioRequisicaoMetadado();

		try {
			MetadadoUI metadoUI = this.componenteMetadado.gerar(obterMetaDadoDTO);
			this.definirParametros(metadoUI.getIdentificadoreNegocialMetadados());
			this.definirParametros(metadoUI.getIdentificadoreBeanMetadados());

			ConversorDados conversorDados = new ConversorDados();
			DadosTela dadosTela = conversorDados.converter(this.getParametros());
			dadosTela.setRequisicaoFuncionalidade(this.requisicao);
			dadosTela.setRequisicaoFuncionalidade(this.requisicao);

			ContextoTela contextoTela = new ContextoTela(dadosTela);

			fluxoNevegacao.nevegar(contextoTela, this.requisicao);
			this.atualizarParametros(dadosTela);

			Application app = FacesContext.getCurrentInstance().getApplication();
			app.createComponent(metadoUI.getMetadado().getBuffer().toString());
		} catch (InfraEstruturaException | NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private ObterMetaDadoDTO getEnvioRequisicaoMetadado() {
		ObterMetaDadoDTO obterMetaDadoDTO = new ObterMetaDadoDTO();
		obterMetaDadoDTO.setNumeroFuncionalidade(this.requisicao.getNumeroFuncionalidade());
		obterMetaDadoDTO.setNumeroTela(this.requisicao.getNumeroTela());
		return obterMetaDadoDTO;
	}

	/**
	 * 
	 */
	public String processar() {
		System.out.println("@PostConstruct Processando Tela de Login");
		System.out.println("Eu sou o objeto: " + this);
		listarValoreRecebidos();
		System.out.println("proxima funcionalidade" + this.requisicao.getUrlProximaPagina());
		return this.requisicao.getUrlProximaPagina();
	}

	public void definirParametros(String... nomesParametros) {
		for (String nome : nomesParametros) {
			parametros.put(nome, "");
		}
	}

	/**
	 * @param identificadores
	 * @see Define os identificadores dos componentes de tela
	 */
	public void definirParametros(List<IdentificadorWrapper> identificadores) {

		for (IdentificadorWrapper id : identificadores) {

			if (id.getWrapper() instanceof IdentificadorBean) {

				bean.put(id.getWrapper().getId(), "");

			} else if (id.getWrapper() instanceof IdentificadorNegocial) {
				parametros.put(id.getWrapper().getId(), "");
			}

		}
	}

	public void configurarRequisicao(ActionEvent event) {

		String acaoRequisicao = (String) event.getComponent().getAttributes().get("acaoRequisicao");
		String acaoFluxo = (String) event.getComponent().getAttributes().get("acaoFluxo");
		String numeroTela = (String) event.getComponent().getAttributes().get("numeroTela");
		String numeroFuncionalidade = (String) event.getComponent().getAttributes().get("numeroFuncionalidade");

		this.requisicao.setAcaoRequisicao(AcaoRequisicao.getInstacia(acaoRequisicao));
		this.requisicao.setAcaoFluxo(AcaoFluxo.getInstancia(acaoFluxo));
		this.requisicao.setNumeroFuncionalidade(Integer.parseInt(numeroFuncionalidade));
		this.requisicao.setNumeroTela(Integer.parseInt(numeroTela));
		System.out.println("acaoRequisicao: " + acaoRequisicao);
		System.out.println("acaoFluxo: " + acaoFluxo);
		System.out.println("numeroFuncionalidade: " + numeroFuncionalidade);
		System.out.println("numeroTela: " + numeroTela);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.faces.event.ActionListener#processAction(javax.faces.event.
	 * ActionEvent ) Todo botao baseado em Action Listern, sera obtidos seus
	 * atributos e adicionados em
	 * 
	 * @param Requisicao
	 */
	@Override
	public void processAction(ActionEvent event) throws AbortProcessingException {

		try {
			System.out.println("--------------------------ComponenteMB-----------------");
			System.out.println("--------------------------processAction----------------");

			System.out.println("Atributos botao acionado: ");

			configurarRequisicao(event);

			ObterMetaDadoDTO obterMetaDadoDTO = getEnvioRequisicaoMetadado();
			MetadadoUI metadoUI;
			metadoUI = this.componenteMetadado.gerar(obterMetaDadoDTO);
			this.definirParametros(metadoUI.getIdentificadoreNegocialMetadados());
			this.definirParametros(metadoUI.getIdentificadoreBeanMetadados());
			carregarValoresOcultos();
			ConversorDados conversorDados = new ConversorDados();

			DadosTela dadosTela = conversorDados.converter(this.getParametros());
			dadosTela.setRequisicaoFuncionalidade(this.requisicao);
			dadosTela.setRequisicaoFuncionalidade(this.requisicao);

			ContextoTela contextoTela = new ContextoTela(dadosTela);
			fluxoNevegacao.nevegar(contextoTela, this.requisicao);
			this.atualizarParametros(dadosTela);
		} catch (InfraEstruturaException | NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * @param dadosTela
	 * @see Atualiza os identificadores dos mapas com objetos que serao usados
	 *      pelos componentes
	 */
	public void atualizarParametros(DadosTela dadosTela) {

		// primeiro procurar algum atributo que esteje dentros do map, se tiver,
		// atualiza
		// se nao tiver, adiciona

		// para cada iteracao procurar dentro do mapa de parametros local
		for (Iterator<IdentificadorNegocial> iteracao = dadosTela.getMapParametroNegocial().keySet()
				.iterator(); iteracao.hasNext();) {

			IdentificadorNegocial identificador = iteracao.next();
			if (identificador != null) {

				Object objeto = this.parametros.get(identificador.getId());
				// Sendo hasmap, o objeto sera atualizado ou adcionado um
				// novo,com um hashmpa,
				// como nao permite chaves duplicadas
				this.parametros.put(identificador.getId(), dadosTela.getMapParametroNegocial().get(identificador));

			}
		}

		// para cada iteracao procurar dentro do mapa de parametros local
		for (Iterator<IdentificadorBean> iteracao = dadosTela.getMapParametroBeanTela().keySet().iterator(); iteracao
				.hasNext();) {

			IdentificadorBean identificador = iteracao.next();
			if (identificador != null) {

				Object objeto = this.bean.get(identificador.getId());
				// Sendo hasmap, o objeto sera atualizado ou adcionado um
				// novo,com um hashmpa,
				// como nao permite chaves duplicadas
				this.bean.put(identificador.getId(), dadosTela.getMapParametroBeanTela().get(identificador));

			}
		}
	}

	private void carregarValoresOcultos() {

		ExternalContext contextoExterno = FacesContext.getCurrentInstance().getExternalContext();
		if (contextoExterno != null) {
			Set<String> names = contextoExterno.getRequestParameterValuesMap().keySet();
			if (names != null) {
				for (String string : names) {

					if (string.contains("parametro_oculto")) {
						System.out.println("Valores Ocultos" + string);
						String[] valor = contextoExterno.getRequestParameterValuesMap().get(string);
						String id = string.replaceAll("form:parametro_oculto_", "");
						Object objeto = this.parametros.get(id);
						// Sendo hasmap, o objeto sera atualizado ou adcionado
						// um
						// novo,com um hashmpa,
						// como nao permite chaves duplicadas
						this.parametros.put(id, valor[0]);
					}

				}

			}

		}

	}

	private void listarValoreRecebidos() {

		System.out.println("Parametros negociais recebido: ");
		for (Iterator<String> iterator = this.parametros.keySet().iterator(); iterator.hasNext();) {
			String key = iterator.next();
			System.out.println("chave: " + key + " valor: " + this.parametros.get(key));
		}

	}

	protected void limparParametros() {
		for (String parametro : parametros.keySet()) {
			parametros.put(parametro, null);
		}
	}

	public Map<String, Object> getParametros() {
		return parametros;
	}

	public void setParametros(Map<String, Object> parametros) {
		this.parametros = parametros;
	}

	public void limparDadosComponente() {

		this.parametros.clear();
	}

	public Requisicao getRequisicao() {
		return requisicao;
	}

	public void setRequisicao(Requisicao Requisicao) {
		this.requisicao = Requisicao;
	}

	public Map<String, Object> getBean() {
		return bean;
	}

	public void setBean(Map<String, Object> bean) {
		this.bean = bean;
	}

}
