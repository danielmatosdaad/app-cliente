package com.app.cliente.configuracaometadado.controller;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;
import org.primefaces.event.DragDropEvent;

import com.br.app.smart.business.app_cliente.util.TransferirDados;

import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.tela.componente.dto.CompositeDTO;
import br.com.app.smart.business.tela.componente.dto.CompositeInterfaceDTO;
import br.projeto.bean.ComponenteBean;
import br.projeto.util.ComponentesRegistrados;

@SessionScoped
@Named("escCpnTela")
public class EscolhaComponenteTela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ContextoConfiguraComponenteTela contexto;

	private List<CompositeInterfaceDTO> componenteTelaRegistrados;

	private CompositeInterfaceDTO removerCompositeEscolhido;

	@PostConstruct
	public void init() {

		List<ComponenteBean> lista = ComponentesRegistrados.buscarComponentes();
		List<File> listaFile = new ArrayList<>();

		for (ComponenteBean componenteBean : lista) {
			listaFile.add(componenteBean.getStream());
		}

		this.componenteTelaRegistrados = new ArrayList<CompositeInterfaceDTO>();
		List<CompositeDTO> composites = this.contexto.getComponenteTelaService().converterArquivo(listaFile);
		for (CompositeDTO compositeDTO : composites) {
			this.componenteTelaRegistrados.add(compositeDTO.getInterfaces());
		}

	}

	public void onFaceletDrop(DragDropEvent ddEvent) {
		CompositeInterfaceDTO escolhido = ((CompositeInterfaceDTO) ddEvent.getData());
		CompositeInterfaceDTO novo;
		try {
			novo = TransferirDados.transferir(escolhido, CompositeInterfaceDTO.class);
			this.contexto.getComponentesEscolhidos().add(novo);
		} catch (InfraEstruturaException e) {
			e.printStackTrace();
		}
		
	}

	public void buttonActionRemoverComponente(ActionEvent actionEvent) {

		List<CompositeInterfaceDTO> faceletEscolhido = this.contexto.getComponentesEscolhidos();
		for (Iterator<CompositeInterfaceDTO> i = faceletEscolhido.iterator(); i.hasNext();) {
			CompositeInterfaceDTO composite = i.next();

			if (this.removerCompositeEscolhido != null) {

				if (composite.getNome().equals(this.removerCompositeEscolhido.getNome())) {

					i.remove();

				}

			}
		}
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public String reiniciar() {

		this.contexto.limpar();
		init();

		return "/adm/configuraTela/escolhaComponente";
	}

	public ContextoConfiguraComponenteTela getContexto() {
		return contexto;
	}

	public void setContexto(ContextoConfiguraComponenteTela contexto) {
		this.contexto = contexto;
	}

	public List<CompositeInterfaceDTO> getComponenteTelaRegistrados() {
		return componenteTelaRegistrados;
	}

	public void setComponenteTelaRegistrados(List<CompositeInterfaceDTO> componenteTelaRegistrados) {
		this.componenteTelaRegistrados = componenteTelaRegistrados;
	}

	public CompositeInterfaceDTO getRemoverCompositeEscolhido() {
		return removerCompositeEscolhido;
	}

	public void setRemoverCompositeEscolhido(CompositeInterfaceDTO removerCompositeEscolhido) {
		this.removerCompositeEscolhido = removerCompositeEscolhido;
	}

}
