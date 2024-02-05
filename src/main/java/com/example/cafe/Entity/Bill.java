package com.example.cafe.Entity;

import com.fasterxml.jackson.databind.JsonNode;
import lombok.Data;
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

@NamedQuery(name = "Bill.getOne", query = "SELECT COUNT(b.id) AS totalOrders\n" +
        "FROM Bill b\n" +
        "WHERE DATE(b.createdDate) = CURRENT_DATE ")

@NamedQuery(name = "Bill.sumBillByDate", query = "SELECT SUM(b.total) FROM Bill b WHERE DATE(b.createdDate) = CURRENT_DATE")

@Data
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

}
