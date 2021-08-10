package dev.wre.api.integration;

import dev.wre.api.models.Users;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class UsersIntegrationTest {

    @Test
    public void testFindAllShouldReturns200(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        ResponseEntity<String> response =  restTemplate.getForEntity("http://localhost:8022/users", String.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testGetByIdShouldReturn400(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("id", "1");
        HttpEntity<?> request = new HttpEntity<Object>(body, requestHeaders);

        ResponseEntity<String> response =  restTemplate.exchange(
                "http://localhost:8022/users/get-one",
                HttpMethod.GET,
                request,
                String.class);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testGetByUserNameShouldReturn200(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        HttpEntity<?> request = new HttpEntity<Object>(requestHeaders);

        ResponseEntity<String> response =  restTemplate.exchange(
                "http://localhost:8022/users/harry",
                HttpMethod.GET,
                request,
                String.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testUpdateUserShouldReturn200(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("username", "newUser2");
        body.add("newPassword", "pass");
        body.add("oldPassword", "pass");
        body.add("email", "newuser2@wre.dev");
        HttpEntity<?> request = new HttpEntity<Object>(body, requestHeaders);

        ResponseEntity<Users> response =  restTemplate.exchange(
                "http://localhost:8022/users/update",
                HttpMethod.PUT,
                request,
                Users.class);
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    public void testDeleteUserShouldReturn202(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("username", "newUser3");
        body.add("password", "pass");
        HttpEntity<?> request = new HttpEntity<Object>(body, requestHeaders);

        ResponseEntity<Users> response =  restTemplate.exchange(
                "http://localhost:8022/users/delete",
                HttpMethod.DELETE,
                request,
                Users.class);
        assertEquals(202, response.getStatusCodeValue());
    }

}
