package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import ru.skypro.homework.Utils.AdDtoMapper;
import ru.skypro.homework.Utils.CreateOrUpdateAdDtoMapper;
import ru.skypro.homework.Utils.ExtendedAdDtoMapper;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.exception.AdForbiddenException;
import ru.skypro.homework.exception.AdNullPointerException;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Users;
import ru.skypro.homework.repository.AdRepository;
import ru.skypro.homework.repository.UsersRepository;
import ru.skypro.homework.service.AdService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdServiceImpl implements AdService {
    private final UsersRepository usersRepository;

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
    public AdDto creatAd(CreateOrUpdateAdDto createOrUpdateAdDto, String image, String userEmail) {
        Users users = usersRepository.findByEmail(userEmail);
        Ad ad = CreateOrUpdateAdDtoMapper.INSTANCE.creatDtoToAd(createOrUpdateAdDto);
        ad.setImage(image);
        ad.setUsers(users);
        adRepository.save(ad);
        AdDto adDto = AdDtoMapper.INSTANCE.toDto(ad);
        return adDto;
    }

    @Override
    public ExtendedAdDto getExtendedAdDto(int id) {
        Ad ad = adRepository.findByPk(id);
        try {
            ExtendedAdDto extendedAdDto = ExtendedAdDtoMapper.INSTANCE.adToExtendedDto(ad);
            return extendedAdDto;
        } catch (NullPointerException e) {
            throw new AdNullPointerException(id);
        }

    }

    @Override
    public AdDto updateAd(int idPk, CreateOrUpdateAdDto createOrUpdateAdDto,String userName) {
        Ad ad = adRepository.findByPk(idPk);
        Users users = usersRepository.findByEmail(userName);
        if (users.getRole().equals(Role.ADMIN) || ad.getUsers().getId() == users.getId()) {
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
    public AdsDto getAdsDtoMe(String userName) {
        Users users = usersRepository.findByEmail(userName);
        List<Ad> ads = adRepository.findAllByUsers(users);
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(ads.size());
        adsDto.setResults(AdDtoMapper.INSTANCE.adToAdsDtoList(ads));
        return adsDto;
    }

    @Override
    public void deletedAd(int idPk, String userName){
        Ad ad = adRepository.findByPk(idPk);
        Users users = usersRepository.findByEmail(userName);
        if (users.getRole().equals(Role.ADMIN) || ad.getUsers().getId() == users.getId()) {
            try {
                adRepository.delete(ad);
            } catch (NullPointerException e) {
                throw new AdNullPointerException(idPk);
            }
        }else throw new AdForbiddenException(userName);
    }
}
