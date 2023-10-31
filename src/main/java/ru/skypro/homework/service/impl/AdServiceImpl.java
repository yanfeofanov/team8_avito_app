package ru.skypro.homework.service.impl;

import org.springframework.stereotype.Service;
import ru.skypro.homework.Utils.AdMapper;
import ru.skypro.homework.config.WebSecurityConfig;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;

import java.util.List;

@Service
public class AdServiceImpl implements AdService {

    private final AdRepository adRepository;

    public AdServiceImpl(AdRepository adRepository) {
        this.adRepository = adRepository;
    }


    @Override
    public AdsDto getAllAds() {
        List<Ad> adList = adRepository.findAll();
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(adList.size());
        adsDto.setResults(AdMapper.INSTANCE.adToAdsDtoList(adList));
        return adsDto;
    }

    @Override
    public Ad creatAd(CreateOrUpdateAdDto createOrUpdateAdDto, String image) {
        AdDto adDto = AdMapper.INSTANCE.creatDtoToAdDto(createOrUpdateAdDto);
        adDto.setImage(image);
        adDto.setAuthor(new Users().getId());
        Ad ad = AdMapper.INSTANCE.toAd(adDto);
        return adRepository.save(ad);
    }
}
