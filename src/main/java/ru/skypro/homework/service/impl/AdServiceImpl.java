package ru.skypro.homework.service.impl;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import org.webjars.NotFoundException;
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
import ru.skypro.homework.service.CommentService;

import java.nio.file.Paths;
import java.util.List;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AdServiceImpl implements AdService {

    private final Logger logger = LoggerFactory.getLogger(AdServiceImpl.class);
    private final UserDetailsService userDetailsService;
    private final AdRepository adRepository;
    private final CommentService commentService;
    private final AdDtoMapper adDtoMapper;
    private final ExtendedAdDtoMapper extendedAdDtoMapper;
    private final CreateOrUpdateAdDtoMapper createOrUpdateAdDtoMapper;
    @Value("${file.path.image}")
    private String filePath;

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
     * @param image               картинка объявления
     * @param userEmail           login пользователя
     * @return AdDto
     */
    @Override
    //@PreAuthorize("hasRole('USER')|| hasRole('ADMIN')")
    public AdDto creatAd(CreateOrUpdateAdDto createOrUpdateAdDto, MultipartFile image, String userEmail) {
        AvitoUser user = (AvitoUser) userDetailsService.loadUserByUsername(userEmail);
        Ad ad = createOrUpdateAdDtoMapper.creatDtoToAd(createOrUpdateAdDto);
        String imageName = uploadImageOnSystem(image, userEmail);
        ad.setImage(getUrlImage(imageName));
        ad.setUser(user);
        adRepository.save(ad);
        logger.info("добавлено новое объявление: " + ad, ad);
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
        } catch (NotFoundException e) {
            throw new AdNotFoundException(id);
        }
    }

    /**
     * Метод обновляет объявление
     *
     * @param idPk                id объявления
     * @param createOrUpdateAdDto title,price,description
     * @param userName            login пользователя
     * @return AdDto
     */

    @Override
    public AdDto updateAd(int idPk, CreateOrUpdateAdDto createOrUpdateAdDto, String userName) {
        AvitoUser user = (AvitoUser) userDetailsService.loadUserByUsername(userName);
        Ad ad = adRepository.findByPk(idPk);
        if (ad == null) {
            logger.warn("не найдено объявление id=" + idPk);
            throw new AdNotFoundException(idPk);
        }
        if (user.getRole().equals(Role.ADMIN) || ad.getUser().getId() == user.getId()) {
            ad.setTitle(createOrUpdateAdDto.getTitle());
            ad.setPrice(createOrUpdateAdDto.getPrice());
            ad.setDescription(createOrUpdateAdDto.getDescription());
            adRepository.save(ad);
            logger.info("внесены изменения в объявление id=" + ad.getPk(), ad);
            return adDtoMapper.toDto(ad);
        } else {
            logger.warn("у пользователя " + userName + " не достаточно прав для удаления объявления id=" + ad.getPk(), ad);
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
     * @param idPk     id объявления
     * @param userName login пользователя
     */
    @Override
    public void deletedAd(int idPk, String userName) {
        AvitoUser user = (AvitoUser) userDetailsService.loadUserByUsername(userName);
        Ad ad = adRepository.findByPk(idPk);
        if (ad == null) {
            logger.warn("не найдено объявление id=" + idPk);
            throw new AdNotFoundException(idPk);
        }
        if (user.getRole().equals(Role.ADMIN) || ad.getUser().getId() == user.getId()) {
            try {
                Files.delete(Path.of(System.getProperty("user.dir") + "/" + filePath + ad.getImage().replaceAll("/ads/get", "")));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            commentService.deleteAllCommentByPk(idPk);
            adRepository.delete(ad);
            logger.info("удалено объявление id=" + ad.getPk(), ad);
        } else {
            logger.warn("у пользователя " + userName + " не достаточно прав для удаления объявления id=" + ad.getPk(), ad);
            throw new ForbiddenException(userName);
        }
    }

    /**
     * Метод обновления картинки по объявлению
     *
     * @param id        уникальный иденктификатор объявления
     * @param image     файл изображения для объявления
     * @param userEmail никнейм пользователя
     */
    @Override
    public void uploadImage(int id, MultipartFile image, String userEmail) {
        AvitoUser user = (AvitoUser) userDetailsService.loadUserByUsername(userEmail);
        Ad ad = adRepository.findByPk(id);
        if (ad == null) {
            logger.warn("не найдено объявление id=" + id);
            throw new AdNotFoundException(id);
        }
        try {
            Files.delete(Path.of(System.getProperty("user.dir") + "/" + filePath + ad.getImage().replaceAll("/ads/get", "")));
        } catch (IOException e) {
            logger.error("произошла ошибка при попытке удаления изображения к объявлению id=" + id, ad);
            throw new RuntimeException(e);
        }
        String imageName = uploadImageOnSystem(image, userEmail);
        ad.setImage(getUrlImage(imageName));
        ad.setUser(user);
        adRepository.save(ad);
        logger.info("у объявления id=" + ad.getPk() + " обновлено изображение", ad);
    }


    /**
     * Метод указывает расширение файла
     *
     * @return String
     */
    private String getExtension(String fileName) {
        return StringUtils.getFilenameExtension(fileName);
    }

    /**
     * Метод создает название файла
     *
     * @param image    картинка объявления
     * @param userName login пользователя
     * @return String
     */
    private String getFileName(String userName, MultipartFile image) {
        return String.format("image%s_%s.%s", userName, UUID.randomUUID(), getExtension(image.getOriginalFilename()));
    }

    /**
     * Метод создает Url файла
     *
     * @param fileName название картинки объявления
     * @return String
     */
    private String getUrlImage(String fileName) {
        return "/ads/get/" + fileName;
    }

    /**
     * Метод загружаем файл
     *
     * @param image     файл
     * @param userEmail имя пользователя
     * @return String имя загруженного файла
     */
    private String uploadImageOnSystem(MultipartFile image, String userEmail) {
        String dir = System.getProperty("user.dir") + "/" + filePath;
        String imageName = getFileName(userEmail, image);
        try {
            Files.createDirectories(Path.of(dir));
            image.transferTo(new File(dir + "/" + imageName));
        } catch (IOException e) {
            logger.error("произошла ошибка при попытке сохранить изображение " + image.getOriginalFilename() + " для объявления на сервер", image);
            throw new RuntimeException(e);
        }
        logger.info("изображение " + imageName + " сохранено на сервере", image);
        return imageName;
    }

    /**
     * Метод получчает массив байтов
     *
     * @param filename имя картинки
     * @return byte[]
     */

    @Override
    public byte[] getAdImage(String filename) {
        try {
            return Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/" + filePath + "/" + filename));
        } catch (IOException e) {
            logger.error("произошла ошибка при попытке прочитать изображение для объявления" + filename);
            throw new RuntimeException(e);
        }
    }
}

