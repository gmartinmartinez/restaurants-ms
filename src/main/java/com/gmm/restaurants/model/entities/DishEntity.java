package com.gmm.restaurants.model.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "DISH", schema = "apl_restaurants")
@GenericGenerator(name = "DishSequence", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "apl_restaurants.dish_id_seq"),
        @Parameter(name = "start_value", value = "0"),
        @Parameter(name = "increment_size", value = "1")
    })
public class DishEntity {

    @Id
    @GeneratedValue(generator = "DishSequence")
    @Column(name = "id")
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    @Column(name = "is_vegetarian")
    private Boolean isVegetarian;

}
