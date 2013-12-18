
// Decompiled by DJ v3.12.12.96 Copyright 2011 Atanas Neshkov  Date: 10/5/2012 2:31:08 PM
// Home Page: http://members.fortunecity.com/neshkov/dj.html  http://www.neshkov.com/dj.html - Check often for new version!
// Decompiler options: packimports(3) 
// Source File Name:   LWAccess.java

package hmac_encoder;

import java.io.*;
import java.net.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

// Referenced classes of package hmac_encoder:
//            Encoder

public class LWAccess
{

    public LWAccess(String szHostName, String szUserID, String szAPIKey)
    {
        m_szHostName = szHostName.trim();
        m_szUserID = szUserID.trim();
        m_szAPIKey = szAPIKey.trim();
    }

    public static String BA(String str)
    {
        return (new StringBuilder()).append("All is well").append(str).toString();
    }

    public static String TransEnglish2French(String inputXml_str)
        throws IOException
    {
        LWAccess lwa = new LWAccess("https://api.sdlbeglobal.com/", "10591", "31efd0ea9174a353fd033319d0af8bc712116965");
        String rep3 = lwa.TranslateAsync("eng", "fra", "1263", "xml", inputXml_str, true);
        return rep3;
    }

    public static String TransEnglish2Frenchhtml(String inputXml_str)
    {
        LWAccess lwa = new LWAccess("https://api.sdlbeglobal.com/", "10591", "31efd0ea9174a353fd033319d0af8bc712116965");
        String rep3 = lwa.TranslateAsync("eng", "fra", "1263", "html", inputXml_str, true);
        return rep3;
    }

    public static String TransEnglish2German(String inputXml_str)
    {
        LWAccess lwa = new LWAccess("https://api.sdlbeglobal.com/", "10591", "31efd0ea9174a353fd033319d0af8bc712116965");
        String rep3 = lwa.TranslateAsync("eng", "ger", "1283", "xml", inputXml_str, true);
        return rep3;
    }

    public static String TransEnglish2Chinese(String inputXml_str)
    {
        LWAccess lwa = new LWAccess("https://api.sdlbeglobal.com/", "10591", "31efd0ea9174a353fd033319d0af8bc712116965");
        String rep3 = lwa.TranslateAsync("eng", "chi", "1265", "xml", inputXml_str, true);
        return rep3;
    }

    public static String GetCurrentDateGMT()
    {
        Locale LOCALE_US = Locale.US;
        String RFC1123_PATTERN = "EEE, dd MMM yyyyy HH:mm:ss z";
        TimeZone tz = TimeZone.getTimeZone("GMT");
        DateFormat rfc1123Format = new SimpleDateFormat(RFC1123_PATTERN, LOCALE_US);
        rfc1123Format.setTimeZone(tz);
        String tareekh = rfc1123Format.format(new Date());
        return tareekh;
    }

    public static String getUserInfo()
    {
        String loadUrl = "https://api.sdlbeglobal.com/v2/user/";
        String szURI = "/v2/user/";
        String szDate = GetCurrentDateGMT();
        String szHttpRequestType = "GET";
        String response = "";
        String szMessage = (new StringBuilder()).append(szHttpRequestType.trim()).append("\n").append(szDate.trim()).append("\n").append(szURI.trim()).toString();
        String szSignature = Encoder.sign(szMessage, m_szAPIKey);
        try
        {
            String sURL = loadUrl;
            URL url = new URL(sURL);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("LW-Date", szDate);
            conn.setRequestProperty("Authorization", (new StringBuilder()).append("LWA:").append(m_szUserID).append(":").append(szSignature).toString());
            conn.setDoOutput(true);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while((response = in.readLine()) != null) ;
            in.close();
        }
        catch(MalformedURLException ex)
        {
            System.out.println(ex.getMessage());
            return ex.getMessage();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
            return ex.getMessage();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return ex.getMessage();
        }
        return response;
    }

    public static String getlpInfo(String src_lang, String target_lang, String szLPID)
    {
        String szURI = (new StringBuilder()).append("/v2/lpinfo/").append(src_lang).append(".").append(target_lang).append("/lpid=").append(szLPID).append("/").toString();
        String loadUrl = (new StringBuilder()).append(m_szHostName).append(szURI).toString();
        String szDate = GetCurrentDateGMT();
        String szHttpRequestType = "GET";
        String response = "";
        String szMessage = (new StringBuilder()).append(szHttpRequestType.trim()).append("\n").append(szDate.trim()).append("\n").append(szURI.trim()).toString();
        String szSignature = Encoder.sign(szMessage, m_szAPIKey);
        try
        {
            String sURL = loadUrl;
            URL url = new URL(sURL);
            URLConnection conn = url.openConnection();
            conn.setRequestProperty("LW-Date", szDate);
            conn.setRequestProperty("Authorization", (new StringBuilder()).append("LWA:").append(m_szUserID).append(":").append(szSignature).toString());
            conn.setDoOutput(true);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
            while((response = in.readLine()) != null) ;
            in.close();
        }
        catch(MalformedURLException ex)
        {
            System.out.println(ex.getMessage());
            return ex.getMessage();
        }
        catch(IOException ex)
        {
            System.out.println(ex.getMessage());
            return ex.getMessage();
        }
        catch(Exception ex)
        {
            System.out.println(ex.getMessage());
            return ex.getMessage();
        }
        return response;
    }

    public String Translate(String szSrcLang, String szTgtLang, String szLPID, String szInputFormat, String szSourceText, boolean qualityScore)
    {
        HttpURLConnection connection = null;
        String s1;
        String szPath = "/v2/";
        if(qualityScore)
            szPath = (new StringBuilder()).append(szPath).append("quality-translation/").toString();
        else
            szPath = (new StringBuilder()).append(szPath).append("translation/").toString();
        szPath = (new StringBuilder()).append(szPath).append(szSrcLang).append(".").append(szTgtLang).append("/").toString();
        if(0 != szLPID.length())
            szPath = (new StringBuilder()).append(szPath).append("lpid=").append(szLPID).append("/").toString();
        if(0 != szInputFormat.length())
            szPath = (new StringBuilder()).append(szPath).append("input_format=").append(szInputFormat).append("/").toString();
        String loadUrl = (new StringBuilder()).append(m_szHostName).append(szPath).toString();
        String szDate = GetCurrentDateGMT();
        String szHttpRequestType = "POST";
        String szMessage = (new StringBuilder()).append(szHttpRequestType.trim()).append("\n").append(szDate.trim()).append("\n").append(szPath.trim()).toString();
        String szSignature = Encoder.sign(szMessage, m_szAPIKey);
        szSourceText = (new StringBuilder()).append("source_text=").append(szSourceText).toString();
        URL url = new URL(loadUrl);
        connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
        connection.setRequestProperty("Content-Length", Integer.toString(szSourceText.getBytes().length));
        connection.setRequestProperty("LW-Date", szDate);
        connection.setRequestProperty("Authorization", (new StringBuilder()).append("LWA:").append(m_szUserID).append(":").append(szSignature).toString());
        connection.setDoInput(true);
        connection.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(szSourceText);
        wr.flush();
        wr.close();
        java.io.InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuffer response = new StringBuffer();
        String line;
        while((line = rd.readLine()) != null) 
        {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        s1 = response.toString();
        if(connection != null)
            connection.disconnect();
        return s1;
        MalformedURLException ex;
        ex;
        String s;
        System.out.println(ex.getMessage());
        s = ex.getMessage();
        if(connection != null)
            connection.disconnect();
        return s;
        ex;
        System.out.println(ex.getMessage());
        s = ex.getMessage();
        if(connection != null)
            connection.disconnect();
        return s;
        ex;
        System.out.println(ex.getMessage());
        s = ex.getMessage();
        if(connection != null)
            connection.disconnect();
        return s;
        Exception exception;
        exception;
        if(connection != null)
            connection.disconnect();
        throw exception;
    }

    public String TranslateAsync(String szSrcLang, String szTgtLang, String szLPID, String szInputFormat, String szSourceText, boolean qualityScore)
    {
        String szTargetText;
        HttpURLConnection connection = null;
        szTargetText = "";
        String szPath;
        szPath = "/v2/";
        if(qualityScore)
            szPath = (new StringBuilder()).append(szPath).append("quality-translation-async/").toString();
        else
            szPath = (new StringBuilder()).append(szPath).append("translation-async/").toString();
        szPath = (new StringBuilder()).append(szPath).append(szSrcLang).append(".").append(szTgtLang).append("/").toString();
        szPath = (new StringBuilder()).append(szPath).append(szSrcLang).append(".").append(szTgtLang).append("/").toString();
        if(0 != szLPID.length())
            szPath = (new StringBuilder()).append(szPath).append("lpid=").append(szLPID).append("/").toString();
        if(0 != szInputFormat.length())
            szPath = (new StringBuilder()).append(szPath).append("input_format=").append(szInputFormat).append("/").toString();
        String loadUrl = (new StringBuilder()).append(m_szHostName).append(szPath).toString();
        String szDate = GetCurrentDateGMT();
        String szHttpRequestType = "POST";
        String szMessage = (new StringBuilder()).append(szHttpRequestType.trim()).append("\n").append(szDate.trim()).append("\n").append(szPath.trim()).toString();
        String szSignature = Encoder.sign(szMessage, m_szAPIKey);
        szSourceText = (new StringBuilder()).append("source_text=").append(szSourceText).toString();
        URL url = new URL(loadUrl);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("POST");
        connection.setRequestProperty("Content-Length", Integer.toString(szSourceText.getBytes().length));
        connection.setRequestProperty("LW-Date", szDate);
        connection.setRequestProperty("Authorization", (new StringBuilder()).append("LWA:").append(m_szUserID).append(":").append(szSignature).toString());
        connection.setDoInput(true);
        connection.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
        wr.writeBytes(szSourceText);
        wr.flush();
        wr.close();
        java.io.InputStream is = connection.getInputStream();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        StringBuffer response = new StringBuffer();
        String line;
        while((line = rd.readLine()) != null) 
        {
            response.append(line);
            response.append('\r');
        }
        rd.close();
        String szRetrievalURL = ExtractTag(response.toString(), "retrieval_url");
        szPath = (new StringBuilder()).append("/").append(szRetrievalURL.replace(m_szHostName, "")).toString();
_L2:
        String sURL;
        String szDate2;
        String szSignature2;
        sURL = (new StringBuilder()).append(m_szHostName).append(szPath).toString();
        String response2 = "";
        szDate2 = GetCurrentDateGMT();
        String szHttpRequestType2 = "GET";
        String szMessage2 = (new StringBuilder()).append(szHttpRequestType2.trim()).append("\n").append(szDate2.trim()).append("\n").append(szPath.trim()).toString();
        szSignature2 = Encoder.sign(szMessage2, m_szAPIKey);
        String xml_response;
        String szState;
        URL url_inner = new URL(sURL);
        URLConnection conn2 = url_inner.openConnection();
        conn2.setRequestProperty("LW-Date", szDate2);
        conn2.setRequestProperty("Authorization", (new StringBuilder()).append("LWA:").append(m_szUserID).append(":").append(szSignature2).toString());
        conn2.setDoOutput(true);
        BufferedReader in2 = new BufferedReader(new InputStreamReader(conn2.getInputStream(), "UTF-8"));
        String response2;
        for(xml_response = ""; (response2 = in2.readLine()) != null; xml_response = (new StringBuilder()).append(xml_response).append(response2).toString())
            System.out.println(response2);

        in2.close();
        szState = ExtractTag(xml_response, "state");
        if(szState.equalsIgnoreCase("DONE"))
        {
            String szScore = ExtractTag(xml_response, "score");
            szTargetText = UpackCData(ExtractTag(xml_response, "target"), szScore);
            break MISSING_BLOCK_LABEL_940;
        }
        if(!szState.equalsIgnoreCase("failed")) goto _L2; else goto _L1
_L1:
        szTargetText = xml_response;
        break MISSING_BLOCK_LABEL_940;
        MalformedURLException ex;
        ex;
        System.out.println(ex.getMessage());
        return ex.getMessage();
        ex;
        System.out.println(ex.getMessage());
        return ex.getMessage();
        ex;
        System.out.println(ex.getMessage());
        return ex.getMessage();
        Exception e;
        e;
        return "false";
        return szTargetText;
    }

    public static String ExtractTag(String frag, String elem)
    {
        int begin = frag.toString().indexOf((new StringBuilder()).append("<").append(elem).append(">").toString());
        int end = frag.toString().indexOf((new StringBuilder()).append("</").append(elem).append(">").toString());
        String szRetrieval = frag.toString().substring(begin + elem.length() + 2, end);
        return szRetrieval;
    }

    public static String UpackCData(String frag, String score)
    {
        String szRetrieval = frag.toString().substring(9, frag.toString().length() - 3);
        szRetrieval = szRetrieval.replaceAll("<DOCUMENT", (new StringBuilder()).append("<DOCUMENT trust_score='").append(score).append("' ").toString());
        return szRetrieval;
    }

    protected static String m_szHostName;
    protected static String m_szUserID;
    protected static String m_szAPIKey;
}
