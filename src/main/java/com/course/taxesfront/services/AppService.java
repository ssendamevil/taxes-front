package com.course.taxesfront.services;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;

import com.course.taxesfront.dtos.*;
import javafx.application.Platform;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.classic.methods.HttpPost;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClients;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.io.entity.StringEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.hc.core5.net.URIBuilder;


public class AppService {
    private static final String BASE_URL = "http://localhost:8888/api";

    public void login(String username, String password, Consumer<Map<String, UserDto>> callback) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(BASE_URL + "/user/login");
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(new LoginRequest(username, password));

            request.setEntity(new StringEntity(json, StandardCharsets.UTF_8));
            request.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getCode() == 200) {
                    String jsonRes = EntityUtils.toString(response.getEntity());
                    UserDto user = new UserDto(jsonRes);
                    Map<String, UserDto> map = new HashMap<>();
                    map.put(String.valueOf(response.getCode()), user);
                    Platform.runLater(() -> callback.accept(map));
                } else {
                    UserDto userDto = new UserDto();
                    Map<String, UserDto> map = new HashMap<>();
                    map.put(String.valueOf(response.getCode()), userDto);
                    Platform.runLater(() -> callback.accept(map));
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            UserDto userDto = new UserDto();
            Map<String, UserDto> map = new HashMap<>();
            map.put("500", userDto);
            Platform.runLater(() -> callback.accept(map));
        }
    }

    public void loadIncomes(String username, Consumer<List<IncomeDto>> callback){
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "/income/"+username);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getCode() == 200) {
                    String json = EntityUtils.toString(response.getEntity());

                    List<IncomeDto> incomes = IncomeObjectParser.parseIncomes(json);

                    Platform.runLater(() -> callback.accept(incomes));
                } else {
                    Platform.runLater(() -> callback.accept(List.of()));
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            Platform.runLater(() -> callback.accept(List.of()));
        }
    }

    public void loadProperties(String username, Consumer<List<PropertyDto>> callback){
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "/property/"+username);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getCode() == 200) {
                    String json = EntityUtils.toString(response.getEntity());

                    List<PropertyDto> properties = PropertyObjectParser.parseProperties(json);

                    Platform.runLater(() -> callback.accept(properties));
                } else {
                    Platform.runLater(() -> callback.accept(List.of()));
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            Platform.runLater(() -> callback.accept(List.of()));
        }
    }

    public void loadTaxesWithFilter(String username, TaxFilter taxFilter, Consumer<List<TaxDto>> callback){
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            URIBuilder uriBuilder = new URIBuilder(BASE_URL + "/taxes/" + username);
            if (Objects.nonNull(taxFilter.getType())) uriBuilder.addParameter("type", taxFilter.getType().toString());
            if (Objects.nonNull(taxFilter.getFrom())) uriBuilder.addParameter("from", taxFilter.getFrom().toString());
            if (Objects.nonNull(taxFilter.getTo())) uriBuilder.addParameter("to", taxFilter.getTo().toString());
            if (Objects.nonNull(taxFilter.getMin())) uriBuilder.addParameter("min", String.valueOf(taxFilter.getMin()));
            if (Objects.nonNull(taxFilter.getMax())) uriBuilder.addParameter("max", String.valueOf(taxFilter.getMax()));


            HttpGet request = new HttpGet(uriBuilder.build());

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getCode() == 200) {
                    String json = EntityUtils.toString(response.getEntity());

                    List<TaxDto> taxes = TaxObjectParser.parseTaxes(json);

                    Platform.runLater(() -> callback.accept(taxes));
                } else {
                    Platform.runLater(() -> callback.accept(List.of()));
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            Platform.runLater(() -> callback.accept(List.of()));
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

    public void addNewIncome(String username, IncomeDto dto, Consumer<String> callback) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(BASE_URL + "/income/"+username);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.registerModule(new JavaTimeModule());
            String json = objectMapper.writeValueAsString(dto);

            request.setEntity(new StringEntity(json, StandardCharsets.UTF_8));
            request.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getCode() == 200) {
                    Platform.runLater(() -> callback.accept("200"));
                } else {
                    Platform.runLater(() -> callback.accept(String.valueOf(response.getCode())));
                }
            }
        } catch (IOException e) {
            Platform.runLater(() -> callback.accept("500"));
        }
    }

    public void calculateTaxes(String username, Consumer<List<TaxDto>> callback){
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "/taxes/calculate/"+username);

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getCode() == 200) {
                    String json = EntityUtils.toString(response.getEntity());
                    List<TaxDto> taxes = TaxObjectParser.parseTaxes(json);

                    Platform.runLater(() -> callback.accept(taxes));
                } else {
                    Platform.runLater(() -> callback.accept(List.of()));
                }
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
        } catch (IOException e) {
            Platform.runLater(() -> callback.accept(List.of()));
        }
    }

    public void register(UserCreate dto,
            Consumer<Integer> callback) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpPost request = new HttpPost(BASE_URL + "/user/register");
            ObjectMapper objectMapper = new ObjectMapper();
            String json = objectMapper.writeValueAsString(dto);

            request.setEntity(new StringEntity(json, StandardCharsets.UTF_8));
            request.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                if (response.getCode() == 200) {
                    Platform.runLater(() -> callback.accept(response.getCode()));
                } else {
                    Platform.runLater(() -> callback.accept(500));
                }
            }
        } catch (IOException e) {
            Platform.runLater(() -> callback.accept(500));
        }
    }

    public void downloadPdf(String username, Consumer<Map<String, String>> callback){
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet request = new HttpGet(BASE_URL + "/pdf/" + username);
            request.setHeader("Content-Type", "application/json");

            try (CloseableHttpResponse response = httpClient.execute(request)) {
                Map<String, String> result = new HashMap<>();
                if (response.getCode() == 200) {
                    InputStream inputStream = response.getEntity().getContent();
                    File pdfFile = new File("tax_report.pdf");
                    try (FileOutputStream outputStream = new FileOutputStream(pdfFile)) {
                        byte[] buffer = new byte[1024];
                        int bytesRead;
                        while ((bytesRead = inputStream.read(buffer)) != -1) {
                            outputStream.write(buffer, 0, bytesRead);
                        }
                    }
                    result.put("200", "PDF successfully downloaded: " + pdfFile.getAbsolutePath());
                } else {
                    result.put(String.valueOf(response.getCode()), "Failed to download PDF. Server response: " + response.getCode());
                }
                Platform.runLater(() -> callback.accept(result));
            }
        } catch (IOException e) {
            Map<String, String> errorResult = new HashMap<>();
            errorResult.put("500", "Error: " + e.getMessage());
            Platform.runLater(() -> callback.accept(errorResult));
        }
    }
}
