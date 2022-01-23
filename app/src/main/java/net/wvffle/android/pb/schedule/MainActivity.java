package net.wvffle.android.pb.schedule;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavAction;
import androidx.navigation.NavBackStackEntry;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.NavOptions;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.messaging.FirebaseMessaging;

import net.wvffle.android.pb.schedule.api.setup.SetupData;
import net.wvffle.android.pb.schedule.databinding.ActivityMainBinding;
import net.wvffle.android.pb.schedule.util.Serializer;

import java.io.IOException;
import java.util.Objects;

import io.sentry.Sentry;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static MainActivity instance;
    private ActionBarDrawerToggle drawerToggle;

    public static MainActivity getInstance() {
        return instance;
    }

    private ActivityMainBinding binding;

    private static int or(int resId1, int resId2) {
        return resId1 == -1 ? resId2 : resId1;
    }

    Toolbar getToolbar() {
        return binding.toolbar;
    }

    public static SetupData getSetupData(Context context) {
        SetupData setupData = null;

        try {
            SharedPreferences pref = context.getSharedPreferences("data", Context.MODE_PRIVATE);
            String savedData = pref.getString("setup-data", "");
            if (savedData.equals("")) {
                return null;
            }

            setupData = (SetupData) Serializer.getInstance().fromString(savedData);
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            Sentry.captureException(e);
        } catch (IncompatibleClassChangeError e) {
            e.printStackTrace();
        }

        return setupData;
    }

    @NonNull
    @Override
    public ActionBar getSupportActionBar() {
        return Objects.requireNonNull(super.getSupportActionBar());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public DrawerLayout getDrawer() {
        return binding.drawer;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        int id = item.getItemId();
        if (id == R.id.settings) {
            navigate(R.id.settingsView);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.home) {
            navigate(R.id.homeView);
            return true;
        }

        if (id == R.id.day_view) {
            navigate(R.id.dayView);
            return true;
        }

        if (id == R.id.extra_subjects) {
            navigate(R.id.extraSubjectsView);
            return true;
        }

        switch (item.getItemId()) {
            case R.id.week_view:
                return true;

            case R.id.updates:
                return true;
        }

        return true;
    }

    public void navigate(int resId) {
        NavController controller = Navigation.findNavController(this, R.id.fragmentContainerView);

        NavBackStackEntry stackEntry = controller.getCurrentBackStackEntry();
        NavDestination destination = stackEntry == null
                ? controller.getCurrentDestination()
                : stackEntry.getDestination();

        NavAction action = destination.getAction(resId);
        NavOptions oldOptions = action == null ? null : action.getNavOptions();

        NavOptions options = oldOptions == null
                ? new NavOptions.Builder()
                .setEnterAnim(R.anim.nav_default_enter_anim)
                .setExitAnim(R.anim.nav_default_exit_anim)
                .setPopEnterAnim(R.anim.nav_default_pop_enter_anim)
                .setPopExitAnim(R.anim.nav_default_pop_exit_anim)
                .build()
                : new NavOptions.Builder()
                .setLaunchSingleTop(oldOptions.shouldLaunchSingleTop())
                .setPopUpTo(resId, oldOptions.isPopUpToInclusive())
                .setEnterAnim(or(oldOptions.getEnterAnim(), R.anim.nav_default_enter_anim))
                .setExitAnim(or(oldOptions.getExitAnim(), R.anim.nav_default_exit_anim))
                .setPopEnterAnim(or(oldOptions.getPopEnterAnim(), R.anim.nav_default_pop_enter_anim))
                .setPopExitAnim(or(oldOptions.getPopExitAnim(), R.anim.nav_default_pop_exit_anim))
                .build();

        getDrawer().close();
        controller.navigate(resId, null, options);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        createNotificationChannel();

        instance = this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().hide();
        getSupportActionBar().setSubtitle(R.string.subtitle);

        // NOTE: Set status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }

        binding.navView.setNavigationItemSelectedListener(this);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        drawerToggle = new ActionBarDrawerToggle(this, binding.drawer, R.string.nav_open, R.string.nav_close);
        binding.drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);

        FirebaseMessaging.getInstance().subscribeToTopic("updates");
        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(task -> {
            if (!task.isSuccessful()) {
                Log.w("TOKEN", "Fetching FCM registration token failed", task.getException());
                return;
            }

            Log.d("TOKEN", task.getResult());
        });
    }

    public SetupData getSetupData() {
        return getSetupData(this);
    }

    private void createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_desc);
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("waff-pb", name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
}
