package net.wvffle.android.pb.schedule;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

import net.wvffle.android.pb.schedule.databinding.ActivityMainBinding;

import java.util.Objects;

import io.sentry.Sentry;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    boolean alerted = false;
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
        setSupportActionBar(binding.toolbar);
        getSupportActionBar().hide();

        // NOTE: Set status bar color
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
        } else {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            getWindow().setStatusBarColor(Color.WHITE);
        }

        binding.navView.setNavigationItemSelectedListener(this);
        drawerToggle = new ActionBarDrawerToggle(this, binding.drawer, R.string.nav_open, R.string.nav_close);
        binding.drawer.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        binding.drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_CLOSED);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void sendDialogDataToActivity(String data)
    {
        Toast.makeText(this,
                data,
                Toast.LENGTH_SHORT)
                .show();
    }

    private final SensorEventListener mSensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta;
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(MainActivity.this);
            alertDialogBuilder.setTitle("Potrząsnąłeś telefonem. Ta akcja pozwala ci na zgłoszenie błędu.");
            alertDialogBuilder.setCancelable(true);
            alertDialogBuilder
                    .setMessage("Czy chcesz to zrobić?")
                    .setCancelable(true)
                    .setPositiveButton( "Tak", (dialog, id) -> {
                        try {
                            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                            builder.setTitle("Opisz swój problem.");

                            final View customLayout
                                    = getLayoutInflater()
                                    .inflate(R.layout.alert_debug_layout, null);
                            builder.setView(customLayout);

                            builder.setPositiveButton("Zatwierdź", (dialog1, which) -> {
                                EditText editText = customLayout.findViewById(R.id.editText);
                                        if (editText.length() > 0 && editText.length() <= 666) {
                                            sendDialogDataToActivity(
                                                    editText.getText()
                                                            .toString()
                                            );
                                        }
                                    })
                                    .setNegativeButton("Anuluj", (dialog12, id1) -> {
                                        dialog12.cancel();
                                        alerted = false;
                                    });

                            AlertDialog alertDialog = builder.create();
                            alertDialog .show();
                            Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                            Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                            positiveButton.setTextColor(Color.parseColor("#FF000000"));
                            negativeButton.setTextColor(Color.parseColor("#FF000000"));
                            alerted = false;

                        } catch (Exception e) {
                            e.printStackTrace();
                            Sentry.captureException(e);
                        }
                    })
                    .setNegativeButton("Nie", (dialog, id) -> {
                        dialog.cancel();
                        alerted = false;
                    });
            if (mAccel > 3 && alerted == false) {
                AlertDialog alertDialog = alertDialogBuilder.create();
                alertDialog.show();
                Button positiveButton = alertDialog.getButton(AlertDialog.BUTTON_POSITIVE);
                Button negativeButton = alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE);
                positiveButton.setTextColor(Color.parseColor("#FF000000"));
                negativeButton.setTextColor(Color.parseColor("#FF000000"));
                alerted = true;
            }
        }
        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };
    @Override
    protected void onResume() {
        mSensorManager.registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }
    @Override
    protected void onPause() {
        mSensorManager.unregisterListener(mSensorListener);
        super.onPause();
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

        switch (item.getItemId()) {
            case R.id.week_view:
                return true;

            case R.id.extra_subjects:
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
}
