package com.okay.core;

import java.nio.DoubleBuffer;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.GLFW;

public class Input {

    private static long window;

    public static void setWindow(long window) { Input.window = window; }

    public static boolean isKeyPressed(int keycode) 
    {
        int state = GLFW.glfwGetKey(window, keycode);

        return state == GLFW.GLFW_REPEAT || state == GLFW.GLFW_PRESS;
    }

    public static Vector2f getMousePosition() 
    {
        DoubleBuffer x = BufferUtils.createDoubleBuffer(1), y = BufferUtils.createDoubleBuffer(1);

        GLFW.glfwGetCursorPos(window, x, y);

        return new Vector2f((float)x.get(0), (float)y.get(0));
    }

    public static boolean isMouseButtonPressed(int button) 
    {
        int state = GLFW.glfwGetMouseButton(window, button);

        return state == GLFW.GLFW_PRESS || state == GLFW.GLFW_REPEAT;
    }
}