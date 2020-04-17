package com.kaiyun.io.thread;

import com.google.common.util.concurrent.ThreadFactoryBuilder;
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

    private static ExecutorService synchronousQueueExecutor;

    static {
        logger.info("Init synchronousQueueExecutor");
        synchronousQueueExecutor = addShutdown(new ThreadPoolExecutor(
                16,
                32,
                10L,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new ThreadFactoryBuilder()
                        .setNameFormat("default-synchronousQueueExecutor-pool-%d")
                        .build(),
                new ThreadPoolExecutor.CallerRunsPolicy()));
    }

    private static ExecutorService addShutdown(ThreadPoolExecutor executor) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> SafeExecutor.execSilence(() -> {
            // 先睡眠2秒，等待SafeShutdown.PROCESSOR_RUN控制的线程跑完一次循环
            TimeUnit.SECONDS.sleep(2);
            executor.shutdown();
            executor.awaitTermination(30, TimeUnit.SECONDS);
        })));
        return executor;
    }

    public static ExecutorService getSynchronousQueueExecutor() {
        return synchronousQueueExecutor;
    }

    public static ExecutorService newSynchronousQueueExecutor(int core) {
        logger.info("Init newSynchronousQueueExecutor");
        return addShutdown(new ThreadPoolExecutor(
                core,
                core,
                60,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new ThreadFactoryBuilder()
                        .setNameFormat("new-SynchronousQueueExecutor-pool-%d")
                        .build(),
                new ThreadPoolExecutor.CallerRunsPolicy()));
    }

    public static ExecutorService newDiscardSynchronousQueueExecutor(int core) {
        logger.info("Init newDiscardSynchronousQueueExecutor");
        return addShutdown(new ThreadPoolExecutor(
                core,
                core,
                60,
                TimeUnit.SECONDS,
                new SynchronousQueue<>(),
                new ThreadFactoryBuilder()
                        .setNameFormat("new-DiscardSynchronousQueueExecutor-pool-%d")
                        .build(),
                new ThreadPoolExecutor.DiscardPolicy()));
    }

    public static ExecutorService newBlockQueueExecutor(int core, int queue) {
        logger.info("Init newBlockQueueExecutor");
        return addShutdown(new ThreadPoolExecutor(
                core,
                core,
                60,
                TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(queue),
                new ThreadFactoryBuilder()
                        .setNameFormat("new-BlockQueueExecutor-pool-%d")
                        .build(),
                new ThreadPoolExecutor.CallerRunsPolicy()));
    }
}
