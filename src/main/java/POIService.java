package com.force.waveoc.service;

import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import com.force.waveoc.domain.ForecastInfo;

public interface POIService {
	
	// public ArrayList<ForecastInfo> parseExcelDocument(byte[] byteArray) throws Exception;
	public ArrayList<ForecastInfo> parseExcelDocument(InputStream is) throws Exception;
}
