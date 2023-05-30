package pl.wsei.mobilne.reminderapp;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EventPast {
    public static class Event implements Serializable {
        private String name;
        private String description;
        private String date;
        private String type;
        private String eventRepeat;
        private boolean isOn;


        public Event(String name, String description, String date, String type, String eventRepeat) {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date currentDate = new Date();
            this.name = name;
            this.description = description;
            this.date = date;
            this.type = type;
            this.eventRepeat = eventRepeat;
            String alarmDateTimeString = date ;
            try
            {
                Date alarmDateTime = dateFormat.parse(alarmDateTimeString);
                this.isOn = alarmDateTime.after(currentDate);
            }
            catch (ParseException e)
            {
                e.printStackTrace();
            }

        }

        public String getName() {return name;}

        public String getDescription() {return description;}

        public String getDate() {return date;}

        public String getType() {return type;}

        public String getEventRepeat() { return eventRepeat; }

        public boolean isOn()
        {
            return isOn;
        }

    }
}