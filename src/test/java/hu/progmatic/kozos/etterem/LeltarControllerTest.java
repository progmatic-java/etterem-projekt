package hu.progmatic.kozos.etterem;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
public class LeltarControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Leltár oldal megjelenik")
    void etelek() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/etterem/leltar?")
                ).andExpect(status().isOk())
                .andExpect(content().string(containsString("Leltár")));
    }

    @Test
    @DisplayName("Termék szerkesztése")
    void szerkesztes() throws Exception {
        mockMvc.perform(
                        MockMvcRequestBuilders.get("/etterem/leltar/10?")
                ).andExpect(status().isOk())
                .andExpect(content().string(containsString("Paradicsom leves")));
    }
}
