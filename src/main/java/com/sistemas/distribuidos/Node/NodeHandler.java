package com.sistemas.distribuidos.Node;

import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaders;
import org.springframework.messaging.simp.stomp.StompSession;
import org.springframework.messaging.simp.stomp.StompSessionHandlerAdapter;

import java.lang.reflect.Type;

/**
 * @author Lucas Almeida
 * Classe criada para tratar eventos
 */
public class NodeHandler extends StompSessionHandlerAdapter {
  private StompSession sessionHandler = null;
  private String nodeId;

  /*Assim que subir o projeto o método é executado*/
  @Override
  public void afterConnected(StompSession session, StompHeaders connectedHeaders) {
    sessionHandler = session;
    session.subscribe("/ws-client/node", this);
    NodeItem nodeItem = new NodeItem();
    nodeId = sessionHandler.getSessionId();
    nodeItem.setNodeId(nodeId);

    session.send("/manager/addNode", nodeItem);
    try {
      Thread.sleep(1000);
    } catch (Exception e) {
      e.printStackTrace();
    }
    session.send("/manager/node", "TESTE");

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
    return NodeItem.class;
  }

  /*Assim que uma chamada for feita para url inscrita esse método é executado*/
  @Override
  public void handleFrame(StompHeaders headers, Object payload) {
    NodeItem item = (NodeItem) payload;
    if (nodeId.equals(item.getNodeId())) {
      System.out.println("Recebido com sucesso id " + item.getNodeId() + " -> " + item.toString());
      item.getList().sort(Integer::compareTo);
      sessionHandler.send("/manager/ordered-list", item);
    }
  }
}