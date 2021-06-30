package com.gmm.restaurants.model.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "REVIEW", schema = "apl_restaurants")
public class ReviewEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "restaurant")
    private String restaurant;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "sender_nick")
    private String nick;

    @Column(name = "stars")
    private Integer stars;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "user_role")
    private String role;

    @Column(name = "user_name")
    private String user;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "restaurant", referencedColumnName = "id", insertable = false, updatable = false)
    private RestaurantEntity restaurantEntity;


}
