package com.example.cafe.DTO;

import lombok.Data;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class ProductDTO {

    Integer id;
    String name;
    String description;
    Integer price;
    String status;
    Date createdDate;
    Date updateDate;
    Integer categoryId;
    String categoryName;

    Integer quantity_product;

    private List<List<String>> temporaryChartData;

    public ProductDTO(Integer id, String name, String description, Integer price, String status, Integer categoryId, String categoryName, Date createdDate, Date updateDate,Integer quantity_product) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.status = status;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
        this.createdDate = createdDate;
        this.updateDate = updateDate;
        this.quantity_product = quantity_product;
    }

    public ProductDTO(Integer id, String name) {
        this.id = id;
        this.name = name;

    }

    public ProductDTO(Integer id, String name, Integer quantity_product) {
        this.id = id;
        this.name = name;
        this.quantity_product = quantity_product;
    }

    public ProductDTO(Integer id, String name, String description, Integer price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public ProductDTO() {

    }

}
