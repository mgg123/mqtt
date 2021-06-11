package com.mh.mqtt.broker;

import com.mh.mqtt.broker.handler.MQTTServerHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.mqtt.MqttDecoder;
import io.netty.handler.codec.mqtt.MqttEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.commons.lang3.StringUtils;

public class brokerStart {



    private void startNettyBroker() throws InterruptedException {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap b = new ServerBootstrap();

        b.group(bossGroup,workerGroup).channel(NioServerSocketChannel.class)
                .option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .option(ChannelOption.SO_BACKLOG, 128)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .childOption(ChannelOption.SO_SNDBUF,1)
                .childOption(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast("logging", new LoggingHandler(LogLevel.INFO));
                        ch.pipeline().addLast("mqttDecoder", new MqttDecoder());
                        ch.pipeline().addLast("mqttEncoder", MqttEncoder.INSTANCE);
                        ch.pipeline().addLast(new MQTTServerHandler());
                    }
                });
        ChannelFuture f = b.bind(8081).sync().await();
        System.out.println("成功开启服务，监听端口：" + 8081);
        f.channel().closeFuture().sync();
    }


    public static void main(String[] args) {
//        brokerStart start = new brokerStart();
//        try {
//            start.startNettyBroker();
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

    }

}
