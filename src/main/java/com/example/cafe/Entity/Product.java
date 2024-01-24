package com.example.cafe.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NamedQuery;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NamedQuery(name = "Product.getAllProduct", query = "select new com.example.cafe.DTO.ProductDTO(p.id,p.name," +
        "p.description,p.price,p.status,p.category.id,p.category.name,p.createdDate, p.quantity_product) from Product p order by p.id desc")
@NamedQuery(name = "Product.updateStatus", query = "update Product p set p.status=:status where p.id=:id")

@NamedQuery(name = "Product.getProductByCategory", query = "select new com.example.cafe.DTO.ProductDTO(p.id," +
        " p.name) from  Product  p where p.category.id=:id and p.status='true' order by p.id desc")

@NamedQuery(name = "Product.getProductByQuantity", query = "select new com.example.cafe.DTO.ProductDTO(p.id," +
        " p.name , p.quantity_product) from  Product  p  order by p.id desc")

@NamedQuery(name = "Product.getByIdProduct", query = "select new com.example.cafe.DTO.ProductDTO(p.id,p.name," +
        "p.description,p.price) from Product p WHERE p.id=:id order by p.id desc")

@NamedQuery(name = "Product.getDateNowProduct",query = "SELECT COUNT(p.id) AS totalOrders\n" +
        "FROM Product p\n" +
        "WHERE DATE(p.createdDate) = CURRENT_DATE ")

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "product")
public class Product implements Serializable {

    private static final long serialVersionUID = 3L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_fk", nullable = false)
    private Category category;

    @Column(name = "description")
    private String description;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "price")
    private Integer price;

    @Column(name = "status")
    private String status;

    @Column(name = "quantity_product")
    private String quantity_product;

    public Product() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public String getQuantity_product() {
        return quantity_product;
    }

    public void setQuantity_product(String quantity_product) {
        this.quantity_product = quantity_product;
    }
}
