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

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
class TermekFooldalControllerTest {

    @Autowired
    private MockMvc mockMvc;
@DisplayName("Termék főoldal megjlenik")
    @Test
    void termekIndex() throws Exception {
      mockMvc.perform(
              MockMvcRequestBuilders.get("/etterem/asztal/3/KEZDOLAP")

      ).andExpect(status().isOk())
              .andExpect(content().string(containsString("ÉTEL")));
    }

    @DisplayName("Levesből típus főoldalra")
    @Test
    void levesbolTipusra() throws Exception{
    mockMvc.perform(
            MockMvcRequestBuilders.get("/etterem/asztal/3/ETEL?")
    ) .andExpect(status().isOk())
            .andExpect(content().string(containsString("LEVES")));
    }
}