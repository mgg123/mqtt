package com.mh.mqtt.client.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.*;

public class MQTTClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MqttMessage mm = (MqttMessage) msg;
        MqttMessageType type = mm.fixedHeader().messageType();

        System.out.println(type.value() + "channelRead " + mm.payload().toString());

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println(ctx.channel().isActive() + "channelInactive");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught::connectionLost" + cause.getMessage());
    }

}
