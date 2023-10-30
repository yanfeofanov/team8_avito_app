package ru.skypro.homework.Utils;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.skypro.homework.dto.*;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.User;
import ru.skypro.homework.repository.AdRepository;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdMapper {

    @Mock
    private AdRepository adRepository;
    @InjectMocks
    public AdMappers adMappers;
    static final User user = new User(0,"firstName","lastName", Role.USER,
            "avatar","userName","password","mail.ru","89009009090");
    static final Ad ad = new Ad(0,user,200,"title","image","description");
    static final CreateOrUpdateAdDto createOrUpdateAdDto = new CreateOrUpdateAdDto("new title",205,"new description");
    static final Ad updateAd = new Ad(0,user,205,createOrUpdateAdDto.getTitle(),"image",createOrUpdateAdDto.getDescription());
    static final AdDto adDto = new AdDto(user.getId(),"picture",ad.getPk(),ad.getPrice(),ad.getTitle());

    static final ExtendedAdDto extendedAdDto = new ExtendedAdDto(ad.getPk(),user.getFirstName(), user.getLastName()
            ,ad.getDescription(), user.getEmail(),ad.getImage(), user.getPhone(),ad.getPrice(),ad.getTitle());

    @Test
    void mapToAdsDtoTest() {

        List<Ad> listAd = List.of(ad);
        List<AdDto> listAdDto = List.of(adDto);

        AdsDto adsDto = new AdsDto();
        adsDto.setCount(1);
        adsDto.setResults(listAdDto);
        when(adRepository.findAll()).thenReturn(listAd);
        assertThat(adMappers.mapToAdsDto())
                .isNotNull()
                .isEqualTo(adsDto);
    }

    @Test
    void mapToAdCreatTest() {
        when(adRepository.findByPk(0)).thenReturn(ad);
        assertThat(adMappers.mapToAd(createOrUpdateAdDto,0))
                .isNotNull()
                .isEqualTo(updateAd);
    }

    @Test
    void mapToExtendedAdDtoTest() {
        when(adRepository.findByPk(0)).thenReturn(ad);
        assertThat(adMappers.mapToExtendedAdDto(0))
                .isNotNull()
                .isEqualTo(extendedAdDto);
    }

}
