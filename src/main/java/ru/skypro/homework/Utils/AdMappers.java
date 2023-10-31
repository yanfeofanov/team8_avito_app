package ru.skypro.homework.Utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDto;
import ru.skypro.homework.dto.AdsDto;
import ru.skypro.homework.dto.CreateOrUpdateAdDto;
import ru.skypro.homework.dto.ExtendedAdDto;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.repository.AdRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdMappers {

    @Autowired
    private AdRepository adRepository;

    public AdDto mapToAdDto(Ad ad) {
        AdDto adDto = new AdDto();
        adDto.setPk(ad.getPk());
        adDto.setTitle(ad.getTitle());
        adDto.setPrice(ad.getPrice());
        adDto.setAuthor(ad.getUsers().getId());
        adDto.setImage(ad.getImage());
        return adDto;
    }

    public ExtendedAdDto mapToExtendedAdDto(int id) {
        Ad ad = adRepository.findByPk(id);
        ExtendedAdDto extendedAdDto = new ExtendedAdDto();
        extendedAdDto.setPk(ad.getPk());
        extendedAdDto.setAuthorFirstName(ad.getUsers().getFirstName());
        extendedAdDto.setAuthorLastName(ad.getUsers().getLastName());
        extendedAdDto.setDescription(ad.getDescription());
        extendedAdDto.setImage(ad.getImage());
        extendedAdDto.setEmail(ad.getUsers().getEmail());
        extendedAdDto.setPhone(ad.getUsers().getPhone());
        extendedAdDto.setTitle(ad.getTitle());
        extendedAdDto.setPrice(ad.getPrice());
        return extendedAdDto;
    }
    public AdsDto mapToAdsDto() {
        List<Ad> adDtoList = adRepository.findAll();
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(adDtoList.size());
        adsDto.setResults(adDtoList.stream().map(this::mapToAdDto).collect(Collectors.toList()));
        return adsDto;
    }

    public Ad mapToAd(CreateOrUpdateAdDto createOrUpdateAdDto, int id) {
        Ad ad = adRepository.findByPk(id);
        ad.setDescription(createOrUpdateAdDto.getDescription());
        ad.setPrice(createOrUpdateAdDto.getPrice());
        ad.setTitle(createOrUpdateAdDto.getTitle());
        return ad;
    }

    public Ad mapToAd(CreateOrUpdateAdDto createOrUpdateAdDto) {
        Ad ad = new Ad();
        ad.setTitle(createOrUpdateAdDto.getTitle());
        ad.setDescription(createOrUpdateAdDto.getDescription());
        ad.setPrice(createOrUpdateAdDto.getPrice());
        return ad;
    }
}
