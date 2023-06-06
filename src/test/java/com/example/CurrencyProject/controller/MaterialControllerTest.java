package com.example.CurrencyProject.controller;

import com.example.CurrencyProject.scraper.material.MaterialScrapper;
import com.example.CurrencyProject.scraper.metal.MetalScrapper;
import com.example.CurrencyProject.scraper.metal.enums.Metal;
import com.example.CurrencyProject.service.MaterialFactory;
import com.example.CurrencyProject.service.MaterialService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.Mockito.when;

@WebMvcTest(MaterialController.class)
@WithMockUser
class MaterialControllerTest {

    @MockBean
    private MaterialService materialService;

    @MockBean
    private MetalScrapper metalScrapper;

    @MockBean
    private MaterialScrapper materialScrapper;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MaterialController materialController;

    MaterialFactory materialFactory = new MaterialFactory();

    @Test
    void getTest() {

    }

    @Test
    void getAllMaterials() throws Exception {

        // given
        when(materialService.getAllMaterials()).thenReturn(materialFactory.createAllMaterials());
        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/material"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }




    @Test
    void getMaterialForDaysShouldThrowBadRequest() throws Exception {
        // given

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/material/days/badRequest/10"))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    void getMaterialForDaysShouldGiveSilver() throws Exception {
        // given


        // then
       MvcResult mvcResult= mockMvc.perform(MockMvcRequestBuilders.get("/material/days/silver/30"))
                .andExpect(MockMvcResultMatchers.status().isOk()).andReturn();

       mvcResult.getResponse();

    }

    @Test
    void getMaterialForDaysShouldGiveRunTimeException() throws Exception {
        // given


        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/material/days/silver/20"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getMaterialForDays() throws Exception {

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/material/days/ZLOTO/30"))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }
}