package skin.support.async;

import android.os.Handler;
import android.os.Looper;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsyncWorker {
    private static final int NUMBER_OF_THREADS = 4;

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
            mDefaultExecutorService = Executors.newFixedThreadPool(NUMBER_OF_THREADS);
        }
        return mDefaultExecutorService;
    }

    public void setDefaultExecutorService(ExecutorService executorService) {
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
