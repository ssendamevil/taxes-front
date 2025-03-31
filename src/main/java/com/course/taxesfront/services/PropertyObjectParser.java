package com.course.taxesfront.services;

import com.course.taxesfront.dtos.PropertyDto;
import com.course.taxesfront.dtos.IncomeType;
import com.course.taxesfront.dtos.PropertyType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class PropertyObjectParser {
    public static List<PropertyDto> parseProperties(String json) {
        JSONArray jsonArray = new JSONArray(json);
        List<PropertyDto> properties = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            PropertyDto property = new PropertyDto(
                    obj.optLong("id"),
                    obj.optDouble("price"),
                    obj.optString("description"),
                    obj.has("date") ? LocalDateTime.parse(obj.getString("date"), formatter) : null,
                    obj.has("type") ? PropertyType.valueOf(obj.getString("type")) : null
            );
            properties.add(property);
        }
        return properties;
    }
}
