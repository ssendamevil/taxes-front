package com.course.taxesfront.services;

import com.course.taxesfront.dtos.IncomeDto;
import com.course.taxesfront.dtos.IncomeType;
import org.json.JSONArray;
import org.json.JSONObject;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class IncomeObjectParser {
    public static List<IncomeDto> parseIncomes(String json) {
        JSONArray jsonArray = new JSONArray(json);
        List<IncomeDto> incomes = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject obj = jsonArray.getJSONObject(i);
            IncomeDto income = new IncomeDto(
                    obj.getLong("id"),
                    IncomeType.valueOf(obj.getString("type")),
                    obj.getDouble("amount"),
                    LocalDateTime.parse(obj.getString("date"), formatter),
                    obj.getString("source")
            );
            incomes.add(income);
        }
        return incomes;
    }
}
