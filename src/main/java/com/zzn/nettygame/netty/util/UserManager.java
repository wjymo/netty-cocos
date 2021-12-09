package com.zzn.nettygame.netty.util;

import com.zzn.nettygame.netty.dto.User;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class UserManager {
    private final static Map<Integer, User> USER_MAP=new ConcurrentHashMap<>();

    private UserManager(){
    }

    public static void addUser(User user){
        USER_MAP.putIfAbsent(user.getUserId(),user);
    }

    public static void removeUser(Integer userId){
        USER_MAP.remove(userId);
    }

    public static Collection<User> listUser(){
        return USER_MAP.values();
    }

}
