package com.example.shampoome_api.helper;

import com.example.shampoome_api.model.CamundaRequestMessage;
import com.example.shampoome_api.model.Customer;
import com.example.shampoome_api.model.Preferences;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.Contract;

import java.util.*;

public class Mapper {

    @Contract(pure=true)
    public CamundaRequestMessage MapPreferencesToCamundaRequestMessage(Preferences preferences) {
        CamundaRequestMessage camundaRequestMessage = new CamundaRequestMessage();
        Dictionary<String, Object> processVariables = new Hashtable<>();
        camundaRequestMessage.setMessageName("StartEvent");

        Map<String, Object> map = new HashMap<>();
        map.put("age", preferences.age);
        map.put("hairType", preferences.hairType);
        map.put("scalp", preferences.scalp);
        map.put("splitEnds", preferences.splitEnds);
        map.put("dandruff", preferences.dandruff);
        map.put("hairLossMedium", preferences.hairLossMedium);
        map.put("hairLossString", preferences.hairLossStrong);
        map.put("thinHair", preferences.thinHair);
        map.put("diet", preferences.diet);
        map.put("bottleSite", preferences.bottleSize);
        map.put("fragrance", preferences.fragrance);
        map.put("nickName", preferences.nickName);

        for(Map.Entry<String, Object> entry : map.entrySet()) {
            if(entry.getValue() == null) continue;
            Map<String, Object> innerMap = new HashMap<>();
            processVariables.put(entry.getKey(), innerMap);
            innerMap.put("value", entry.getValue());
            innerMap.put("type", getPrimitiveName(entry.getValue()));
        }

        camundaRequestMessage.setProcessVariables(processVariables);
        return camundaRequestMessage;
    }

    private String getPrimitiveName(Object value) {
        String name = value.getClass().getName().toLowerCase(Locale.ROOT);
        if(name.endsWith("boolean")) return "Boolean";
        else if(name.endsWith("string")) return "String";
        else if(name.endsWith("integer")) return "Integer";
        else return "Unknown";
    }
}
