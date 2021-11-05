package com.enikolov.netitbackendhr.models.general;

import com.enikolov.netitbackendhr.models.users.Employee;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "td_campaigns")
public class Campaign {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String publishDate;
    @ManyToOne
    private Employer employer;

    private String title;

    private String description;

    @ManyToMany
    @JoinTable(
            name                = "tc_campaign_candidates",
            joinColumns         = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns  = @JoinColumn(name = "employee_id")
    )
    private List<Employee> candidates;

}
