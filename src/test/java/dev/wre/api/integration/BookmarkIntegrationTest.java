package dev.wre.api.integration;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class BookmarkIntegrationTest {

    @Test
    public void testFindAllShouldReturns200(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response =  restTemplate.getForEntity(
                "http://localhost:8022/bookmarks",
                String.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testFindAllPublicShouldReturns200(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response =  restTemplate.getForEntity(
                "http://localhost:8022/bookmarks/public",
                String.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetByIdShouldReturns200(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("id", "1");
        HttpEntity<?> request = new HttpEntity<Object>(body, requestHeaders);
        ResponseEntity<String> response =  restTemplate.getForEntity(
                "http://localhost:8022/bookmarks/public",
                String.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testUpdateShouldReturns200(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        ResponseEntity<String> response =  restTemplate.getForEntity(
                "http://localhost:8022/bookmarks/1",
                String.class);
        assertEquals(200, response.getStatusCodeValue());
    }
}
