package net.wvffle.android.pb.schedule.viewmodels;

import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.wvffle.android.pb.schedule.MainActivity;
import net.wvffle.android.pb.schedule.ObjectBox;
import net.wvffle.android.pb.schedule.models.Degree;
import net.wvffle.android.pb.schedule.models.Update;
import net.wvffle.android.pb.schedule.models.Update_;

import java.util.List;

import io.objectbox.query.QueryBuilder;

public class SetupViewModel extends ViewModel {
    private final MutableLiveData<String> buttonName = new MutableLiveData<>("");
    private final MutableLiveData<Update> update = new MutableLiveData<>(
            ObjectBox.getUpdateBox()
                    .query()
                    .order(Update_.id, QueryBuilder.DESCENDING)
                    .build()
                    .findFirst()
    );
    private final MutableLiveData<List<Degree>> degrees = new MutableLiveData<>();

    public LiveData<String> getButtonName() {
        return buttonName;
    }

    public void setButtonName(String name) {
        buttonName.setValue(name);
    }

    public void setButtonName(@StringRes int resId) {
        setButtonName(MainActivity.getInstance().getString(resId));
    }

    public MutableLiveData<Update> getUpdate() {
        return update;
    }

    public MutableLiveData<List<Degree>> getDegrees() {
        return degrees;
    }

    public void setDegrees(List<Degree> degreeList) {
        degrees.setValue(degreeList);
    }
}
