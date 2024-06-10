package com.serviceapp.serviceapp.company;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "company")
public class Company {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "company_sequence")
  @SequenceGenerator(name = "company_sequence", sequenceName = "company_sequence", allocationSize = 1)
  private Long id;

  private String email;
  private String password;
  private String logoUrl;
  private String companyName;
  private String businessEIN;
  private String companyPhoneNumber;
  private Integer yearFounded;
  private Boolean verify;
  private String officeName;
  private String servingArea;
  private String addressLine1;
  private String addressLine2;
  private String city;
  private String state;
  private String zipCode;
  private String ownerFirstName;
  private String ownerLastName;
  private String ownerPhoneNumber;
  private String username;
  private String ownerDateOfBirth;

  @OneToMany(mappedBy = "company", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<BusinessHour> businessHours;

  public Company() {
  }

  public Company(String email, String password) {
      this.email = email;
      this.password = password;
      this.logoUrl = null;
      this.companyName = null;
      this.businessEIN = null;
      this.companyPhoneNumber = null;
      this.yearFounded = null;
      this.verify = false;
      this.officeName = null;
      this.servingArea = null;
      this.addressLine1 = null;
      this.addressLine2 = null;
      this.city = null;
      this.state = null;
      this.zipCode = null;
      this.ownerFirstName = null;
      this.ownerLastName = null;
      this.ownerPhoneNumber = null;
      this.username = null;
      this.ownerDateOfBirth = null;
  }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getBusinessEIN() {
        return businessEIN;
    }

    public void setBusinessEIN(String businessEIN) {
        this.businessEIN = businessEIN;
    }

    public String getCompanyPhoneNumber() {
        return companyPhoneNumber;
    }

    public void setCompanyPhoneNumber(String companyPhoneNumber) {
        this.companyPhoneNumber = companyPhoneNumber;
    }

    public Integer getYearFounded() {
        return yearFounded;
    }

    public void setYearFounded(Integer yearFounded) {
        this.yearFounded = yearFounded;
    }

    public Boolean getVerify() {
        return verify;
    }

    public void setVerify(Boolean verify) {
        this.verify = verify;
    }

    public String getOfficeName() {
        return officeName;
    }

    public void setOfficeName(String officeName) {
        this.officeName = officeName;
    }

    public String getServingArea() {
        return servingArea;
    }

    public void setServingArea(String servingArea) {
        this.servingArea = servingArea;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getOwnerFirstName() {
        return ownerFirstName;
    }

    public void setOwnerFirstName(String ownerFirstName) {
        this.ownerFirstName = ownerFirstName;
    }

    public String getOwnerLastName() {
        return ownerLastName;
    }

    public void setOwnerLastName(String ownerLastName) {
        this.ownerLastName = ownerLastName;
    }

    public String getOwnerPhoneNumber() {
        return ownerPhoneNumber;
    }

    public void setOwnerPhoneNumber(String ownerPhoneNumber) {
        this.ownerPhoneNumber = ownerPhoneNumber;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOwnerDateOfBirth() {
        return ownerDateOfBirth;
    }

    public void setOwnerDateOfBirth(String ownerDateOfBirth) {
        this.ownerDateOfBirth = ownerDateOfBirth;
    }

    public List<BusinessHour> getBusinessHours() {
        return businessHours;
    }

    public void setBusinessHours(List<BusinessHour> businessHours) {
        this.businessHours = businessHours;
    }
}
