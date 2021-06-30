package com.gmm.restaurants.model.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "RESTAURANT", schema = "apl_restaurants")
public class RestaurantEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name")
    private String name;

    @Column(name = "address")
    private String address;

    @Column(name = "zipcode")
    private String zipcode;

    @Column(name = "city")
    private String city;

    @Column(name = "province")
    private String province;

    @Column(name = "country")
    private String country;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "website")
    private String website;

    @Column(name = "description")
    private String description;

    @Column(name = "food_type")
    private String foodType;

    @Column(name = "average_amount")
    private Double averageAmount;

    @Column(name = "user_role")
    private String role;

    @Column(name = "user_name")
    private String user;

    @OneToMany(mappedBy = "restaurantEntity", fetch = FetchType.LAZY, targetEntity = ReviewEntity.class)
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("id ASC")
    private List<ReviewEntity> reviews;

    @OneToMany(mappedBy = "restaurantEntity", fetch = FetchType.LAZY, targetEntity = BookingEntity.class)
    @Fetch(FetchMode.SUBSELECT)
    @OrderBy("id ASC")
    private List<BookingEntity> bookings;

    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "MENU_IN_RESTAURANT", schema = "apl_restaurants",
            joinColumns = @JoinColumn(name = "restaurant_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"))
    private List<MenuEntity> menus;

}
