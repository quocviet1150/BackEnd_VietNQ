package com.example.cafe.Entity;

import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@NamedQuery(name = "User.findByUserNameId", query = "select u from User u where u.userName=:userName")

@NamedQuery(name = "User.getAllUser", query = "SELECT new com.example.cafe.DTO.UserDTO(u.id, u.name," +
        " u.userName, u.contactNumber,u.role, u.status,u.createdDate) FROM User u WHERE u.role ='user' order by u.id desc ")

@NamedQuery(name = "User.getAllAdmin", query = "SELECT u.userName FROM User u WHERE u.role = 'admin'")

@NamedQuery(name = "User.updateStatus", query = "update User u set u.status=:status where u.id=:id")

@NamedQuery(name = "User.countByRole", query = "SELECT COUNT(*) FROM User u WHERE u.role = 'user'")

@NamedQuery(name = "User.updateDecentralization", query = "update User u set u.role=:role where u.id=:id")

@Data
@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "user_login")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "contactNumber")
    private String contactNumber;

    @Column(name = "userName")
    private String userName;

    @Column(name = "password")
    private String password;

    @Column(name = "created_date")
    private Date createdDate;

    @Column(name = "status")
    private String status;

    @Column(name = "role")
    private String role;
    @Column(name = "imagePath")
    private String imagePath;

    @Column(name = "fileName")
    private String fileName;

}
