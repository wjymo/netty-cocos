package com.zzn.nettygame.netty.handler.cmdhandler;

import com.google.protobuf.GeneratedMessageV3;
import com.zzn.nettygame.netty.util.PackageUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
@Slf4j
public class CmdHandlerFactory {
    private CmdHandlerFactory(){}

    private static final Map<Class<?>,ICmdHandler<? extends GeneratedMessageV3>> _hashmap=new HashMap<>();
    static {
//        _hashmap.put(GameMsgProtocol.UserEntryCmd.class,new UserEntryCmdHandler());
//        _hashmap.put(GameMsgProtocol.WhoElseIsHereCmd.class,new WhoElseIsHereCmdHandler());
//        _hashmap.put(GameMsgProtocol.UserMoveToCmd.class,new UserMoveToCmdHandler());
    }
    public static void init(){
        String packageName = CmdHandlerFactory.class.getPackage().getName();
        Set<Class<?>> classes = PackageUtil.listSubClazz(packageName, true, ICmdHandler.class);
        log.info("GameMsgProtocol的clazz->handler:{}",classes);
        for (Class<?> handlerClazz : classes) {
            if (handlerClazz == null||0!=(handlerClazz.getModifiers()& Modifier.ABSTRACT)) {
                continue;
            }
            Class<?> msgClazz=null;
            Method[] declaredMethods = handlerClazz.getDeclaredMethods();
            for (Method declaredMethod : declaredMethods) {
                if(declaredMethod==null|| !StringUtils.equals(declaredMethod.getName(),"handle")){
                    continue;
                }
                Class<?>[] parameterTypes = declaredMethod.getParameterTypes();
                if(parameterTypes.length<2||parameterTypes[1]==GeneratedMessageV3.class||
                        !GeneratedMessageV3.class.isAssignableFrom(parameterTypes[1])){
                    continue;
                }
                msgClazz = parameterTypes[1];
//                break;
            }
            if (msgClazz == null) {
                continue;
            }
            try {
                ICmdHandler<?> iCmdHandler = (ICmdHandler<?>) handlerClazz.newInstance();
                _hashmap.put(msgClazz,iCmdHandler);
            } catch (Exception e) {
                log.error(e.getMessage(),e);
            }

        }
        System.out.println(1);
    }

    public static ICmdHandler<? extends GeneratedMessageV3> create(Object msg){
//        if(msg instanceof GameMsgProtocol.UserEntryCmd){
////            GameMsgProtocol.UserEntryCmd cmd = (GameMsgProtocol.UserEntryCmd) msg;
//            return new UserEntryCmdHandler();
//        }else if(msg instanceof GameMsgProtocol.WhoElseIsHereCmd){
//            return new WhoElseIsHereCmdHandler();
//        }else if(msg instanceof GameMsgProtocol.UserMoveToCmd){
////            GameMsgProtocol.UserMoveToCmd cmd = (GameMsgProtocol.UserMoveToCmd) msg;
//            return new UserMoveToCmdHandler();
//        }
//        return null;
        return _hashmap.get(msg.getClass());
    }
}
