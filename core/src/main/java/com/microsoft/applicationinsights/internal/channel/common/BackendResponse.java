package com.microsoft.applicationinsights.internal.channel.common;

/**
 * Utility class used by the {@link PartialSuccessHandler}
 *
 * @author jamdavi
 *
 */
class BackendResponse {

    int itemsReceived;
    int itemsAccepted;
    Error[] errors;
    
    public int getItemsReceived() {
		return itemsReceived;
	}
    public void setItemsReceived(int itemsReceived) {
		this.itemsReceived = itemsReceived;
	}
    public int getItemsAccepted() {
		return itemsAccepted;
	}
    public void setItemsAccepted(int itemsAccepted) {
		this.itemsAccepted = itemsAccepted;
	}
    public Error[] getErrors() {
		return errors;
	}
    public void setErrors(Error[] errors) {
		this.errors = errors;
	}

    static class Error {
        public int index;
        public int statusCode;
        public String message;
    }
}
