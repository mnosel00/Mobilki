package pl.wsei.mobilne.reminderapp;

import static androidx.constraintlayout.helper.widget.MotionEffect.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.jakewharton.threetenabp.AndroidThreeTen;

import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private RecyclerView recyclerView;
    private EventAdapter adapter;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    List<Event> passedEvents = new ArrayList<>();
    private Timer alarmTimer;
    private Handler handler = new Handler();


    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int a = ((UserSettings) getApplication()).getCurrentTheme();
        setTheme(a);


        setContentView(R.layout.activity_main);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
        {
            if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED)
            {
                ActivityCompat.requestPermissions(MainActivity.this,new String[]{Manifest.permission.POST_NOTIFICATIONS},101);
            }
        }

        Intent i = getIntent();
        List<Event> eventsListOnResume = (List<Event>) i.getSerializableExtra("passedEvents");
        if (eventsListOnResume != null) {
            passedEvents.addAll(eventsListOnResume);

        }

        i.removeExtra("passedEvents");
        LocalDateTime currentTime = LocalDateTime.now();
        Log.d("time", String.valueOf(currentTime));

        // Find the NavigationView within the merged layout
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigationView);




        // Set up the ActionBarDrawerToggle and attach it to the DrawerLayout
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new EventAdapter(passedEvents);

        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new EventAdapter.OnItemClickListener() {
            @Override
            public void onDeleteClick(int position) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Potwierdzenie usunięcia");
                builder.setMessage("Czy na pewno chcesz usunąć ten element?");
                builder.setPositiveButton("Tak", (dialog, which) -> {
                    passedEvents.remove(position);
                    adapter = new EventAdapter(passedEvents);
                    adapter.notifyItemRemoved(position);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
                });
                builder.setNegativeButton("Nie", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }

            @Override
            public void onEditClick(int position) {
                Event eventToEdit = passedEvents.get(position);

                Intent intent = new Intent(MainActivity.this, EventsActivity.class);
                intent.putExtra("eventToEdit", position);
                intent.putExtra("passedEvents", (Serializable) passedEvents);

                startActivity(intent);

            }
        });


        adapter.notifyDataSetChanged();

        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date currentDate = new Date();

        for (Event event : passedEvents) {
            String alarmDateTimeString = event.getDate() + " " + event.getTime();
            try {
                Date alarmDateTime = dateFormat.parse(alarmDateTimeString);
                if (alarmDateTime.after(currentDate)) {

                    setAlarm(alarmDateTime,event.getEventRepeat(),event.getName(),event.getDescription(),event.getType(),event.isOn());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        navigationView.setNavigationItemSelectedListener(item -> {

            switch (item.getItemId()) {

                case R.id.events: {
                    Toast.makeText(MainActivity.this, "Events", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, EventsActivity.class);
                    intent.putExtra("passedEvents", (Serializable) passedEvents);
                    Log.d("nav2", "log z Nawisdsdsgacji");
                    startActivity(intent);
                    break;
                }

                case R.id.settings: {
                    Toast.makeText(MainActivity.this, "Settings", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, SettingsActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.about: {
                    Toast.makeText(MainActivity.this, "About", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                    startActivity(intent);
                    break;
                }
                case R.id.exit: {
                    Toast.makeText(MainActivity.this, "Exit", Toast.LENGTH_SHORT).show();
                    new AlertDialog.Builder(MainActivity.this)
                            .setMessage("Are you sure want to exit?")
                            .setCancelable(false)
                            .setPositiveButton("yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            })
                            .setNegativeButton("NO", null)
                            .show();
                    break;
                }
            }
            return false;
        });
    }

    private void setAlarm(Date alarmDateTime, String rep, String name, String description,String type,boolean isOn) {
        // Oblicz opóźnienie w milisekundach do czasu alarmu
        long delayInMillis = alarmDateTime.getTime() - System.currentTimeMillis();


        // Uruchom budzik po upływie opóźnienia
        alarmTimer = new Timer();
        alarmTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    public void run() {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                            if (type.equals("Write an email"))
                            {
                                makeNotificationToSendEmail(alarmDateTime, rep,name,description,type);
                            }
                            else if (type.equals("Write an sms"))
                            {
                                makeNotificationToSendSMS(alarmDateTime,rep,name,description,type);
                            }
                            else
                            {
                                makeNotification(name,description);
                            }

                        }
                        Log.d("Alarm", "Budzik gra");

                        if (rep.equals("Every day"))
                            {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(alarmDateTime);
                                calendar.add(Calendar.DAY_OF_YEAR, 1); // Dodaj 1 dzień

                                // Ustaw nową datę i czas dla następnego powtórzenia alarmu
                                Date nextAlarmDateTime = calendar.getTime();
                                setAlarm(nextAlarmDateTime, rep,name,description,type,isOn);

                            }
                            else if (rep.equals("Every week"))
                            {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(alarmDateTime);
                                calendar.add(Calendar.WEEK_OF_YEAR, 1); // Dodaj 1 tydzien

                                // Ustaw nową datę i czas dla następnego powtórzenia alarmu
                                Date nextAlarmDateTime = calendar.getTime();
                                setAlarm(nextAlarmDateTime, rep,name,description,type,isOn);

                            }
                            else if (rep.equals("Every month"))
                            {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(alarmDateTime);
                                calendar.add(Calendar.MONTH, 1); // Dodaj 1 miesiac

                                // Ustaw nową datę i czas dla następnego powtórzenia alarmu
                                Date nextAlarmDateTime = calendar.getTime();
                                setAlarm(nextAlarmDateTime, rep,name,description,type,isOn);
                            }
                            else if (rep.equals("Every year"))
                            {
                                Calendar calendar = Calendar.getInstance();
                                calendar.setTime(alarmDateTime);
                                calendar.add(Calendar.YEAR, 1); // Dodaj 1 rok

                                // Ustaw nową datę i czas dla następnego powtórzenia alarmu
                                Date nextAlarmDateTime = calendar.getTime();
                                setAlarm(nextAlarmDateTime, rep,name,description,type,isOn);
                            }
                        // Tutaj możesz dodać kod do odtworzenia dźwięku budzika
                    }
                });
            }
        }, delayInMillis);

        DateFormat alarmDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Log.d("Alarm", "Budzik został ustawiony" + alarmDateFormat.format(alarmDateTime));
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen((GravityCompat.START)))
        {
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public void makeNotificationToSendSMS(Date alarmDateTime, String rep, String name, String description,String type)
    {
        String concat = name + " " + description + " " + alarmDateTime.toString() + " " + rep + " " + type;
        String channelID = "CHANNEL_ID_NOTIFICATIONa";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),channelID);

        builder.setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .setContentTitle("New SMS Event")
                .setContentText("Click here to send an SMS ")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(getApplicationContext(),SendSMSActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("dataToBePassed",concat);



        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelID);

            if (notificationChannel == null){
                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(channelID,"Some descr",importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        notificationManager.notify(0,builder.build());

    }
    @RequiresApi(api = Build.VERSION_CODES.S)
    public void makeNotificationToSendEmail(Date alarmDateTime, String rep, String name, String description,String type)
    {
        String concat = name + " " + description + " " + alarmDateTime.toString() + " " + rep + " " + type;
        String channelID = "CHANNEL_ID_NOTIFICATIONa";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),channelID);

        builder.setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .setContentTitle("New Email Event")
                .setContentText("Click here to send an email ")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(getApplicationContext(),SendEmailActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("dataToBePassed",concat);



        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelID);

            if (notificationChannel == null){
                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(channelID,"Some descr",importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        notificationManager.notify(0,builder.build());

    }

    @RequiresApi(api = Build.VERSION_CODES.S)
    public void makeNotification(String name, String description)
    {
        String concat = name + " " + description;
        String channelID = "CHANNEL_ID_NOTIFICATIONa";
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),channelID);

        builder.setSmallIcon(R.drawable.baseline_circle_notifications_24)
                .setContentTitle("New Event")
                .setContentText("You have " + " " +name + " "+" event upcoming")
                .setAutoCancel(true)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);

        Intent intent = new Intent(getApplicationContext(),NotificationActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("dataToBePassed",concat);



        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(),0,intent, PendingIntent.FLAG_CANCEL_CURRENT | PendingIntent.FLAG_MUTABLE);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            NotificationChannel notificationChannel = notificationManager.getNotificationChannel(channelID);

            if (notificationChannel == null){
                int importance = NotificationManager.IMPORTANCE_HIGH;
                notificationChannel = new NotificationChannel(channelID,"Some descr",importance);
                notificationChannel.setLightColor(Color.GREEN);
                notificationChannel.enableVibration(true);
                notificationManager.createNotificationChannel(notificationChannel);
            }
        }

        notificationManager.notify(0,builder.build());

    }


}