package com.ttm.crm.server.loader.csv;

import java.util.List;

public class CSVFileDetails {

	private int rows;
	private int columns;
	private int loadableRows;
	private List<String> matchedColumnHeaders;
	private List<String> unmatchedCSVHeaders;
	private List<String> unmatchedObjectHeaders;
	private List<CSVFileRow> records;
	
	
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
	public int getColumns() {
		return columns;
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	public List<String> getMatchedColumnHeaders() {
		return matchedColumnHeaders;
	}
	public void setMatchedColumnHeaders(List<String> matchedColumnHeaders) {
		this.matchedColumnHeaders = matchedColumnHeaders;
	}
	public List<String> getUnmatchedCSVHeaders() {
		return unmatchedCSVHeaders;
	}
	public void setUnmatchedCSVHeaders(List<String> unmatchedCSVHeaders) {
		this.unmatchedCSVHeaders = unmatchedCSVHeaders;
	}
	public List<String> getUnmatchedObjectHeaders() {
		return unmatchedObjectHeaders;
	}
	public void setUnmatchedObjectHeaders(List<String> unmatchedObjectHeaders) {
		this.unmatchedObjectHeaders = unmatchedObjectHeaders;
	}
	public List<CSVFileRow> getRecords() {
		return records;
	}
	public void setRecords(List<CSVFileRow> records) {
		this.records = records;
	}
	public int getLoadableRows() {
		return loadableRows;
	}
	public void setLoadableRows(int loadableRows) {
		this.loadableRows = loadableRows;
	}
}
