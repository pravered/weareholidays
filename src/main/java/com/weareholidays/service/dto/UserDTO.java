package com.weareholidays.service.dto;

import com.weareholidays.config.Constants;

import com.weareholidays.domain.Authority;
import com.weareholidays.domain.User;

import org.hibernate.validator.constraints.Email;

import javax.validation.constraints.*;
import java.time.ZonedDateTime;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * A DTO representing a user, with his authorities.
 */
public class UserDTO {

    private Long id;

    @Pattern(regexp = Constants.LOGIN_REGEX)
    @Size(min = 1, max = 100)
    private String login;

    @Size(max = 50)
    private String firstName;

    @Size(max = 50)
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    @Size(max = 256)
    private String imageUrl;

    private boolean activated = false;

    @Size(min = 2, max = 5)
    private String langKey;

    private String createdBy;

    private ZonedDateTime createdDate;

    private String lastModifiedBy;



    private ZonedDateTime lastModifiedDate;

    private Set<String> authorities;

    private Long totalTrips;

    private String gender;

    private String name;

    private Long phone;

    private String place;

    private String profileImageLocalUrl;

    private Long totalPublishedTrips;

    private String userName;

    public String getGender() {
        return gender;
    }

    public String getName() {
        return name;
    }


    public String getPlace() {
        return place;
    }

    public String getProfileImageLocalUrl() {
        return profileImageLocalUrl;
    }


    public String getUserName() {
        return userName;
    }

    public UserDTO() {
        // Empty constructor needed for MapStruct.
    }

    public UserDTO(User user) {
        this(user.getId(), user.getLogin(), user.getFirstName(), user.getLastName(),
            user.getEmail(), user.getActivated(), user.getImageUrl(), user.getLangKey(),
            user.getCreatedBy(), user.getCreatedDate(), user.getLastModifiedBy(), user.getLastModifiedDate(),
            user.getAuthorities().stream().map(Authority::getName)
                .collect(Collectors.toSet()),user.getTotalTrips(),user.getGender(),user.getName(),user.getPhone(),user.getPlace(),user.getProfileImageLocalUrl(),user.getTotalPublishedTrips(),user.getUserName());
    }

    public UserDTO(Long id, String login, String firstName, String lastName,
                   String email, boolean activated, String imageUrl, String langKey,
                   String createdBy, ZonedDateTime createdDate, String lastModifiedBy, ZonedDateTime lastModifiedDate,
                   Set<String> authorities, Long totalTrips, String gender, String name, Long phone, String place, String profileImageLocalUrl, Long totalPublishedTrips, String userName) {

        this.id = id;
        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.activated = activated;
        this.imageUrl = imageUrl;
        this.langKey = langKey;
        this.createdBy = createdBy;
        this.createdDate = createdDate;
        this.lastModifiedBy = lastModifiedBy;
        this.lastModifiedDate = lastModifiedDate;
        this.authorities = authorities;
        this.totalTrips = totalTrips;
        this.gender = gender;
        this.name = name;
        this.phone = phone;
        this.place = place;
        this.profileImageLocalUrl = profileImageLocalUrl;
        this.totalPublishedTrips = totalPublishedTrips;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public boolean isActivated() {
        return activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public String getCreatedBy() {
        return createdBy;
    }

    public ZonedDateTime getCreatedDate() {
        return createdDate;
    }

    public String getLastModifiedBy() {
        return lastModifiedBy;
    }

    public ZonedDateTime getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(ZonedDateTime lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }

    public Set<String> getAuthorities() {
        return authorities;
    }

    public Long getTotalTrips() {
        return totalTrips;
    }

    public Long getPhone() {
        return phone;
    }

    public Long getTotalPublishedTrips() {
        return totalPublishedTrips;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
            "login='" + login + '\'' +
            ", firstName='" + firstName + '\'' +
            ", lastName='" + lastName + '\'' +
            ", email='" + email + '\'' +
            ", imageUrl='" + imageUrl + '\'' +
            ", activated=" + activated +
            ", langKey='" + langKey + '\'' +
            ", createdBy=" + createdBy +
            ", createdDate=" + createdDate +
            ", lastModifiedBy='" + lastModifiedBy + '\'' +
            ", lastModifiedDate=" + lastModifiedDate +
            ", authorities=" + authorities +
            "}";
    }
}
