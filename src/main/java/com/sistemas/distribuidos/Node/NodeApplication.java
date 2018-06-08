package com.sistemas.distribuidos.Node;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.stomp.StompSessionHandler;
import org.springframework.scheduling.concurrent.ConcurrentTaskScheduler;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Lucas Almeida
 * Realiza configurações necessárias para ouvir serviço WebSocket
 * Inicia serviço e mantém online ouvindo a url determinada
 * */
@SpringBootApplication
public class NodeApplication {

  public static void main(String[] args) {

    List<Transport> transports = new ArrayList<>();
    transports.add(new WebSocketTransport(new StandardWebSocketClient()));

    SockJsClient sockJsClient = new SockJsClient(transports);
    WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);
    stompClient.setMessageConverter(new StringMessageConverter());
    stompClient.setTaskScheduler(new ConcurrentTaskScheduler());

    String url = "ws://localhost:8090/sistemas-distribuidos";
    StompSessionHandler sessionHandler = new NodeHandler();
    stompClient.connect(url, sessionHandler);

    SpringApplication.run(NodeApplication.class, args);
  }
}