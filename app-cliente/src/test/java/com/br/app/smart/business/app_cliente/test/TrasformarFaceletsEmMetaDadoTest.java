package com.br.app.smart.business.app_cliente.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import javax.xml.transform.ErrorListener;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

import org.xml.sax.SAXException;

import br.com.projeto.arquivo.util.FileUtil;
import br.com.projeto.metadado.bean.MetaDado;
import br.com.projeto.metadado.infra.CriadorXML;
import br.com.projeto.metadado.infra.ProcessadorXml;
import br.com.projeto.metadado.infra.comum.TipoTransformacao;
import br.projeto.util.FaceltsRegistrados;
import junit.framework.TestCase;
import java.io.InputStream;
import java.io.StringReader;
import java.net.URL;

public class TrasformarFaceletsEmMetaDadoTest extends TestCase {

	public static void main(String args[])
			throws ParserConfigurationException, SAXException, IOException, XMLStreamException, JAXBException, TransformerConfigurationException, TransformerException {

		String inXML = "barraBotao.xhtml";
		String inXSL = "componenteUi.xslt";
		String outTXT = "saida.xml";

		URL entradaXHTML = TrasformarFaceletsEmMetaDadoTest.class.getResource(inXML);
		URL entradaXSL = TrasformarFaceletsEmMetaDadoTest.class.getResource(inXSL);
		URL saidaArquivo = TrasformarFaceletsEmMetaDadoTest.class.getResource(outTXT);
		try {
			transform(entradaXHTML.getPath(), entradaXSL.getPath(), saidaArquivo.getPath());
		} catch (TransformerConfigurationException e) {
			System.err.println("Invalid factory configuration");
			System.err.println(e);
		} catch (TransformerException e) {
			System.err.println("Error during transformation");
			System.err.println(e);
		}
		File f = new File(saidaArquivo.getPath().toString());
		StringBuilder sb = FileUtil.read(f);
		JAXBContext contextosJAXB = JAXBContext.newInstance(MetaDado.class);
		Object o = CriadorXML.getUnmarshaller(contextosJAXB)
				.unmarshal(new StreamSource(new StringReader(sb.toString().trim())));
		System.out.println(o);

		ProcessadorXml processador = new ProcessadorXml();

		List<File> lista = FaceltsRegistrados.buscarComponentesFacelets();
		for (File xml : lista) {
			Object obj = processador.transformar(MetaDado.class,xml);
			if (obj instanceof MetaDado) {

				MetaDado mdo = (MetaDado) obj;
				System.out.println(mdo.getConteudo().getComponentes().get(0).getNomeComponente());
			}
		}

	}

	public static void transform(String inXML, String inXSL, String outTXT)
			throws TransformerConfigurationException, TransformerException {
		TransformerFactory factory = TransformerFactory.newInstance();
		StreamSource xslStream = new StreamSource(inXSL);
		Transformer transformer = factory.newTransformer(xslStream);
		transformer.setErrorListener(new MyErrorListener());
		StreamSource in = new StreamSource(inXML);
		StreamResult out = new StreamResult(outTXT);
		transformer.transform(in, out);
		System.out.println("The generated XML file is:" + outTXT);

	}
}

class MyErrorListener implements ErrorListener {
	public void warning(TransformerException e) throws TransformerException {
		show("Warning", e);
		throw (e);
	}

	public void error(TransformerException e) throws TransformerException {
		show("Error", e);
		throw (e);
	}

	public void fatalError(TransformerException e) throws TransformerException {
		show("Fatal Error", e);
		throw (e);
	}

	private void show(String type, TransformerException e) {
		System.out.println(type + ": " + e.getMessage());
		if (e.getLocationAsString() != null)
			System.out.println(e.getLocationAsString());

	}
}