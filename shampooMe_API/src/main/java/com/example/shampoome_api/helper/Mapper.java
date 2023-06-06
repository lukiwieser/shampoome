package com.example.shampoome_api.helper;

import com.example.shampoome_api.model.CamundaRequestMessage;
import com.example.shampoome_api.model.Feedback;
import com.example.shampoome_api.model.OrderInput;
import com.example.shampoome_api.model.Preferences;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Mapper {

    @Contract(pure=true)
    public CamundaRequestMessage MapPreferencesToCamundaRequestMessage(Preferences preferences) {
        CamundaRequestMessage camundaRequestMessage = new CamundaRequestMessage();
        camundaRequestMessage.setMessageName("StartEvent");

        Map<String, Object> map = new HashMap<>();
        map.put("ageOver25", preferences.ageOver25);
        map.put("hairType", preferences.hairType);
        map.put("scalp", preferences.scalp);
        map.put("splitEnds", preferences.splitEnds);
        map.put("dandruff", preferences.dandruff);
        map.put("hairLossMedium", preferences.hairLossMedium);
        map.put("hairLossStrong", preferences.hairLossStrong);
        map.put("thinHair", preferences.thinHair);
        map.put("bottleSize", preferences.bottleSize);
        map.put("fragrance", preferences.fragrance);
        map.put("nickName", preferences.nickName);

        camundaRequestMessage.setProcessVariables(createProcessVariables(map));
        return camundaRequestMessage;
    }

    @Contract(pure = true)
    public CamundaRequestMessage MapOrderToCamundaRequestMessage(OrderInput order) {
        CamundaRequestMessage camundaRequestMessage = new CamundaRequestMessage();
        camundaRequestMessage.setMessageName("CustomerOrder");

        Map<String, Object> map = new HashMap<>();
        map.put("processId", order.processId);
        map.put("shippingAddress", order.shippingAddress);
        map.put("matriculationNumber", order.matriculationNumber);
        camundaRequestMessage.setProcessVariables(createProcessVariables(map));

        return camundaRequestMessage;
    }

    public CamundaRequestMessage MapFeedbackToCamundaRequestMessage(Feedback feedback) {
        CamundaRequestMessage camundaRequestMessage = new CamundaRequestMessage();
        camundaRequestMessage.setMessageName("CustomerFeedback");

        Map<String, Object> map = new HashMap<>();
        map.put("processId", feedback.processId);
        map.put("overallSatisfaction", feedback.overallSatisfaction);
        map.put("priceSatisfaction", feedback.priceSatisfaction);
        map.put("comment", feedback.comment);
        camundaRequestMessage.setProcessVariables(createProcessVariables(map));

        return camundaRequestMessage;
    }

    private @NotNull String getPrimitiveName(Object value) {
        String name = value.getClass().getName().toLowerCase(Locale.ROOT);
        if(name.endsWith("boolean")) return "Boolean";
        else if(name.endsWith("string")) return "String";
        else if(name.endsWith("integer")) return "Integer";
        else return "Unknown";
    }

    private @NotNull Dictionary<String, Object> createProcessVariables(Map<String, Object> map) {
        Dictionary<String, Object> processVariables = new Hashtable<>();
        for(Map.Entry<String, Object> entry : map.entrySet()) {
            if(entry.getValue() == null) continue;
            Map<String, Object> innerMap = new HashMap<>();
            processVariables.put(entry.getKey(), innerMap);
            innerMap.put("value", entry.getValue());
            innerMap.put("type", getPrimitiveName(entry.getValue()));
        }
        return processVariables;
    }
}
