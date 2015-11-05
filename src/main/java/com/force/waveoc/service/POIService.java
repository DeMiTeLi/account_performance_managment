package com.force.waveoc.service;

import org.json.JSONArray;
import java.io.InputStream;

public interface POIService {

    JSONArray parseExcelDocument(InputStream inputStream, String fileName) throws Exception;
}
