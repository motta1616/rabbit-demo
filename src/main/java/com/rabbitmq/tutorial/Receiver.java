package com.rabbitmq.tutorial;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

public class Receiver {
    private final static String HOST_NAME = "localhost";
    private final static String QUEUE_NAME = "demo-lab-queue";

    public static void main(String[] args) throws Exception {

        final ConnectionFactory connectionFactory = new ConnectionFactory();
        connectionFactory.setHost(HOST_NAME);

        final Connection connection = connectionFactory.newConnection();
        final Channel channel = connection.createChannel();
        channel.queueDeclare(QUEUE_NAME, false, false, false, null);

        System.out.println("Esperando mensajes de la cola. Para salir presione CTRL + C");

        final DeliverCallback deliverCallback = (consumerTag, delivery) -> {
            final String message = new String(delivery.getBody(), "UTF-8");
            System.out.println("Recibido de mensaje de la cola : " + message);
        };

        channel.basicConsume(QUEUE_NAME, true, deliverCallback, consumerTag -> {});
    }
}
