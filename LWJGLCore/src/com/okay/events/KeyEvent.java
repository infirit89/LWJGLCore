package com.okay.events;

import org.lwjgl.glfw.GLFW;

public class KeyEvent {
    
    private byte[] keys = new byte[GLFW.GLFW_KEY_LAST], lastKeys = new byte[GLFW.GLFW_KEY_LAST];

    private static KeyEvent instance = null;

    public static KeyEvent getInstance() 
    {
        if(instance == null) 
            instance = new KeyEvent();

        return instance;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods) 
    {
        getInstance().lastKeys[key] = getInstance().keys[key];
        getInstance().keys[key] = (byte)action;
    }

    public static boolean isKeyPressed(int keycode) { return getInstance().keys[keycode] == GLFW.GLFW_PRESS; }
    public static boolean isKeyHeld(int keycode) { return getInstance().keys[keycode] == GLFW.GLFW_PRESS || getInstance().keys[keycode] == GLFW.GLFW_REPEAT; }

    public static boolean isKeyReleased(int keycode) 
    {
        if(getInstance().keys[keycode] != getInstance().lastKeys[keycode] && getInstance().keys[keycode] == GLFW.GLFW_RELEASE)
        {
            getInstance().lastKeys[keycode] = GLFW.GLFW_RELEASE;
            return true;
        }

        return false;
    } 

}
