package com.scut.seckill.controller;

import com.scut.seckill.common.Message;
import com.scut.seckill.common.SecKillEnum;
import com.scut.seckill.service.SecKillService;
import com.scut.seckill.web.req.SecKillRequest;
import com.scut.seckill.web.vo.SecKillResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/seckill")
@RestController
public class SecKillController {

    @Autowired
    private SecKillService secKillService;

    /**
     * MySQL悲观锁
     * @param requestMessage
     * @return
     */
    @RequestMapping(value = "/pessLockInMySQL",method = RequestMethod.POST)
    public Message<SecKillResponse> pessLockInMySQL(@RequestBody Message<SecKillRequest> requestMessage){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",requestMessage.getBody().getUserId());
        paramMap.put("productId",requestMessage.getBody().getProductId());
        SecKillEnum secKillEnum = secKillService.handleByPessLockInMySQL(paramMap);
        Message<SecKillResponse> responseMessage = new Message<>(secKillEnum,null);
        return responseMessage;
    }

    /**
     * MySQL乐观锁
     * @param requestMessage
     * @return
     */
    @RequestMapping(value = "/posiLockInMySQL",method = RequestMethod.POST)
    public Message<SecKillResponse> posiLockInMySQL(@RequestBody Message<SecKillRequest> requestMessage){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",requestMessage.getBody().getUserId());
        paramMap.put("productId",requestMessage.getBody().getProductId());
        SecKillEnum secKillEnum = secKillService.handleByPosiLockInMySQL(paramMap);
        Message<SecKillResponse> responseMessage = new Message<>(secKillEnum,null);
        return responseMessage;
    }

    /**
     * 利用redis的watch监控的特性
     * @throws InterruptedException
     */
    @RequestMapping(value = "/baseOnRedisWatch",method = RequestMethod.POST)
    public Message<SecKillResponse> baseOnRedisWatch(@RequestBody Message<SecKillRequest> requestMessage) throws InterruptedException {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",requestMessage.getBody().getUserId());
        paramMap.put("productId",requestMessage.getBody().getProductId());
        SecKillEnum secKillEnum = secKillService.handleByRedisWatch(paramMap);
        Message<SecKillResponse> responseMessage = new Message<>(secKillEnum,null);
        return responseMessage;
    }

    /**
     * 利用AtomicInteger的CAS机制特性
     * @param requestMessage
     * @return
     */
    @RequestMapping(value = "/baseOnAtomicInteger",method = RequestMethod.POST)
    public  Message<SecKillResponse> baseOnAtomicInteger(@RequestBody Message<SecKillRequest> requestMessage){
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",requestMessage.getBody().getUserId());
        paramMap.put("productId",requestMessage.getBody().getProductId());
        SecKillEnum secKillEnum = secKillService.handleByAtomicInteger(paramMap);
        Message<SecKillResponse> responseMessage = new Message<>(secKillEnum,null);
        return responseMessage;
    }

    /**
     * 利用redis的watch监控的特性（我自己改进在高并发情况下重复购买的漏洞）
     * @throws InterruptedException
     */
    @RequestMapping(value = "/baseOnRedisWatch2",method = RequestMethod.POST)
    public Message<SecKillResponse> baseOnRedisWatch2(@RequestBody Message<SecKillRequest> requestMessage) throws InterruptedException {
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("userId",requestMessage.getBody().getUserId());
        paramMap.put("productId",requestMessage.getBody().getProductId());
        SecKillEnum secKillEnum = secKillService.handleByRedisWatch2(paramMap);
        Message<SecKillResponse> responseMessage = new Message<>(secKillEnum,null);
        return responseMessage;
    }
}
