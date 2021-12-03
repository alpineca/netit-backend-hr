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
    private String publishTime;
    @Column(name="employer_id")
    private int employerId;
    private String title;
    @Column(columnDefinition="TEXT")
    private String description;
    private String category;
    private int salaryMin;
    private int salaryMax;

    @ManyToMany
    @JoinTable(
            name                = "tc_applicants",
            joinColumns         = @JoinColumn(name = "campaign_id"),
            inverseJoinColumns  = @JoinColumn(name = "employee_id")
    )
    private List<Employee> applicants;

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

//    /**
//     * @return the employer
//     */
//    public Employer getEmployer() {
//        return employer;
//    }
//
//    /**
//     * @param employer the employer to set
//     */
//    public void setEmployer(Employer employer) {
//        this.employer = employer;
//    }

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
    public List<Employee> getApplicants() {
        return applicants;
    }

    /**
     * @param applicants the candidates to set
     */
    public void setApplicants(List<Employee> applicants) {
        this.applicants = applicants;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
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

    public String getPublishTime() {
        return publishTime;
    }

    public void setPublishTime(String publishTime) {
        this.publishTime = publishTime;
    }

    public int getEmployerId() {
        return employerId;
    }

    public void setEmployerId(int employerId) {
        this.employerId = employerId;
    }
}
