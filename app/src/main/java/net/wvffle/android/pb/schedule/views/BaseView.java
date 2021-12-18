package net.wvffle.android.pb.schedule.views;

import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import net.wvffle.android.pb.schedule.MainActivity;
import net.wvffle.android.pb.schedule.R;

public class BaseView<B extends ViewDataBinding> extends Fragment {
    private B binding;

    protected void setup(B binding) {
        this.binding = binding;
        binding.setLifecycleOwner(getViewLifecycleOwner());
    }

    protected NavController getNavController() {
        return Navigation.findNavController(MainActivity.getInstance(), R.id.fragmentContainerView);
    }

    protected void navigate(int resId) {
        getNavController().navigate(resId);
    }
}
