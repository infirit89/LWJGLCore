package com.okay.core;

public class Debug {

    private static final String RESET = "\u001B[0m";
    private static final String RED = "\u001B[31m";
    private static final String GREEN = "\u001B[32m";
    private static final String YELLOW = "\u001B[33m";

    public static void LogInfo(Object message) { System.out.println(message); }

    public static void Log(Object message) {  System.out.println( GREEN + message + RESET); }

    public static void LogError(Object message) { System.out.println(RED + message + RESET); }

    public static void LogWarning(Object message) { System.out.println(YELLOW + message + RESET); }
    
}
