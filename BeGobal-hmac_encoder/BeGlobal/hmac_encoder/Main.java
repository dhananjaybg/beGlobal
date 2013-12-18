
// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 10/5/2012 2:34:40 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Main.java

package hmac_encoder;

import java.io.*;

// Referenced classes of package hmac_encoder:
//            LWAccess

public class Main
{

    public Main()
    {
    }

    private static String readFileAsString(String filePath)
        throws IOException
    {
        byte buffer[];
        BufferedInputStream f;
        buffer = new byte[(int)(new File(filePath)).length()];
        f = null;
        f = new BufferedInputStream(new FileInputStream(filePath));
        f.read(buffer);
        if(f != null)
            try
            {
                f.close();
            }
            catch(IOException ignored) { }
        break MISSING_BLOCK_LABEL_72;
        Exception exception;
        exception;
        if(f != null)
            try
            {
                f.close();
            }
            catch(IOException ignored) { }
        throw exception;
        return new String(buffer);
    }

    public static void main(String args[])
        throws IOException
    {
        LWAccess lwa = new LWAccess("https://api.sdlbeglobal.com/", "10591", "31efd0ea9174a353fd033319d0af8bc712116965");
        LWAccess _tmp = lwa;
        String rep = LWAccess.getUserInfo();
        System.out.println(rep);
        String inputXml_str = readFileAsString("C:\\Users\\dghevde\\Documents\\NetBeansProjects\\HMAC_Encoder\\src\\hmac_encoder\\DitaTopicSimple.xml");
        String rep3 = lwa.TranslateAsync("eng", "fra", "1263", "html", inputXml_str, true);
        System.out.println((new StringBuilder()).append("Translated Content:").append(rep3).toString());
    }

    public static void stringtofile(String FileName, String MyData)
    {
        try
        {
            BufferedWriter out = new BufferedWriter(new FileWriter(FileName));
            out.write(MyData);
            out.close();
        }
        catch(IOException e)
        {
            System.out.println("Exception ");
        }
    }
}
