package com.app.cliente.infra.resourcehandler;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidadorRecursoUrl {

	private static final String urlRecursoPadrao = "/metadado/componente.xhtml";
	private static final String atributoFuncionalidade = "numeroFuncionalidade";
	private static final String atributoNumeroTela = "numeroTela";
	private static final String regexNumerico = "\\d";

	public static boolean validar(String urlParaValidar) {

		if (urlParaValidar.contains(urlRecursoPadrao)) {

			if (urlParaValidar.contains(atributoFuncionalidade)
					& urlParaValidar.contains(atributoNumeroTela)) {

				String[] sRequisicao = urlParaValidar.split("[?]");

				if (sRequisicao != null) {

					String[] sAtributo = sRequisicao[1].split("[;]");

					if (sAtributo != null && sAtributo.length > 1) {

						
						String funcionalidade = sAtributo[0].split("[=]")[1];
						String numeroTela = sAtributo[1].split("[=]")[1];
						Pattern p = Pattern.compile(regexNumerico);
						Matcher mFuncionalidade = p.matcher(funcionalidade);
						Matcher mNumeroTela = p.matcher(numeroTela);

						if (mFuncionalidade.find() & mNumeroTela.find()) {

							if (Integer.valueOf(funcionalidade) >= 0
									& Integer.valueOf(numeroTela) >= 0) {
								return true;
							}

						}

					}

				}

			}

		}

		return false;
	}

	public static void main(String[] args) {

		// String urlDefault = "/metadado/componente.xhtml";
		// String UrlMontada = urlDefault.concat("?funcionalidade=")
		// .concat("1").concat("&")
		// .concat("numeroTela=").concat("2");
		//
		// System.out.println(UrlMontada);
		// ValidadorRecursoUrl vTeste = new ValidadorRecursoUrl(UrlMontada);
		// System.out.println(vTeste.validar());
		//
	}
}
