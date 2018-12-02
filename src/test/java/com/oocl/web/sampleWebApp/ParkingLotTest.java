package com.oocl.web.sampleWebApp;

import com.oocl.web.sampleWebApp.domain.ParkingLot;
import com.oocl.web.sampleWebApp.domain.ParkingLotRepository;
import com.oocl.web.sampleWebApp.models.ParkingLotResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static com.oocl.web.sampleWebApp.WebTestUtil.getContentAsObject;
import static junit.framework.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParkingLotTest {
    @Autowired
    private ParkingLotRepository parkingLotRepository;

    @Autowired
    private MockMvc mvc;

    @Test
    public void test_get_parking_lots() throws Exception{
        //g
        parkingLotRepository.deleteAll();
        parkingLotRepository.save(new ParkingLot("Test1", 1));
        //w
        MvcResult result = mvc.perform(get("/parkinglots")).andReturn();

        //t
        assertEquals(200, result.getResponse().getStatus());
        assertEquals("Test1", getContentAsObject(result, ParkingLotResponse[].class)[0].getParkingLotId());
        assertEquals(1, getContentAsObject(result, ParkingLotResponse[].class)[0].getCapacity());

    }

}
