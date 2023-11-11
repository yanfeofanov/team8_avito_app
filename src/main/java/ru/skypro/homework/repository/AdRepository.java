package ru.skypro.homework.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.model.Users;

import java.util.List;

@Repository
public interface AdRepository extends JpaRepository<Ad, Integer> {

    Ad findByPk(int adId);

    void deleteAdByPk(int adId);

    List<Ad> findAllByUser(Users user);
}


