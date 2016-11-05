package com.app.cliente.configuracaometadado.controller;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ActionEvent;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.DragDropEvent;

import br.com.projeto.metadado.bean.Componente;
import br.com.projeto.metadado.bean.Conteudo;
import br.com.projeto.metadado.bean.MetaDado;
import br.com.projeto.metadado.regras.IRegrasMetaDado;
import br.projeto.util.FaceltsRegistrados;

@ApplicationScoped
@Named("configuraMetaDadoTela")
public class ConfiguraMetaDadoTela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private IRegrasMetaDado service;

	private List<MetaDado> metaDados;

	private List<MetaDado> droppedMetaDados;

	private MetaDado selectedMetaDado;

	private MetaDado metadadoParaGerar;

	private StringBuffer resultadoMetaDado = new StringBuffer();

	@PostConstruct
	public void init() {
		this.droppedMetaDados = new ArrayList<MetaDado>();
		List<File> lista = FaceltsRegistrados.buscarComponentesFacelets();
		this.metaDados = service.transformarFaceletsEmMetadados(lista);

		selectedMetaDado = new MetaDado();
		Conteudo conteudo = new Conteudo();
		List<Componente> componentes = new ArrayList<>();
		conteudo.setComponentes(componentes);
		selectedMetaDado.setConteudo(conteudo);

	}

	public void onMetaDadoDrop(DragDropEvent ddEvent) {
		MetaDado mdo = ((MetaDado) ddEvent.getData());

		droppedMetaDados.add(mdo);
		metaDados.remove(mdo);
	}

	public void buttonActionRemoverComponente(ActionEvent actionEvent) {

		for (Iterator<MetaDado> i = droppedMetaDados.iterator(); i.hasNext();) {
			MetaDado mdo = i.next();
			if (mdo.getNomeMetadado().equals(this.selectedMetaDado.getNomeMetadado())) {

				getMetaDados().add(this.selectedMetaDado);
				i.remove();

			}
		}
	}

	public void buttonActionGerarMetaDados(ActionEvent actionEvent) {

		this.resultadoMetaDado = this.service.transformaParaXml(this.selectedMetaDado);
		System.out.println(this.resultadoMetaDado.toString());
		this.resultadoMetaDado = this.service.transformaMetaDadoParaFacelet(this.selectedMetaDado);
	}

	public void buttonSalvarPropriedadeMetaDados(ActionEvent actionEvent) {
		addMessage("Propriedades Salvas");
	}

	public void addMessage(String summary) {
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, summary, null);
		FacesContext.getCurrentInstance().addMessage(null, message);
	}

	public synchronized List<MetaDado> getMetaDados() {
		return metaDados;
	}

	public synchronized List<MetaDado> getDroppedMetaDados() {
		return droppedMetaDados;
	}

	public synchronized MetaDado getSelectedMetaDado() {
		return selectedMetaDado;
	}

	public synchronized void setSelectedMetaDado(MetaDado selectedMetaDado) {
		this.selectedMetaDado = selectedMetaDado;
	}

	public IRegrasMetaDado getService() {
		return service;
	}

	public void setService(IRegrasMetaDado service) {
		this.service = service;
	}

	public void setMetaDados(List<MetaDado> metaDados) {
		this.metaDados = metaDados;
	}

	public synchronized void synchronizedsetDroppedMetaDados(List<MetaDado> droppedMetaDados) {
		this.droppedMetaDados = droppedMetaDados;
	}

	public StringBuffer getResultadoMetaDado() {
		return resultadoMetaDado;
	}

	public void setResultadoMetaDado(StringBuffer resultadoMetaDado) {
		this.resultadoMetaDado = resultadoMetaDado;
	}

	public MetaDado getMetadadoParaGerar() {
		return metadadoParaGerar;
	}

	public void setMetadadoParaGerar(MetaDado metadadoParaGerar) {
		this.metadadoParaGerar = metadadoParaGerar;
	}

}
