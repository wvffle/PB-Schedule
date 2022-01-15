package net.wvffle.android.pb.schedule.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;

import net.wvffle.android.pb.schedule.MainActivity;

public abstract class BaseView<B extends ViewDataBinding> extends Fragment {
    @LayoutRes
    protected abstract int getLayoutId();

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(getLayoutId(), container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        B binding = DataBindingUtil.bind(view);
        assert binding != null;

        binding.setLifecycleOwner(getViewLifecycleOwner());
        setup(binding);
    }

    protected abstract void setup(B binding);

    protected void navigate(@IdRes int actionId) {
        MainActivity.getInstance().navigate(actionId);
    }
}
