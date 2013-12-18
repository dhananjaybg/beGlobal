
// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 10/5/2012 2:33:40 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   Encoder.java

package hmac_encoder;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import org.apache.commons.codec.binary.Base64;

public class Encoder
{

    public Encoder()
    {
    }

    public static String sign(String source, String key)
    {
        if(key == null)
            throw new IllegalArgumentException("key cannot be null");
        Mac mac = null;
        try
        {
            mac = Mac.getInstance("HmacSHA1");
            mac.init(new SecretKeySpec(key.getBytes(), "HmacSHA1"));
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
        byte result[] = mac.doFinal(source.getBytes());
        String signature = new String(Base64.encodeBase64(result));
        return signature;
    }

    public static String encode(String source)
    {
        String encoded = new String(Base64.encodeBase64(source.getBytes()));
        return encoded;
    }

    private static final String SIGNING_ALGORITHM = "HmacSHA1";
}
