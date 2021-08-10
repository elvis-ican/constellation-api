package dev.wre.api.integration;

import dev.wre.api.models.Users;
import dev.wre.api.util.TokenUtil;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import javax.crypto.SecretKey;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
public class LoginIntegrationTest {

    @Test
    public void testLoginShouldReturnCorrectHeader(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("username", "harry");
        body.add("password", "magicmagic");
        HttpEntity<?> request = new HttpEntity<Object>(body, requestHeaders);

        ResponseEntity<String> response =  restTemplate.exchange(
                "http://localhost:8022/login",
                HttpMethod.POST,
                request,
                String.class);

        HttpHeaders responseHeaders = response.getHeaders();

        SecretKey secretKey = null;
        String token = null;
        try {
            secretKey = TokenUtil.getKeyFromPassword("harry");
            token = TokenUtil.convertSecretKeyToString(secretKey);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            e.printStackTrace();
        }
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);
        headers.set("Username", "harry");
        headers.set("UserId", "1");
        headers.set("Access-Control-Expose-Headers", "Content-Type, Allow, Authorization, Username, UserId");

        assertAll(
                ()->assertEquals(responseHeaders.get("Authorization"), headers.get("Authorization")),
                ()->assertEquals(responseHeaders.get("Username"), headers.get("Username")),
                ()->assertEquals(responseHeaders.get("UserId"), headers.get("UserId")),
                ()->assertEquals(responseHeaders.get("Access-Control-Expose-Headers"),
                        headers.get("Access-Control-Expose-Headers")),
                ()->assertEquals(200, response.getStatusCodeValue())
        );
    }

    @Test
    public void testGetOneUnauthorizedReturns405(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("username", "notExistedUser");
        ResponseEntity<String> response =  restTemplate.getForEntity("http://localhost:8022/login", String.class);
        assertEquals(405, response.getStatusCodeValue());
    }

    @Test
    public void testGetOneUnauthorizedReturns403(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("username", null);
        ResponseEntity<String> response =  restTemplate.getForEntity("http://localhost:8022/login", String.class);
        assertEquals(405, response.getStatusCodeValue());
    }

    @Test
    public void testRegisterShouldReturnCorrectUser(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("username", "newUser3");
        body.add("password", "pass");
        body.add("email", "newuser3@wre.dev");
        HttpEntity<?> request = new HttpEntity<Object>(body, requestHeaders);

        ResponseEntity<Users> response =  restTemplate.exchange(
                "http://localhost:8022/register",
                HttpMethod.POST,
                request,
                Users.class);
        Users actualBody = response.getBody();
        Users expectedBody = new Users("newUser3", "pass", "newuser3@wre.dev", true);
        assertAll(
                ()->assertEquals(expectedBody.getUsername(), actualBody.getUsername()),
                ()->assertEquals(expectedBody.getPassword(), actualBody.getPassword()),
                ()->assertEquals(expectedBody.getEmail(), actualBody.getEmail()),
                ()->assertEquals(201, response.getStatusCodeValue())
        );
    }

    @Test
    public void testRegisterExistedUserShouldReturns406(){
        TestRestTemplate restTemplate = new TestRestTemplate();
        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.valueOf(MediaType.APPLICATION_FORM_URLENCODED_VALUE));
        MultiValueMap<String, String> body = new LinkedMultiValueMap<String, String>();
        body.add("username", "harry");
        body.add("password", "pass");
        body.add("email", "newuser2@wre.dev");
        HttpEntity<?> request = new HttpEntity<Object>(body, requestHeaders);

        ResponseEntity<Users> response =  restTemplate.exchange(
                "http://localhost:8022/register",
                HttpMethod.POST,
                request,
                Users.class);
        assertEquals(406, response.getStatusCodeValue());
    }


}
