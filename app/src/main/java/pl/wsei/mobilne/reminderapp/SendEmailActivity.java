package pl.wsei.mobilne.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class SendEmailActivity extends AppCompatActivity {
private EditText mEditTextTo;
private EditText mEditTextSubject;
private EditText mEditTextMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int a = ((UserSettings) getApplication()).getCurrentTheme();
        setTheme(a);
        setContentView(R.layout.activity_send_email);


        mEditTextTo = findViewById(R.id.edit_text_to);
        mEditTextSubject = findViewById(R.id.edit_text_subject);
        mEditTextMessage = findViewById(R.id.edit_text_message);
        Intent i = getIntent();
        String values = i.getStringExtra("dataToBePassed");

        mEditTextMessage.setText(values);
        Button btnSend = findViewById(R.id.btn_send);
        
        btnSend.setOnClickListener(v -> {
            sendMail();
        });
    }

    private void sendMail() {
        String recipientList = mEditTextTo.getText().toString();
        String[] recpients = recipientList.split(",");

        String subject = mEditTextSubject.getText().toString();
        String message = mEditTextMessage.getText().toString();

        Intent i = new Intent(Intent.ACTION_SEND);
        i.putExtra(Intent.EXTRA_EMAIL,recpients);
        i.putExtra(Intent.EXTRA_SUBJECT,subject);
        i.putExtra(Intent.EXTRA_TEXT,message);

        i.setType("message/rfc822");

        startActivity(Intent.createChooser(i,"Choose one"));

    }
}