package pl.wsei.mobilne.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class NotificationActivity extends AppCompatActivity {
    String values;
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        textView = findViewById(R.id.textViewData);

        Intent i = getIntent();
        String values = i.getStringExtra("dataToBePassed");

        if (values != null) {
            textView.setText(values);
        } else {
            textView.setText("No data received");
        }


    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.finish();
        Log.d("killed","activity was killed ");
    }
}