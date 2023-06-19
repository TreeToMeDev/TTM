package com.ttm.crm.server.loader.csv;

import java.util.ArrayList;
import java.util.List;

public class CSVFileRow {

	private int rowNum;
	private FileRowStatus status;
	private List<CSVFileRowColumn> columns;
	private List<String> errors;
	
	public CSVFileRow(int rowNum, FileRowStatus status) {
		this.rowNum = rowNum;
		this.status = status;
		this.columns = new ArrayList<CSVFileRowColumn>();
		this.errors = new ArrayList<String>();
	}
	
	public void addColumn(String name, String value) {
		columns.add(new CSVFileRowColumn(name, value));
	}
	
	public void addError(String error) {
		errors.add(error);
		this.status = FileRowStatus.ERROR;
	}
	
	public int getRowNum() {
		return rowNum;
	}
	public void setRowNum(int rowNum) {
		this.rowNum = rowNum;
	}
	public FileRowStatus getStatus() {
		return status;
	}
	public void setStatus(FileRowStatus status) {
		this.status = status;
	}
	public List<CSVFileRowColumn> getColumns() {
		return columns;
	}
	public void setColumns(List<CSVFileRowColumn> columns) {
		this.columns = columns;
	}
	public List<String> getErrors() {
		return errors;
	}
	public void setErrors(List<String> errors) {
		this.errors = errors;
	}
}
