package shampoome.gateway.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CamundaResponseStartEvent {

    private String id;

    public String getId() {
        return id;
    }

    @SuppressWarnings("unchecked")
    @JsonProperty("processInstance")
    private void unpackNested(Map<String,Object> processInstance) {
        this.id = (String)processInstance.get("id");
    }
}
