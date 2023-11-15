package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.skypro.homework.Utils.AdDtoMapper;
import ru.skypro.homework.Utils.CreateOrUpdateAdDtoMapper;
import ru.skypro.homework.Utils.ExtendedAdDtoMapper;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.exception.ForbiddenException;
import ru.skypro.homework.exception.AdNotFoundException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.AvitoUser;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.service.AdService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdServiceImpl implements AdService {

    private final UserDetailsService userDetailsService;
    private final AdRepository adRepository;
    private final AdDtoMapper adDtoMapper;
    private final ExtendedAdDtoMapper extendedAdDtoMapper;
    private final CreateOrUpdateAdDtoMapper createOrUpdateAdDtoMapper;

    /**
     * Метод выводит AdsDto (кол-во объявлений и все объявления)
     *
     * @return AdsDto
     */
    @Override
    public AdsDto getAllAds() {
        List<Ad> adList = adRepository.findAll();
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(adList.size());
        adsDto.setResults(adDtoMapper.adToAdsDtoList(adList));
        return adsDto;
    }

    /**
     * Метод создает объявление
     *
     * @param createOrUpdateAdDto title,price,description
     * @param image картинка объявления
     * @param userEmail login пользователя
     * @return AdDto
     */
    @Override
    //@PreAuthorize("hasRole('USER')")
    public AdDto creatAd(CreateOrUpdateAdDto createOrUpdateAdDto, String image, String userEmail) {
        AvitoUser user = (AvitoUser) userDetailsService.loadUserByUsername(userEmail);
        Ad ad = createOrUpdateAdDtoMapper.creatDtoToAd(createOrUpdateAdDto);
        ad.setImage(image);
        ad.setUser(user);
        adRepository.save(ad);
        return adDtoMapper.toDto(ad);
    }

    /**
     * Метод выдает информацию по объявлению
     *
     * @param id id объявления
     * @return ExtendedAdDto
     */
    @Override
    //@PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public ExtendedAdDto getExtendedAdDto(int id) {
        Ad ad = adRepository.findByPk(id);
        try {
            return extendedAdDtoMapper.adToExtendedDto(ad);
        } catch (NullPointerException e) {
            throw new AdNotFoundException(id);
        }
    }

    /**
     * Метод обновляет объявление
     *
     * @param idPk id объявления
     * @param createOrUpdateAdDto title,price,description
     * @param userName login пользователя
     * @return AdDto
     */
    @Override
    public AdDto updateAd(int idPk, CreateOrUpdateAdDto createOrUpdateAdDto, String userName) {
        AvitoUser user = (AvitoUser) userDetailsService.loadUserByUsername(userName);
        Ad ad = adRepository.findByPk(idPk);
        if (ad == null) {
            throw new AdNotFoundException(idPk);
        }
        if (user.getRole().equals(Role.ADMIN) || ad.getUser().getId() == user.getId()) {
            ad.setTitle(createOrUpdateAdDto.getTitle());
            ad.setPrice(createOrUpdateAdDto.getPrice());
            ad.setDescription(createOrUpdateAdDto.getDescription());
            adRepository.save(ad);
            return adDtoMapper.toDto(ad);
        } else {
            throw new ForbiddenException(userName);
        }
    }
    /**
     * Метод выводит AdsDto (кол-во объявлений и все объявления пользователя)
     *
     * @param userName login пользователя
     * @return AdsDto
     */
    @Override
    //@PreAuthorize("hasRole('USER') || hasRole('ADMIN')")
    public AdsDto getAdsDtoMe(String userName) {
        AvitoUser user = (AvitoUser) userDetailsService.loadUserByUsername(userName);
        List<Ad> ads = adRepository.findAllByUser(user);
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(ads.size());
        adsDto.setResults(adDtoMapper.adToAdsDtoList(ads));
        return adsDto;
    }

    /**
     * Метод удаляет объявление(может удалять Admin или создатель объявления)
     *
     * @param idPk id объявления
     * @param userName login пользователя
     * @return void
     */
    @Override
    public void deletedAd(int idPk, String userName) {
        AvitoUser user = (AvitoUser) userDetailsService.loadUserByUsername(userName);
        Ad ad = adRepository.findByPk(idPk);
        if (ad == null) {
            throw new AdNotFoundException(idPk);
        }
        if (user.getRole().equals(Role.ADMIN) || ad.getUser().getId() == user.getId()) {
            adRepository.delete(ad);
        } else {
            throw new ForbiddenException(userName);
        }
    }
}
