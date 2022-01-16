package net.wvffle.android.pb.schedule;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import net.wvffle.android.pb.schedule.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private SensorManager mSensorManager;
    private float mAccel;
    private float mAccelCurrent;
    private float mAccelLast;
    private static MainActivity instance;

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        ActivityMainBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        Objects.requireNonNull(mSensorManager).registerListener(mSensorListener, mSensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);
        mAccel = 10f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
//            actionBar.hide();
        }

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
            boolean alerted = false;
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    MainActivity.this);
            // set title
            alertDialogBuilder.setTitle("Potrząsnąłeś telefonem. Ta akcja pozwala ci na zgłoszenie błędu.");
            alertDialogBuilder.setCancelable(true);
            // set dialog message
            alertDialogBuilder
                    .setMessage("Czy chcesz to zrobić?")
                    .setCancelable(true)
                    .setPositiveButton( "Tak",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            try {
                                AlertDialog.Builder builder
                                        = new AlertDialog.Builder(MainActivity.this);
                                builder.setTitle("Opisz swój problem.");

                                // set the custom layout
                                final View customLayout
                                        = getLayoutInflater()
                                        .inflate(
                                                R.layout.custom_layout,
                                                null);
                                builder.setView(customLayout);

                                // add a button
                                builder
                                        .setPositiveButton(
                                                "Zatwierdź",
                                                new DialogInterface.OnClickListener() {

                                                    @Override
                                                    public void onClick(
                                                            DialogInterface dialog,
                                                            int which)
                                                    {

                                                        // send data from the
                                                        // AlertDialog to the Activity
                                                        EditText editText
                                                                = customLayout
                                                                .findViewById(
                                                                        R.id.editText);
                                                        sendDialogDataToActivity(
                                                                editText
                                                                        .getText()
                                                                        .toString());
                                                    }
                                                })
                                        .setNegativeButton("Anuluj",new DialogInterface.OnClickListener() {
                                                    public void onClick(DialogInterface dialog, int id) {
                                                        //do something if you need
                                                        dialog.cancel();
                                                    }
                                                });


                                // create and show
                                // the alert dialog
                                AlertDialog alertDialog
                                        = builder.create();
                                alertDialog .show();

                            } catch (Exception e) {
                                //Exception
                            }
                        }
                    })
                    .setNegativeButton("Nie",new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog,int id) {
                            //do something if you need
                            dialog.cancel();
                        }
                    });
            if (mAccel > 3 && alerted == false) {
                // create alert dialog
                AlertDialog alertDialog = alertDialogBuilder.create();

                alerted = true;
                // show it
                alertDialog.show();
                return;

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
}
