package servlets;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.List;
import utils.Utils;
import org.json.JSONObject;
import org.json.JSONException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.fileupload.FileItem;

public class Images extends HttpServlet {
    /**
     * POST /v1/images
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            // Create a factory for disk-based file items
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            List<FileItem> items = upload.parseRequest(request);
            for(FileItem item : items){
                if(!item.isFormField()){
                    String name = new File(item.getName()).getName();
                    item.write( new File("/Users/itgo/Documents/java/apache-tomcat-9.0.41/webapps/sbs-impl/src/main/resources" + File.separator + name));
                }
            }
        } catch (FileUploadException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        response.setContentType("application/json;charset=UTF-8");
        JSONObject output = new JSONObject();
        JSONObject data = new JSONObject();
        data.put("imageurl", "xxx.jpg");
        output.put("code", 0);
        output.put("msg", "上传成功");
        output.put("data", data);
        PrintWriter out = response.getWriter();
        out.println(output);
    }

    /**
     * GET /v1/images/${iamgeName}
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        String imageName = pathParts[1];
        response.setContentType("image/png");
        InputStream fielStream = new FileInputStream(new File("/Users/itgo/Documents/java/apache-tomcat-9.0.41/webapps/sbs-impl/src/main/resources/"+imageName));
        int size = fielStream.available();
        byte data[] = new byte[size];   
        fielStream.read(data);
        fielStream.close();
        OutputStream os = response.getOutputStream();  
        os.write(data);  
        os.flush();  
        os.close();          
    }
    
}