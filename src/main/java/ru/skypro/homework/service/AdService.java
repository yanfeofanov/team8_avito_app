package ru.skypro.homework.service;

import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.model.Ad;

public interface AdService {

    AdsDto getAllAds();

    AdDto creatAd(CreateOrUpdateAdDto createOrUpdateAdDto, String image, String userEmail);

    ExtendedAdDto getExtendedAdDto(int idPk);

    AdDto updateAd(int idPk, CreateOrUpdateAdDto createOrUpdateAdDto, String userName);

    AdsDto getAdsDtoMe(String userName);

    void deletedAd(int idPk, String userName);
}
