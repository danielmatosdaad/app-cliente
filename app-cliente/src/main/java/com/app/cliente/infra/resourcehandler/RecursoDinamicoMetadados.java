package com.app.cliente.infra.resourcehandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.net.URL;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.FacesException;
import javax.faces.application.ResourceHandler;
import javax.faces.application.ResourceHandlerWrapper;
import javax.faces.application.ViewResource;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import com.app.cliente.infra.controller.fluxo.Requisicao;

import br.com.app.smart.business.exception.InfraEstruturaException;
import br.com.app.smart.business.exception.NegocioException;
import br.com.app.smart.business.funcionalidade.dto.MetaDadoDTO;
import br.com.app.smart.business.tela.componente.interfaces.IMetaDadoUtilDAO;

@Named
@ApplicationScoped
public class RecursoDinamicoMetadados extends ResourceHandlerWrapper implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private ResourceHandler wrapped;

	@Inject
	private Requisicao rf;

	@Inject
	private IMetaDadoUtilDAO metaDadoUtilDAO;

	public RecursoDinamicoMetadados() {

	}

	public RecursoDinamicoMetadados(ResourceHandler wrapped) {
		this.wrapped = wrapped;
	}

	public boolean validacaoUrlRecurso(String resourceName) {

		return ValidadorRecursoUrl.validar(resourceName);
	}

	@Override
	public ViewResource createViewResource(FacesContext context, String resourceName) {

		if (resourceName.contains("/metadado/componente.xhtml") & validacaoUrlRecurso(resourceName)) {
			try {
				File file = File.createTempFile("stream-Componentes-Dinanamicos", ".xhtml");
				try (Writer writer = new FileWriter(file)) {

					String out = injetarComponenteMetadadoUI(resourceName);
					writer.append(out);

				} catch (TransformerException | JAXBException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				final URL url = file.toURI().toURL();
				return new ViewResource() {
					@Override
					public URL getURL() {
						return url;
					}
				};
			} catch (IOException e) {
				throw new FacesException(e);
			}
		}

		return super.createViewResource(context, resourceName);
	}

	@Override
	public ResourceHandler getWrapped() {
		return wrapped;
	}

	public String injetarComponenteMetadadoUI(String recursosUrl)
			throws FileNotFoundException, TransformerException, JAXBException {

		Requisicao rerquisicao = criarRequisicaoFuncionalidade(recursosUrl);
		try {
			MetaDadoDTO metaDadoDTO = metaDadoUtilDAO
					.buscarMetaDadoFuncionalidade(rerquisicao.getNumeroFuncionalidade(), rerquisicao.getNumeroTela());
			if (metaDadoDTO != null) {

				return metaDadoDTO.getXhtml();
			}
		} catch (InfraEstruturaException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NegocioException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "<outputLabel xmlns=\"http://primefaces.org/ui\" value=\"tela nao achada\"/>";

	}

	public Requisicao criarRequisicaoFuncionalidade(String recursosUrl) {

		String[] sRequisicao = recursosUrl.split("[?]");

		if (sRequisicao != null) {

			String[] sAtributo = sRequisicao[1].split("[;]");

			if (sAtributo != null && sAtributo.length > 1) {

				String numeroFuncionalidade = sAtributo[0].split("[=]")[1];
				String numeroTela = sAtributo[1].split("[=]")[1];

				rf.setNumeroFuncionalidade(Integer.parseInt(numeroFuncionalidade));
				rf.setNumeroTela(Integer.parseInt(numeroTela));
			}

		}

		return rf;
	}

}