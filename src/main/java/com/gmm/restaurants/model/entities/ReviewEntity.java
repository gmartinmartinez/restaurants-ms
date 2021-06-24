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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Table(name = "REVIEW", schema = "apl_restaurants")
@GenericGenerator(name = "ReviewSequence", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "apl_restaurants.review_id_seq"),
        @Parameter(name = "start_value", value = "0"),
        @Parameter(name = "increment_size", value = "1")
    })
public class ReviewEntity {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "restaurant")
    private Integer restaurant;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name = "restaurant", referencedColumnName = "id", insertable = false, updatable = false)
    private RestaurantEntity restaurantEntity;


}
