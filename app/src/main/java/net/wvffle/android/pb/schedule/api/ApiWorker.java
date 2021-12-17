package net.wvffle.android.pb.schedule.api;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.work.Data;
import androidx.work.OneTimeWorkRequest;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

import java.util.HashMap;
import java.util.Map;

public class ApiWorker extends Worker {
    private static long callId = 0;
    private static final Map<Long, Executor> executorMap = new HashMap<>();

    public ApiWorker (@NonNull Context context, @NonNull WorkerParameters params) {
        super(context, params);
    }

    /**
     * Performs work in an asynchronous context
     * @return Result
     */
    @NonNull
    @Override
    public Result doWork() {
        long id = getInputData().getLong("callId", -1);
        if (id == -1 || !executorMap.containsKey(id)) {
            return Result.failure();
        }

        Executor executor = executorMap.get(id);
        assert executor != null;
        return executor.execute();
    }

    /**
     * Create a custom OneTimeWorkRequest with an executor
     * @param executor Executor lambda
     * @return OneTimeWorkRequest object
     */
    public static OneTimeWorkRequest create (Executor executor) {
        executorMap.put(callId, executor);

        Data data = new Data.Builder()
                .putLong("callId", callId++)
                .build();

        return new OneTimeWorkRequest.Builder(ApiWorker.class)
                .setInputData(data)
                .build();
    }

    /**
     * Executor interface
     */
    public interface Executor {
        Result execute();
    }
}
