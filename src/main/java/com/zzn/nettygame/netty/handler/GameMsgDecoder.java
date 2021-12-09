package com.zzn.nettygame.netty.handler;

import com.google.protobuf.Message;
import com.zzn.nettygame.netty.util.GameMsgRecognizer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GameMsgDecoder extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        if(!(msg instanceof BinaryWebSocketFrame)){
            return;
        }
        BinaryWebSocketFrame inputWebsocket = (BinaryWebSocketFrame) msg;

        try {
            ByteBuf byteBuf = inputWebsocket.content();
            short length = byteBuf.readShort();
            short msgCode = byteBuf.readShort();
            byte[] msgBody=new byte[byteBuf.readableBytes()];
            byteBuf.readBytes(msgBody);

//            GeneratedMessageV3 cmd=null;
//            switch (msgCode){
//                case GameMsgProtocol.MsgCode.USER_ENTRY_CMD_VALUE:
//                    cmd=GameMsgProtocol.UserEntryCmd.parseFrom(msgBody);
//                    break;
//                case GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_CMD_VALUE:
//                    cmd=GameMsgProtocol.WhoElseIsHereCmd.parseFrom(msgBody);
//                    break;
//                case GameMsgProtocol.MsgCode.USER_MOVE_TO_CMD_VALUE:
//                    cmd=GameMsgProtocol.UserMoveToCmd.parseFrom(msgBody);
//                    break;
//                default:
//                    break;
//            }
            Message.Builder builder = GameMsgRecognizer.getBuilderByMsgCode(msgCode);
            builder.clear();
            builder.mergeFrom(msgBody);
            Message cmd = builder.build();

            if(null!=cmd){
                ctx.fireChannelRead(cmd);
            }
        } catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }
}
