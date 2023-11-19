package ru.skypro.homework.service;

import org.springframework.web.multipart.MultipartFile;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;

public interface AdService {

    AdsDto getAllAds();

    AdDto creatAd(CreateOrUpdateAdDto createOrUpdateAdDto, MultipartFile image, String userEmail);

    ExtendedAdDto getExtendedAdDto(int idPk);

    AdDto updateAd(int idPk, CreateOrUpdateAdDto createOrUpdateAdDto, String userName);

    AdsDto getAdsDtoMe(String userName);

    void deletedAd(int idPk, String userName);

    void uploadImage(int id, MultipartFile image, String userEmail);

    byte[] getAdImage(String filename);

    String getFilePath();
}
