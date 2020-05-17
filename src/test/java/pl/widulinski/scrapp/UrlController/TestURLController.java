package pl.widulinski.scrapp.UrlController;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import pl.widulinski.scrapp.createExcel.CreateExcel;
import pl.widulinski.scrapp.webDriver.DriverBuilder;




import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@Slf4j
@SpringBootTest
class TestURLController extends DriverBuilder {

    @Autowired
    CreateExcel createExcel;

    @Autowired
    private MockMvc mockMvc;


    @Test
    public void urlControllerTest() throws Exception {
        mockMvc
                .perform(get("/"))
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(content().string(containsString("Select Shop")));

        mockMvc
                .perform(get("/adminMenu"))
                .andExpect(status().isOk())
                .andExpect(view().name("adminMenu"))
                .andExpect(content().string(containsString("WebShop Name")));
    }





}
