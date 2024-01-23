package com.example.cafe.Entity;

import com.fasterxml.jackson.databind.JsonNode;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NamedQuery(name = "Bill.getAllBills", query = "select b from Bill b order by b.id desc ")

@NamedQuery(name = "Bill.getBillByUserName", query = "select b from Bill b where b.createdBy=:username order by b.id desc ")

@NamedQuery(name = "Bill.sumBill", query = "SELECT SUM(b.total) FROM Bill b")

@NamedQuery(name = "Bill.getOne",query = "SELECT COUNT(b.id) AS totalOrders\n" +
        "FROM Bill b\n" +
        "WHERE DATE(b.createdDate) = CURRENT_DATE ")

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
    private String productDetails;

    @Column(name = "createdby")
    private String createdBy;

    @Column(name = "created_date")
    private Date createdDate;

    public Bill() {

    }

    public Bill(Integer id, String name, String uuid, String email, String contactNumber, String paymentMethod, Integer total, String productDetails, String createdBy) {
        this.id = id;
        this.name = name;
        this.uuid = uuid;
        this.email = email;
        this.contactNumber = contactNumber;
        this.paymentMethod = paymentMethod;
        this.total = total;
        this.productDetails = productDetails;
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


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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

    public String getProductDetails() {
        return productDetails;
    }

    public void setProductDetails(String productDetails) {
        this.productDetails = productDetails;
    }
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

}
