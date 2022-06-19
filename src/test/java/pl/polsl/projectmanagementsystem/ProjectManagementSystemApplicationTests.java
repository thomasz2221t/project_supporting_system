package pl.polsl.projectmanagementsystem;

import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.aws.messaging.listener.SimpleMessageListenerContainer;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
@SpringBootTest
@ActiveProfiles("local")
@AutoConfigureMockMvc
@RunWith(MockitoJUnitRunner.class)
class ProjectManagementSystemApplicationTests {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SimpleMessageListenerContainer simpleMessageListenerContainer;

    @SneakyThrows
    @Test
    public void contextLoads() {
        mockMvc.perform(MockMvcRequestBuilders.get("/actuator/health"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

}
