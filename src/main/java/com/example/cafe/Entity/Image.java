package com.example.cafe.Entity;


import lombok.Data;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.NamedQuery;

import javax.persistence.*;
import java.io.Serializable;

@NamedQuery(name = "Image.deleteByImagePath", query = "DELETE FROM Image i WHERE i.imagePath = :imagePath")

@NamedQuery(name = "Image.findByImagePath", query = "SELECT i FROM Image i WHERE i.id = :id")

@NamedQuery(name = "Image.findByStatusTrue", query = "SELECT i FROM Image i WHERE i.status = 'true' order by i.id desc")

@NamedQuery(name = "Image.updateStatus", query = "update Image i set i.status=:status where i.id=:id")

@NamedQuery(name = "Image.findByFileName", query = "SELECT i FROM Image i WHERE i.fileName = :fileName")

@Data
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

}
