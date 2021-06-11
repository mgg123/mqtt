package com.mh.mqtt.broker.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.mqtt.MqttMessage;
import io.netty.handler.codec.mqtt.MqttMessageType;
import io.netty.handler.codec.mqtt.MqttPublishMessage;
import org.apache.commons.lang3.StringUtils;

public class MQTTServerHandler extends ChannelInboundHandlerAdapter {




    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("a client connected " + ctx.channel().remoteAddress());
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        MqttMessage message = (MqttMessage)msg;
        System.out.println("broker msg playload "+message.fixedHeader().messageType()
                + message.toString());

        //MqttMessageWrapper wrapper = new MqttMessageWrapper(message, ctx.channel());
//        if(message.fixedHeader().messageType() == MqttMessageType.CONNECT){
//               System.out.println("login request,sessionId is "+ ctx.name() +",continue to process..");
//        }else{
//            String userName = SessionUtils.getUserNameFromChannel(wrapper.getChannel());
//            if(StringUtils.isEmpty(userName)){
//                log.warn("{} have not logined,server will disconnect the client.",wrapper.getChannel().remoteAddress());
//                wrapper.getChannel().disconnect();
//                return;
//            }else{
//                if(log.isDebugEnabled())
//                    log.debug("channelRead,userName is {},sessionId is {},continue to process..",userName,wrapper.getSessionId());
//            }
//            SessionManager.refreshSession(wrapper.getSessionId());
//        }


//
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.println("exceptionCaught msg %s playload %s,%s"+ctx.channel().remoteAddress()
                + cause.toString());
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("channelInactive msg %s playload %s,%s"+ctx.channel().remoteAddress()
                + ctx.toString());
    }

}
