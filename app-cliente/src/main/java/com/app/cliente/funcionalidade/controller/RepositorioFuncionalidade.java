package com.app.cliente.funcionalidade.controller;

import java.io.Serializable;
import java.util.List;
import java.util.logging.Logger;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import br.com.app.smart.business.dao.interfaces.IServicoRemoteDAO;
import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.funcionalidade.dto.FuncionalidadeDTO;

@ApplicationScoped
@Named
public class RepositorioFuncionalidade implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private IServicoRemoteDAO<FuncionalidadeDTO> funcionalidadeService;

	private List<FuncionalidadeDTO> repositorioFuncionalidade;

	@PostConstruct
	public void init() {

		listaTodos();

	}

	public void listaTodos() {
		try {
			repositorioFuncionalidade = funcionalidadeService.bustarTodos();
		} catch (InfraEstruturaException | NegocioException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
	}

	public List<FuncionalidadeDTO> getrepositorioFuncionalidade() {
		return repositorioFuncionalidade;
	}

	public void setrepositorioFuncionalidade(List<FuncionalidadeDTO> repositorioFuncionalidade) {
		this.repositorioFuncionalidade = repositorioFuncionalidade;
	}

	public FuncionalidadeDTO get(String id) {
		listaTodos();
		Long idBuscar = Long.valueOf(id);

		if (repositorioFuncionalidade != null && !repositorioFuncionalidade.isEmpty()) {

			FuncionalidadeDTO funcionalidadeDTO = repositorioFuncionalidade.get(0);
			if (funcionalidadeDTO.getId().longValue() == idBuscar.longValue()) {
				return funcionalidadeDTO;
			}

			if (funcionalidadeDTO.getFuncionalidadeFilhos() != null
					&& !funcionalidadeDTO.getFuncionalidadeFilhos().isEmpty()) {

				FuncionalidadeDTO funcionalidadeFilho = get(funcionalidadeDTO.getFuncionalidadeFilhos(), idBuscar);

				if (funcionalidadeFilho != null) {

					return funcionalidadeFilho;
				}
			}
		}

		return null;
	}

	public FuncionalidadeDTO get(List<FuncionalidadeDTO> filhos, Long idBuscar) {

		if (repositorioFuncionalidade != null && !repositorioFuncionalidade.isEmpty()) {

			for (FuncionalidadeDTO funcionalidadeDTO : filhos) {
				if (funcionalidadeDTO.getId().longValue() == idBuscar.longValue()) {
					return funcionalidadeDTO;
				}

				if (funcionalidadeDTO.getFuncionalidadeFilhos() != null
						&& !funcionalidadeDTO.getFuncionalidadeFilhos().isEmpty()) {

					FuncionalidadeDTO funcionalidadeFilho = get(funcionalidadeDTO.getFuncionalidadeFilhos(), idBuscar);

					if (funcionalidadeFilho != null) {

						return funcionalidadeFilho;
					}
				}
			}

		}

		return null;
	}

	public void atualizarRepositorio() {
		listaTodos();
	}

	public List<FuncionalidadeDTO> getRepositorioFuncionalidade() {
		return repositorioFuncionalidade;
	}

	public void setRepositorioFuncionalidade(List<FuncionalidadeDTO> repositorioFuncionalidade) {
		this.repositorioFuncionalidade = repositorioFuncionalidade;
	}
	
	
}
