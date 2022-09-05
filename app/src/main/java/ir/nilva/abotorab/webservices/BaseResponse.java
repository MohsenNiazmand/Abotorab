package ir.nilva.abotorab.webservices;

import com.google.gson.annotations.Expose;

public class BaseResponse {
    @Expose
    private String messageBody;

    public String getMessageBody() {
        return messageBody;
    }

    public void setMessageBody(String messageBody) {
        this.messageBody = messageBody;
    }
}
