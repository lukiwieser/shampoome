package shampoome.gateway.model;

import java.util.Dictionary;

public abstract class CamundaBaseRequestMessage {

    private String messageName;

    public CamundaBaseRequestMessage(String messageName, Dictionary<String, Object> processVariables) {
        this.messageName = messageName;
        this.processVariables = processVariables;
    }

    public CamundaBaseRequestMessage() {
    }

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
