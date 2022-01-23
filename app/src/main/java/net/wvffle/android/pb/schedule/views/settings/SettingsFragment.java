package net.wvffle.android.pb.schedule.views.settings;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.preference.Preference;
import androidx.preference.PreferenceFragmentCompat;

import net.wvffle.android.pb.schedule.MainActivity;
import net.wvffle.android.pb.schedule.R;

public class SettingsFragment extends PreferenceFragmentCompat {
    @Override
    public void onCreatePreferences(Bundle savedInstanceState, String rootKey) {
        setPreferencesFromResource(R.xml.main_preferences, rootKey);

        Preference reset = findPreference("reset");
        assert reset != null;

        reset.setOnPreferenceClickListener(preference -> {
            SharedPreferences pref = requireActivity().getSharedPreferences("data", Context.MODE_PRIVATE);
            pref.edit().putBoolean("setup-done", false).apply();
            MainActivity.getInstance().navigate(R.id.setupView);
            return true;
        });
    }


}
