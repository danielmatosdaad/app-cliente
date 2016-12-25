package com.app.cliente.grupofuncionalidade.controller;

import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.app.smart.business.dao.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.funcionalidade.dto.GrupoFuncionalidadeDTO;

@Model
public class GrupoFuncionalidadeController {
	
	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@Inject
	private Event<GrupoFuncionalidadeDTO> grupoFuncionalidadeEventSrc;

	@Inject
	IServicoRemoteDAO<GrupoFuncionalidadeDTO> grupoFuncionalidadeService;

	@Produces
	@Named
	private GrupoFuncionalidadeDTO grupoFuncionalidadeDTO;

	@Inject
	private RepositorioGrupoFuncionalidade repositorioGrupoFuncionalidade;
	
	public void onGrupoFuncionalidadeListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final GrupoFuncionalidadeDTO grupoFuncionalidadeDTO) {
		initGrupoFuncionalidadeDTO();
		this.repositorioGrupoFuncionalidade.atualizarRepositorio();
	}


	@PostConstruct
	public void initGrupoFuncionalidadeDTO() {
		this.grupoFuncionalidadeDTO = criarGrupoFuncionalidade();
	}

	private GrupoFuncionalidadeDTO criarGrupoFuncionalidade() {
		return new GrupoFuncionalidadeDTO();
	}

	public void registrar() throws Exception {
		try {

			if (grupoFuncionalidadeService != null) {
				System.out.println(grupoFuncionalidadeService);
			}
			if (grupoFuncionalidadeDTO != null) {
				log.info("Reginstrando parametro");
				GrupoFuncionalidadeDTO GrupoFuncionalidadeDTORecebido = grupoFuncionalidadeService.adiconar(grupoFuncionalidadeDTO);
				System.out.println(GrupoFuncionalidadeDTORecebido);
				System.out.println(GrupoFuncionalidadeDTORecebido.getId());
				log.info("Reginstrado com sucesso - id:" + GrupoFuncionalidadeDTORecebido.getId());
				grupoFuncionalidadeEventSrc.fire(grupoFuncionalidadeDTO);
			}
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado com sucesso!",
					"Registrado com sucesso.");
			facesContext.addMessage(null, m);
			initGrupoFuncionalidadeDTO();
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Nao foi posssivel registrar");
			facesContext.addMessage(null, m);
		}
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

	public GrupoFuncionalidadeDTO getGrupoFuncionalidadeDTO() {
		return grupoFuncionalidadeDTO;
	}

	public void setGrupoFuncionalidadeDTO(GrupoFuncionalidadeDTO grupoFuncionalidadeDTO) {
		this.grupoFuncionalidadeDTO = grupoFuncionalidadeDTO;
	}
	
	
}
