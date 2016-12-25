package com.br.app.smart.business.app_cliente.util;

import org.modelmapper.ModelMapper;

import br.com.app.smart.business.exception.InfraEstruturaException;

public class TransferirDados {

	@SuppressWarnings("unchecked")
	public static <T> T transferir(Object objeto, Class<T> destino) throws InfraEstruturaException {
		ModelMapper mapper = new ModelMapper();
		return mapper.map(objeto, destino);

	}
}
