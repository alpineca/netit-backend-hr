package com.enikolov.netitbackendhr.models.extra.infoContent;

import javax.persistence.*;

@Entity
@Table(name = "tm_benefits")
public class Benefits {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String benefitName;
}
