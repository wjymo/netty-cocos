package com.zzn.nettygame;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

@SpringBootTest
class NettyGameApplicationTests {

    @Test
    void contextLoads() {
        int[] ints=new int[]{25,20,5,1};
        int money=41;

        int i=0;
        while (i<ints.length){
            if(money-ints[i]<0){
                i++;
                continue;
            }
            money = money - ints[i];
            System.out.println("money被减为："+money+",此次使用的金币为："+ints[i]);
//            i=0;
        }
//        System.out.println(money);
    }

}
