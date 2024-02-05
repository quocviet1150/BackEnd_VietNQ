package com.example.cafe.Entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Category.getAllCategory", query = "SELECT c FROM Category c where c.id in (select p.category " +
        "from Product p where p.status = 'true') order by c.id desc")

@NamedQuery(
        name = "Category.getAllCategoryStatus",
        query = "SELECT c FROM Category c " +
                "where c.id in (select p.category.id from Product p where p.status = 'true') " +
                "and c.status = 'true' " +
                "order by c.id desc"
)

@NamedQuery(name = "Category.updateStatus", query = "update Category c set c.status=:status where c.id=:id")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "category")
public class Category implements Serializable {

    private static final long serialVersionUID = 2L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "status")
    private String status;

    public Category() {

    }

}
