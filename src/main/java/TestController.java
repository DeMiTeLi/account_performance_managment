package main.java;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by ramansilin on 10/29/15.
 */
@Controller
@RequestMapping(value = "/")
public class TestController extends HttpServlet {

    @Autowired
    private ApplicationContext appContext;

    @RequestMapping(value = "/testpost", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> printHelloPost() {
//        return "Hello GET, World!";
        return new ResponseEntity<String>("{\"message\": \"Hello, Post.\"}", getDefaultResponseHeaders(),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/testpost", method = RequestMethod.OPTIONS)
    @ResponseBody
    public ResponseEntity<String> getAccess() {
//        return "Hello GET, World!";
        return new ResponseEntity<String>("{\"message\": \"Hello, Options.\"}", getDefaultResponseHeaders(),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/testget", method = RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<String> printHelloGet() {
//        return "Hello GET, World!";
        return new ResponseEntity<String>("{\"message\": \"Hello, Get.\"}", getDefaultResponseHeaders(),
                HttpStatus.OK);
    }

    protected HttpHeaders getDefaultResponseHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
//        headers.add("Access-Control-Allow-Origin", "*");
        return headers;
    }

    @Override
    public void init(ServletConfig context) throws ServletException {
        super.init(context);
    }

    @RequestMapping(value = "/fileupload", method = RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<String> fileUpload(HttpServletRequest request, HttpServletResponse response){
        List<FileItem> items = new ArrayList<FileItem>();
        try {

            //  factory for disk-based file items   This tells ServletFileUpload how to store the files.
            // by default, if file is larger than 10KB, this will create a temporary file on disk
            // this temp file will be located, by default in the directory defined by the system property java.io.tmpdir
            FileItemFactory factory = new DiskFileItemFactory();

            // ServletFileUpload handles the file uploads in the request:
            ServletFileUpload upload = new ServletFileUpload(factory);

            // the list "items" is contains every element in the form that was submitted.
            // Parse the request
            items = upload.parseRequest(request);


            // Process the uploaded items
            Iterator<FileItem> iter = items.iterator();
            while (iter.hasNext()) {
                FileItem item = iter.next();
                System.out.println("::: blia :::" + item.getName());
            }





//            // Create a factory for disk-based file items
//            DiskFileItemFactory factory = new DiskFileItemFactory();
//
//            // Configure a repository (to ensure a secure temp location is used)
//            ServletContext servletContext = this.getServletConfig().getServletContext();
//            File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
//            factory.setRepository(repository);
//
//            // Create a new file upload handler
//            ServletFileUpload upload = new ServletFileUpload(factory);
//
//            // Parse the request
//            List<FileItem> items = upload.parseRequest(request);
//
//
//            // Process the uploaded items
//            Iterator<FileItem> iter = items.iterator();
//            while (iter.hasNext()) {
//                FileItem item = iter.next();
//                System.out.println("::: blia :::" + item.getName());
//            }



//        System.out.println(":::: getQueryString()  :::" + request.getQueryString());
//        System.out.println(":::: getParameter(dateformat)  :::" + request.getParameter("dateformat"));
//        System.out.println(":::: getParameterMap()  :::" + request.getParameterMap());
//        System.out.println(":::: getHeader()  :::" + request.getHeader("dateformat"));
//        System.out.println(":::: getContentType()  :::" + request.getContentType());
//        System.out.println(":::: getInputStream()  :::" + request.getInputStream());
//        System.out.println(":::: getHeaderNames()  :::" + request.getHeaderNames().toString());
//        System.out.println(":::: getHeaderNames()  :::" + dateformat);
//        System.out.println(":::: getHeaderNames()  :::");
//
//        CommonsMultipartResolver resolver = (CommonsMultipartResolver) appContext.getBean("multipartResolver");
//
//        MultipartHttpServletRequest multiRequest = resolver.resolveMultipart(request);
//
//        System.out.println(":::: getFileMap()  :::" + multiRequest.getFileMap());
//        System.out.println(":::: getHeaderNames()  :::" + multiRequest.getHeaderNames());
//        System.out.println(":::: getRequestHeaders()  :::" + multiRequest.getRequestHeaders());
//        System.out.println(":::: getMultipartHeaders()  :::" + multiRequest.getMultipartHeaders("Test Excel File"));

//        String requestHeader = requestEntity.getHeaders().getFirst("dateformat");
//        System.out.println("::: requestHeader" + requestHeader);
//
//        byte[] requestBody = requestEntity.getBody();
//        System.out.println("::: bytest in file ::: " + requestBody.length);

//        System.out.println("::: getFileMap().keySet ::::" + request.getFileMap().keySet());
//        System.out.println("::: getFileMap() ::::" + request.getFileMap());
//        System.out.println("::: getMultiFileMap().keySet ::::" + request.getMultiFileMap().keySet());
//
//        Iterator<String> itr =  request.getFileNames();
//
//
//        MultipartFile mpf = request.getFile(itr.next());
//        System.out.println(":::" + mpf.getOriginalFilename() +" uploaded!");

        } catch (Exception e){
            e.printStackTrace();
            System.out.println("::: e ::: " + e.getMessage());
        }

        return new ResponseEntity<String>("{\"message\": \"response:\" List.size() = "+ items.size() + "}", getDefaultResponseHeaders(),
                HttpStatus.OK);

    }



}
