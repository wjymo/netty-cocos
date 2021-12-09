package com.zzn.nettygame.netty.util;

import com.google.protobuf.GeneratedMessageV3;
import com.google.protobuf.Message;
import com.zzn.nettygame.netty.proto1.GameMsgProtocol;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.Map;

@Slf4j
public class GameMsgRecognizer {
    private GameMsgRecognizer(){}
    /**
     * @method
     * @desc TODO
     * @version V1.0.0
     * 消息编号 -> 消息对象 
     * @author wangjingyu
     * @date 2021/12/6 16:21
     * @return 
     */
    private static final Map<Integer, GeneratedMessageV3> _msgCode2MsgObjMap =new HashMap<>();
    private static final Map<Class<?>, Integer> _clazz2MagCode =new HashMap<>();

    static {
//        _msgCode2MsgObjMap.put(GameMsgProtocol.MsgCode.USER_ENTRY_CMD_VALUE,GameMsgProtocol.UserEntryCmd.getDefaultInstance());
//        _msgCode2MsgObjMap.put(GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_CMD_VALUE,GameMsgProtocol.WhoElseIsHereCmd.getDefaultInstance());
//        _msgCode2MsgObjMap.put(GameMsgProtocol.MsgCode.USER_MOVE_TO_CMD_VALUE,GameMsgProtocol.UserMoveToCmd.getDefaultInstance());
//
//        _clazz2MagCode.put(GameMsgProtocol.UserEntryResult.class,GameMsgProtocol.MsgCode.USER_ENTRY_RESULT_VALUE);
//        _clazz2MagCode.put(GameMsgProtocol.WhoElseIsHereResult.class,GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_RESULT_VALUE);
//        _clazz2MagCode.put(GameMsgProtocol.UserMoveToResult.class,GameMsgProtocol.MsgCode.USER_MOVE_TO_RESULT_VALUE);
//        _clazz2MagCode.put(GameMsgProtocol.UserQuitResult.class,GameMsgProtocol.MsgCode.USER_QUIT_RESULT_VALUE);
    }
    public static void init(){
        Class<?>[] innerClassArray = GameMsgProtocol.class.getDeclaredClasses();
        for (Class<?> innerClass : innerClassArray) {
            if(innerClass==null||!GeneratedMessageV3.class.isAssignableFrom(innerClass)){
                continue;
            }
            String simpleName = innerClass.getSimpleName().toLowerCase();

            for (GameMsgProtocol.MsgCode msgCode : GameMsgProtocol.MsgCode.values()) {
                if (msgCode == null) {
                    continue;
                }
                String name = msgCode.name();
                name=name.replace("_","");
                name=name.toLowerCase();
                if(!name.startsWith(simpleName)){
                    continue;
                }
                try {
                    Object returnObj = innerClass.getDeclaredMethod("getDefaultInstance").invoke(innerClass);
                    _msgCode2MsgObjMap.put(msgCode.getNumber(),(GeneratedMessageV3)returnObj);
                    _clazz2MagCode.put(innerClass,msgCode.getNumber());
                } catch (Exception e) {
                    log.error("反射获取消息code->消息对象异常：{}",e.getMessage(),e);
                }
            }
        }

    }
    public static Message.Builder getBuilderByMsgCode(int msgCode){
//        if(msgCode== GameMsgProtocol.MsgCode.USER_ENTRY_CMD_VALUE){
//            return GameMsgProtocol.UserEntryCmd.getDefaultInstance().newBuilderForType();
//        }else if(msgCode==GameMsgProtocol.MsgCode.WHO_ELSE_IS_HERE_CMD_VALUE){
//            return GameMsgProtocol.WhoElseIsHereCmd.getDefaultInstance().newBuilderForType();
//        }else {
//            return null;
//        }
        GeneratedMessageV3 generatedMessageV3 = _msgCode2MsgObjMap.get(msgCode);
        return generatedMessageV3.newBuilderForType();
    }

    public static Integer getMsgCodeByClazz(Class<?> clazz){
        return _clazz2MagCode.get(clazz);
    }
}
