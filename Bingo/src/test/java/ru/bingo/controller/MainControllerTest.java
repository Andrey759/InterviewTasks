package ru.bingo.controller;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.client.RestTemplate;

public class MainControllerTest {

    public static void main(String... args) {
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://localhost:8080/login";
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Basic ZyBn");
        HttpEntity<String> request = new HttpEntity<String>(headers);
        String text = restTemplate.exchange(url, HttpMethod.POST, request, String.class).getBody();
        System.out.println(text);
    }

}
