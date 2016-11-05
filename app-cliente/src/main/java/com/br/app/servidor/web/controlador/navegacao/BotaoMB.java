package com.br.app.servidor.web.controlador.navegacao;

import java.io.Serializable;

import javax.enterprise.context.RequestScoped;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import javax.inject.Named;

@RequestScoped
@Named
public class BotaoMB implements ActionListener, Serializable {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public void processAction(ActionEvent arg0) throws AbortProcessingException {
		// TODO Auto-generated method stub
		
	}
	
}
