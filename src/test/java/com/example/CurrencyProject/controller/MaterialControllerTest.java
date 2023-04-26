package com.example.CurrencyProject.controller;

import com.example.CurrencyProject.dto.GoldDto;
import com.example.CurrencyProject.model.Material;
import com.example.CurrencyProject.service.GoldService;
import com.example.CurrencyProject.service.MaterialService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import reactor.core.publisher.Mono;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(MaterialController.class)
@WithMockUser
class MaterialControllerTest {

    @MockBean
    private MaterialService materialService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private MaterialController materialController;

    @Test
    void getAllMaterials() throws Exception {

        // given
        List<Material> materials = Arrays.asList( Material.builder().name("gold").price(12.21)
                        .data(LocalDate.of(2002,2,12)).build());

        when(materialService.getAllMaterials()).thenReturn(materials);
        // when

        // then
        mockMvc.perform(MockMvcRequestBuilders.get("/material"))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }





}