package com.zzn.nettygame.netty;

import com.zzn.nettygame.netty.handler.GameMsgDecoder;
import com.zzn.nettygame.netty.handler.GameMsgEncoder;
import com.zzn.nettygame.netty.handler.GameMsgHandler;
import com.zzn.nettygame.netty.handler.Test2Handler;
import com.zzn.nettygame.netty.handler.cmdhandler.CmdHandlerFactory;
import com.zzn.nettygame.netty.util.GameMsgRecognizer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class Stater implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("正在启动websocket服务器");
        GameMsgRecognizer.init();
        CmdHandlerFactory.init();
        NioEventLoopGroup boss = new NioEventLoopGroup();
        NioEventLoopGroup work = new NioEventLoopGroup();

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(boss, work);
        bootstrap.channel(NioServerSocketChannel.class);
        bootstrap.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) throws Exception {
                socketChannel.pipeline().addLast(new HttpServerCodec());
                socketChannel.pipeline().addLast(new HttpObjectAggregator(65535));
                socketChannel.pipeline().addLast(new WebSocketServerProtocolHandler("/websocket"));
                socketChannel.pipeline().addLast(new GameMsgDecoder());
                socketChannel.pipeline().addLast(new GameMsgEncoder());
//                socketChannel.pipeline().addLast(new TestHandler());
                socketChannel.pipeline().addLast(new GameMsgHandler());
            }
        });

        try {
            Channel channel = bootstrap.bind(6666).sync().channel();
            log.info("webSocket服务器启动成功：" + channel);
            channel.closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
            log.info("运行出错：" + e);
        } finally {
            boss.shutdownGracefully();
            work.shutdownGracefully();
            log.info("websocket服务器已关闭");
        }
    }
}
