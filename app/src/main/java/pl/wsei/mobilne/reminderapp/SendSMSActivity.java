package pl.wsei.mobilne.reminderapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.text.Editable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SendSMSActivity extends AppCompatActivity {
private EditText phoneNumber;
private EditText messageText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int a = ((UserSettings) getApplication()).getCurrentTheme();
        setTheme(a);
        setContentView(R.layout.activity_send_smsactivity);


        ActivityCompat.requestPermissions(SendSMSActivity.this,
                new String[]{Manifest.permission.SEND_SMS,Manifest.permission.READ_SMS,Manifest.permission.RECEIVE_SMS},
                PackageManager.PERMISSION_GRANTED);
        
        phoneNumber = findViewById(R.id.edit_text_phone_number);
        messageText = findViewById(R.id.edit_text_message);

        Intent i = getIntent();
        String values = i.getStringExtra("dataToBePassed");

        messageText.setText(values);
        
        Button btn = findViewById(R.id.button_send);
        
        btn.setOnClickListener(v -> {
            openMessagingApp();
        });
    }

    private void openMessagingApp() {
        String number = phoneNumber.getText().toString();
        Uri uri = Uri.parse("smsto:" + number);
        Intent smsIntent = new Intent(Intent.ACTION_SENDTO, uri);
        smsIntent.putExtra("sms_body", messageText.getText().toString());
        startActivity(smsIntent);
    }

}