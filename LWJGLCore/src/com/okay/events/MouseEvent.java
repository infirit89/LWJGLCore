package com.okay.events;

import org.joml.Vector2f;
import org.lwjgl.glfw.GLFW;

public class MouseEvent {
    
    private byte[] buttons = new byte[GLFW.GLFW_KEY_LAST], lastButtons = new  byte[GLFW.GLFW_KEY_LAST];
    private Vector2f mousePosition = new Vector2f(), scrollWheel = new Vector2f();

    private static MouseEvent instance = null;

    public static MouseEvent getInstance() 
    {
        if(instance == null)
            instance = new MouseEvent();

        return instance;
    }

    public static void buttonCallback(long window, int button, int action, int mods) 
    {
        getInstance().lastButtons[button] = getInstance().buttons[button];
        getInstance().buttons[button] = (byte)action;
    }

    public static void cursorCallback(long window, double xpos, double ypos) { getInstance().mousePosition.set(xpos, ypos); }

    public static void scrollCallback(long window, double xoffset, double yoffset) { getInstance().scrollWheel.set(xoffset, yoffset); }

    public static boolean isButtonPressed(int button) { return getInstance().buttons[button] == GLFW.GLFW_PRESS; }
    public static boolean isButtonHeld(int button) { return getInstance().buttons[button] == GLFW.GLFW_PRESS || getInstance().buttons[button] == GLFW.GLFW_REPEAT; }
    public static boolean isButtonReleased(int button) 
    {
        if(getInstance().buttons[button] != getInstance().lastButtons[button] && getInstance().buttons[button] == GLFW.GLFW_RELEASE) 
        {
            getInstance().lastButtons[button] = GLFW.GLFW_RELEASE;

            return true;
        }

        return false;
    }

    
    public static Vector2f getMousePosition() { return getInstance().mousePosition; }
    public static Vector2f getMouseScroll() { return getInstance().scrollWheel; }
}
