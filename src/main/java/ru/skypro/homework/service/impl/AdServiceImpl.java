package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.skypro.homework.Utils.AdDtoMapper;
import ru.skypro.homework.Utils.CreateOrUpdateAdDtoMapper;
import ru.skypro.homework.Utils.ExtendedAdDtoMapper;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.exception.AdForbiddenException;
import ru.skypro.homework.exception.AdNullPointerException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdServiceImpl implements AdService {

    private final UserDetailsService userDetailsService;
    private final AdRepository adRepository;

    @Override
    public AdsDto getAllAds() {
        List<Ad> adList = adRepository.findAll();
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(adList.size());
        adsDto.setResults(AdDtoMapper.INSTANCE.adToAdsDtoList(adList));
        return adsDto;
    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public AdDto creatAd(CreateOrUpdateAdDto createOrUpdateAdDto, String image, String userEmail) {
        Users user = (Users) userDetailsService.loadUserByUsername(userEmail);
        Ad ad = CreateOrUpdateAdDtoMapper.INSTANCE.creatDtoToAd(createOrUpdateAdDto);
        ad.setImage(image);
        ad.setUser(user);
        adRepository.save(ad);
        return AdDtoMapper.INSTANCE.toDto(ad);
    }

    @Override
    @PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public ExtendedAdDto getExtendedAdDto(int id) {
        Ad ad = adRepository.findByPk(id);
        try {
            return ExtendedAdDtoMapper.INSTANCE.adToExtendedDto(ad);
        } catch (NullPointerException e) {
            throw new AdNullPointerException(id);
        }
    }

    @Override
    public AdDto updateAd(int idPk, CreateOrUpdateAdDto createOrUpdateAdDto, String userName) {
        Ad ad = adRepository.findByPk(idPk);
        Users user = (Users) userDetailsService.loadUserByUsername(userName);
        if (user.getRole().equals(Role.ADMIN) || ad.getUser().getId() == user.getId()) {
            try {
                ad.setTitle(createOrUpdateAdDto.getTitle());
                ad.setPrice(createOrUpdateAdDto.getPrice());
                ad.setDescription(createOrUpdateAdDto.getDescription());
                adRepository.save(ad);
                return AdDtoMapper.INSTANCE.toDto(ad);
            } catch (NullPointerException e) {
                throw new AdNullPointerException(idPk);
            }
        } else throw new AdForbiddenException(userName);

    }

    @Override
    @PreAuthorize("hasRole('USER')")
    public AdsDto getAdsDtoMe(String userName) {
        Users user = (Users) userDetailsService.loadUserByUsername(userName);
        List<Ad> ads = adRepository.findAllByUser(user);
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(ads.size());
        adsDto.setResults(AdDtoMapper.INSTANCE.adToAdsDtoList(ads));
        return adsDto;
    }

    @Override
    public void deletedAd(int idPk, String userName) {
        Ad ad = adRepository.findByPk(idPk);
        Users user = (Users) userDetailsService.loadUserByUsername(userName);
        if (user.getRole().equals(Role.ADMIN) || ad.getUser().getId() == user.getId()) {
            try {
                adRepository.delete(ad);
            } catch (NullPointerException e) {
                throw new AdNullPointerException(idPk);
            }
        } else throw new AdForbiddenException(userName);
    }
}
