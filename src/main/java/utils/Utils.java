package utils;

import org.json.JSONObject;
import javax.servlet.http.*;
import javax.servlet.*;
import java.io.*;

public class Utils {
    public static void buildResponse(HttpServletResponse response, Integer code, String msg, JSONObject data) throws IOException{
        response.setContentType("application/json;charset=UTF-8");
        JSONObject output = new JSONObject();
        output.put("code", code);
        output.put("msg", msg);
        output.put("data", data);
        response.getWriter().println(output);
    }
}