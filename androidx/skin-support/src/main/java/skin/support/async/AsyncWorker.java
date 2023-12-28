package skin.support.async;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

public class AsyncWorker {
    private static final int CORE_POOL_SIZE = 1;
    private static final int MAXIMUM_POOL_SIZE = 20;
    private static final int KEEP_ALIVE_SECONDS = 3;

    private static final ThreadFactory sThreadFactory = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        public Thread newThread(Runnable r) {
            return new Thread(r, "AsyncTask #" + mCount.getAndIncrement());
        }
    };


    private ExecutorService mDefaultExecutorService;
    protected Handler mMainHandler;

    private AsyncWorker() {
        mMainHandler = new Handler(Looper.getMainLooper());
    }

    public static AsyncWorker getInstance() {
        return InstanceHolder.INSTANCE;
    }

    public ExecutorService getExecutorService() {
        if (mDefaultExecutorService == null) {
            ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                    CORE_POOL_SIZE, MAXIMUM_POOL_SIZE, KEEP_ALIVE_SECONDS, TimeUnit.SECONDS,
                    new SynchronousQueue<>(), sThreadFactory);
            threadPoolExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
            mDefaultExecutorService = threadPoolExecutor;
        }
        return mDefaultExecutorService;
    }

    public void setExecutorService(ExecutorService executorService) {
        if (mDefaultExecutorService != null) {
            mDefaultExecutorService.shutdownNow();
        }
        mDefaultExecutorService = executorService;
    }

    public Handler getHandler() {
        return mMainHandler;
    }

    private static final class InstanceHolder {
        static final AsyncWorker INSTANCE = new AsyncWorker();
    }
}
