package com.course.taxesfront.services;

import com.course.taxesfront.dtos.TaxDto;
import com.course.taxesfront.dtos.TaxType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TaxObjectParser {
    public static List<TaxDto> parseTaxes(String json) {
        JSONArray jsonArray = new JSONArray(json);
        List<TaxDto> taxes = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            TaxDto tax = new TaxDto(
                    obj.getLong("id"),
                    obj.getDouble("amount"),
                    !obj.isNull("incomeId") ? obj.getLong("incomeId") : null,
                    obj.getString("description"),
                    LocalDateTime.parse(obj.getString("date"), formatter),
                    TaxType.valueOf(obj.getString("type")),
                    !obj.isNull("propertyId") ? obj.getLong("propertyId") : null
            );
            taxes.add(tax);
        }
        return taxes;
    }
}
