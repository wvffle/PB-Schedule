package net.wvffle.android.pb.schedule.viewmodels;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import androidx.work.ListenableWorker;
import androidx.work.WorkManager;

import net.wvffle.android.pb.schedule.api.ApiWorker;
import net.wvffle.android.pb.schedule.api.BackendApi;
import net.wvffle.android.pb.schedule.api.update.UpdateEntry;

import java.util.ArrayList;
import java.util.List;

public class UpdateListViewModel extends ViewModel {
    private final MutableLiveData<List<UpdateEntry>> updateEntries = new MutableLiveData<>(new ArrayList<>());
    public final MutableLiveData<Boolean> dataLoading = new MutableLiveData<>(false);

    public void loadUpdates (Context context) {
        WorkManager.getInstance(context).enqueue(ApiWorker.create(() -> {
            dataLoading.postValue(true);
            updateEntries.postValue(BackendApi.getUpdates());
            dataLoading.postValue(false);
            return ListenableWorker.Result.success();
        }));
    }

    public LiveData<List<UpdateEntry>> getUpdates () {
        return updateEntries;
    }

    public LiveData<Boolean> isLoading () {
        return dataLoading;
    }
}
