package com.scut.seckill.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringRunner.class)
@SpringBootTest
public class TestSecKillService {

    @Autowired
    private SecKillService secKillService;

    @Test
    public void testHandleByRedisWatch() {
        int count = 100;//并发线程数
        final CyclicBarrier cyclicBarrier = new CyclicBarrier(count);
        ExecutorService executorService = Executors.newFixedThreadPool(count);
        for (int i = 0; i < count; i++) {
            executorService.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("Thread：" + Thread.currentThread().getName() + "准备...");
                        //cyclicBarrier一定要等到满100个线程到了才往后执行
                        cyclicBarrier.await();
                        System.out.println("Thread：" + Thread.currentThread().getName() + "开始执行");
                        //do something
                        Map<String, Object> paramMap = new HashMap<>();
                        paramMap.put("userId", 1);
                        paramMap.put("productId", 1);
                        secKillService.handleByRedisWatch(paramMap);
                    } catch (Exception e) {
//                        e.printStackTrace();
                    }
                }
            });
        }
    }
}
