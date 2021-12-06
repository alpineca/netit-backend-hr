package com.enikolov.netitbackendhr.models.extra;

import javax.persistence.*;

@Entity
@Table(name = "tm_categories")
public class Category {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String categoryName;

    public Category(){

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }
}
