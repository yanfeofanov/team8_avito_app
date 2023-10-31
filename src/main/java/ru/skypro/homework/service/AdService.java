package ru.skypro.homework.service;

import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.model.Ad;

import java.util.List;

public interface AdService {

    AdsDto getAllAds();

    Ad creatAd(CreateOrUpdateAdDto createOrUpdateAdDto, String image);
}
