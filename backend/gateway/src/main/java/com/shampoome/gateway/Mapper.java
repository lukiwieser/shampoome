package com.shampoome.gateway;

import com.shampoome.gateway.model.*;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class Mapper {

    @Contract(pure=true)
    public CamundaBaseRequestMessage MapPreferencesToCamundaRequestMessage(Preferences preferences) {
        CamundaStartRequestMessage camundaRequestMessage = new CamundaStartRequestMessage();
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
    public ExtendedCamundaRequestMessage MapOrderToCamundaRequestMessage(OrderInput order) {
        ExtendedCamundaRequestMessage camundaRequestMessage = new ExtendedCamundaRequestMessage();
        camundaRequestMessage.setMessageName("CustomerOrder");

        Map<String, Object> map = new HashMap<>();
        map.put("shippingAddress", order.shippingAddress);
        map.put("matriculationNumber", order.matriculationNumber);
        camundaRequestMessage.setProcessVariables(createProcessVariables(map));
        camundaRequestMessage.processInstanceId = order.processId;

        return camundaRequestMessage;
    }

    public ExtendedCamundaRequestMessage MapFeedbackToCamundaRequestMessage(Feedback feedback) {
        ExtendedCamundaRequestMessage camundaRequestMessage = new ExtendedCamundaRequestMessage();
        camundaRequestMessage.setMessageName("CustomerFeedback");

        Map<String, Object> map = new HashMap<>();
        map.put("overallSatisfaction", feedback.overallSatisfaction);
        map.put("priceSatisfaction", feedback.priceSatisfaction);
        map.put("comment", feedback.comments);
        camundaRequestMessage.setProcessVariables(createProcessVariables(map));
        camundaRequestMessage.processInstanceId = feedback.processId;

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
