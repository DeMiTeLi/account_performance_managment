package com.force.waveoc.service;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;

import org.apache.commons.io.IOUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.force.waveoc.domain.ForecastInfo;

@Service
public class POIServiceImpl implements POIService {
			
	@Override
	public ArrayList<ForecastInfo> parseExcelDocument(InputStream is) throws Exception {
		
		ArrayList<ForecastInfo> resultList = new ArrayList<ForecastInfo>();
		
		// need an open InputStream; for a file-based system, this would be appropriate:
		// InputStream stream = new FileInputStream(fileName);
		POIFSFileSystem fs = null;
		try
		{
		    fs = new POIFSFileSystem(is);
		}
		catch (IOException e)
		{
			System.out.println(e);
			System.out.println(e.toString());
		    // an I/O error occurred, or the InputStream did not provide a compatible
		    // POIFS data structure
		}
		
		// ByteArrayInputStream bis = new ByteArrayInputStream(fs);
		
		// create Workbook instance holding reference to .xls file
		HSSFWorkbook workbook = new HSSFWorkbook(fs);
		HSSFSheet sheet = workbook.getSheetAt(0);
		HSSFRow row; 
		HSSFCell cell;
 
		Iterator rows = sheet.rowIterator();
 
		while (rows.hasNext())
		{
			row = (HSSFRow) rows.next();
			Iterator cells = row.cellIterator();
			
			while (cells.hasNext())
			{
				cell=(HSSFCell) cells.next();
				
				if (cell.getCellType() == HSSFCell.CELL_TYPE_STRING)
				{
					System.out.print(cell.getStringCellValue()+" ");
					ForecastInfo infoObject = new ForecastInfo();
					infoObject.setOpportunityName(cell.getStringCellValue());
					
					resultList.add(infoObject);
				}
				else if(cell.getCellType() == HSSFCell.CELL_TYPE_NUMERIC)
				{
					System.out.print(cell.getNumericCellValue()+" ");
				}
				else
				{
					//U Can Handel Boolean, Formula, Errors
				}
			}
			System.out.println();
		}
		

        // get first/desired sheet from the workbook

        
		return resultList;
	}

}
