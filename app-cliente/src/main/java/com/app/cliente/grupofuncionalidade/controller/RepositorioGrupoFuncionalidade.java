package com.app.cliente.grupofuncionalidade.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.model.SelectItem;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.app.smart.business.dao.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.funcionalidade.dto.GrupoFuncionalidadeDTO;

@ApplicationScoped
@Named
public class RepositorioGrupoFuncionalidade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private IServicoRemoteDAO<GrupoFuncionalidadeDTO> grupoFuncionalidadeService;

	private List<GrupoFuncionalidadeDTO> grupoFuncionalidadeRepositorio;

	@PostConstruct
	public void init() {

		listaTodos();

	}

	public void listaTodos() {
		try {
			this.grupoFuncionalidadeRepositorio = grupoFuncionalidadeService.bustarTodos();
		} catch (InfraEstruturaException | NegocioException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
	}

	public GrupoFuncionalidadeDTO get(String id) {

		Long idBuscar = Long.valueOf(id);

		if (grupoFuncionalidadeRepositorio != null && !grupoFuncionalidadeRepositorio.isEmpty()) {

			for (GrupoFuncionalidadeDTO GrupoFuncionalidadeDTO : grupoFuncionalidadeRepositorio) {
				if (GrupoFuncionalidadeDTO.getId().longValue() == idBuscar.longValue()) {
					return GrupoFuncionalidadeDTO;
				}
			}
		}

		return null;
	}

	public void atualizarRepositorio() {

		listaTodos();
	}

	public List<GrupoFuncionalidadeDTO> getGrupoFuncionalidadeRepositorio() {
		return grupoFuncionalidadeRepositorio;
	}

	public List getSelectItemGrupoFuncionalidade() {
		List items = new ArrayList();

		if (this.getGrupoFuncionalidadeRepositorio() != null) {
			for (GrupoFuncionalidadeDTO dto : this.getGrupoFuncionalidadeRepositorio()) {
				items.add(new SelectItem(dto, dto.getNomeGrupoFuncionalidade()));
			}
		}

		return items;
	}

}
