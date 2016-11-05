package com.app.cliente.parametro.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;


import br.com.app.smart.business.dao.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.parametro.dto.ParametroDTO;
import br.com.app.smart.business.parametro.dto.TipoParametroDTO;

@Model
public class ParametroController {

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@Inject
	private Event<ParametroDTO> parametroEventSrc;

	@Inject
	IServicoRemoteDAO<ParametroDTO> parametroService;

	@Produces
	@Named
	private ParametroDTO parametroDTO;

	private List<ParametroDTO> repositorioParametro;

	@Produces
	@Named
	public List<ParametroDTO> getRepositorioParametro() {
		return repositorioParametro;
	}

	public void onParametroListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final ParametroDTO parametro) {
		listaTodosParametros();
	}

	public void listaTodosParametros() {
		try {
			repositorioParametro = parametroService.bustarTodos();
		} catch (InfraEstruturaException | NegocioException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
	}

	@PostConstruct
	public void initParametroDTO() {

		this.parametroDTO = criarParametro();
		listaTodosParametros();
	}

	private ParametroDTO criarParametro() {
		return new ParametroDTO();
	}

	public void registrar() throws Exception {
		try {

			if (parametroService != null) {
				System.out.println(parametroService);
			}
			if (parametroDTO != null) {
				log.info("Reginstrando parametro");
				parametroDTO.setDataInclusao(new Date());
				ParametroDTO parametroDTORecebido = parametroService.adiconar(parametroDTO);
				System.out.println(parametroDTORecebido);
				System.out.println(parametroDTORecebido.getId());
				log.info("Reginstrado com sucesso - id:" + parametroDTORecebido.getId());
				parametroEventSrc.fire(parametroDTO);
			}
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado com sucesso!",
					"Registrado com sucesso.");
			facesContext.addMessage(null, m);
			initParametroDTO();
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

	public List getListaTipoParametros() {
		List items = new ArrayList();
		for (TipoParametroDTO tipo : TipoParametroDTO.values()) {
			items.add(new SelectItem(tipo, tipo.getTexto()));
		}
		return items;
	}
}
