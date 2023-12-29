package skin.support.async;

import java.util.concurrent.ExecutorService;

public abstract class AsyncTask<Input, Progress, Output> {
    private boolean mCancelled = false;

    public AsyncTask() {
    }

    public AsyncTask(ExecutorService executorService) {
        setExecutorService(executorService);
    }

    public void setExecutorService(ExecutorService executorService) {
        AsyncWorker.getInstance().setExecutorService(executorService);
    }

    /**
     * @see #execute(Object)
     */
    public AsyncTask<Input, Progress, Output> execute() {
        return execute(null);
    }

    /**
     * Starts is all
     *
     * @param input Data you want to work with in the background
     */
    public AsyncTask<Input, Progress, Output> execute(final Input input) {
        onPreExecute();
        ExecutorService executorService = AsyncWorker.getInstance().getExecutorService();
        executorService.execute(() -> {
            try {
                final Output output = doInBackground(input);
                AsyncWorker.getInstance().getHandler().post(() -> onPostExecute(output));
            } catch (final Exception e) {
                e.printStackTrace();
                AsyncWorker.getInstance().getHandler().post(() -> onBackgroundError(e));
            }
        });
        return this;
    }

    /**
     * Call to publish progress from background
     *
     * @param progress Progress made
     */
    protected void publishProgress(final Progress progress) {
        AsyncWorker.getInstance().getHandler().post(() -> {
            onProgress(progress);

            if (onProgressListener != null) {
                onProgressListener.onProgress(progress);
            }
        });
    }

    protected void onProgress(final Progress progress) {

    }

    /**
     * Call to cancel background work
     */
    public void cancel() {
        mCancelled = true;
    }

    /**
     * @return Returns true if the background work should be cancelled
     */
    protected boolean isCancelled() {
        return mCancelled;
    }

    /**
     * Call this method after cancelling background work
     */
    protected void onCancelled() {
        AsyncWorker.getInstance().getHandler().post(() -> {
            if (onCancelledListener != null) {
                onCancelledListener.onCancelled();
            }
        });
    }

    /**
     * Work which you want to be done on UI thread before {@link #doInBackground(Object)}
     */
    protected void onPreExecute() {

    }

    /**
     * Work on background
     *
     * @param input Input data
     * @return Output data
     * @throws Exception Any uncaught exception which occurred while working in background. If
     *                   any occurs, {@link #onBackgroundError(Exception)} will be executed (on the UI thread)
     */
    protected abstract Output doInBackground(Input input) throws Exception;

    /**
     * Work which you want to be done on UI thread after {@link #doInBackground(Object)}
     *
     * @param output Output data from {@link #doInBackground(Object)}
     */
    protected void onPostExecute(Output output) {

    }

    /**
     * Triggered on UI thread if any uncaught exception occurred while working in background
     *
     * @param e Exception
     * @see #doInBackground(Object)
     */
    protected abstract void onBackgroundError(Exception e);

    private OnProgressListener<Progress> onProgressListener;

    public interface OnProgressListener<Progress> {
        void onProgress(Progress progress);
    }

    public void setOnProgressListener(OnProgressListener<Progress> onProgressListener) {
        this.onProgressListener = onProgressListener;
    }

    private OnCancelledListener onCancelledListener;

    public interface OnCancelledListener {
        void onCancelled();
    }

    public void setOnCancelledListener(OnCancelledListener onCancelledListener) {
        this.onCancelledListener = onCancelledListener;
    }
}
