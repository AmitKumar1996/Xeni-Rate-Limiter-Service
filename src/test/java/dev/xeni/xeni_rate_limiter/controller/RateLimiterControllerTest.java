package dev.xeni.xeni_rate_limiter.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

import dev.xeni.xeni_rate_limiter.model.ClientConfig;
import dev.xeni.xeni_rate_limiter.repository.ClientConfigRepository;

@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RateLimiterControllerTest {

    private MockMvc mockMvc;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ClientConfigRepository clientConfigRepository;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        clientConfigRepository.deleteAll(); // Reset before each test
    }

    @Test
    public void testRegisterClient() throws Exception {
        ClientConfig config = new ClientConfig("client123", 5, 60);

        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(config)))
                .andExpect(status().isOk());
               
    }

    @Test
    public void testIsRequestAllowed() throws Exception {
        // First register the client
        ClientConfig config = new ClientConfig("client123", 5, 60);
        clientConfigRepository.save(config);

        mockMvc.perform(get("/api/request")
                .param("clientId", "client123"))
                .andExpect(status().isOk());
                
    }

    @Test
    public void testGetClientConfig() throws Exception {
        ClientConfig config = new ClientConfig("client123", 5, 60);
        clientConfigRepository.save(config);

        mockMvc.perform(get("/api/client-config")
                .param("clientId", "client123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.clientId").value("client123"))
                .andExpect(jsonPath("$.requestsPerWindow").value(5))
                .andExpect(jsonPath("$.windowInSeconds").value(60));
    }

    
}
