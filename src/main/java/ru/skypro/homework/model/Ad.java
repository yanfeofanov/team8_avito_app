package ru.skypro.homework.model;

import lombok.*;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ad ad = (Ad) o;
        return pk == ad.pk;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pk);
    }
}
