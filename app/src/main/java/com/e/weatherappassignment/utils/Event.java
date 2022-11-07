package com.e.weatherappassignment.utils;

import org.json.JSONObject;

public class Event {
    private int key;
    private String response;
    private JSONObject jsonObject;
    private int responseInteger;

    public Event(int key, String response) {
        this.key = key;
        this.response = response;
    }
    public Event(int key, JSONObject jsonObject) {
        this.key = key;
        this.jsonObject = jsonObject;
    }


    public Event(int key, int data) {
        this.key = key;
        this.responseInteger = data;
    }


    public int getKey() {
        return key;
    }

    public JSONObject getJsonObject() {
        return jsonObject;
    }


    public String getResponse() {
        return response;
    }

    public Integer getResponseInt() {
        return responseInteger;
    }

}
