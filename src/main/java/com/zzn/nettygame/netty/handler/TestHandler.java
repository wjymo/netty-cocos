package com.zzn.nettygame.netty.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TestHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
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
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        String text = textWebSocketFrame.text();
        System.out.println(text);
        TextWebSocketFrame response = new TextWebSocketFrame("骁儿爱"+text);
        channelHandlerContext.writeAndFlush(response);
    }
}
