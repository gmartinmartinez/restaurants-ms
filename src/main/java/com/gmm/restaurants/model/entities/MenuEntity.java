package com.gmm.restaurants.model.entities;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name = "MENU", schema = "apl_restaurants")
public class MenuEntity {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "description")
    private String description;

    @Column(name = "availability")
    private String availability;

    @ManyToMany
    @Fetch(FetchMode.SUBSELECT)
    @JoinTable(name = "DISH_IN_MENU", schema = "apl_restaurants",
        joinColumns = @JoinColumn(name = "menu_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "dish_id", referencedColumnName = "id"))
    private List<DishEntity> dishes;
}
