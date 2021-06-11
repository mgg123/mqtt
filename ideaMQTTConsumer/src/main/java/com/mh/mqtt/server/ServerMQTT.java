package com.mh.mqtt.server;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.eclipse.paho.client.mqttv3.*;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

@EnableDubbo
public class ServerMQTT {

    public static final String host = "tcp://0.0.0.0:8081";

    public static final String port = "61613";

    public static final String topic = "topicmh";

    public static final String clientId = "server11";

    private MqttClient client;
    private MqttTopic topic11;
    private String userName = "admin";
    private String passWord = "password";
    private MqttMessage message;

    public ServerMQTT() {
        try {
            client = new MqttClient(host,clientId,new MemoryPersistence());
            connect();
        } catch (MqttException e) {
            System.out.println("ServerMqtt error msg : " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void  connect() {
        MqttConnectOptions options = new MqttConnectOptions();
        options.setCleanSession(false);
        options.setUserName(userName);
        options.setPassword(passWord.toCharArray());
        // 设置超时时间
        options.setConnectionTimeout(10);
        // 设置会话心跳时间
        options.setKeepAliveInterval(20);
        try {
            client.setCallback(new PushCallback(host,port,clientId));
            client.connect(options);
            topic11 = client.getTopic(topic);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *
     * @param topic
     * @param message
     * @throws MqttPersistenceException
     * @throws MqttException
     */
    public void publish(MqttTopic topic , MqttMessage message) throws MqttPersistenceException,
            MqttException {
        MqttDeliveryToken token = topic.publish(message);
        token.waitForCompletion();
        System.out.println("message is published completely! "
                + token.isComplete());
    }

    /**
     *  启动入口
     * @param args
     * @throws MqttException
     */
    public static void main(String[] args) throws MqttException {
        ServerMQTT server = new ServerMQTT();

        server.message = new MqttMessage();
        server.message.setQos(1);
        server.message.setRetained(true);
        server.message.setPayload("hello,topic11".getBytes());
        server.publish(server.topic11 , server.message);
        System.out.println(server.message.isRetained() + "------ratained状态");
    }




}
