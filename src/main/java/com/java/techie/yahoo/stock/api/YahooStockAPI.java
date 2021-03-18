package com.java.techie.yahoo.stock.api;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.java.techie.yahoo.stock.api.dto.StockDto;

import yahoofinance.Stock;
import yahoofinance.YahooFinance;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.histquotes.Interval;

//https://www.youtube.com/watch?v=ZIjBSQc6MNA
//https://financequotes-api.com/
//https://financequotes-api.com/javadoc/yahoofinance/YahooFinance.html
//https://mkyong.com/java/apache-poi-reading-and-writing-excel-file-in-java/

public class YahooStockAPI {
	
//	private static final String FILE_NAME = "D:/BasuraTemporal/Borrar/Borrar/MyFirstExcel.xlsx";
	private static final String FILE_NAME = "c:/MyFirstExcel.xlsx";
	
	public static void main(String[] args) throws IOException {
		YahooStockAPI yahooStockApi = new YahooStockAPI();

//		System.out.println(yahooStockApi.getStock("INTC"));
//
//		String[] stockNames = { "GOOG", "^VIX", "^GSPC" };
//		System.out.println(yahooStockApi.getStock(stockNames));

//		yahooStockApi.getHistory("^GSPC");
		
//		yahooStockApi.getHistory("^GSPC", 1, "daily");

		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();
		from.add(Calendar.MONTH, -1);

		
//		yahooStockApi.getHistory("^GSPC", 1, "DAILY");
		yahooStockApi.getHistory("^VIX", from, to, "DAILY");
		System.out.println("FIN DATOS YAHOO API");
		
		XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("Datatypes in Java");
        Object[][] datatypes = {
                {"Datatype", "Type", "Size(in bytes)"},
                {"int", "Primitive", 2},
                {"float", "Primitive", 4},
                {"double", "Primitive", 8},
                {"char", "Primitive", 1},
                {"String", "Non-Primitive", "No fixed size"}
        };

        int rowNum = 0;
        System.out.println("Creating excel");

        for (Object[] datatype : datatypes) {
            Row row = sheet.createRow(rowNum++);
            int colNum = 0;
            for (Object field : datatype) {
                Cell cell = row.createCell(colNum++);
                if (field instanceof String) {
                    cell.setCellValue((String) field);
                } else if (field instanceof Integer) {
                    cell.setCellValue((Integer) field);
                }
            }
        }

        try {
            FileOutputStream outputStream = new FileOutputStream(FILE_NAME);
            workbook.write(outputStream);
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("Done");
	}
	public StockDto getStock(String stockName) throws IOException {
		StockDto dto = null;
		Stock stock = YahooFinance.get(stockName);
		dto = new StockDto(stock.getName(), stock.getQuote().getPrice(), stock.getQuote().getChange(),
				stock.getCurrency(), stock.getQuote().getBid());
		return dto;
	}

	public Map<String, Stock> getStock(String[] stockName) throws IOException {
		Map<String, Stock> stock = YahooFinance.get(stockName);
		return stock;
	}

	public Stock getStockFirst(String stockName) throws IOException {
		return YahooFinance.get(stockName);
	}

	public void getHistory(String stockName) throws IOException {
		Stock stock = YahooFinance.get(stockName);
		List<HistoricalQuote> history = stock.getHistory();
		for (HistoricalQuote quote : history) {
			System.out.println("===================");
			System.out.println("Symbol: " + quote.getSymbol());
			System.out.println("Date: " + convertDate(quote.getDate()));
			System.out.println("High: " + quote.getHigh());
			System.out.println("Low: " + quote.getLow());
			System.out.println("Close: " + quote.getClose());
			System.out.println("AdjClose: " + quote.getAdjClose());
			System.out.println("===================");
		}
	}

	public void getHistory(String stockName, int year, String searchType) throws IOException {
		Calendar from = Calendar.getInstance();
		Calendar to = Calendar.getInstance();

		from.add(Calendar.YEAR, Integer.valueOf("-" + year));

		Stock stock = YahooFinance.get(stockName);
		List<HistoricalQuote> history = stock.getHistory(from, to, getInterval(searchType));
		for (HistoricalQuote quote : history) {
			System.out.println("===================");
			System.out.println("Symbol: " + quote.getSymbol());
			System.out.println("Date: " + convertDate(quote.getDate()));
			System.out.println("High: " + quote.getHigh());
			System.out.println("Low: " + quote.getLow());
			System.out.println("Close: " + quote.getClose());
			System.out.println("AdjClose: " + quote.getAdjClose());
			System.out.println("===================");
		}
	}

	public void getHistory(String stockName, Calendar from, Calendar to, String searchType) throws IOException {
		Stock stock = YahooFinance.get(stockName);
		List<HistoricalQuote> history = stock.getHistory(from, to, getInterval(searchType));
		for (HistoricalQuote quote : history) {
			System.out.println("===================");
			System.out.println("Symbol: " + quote.getSymbol());
			System.out.println("Date: " + convertDate(quote.getDate()));
			System.out.println("High: " + quote.getHigh());
			System.out.println("Low: " + quote.getLow());
			System.out.println("Close: " + quote.getClose());
			System.out.println("AdjClose: " + quote.getAdjClose());
			System.out.println("===================");
		}
	}
	
	private String convertDate(Calendar cal) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		String formatDate = format.format(cal.getTime());
		return formatDate;

	}

	private Interval getInterval(String searchType) {
		Interval interval = null;
		if(searchType=="MONTHLY") {
			interval = Interval.MONTHLY;
		}
		if(searchType=="WEEKLY") {
			interval = Interval.WEEKLY;
		}
		if(searchType=="DAILY") {
			interval = Interval.DAILY;
		}
//		
//		switch (searchType.toUpperCase()) {
//		case "MONTHLY":
//			interval = Interval.MONTHLY;
//			break;
//		case "WEEKLY":
//			interval = Interval.WEEKLY;
//			break;
//		case "DAILY":
//			interval = Interval.DAILY;
//			break;
//		}
		return interval;
	}
}


