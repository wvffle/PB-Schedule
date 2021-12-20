package net.wvffle.android.pb.schedule.views;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavAction;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import net.wvffle.android.pb.schedule.MainActivity;
import net.wvffle.android.pb.schedule.R;

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

    protected NavController getNavController() {
        return Navigation.findNavController(MainActivity.getInstance(), R.id.fragmentContainerView);
    }

    private static int or(int resId1, int resId2) {
        return resId1 == -1 ? resId2 : resId1;
    }

    protected void navigate(int resId) {
        NavController controller = getNavController();

        NavBackStackEntry stackEntry = controller.getCurrentBackStackEntry();
        NavDestination destination = stackEntry == null
                ? controller.getCurrentDestination()
                : stackEntry.getDestination();

        NavAction action = destination.getAction(resId);
        NavOptions oldOptions = action == null ? null : action.getNavOptions();

        NavOptions options = oldOptions == null
                ? null // TODO: Default nav options
                : new NavOptions.Builder()
                .setLaunchSingleTop(oldOptions.shouldLaunchSingleTop())
                .setPopUpTo(resId, oldOptions.isPopUpToInclusive())
                .setEnterAnim(or(oldOptions.getEnterAnim(), R.anim.nav_default_enter_anim))
                .setExitAnim(or(oldOptions.getExitAnim(), R.anim.nav_default_exit_anim))
                .setPopEnterAnim(or(oldOptions.getPopEnterAnim(), R.anim.nav_default_pop_enter_anim))
                .setPopExitAnim(or(oldOptions.getPopExitAnim(), R.anim.nav_default_pop_exit_anim))
                .build();

        controller.navigate(resId, null, options);
    }
}
