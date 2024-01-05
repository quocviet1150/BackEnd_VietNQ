package com.example.cafe.wrapper;


public class UserWrapper {
    private Integer id;
    private String name;
    private String userName;
    private String contactNumber;
    private String status;

    public UserWrapper() {

    }

    public UserWrapper(Integer id, String name, String userName, String contactNumber, String status) {
        this.id = id;
        this.name = name;
        this.userName = userName;
        this.contactNumber = contactNumber;
        this.status = status;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getContactNumber() {
        return contactNumber;
    }

    public void setContactNumber(String contactNumber) {
        this.contactNumber = contactNumber;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
