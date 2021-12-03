package com.hungnln.utils;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.Part;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Paths;


public class UploadFileUtils {
    public static String uploadFile(HttpServletRequest request) throws IOException, ServletException, URISyntaxException {
        File f = new File(UploadFileUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
        String projectName = "CafeShop";
        int index = f.toString().indexOf(projectName);
        String basePath = f.getAbsolutePath().substring(0, index + projectName.length()) + "\\src\\main\\webapp\\images\\";
        System.out.println("BasePath " + basePath);
        String fileName = "";
        try {
            Part filePart = request.getPart("file");
            fileName = (String) getFileName(filePart);
            File fileSaveDir = new File(basePath);
            if (!fileSaveDir.exists()) {
                fileSaveDir.mkdir();
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
                File outputFilePath = new File(basePath + fileName);
                inputStream = filePart.getInputStream();
                outputStream = new FileOutputStream(outputFilePath);
                int read = 0;
                final byte[] bytes = new byte[1024];
                while ((read = inputStream.read(bytes)) != -1) {
                    outputStream.write(bytes, 0, read);
                }
            } catch (Exception e) {
                e.printStackTrace();
                fileName = "";
            } finally {
                if (inputStream != null) {
                    inputStream.close();
                }
                if (outputStream != null) {
                    outputStream.close();
                }
            }

        } catch (Exception e) {
            fileName = "";
        }
        return fileName;
    }

    private static String getFileName(Part part) {
        for (String content : part.getHeader("content-disposition").split(";")) {
            if (content.trim().startsWith("filename")) {
                return content.substring(content.indexOf('=') + 1).trim().replace("\"", "");
            }
        }

        return null;
    }

}
