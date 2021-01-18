package com.myhotel.contract;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest
@RunWith(SpringRunner.class)
public class HotelBookingControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    public void maleGreetingTest200Ok() throws Exception {
       /* String name = "Rajan";
        String gender = "male";

        mockMvc.perform(
                get("/api/greeting")
                        .param("name", name)
                        .param("gender", gender)
        ).andExpect(
                status().isOk()
        ).andExpect(
                content().string(containsString(
                        String.format("hotel has been book succsfully")
                ))
        );*/


    }


}
