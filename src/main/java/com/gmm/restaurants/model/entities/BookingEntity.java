package com.gmm.restaurants.model.entities;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "BOOKING", schema = "apl_restaurants")
@GenericGenerator(name = "BookingSequence", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
parameters = {
    @Parameter(name = "sequence_name", value = "apl_restaurants.booking_id_seq"),
    @Parameter(name = "increment_size", value = "1")
})
public class BookingEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "restaurant")
    private Integer restaurant;

    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    @Column(name = "booking_date")
    private LocalDateTime bookingDate;

    @Column(name = "people_number")
    private Integer peopleNumber;

    @Column(name = "name")
    private String name;

    @Column(name = "phone")
    private String phone;

    @Column(name = "email")
    private String email;

    @Column(name = "comments")
    private String comments;

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "restaurant", referencedColumnName = "id", insertable = false, updatable = false)
    private RestaurantEntity restaurantEntity;


}
