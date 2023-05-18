package pl.wsei.mobilne.reminderapp;

import android.content.res.ColorStateList;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.ToggleButton;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.EventViewHolder> {

    private List<Event> eventList = new ArrayList<>();
    private OnItemClickListener mListener;

    public EventAdapter(List<Event> eventList)
    {
        this.eventList.addAll(eventList);

    }
    public interface OnItemClickListener {
        void onDeleteClick(int position);
        void onEditClick(int position);
    }

    // Metoda, która ustawia listener dla kliknięcia na przycisk
    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @Override
    public EventAdapter.EventViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_event,parent,false);
        return new EventViewHolder(view);
    }

    @Override
    public void onBindViewHolder( EventAdapter.EventViewHolder holder, int position) {
        Event event = eventList.get(position);

        holder.deleteButton.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onDeleteClick(holder.getAdapterPosition());
            }
        });

        holder.editButton.setOnClickListener(v -> {
            if (mListener!=null)
            {
                mListener.onEditClick(holder.getAdapterPosition());
            }
        });

        if (event.isOn())
        {
            holder.checkBox.setChecked(true);
            holder.checkBox.setText("Active");
        }
        else
        {
            holder.checkBox.setChecked(false);
            holder.checkBox.setText("Not Active");
        }
        holder.event_item_name.setText(event.getName());
        holder.event_item_description.setText(event.getDescription());
        holder.event_item_date.setText(event.getDate());

        String timeString = event.getTime().toString();
        holder.event_item_time.setText(timeString);

        holder.event_item_type.setText(event.getType());
        holder.event_item_rep.setText(event.getEventRepeat());

    }

    @Override
    public int getItemCount() {
        return eventList.size();
    }

    public class EventViewHolder extends RecyclerView.ViewHolder {

        public TextView event_item_name;
        public TextView event_item_description;
        public TextView event_item_date;
        public TextView event_item_time;
        public TextView event_item_type;
        public TextView event_item_rep;
        public Button deleteButton;
        public Button editButton;
        public ToggleButton checkBox;

        public EventViewHolder(@NonNull View itemView) {
            super(itemView);
            checkBox = itemView.findViewById(R.id.myCheckbox);
            deleteButton = itemView.findViewById(R.id.remove_event_button);
            editButton = itemView.findViewById(R.id.edit_event_button);
            event_item_name = itemView.findViewById(R.id.event_item_name);
            event_item_description = itemView.findViewById(R.id.event_item_description);
            event_item_date = itemView.findViewById(R.id.event_item_date);
            event_item_time = itemView.findViewById(R.id.event_item_time);
            event_item_type = itemView.findViewById(R.id.event_item_type);
            event_item_rep = itemView.findViewById(R.id.event_item_rep);
        }
    }


}
