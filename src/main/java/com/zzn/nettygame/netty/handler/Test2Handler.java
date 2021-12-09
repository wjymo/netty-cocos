package com.zzn.nettygame.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Test2Handler extends SimpleChannelInboundHandler<WebSocketFrame> {
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        String s = ctx.channel().id().asLongText();
        log.info("channel：{}加入连接",s);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        String s = ctx.channel().id().asLongText();
        log.info("channel：{}退出连接",s);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, WebSocketFrame webSocketFrame) throws Exception {
        String name = webSocketFrame.getClass().getName();
        System.out.println(name);
        TextWebSocketFrame response = new TextWebSocketFrame("骁儿爱");
        channelHandlerContext.writeAndFlush(response);
    }
}
