package com.ttm.crm.server.loader.csv;

public enum FileRowStatus {
	ERROR("Error"),
	NEW("New"),
	UPDATED("Updated");
	
	public final String label;

    private FileRowStatus(String label) {
        this.label = label;
    }
}
