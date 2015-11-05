package com.force.waveoc.controller;

import com.force.waveoc.service.POIServiceImpl;
import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServlet;

/**
 * Created by ramansilin on 10/29/15.
 */
@Controller
public class ParseController extends HttpServlet {

    private static final Logger log = Logger.getLogger(ParseController.class);

    @RequestMapping( value = "/upload_file", method = RequestMethod.POST)
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile multipartFile, @RequestParam("dateformat") String dateformat) throws Exception {
        log.info("::: dateformat :::" + dateformat);
        log.info("::: multipartFile :::" + multipartFile);
        log.info("::: multipartFile Name :::" + multipartFile.getOriginalFilename());
        log.info("::: multipartFile getContentType() :::" + multipartFile.getContentType());

        JSONArray jsonArrayToReturn = new JSONArray();

        try {
            POIServiceImpl servise = new POIServiceImpl();
            jsonArrayToReturn = servise.parseExcelDocument(multipartFile.getInputStream(), multipartFile.getOriginalFilename());

        } catch(Exception e){
            log.info("Exception thrown in file process time");
            e.getMessage();
            e.printStackTrace();
        }
        return new ResponseEntity<String>(jsonArrayToReturn.toString(), getDefaultResponseHeaders(),HttpStatus.OK);
    }

    protected HttpHeaders getDefaultResponseHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        return headers;
    }
}
