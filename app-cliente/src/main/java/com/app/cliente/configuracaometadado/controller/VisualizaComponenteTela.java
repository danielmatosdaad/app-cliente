package com.app.cliente.configuracaometadado.controller;

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

import br.com.app.smart.business.processoconfiguracao.dto.ResultadoConvercaoComponenteUI;
import br.com.projeto.arquivo.util.FileUtil;

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
	private ContextoConfiguraComponenteTela contexto;

	@PostConstruct
	public void init() {

	}

	public String converterComponenteTela() {

		try {
			ResultadoConvercaoComponenteUI resultado = contexto.getComponenteTelaService()
					.converterComponenteUI(this.contexto.getComponenteTelaDTOs());
			List<StringBuffer> out = resultado.getComponeteXhtml();
			String outConcat = "<html>";
			if (out != null) {

				for (StringBuffer sb : out) {
					outConcat = outConcat.concat(sb.toString());
				}

			}
			outConcat = outConcat.concat("</html>");
			this.contexto.setOutputComponenteTelaUI(outConcat);
			this.contexto.setResultadoConvercaoComponenteUI(resultado);
			return outConcat;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}

	public String gerarView() {
		String out = converterComponenteTela();
		System.out.println(out);

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

	public void setConfiguraPropriedadeMetaDadoTela(ConfiguraComponenteTela configuraPropriedadeMetaDadoTela) {
		this.configuraPropriedadeMetaDadoTela = configuraPropriedadeMetaDadoTela;
	}

}
