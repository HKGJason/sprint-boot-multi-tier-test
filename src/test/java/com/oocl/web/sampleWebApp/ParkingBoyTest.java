package com.oocl.web.sampleWebApp;

import com.oocl.web.sampleWebApp.domain.ParkingBoy;
import com.oocl.web.sampleWebApp.domain.ParkingBoyRepository;
import com.oocl.web.sampleWebApp.domain.ParkingLot;
import com.oocl.web.sampleWebApp.domain.ParkingLotRepository;
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
    private ParkingLotRepository parkingLotRepository;

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
    public void parking_boy_get_not_exist() throws Exception{
        //w t
        mvc.perform(get("parkingboys/notexist")).andExpect(status().isBadRequest());
    }

    @Test
    public void add_new_parking_boy_test() throws Exception{
        //given
        parkingBoyRepository.deleteAll();
        String boyJson = "{\"employeeId\":"+123+"}";
        //when
        mvc.perform(post("/parkingboys").contentType(MediaType.APPLICATION_JSON)
         //t
                .content(boyJson)).andExpect(status().isCreated());


    }
    @Test
    public void parking_boy_ID_exceed_maximum_post_fail_test() throws Exception{
        //g
        parkingBoyRepository.deleteAll();
        String idtooLong = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
        String boyJson = "{\"employeeId\":"+idtooLong+"}";
        //w
        mvc.perform(post("/parkingboys").contentType(MediaType.APPLICATION_JSON).content(boyJson))
        //t
               .andExpect(status().isBadRequest());
    }
    @Test
    public void parking_boy_ID_not_unique_post_fail_test() throws Exception{
        //g
        parkingBoyRepository.deleteAll();
        ParkingBoy boy = parkingBoyRepository.save(new ParkingBoy("Test1"));
        parkingBoyRepository.flush();
        //w
        String boyJson = "{\"employeeId\":"+"Test1"+"}";

        mvc.perform(post("/parkingboys").contentType(MediaType.APPLICATION_JSON).content(boyJson))
                //t
                .andExpect(status().isBadRequest());
    }

    @Test
    public void get_parking_boy_by_parking_lot_test() throws Exception{
        //g
        ParkingBoy boy = parkingBoyRepository.save(new ParkingBoy("TestBoy"));
        ParkingLot lot = parkingLotRepository.save(new ParkingLot("TestLot", 50));
        parkingBoyRepository.flush();
        parkingLotRepository.flush();
        lot.setBoy(boy);
        //w
        MvcResult result = mvc.perform(get("/parkingboys/"+boy.getId()+"/parkinglots")).andReturn();
        //t
        ParkingBoyLotAssocationResponse response

    }
}
