package com.example.smarthome.relays.models;

import com.example.smarthome.WifiDevice;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Relay extends WifiDevice{

    @SerializedName("id")
    @Expose
    private Long id;

    private boolean expanded;

    public Relay(String name,  String ip, boolean on){
        super(name, ip, on);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public boolean isExpanded() {
        return expanded;
    }

    public void setExpanded(boolean expanded) {
        this.expanded = expanded;
    }
}
