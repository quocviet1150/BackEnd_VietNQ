package com.example.cafe.DTO;


import lombok.Data;

import java.util.Date;

@Data
public class UserDTO {
    private Integer id;
    private String name;
    private String userName;
    private String contactNumber;
    Date createdDate;
    private String role;
    private String status;

    public UserDTO(Integer id, String name, String userName, String contactNumber, String role, String status, Date createdDate) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.contactNumber = contactNumber;
        this.role = role;
        this.status = status;
        this.createdDate = createdDate;
    }

}
