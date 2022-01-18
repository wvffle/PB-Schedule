package net.wvffle.android.pb.schedule.views;

import net.wvffle.android.pb.schedule.MainActivity;
import net.wvffle.android.pb.schedule.R;
import net.wvffle.android.pb.schedule.databinding.FragmentSettingsViewBinding;
import net.wvffle.android.pb.schedule.views.settings.SettingsFragment;

public class SettingsView extends BaseView<FragmentSettingsViewBinding> {
    @Override
    protected int getLayoutId() {
        return R.layout.fragment_settings_view;
    }

    @Override
    protected void setup(FragmentSettingsViewBinding binding) {
        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.preferences, new SettingsFragment())
                .commit();

        MainActivity.getInstance()
                .getSupportActionBar()
                .setTitle(R.string.settings);
    }
}