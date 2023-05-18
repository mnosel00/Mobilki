package pl.wsei.mobilne.reminderapp;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import java.io.Serializable;
import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class EventsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private EditText mEventNameEditText;
    private EditText mEventDescriptionEditText;
    private EditText mEventDateEditText;
    private EditText mEventTimeEditText;
    private Spinner mEventTypeSpinner;
    private Spinner mEventRepeatSinner;
    private Button mAddEventButton;
    private Button mGobackEventButton;
    int eventToEditPosition = -1;


    private List<Event> passedEvents = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int a = ((UserSettings) getApplication()).getCurrentTheme();
        setTheme(a);
        setContentView(R.layout.activity_events);


        Intent i = getIntent();
        passedEvents = (List<Event>) i.getSerializableExtra("passedEvents") ;



        mEventNameEditText = findViewById(R.id.event_name_edit_text);
        mEventDescriptionEditText = findViewById(R.id.event_description_edit_text);
        mEventDateEditText = findViewById(R.id.event_date_edit_text);
        mEventTypeSpinner = findViewById(R.id.event_type_spinner);
        mAddEventButton = findViewById(R.id.add_event_button);
        mGobackEventButton = findViewById(R.id.goback_event_button);
        mEventTimeEditText = findViewById(R.id.event_time_edit_text);
        mEventRepeatSinner = findViewById(R.id.event_type_spinner_time);

        if (i.hasExtra("eventToEdit"))
        {
            eventToEditPosition = i.getIntExtra("eventToEdit",-1);

            if (eventToEditPosition >= 0 && eventToEditPosition < passedEvents.size()) {
                Event selectedEvent = passedEvents.get(eventToEditPosition);

                mEventNameEditText.setText(selectedEvent.getName());
                mEventDescriptionEditText.setText(selectedEvent.getDescription());
                mAddEventButton.setText("Save event");
                mGobackEventButton.setText("Go To Event List");


            }
        }

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.event_types, android.R.layout.simple_spinner_item);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        ArrayAdapter<CharSequence> adapterRep = ArrayAdapter.createFromResource(this,
                R.array.repeat_options, android.R.layout.simple_spinner_item);

        adapterRep.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);


        mEventTypeSpinner.setAdapter(adapter);
        mEventTypeSpinner.setOnItemSelectedListener(this);

        mEventRepeatSinner.setAdapter(adapterRep);
        mEventRepeatSinner.setOnItemSelectedListener(this);

        mEventDateEditText.setOnClickListener(view -> {
            final Calendar calendar = Calendar.getInstance();
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH);
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(EventsActivity.this,
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            month += 1;

                            String formattedMonth = String.format("%02d", month);
                            String formattedDay = String.format("%02d", day);

                            String date = year + "-" + formattedMonth + "-" + formattedDay;
                            mEventDateEditText.setText(date);
                        }
                    }, year, month, day);

            datePickerDialog.show();
        });
        mEventTimeEditText.setOnClickListener(v -> {
            Calendar currentTime = Calendar.getInstance();
            int hour = currentTime.get(Calendar.HOUR_OF_DAY);
            int minute = currentTime.get(Calendar.MINUTE);

            TimePickerDialog timePickerDialog = new TimePickerDialog(EventsActivity.this,
                    new TimePickerDialog.OnTimeSetListener() {
                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                            @SuppressLint("DefaultLocale") String selectedTime = String.format("%02d:%02d", hourOfDay, minute);
                            mEventTimeEditText.setText(selectedTime);
                        }
                    }, hour, minute, true);

            timePickerDialog.show();
        });


        mAddEventButton.setOnClickListener(view -> {
            String eventName = mEventNameEditText.getText().toString();
            String eventDescription = mEventDescriptionEditText.getText().toString();
            String eventDate = mEventDateEditText.getText().toString();
            String eventTime = mEventTimeEditText.getText().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());
            Date date = null;
            try {
                date = sdf.parse(eventTime);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            Time time = new Time(date.getTime());

            String eventType = mEventTypeSpinner.getSelectedItem().toString();
            String eventRepeat = mEventRepeatSinner.getSelectedItem().toString();



            if (eventName.isEmpty() || eventDate.isEmpty()) {
                Toast.makeText(getApplicationContext(),
                        "Please enter event name and date", Toast.LENGTH_SHORT).show();
            } else {
                Event event = new Event(eventName, eventDescription, eventDate, time, eventType,eventRepeat);

                passedEvents.add(event);
                if (i.hasExtra("eventToEdit"))
                {
                    passedEvents.remove(eventToEditPosition);
                }

                Log.d("TAG", String.valueOf(time));
                Toast.makeText(getApplicationContext(),
                        "Event added successfully", Toast.LENGTH_SHORT).show();
                clearFields();
            }
        });
        mGobackEventButton.setOnClickListener(v -> {
            Intent intent = new Intent(EventsActivity.this, MainActivity.class);
            intent.putExtra("passedEvents",(Serializable) passedEvents);
            startActivity(intent);
        });

    }

    private void clearFields() {
        mEventNameEditText.setText("");
        mEventDescriptionEditText.setText("");
        mEventDateEditText.setText("");
        mEventTimeEditText.setText("");
        mEventTypeSpinner.setSelection(0);
        mEventRepeatSinner.setSelection(0);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int position, long l) {
        String selectedEventType = adapterView.getItemAtPosition(position).toString();
        Toast.makeText(getApplicationContext(),
                "Selected event type: " + selectedEventType, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}