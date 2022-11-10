package helper;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
//import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ExcelDataConfig {

	public File spreadsheet;
	public File writespreadsheet;
	public Sheet currentSheet;
	public Map<String, Integer> columns;
	public Workbook workbooks ;
	private ExtentTest logger;


	public ExcelDataConfig(File file) {
		try {
			spreadsheet = file;
			columns = new LinkedHashMap<String, Integer>();
			//workbooks = WorkbookFactory.create(spreadsheet);
			workbooks = WorkbookFactory.create(new FileInputStream(spreadsheet));

		}

		catch (Exception e) {

			System.out.println(e.getMessage());
		}

	}

	public ExcelDataConfig(String file) {
		try {
			spreadsheet = new File(file);
			columns = new LinkedHashMap<String, Integer>();
			//workbooks = WorkbookFactory.create(spreadsheet);
			workbooks = WorkbookFactory.create(new FileInputStream(spreadsheet));

		}

		catch (Exception e) {

			System.out.println(e.getMessage());
		}

	}
	
	public void setLogger(ExtentTest logger) {
		this.logger =logger;

	}


	public  void switchToSheet(String name){
		try{
			currentSheet = workbooks.getSheet(name);
			

			//int colNum = currentSheet.getRow(0).getLastCellNum();
			//if (currentSheet.getRow(0).cellIterator().hasNext()) {
			//	for (int j = 0; j < colNum; j++) {
			//		columns.put(currentSheet.getRow(0).getCell(j).getStringCellValue(), currentSheet.getRow(0).getCell(j).getColumnIndex());
			//	}
			//}

		}catch(Exception e){
			e.printStackTrace();
		}
	}

	public  void switchToSheet(Integer shIndex){
		try{
			currentSheet = workbooks.getSheetAt(shIndex);

			//int colNum = currentSheet.getRow(0).getLastCellNum();
			//if (currentSheet.getRow(0).cellIterator().hasNext()) {
			//	for (int j = 0; j < colNum; j++) {
			//		columns.put(currentSheet.getRow(0).getCell(j).getStringCellValue(), currentSheet.getRow(0).getCell(j).getColumnIndex());
			//	}
			//}

		}catch(Exception e){
			e.printStackTrace();
		}
	}
	public String getAllSheetNames() {
		int sheetCount= workbooks.getNumberOfSheets();
		StringBuffer sheetnames = new StringBuffer();

		for(int i=0; i<sheetCount;i++) {

			sheetnames.append(workbooks.getSheetName(i)).append(",");
		}
		return sheetnames.deleteCharAt(sheetnames.length() - 1).toString();
	}
	
	public  void createSheet(String name){
		try{
			workbooks.createSheet(name);	


			//currentSheet = workbooks.getSheet(name);

		}catch(Exception e){
			e.printStackTrace();
		}

	}

	public  boolean verifySheetExists(String name){
		try{

			Sheet tmpsheet = workbooks.getSheet(name);
			if (tmpsheet != null) {

				return true;
			}



		}catch(Exception e){
			e.printStackTrace();
		}
		return false;
	}

	public void closeFile() {
		try {			
			workbooks.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	public void refershChanges() {
		currentSheet.getWorkbook().getCreationHelper().createFormulaEvaluator().evaluateAll();
	}

	public void saveFile() throws IOException {
		FileOutputStream fos;
		try {
			fos = new FileOutputStream(spreadsheet);
			workbooks.write(fos);
			//workbooks.getCreationHelper().createFormulaEvaluator().;
			
			fos.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	//public String getData(int row, String column ){
	//	Row dataRow =	  currentSheet.getRow(row);
	//	return	getCellDataAsString(dataRow.getCell(columns.get(column)));
	//}

	private  String getCellDataAsString(Cell cell){
		String val ="";
		if (cell == null) {
			return "";
		}
		try {
			CellType cellType = cell.getCellType();


			if (cellType == CellType.STRING) {
				//val= cell.getStringCellValue();

				// HSSFRichTextString str = (HSSFRichTextString) column.getRichStringCellValue();

				//XSSFRichTextString str = (XSSFRichTextString) cell.getRichStringCellValue();
				RichTextString str = (RichTextString) cell.getRichStringCellValue();
				//System.out.println(str);
				val= str.toString();

			}
			if (cellType == CellType.NUMERIC) {
				/*
				 * //val= String.valueOf((int)cell.getNumericCellValue());
				 * 
				 * DataFormatter formatter = new DataFormatter(); val =
				 * formatter.formatCellValue(cell);
				 */

				//XSSFCellStyle style = (XSSFCellStyle) cell.getCellStyle();
				CellStyle style = (CellStyle) cell.getCellStyle();

				if(style == null) {
					val= String.valueOf((int)cell.getNumericCellValue());
				} else {
					DataFormatter formatter = new DataFormatter(Locale.getDefault());

					val =  formatter.formatRawCellContents(
							cell.getNumericCellValue(),
							style.getDataFormat(),
							style.getDataFormatString());
				}

			}
			if (cellType == CellType.FORMULA) {
				//System.out.println("Formula is " + cell.getCellFormula());



				switch(cell.getCachedFormulaResultType()) {
				case NUMERIC: 	    
					//XSSFCellStyle style = (XSSFCellStyle) cell.getCellStyle();
					CellStyle style = (CellStyle) cell.getCellStyle();
					if(style == null) {
					} else {
						DataFormatter formatter = new DataFormatter(Locale.getDefault());

						val =  formatter.formatRawCellContents(
								cell.getNumericCellValue(),
								style.getDataFormat(),
								style.getDataFormatString());
					}
					break;
				case STRING: 
					val= cell.getStringCellValue(); 
					break;
				default:
					break;
				}



			}

			if(cellType == CellType.BLANK) {
				val ="";
			}
			return val;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return val;
	}
	
	public  int getRowCount() {		 

		//return currentSheet.getPhysicalNumberOfRows();
		return currentSheet.getLastRowNum();
	}
	/// Added to remove error in project to support old code. Should be deleted later
	public  int getRowCount(int sheet) {		 

		//return currentSheet.getPhysicalNumberOfRows();
		return currentSheet.getLastRowNum();
	}

	public  String getData(int row, int column) {
		Row dataRow = currentSheet.getRow(row);
		return getCellDataAsString(dataRow.getCell(column));
	}
	
	/// Added to remove error in project to support old code. Should be deleted later
	public  String getData(int sheet,int row, int column) {
		Row dataRow = currentSheet.getRow(row);
		return getCellDataAsString(dataRow.getCell(column));
	}
	public void setCellData(int row, int column, String value) {
		Row  currentRow =	currentSheet.getRow(row);
		if(currentRow == null) {
			currentRow =currentSheet.createRow(row);
		}

		Cell currentCell = currentRow.getCell(column);
		if (currentCell == null) {
			currentCell =currentRow.createCell(column);
		}
		currentCell.setCellValue(value);

	}

	public void setCellData(int row, String column, String value) {
		Row  currentRow =	currentSheet.getRow(row);
		int colIndex = getCoumnIndex(0,column);
		if(currentRow == null) {
			currentRow =currentSheet.createRow(row);
		}

		Cell currentCell = currentRow.getCell(colIndex);
		if (currentCell == null) {
			currentCell =currentRow.createCell(colIndex);
		}
		currentCell.setCellValue(value);

	}
	
	public void verifyCellData(int row, String column, String value) {
		
		Row  currentRow =	currentSheet.getRow(row);
		int colIndex = getCoumnIndex(0,column);
		if(currentRow == null) {
			currentRow =currentSheet.createRow(row);
		}

		Cell currentCell = currentRow.getCell(colIndex);
		String cval = getCellDataAsString(currentCell);
		
		if(cval.matches(value)) {
			if(logger != null) {
				logger.log(Status.PASS,"Cell value "+ cval+" in row "+ row+"  mathches the expected cell value " +value+ " in column "+column);
			}				
		}else {

			if(logger != null) {
				logger.log(Status.FAIL,"Cell value "+ cval+" in row "+ row+" doesn't match the expected cell value " +value+ " in column "+column);
			}
			
		}

	}
	
	public void setCellDataBold(int row, int column, String value) {
		CellStyle style = workbooks.createCellStyle();
		Font font = workbooks.createFont();
		font.setBold(true);
		style.setFont(font);
		Row  currentRow =	currentSheet.getRow(row);
		
		if(currentRow == null) {
			currentRow =currentSheet.createRow(row);
		}

		Cell currentCell = currentRow.getCell(column);
		if (currentCell == null) {
			currentCell =currentRow.createCell(column);
		}
		currentCell.setCellValue(value);
		currentCell.setCellStyle(style);
	}
	
	public void setCellDataGreenBG(int row, String column, String value) {
		CellStyle style = workbooks.createCellStyle();
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
		Row  currentRow =	currentSheet.getRow(row);
		int colIndex = getCoumnIndex(0,column);
		if(currentRow == null) {
			currentRow =currentSheet.createRow(row);
		}

		Cell currentCell = currentRow.getCell(colIndex);
		if (currentCell == null) {
			currentCell =currentRow.createCell(colIndex);
		}
		currentCell.setCellValue(value);
		currentCell.setCellStyle(style);
	}
	
	public void setCellDataRedBG(int row, String column, String value) {
		CellStyle style = workbooks.createCellStyle();
		style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
		style.setFillForegroundColor(IndexedColors.RED.getIndex());
		Row  currentRow =	currentSheet.getRow(row);
		int colIndex = getCoumnIndex(0,column);
		if(currentRow == null) {
			currentRow =currentSheet.createRow(row);
		}

		Cell currentCell = currentRow.getCell(colIndex);
		if (currentCell == null) {
			currentCell =currentRow.createCell(colIndex);
		}
		currentCell.setCellValue(value);
		currentCell.setCellStyle(style);
	}

	public int getCoumnIndex(int row, String value) {
		int col = 0;
		//int colCount = currentSheet.getRow(row).getLastCellNum();
		Iterator<Cell> cellIterator = currentSheet.getRow(row).cellIterator();
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			String cellValue = getCellDataAsString(cell);
			if(value.matches(cellValue)) {
				return col;
			}
			col =col+1;
		}

		return -1;

	}

	public  int getColCount() {		 

		//return columns.size();

		return	currentSheet.getRow(0).getLastCellNum();

	}


	public  int getRowNum(int column, String datavalue ) {
		int rowCount = getRowCount();
		int currRow = 0 ;
		for (int i = 0; i  <= rowCount;i++) {
			if (datavalue.toString().equals(getData(i ,column ).toString()) ){
				currRow  = i;
				break;
			}
		}
		return currRow;


	}
	public  int getRowNum(String column, String datavalue ) {
		int colIndex = getCoumnIndex(0,column);
		int rowCount = getRowCount();
		int currRow = 0 ;
		for (int i = 0; i  <= rowCount;i++) {
			if (datavalue.toString().equals(getData(i ,colIndex ).toString()) ){
				currRow  = i;
				break;
			}
		}
		return currRow;


	}

	public ArrayList<Integer> getScenarioRows(String datascenario){
		int rowCount = getRowCount();
		ArrayList<Integer> arryList = new ArrayList<Integer>();

		if (datascenario.contentEquals("<ALL>")){
			for (int i = 1; i <rowCount; i++) {
				String dScenario = getData(i,0);
				if(!dScenario.isEmpty()) {
					arryList.add(i);

				}
			}
		}

		else
		{
			for (int i = 1; i <=rowCount; i++) {

				if(datascenario.matches(getData(i,0))) {
					arryList.add(i);

				}
			}

		}
		return arryList;


	}

	public  String[][] passData(String sheetname, List<String> exec_scenario){

		//ExcelDataConfig config = new ExcelDataConfig(spreadsheet);
		switchToSheet(sheetname);
		int colCount = getColCount();
		int row; 
		// adding multi dimenstional array
		ArrayList<ArrayList<String>> grid = new ArrayList<ArrayList<String>>();
		//Object grid[][] = new Object[row][colCount];
		//Loop 1 will loop on the data scenarios mentioned in the property sheet and add all the identified  rows to an ArrayList.
		ArrayList<Integer> arryList = new ArrayList<Integer>();
		try {
			for (int j =0; j < exec_scenario.size(); j++) {

				//Grabbing the row numbers to be executed based on the scenarios


				// Updating the array with all the scenarios we intend to run
				arryList.addAll(getScenarioRows(exec_scenario.get(j)));

			}
			//Loop 2  will loop on the rows of data identified for execution.	
			for (int i =0; i< arryList.size(); i++) {
				// get the row number of the scenario
				//System.out.println("Data Scenario "+exec_scenario.get(i)+" execution started");
				row = arryList.get(i);
				System.out.println("Row: " +row);

				grid.add(new ArrayList<String>());

				// Loop 3 will loop on each column of the data row
				for (int c =0; c <colCount; c++) {
					if(!(c== 0)) {
						String cellData = getData(row, c);
						//System.out.println(cellData);
						int k = c-1;
						grid.get(i).add(k, cellData);

					}
				}


				//}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//System.out.println(rows);
		String[][] data = new String[grid.size()][];
		for (int i = 0; i < data.length; i++) {
			data[i] = grid.get(i).toArray(new String[grid.get(i).size()]);
		}
		return data;
	}

}
