package com.example.ahmed.soonami3;

public class Event {
    /** Title of the earthquake event */
    public final String title;

    /** Time that the earthquake happened (in milliseconds) */
    public final long time;

    /** Whether or not a tsunami alert was issued (1 if it was issued, 0 if no alert was issued) */
    public final int tsunamiAlert;



    /** Url that the url of the event for more details */
    public final String url;
    /**
     * Constructs a new {@link Event}.
     *
     * @param eventTitle is the title of the earthquake event
     * @param eventTime is the time the earhtquake happened
     * @param eventTsunamiAlert is whether or not a tsunami alert was issued
     */
    public Event(String eventTitle, long eventTime, int eventTsunamiAlert , String url) {
        title = eventTitle;
        time = eventTime;
        tsunamiAlert = eventTsunamiAlert;
        this.url = url;
    }

    public String getTitle() {
        return title;
    }

    public long getTime() {
        return time;
    }

    public int getTsunamiAlert() {
        return tsunamiAlert;
    }

    public String getUrl() {
        return url;
    }
}
