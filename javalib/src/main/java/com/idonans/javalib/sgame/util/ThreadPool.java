package com.idonans.javalib.sgame.util;

/**
 * Created by idonans on 2016/11/24.
 */

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 共享的线程池
 * Created by idonans on 16-4-13.
 */
public class ThreadPool {

    private static class InstanceHolder {

        private static final ThreadPool sInstance = new ThreadPool();

    }

    public static ThreadPool getInstance() {
        return InstanceHolder.sInstance;
    }

    private final AtomicInteger mCount = new AtomicInteger();
    private final ThreadPoolExecutor mExecutor = new ThreadPoolExecutor(
            0, Integer.MAX_VALUE, 60L, TimeUnit.SECONDS,
            new SynchronousQueue<Runnable>());

    private ThreadPool() {
    }

    public void post(final Runnable runnable) {
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mCount.incrementAndGet();
                try {
                    Thread.currentThread().setName("thread_pool");
                    runnable.run();
                } finally {
                    mCount.decrementAndGet();
                }
            }
        });
    }

    /**
     * 获得线程池中正在运行的任务数量 (任务数量可能比当前线程池中的线程数量少)
     */
    public int getCount() {
        return mCount.get();
    }

}