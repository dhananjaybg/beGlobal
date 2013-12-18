// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 10/5/2012 2:35:01 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   SimpleLog.java

package hmac_encoder;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SimpleLog
{

    private SimpleLog()
    {
    }

    public static void setLogFilename(String filename)
    {
        logFile = filename;
        (new File(filename)).delete();
        try
        {
            write((new StringBuilder()).append("LOG file : ").append(filename).toString());
        }
        catch(Exception e)
        {
            System.out.println(stack2string(e));
        }
    }

    public static void write(String msg)
    {
        write(logFile, msg);
    }

    public static void write(Exception e)
    {
        write(logFile, stack2string(e));
    }

    public static void write(String file, String msg)
    {
        try
        {
            Date now = new Date();
            String currentTime = df.format(now);
            FileWriter aWriter = new FileWriter(file, true);
            aWriter.write((new StringBuilder()).append(currentTime).append(" ").append(msg).append(System.getProperty("line.separator")).toString());
            System.out.println((new StringBuilder()).append(currentTime).append(" ").append(msg).toString());
            aWriter.flush();
            aWriter.close();
        }
        catch(Exception e)
        {
            System.out.println(stack2string(e));
        }
    }

    private static String stack2string(Exception e)
    {
        try
        {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            e.printStackTrace(pw);
            return (new StringBuilder()).append("------\r\n").append(sw.toString()).append("------\r\n").toString();
        }
        catch(Exception e2)
        {
            return "bad stack2string";
        }
    }

    private static String logFile = "/msglog.txt";
    private static final DateFormat df = new SimpleDateFormat("yyyy.MM.dd  hh:mm:ss ");

}
