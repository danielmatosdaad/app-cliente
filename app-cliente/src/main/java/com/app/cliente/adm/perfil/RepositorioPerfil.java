package com.app.cliente.adm.perfil;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import br.com.app.smart.business.dao.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.funcionalidade.dto.PerfilDTO;

@ApplicationScoped
@Named
public class RepositorioPerfil implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	IServicoRemoteDAO<PerfilDTO> perfilService;


	private List<PerfilDTO> repositorioPerfil;

	private TreeNode root;
	
	@PostConstruct
	public void init() {

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

	public void listaTodosPerfis() {
		try {
			repositorioPerfil = perfilService.bustarTodos();
		} catch (InfraEstruturaException | NegocioException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
	}

	public List<PerfilDTO> getRepositorioPerfil() {
		return repositorioPerfil;
	}

	public void setRepositorioPerfil(List<PerfilDTO> repositorioPerfil) {
		this.repositorioPerfil = repositorioPerfil;
	}

	public PerfilDTO get(String id) {
		listaTodosPerfis();
		Long idBuscar = Long.valueOf(id);

		if (repositorioPerfil != null && !repositorioPerfil.isEmpty()) {

			PerfilDTO perfilDTO = repositorioPerfil.get(0);
			if (perfilDTO.getId().longValue() == idBuscar.longValue()) {
				return perfilDTO;
			}

			if (perfilDTO.getPerfilFilhos() != null && !perfilDTO.getPerfilFilhos().isEmpty()) {

				PerfilDTO perfilFilho = get(perfilDTO.getPerfilFilhos(), idBuscar);

				if (perfilFilho != null) {

					return perfilFilho;
				}
			}
		}

		return null;
	}

	PerfilDTO get(List<PerfilDTO> filhos, Long idBuscar) {

		if (repositorioPerfil != null && !repositorioPerfil.isEmpty()) {

			for (PerfilDTO perfilDTO : filhos) {
				if (perfilDTO.getId().longValue() == idBuscar.longValue()) {
					return perfilDTO;
				}

				if (perfilDTO.getPerfilFilhos() != null && !perfilDTO.getPerfilFilhos().isEmpty()) {

					PerfilDTO perfilFilho = get(perfilDTO.getPerfilFilhos(), idBuscar);

					if (perfilFilho != null) {

						return perfilFilho;
					}
				}
			}

		}

		return null;
	}

	public void atualizarRepositorio() {
		listaTodosPerfis();
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

	public List getSelectItemPerfil() {
		List items = new ArrayList();

		if (this.getRepositorioPerfil() != null) {
			for (PerfilDTO dto : this.getRepositorioPerfil()) {
				items.add(new SelectItem(dto, dto.getNomePerfil()));
			}

		}
		return items;
	}
	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getRoot() {
		return root;
	}
}
