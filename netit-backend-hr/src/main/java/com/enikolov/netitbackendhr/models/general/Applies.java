package com.enikolov.netitbackendhr.models.general;

import com.enikolov.netitbackendhr.enums.AppliesStatus;
import com.enikolov.netitbackendhr.models.users.Employee;
import com.enikolov.netitbackendhr.models.users.Employer;

import javax.persistence.*;

@Entity
@Table(name = "tc_applies")
public class Applies {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String date;
    private String time;
    private AppliesStatus status;
    private String motivation;
    private String hrInterviewReview;
    @ManyToOne
    @JoinColumn(name = "campaign_id")
    private Campaign campaign;
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    public Employee getEmployee() {
        return employee;
    }

    public Campaign getCampaign() {
        return campaign;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setCampaign(Campaign campaign) {
        this.campaign = campaign;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public AppliesStatus getStatus() {
        return status;
    }

    public void setStatus(AppliesStatus status) {
        this.status = status;
    }

    public String getMotivation() {
        return motivation;
    }

    public void setMotivation(String motivation) {
        this.motivation = motivation;
    }

    public String getHrInterviewReview() {
        return hrInterviewReview;
    }

    public void setHrInterviewReview(String hrInterviewReview) {
        this.hrInterviewReview = hrInterviewReview;
    }
}
