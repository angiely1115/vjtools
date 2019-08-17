package com.vip.vjtools.vjkit.concurrent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author: lvrongzhuan
 * @Description: 异步执行获取返回结果
 * @Date: 2018/11/12 14:47
 * @Version: 1.0
 * modified by:
 */
public class CompletableFutureResult<U> {
    private static Logger logger = LoggerFactory.getLogger(ThreadDumpper.class);
    private CompletableFutureResult() {
    }

    /**
     * 获取结果超时时间
     */
    private  static final Long FUTURE_GET_TIME_OUT = 10L;
    public static <U> Optional<U>  getFutureResult(CompletableFuture<U> completableFuture){
        try {
            return Optional.of(completableFuture.get(FUTURE_GET_TIME_OUT, TimeUnit.SECONDS));
        } catch (InterruptedException e) {
            logger.error("获取结果线程中断异常",e);
            Thread.currentThread().interrupt();
        } catch (ExecutionException e) {
            logger.error("获取结果执行异常",e);
        } catch (TimeoutException e) {
            logger.error("获取结果超时异常",e);
        }
        return Optional.empty();
    }
}
