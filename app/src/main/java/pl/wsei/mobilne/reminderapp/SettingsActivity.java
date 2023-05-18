package pl.wsei.mobilne.reminderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.preference.PreferenceManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.switchmaterial.SwitchMaterial;

public class SettingsActivity extends AppCompatActivity {

    private View parentView;

    private SwitchMaterial themeSwitch;
    private TextView titleTheme, setTheTheme;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        int currentTheme = ((UserSettings) getApplication()).getCurrentTheme();
        setTheme(currentTheme);
        setContentView(R.layout.activity_settings);
        themeSwitch = findViewById(R.id.themeSwithc);

        setTheTheme = findViewById(R.id.setTheTheme);

        sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        boolean switchState = sharedPreferences.getBoolean("switchState",false);


        themeSwitch.setChecked(switchState);
        if (themeSwitch.isChecked())
        {
            setTheTheme.setText("Dark");
        }else{
            setTheTheme.setText("Light");
        }



        themeSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {

            if (themeSwitch.isChecked())
            {

                ((UserSettings) getApplication()).setCurrentTheme(R.style.AppTheme_Dark);
                editor.putBoolean("switchState", true);
                editor.putInt("themeNo",((UserSettings) getApplication()).getCurrentTheme());
                editor.apply();
            }
            else
            {
                setTheTheme.setText("Light");
                ((UserSettings) getApplication()).setCurrentTheme(R.style.Theme_ReminderApp);
                editor.putBoolean("switchState", false);
                editor.putInt("themeNo",((UserSettings) getApplication()).getCurrentTheme());
                editor.apply();
            }

            restartActivity();



        });



    }

    private void restartActivity() {
        //Intent intent = new Intent(MainActivity.this, EventsActivity.class);
        Intent intent = new Intent(SettingsActivity.this,SplashScreenActivity.class);
        finish();
        startActivity(intent);
    }


}