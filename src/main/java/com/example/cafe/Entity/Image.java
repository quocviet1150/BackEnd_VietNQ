package com.example.cafe.Entity;


import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Image.deleteByImagePath", query = "DELETE FROM Image i WHERE i.imagePath = :imagePath")

@NamedQuery(name = "Image.findByImagePath", query = "SELECT i FROM Image i WHERE i.id = :id")

@NamedQuery(name = "Image.findByStatusTrue", query = "SELECT i FROM Image i WHERE i.status = 'true' ")

@NamedQuery(name = "Image.updateStatus", query = "update Image i set i.status=:status where i.id=:id")



@Entity
@DynamicUpdate
@DynamicInsert
@Table(name = "image")
public class Image implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;

    @Column(name = "name")
    private String name;

    @Column(name = "imagePath")
    private String imagePath;

    @Column(name = "fileName")
    private String fileName;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private String status;

    public Image() {

    }

    public Image(Integer id, String name, String imagePath, String fileName, String description, String status) {
        this.id = id;
        this.name = name;
        this.imagePath = imagePath;
        this.fileName = fileName;
        this.description = description;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
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

}
