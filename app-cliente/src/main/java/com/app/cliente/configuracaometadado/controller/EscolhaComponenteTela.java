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

import br.com.projeto.facelet.bean.ComponenteFacelet;
import br.com.projeto.facelet.bean.ConteudoFacelet;
import br.com.projeto.facelet.bean.Facelet;
import br.com.projeto.metadado.regras.IRegrasMetaDado;
import br.projeto.util.FaceltsRegistrados;

@SessionScoped
@Named("escCpnTela")
public class EscolhaComponenteTela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ContextoConfiguraComponenteTela ctxConfMdo;

	private List<Facelet> faceletsRegistrados;

	private Facelet selecionaFaceletRemover;


	@PostConstruct
	public void init() {
		
		List<File> lista = FaceltsRegistrados.buscarComponentesFacelets();
		this.faceletsRegistrados = ctxConfMdo.getService().transformarEmFacelets(lista);

	}

	public void onFaceletDrop(DragDropEvent ddEvent) {
		Facelet faceletEscolhido = ((Facelet) ddEvent.getData());
		this.ctxConfMdo.getFaceletsEscolhidos().add(faceletEscolhido);
	}

	public void buttonActionRemoverComponente(ActionEvent actionEvent) {

		List<Facelet> faceletEscolhido = this.ctxConfMdo.getFaceletsEscolhidos();
		for (Iterator<Facelet> i = faceletEscolhido.iterator(); i.hasNext();) {
			Facelet facelet = i.next();

			if (this.selecionaFaceletRemover != null) {

				if (facelet.getNomeMetadado().equals(this.selecionaFaceletRemover.getNomeMetadado())) {

					i.remove();

				}

			}
		}
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public void reiniciar(ActionEvent actionEvent) {

		init();
		this.ctxConfMdo.init();
	}

	public ContextoConfiguraComponenteTela getCtxConfMdo() {
		return ctxConfMdo;
	}

	public void setCtxConfMdo(ContextoConfiguraComponenteTela ctxConfMdo) {
		this.ctxConfMdo = ctxConfMdo;
	}

	public List<Facelet> getFaceletsRegistrados() {
		return faceletsRegistrados;
	}

	public void setFaceletsRegistrados(List<Facelet> faceletsRegistrados) {
		this.faceletsRegistrados = faceletsRegistrados;
	}

	public Facelet getSelecionaFaceletRemover() {
		return selecionaFaceletRemover;
	}

	public void setSelecionaFaceletRemover(Facelet selecionaFaceletRemover) {
		this.selecionaFaceletRemover = selecionaFaceletRemover;
	}

}
