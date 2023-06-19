package com.ttm.crm.server.response;

public class ActionResponse {

	public ActionStatus actionStatus;
	public String message;

	public ActionResponse(ActionStatus actionStatus) {
		this.actionStatus = actionStatus;
	}

	public ActionResponse(ActionStatus actionStatus, String message) {
		// messages = new ArrayList<String>();
		// messages.add(message);
		this.actionStatus = actionStatus;
		this.message = message;
	}

}
