package ru.skypro.homework.controller;

import org.junit.jupiter.api.Test;
import org.mockito.internal.verification.Times;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.service.impl.AdServiceImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AdsController.class)

class AdsControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AdServiceImpl service;
    @MockBean
    private SecurityContextHolder securityContextHolder;
    @MockBean
    private Authentication authentication;

    AdDto adDtoTest1 = new AdDto(1, "image1", 1, 100, "тестовое объявление 1");
    AdDto adDtoTest2 = new AdDto(1, "image2", 2, 200, "тестовое объявление 2");
    AdDto adDtoTest3 = new AdDto(2, "image3", 3, 300, "тестовое объявление 3");

    @Test
    void getAllAds() throws Exception {
        AdsDto adsDtoTest = new AdsDto(3, List.of(adDtoTest1, adDtoTest2, adDtoTest3));
        when(service.getAllAds()).thenReturn(adsDtoTest);
        mockMvc.perform(MockMvcRequestBuilders.get("/ads"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.count").value(adsDtoTest.getCount()))
                .andExpect(jsonPath("$.results[0].author").value(adDtoTest1.getAuthor()));
        verify(service, new Times(1)).getAllAds();

    }

    @Test
    void addAd() {
    }

    @Test
    void getAds() {
    }

    @Test
    void removeAd() {
    }

    @Test
    void updateAds() {
    }

    @Test
    void getAdsMe() {
    }

    @Test
    void updateImage() {
    }

    @Test
    void serveFile() {
    }
}