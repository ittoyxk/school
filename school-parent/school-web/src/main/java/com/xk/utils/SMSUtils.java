package com.xk.utils;


import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Slf4j
public class SMSUtils {
    public static String serviceURL = "http://sdk2.entinfo.cn:8061/webservice.asmx";
    public static String sn = "SDK-BBX-010-25398";// 序列号
    public static String password = "9c0aeec@682";

    /*
     * 方法名称：getMD5
     * 功    能：字符串MD5加密
     * 参    数：待转换字符串
     * 返 回 值：加密之后字符串
     */
    public static String getMD5(String sourceStr) throws UnsupportedEncodingException
    {
        String resultStr = "";
        try {
            byte[] temp = sourceStr.getBytes();
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(temp);
            byte[] b = md5.digest();
            for (int i = 0; i < b.length; i++) {
                char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8',
                        '9', 'A', 'B', 'C', 'D', 'E', 'F'};
                char[] ob = new char[2];
                ob[0] = digit[(b[i] >>> 4) & 0X0F];
                ob[1] = digit[b[i] & 0X0F];
                resultStr += new String(ob);
            }
            return resultStr;
        } catch (NoSuchAlgorithmException e) {
            log.error("NoSuchAlgorithmException:{}", e);
            return null;
        }
    }


    /*
     * 方法名称：mdsmssend
     * 功    能：发送短信
     * 参    数：mobile,content,ext,stime,rrid,msgfmt(手机号，内容，扩展码，定时时间，唯一标识，内容编码)
     * 返 回 值：唯一标识，如果不填写rrid将返回系统生成的
     */
    public static String sendSMS(String mobile, String content, String ext, String stime, String rrid, String msgfmt) throws UnsupportedEncodingException
    {
        content= URLEncoder.encode(content,"UTF-8");
        String pwd = getMD5(sn + password);
        String result = "";
        String soapAction = "http://entinfo.cn/mdsmssend";
        String xml = "<?xml version=\"1.0\" encoding=\"utf-8\"?>";
        xml += "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">";
        xml += "<soap:Body>";
        xml += "<mdsmssend  xmlns=\"http://entinfo.cn/\">";
        xml += "<sn>" + sn + "</sn>";
        xml += "<pwd>" + pwd + "</pwd>";
        xml += "<mobile>" + mobile + "</mobile>";
        xml += "<content>" + content + "</content>";
        xml += "<ext>" + ext + "</ext>";
        xml += "<stime>" + stime + "</stime>";
        xml += "<rrid>" + rrid + "</rrid>";
        xml += "<msgfmt>" + msgfmt + "</msgfmt>";
        xml += "</mdsmssend>";
        xml += "</soap:Body>";
        xml += "</soap:Envelope>";

        URL url;
        try {
            url = new URL(serviceURL);

            URLConnection connection = url.openConnection();
            HttpURLConnection httpconn = (HttpURLConnection) connection;
            ByteArrayOutputStream bout = new ByteArrayOutputStream();
            bout.write(xml.getBytes());
            byte[] b = bout.toByteArray();
            httpconn.setRequestProperty("Content-Length", String.valueOf(b.length));
            httpconn.setRequestProperty("Content-Type", "text/xml; charset=gb2312");
            httpconn.setRequestProperty("SOAPAction", soapAction);
            httpconn.setRequestMethod("POST");
            httpconn.setDoInput(true);
            httpconn.setDoOutput(true);

            OutputStream out = httpconn.getOutputStream();
            out.write(b);
            out.close();

            InputStreamReader isr = new InputStreamReader(httpconn.getInputStream());
            BufferedReader in = new BufferedReader(isr);
            String inputLine;
            while (null != (inputLine = in.readLine())) {
                Pattern pattern = Pattern.compile("<mdsmssendResult>(.*)</mdsmssendResult>");
                Matcher matcher = pattern.matcher(inputLine);
                while (matcher.find()) {
                    result = matcher.group(1);
                }
            }
            log.info("ss:{}",result);
            return result;
        } catch (Exception e) {
            log.error("Exception:{}", e);
            return "";
        }
    }


}
