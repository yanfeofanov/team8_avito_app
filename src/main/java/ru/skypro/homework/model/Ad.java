package ru.skypro.homework.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "ad")
public class Ad {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int pk;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    private int price;
    private String title;
    private String image;
    private String description;
}
