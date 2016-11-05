package com.app.cliente.adm.perfil;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import br.com.app.smart.business.dao.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.funcionalidade.dto.PerfilDTO;

@ApplicationScoped
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

	@PostConstruct
	public void init() {

		listaTodosPerfis();

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
}
