package com.weareholidays.web.rest.vm;

import com.weareholidays.service.dto.UserDTO;
import javax.validation.constraints.Size;

import java.time.ZonedDateTime;
import java.util.Set;

/**
 * View Model extending the UserDTO, which is meant to be used in the user management UI.
 */
public class ManagedUserVM extends UserDTO {

    public static final int PASSWORD_MIN_LENGTH = 4;

    public static final int PASSWORD_MAX_LENGTH = 100;

    @Size(min = PASSWORD_MIN_LENGTH, max = PASSWORD_MAX_LENGTH)
    private String password;


    private Long totalTrips;

    private String gender;

    private String name;

    private Long phone;

    private String place;

    private String profileImageLocalUrl;

    private Long totalPublishedTrips;

    private String userName;

    @Override
    public Long getTotalTrips() {
        return totalTrips;
    }

    @Override
    public Long getPhone() {
        return phone;
    }

    @Override
    public Long getTotalPublishedTrips() {
        return totalPublishedTrips;
    }

    public ManagedUserVM() {

        // Empty constructor needed for Jackson.
    }

    public ManagedUserVM(Long id, String login, String password, String firstName, String lastName,
                         String email, boolean activated, String imageUrl, String langKey,
                         String createdBy, ZonedDateTime createdDate, String lastModifiedBy, ZonedDateTime lastModifiedDate,
                         Set<String> authorities, Long totalTrips, String gender, String name, Long phone, String place, String profileImageLocalUrl, Long totalPublishedTrips, String userName) {

        super(id, login, firstName, lastName, email, activated, imageUrl, langKey,
            createdBy, createdDate, lastModifiedBy, lastModifiedDate,  authorities, totalTrips, gender, name, phone, place, profileImageLocalUrl, totalPublishedTrips, userName);

        this.password = password;
    }

    public String getPassword() {
        return password;
    }


    @Override
    public String getGender() {
        return gender;
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public String getPlace() {
        return place;
    }

    @Override
    public String getProfileImageLocalUrl() {
        return profileImageLocalUrl;
    }



    @Override
    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return "ManagedUserVM{" +
            "} " + super.toString();
    }
}
