package Excel_ex;
import java.util.Iterator;
import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.*;
import java.lang.reflect.Field;
import org.apache.poi.ss.usermodel.Row;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


@MultipartConfig
public class UploadFileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    // Get the uploaded file from the request
	    Part filePart = request.getPart("file");
	    InputStream fileContent = filePart.getInputStream();

	    // Read the contents of the excel file
	    System.out.println("here");
	   
	    Workbook workbook = new XSSFWorkbook(fileContent);
	    Sheet sheet = workbook.getSheetAt(0);
	    Iterator<Row> rows = sheet.iterator();
	    System.out.println("here");

	    String filename = getFileName(filePart);

	    // Save the uploaded file to the file system
	    saveFile(fileContent, filename,request);
	    System.out.println("here");
	    // Prepare the HTML table
	    StringBuilder html = new StringBuilder("<table style='border:1px black solid;' border='2'>");
	    int count=0;
	    while (rows.hasNext()) {
	    	if(count==0) {
	        Row row = rows.next();
	        html.append("<tr style='border:2px green black;'>");
	        Iterator<Cell> cells = row.cellIterator();
	        while (cells.hasNext()) {
	            Cell cell = cells.next();
	            html.append("<td>");
	            switch (cell.getCellType()) {
	                case NUMERIC:
	                    html.append(cell.getNumericCellValue());
	                    break;
	                case STRING:
	                    html.append(cell.getStringCellValue());
	                    break;
	                default:
	                    break;
	            }
	            html.append("</td>");
	        }
	        html.append("</tr>");
	    }else {
	    	Row row = rows.next();
	        html.append("<tr>");
	        Iterator<Cell> cells = row.cellIterator();
	        while (cells.hasNext()) {
	            Cell cell = cells.next();
	            html.append("<td>");
	            switch (cell.getCellType()) {
	                case NUMERIC:
	                    html.append(cell.getNumericCellValue());
	                    break;
	                case STRING:
	                    html.append(cell.getStringCellValue());
	                    break;
	                default:
	                    break;
	            }
	            html.append("</td>");
	        }
	        html.append("</tr>");
	    }
	    }
	    html.append("</table>");

	    request.setAttribute("html", html.toString());
	    request.setAttribute("filename", filename);
	   // response.sendRedirect("display.jsp?html="+html.toString()+"filename=" +filename);
	    request.getRequestDispatcher("display.jsp").forward(request, response);
	}
	private String getFileName(Part part) {
	    for (String content : part.getHeader("content-disposition").split(";")) {
	        if (content.trim().startsWith("filename")) {
	            return content.substring(content.indexOf("=") + 2, content.length()-1);
	        }
	    }
	    return null;
	}

	private void saveFile(InputStream fileContent, String filename, HttpServletRequest request) throws IOException {
	    OutputStream outputStream = new FileOutputStream(new File(request.getRealPath("/")+ filename));
	    int read = 0;
	    byte[] bytes = new byte[1024];
	    while ((read = fileContent.read(bytes)) != -1) {
	        outputStream.write(bytes, 0, read);
	    }
	    outputStream.close();
	    fileContent.close();
	}

}
