package com.app.cliente.configuracaometadado.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import com.app.cliente.adm.perfil.RepositorioPerfil;
import com.app.cliente.funcionalidade.controller.RepositorioFuncionalidade;
import com.app.cliente.grupofuncionalidade.controller.RepositorioGrupoFuncionalidade;

import br.com.app.smart.business.dao.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.dto.RegistroAuditoriaDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.funcionalidade.dto.FuncionalidadeDTO;
import br.com.app.smart.business.funcionalidade.dto.GrupoFuncionalidadeDTO;
import br.com.app.smart.business.funcionalidade.dto.MetaDadoDTO;
import br.com.app.smart.business.funcionalidade.dto.PerfilDTO;
import br.com.app.smart.business.processoconfiguracao.dto.ProcessoConfiguracaoDTO;
import br.com.app.smart.business.processoconfiguracao.interfaces.IProcessoConfiguracaoRemote;

@Named(value = "cadCpnTela")
@RequestScoped
public class CadastroComponenteTela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@Inject
	private IServicoRemoteDAO<MetaDadoDTO> metaDadoService;

	@Inject
	private ContextoConfiguraComponenteTela contexto;

	@Inject
	private Event<MetaDadoDTO> metadadoEventSrc;

	@Named
	private MetaDadoDTO metaDadoDTO;

	@Named
	private FuncionalidadeDTO funcionalidadeRegistro;

	@Named
	private PerfilDTO perfilRegistro;

	@Named
	private GrupoFuncionalidadeDTO grupoFuncionalidadeRegistro;

	private List<MetaDadoDTO> listaMetadado;

	@Inject
	private IProcessoConfiguracaoRemote processoConfiguracaoRemote;

	private ProcessoConfiguracaoDTO processoConfiguracaoDTO;

	public void onMetaDaDoListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final MetaDadoDTO metaDado) {
		listaTodosMetaDados();
	}

	public void listaTodosMetaDados() {
		try {
			listaMetadado = metaDadoService.bustarTodos();
		} catch (InfraEstruturaException | NegocioException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
	}

	@PostConstruct
	public void initMetaDadoDTO() {
		this.metaDadoDTO = criarMetaDado();
		this.funcionalidadeRegistro = new FuncionalidadeDTO();
		this.grupoFuncionalidadeRegistro = new GrupoFuncionalidadeDTO();
		this.perfilRegistro = new PerfilDTO();

		this.processoConfiguracaoDTO = new ProcessoConfiguracaoDTO();

		listaTodosMetaDados();
		this.metaDadoDTO.setXml(this.contexto.getOutputComponenteTelaUI());
	}

	private MetaDadoDTO criarMetaDado() {

		RegistroAuditoriaDTO r = new RegistroAuditoriaDTO();
		r.setDataCadastro(new Date());

		MetaDadoDTO m = new MetaDadoDTO();
		m.setRegistroAuditoria(r);
		return m;
	}

	public String registrar() throws Exception {
		try {

			if (metaDadoService == null || processoConfiguracaoRemote == null) {
				System.out.println(metaDadoService);
				return "/adm/configuraTela/cadastraComponente";
			}

			if (metaDadoDTO != null) {
				log.info("Reginstrando metaDado");
				this.metaDadoDTO.setIdentificadores(this.contexto.getIdentificadores());
				this.metaDadoDTO
						.setXml(this.contexto.getResultadoConvercaoComponenteUI().getComponenteXml().get(0).toString());
				this.metaDadoDTO.setXhtml(this.contexto.getOutputComponenteTelaUI());
				this.processoConfiguracaoDTO.setMetadadoDTO(metaDadoDTO);
				this.processoConfiguracaoDTO.setFuncionalidadeDTO(funcionalidadeRegistro);
				this.processoConfiguracaoDTO.setGrupoFuncionalidadeDTO(grupoFuncionalidadeRegistro);
				this.processoConfiguracaoDTO.setPerfilDTO(perfilRegistro);
				this.processoConfiguracaoDTO.setIdentificadoresDTO(this.contexto.getIdentificadores());
				this.processoConfiguracaoDTO = processoConfiguracaoRemote
						.configurarNevegacaoSistema(this.processoConfiguracaoDTO);
				log.info("Reginstrado com sucesso - id:" + metaDadoDTO.getId());
				log.info("Reginstrado com sucesso" + this.processoConfiguracaoDTO);
			}
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado com sucesso!",
					"Registrado com sucesso.");
			facesContext.addMessage(null, m);
			initMetaDadoDTO();
			return "/adm/configuraTela/sucessoCriacaoTela";
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Nao foi posssivel registrar");
			facesContext.addMessage(null, m);
		}
		
		return null;
	}

	private String getRootErrorMessage(Exception e) {
		// Default to general error message that registration failed.
		String errorMessage = "Algo nao funcionou bem. Para maiores informacoes consultar o log";
		if (e == null) {
			// This shouldn't happen, but return the default messages
			return errorMessage;
		}

		// Start with the exception and recurse to find the root cause
		Throwable t = e;
		while (t != null) {
			// Get the message from the Throwable class instance
			errorMessage = t.getLocalizedMessage();
			t = t.getCause();
		}
		// This is the root cause message
		return errorMessage;
	}

	public List<MetaDadoDTO> getListaMetadado() {
		try {
			return metaDadoService.bustarTodos();
		} catch (InfraEstruturaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		;
		return listaMetadado;
	}

	public void setListaMetadado(List<MetaDadoDTO> listaMetadado) {
		this.listaMetadado = listaMetadado;
	}

	public MetaDadoDTO getMetaDadoDTO() {
		return metaDadoDTO;
	}

	public void setMetaDadoDTO(MetaDadoDTO metaDadoDTO) {
		this.metaDadoDTO = metaDadoDTO;
	}

	public ContextoConfiguraComponenteTela getContexto() {
		return contexto;
	}

	public void setContexto(ContextoConfiguraComponenteTela contexto) {
		this.contexto = contexto;
	}

	public FuncionalidadeDTO getFuncionalidadeRegistro() {
		return funcionalidadeRegistro;
	}

	public void setFuncionalidadeRegistro(FuncionalidadeDTO funcionalidadeRegistro) {
		this.funcionalidadeRegistro = funcionalidadeRegistro;
	}

	public PerfilDTO getPerfilRegistro() {
		return perfilRegistro;
	}

	public void setPerfilRegistro(PerfilDTO perfilRegistro) {
		this.perfilRegistro = perfilRegistro;
	}

	public GrupoFuncionalidadeDTO getGrupoFuncionalidadeRegistro() {
		return grupoFuncionalidadeRegistro;
	}

	public void setGrupoFuncionalidadeRegistro(GrupoFuncionalidadeDTO grupoFuncionalidadeRegistro) {
		this.grupoFuncionalidadeRegistro = grupoFuncionalidadeRegistro;
	}

	public IProcessoConfiguracaoRemote getProcessoConfiguracaoRemote() {
		return processoConfiguracaoRemote;
	}

	public void setProcessoConfiguracaoRemote(IProcessoConfiguracaoRemote processoConfiguracaoRemote) {
		this.processoConfiguracaoRemote = processoConfiguracaoRemote;
	}

}
