package com.kaiyun.io.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @program: trafficdataserver
 * @description: 线程池工厂
 * @author: Chonghao Zhong
 * @create: 2020-03-30 15:33
 **/
public class ThreadPoolFactory {
    private static Logger logger = LoggerFactory.getLogger(ThreadPoolFactory.class);

    private static ThreadPoolExecutor synchronousQueueExecutor;

    static {
        logger.info("Init synchronousQueueExecutor");
        synchronousQueueExecutor = addShutdown(new ThreadPoolExecutor(
                16,
                32,
                10L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()));
    }

    private static ThreadPoolExecutor addShutdown(ThreadPoolExecutor executor) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> SafeExecutor.execSilence(() -> {
            // 先睡眠2秒，等待SafeShutdown.PROCESSOR_RUN控制的线程跑完一次循环
            TimeUnit.SECONDS.sleep(2);
            executor.shutdown();
            executor.awaitTermination(30, TimeUnit.SECONDS);
        })));
        return executor;
    }

    public static ThreadPoolExecutor getSynchronousQueueExecutor() {
        return synchronousQueueExecutor;
    }

    public static ThreadPoolExecutor newSynchronousQueueExecutor(int core) {
        logger.info("Init newSynchronousQueueExecutor");
        return addShutdown(new ThreadPoolExecutor(
                core,
                core,
                60,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()));
    }

    public static ThreadPoolExecutor newDiscardSynchronousQueueExecutor(int core) {
        return addShutdown(new ThreadPoolExecutor(
                core,
                core,
                60,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.DiscardPolicy()));
    }

    public static ThreadPoolExecutor newBlockQueueExecutor(int core, int queue) {
        return addShutdown(new ThreadPoolExecutor(
                core,
                core,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queue),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.CallerRunsPolicy()));
    }
}
