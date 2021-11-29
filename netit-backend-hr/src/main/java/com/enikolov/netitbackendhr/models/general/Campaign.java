package com.enikolov.netitbackendhr.models.general;

import com.enikolov.netitbackendhr.models.users.Employee;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "td_campaigns")
public class Campaign {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String publishDate;
    @ManyToOne
    private Employer employer;

    private String title;

    private String description;

    @ManyToMany
    @JoinTable(
            name                = "tc_applicants",
            joinColumns         = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns  = @JoinColumn(name = "employee_id")
    )
    private List<Employee> candidates;

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the publishDate
     */
    public String getPublishDate() {
        return publishDate;
    }

    /**
     * @param publishDate the publishDate to set
     */
    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    /**
     * @return the employer
     */
    public Employer getEmployer() {
        return employer;
    }

    /**
     * @param employer the employer to set
     */
    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the candidates
     */
    public List<Employee> getCandidates() {
        return candidates;
    }

    /**
     * @param candidates the candidates to set
     */
    public void setCandidates(List<Employee> candidates) {
        this.candidates = candidates;
    }

    

}
