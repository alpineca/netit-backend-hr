package com.enikolov.netitbackendhr.models.general;

import com.enikolov.netitbackendhr.models.extra.Category;
import com.enikolov.netitbackendhr.models.users.Employee;
import com.enikolov.netitbackendhr.models.users.Employer;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "td_campaigns")
public class Campaign {
    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String publishDate;
    private String publishTime;
    @ManyToOne
    @JoinColumn(name = "employer_id")
    private Employer employer;
    private String title;
    @Column(columnDefinition="TEXT")
    private String description;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    private int salaryMin;
    private int salaryMax;

    @ManyToMany
    @JoinTable(
            name                = "tc_applicants",
            joinColumns         = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns  = @JoinColumn(name = "employee_id")
    )
    private List<Employee> applicants;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPublishDate() {
        return publishDate;
    }

    public void setPublishDate(String publishDate) {
        this.publishDate = publishDate;
    }

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public Employer getEmployer() {
        return employer;
    }

    public void setEmployer(Employer employer) {
        this.employer = employer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public int getSalaryMin() {
        return salaryMin;
    }

    public void setSalaryMin(int salaryMin) {
        this.salaryMin = salaryMin;
    }

    public int getSalaryMax() {
        return salaryMax;
    }

    public void setSalaryMax(int salaryMax) {
        this.salaryMax = salaryMax;
    }

    public List<Employee> getApplicants() {
        return applicants;
    }

    public void setApplicants(List<Employee> applicants) {
        this.applicants = applicants;
    }
}
