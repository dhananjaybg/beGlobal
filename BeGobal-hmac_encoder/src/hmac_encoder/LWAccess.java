package hmac_encoder;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.codec.binary.Base64;
import hmac_encoder.SimpleLog.*;

public class LWAccess
{
protected static String m_szHostName;
protected static String m_szUserID;
protected static String m_szAPIKey;

public LWAccess(String szHostName, String szUserID, String szAPIKey)
{
	m_szHostName = szHostName.trim();
	m_szUserID = szUserID.trim();
	m_szAPIKey = szAPIKey.trim();
}

public static String BA(String str)
{
    return "All is well indeed" + str;
}

public static String TransFrench2English(String inputXml_str)
throws IOException
{
System.out.println("DHANANJAY_GHEVDE_FRENCH_PASSING_START");
System.out.println(inputXml_str);
System.out.println("DHANANJAY_GHEVDE_FRENCH_PASSING_END");

LWAccess lwa = new LWAccess("https://api.sdlbeglobal.com/", "10591", "31efd0ea9174a353fd033319d0af8bc712116965");

String rep3 = lwa.TranslateAsync("fra", "eng", "62", "xml", inputXml_str, false);

//rep3 = "DhananjayGhevde";

return rep3;
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



String szMessage = szHttpRequestType.trim() + "\n" + szDate.trim() + "\n" + szURI.trim();
String szSignature = Encoder.sign(szMessage, m_szAPIKey);

try
{
String sURL = loadUrl;
URL url = new URL(sURL);
URLConnection conn = url.openConnection();
conn.setRequestProperty("LW-Date", szDate);
conn.setRequestProperty("Authorization", "LWA:" + m_szUserID + ":" + szSignature);
conn.setDoOutput(true);

BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

while ((response = in.readLine()) != null) {}



in.close();
}
catch (MalformedURLException ex) {
System.out.println(ex.getMessage());
return ex.getMessage();
}
catch (IOException ex) {
System.out.println(ex.getMessage());
return ex.getMessage();
}
catch (Exception ex) {
System.out.println(ex.getMessage());
return ex.getMessage();
}

return response;
}


public static String getlpInfo(String src_lang, String target_lang, String szLPID)
{
String szURI = "/v2/lpinfo/" + src_lang + "." + target_lang + "/lpid=" + szLPID + "/";
String loadUrl = m_szHostName + szURI;
String szDate = GetCurrentDateGMT();
String szHttpRequestType = "GET";
String response = "";



String szMessage = szHttpRequestType.trim() + "\n" + szDate.trim() + "\n" + szURI.trim();
String szSignature = Encoder.sign(szMessage, m_szAPIKey);



try
{
String sURL = loadUrl;
URL url = new URL(sURL);
URLConnection conn = url.openConnection();
conn.setRequestProperty("LW-Date", szDate);
conn.setRequestProperty("Authorization", "LWA:" + m_szUserID + ":" + szSignature);
conn.setDoOutput(true);

BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));

while ((response = in.readLine()) != null) {}



in.close();
}
catch (MalformedURLException ex) {
System.out.println(ex.getMessage());
return ex.getMessage();
}
catch (IOException ex) {
System.out.println(ex.getMessage());
return ex.getMessage();
}
catch (Exception ex) {
System.out.println(ex.getMessage());
return ex.getMessage();
}

return response;
}





public String Translate(String szSrcLang, String szTgtLang, String szLPID, String szInputFormat, String szSourceText, boolean qualityScore)
{
HttpURLConnection connection = null;

try
{
String szPath = "/v2/";



if (qualityScore) {
szPath = szPath + "quality-translation/";
} else {
szPath = szPath + "translation/";
}
szPath = szPath + szSrcLang + "." + szTgtLang + "/";

if (0 != szLPID.length())
szPath = szPath + "lpid=" + szLPID + "/";

if (0 != szInputFormat.length()) {
szPath = szPath + "input_format=" + szInputFormat + "/";
}



String loadUrl = m_szHostName + szPath;
String szDate = GetCurrentDateGMT();
String szHttpRequestType = "POST";


String szMessage = szHttpRequestType.trim() + "\n" + szDate.trim() + "\n" + szPath.trim();
String szSignature = Encoder.sign(szMessage, m_szAPIKey);


szSourceText = "source_text=" + szSourceText;

URL url = new URL(loadUrl);
connection = (HttpURLConnection)url.openConnection();
connection.setRequestMethod("POST");
connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
connection.setRequestProperty("Content-Length", Integer.toString(szSourceText.getBytes().length));
connection.setRequestProperty("LW-Date", szDate);
connection.setRequestProperty("Authorization", "LWA:" + m_szUserID + ":" + szSignature);


connection.setDoInput(true);
connection.setDoOutput(true);


DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
wr.writeBytes(szSourceText);
wr.flush();
wr.close();


InputStream is = connection.getInputStream();
BufferedReader rd = new BufferedReader(new InputStreamReader(is));

StringBuffer response = new StringBuffer();
String line;
while ((line = rd.readLine()) != null) {
response.append(line);
response.append((char)'\r');
}

rd.close();
return response.toString();

}
catch (MalformedURLException ex)
{

System.out.println(ex.getMessage());
return ex.getMessage();
}
catch (IOException ex)
{
System.out.println(ex.getMessage());
return ex.getMessage();
}
catch (Exception ex) {
String loadUrl;
System.out.println(ex.getMessage());
return ex.getMessage();

}
finally
{
if (connection != null) {
connection.disconnect();
}
}
}






public String TranslateAsync(String szSrcLang, String szTgtLang, String szLPID, String szInputFormat, String szSourceText, boolean qualityScore)
{
HttpURLConnection connection = null;
String szTargetText = "";

try
{
String szPath = "/v2/";

if (qualityScore) {

    szPath = szPath + "quality-translation-async/";

} else {
szPath = szPath + "translation-async/";
}
szPath = szPath + szSrcLang + "." + szTgtLang + "/";

//szPath = szPath + szSrcLang + "." + szTgtLang + "/";
if (0 != szLPID.length())
{
    szPath = szPath + "lpid=" + szLPID + "/";
}

if(0 != szInputFormat.length()) {    
    
    szPath = szPath + "input_format=" + szInputFormat + "/";

}

System.out.println("DHANANJAY_TWO");

String loadUrl = m_szHostName + szPath;
String szDate = GetCurrentDateGMT();
String szHttpRequestType = "POST";


String szMessage = szHttpRequestType.trim() + "\n" + szDate.trim() + "\n" + szPath.trim();
String szSignature = Encoder.sign(szMessage, m_szAPIKey);


szSourceText = "source_text=" + szSourceText;

URL url = new URL(loadUrl);
connection = (HttpURLConnection)url.openConnection();
connection.setRequestMethod("POST");

connection.setRequestProperty("Content-Length", Integer.toString(szSourceText.getBytes().length));
connection.setRequestProperty("LW-Date", szDate);
connection.setRequestProperty("Authorization", "LWA:" + m_szUserID + ":" + szSignature);


connection.setDoInput(true);
connection.setDoOutput(true);


DataOutputStream wr = new DataOutputStream(connection.getOutputStream());
wr.writeBytes(szSourceText);
wr.flush();
wr.close();

System.out.println("DHANANJAY_THREE");    

InputStream is = connection.getInputStream();
BufferedReader rd = new BufferedReader(new InputStreamReader(is));

StringBuffer response = new StringBuffer();
String line;
while ((line = rd.readLine()) != null) {
response.append(line);
response.append((char)'\r');
}

rd.close();

String szRetrievalURL = ExtractTag(response.toString(), "retrieval_url");
szPath = "/" + szRetrievalURL.replace(m_szHostName, "");


for (;;)
{
String sURL = m_szHostName + szPath;

String response2 = "";
String szDate2 = GetCurrentDateGMT();
String szHttpRequestType2 = "GET";

String szMessage2 = szHttpRequestType2.trim() + "\n" + szDate2.trim() + "\n" + szPath.trim();
String szSignature2 = Encoder.sign(szMessage2, m_szAPIKey);


try
{
URL url_inner = new URL(sURL);
URLConnection conn2 = url_inner.openConnection();
conn2.setRequestProperty("LW-Date", szDate2);
conn2.setRequestProperty("Authorization", "LWA:" + m_szUserID + ":" + szSignature2);
conn2.setDoOutput(true);

BufferedReader in2 = new BufferedReader(new InputStreamReader(conn2.getInputStream(), "UTF-8"));
String xml_response = "";

while ((response2 = in2.readLine()) != null) {
System.out.println(response2);
xml_response = xml_response + response2;
}
in2.close();

System.out.println("DHANANJAY_FOUR_BEFORE_EXtracting_start_Tag");   

String szState = ExtractTag(xml_response, "state");

System.out.println("DHANANJAY_FIVE_Post_EXtracting_start_Tag");   

if (szState.equalsIgnoreCase("DONE"))
{


if (qualityScore) {
String szScore = ExtractTag(xml_response, "score");

szTargetText = UpackCData(ExtractTag(xml_response, "target"), szScore);

}else{
    
    System.out.println("DHANANJAY_QUALITY_SCORE_WAS_FALSE");
    
    szTargetText = UpackCData(ExtractTag(xml_response, "translation"), "2.7");
    
    System.out.println();
}


break;
}
if (szState.equalsIgnoreCase("failed"))
{



szTargetText = xml_response;
break;
}

}
catch (MalformedURLException ex)
{
System.out.println(ex.getMessage());
return ex.getMessage();
}
catch (IOException ex) {
System.out.println(ex.getMessage());
return ex.getMessage();
}
catch (Exception ex) {
//System.out.println(ex.getMessage());
//return ex.getMessage();
 return "falseX"; 
}

}


}
catch (Exception e)
{

    System.out.println(e.getMessage());

    return e.getMessage();
}
return szTargetText;
}


    public static String ExtractTag(String frag, String elem)
    {
    int begin = frag.toString().indexOf("<" + elem + ">");
    int end = frag.toString().indexOf("</" + elem + ">");
    String szRetrieval = frag.toString().substring(begin + elem.length() + 2, end);
    return szRetrieval;
    }


    public static String UpackCData(String frag, String score)
    {
    String szRetrieval = frag.toString().substring(9, frag.toString().length() - 3);

    szRetrieval = szRetrieval.replaceAll("<DOCUMENT", "<DOCUMENT trust_score='" + score + "' ");
    return szRetrieval;
    }
}
