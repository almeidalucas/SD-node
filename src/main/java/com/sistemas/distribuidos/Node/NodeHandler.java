package com.sistemas.distribuidos.Node;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

/**
 * @author Lucas Almeida
 * Classe criada para tratar eventos
 * */
public class NodeHandler extends StompSessionHandlerAdapter {

  /*Assim que subir o projeto o método é executado*/
  @Override
  public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
    session.subscribe("/ws-client/node", this);
    session.send("/manager/node", "Nó conectado!");

    System.out.println("Conectado!");
  }

  /*Executa caso aconteça algum problema*/
  @Override
  public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
    exception.printStackTrace();
  }

  /*Define o tipo do retorno*/
  @Override
  public Type getPayloadType(StompHeaders headers) {
    return String.class;
  }

  /*Assim que uma chamada for feita para url inscrita esse método é executado*/
  @Override
  public void handleFrame(StompHeaders headers, Object payload) {
    System.out.println("Recebido com sucesso: " + payload.toString());
  }
}