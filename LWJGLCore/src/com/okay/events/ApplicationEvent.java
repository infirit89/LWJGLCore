package com.okay.events;

import org.joml.Vector2i;

public class ApplicationEvent {

    private boolean hasClosed = false, hasResized = false;
    private int newWidth, newHeight;

    private static ApplicationEvent instance = null;

    public static ApplicationEvent getInstance() 
    {
        if(instance == null)
            instance = new ApplicationEvent();

        return instance;
    }
    
    public static void closeCallback(long window) { getInstance().hasClosed = true; }

    public static void sizeCallback(long widnow, int width, int height) 
    {
        getInstance().newHeight = width;
        getInstance().newHeight = height;

        getInstance().hasResized = true;
    }

    public static void update() { getInstance().hasResized = false; }

    public static boolean hasResized() { return getInstance().hasResized; }
    public static boolean hasClosed() { return getInstance().hasClosed; }

    public static Vector2i getWindowNewSize() { return new Vector2i(getInstance().newWidth, getInstance().newHeight); }

    public static int getWindowNewWidth() { return getInstance().newWidth; }
    public static int getWindowNewHeight() { return getInstance().newHeight; }
}
