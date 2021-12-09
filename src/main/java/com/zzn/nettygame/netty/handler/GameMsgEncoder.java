package com.zzn.nettygame.netty.handler;

import com.google.protobuf.GeneratedMessageV3;
import com.zzn.nettygame.netty.util.GameMsgRecognizer;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOutboundHandlerAdapter;
import io.netty.channel.ChannelPromise;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameMsgEncoder extends ChannelOutboundHandlerAdapter {

    @Override
    public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
        if(!(msg instanceof GeneratedMessageV3)){
            super.write(ctx,msg,promise);
            return;
        }
        int msgCode=-1;
        try {
//            if(msg instanceof GameMsgProtocol.UserEntryResult){
//    //            GameMsgProtocol.UserEntryResult result = (GameMsgProtocol.UserEntryResult) msg;
//                msgCode=GameMsgProtocol.MsgCode.USER_ENTRY_RESULT_VALUE;
//            }else if(msg instanceof GameMsgProtocol.WhoElseIsHereResult){
//                msgCode=GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_RESULT_VALUE;
//            }else if(msg instanceof GameMsgProtocol.UserMoveToResult){
//                msgCode=GameMsgProtocol.MsgCode.USER_MOVE_TO_RESULT_VALUE;
//            } else if(msg instanceof GameMsgProtocol.UserQuitResult){
//                msgCode=GameMsgProtocol.MsgCode.USER_QUIT_RESULT_VALUE;
//            } else {
//                return;
//            }
            msgCode=GameMsgRecognizer.getMsgCodeByClazz(msg.getClass());


            byte[] msgBody = ((GeneratedMessageV3) msg).toByteArray();
            ByteBufAllocator alloc = ctx.alloc();
            ByteBuf buffer = alloc.buffer();
            buffer.writeShort((short)msgBody.length);
            buffer.writeShort((short)msgCode);
            buffer.writeBytes(msgBody);

            BinaryWebSocketFrame webSocketFrame=new BinaryWebSocketFrame(buffer);
            super.write(ctx,webSocketFrame,promise);
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }
}
