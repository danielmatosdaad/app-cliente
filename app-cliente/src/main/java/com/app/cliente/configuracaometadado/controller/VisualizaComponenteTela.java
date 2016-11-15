package com.app.cliente.configuracaometadado.controller;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.xml.bind.JAXBException;
import javax.xml.transform.TransformerException;

import br.com.projeto.arquivo.util.FileUtil;
import br.com.projeto.facelet.bean.Facelet;
import br.com.projeto.metadado.bean.MetaDado;
import br.com.projeto.metadado.infra.comum.StringBufferOutputStream;

@SessionScoped
@Named("visCpnTela")
public class VisualizaComponenteTela implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Inject
	private ConfiguraComponenteTela configuraPropriedadeMetaDadoTela;

	@Inject
	private ContextoConfiguraComponenteTela ctxConfMdo;

	@PostConstruct
	public void init() {

	}

	public String converterFaceletMetaDado() {

		List<Facelet> faceletes = this.ctxConfMdo.getFaceletsEscolhidos();
		this.ctxConfMdo.setMetadadGerado(ctxConfMdo.getService().converterFaceletMetaDado(faceletes));
		MetaDado mdo = this.ctxConfMdo.getMetadadGerado();
		try {
			StringBufferOutputStream sbos = this.ctxConfMdo.getService().transformarMetadado(mdo);
			return sbos.getBuffer().toString();
		} catch (FileNotFoundException | TransformerException | JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public String gerarView() {
		String out = converterFaceletMetaDado();
		try {
			ExternalContext context = FacesContext.getCurrentInstance().getExternalContext();
			String absoluteWebPath = context.getRealPath("/");
			Path path = Paths.get(absoluteWebPath);
			Date data = new Date();
			Path pathFile = FileUtil.criarArquivo(path, data.getTime(), ".xhtml");
			try (Writer writer = new FileWriter(pathFile.toFile())) {

				writer.append(out);
			}
			String path1 = pathFile.toUri().toURL().toString();
			return path1;
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		return null;
	}

	public ConfiguraComponenteTela getConfiguraPropriedadeMetaDadoTela() {
		return configuraPropriedadeMetaDadoTela;
	}

	public void setConfiguraPropriedadeMetaDadoTela(
			ConfiguraComponenteTela configuraPropriedadeMetaDadoTela) {
		this.configuraPropriedadeMetaDadoTela = configuraPropriedadeMetaDadoTela;
	}

}
