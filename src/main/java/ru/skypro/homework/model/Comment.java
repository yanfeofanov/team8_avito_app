package ru.skypro.homework.model;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "comment")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int pk;
    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users user;
    @ManyToOne
    @JoinColumn(name = "ad_id")
    private Ad ad;
    private String text;
    @Column(name = "date_time")
    private long createdAt;
}
