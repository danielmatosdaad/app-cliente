package com.app.cliente.adm.perfil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.enterprise.event.Event;
import javax.enterprise.event.Observes;
import javax.enterprise.event.Reception;
import javax.enterprise.inject.Produces;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.app.smart.business.dao.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.funcionalidade.dto.PerfilDTO;

@SessionScoped
@Named
public class PerfilController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private FacesContext facesContext;

	@Inject
	private Event<PerfilDTO> perfilEventSrc;

	@Inject
	IServicoRemoteDAO<PerfilDTO> perfilService;

	@Produces
	@Named
	private PerfilDTO perfilPaiDTO;

	@Produces
	@Named
	private PerfilDTO perfilDTO;

	private List<PerfilDTO> repositorioPerfil;

	// @ManagedProperty("#{repositorioPerfil}")
	// private RepositorioPerfil repositorioPerfil;

	private TreeNode root;

	@Produces
	@Named
	public List<PerfilDTO> getRepositorioPerfil() {
		return repositorioPerfil;
	}

	public void onPerfilListChanged(@Observes(notifyObserver = Reception.IF_EXISTS) final PerfilDTO perfil) {
		init();
	}

	public void listaTodosPerfis() {
		try {
			repositorioPerfil = perfilService.bustarTodos();
		} catch (InfraEstruturaException | NegocioException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
	}

	private PerfilDTO criarPerfil() {
		return new PerfilDTO();
	}

	public void registrar() throws Exception {
		try {

			if (perfilService == null) {
				FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Servico indisponivel!",
						"Servico indisponivel.");
				facesContext.addMessage(null, m);
			}
			if (perfilDTO != null) {
				log.info("Reginstrando perfil");
				if (this.perfilPaiDTO != null && this.perfilPaiDTO.getId() != null) {
					perfilDTO.setPerfilPai(this.perfilPaiDTO);
				} else {

					perfilDTO.setPerfilPai(null);
				}

				perfilDTO = perfilService.adiconar(perfilDTO);
				System.out.println(perfilDTO);
				System.out.println(perfilDTO.getId());
				log.info("Reginstrado com sucesso - id:" + perfilDTO.getId());
				perfilEventSrc.fire(perfilDTO);
			}
			FacesMessage m = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registrado com sucesso!",
					"Registrado com sucesso.");
			facesContext.addMessage(null, m);
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

	@PostConstruct
	public void init() {

		this.perfilDTO = criarPerfil();
		this.perfilPaiDTO = criarPerfil();
		listaTodosPerfis();
		if (repositorioPerfil == null || repositorioPerfil.isEmpty()) {
			root = new DefaultTreeNode("Perfis ainda nao configurados", null);
			return;
		}
		root = new DefaultTreeNode(repositorioPerfil.get(0).getNomePerfil(), null);
		root.setExpanded(true);

		configurarSubPerfis(repositorioPerfil.get(0), root);

	}

	private void configurarSubPerfis(PerfilDTO perfilDTO, TreeNode root) {
		if (perfilDTO.getPerfilFilhos() != null) {
			for (PerfilDTO subPerfil : perfilDTO.getPerfilFilhos()) {
				TreeNode node = new DefaultTreeNode(subPerfil.getNomePerfil(), root);
				node.setExpanded(true);
				root.getChildren().add(node);
				configurarSubPerfis(subPerfil, node);
			}

		}
	}

	public PerfilDTO getPerfilPaiDTO() {
		return perfilPaiDTO;
	}

	public void setPerfilPaiDTO(PerfilDTO perfilPaiDTO) {
		this.perfilPaiDTO = perfilPaiDTO;
	}

	public PerfilDTO getPerfilDTO() {
		return perfilDTO;
	}

	public void setPerfilDTO(PerfilDTO perfilDTO) {
		this.perfilDTO = perfilDTO;
	}

	public void setRepositorioPerfil(List<PerfilDTO> repositorioPerfil) {
		this.repositorioPerfil = repositorioPerfil;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getRoot() {
		return root;
	}

	private void configurarSubPerfisSelectItem(PerfilDTO perfilDTO, List items) {
		if (perfilDTO != null) {
			items.add(new SelectItem(perfilDTO, perfilDTO.getNomePerfil()));
			if (perfilDTO.getPerfilFilhos() != null && !perfilDTO.getPerfilFilhos().isEmpty()) {
				for (PerfilDTO filhos : perfilDTO.getPerfilFilhos()) {
					configurarSubPerfisSelectItem(filhos, items);
				}
				
			}

		}
	}

	public List getListaPerfilSelectItem() {
		List items = new ArrayList();

		if (this.repositorioPerfil != null && !this.repositorioPerfil.isEmpty()) {
			configurarSubPerfisSelectItem(repositorioPerfil.get(0), items);
		}
		return items;
	}
}
