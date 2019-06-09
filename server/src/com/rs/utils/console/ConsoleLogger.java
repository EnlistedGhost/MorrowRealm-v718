package com.rs.utils.console;

import java.io.PrintStream;

import com.rs.utils.Utils;

public class ConsoleLogger extends PrintStream {
   
    public ConsoleLogger(PrintStream out) {
        super(out);
    }

    public final String FONT_COLOR = "black";
    
    @Override
    public void print(String str) {
        Console.getInstance().append(FONT_COLOR, "["+Utils.getDateTime()+"] "+str);
    }

    @Override
    public void print(boolean b) {
        Console.getInstance().append(FONT_COLOR, "["+Utils.getDateTime()+"] "+b);
    }

    @Override
    public void print(int i) {
        Console.getInstance().append(FONT_COLOR, "["+Utils.getDateTime()+"] "+i);
    }

    @Override
    public void print(long l) {
        Console.getInstance().append(FONT_COLOR, "["+Utils.getDateTime()+"] "+l);
    }
   
}