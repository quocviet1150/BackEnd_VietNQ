package com.example.cafe.Entity;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Bill.getAllBills",query = "select b from Bill b order by b.id desc ")
@NamedQuery(name = "Bill.getBillByUserName",query = "select b from Bill b where b.createdBy=:username order by b.id desc ")

@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "bill")
public class Bill implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "uuid")
    private String uuid;

    @Column(name = "email")
    private String email;

    @Column(name = "contactNumber")
    private String contactNumber;

    @Column(name = "paymentMethod")
    private String paymentMethod;

    @Column(name = "total")
    private Integer total;

    @Column(name = "productDetails", columnDefinition = "json")
    private String productDetail;

    @Column(name = "createdby")
    private String createdBy;

    public Bill() {

    }

    public Bill(Integer id, String name, String uuid, String email, String contactNumber, String paymentMethod, Integer total, String productDetail, String createdBy) {
        this.id = id;
        this.name = name;
        this.uuid = uuid;
        this.email = email;
        this.contactNumber = contactNumber;
        this.paymentMethod = paymentMethod;
        this.total = total;
        this.productDetail = productDetail;
        this.createdBy = createdBy;
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

    public String getProductDetail() {
        return productDetail;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setProductDetail(String productDetail) {
        this.productDetail = productDetail;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(String createdBy) {
        this.createdBy = createdBy;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }
}
