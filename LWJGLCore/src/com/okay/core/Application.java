package com.okay.core;

import com.okay.events.ApplicationEvent;
import com.okay.window.Window;

import org.lwjgl.glfw.GLFW;

public class Application {

    private boolean running = true;
    private Window window;
    private float prevTime = 0.0f;

    public Application(int width, int height, String title) 
    {
        window = new Window(width, height, title);
        Input.setWindow(window.getNativeWindow());
    }

    public void run() 
    {
        start();
        while(running) 
        {  
            float time = (float)GLFW.glfwGetTime();
            Time timer = new Time(time - prevTime);
            prevTime = time;

            update(timer);
            window.update();
            
            running = !ApplicationEvent.hasClosed();
        }
        close();
    }

    protected void start() {}

    protected void update(Time time) {}

    protected void close() { window.destroy(); }
}
