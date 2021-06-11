package com.mh.mqtt.server;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class PushCallback implements MqttCallback {

    String ip;
    String port;
    String clientId;

    public PushCallback(String ip,String port,String clientId) {
        this.ip = ip;
        this.port = port;
        this.clientId = clientId;
    }

    public void connectionLost(Throwable throwable) {
        System.out.println("mqtt connection lost " + toString() + " erromsg is " + throwable.getStackTrace());
    }

    public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
        System.out.println("mqtt messageArrived  " + toString() + " msg is " + mqttMessage.toString());
    }

    public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {
        try {
            System.out.println("mqtt deliveryComplete " + toString() + " msg is " + iMqttDeliveryToken.getMessage());
        } catch (MqttException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return "PushCallback{" +
                "ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", clientId='" + clientId + '\'' +
                '}';
    }
}
