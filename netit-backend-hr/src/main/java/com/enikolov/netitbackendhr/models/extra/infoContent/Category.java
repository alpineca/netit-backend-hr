package com.enikolov.netitbackendhr.models.extra.infoContent;

import javax.persistence.*;

@Entity
@Table(name = "tm_categories")
public class Category {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String categoryName;
}
