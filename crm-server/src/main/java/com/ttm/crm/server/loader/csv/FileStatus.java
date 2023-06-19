package com.ttm.crm.server.loader.csv;

public enum FileStatus {
	ERROR("Error"),
	UPLOADED("Uploaded"),
	POSTED("Posted");
	
	public final String label;

    private FileStatus(String label) {
        this.label = label;
    }
}
