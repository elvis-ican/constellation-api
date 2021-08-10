package dev.wre.api.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin
@RequestMapping("/googlesearch")
public class GoogleSearchController {

    @Autowired
    private RestTemplate restTemplate;

    //private final String apiKey = System.getenv("API_KEY");
    //private final String searchEngineID = System.getenv("SEARCH_ENGINE_ID");

    //Rensy keys
    private final String apiKey = "AIzaSyDcc0ImR7tEe63_3BymrtWh3uc0MkQDw3Q";
    private final String searchEngineID = "70e4cf11a03a3c97c";

    @GetMapping(value = "/{searchTopic}", produces = "application/json")
    public ResponseEntity<String> getGoogleSearch(@PathVariable("searchTopic") String searchTopic) throws JSONException {
        String url = "https://www.googleapis.com/customsearch/v1?key="+apiKey+"&cx="+searchEngineID+"&q="+searchTopic;
        ResponseEntity<String> responseEntity = restTemplate.getForEntity(url, String.class);
        JSONObject jsonString = new JSONObject(responseEntity.getBody());
        String items = jsonString.getString("items");
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}