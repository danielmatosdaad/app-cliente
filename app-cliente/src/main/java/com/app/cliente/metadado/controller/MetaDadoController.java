package com.app.cliente.metadado.controller;

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
import javax.inject.Inject;
import javax.inject.Named;

import br.com.app.smart.business.dao.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.dto.RegistroAuditoriaDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.funcionalidade.dto.MetaDadoDTO;
import br.com.projeto.metadado.regras.IRegrasMetaDado;

@Model
public class MetaDadoController {

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@Inject
	private IServicoRemoteDAO<MetaDadoDTO> metaDadoService;
	
	@Inject
	private IRegrasMetaDado regrasMetadados;

	@Inject
	private Event<MetaDadoDTO> metadadoEventSrc;

	@Produces
	@Named
	private MetaDadoDTO metaDadoDTO;

	private List<MetaDadoDTO> repositorioMetaDado;

	@Produces
	@Named
	public List<MetaDadoDTO> getRepositorioMetaDado() {
		return repositorioMetaDado;
	}

	public void onMetaDaDoListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final MetaDadoDTO metaDado) {
		listaTodosMetaDados();
	}

	public void listaTodosMetaDados() {
		try {
			repositorioMetaDado = metaDadoService.bustarTodos();
		} catch (InfraEstruturaException | NegocioException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
	}

	@PostConstruct
	public void initMetaDadoDTO() {

		this.metaDadoDTO = criarMetaDado();
		listaTodosMetaDados();
	}

	private MetaDadoDTO criarMetaDado() {

		RegistroAuditoriaDTO r = new RegistroAuditoriaDTO();
		r.setDataCadastro(new Date());

		MetaDadoDTO m = new MetaDadoDTO();
		m.setRegistroAuditoria(r);
		return m;
	}

	public void registrar() throws Exception {
		try {

			if (metaDadoService != null) {
				System.out.println(metaDadoService);
			}
			if (metaDadoDTO != null) {
				log.info("Reginstrando metaDado");
				metaDadoDTO = metaDadoService.adiconar(metaDadoDTO);
				System.out.println(metaDadoDTO);
				System.out.println(metaDadoDTO.getId());
				regrasMetadados.atualizarTela(metaDadoDTO.getId());
				log.info("Reginstrado com sucesso - id:" + metaDadoDTO.getId());
				metadadoEventSrc.fire(metaDadoDTO);
			}
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado com sucesso!",
					"Registrado com sucesso.");
			facesContext.addMessage(null, m);
			initMetaDadoDTO();
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

}
