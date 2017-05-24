package com.weareholidays.service;

import com.weareholidays.domain.Authority;
import com.weareholidays.domain.User;
import com.weareholidays.repository.AuthorityRepository;
import com.weareholidays.config.Constants;
import com.weareholidays.repository.UserRepository;
import com.weareholidays.security.AuthoritiesConstants;
import com.weareholidays.security.SecurityUtils;
import com.weareholidays.service.util.RandomUtil;
import com.weareholidays.service.dto.UserDTO;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.ZonedDateTime;
import java.util.*;

/**
 * Service class for managing users.
 */
@Service
@Transactional
public class UserService {

    private final Logger log = LoggerFactory.getLogger(UserService.class);

    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    public final JdbcTokenStore jdbcTokenStore;

    private final AuthorityRepository authorityRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder, JdbcTokenStore jdbcTokenStore, AuthorityRepository authorityRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jdbcTokenStore = jdbcTokenStore;
        this.authorityRepository = authorityRepository;
    }

    public Optional<User> activateRegistration(String key) {
        log.debug("Activating user for activation key {}", key);
        return userRepository.findOneByActivationKey(key)
            .map(user -> {
                // activate given user for the registration key.
                user.setActivated(true);
                user.setActivationKey(null);
                log.debug("Activated user: {}", user);
                return user;
            });
    }

    public Optional<User> completePasswordReset(String newPassword, String key) {
       log.debug("Reset user password for reset key {}", key);

       return userRepository.findOneByResetKey(key)
            .filter(user -> {
                ZonedDateTime oneDayAgo = ZonedDateTime.now().minusHours(24);
                return user.getResetDate().isAfter(oneDayAgo);
           })
           .map(user -> {
                user.setPassword(passwordEncoder.encode(newPassword));
                user.setResetKey(null);
                user.setResetDate(null);
                return user;
           });
    }

    public Optional<User> requestPasswordReset(String mail) {
        return userRepository.findOneByEmail(mail)
            .filter(User::getActivated)
            .map(user -> {
                user.setResetKey(RandomUtil.generateResetKey());
                user.setResetDate(ZonedDateTime.now());
                return user;
            });
    }

    public User createUser(String login, String password, String firstName, String lastName, String email,
        String imageUrl, String langKey, Long totalTrips, String gender, String name, Long phone, String place, String profileImageLocalUrl, Long totalPublishedTrips) {

        User newUser = new User();
        Authority authority = authorityRepository.findOne(AuthoritiesConstants.USER);
        Set<Authority> authorities = new HashSet<>();
        String encryptedPassword = passwordEncoder.encode(password);
        newUser.setLogin(login);
        // new user gets initially a generated password
        newUser.setPassword(encryptedPassword);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setImageUrl(imageUrl);
        newUser.setLangKey(langKey);
        // new user is not active
        newUser.setActivated(true);
        // new user gets registration key
        newUser.setActivationKey(RandomUtil.generateActivationKey());
        authorities.add(authority);
        newUser.setAuthorities(authorities);
        newUser.setTotalTrips(totalTrips);
        newUser.setGender(gender);
        newUser.setName(name);
        newUser.setPhone(phone);
        newUser.setPlace(place);
        newUser.setProfileImageLocalUrl(profileImageLocalUrl);
        newUser.setTotalPublishedTrips(totalPublishedTrips);
        userRepository.save(newUser);
        log.debug("Created Information for User: {}", newUser);
        return newUser;
    }

    public User createUser(UserDTO userDTO) {
        User user = new User();
        user.setLogin(userDTO.getLogin());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setEmail(userDTO.getEmail());
        user.setImageUrl(userDTO.getImageUrl());
        if (userDTO.getLangKey() == null) {
            user.setLangKey("en"); // default language
        } else {
            user.setLangKey(userDTO.getLangKey());
        }
        if (userDTO.getAuthorities() != null) {
            Set<Authority> authorities = new HashSet<>();
            userDTO.getAuthorities().forEach(
                authority -> authorities.add(authorityRepository.findOne(authority))
            );
            user.setAuthorities(authorities);
        }
        String encryptedPassword = passwordEncoder.encode(RandomUtil.generatePassword());
        user.setPassword(encryptedPassword);
        user.setResetKey(RandomUtil.generateResetKey());
        user.setResetDate(ZonedDateTime.now());
        user.setActivated(true);
        user.setTotalTrips(userDTO.getTotalTrips());
        user.setGender(userDTO.getGender());
        user.setName(userDTO.getName());
        user.setPhone(userDTO.getPhone());
        user.setPlace(userDTO.getPlace());
        user.setProfileImageLocalUrl(user.getProfileImageLocalUrl());
        user.setTotalPublishedTrips(userDTO.getTotalPublishedTrips());
        userRepository.save(user);
        log.debug("Created Information for User: {}", user);
        return user;
    }

    /**
     * Update basic information (first name, last name, email, language) for the current user.
     *
     * @param firstName first name of user
     * @param lastName last name of user
     * @param email email id of user
     * @param langKey language key
     * @param imageUrl image URL of user
     */
    public void updateUser(String firstName, String lastName, String email, String langKey, String imageUrl) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setEmail(email);
            user.setLangKey(langKey);
            user.setImageUrl(imageUrl);
            log.debug("Changed Information for User: {}", user);
        });
    }

    /**
     * Update all information for a specific user, and return the modified user.
     *
     * @param userDTO user to update
     * @return updated user
     */
    public Optional<UserDTO> updateUser(UserDTO userDTO) {
        return userRepository
            .findOneByEmail(userDTO.getEmail())
            .map(user -> {
               // user.setLogin(userDTO.getLogin());
                if(userDTO.getFirstName() != null)
                    user.setFirstName(userDTO.getFirstName());
                if(userDTO.getLastName() != null)
                    user.setLastName(userDTO.getLastName());
               // user.setEmail(userDTO.getEmail());
                if(userDTO.getImageUrl() != null)
                    user.setImageUrl(userDTO.getImageUrl());
                if(userDTO.isActivated())
                    user.setActivated(userDTO.isActivated());
                if(userDTO.getLangKey() != null)
                    user.setLangKey(userDTO.getLangKey());
                if(userDTO.getTotalTrips() != null)
                    user.setTotalTrips(userDTO.getTotalTrips());
                if(userDTO.getGender() != null)
                    user.setGender(userDTO.getGender());
                if(userDTO.getName() != null)
                    user.setName(userDTO.getName());
                if(userDTO.getPhone() != null)
                    user.setPhone(userDTO.getPhone());
                if(userDTO.getPlace() != null)
                    user.setPlace(userDTO.getPlace());
                if(userDTO.getProfileImageLocalUrl() != null)
                    user.setProfileImageLocalUrl(user.getProfileImageLocalUrl());
                if(userDTO.getTotalPublishedTrips() != null)
                    user.setTotalPublishedTrips(userDTO.getTotalPublishedTrips());
                Set<Authority> managedAuthorities = user.getAuthorities();
                managedAuthorities.clear();
                if(userDTO.getAuthorities() != null){
                    userDTO.getAuthorities().stream()
                        .map(authorityRepository::findOne)
                        .forEach(managedAuthorities::add);
                }

                log.debug("Changed Information for User: {}", user);
                return user;
            })
            .map(UserDTO::new);
    }

    public void deleteUser(String login) {
        jdbcTokenStore.findTokensByUserName(login).forEach(token ->
            jdbcTokenStore.removeAccessToken(token));
        userRepository.findOneByLogin(login).ifPresent(user -> {
            userRepository.delete(user);
            log.debug("Deleted User: {}", user);
        });
    }

    public void changePassword(String password) {
        userRepository.findOneByLogin(SecurityUtils.getCurrentUserLogin()).ifPresent(user -> {
            String encryptedPassword = passwordEncoder.encode(password);
            user.setPassword(encryptedPassword);
            log.debug("Changed password for User: {}", user);
        });
    }

    @Transactional(readOnly = true)
    public Page<UserDTO> getAllManagedUsers(Pageable pageable) {
        return userRepository.findAllByLoginNot(pageable, Constants.ANONYMOUS_USER).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public Optional<User> getUserWithAuthoritiesByLogin(String login) {
        return userRepository.findOneWithAuthoritiesByLogin(login);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities(Long id) {
        return userRepository.findOneWithAuthoritiesById(id);
    }

    @Transactional(readOnly = true)
    public User getUserWithAuthorities() {
        return userRepository.findOneWithAuthoritiesByLogin(SecurityUtils.getCurrentUserLogin()).orElse(null);
    }


    /**
     * Not activated users should be automatically deleted after 3 days.
     * <p>
     * This is scheduled to get fired everyday, at 01:00 (am).
     * </p>
     */
    @Scheduled(cron = "0 0 1 * * ?")
    public void removeNotActivatedUsers() {
        ZonedDateTime now = ZonedDateTime.now();
        List<User> users = userRepository.findAllByActivatedIsFalseAndCreatedDateBefore(now.minusDays(3));
        for (User user : users) {
            log.debug("Deleting not activated user {}", user.getLogin());
            userRepository.delete(user);
        }
    }
}
