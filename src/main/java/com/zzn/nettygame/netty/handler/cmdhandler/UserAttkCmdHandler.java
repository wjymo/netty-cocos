package com.zzn.nettygame.netty.handler.cmdhandler;

import com.zzn.nettygame.netty.proto1.GameMsgProtocol;
import io.netty.channel.ChannelHandlerContext;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserAttkCmdHandler implements ICmdHandler<GameMsgProtocol.UserAttkCmd> {
    @Override
    public void handle(ChannelHandlerContext ctx, GameMsgProtocol.UserAttkCmd cmd){
        log.error("用户攻击的信息");
    }
}
