package net.wvffle.android.pb.schedule.viewmodels;

import androidx.annotation.StringRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.wvffle.android.pb.schedule.MainActivity;

public class SetupViewModel extends ViewModel {
    private final MutableLiveData<String> buttonName = new MutableLiveData<>("");

    public LiveData<String> getButtonName() {
        return buttonName;
    }

    public void setButtonName(String name) {
        buttonName.setValue(name);
    }

    public void setButtonName(@StringRes int resId) {
        setButtonName(MainActivity.getInstance().getString(resId));
    }
}
