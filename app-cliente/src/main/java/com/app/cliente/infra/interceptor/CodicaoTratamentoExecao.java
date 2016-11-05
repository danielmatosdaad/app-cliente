package com.app.cliente.infra.interceptor;

public enum CodicaoTratamentoExecao {

	SIM("sim", Boolean.TRUE), NAO("não", Boolean.FALSE);

	private String value;
	private Boolean valueInBoolean;

	private CodicaoTratamentoExecao(String value, Boolean valueInBoolean) {
		this.value = value;
		this.valueInBoolean = valueInBoolean;
	}

	public String getValue() {
		return value;
	}

	public Boolean parseBoolean() {
		return valueInBoolean;
	}
}
