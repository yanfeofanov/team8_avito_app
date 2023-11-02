package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
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
import ru.skypro.homework.repository.UsersRepository;
import ru.skypro.homework.service.AdService;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AdServiceImpl implements AdService {
    private final UsersRepository usersRepository;

    private final AdRepository adRepository;

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
        adsDto.setResults(AdDtoMapper.INSTANCE.adToAdsDtoList(adList));
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
    public AdDto creatAd(CreateOrUpdateAdDto createOrUpdateAdDto, String image, String userEmail) {
        Users users = usersRepository.findByEmail(userEmail);
        Ad ad = CreateOrUpdateAdDtoMapper.INSTANCE.creatDtoToAd(createOrUpdateAdDto);
        ad.setImage(image);
        ad.setUsers(users);
        adRepository.save(ad);
        AdDto adDto = AdDtoMapper.INSTANCE.toDto(ad);
        return adDto;
    }

    /**
     * Метод выдает информацию по объявлению
     *
     * @param id id объявления
     * @return ExtendedAdDto
     */
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

    /**
     * Метод обновляет объявление
     *
     * @param idPk id объявления
     * @param createOrUpdateAdDto title,price,description
     * @param userName login пользователя
     * @return AdDto
     */
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
    /**
     * Метод выводит AdsDto (кол-во объявлений и все объявления пользователя)
     *
     * @param userName login пользователя
     * @return AdsDto
     */
    @Override
    public AdsDto getAdsDtoMe(String userName) {
        Users users = usersRepository.findByEmail(userName);
        List<Ad> ads = adRepository.findAllByUsers(users);
        AdsDto adsDto = new AdsDto();
        adsDto.setCount(ads.size());
        adsDto.setResults(AdDtoMapper.INSTANCE.adToAdsDtoList(ads));
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
