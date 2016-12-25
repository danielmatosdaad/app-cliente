package com.app.cliente.funcionalidade.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.enterprise.inject.Model;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import com.app.cliente.adm.perfil.RepositorioPerfil;
import com.app.cliente.grupofuncionalidade.controller.RepositorioGrupoFuncionalidade;
import com.app.cliente.metadado.controller.RepositorioMetaDado;

import br.com.app.smart.business.dao.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.dto.RegistroAuditoriaDTO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.funcionalidade.dto.FuncionalidadeDTO;
import br.com.app.smart.business.funcionalidade.dto.GrupoFuncionalidadeDTO;
import br.com.app.smart.business.funcionalidade.dto.MetaDadoDTO;
import br.com.app.smart.business.funcionalidade.dto.PerfilDTO;
import br.com.app.smart.business.funcionalidade.interfaces.IFuncionalidadeDAO;
import br.com.app.smart.business.parametro.dto.TipoParametroDTO;

import javax.annotation.PostConstruct;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;

@Model
public class FuncionalidadeController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@Inject
	private IServicoRemoteDAO<FuncionalidadeDTO> funcionalidadeService;

	@Inject
	private RepositorioGrupoFuncionalidade repositorioGrupoFuncionalidade;

	@Inject
	private RepositorioMetaDado repositorioMetaDado;

	@Inject
	private RepositorioPerfil repositorioPerfil;

	@Inject
	private RepositorioFuncionalidade repositorioFuncionalidade;

	@Produces
	@Named
	private FuncionalidadeDTO funcionalidadeRegistro;

	@Named
	private PerfilDTO perfilRegistro;

	@Named
	private GrupoFuncionalidadeDTO grupoFuncionalidadeRegistro;

	@Named
	private MetaDadoDTO metaDadoRegistro;

	@Inject
	private Event<FuncionalidadeDTO> funcionalidadeEventSrc;

	@Inject
	private IFuncionalidadeDAO funcionalidadeDAO;
	
	public void onFireEvenChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final FuncionalidadeDTO dto) throws InfraEstruturaException, NegocioException {
		initFuncionalidadeDTO();
	}

	@PostConstruct
	private void initFuncionalidadeDTO() throws InfraEstruturaException, NegocioException {
		this.funcionalidadeRegistro = criarFuncionalidade();

		this.repositorioGrupoFuncionalidade.atualizarRepositorio();
		this.repositorioMetaDado.atualizarRepositorio();
		this.repositorioPerfil.atualizarRepositorio();
		this.repositorioFuncionalidade.atualizarRepositorio();
	}

	private FuncionalidadeDTO criarFuncionalidade() {
		return new FuncionalidadeDTO();
	}

	public void registrar() throws Exception {
		try {

			if (funcionalidadeService != null) {
				System.out.println(funcionalidadeService);
			}
			if (funcionalidadeRegistro != null) {

				this.funcionalidadeRegistro.setPerfil(this.perfilRegistro);
				this.funcionalidadeRegistro.setGrupoFuncionalidade(this.grupoFuncionalidadeRegistro);
				this.metaDadoRegistro.setFuncionalidade(this.funcionalidadeRegistro);
				List<MetaDadoDTO> listaMetadado = new ArrayList<MetaDadoDTO>();
				listaMetadado.add(this.metaDadoRegistro);
				
				this.funcionalidadeRegistro.setMetadados(listaMetadado);
				FuncionalidadeDTO FuncionalidadeRebidoDTO = funcionalidadeService.adiconar(funcionalidadeRegistro);
				funcionalidadeEventSrc.fire(this.funcionalidadeRegistro);
				FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado com sucesso!",
						"Registrado com sucesso.");
				facesContext.addMessage(null, m);
				initFuncionalidadeDTO();
			}
			
		} catch (Exception e) {
			String errorMessage = getRootErrorMessage(e);
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_ERROR, errorMessage, "Nao foi posssivel registrar");
			facesContext.addMessage(null, m);
		}
	}
	
	public void registrarFuncionalidadeSemRelacionamentos() {

		try {
			FuncionalidadeDTO FuncionalidadeDTO = this.funcionalidadeDAO.adicionarFuncionalidadeSemRelacionamento(this.funcionalidadeRegistro);
			System.out.println(FuncionalidadeDTO);
			System.out.println(FuncionalidadeDTO.getId());
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado com sucesso!",
					"Registrado com sucesso.");
			facesContext.addMessage(null, m);
			funcionalidadeEventSrc.fire(this.funcionalidadeRegistro);
			
		} catch (Exception e) {
			e.printStackTrace();
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

	public List getSelectItemPerfil() {
		List items = new ArrayList();

		if (this.repositorioPerfil != null) {

			if (this.repositorioPerfil.getRepositorioPerfil() != null) {
				for (PerfilDTO dto : this.repositorioPerfil.getRepositorioPerfil()) {
					items.add(new SelectItem(dto, dto.getNomePerfil()));
				}

			}
		}

		return items;
	}

	public List getSelectItemMetaDado() {
		List items = new ArrayList();

		if (this.repositorioMetaDado != null) {
			if (repositorioMetaDado.getMetadadoRepositorio() != null) {

				for (MetaDadoDTO dto : repositorioMetaDado.getMetadadoRepositorio()) {
					items.add(new SelectItem(dto, dto.getNomeTela()));
				}
			}

		}

		return items;
	}

	public List getSelectItemGrupoFuncionalidade() {
		List items = new ArrayList();

		if (this.repositorioGrupoFuncionalidade != null) {
			if (this.repositorioGrupoFuncionalidade.getGrupoFuncionalidadeRepositorio() != null) {
				for (GrupoFuncionalidadeDTO dto : this.repositorioGrupoFuncionalidade
						.getGrupoFuncionalidadeRepositorio()) {
					items.add(new SelectItem(dto, dto.getNomeGrupoFuncionalidade()));
				}
			}

		}

		return items;
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

	public MetaDadoDTO getMetaDadoRegistro() {
		return metaDadoRegistro;
	}

	public void setMetaDadoRegistro(MetaDadoDTO metaDadoRegistro) {
		this.metaDadoRegistro = metaDadoRegistro;
	}

	public RepositorioGrupoFuncionalidade getRepositorioGrupoFuncionalidade() {
		return repositorioGrupoFuncionalidade;
	}

	public void setRepositorioGrupoFuncionalidade(RepositorioGrupoFuncionalidade repositorioGrupoFuncionalidade) {
		this.repositorioGrupoFuncionalidade = repositorioGrupoFuncionalidade;
	}

	public RepositorioMetaDado getRepositorioMetaDado() {
		return repositorioMetaDado;
	}

	public void setRepositorioMetaDado(RepositorioMetaDado repositorioMetaDado) {
		this.repositorioMetaDado = repositorioMetaDado;
	}

	public RepositorioPerfil getRepositorioPerfil() {
		return repositorioPerfil;
	}

	public void setRepositorioPerfil(RepositorioPerfil repositorioPerfil) {
		this.repositorioPerfil = repositorioPerfil;
	}

	public RepositorioFuncionalidade getRepositorioFuncionalidade() {
		return repositorioFuncionalidade;
	}

	public void setRepositorioFuncionalidade(RepositorioFuncionalidade repositorioFuncionalidade) {
		this.repositorioFuncionalidade = repositorioFuncionalidade;
	}
	
	

}
