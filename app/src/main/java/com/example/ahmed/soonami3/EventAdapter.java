package com.example.ahmed.soonami3;


import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class EventAdapter extends ArrayAdapter<Event> {

    ArrayList<Event> allEvents;
    Context context;

    public EventAdapter(@NonNull Context context, @NonNull ArrayList<Event> allEvents) {
        super(context, 0 , allEvents);
        this.allEvents = allEvents;
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Event event = getItem(position);
        Date date;

        ViewHolder viewHolder ;
        if(convertView == null){
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item , parent , false);
            viewHolder.event_name = (TextView)convertView.findViewById(R.id.event_name);
            viewHolder.event_alert = (TextView)convertView.findViewById(R.id.event_alert);
            viewHolder.event_date = (TextView)convertView.findViewById(R.id.event_date);
            viewHolder.event_time = (TextView)convertView.findViewById(R.id.event_time);
            convertView.setTag(viewHolder);
        }

        else {
            viewHolder = (ViewHolder)convertView.getTag();
        }

        date = new Date(event.getTime());

        viewHolder.event_name.setText("" + event.getTitle());
        viewHolder.event_alert.setText(""+ getTsunamiAlertString(event.getTsunamiAlert()));

        viewHolder.event_time.setText(""+ formatTime(date));
        viewHolder.event_date.setText(""+ formatDate(date));

        return convertView;
    }




    private String formatDate (Date dateObject){
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd LLL yyyy");
        return dateFormat.format(dateObject);
    }

    private String formatTime (Date dateObject){
        SimpleDateFormat timeFormat = new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String getDateString(long timeInMilliseconds) {
        SimpleDateFormat formatter = new SimpleDateFormat("EEE, d MMM yyyy 'at' HH:mm:ss z");
        return formatter.format(timeInMilliseconds);
    }
    /**
     * Return the display string for whether or not there was a tsunami alert for an earthquake.
     */
    private String getTsunamiAlertString(int tsunamiAlert) {
        switch (tsunamiAlert) {
            case 0:
                return context.getString(R.string.alert_no);
            case 1:
                return context.getString(R.string.alert_yes);
            default:
                return context.getString(R.string.alert_not_available);
        }
    }


    public static class ViewHolder {
        private TextView event_name;
        private TextView event_date;
        private TextView event_time;
        private TextView event_alert;
    }

}
