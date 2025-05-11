package dev.xeni.xeni_rate_limiter.controller;

import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc // ✅ Add this to let Spring initialize MockMvc
public class RateLimiterControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        
    }

    @Test
    public void testRegisterClient() throws Exception {
        String json = "{\"clientId\":\"testClient\", \"requestsPerWindow\":100}";

        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isOk());
    }
}
