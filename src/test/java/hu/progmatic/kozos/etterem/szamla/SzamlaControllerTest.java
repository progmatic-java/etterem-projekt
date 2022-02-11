package hu.progmatic.kozos.etterem.szamla;

import org.junit.jupiter.api.Disabled;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@WithMockUser
@AutoConfigureMockMvc
class SzamlaControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Számla oldal megjelenik")
  void szamlaTest() throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.get("/etterem/asztal/1/szamla?")
        ).andExpect(status().isOk())
        .andExpect(content().string(containsString("Név")))
        .andExpect(content().string(containsString("Mennyiség")))
        .andExpect(content().string(containsString("Ár")));
  }
}