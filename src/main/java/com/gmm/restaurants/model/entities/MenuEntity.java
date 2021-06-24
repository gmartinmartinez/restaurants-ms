package com.gmm.restaurants.model.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
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
@Table(name = "MENU", schema = "apl_restaurants")
@GenericGenerator(name = "MenuSequence", strategy = "org.hibernate.id.enhanced.SequenceStyleGenerator",
    parameters = {
        @Parameter(name = "sequence_name", value = "apl_restaurants.menu_id_seq"),
        @Parameter(name = "start_value", value = "0"),
        @Parameter(name = "increment_size", value = "1")
    })
public class MenuEntity {

    @Id
    @GeneratedValue(generator = "MenuSequence")
    @Column(name = "id")
    private Integer id;

    @Column(name = "description")
    private String description;

    @Column(name = "availability")
    private String availability;

    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "DISH_IN_MENU", schema = "apl_restaurants",
        joinColumns = @JoinColumn(name = "dish_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"))
    private List<DishEntity> dishes;
}
