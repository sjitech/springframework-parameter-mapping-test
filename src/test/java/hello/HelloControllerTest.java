package hello;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockServletContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import static hello.HelloController.*;
import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = MockServletContext.class)
@WebAppConfiguration
public class HelloControllerTest {

    private MockMvc mvc;

    @Before
    public void setUp() {
        try {
            mvc = MockMvcBuilders.standaloneSetup(new HelloController()).build();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }

    @Test
    public void serveLocalDateTimeWithCustomFormat() throws Exception {
        String val = LocalDateTime.now().format(DateTimeFormatter.ofPattern(localDateTimePattern));
        mvc.perform(MockMvcRequestBuilders.get("/serveLocalDateTimeWithCustomFormatAndDefaultValue")
                .param("v", val)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(val.toString())));
    }

    @Test
    public void serveLocalDateTimeDefaultValue() throws Exception {
        LocalDateTime val = localDateTime;
        mvc.perform(MockMvcRequestBuilders.get("/serveLocalDateTimeWithCustomFormatAndDefaultValue")
//                .param("v", s)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(val.toString())));
    }

    @Test
    public void serveXxxEnum() throws Exception {
        XxxEnum val = XxxEnum.enum1;
        mvc.perform(MockMvcRequestBuilders.get("/serveXxxEnumWithDefaultValue")
                .param("v", val.toString())
        )
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(val.toString())));
    }

    @Test
    public void serveXxxEnumDefaultValue() throws Exception {
        XxxEnum val = defaultXxxEnum;
        mvc.perform(MockMvcRequestBuilders.get("/serveXxxEnumWithDefaultValue")
//				.param("v", s)
        )
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(val.toString())));
    }

    @Test
    public void serveObjectWithCustomDateTimeFormat() throws Exception {
        mvc.perform(MockMvcRequestBuilders.post("/serveObjectWithCustomDateTimeFormat")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"ldt\":\"" + localDateTimeStr + "\",\"innerClassInstance\":{\"ldt\":\"" + localDateTimeStr + "\"}}")
        )
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(localDateTime.toString() + " " + localDateTime.toString())));
    }

    @Test
    public void serveObjectWithHyphenNameProperty() throws Exception {
        Long val = 123L;
        mvc.perform(MockMvcRequestBuilders.post("/serveObjectWithHyphenNameProperty")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"hyphen-name\":" + val + "}")
        )
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo(val.toString())));
    }
}
