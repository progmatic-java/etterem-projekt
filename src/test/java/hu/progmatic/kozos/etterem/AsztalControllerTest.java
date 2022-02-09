package hu.progmatic.kozos.etterem;

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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WithMockUser
@SpringBootTest
@AutoConfigureMockMvc
class AsztalControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Test
  @DisplayName("Asztal főoldal megjelenik")
  void asztalIndex() throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.get("/etterem/asztal/2/KEZDOLAP")
        ).andExpect(status().isOk())
        .andExpect(content().string(containsString("ÉTEL")));
  }

  @Test
  @DisplayName("Étel oldal megjelenik")
  void etelek() throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.get("/etterem/asztal/2/ETEL?")
        ).andExpect(status().isOk())
        .andExpect(content().string(containsString("LEVES")));
  }

  @Test
  @DisplayName("Ital oldal megjelenik")
  void italok() throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.get("/etterem/asztal/2/ITAL?")
        ).andExpect(status().isOk())
        .andExpect(content().string(containsString("RÖVIDITAL")));
  }

  @Test
  @DisplayName("Számla oldal megjelenik")
  @Disabled
  void szamla() throws Exception {
    mockMvc.perform(
            MockMvcRequestBuilders.get("/etterem/asztal/6/szamla?")
        ).andExpect(status().isOk())
        .andExpect(content().string(containsString("Számla")));
  }

  @Test
  @DisplayName("Termék hozzáadása a rendeléshez")
  @Disabled
  void hozzadas() throws Exception {
    mockMvc.perform(
            post("/etterem/asztal/1/ETEL/tipus/LEVES")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .content("etteremTermekId=10&mennyiseg=1")
        ).andDo(print()).andExpect(status().isOk())
        .andExpect(content().string(containsString("Paradicsom leves")));
  }
}
