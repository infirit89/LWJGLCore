package com.okay.core;

public class Insist {

    public static void Assert(boolean condition, Object message) 
    {
        if(!condition) throw new AssertionError(message);
    }
    
}
