package com.force.waveoc.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

@Service
public class POIServiceImpl implements POIService {
			
	public JSONArray parseExcelDocument(InputStream inputStream, String fileName ) throws Exception {
		
        Workbook wb;
        if(fileName.contains("xlsx")){
            wb = new XSSFWorkbook(inputStream);

        } else {
            wb = new HSSFWorkbook(inputStream);
        }

		Sheet sheet = wb.getSheetAt(0);
        Row row;
        Cell cell;
 
		Iterator<Row> rows = sheet.rowIterator();

        List<String> headersList = new ArrayList<String>();

        JSONArray jsonArray = new JSONArray();
		while (rows.hasNext()) {
            JSONObject jsonObject = new JSONObject();
			row = rows.next();
			Iterator<Cell> cells = row.cellIterator();
			
			while (cells.hasNext()) {
                cell = cells.next();

                if(cell.getRowIndex() == 0){
                    headersList.add(cell.getStringCellValue());

                } else {
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            jsonObject.put(headersList.get(cell.getColumnIndex()), cell.getStringCellValue());
                            break;

                        case Cell.CELL_TYPE_NUMERIC:
                            jsonObject.put(headersList.get(cell.getColumnIndex()), cell.getNumericCellValue());
                            break;

                        case Cell.CELL_TYPE_BOOLEAN:
                            jsonObject.put(headersList.get(cell.getColumnIndex()), cell.getBooleanCellValue());
                            break;

                        default :
                    }
                }
			}
            if(jsonObject.length() > 0) {
                jsonArray.put(jsonObject);
            }
            System.out.println("jsonArray === " + jsonArray);
        }
        System.out.println("headersList === " + headersList);

        return jsonArray;
	}
}
