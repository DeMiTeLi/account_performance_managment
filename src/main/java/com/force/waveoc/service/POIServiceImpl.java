package com.force.waveoc.service;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.format.CellDateFormatter;
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

        // Decide which rows to process
        int rowStart = sheet.getFirstRowNum();
        int rowEnd = sheet.getLastRowNum();
        int lastColumn =sheet.getRow(rowStart).getLastCellNum();

        // fill headersList
        Row headersRow = sheet.getRow(rowStart);
        for (int cn = 0; cn < lastColumn; cn++) {
            Cell headerCell = headersRow.getCell(cn, Row.RETURN_BLANK_AS_NULL);
            if (headerCell != null) {
                headersList.add(headerCell.getStringCellValue());
            } else {
                headersList.add("");
            }
        }

        for (int rowNum = rowStart + 1; rowNum <= rowEnd; rowNum++) {
            JSONObject jsonObject = new JSONObject();
            row = sheet.getRow(rowNum);
            if (row == null) {
                // This whole row is empty
                // Handle it as needed
                continue;
            }

            for (int cn = 0; cn < lastColumn; cn++) {
                cell = row.getCell(cn, Row.RETURN_BLANK_AS_NULL);
                if (cell == null) {
                    // The spreadsheet is empty in this cell
                    jsonObject.put(headersList.get(cn), "");

                } else {
                    // Do something useful with the cell's contents
                    switch (cell.getCellType()) {
                        case Cell.CELL_TYPE_STRING:
                            jsonObject.put(headersList.get(cell.getColumnIndex()), cell.getStringCellValue());
                            break;

                        case Cell.CELL_TYPE_NUMERIC:
                            if(DateUtil.isCellDateFormatted(cell)) {
                                double dv = cell.getNumericCellValue();
                                Date date = DateUtil.getJavaDate(dv);
                                String dateFmt = cell.getCellStyle().getDataFormatString();
                                String strValue = new CellDateFormatter(dateFmt).format(date);

                                // delete redundant string artefacts
                                if(strValue.indexOf("]") > 0){
                                    strValue = strValue.substring(strValue.indexOf("]") + 1, strValue.length());
                                }

                                if(strValue.indexOf(";") > 0){
                                    strValue = strValue.substring(0, strValue.indexOf(";"));
                                }
                                //

                                jsonObject.put(headersList.get(cell.getColumnIndex()), strValue);

                            } else {
                                jsonObject.put(headersList.get(cell.getColumnIndex()), cell.getNumericCellValue());
                            }
                            break;

                        case Cell.CELL_TYPE_BOOLEAN:
                            jsonObject.put(headersList.get(cell.getColumnIndex()), cell.getBooleanCellValue());
                            break;

                        default:
                    }
                }
            }

            if(jsonObject.length() > 0) {
                jsonArray.put(jsonObject);
            }

//            System.out.println("jsonArray === " + jsonArray);
        }




//        while (rows.hasNext()) {
//            JSONObject jsonObject = new JSONObject();
//			row = rows.next();
//			Iterator<Cell> cells = row.cellIterator();
//
//			while (cells.hasNext()) {
//                cell = cells.next();
//
//                if(cell.getRowIndex() == 0){
//                    headersList.add(cell.getStringCellValue());
//
//                } else {
//
//                    System.out.println("cell.getColumnIndex() === " + cell.getColumnIndex());
//                    System.out.println("cell.getRowIndex() === " + cell.getRowIndex());
//                    System.out.println("cell.getCellType() === " + cell.getCellType());
//
//                    switch (cell.getCellType()) {
//                        case Cell.CELL_TYPE_STRING:
//                            jsonObject.put(headersList.get(cell.getColumnIndex()), cell.getStringCellValue());
//                            break;
//
//                        case Cell.CELL_TYPE_NUMERIC:
//                            jsonObject.put(headersList.get(cell.getColumnIndex()), cell.getNumericCellValue());
//                            break;
//
//                        case Cell.CELL_TYPE_BOOLEAN:
//                            jsonObject.put(headersList.get(cell.getColumnIndex()), cell.getBooleanCellValue());
//                            break;
//
//                        default :
//                            jsonObject.put(headersList.get(cell.getColumnIndex()), "");
//                    }
//                }
//			}
//            if(jsonObject.length() > 0) {
//                jsonArray.put(jsonObject);
//            }
//            System.out.println("jsonArray === " + jsonArray);
//        }
        // System.out.println("headersList === " + headersList);

        return jsonArray;
	}
}
