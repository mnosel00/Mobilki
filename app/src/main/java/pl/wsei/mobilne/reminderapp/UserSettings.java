package pl.wsei.mobilne.reminderapp;

import android.app.Application;
import android.content.SharedPreferences;

public class UserSettings extends Application {
    private int currentTheme;


    public int getCurrentTheme() {
        return currentTheme;
    }

    public void setCurrentTheme(int theme) {
        currentTheme = theme;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences pref = getSharedPreferences("MyPrefs",MODE_PRIVATE);
        int theme = pref.getInt("themeNo",R.style.Theme_ReminderApp);

        // Domyślnie ustawiamy jeden z motywów
        setCurrentTheme(theme);
    }
}

