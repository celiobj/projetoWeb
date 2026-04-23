/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.controlefacilWeb.demo.services;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;

import com.controlefacilWeb.demo.database.Login;

import java.net.http.HttpClient;

/**
 *
 * @author celio.junior
 */
public class RestApi {

    String urlBase = Login.getUrlBase();

    public HttpResponse<String> Post(String url, String request) throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest postRequest = (HttpRequest) HttpRequest.newBuilder()
                .uri(new URI(urlBase + url))
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(request))
                .build();

        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        return postResponse;
    }

    public HttpResponse<String> Put(String url, String request) throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest postRequest = (HttpRequest) HttpRequest.newBuilder()
                .uri(new URI(urlBase + url))
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(request))
                .build();

        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        return postResponse;
    }

    public HttpResponse<String> Patch(String url, String request) throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();
        HttpRequest postRequest = (HttpRequest) HttpRequest.newBuilder()
                .uri(new URI(urlBase + url))
                .header("accept", "application/json")
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(request))
                .build();

        HttpResponse<String> postResponse = httpClient.send(postRequest, HttpResponse.BodyHandlers.ofString());
        return postResponse;
    }

    public HttpResponse<String> Get(String url, String request) throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest getRequest = (HttpRequest) HttpRequest.newBuilder()
                .uri(new URI(urlBase + url + request))
                .header("accept", "application/json")
                //  .GET()//GET é opcional
                .build();

        HttpResponse<String> getResponse = httpClient.send(getRequest, BodyHandlers.ofString());
        return getResponse;
    }

    public HttpResponse<String> Delete(String url, String request) throws URISyntaxException, IOException, InterruptedException {
        HttpClient httpClient = HttpClient.newHttpClient();

        HttpRequest getRequest = (HttpRequest) HttpRequest.newBuilder()
                .uri(new URI(urlBase + url + request))
                .header("accept", "application/json")
                .DELETE()//GET é opcional
                .build();

        HttpResponse<String> getResponse = httpClient.send(getRequest, BodyHandlers.ofString());
        return getResponse;
    }
}
