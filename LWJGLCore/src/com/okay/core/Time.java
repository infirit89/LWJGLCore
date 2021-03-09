package com.okay.core;

public class Time {
    
    private float time;

    Time(float time) { this.time = time; }

    public float getSeconds() { return this.time; }
    public float getMiliSeconds() { return this.time * 1000; }
}
