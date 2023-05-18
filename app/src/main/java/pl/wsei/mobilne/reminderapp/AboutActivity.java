package pl.wsei.mobilne.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int a = ((UserSettings) getApplication()).getCurrentTheme();
        setTheme(a);
        setContentView(R.layout.activity_about);

    }
}