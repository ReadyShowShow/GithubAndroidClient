package com.jian.github.event;

public class Event {
    /**
     * @see EventType
     */
    private String eventType;
    private Object data;

    /**
     * @param eventType {@link EventType}
     */
    public Event(String eventType) {
        this.eventType = eventType;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
