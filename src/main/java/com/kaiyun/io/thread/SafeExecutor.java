package com.kaiyun.io.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @program: trafficdataserver
 * @description:
 * @author: Chonghao Zhong
 * @create: 2020-03-30 16:43
 **/
public class SafeExecutor {
    private static Logger logger = LoggerFactory.getLogger(SafeExecutor.class);

    public static void execSilence(Worker worker) {
        try {
            worker.work();
        } catch (Throwable e) {
            logger.error("线程池优雅关闭出现异常{}", e.getMessage());
        }
    }
}
