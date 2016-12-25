package com.app.cliente.identifiadores.controller;

import java.io.Serializable;
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

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;

import br.com.app.smart.business.dao.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.funcionalidade.dto.IdentificadorDTO;
import br.com.app.smart.business.funcionalidade.dto.TipoIdentificadorDTO;

@Model
public class IndentificadoresController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@Inject
	private Event<IdentificadorDTO> IdentificadorEventSrc;

	@Inject
	private RepositorioIdentificador repositorioIdentificador;

	@Inject
	private IServicoRemoteDAO<IdentificadorDTO> identificadorService;

	@Produces
	@Named
	private IdentificadorDTO identificadorDTO;

	private List<IdentificadorDTO> listaIdentificador;

	public void onIdentificadorListChanged(
			@Observes(notifyObserver = Reception.IF_EXISTS) final IdentificadorDTO Identificador) {
		listaTodosIdentificadors();
	}

	public void listaTodosIdentificadors() {
		this.listaIdentificador = repositorioIdentificador.getListaIdentificador();
	}

	@PostConstruct
	public void initIdentificadorDTO() {

		this.identificadorDTO = criarIdentificador();
		listaTodosIdentificadors();
	}

	private IdentificadorDTO criarIdentificador() {
		return new IdentificadorDTO();
	}

	public void registrar() throws Exception {
		try {

			if (this.identificadorService != null) {
				System.out.println(identificadorService);
			}
			if (identificadorDTO != null) {
				log.info("Reginstrando Identificador");
				identificadorDTO.setDataInclusao(new Date());
				IdentificadorDTO IdentificadorDTORecebido = identificadorService.adiconar(identificadorDTO);
				System.out.println(IdentificadorDTORecebido);
				System.out.println(IdentificadorDTORecebido.getId());
				log.info("Reginstrado com sucesso - id:" + IdentificadorDTORecebido.getId());
				IdentificadorEventSrc.fire(identificadorDTO);
				this.repositorioIdentificador.atualizarRepositorio();
			}
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado com sucesso!",
					"Registrado com sucesso.");
			facesContext.addMessage(null, m);
			initIdentificadorDTO();
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

	public List getListaTipoIdentificadors() {
		List items = new ArrayList();
		for (TipoIdentificadorDTO tipo : TipoIdentificadorDTO.values()) {
			items.add(new SelectItem(tipo, tipo.getTexto()));
		}
		return items;
	}

	public List<IdentificadorDTO> getListaIdentificador() {
		return listaIdentificador;
	}

	public void setListaIdentificador(List<IdentificadorDTO> listaIdentificador) {
		this.listaIdentificador = listaIdentificador;
	}

	public void onRowEdit(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Identificador Edited", "y");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onRowCancel(RowEditEvent event) {
		FacesMessage msg = new FacesMessage("Edit Cancelled", "x");
		FacesContext.getCurrentInstance().addMessage(null, msg);
	}

	public void onCellEdit(CellEditEvent event) {
		Object oldValue = event.getOldValue();
		Object newValue = event.getNewValue();

		if (newValue != null && !newValue.equals(oldValue)) {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cell Changed",
					"Old: " + oldValue + ", New:" + newValue);
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}

	public IdentificadorDTO getIdentificadorDTO() {
		return identificadorDTO;
	}

	public void setIdentificadorDTO(IdentificadorDTO identificadorDTO) {
		this.identificadorDTO = identificadorDTO;
	}

}
