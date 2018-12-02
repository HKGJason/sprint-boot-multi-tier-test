package com.oocl.web.sampleWebApp;

import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingBoyRepository;
import com.oocl.web.sampleWebApp.models.ParkingBoyResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityManager;

import static com.oocl.web.sampleWebApp.WebTestUtil.getContentAsObject;
import static junit.framework.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingBoyTest {
    @Autowired
    private ParkingBoyRepository parkingBoyRepository;

    @Autowired
    private MockMvc mvc;

    @Autowired
    private EntityManager entityManager;

    @Test
    public void parking_boy_get_test() throws Exception{
        //given
        parkingBoyRepository.deleteAll();
        ParkingBoy boy = parkingBoyRepository.save(new ParkingBoy("Test1"));
        parkingBoyRepository.flush();
        //when
        MvcResult result = mvc.perform(get("/parkingboys")).andReturn();
        //then
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("Test1", getContentAsObject(result, ParkingBoyResponse[].class)[0].getEmployeeId());
    }
    @Test
    public void add_new_parking_boy_test() throws Exception{
        //given
        String boyJson = "{\"employeeId\":"+1+"}";
        //when
        mvc.perform(post("/parkingboys").contentType(MediaType.APPLICATION_JSON)
                .content(boyJson)).andExpect(status().isCreated());


    }
}
