package com.example.shampoome_api.model;

import java.util.Dictionary;
import java.util.Hashtable;

public class CamundaRequestMessage {

    private String messageName;
    public final String tenantId = "shampoome-process";
    public final boolean resultEnabled = true;

    private Dictionary<String, Object> processVariables;

    public String getMessageName() {
        return messageName;
    }

    public void setMessageName(String messageName) {
        this.messageName = messageName;
    }

    public Dictionary<String, Object> getProcessVariables() {
        return processVariables;
    }

    public void setProcessVariables(Dictionary<String, Object> processVariables) {
        this.processVariables = processVariables;
    }
}
