package utils;

import org.json.JSONObject;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

public class Utils {
    final static String secretKey = "sbs-impl-cookie-secret-key";

    public static void buildResponse(HttpServletResponse response, Integer code, String msg, JSONObject data) throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        JSONObject output = new JSONObject();
        output.put("code", code);
        output.put("msg", msg);
        output.put("data", data);
        response.getWriter().println(output);
    }

    public static String createAndSetSessionCookie(HttpServletResponse response, JSONObject data) {
	    String originalString = data.toString();
        String encryptedString = AES.encrypt(originalString, secretKey) ;
        Cookie  mycookie = new Cookie("session-id", encryptedString);
        response.addCookie(mycookie);
	    // String decryptedString = AES.decrypt(encryptedString, secretKey) ;
        return encryptedString;
    }

    public static JSONObject parseSessionCookie(String sessionCookieStr) {
        String decryptedString = AES.decrypt(sessionCookieStr, secretKey) ;
        return new JSONObject(decryptedString);
    }
}