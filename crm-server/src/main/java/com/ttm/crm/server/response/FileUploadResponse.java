package com.ttm.crm.server.response;

import com.ttm.crm.server.loader.csv.CSVUploadResponse;

public class FileUploadResponse {

	private CSVUploadResponse csvResponse;

	public CSVUploadResponse getCsvResponse() {
		return csvResponse;
	}

	public void setCsvResponse(CSVUploadResponse csvResponse) {
		this.csvResponse = csvResponse;
	}
}
