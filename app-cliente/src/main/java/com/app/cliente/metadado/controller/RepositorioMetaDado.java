package com.app.cliente.metadado.controller;

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
import br.com.app.smart.business.funcionalidade.dto.MetaDadoDTO;
import br.com.app.smart.business.funcionalidade.dto.PerfilDTO;

@ApplicationScoped
public class RepositorioMetaDado implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private IServicoRemoteDAO<MetaDadoDTO> metadadoService;

	private List<MetaDadoDTO> metadadoRepositorio;
	
	@PostConstruct
	public void init() {

		listaTodos();

	}

	public void listaTodos() {
		try {
			metadadoRepositorio = metadadoService.bustarTodos();
		} catch (InfraEstruturaException | NegocioException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
	}

	public MetaDadoDTO get(String id) {

		Long idBuscar = Long.valueOf(id);

		if (metadadoRepositorio != null && !metadadoRepositorio.isEmpty()) {

			for (MetaDadoDTO metaDadoDTO : metadadoRepositorio) {
				if (metaDadoDTO.getId().longValue() == idBuscar.longValue()) {
					return metaDadoDTO;
				}
			}
		}

		return null;
	}

	public void atualizarRepositorio() {

		listaTodos();
	}

	public List<MetaDadoDTO> getMetadadoRepositorio() {
		return metadadoRepositorio;
	}

	
	
}
