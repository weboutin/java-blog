package servlets;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.json.JSONObject;

import utils.Utils;

public class ImagesServlet extends HttpServlet {
    /**
     * POST /v1/images
     */
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String filename = null;
        try {
            DiskFileItemFactory factory = new DiskFileItemFactory();
            ServletFileUpload upload = new ServletFileUpload(factory);

            String rootDir = request.getServletContext().getRealPath("./");

            List<FileItem> items = upload.parseRequest(request);
            for (FileItem item : items) {
                if (!item.isFormField()) {
                    filename = new File(item.getName()).getName();
                    item.write(new File(rootDir + "uploads" + File.separator + filename));
                }
            }
            response.setContentType("application/json;charset=UTF-8");
            JSONObject data = new JSONObject();
            data.put("imageurl", "/v1/images/" + filename);
            Utils.buildResponse(response, 0, "上传成功", data);
        } catch (FileUploadException e) {
            e.printStackTrace();
            JSONObject data = new JSONObject();
            Utils.buildResponse(response, -1, "系统错误", data);
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject data = new JSONObject();
            Utils.buildResponse(response, -1, "系统错误", data);
        }
    }

    /**
     * GET /v1/images/${iamgeName}
     */
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String pathInfo = request.getPathInfo();
            String[] pathParts = pathInfo.split("/");
            String imageName = pathParts[1];
            response.setContentType("image/png");
            String rootDir = request.getServletContext().getRealPath("./");
            InputStream fielStream = new FileInputStream(new File(rootDir + "uploads/" + imageName));
            int size = fielStream.available();
            byte data[] = new byte[size];
            fielStream.read(data);
            fielStream.close();
            OutputStream os = response.getOutputStream();
            os.write(data);
            os.flush();
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
            JSONObject data = new JSONObject();
            Utils.buildResponse(response, -1, "系统异常", data);
        }

    }

}