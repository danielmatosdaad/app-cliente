package com.app.cliente.identifiadores.controller;

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
import br.com.app.smart.business.funcionalidade.dto.IdentificadorDTO;

@ApplicationScoped
@Named("repIdentificador")
public class RepositorioIdentificador implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private Logger log;

	@Inject
	private IServicoRemoteDAO<IdentificadorDTO> identificadorService;

	private List<IdentificadorDTO> listaIdentificador;

	@PostConstruct
	public void init() {

		listaTodos();

	}

	public void listaTodos() {
		try {
			listaIdentificador = identificadorService.bustarTodos();
		} catch (InfraEstruturaException | NegocioException e) {
			e.printStackTrace();
			log.info(e.getMessage());
		}
	}

	public IdentificadorDTO get(String id) {
		listaTodos();
		Long idBuscar = Long.valueOf(id);

		if (listaIdentificador != null && !listaIdentificador.isEmpty()) {

			IdentificadorDTO IdentificadorDTO = listaIdentificador.get(0);
			if (IdentificadorDTO.getId().longValue() == idBuscar.longValue()) {
				return IdentificadorDTO;
			}

		}

		return null;
	}

	public IdentificadorDTO get(List<IdentificadorDTO> filhos, Long idBuscar) {

		if (listaIdentificador != null && !listaIdentificador.isEmpty()) {

			for (IdentificadorDTO IdentificadorDTO : filhos) {
				if (IdentificadorDTO.getId().longValue() == idBuscar.longValue()) {
					return IdentificadorDTO;
				}
			}

		}

		return null;
	}

	public void atualizarRepositorio() {
		listaTodos();
	}

	public List<IdentificadorDTO> getListaIdentificador() {
		return listaIdentificador;
	}

	public void setListaIdentificador(List<IdentificadorDTO> listaIdentificador) {
		this.listaIdentificador = listaIdentificador;
	}
	
	public List getListaIdentificadores() {
		
		List items = new ArrayList();
		if(this.listaIdentificador!=null){
			
			for (IdentificadorDTO identificadorDTO : listaIdentificador) {
				
				items.add(new SelectItem(identificadorDTO, identificadorDTO.getValor()));
			}
		}
		
		return items;
	}

}
