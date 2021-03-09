package com.okay.window;

import static org.lwjgl.glfw.GLFW.*;

import com.okay.core.Insist;
import com.okay.events.ApplicationEvent;
import com.okay.events.KeyEvent;
import com.okay.events.MouseEvent;

import org.lwjgl.glfw.GLFWVidMode;
import org.lwjgl.opengl.GL;

public class Window {
    
    private static long window;

    private int width, height;

    private byte vsync = 1;

    public Window(int width, int height, String title) 
    {
        createWindow(width, height, title);
        this.width = width;
        this.height = height;
    }

    private void createWindow(int width, int height, String title) 
    {
        if(!glfwInit()) Insist.Assert(!true, "GLFW couldn't initialize!");

        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);

        window = glfwCreateWindow(width, height, title, 0, 0);

        glfwSetKeyCallback(window, KeyEvent::keyCallback);

        glfwSetMouseButtonCallback(window, MouseEvent::buttonCallback);
        glfwSetCursorPosCallback(window, MouseEvent::cursorCallback);
        glfwSetScrollCallback(window, MouseEvent::scrollCallback);

        glfwSetWindowSizeCallback(window, (window, newWidth, newHeight) -> {
            this.width = newWidth;
            this.height = newHeight;

            ApplicationEvent.sizeCallback(window, newWidth, newHeight);
        });

        glfwSetWindowCloseCallback(window, ApplicationEvent::closeCallback);

        GLFWVidMode vidMode = glfwGetVideoMode(glfwGetPrimaryMonitor());

        glfwSetWindowPos(window, vidMode.width() / 2 - width / 2, vidMode.height() / 2 - height / 2);

        glfwSwapInterval(vsync);

        glfwMakeContextCurrent(window);
        GL.createCapabilities();

        glfwShowWindow(window);
    }

    public void update() 
    {
        glfwSwapBuffers(window);
        glfwPollEvents();
    }

    public void destroy() 
    {
        glfwDestroyWindow(window);
        glfwTerminate();
    }

    public int getWidth() { return this.width; }
    public int getHeight() { return this.height; }
    public long getNativeWindow() { return window; }
}
