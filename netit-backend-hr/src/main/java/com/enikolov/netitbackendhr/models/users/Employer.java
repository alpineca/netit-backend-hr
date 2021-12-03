package com.enikolov.netitbackendhr.models.users;

import com.enikolov.netitbackendhr.models.extra.infoContent.Benefits;
import com.enikolov.netitbackendhr.models.extra.infoContent.Category;
import com.enikolov.netitbackendhr.models.general.Campaign;
import com.enikolov.netitbackendhr.models.users.User;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "td_employers")
public class Employer {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String companyName;
    private int employeesCount;
    private String category;
    private String headquarterLocation;
    private String address;
    private String webSite;
    @Column(columnDefinition="TEXT")
    private String description;

    // private User user;

    @Column(name="user_id")
    private int userId;

    @OneToMany(mappedBy = "employerId")
    private List<Campaign> campaigns;

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
     * @return the companyName
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName the companyName to set
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * @return the employeesCount
     */
    public int getEmployeesCount() {
        return employeesCount;
    }

    /**
     * @param employeesCount the employeesCount to set
     */
    public void setEmployeesCount(int employeesCount) {
        this.employeesCount = employeesCount;
    }

    /**
     * @return the category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category the category to set
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return the headquarterLocation
     */
    public String getHeadquarterLocation() {
        return headquarterLocation;
    }

    /**
     * @param headquarterLocation the headquarterLocation to set
     */
    public void setHeadquarterLocation(String headquarterLocation) {
        this.headquarterLocation = headquarterLocation;
    }

    /**
     * @return the address
     */
    public String getAddress() {
        return address;
    }

    /**
     * @param address the address to set
     */
    public void setAddress(String address) {
        this.address = address;
    }

    /**
     * @return the webSite
     */
    public String getWebSite() {
        return webSite;
    }

    /**
     * @param webSite the webSite to set
     */
    public void setWebSite(String webSite) {
        this.webSite = webSite;
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

    // /**
    //  * @return the user
    //  */
    // public User getUser() {
    //     return user;
    // }

    // /**
    //  * @param user the user to set
    //  */
    // public void setUser(User user) {
    //     this.user = user;
    // }

    /**
     * @return the campaigns
     */
    public List<Campaign> getCampaigns() {
        return campaigns;
    }

    /**
     * @param campaigns the campaigns to set
     */
    public void setCampaigns(List<Campaign> campaigns) {
        this.campaigns = campaigns;
    }

    /**
     * @return the userId
     */
    public int getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(int userId) {
        this.userId = userId;
    }

    

    

    
}
