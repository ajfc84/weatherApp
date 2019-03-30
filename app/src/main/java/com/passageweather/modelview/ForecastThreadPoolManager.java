package com.passageweather.modelview;

import android.graphics.Bitmap;
import android.os.Process;
import android.util.Log;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ForecastThreadPoolManager {

    private static final ForecastThreadPoolManager instance;
    private static final int NUMBER_OF_CORES = Runtime.getRuntime().availableProcessors();
    private static final int KEEP_ALIVE_TIME = 1;
    private static final TimeUnit KEEP_ALIVE_TIME_UNIT;

    private final ExecutorService mExecutorService;
    private final BlockingQueue<Runnable> mTaskQueue;
//    private List<Future> mRunningTaskList;

    static {
        KEEP_ALIVE_TIME_UNIT = TimeUnit.SECONDS;
        instance = new ForecastThreadPoolManager();
    }

    private ForecastThreadPoolManager() {
        mTaskQueue = new LinkedBlockingDeque<Runnable>();
//        mRunningTaskList = new ArrayList<>();
        mExecutorService = new ThreadPoolExecutor(
                NUMBER_OF_CORES,
                NUMBER_OF_CORES * 2,
                KEEP_ALIVE_TIME,
                KEEP_ALIVE_TIME_UNIT,
                mTaskQueue,
                new BackgroundThreadFactory());
    }

    public static ForecastThreadPoolManager newInstance() {
        return instance;
    }

    public void addRunnable(Runnable r) {
        mExecutorService.execute(r);
    }

    public Future addCallable(Callable callable) {
        Future future = mExecutorService.submit(callable);
//        mRunningTaskList.add(future);
        return future;
    }

/*
    public synchronized void cancelAllTasks() {
        mTaskQueue.clear();
        for (Future task : mRunningTaskList) {
            if(!task.isDone())
                task.cancel(true);
        }
        mRunningTaskList.clear();
    }
*/

    private static class BackgroundThreadFactory implements ThreadFactory {

        @Override
        public Thread newThread(Runnable r) {
            Thread t = new Thread(r);
            t.setName("ForecastThread");
            t.setPriority(Process.THREAD_PRIORITY_BACKGROUND);

            t.setUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() {
                @Override
                public void uncaughtException(Thread t, Throwable e) {
                    Log.e(this.getClass().getName(), e.getMessage());
                }
            });

            return t;
        }

    }

}
