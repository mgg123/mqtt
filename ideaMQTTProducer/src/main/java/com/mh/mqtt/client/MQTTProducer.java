package com.mh.mqtt.client;

import com.mh.mqtt.client.handler.MQTTClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.mqtt.*;
import io.netty.handler.logging.LogLevel;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.net.URISyntaxException;

public class MQTTProducer {

    public static final Bootstrap TCP = new Bootstrap();

    public static void connectTcp(URI uri, String clientId, String message) throws InterruptedException {
        Channel ch = TCP.connect(uri.getHost(),uri.getPort()).sync().channel();

        MqttFixedHeader mqttFixedHeader = new MqttFixedHeader(MqttMessageType.CONNECT, false, MqttQoS.AT_LEAST_ONCE, false, 0);
        MqttConnectVariableHeader variableHeader = new MqttConnectVariableHeader("MQTT", 4, false, false, false, 0, false, true, 60);
        MqttConnectPayload payload = new MqttConnectPayload(clientId, "", "hello world".getBytes(CharsetUtil.UTF_8), "123456", "password".getBytes(CharsetUtil.UTF_8));
        MqttMessage cm = MqttMessageFactory.newMessage(mqttFixedHeader, variableHeader, payload);

        ch.writeAndFlush(cm).await().addListener((future) -> {
           System.out.println("send login msg succ"+ch.localAddress()+" " + ch.localAddress());
        });
        System.out.println("this is time to wait");
    }

    public static void main(String[] args) throws URISyntaxException, InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        TCP.group(bossGroup).channel(NioSocketChannel.class);
        TCP.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                ch.pipeline().addLast("mqttDecoder", new MqttDecoder(1024 * 1024 * 3));
                ch.pipeline().addLast("mqttEncoder", MqttEncoder.INSTANCE);
                pipeline.addLast("handler", new MQTTClientHandler());
            }
        }).option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.RCVBUF_ALLOCATOR,
                        new AdaptiveRecvByteBufAllocator(64, 2048, 65536));


        connectTcp(new URI("tcp://127.0.0.1:8081"),"mgg","helloworld,helloworld,helloworld,helloworld,helloworld");

        connectTcp(new URI("tcp://127.0.0.1:8081"),"mgg","helloworld,ggggggg helloworld");

    }

}
