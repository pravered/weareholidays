package com.weareholidays.web.rest;

import com.weareholidays.WeareholidaysApp;

import com.weareholidays.domain.TripPeople;
import com.weareholidays.repository.TripPeopleRepository;
import com.weareholidays.service.TripPeopleService;
import com.weareholidays.service.dto.TripPeopleDTO;
import com.weareholidays.service.mapper.TripPeopleMapper;
import com.weareholidays.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the TripPeopleResource REST controller.
 *
 * @see TripPeopleResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeareholidaysApp.class)
public class TripPeopleResourceIntTest {

    private static final String DEFAULT_PHONE_BOOK_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_BOOK_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK_TYPE = "BBBBBBBBBB";

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_EMAIL = "AAAAAAAAAA";
    private static final String UPDATED_EMAIL = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_LOCAL_URI = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_LOCAL_URI = "BBBBBBBBBB";

    private static final String DEFAULT_IMAGE_REMOTE_URI = "AAAAAAAAAA";
    private static final String UPDATED_IMAGE_REMOTE_URI = "BBBBBBBBBB";

    private static final String DEFAULT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_TYPE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_IN_TRIP = false;
    private static final Boolean UPDATED_IN_TRIP = true;

    private static final String DEFAULT_IDENTIFIER = "AAAAAAAAAA";
    private static final String UPDATED_IDENTIFIER = "BBBBBBBBBB";

    @Autowired
    private TripPeopleRepository tripPeopleRepository;

    @Autowired
    private TripPeopleMapper tripPeopleMapper;

    @Autowired
    private TripPeopleService tripPeopleService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTripPeopleMockMvc;

    private TripPeople tripPeople;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TripPeopleResource tripPeopleResource = new TripPeopleResource(tripPeopleService);
        this.restTripPeopleMockMvc = MockMvcBuilders.standaloneSetup(tripPeopleResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static TripPeople createEntity(EntityManager em) {
        TripPeople tripPeople = new TripPeople()
            .phoneBookType(DEFAULT_PHONE_BOOK_TYPE)
            .facebookType(DEFAULT_FACEBOOK_TYPE)
            .name(DEFAULT_NAME)
            .email(DEFAULT_EMAIL)
            .imageLocalUri(DEFAULT_IMAGE_LOCAL_URI)
            .imageRemoteUri(DEFAULT_IMAGE_REMOTE_URI)
            .type(DEFAULT_TYPE)
            .inTrip(DEFAULT_IN_TRIP)
            .identifier(DEFAULT_IDENTIFIER);
        return tripPeople;
    }

    @Before
    public void initTest() {
        tripPeople = createEntity(em);
    }

    @Test
    @Transactional
    public void createTripPeople() throws Exception {
        int databaseSizeBeforeCreate = tripPeopleRepository.findAll().size();

        // Create the TripPeople
        TripPeopleDTO tripPeopleDTO = tripPeopleMapper.tripPeopleToTripPeopleDTO(tripPeople);
        restTripPeopleMockMvc.perform(post("/api/trip-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripPeopleDTO)))
            .andExpect(status().isCreated());

        // Validate the TripPeople in the database
        List<TripPeople> tripPeopleList = tripPeopleRepository.findAll();
        assertThat(tripPeopleList).hasSize(databaseSizeBeforeCreate + 1);
        TripPeople testTripPeople = tripPeopleList.get(tripPeopleList.size() - 1);
        assertThat(testTripPeople.getPhoneBookType()).isEqualTo(DEFAULT_PHONE_BOOK_TYPE);
        assertThat(testTripPeople.getFacebookType()).isEqualTo(DEFAULT_FACEBOOK_TYPE);
        assertThat(testTripPeople.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTripPeople.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testTripPeople.getImageLocalUri()).isEqualTo(DEFAULT_IMAGE_LOCAL_URI);
        assertThat(testTripPeople.getImageRemoteUri()).isEqualTo(DEFAULT_IMAGE_REMOTE_URI);
        assertThat(testTripPeople.getType()).isEqualTo(DEFAULT_TYPE);
        assertThat(testTripPeople.isInTrip()).isEqualTo(DEFAULT_IN_TRIP);
        assertThat(testTripPeople.getIdentifier()).isEqualTo(DEFAULT_IDENTIFIER);
    }

    @Test
    @Transactional
    public void createTripPeopleWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tripPeopleRepository.findAll().size();

        // Create the TripPeople with an existing ID
        tripPeople.setId(1L);
        TripPeopleDTO tripPeopleDTO = tripPeopleMapper.tripPeopleToTripPeopleDTO(tripPeople);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTripPeopleMockMvc.perform(post("/api/trip-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripPeopleDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TripPeople> tripPeopleList = tripPeopleRepository.findAll();
        assertThat(tripPeopleList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTripPeople() throws Exception {
        // Initialize the database
        tripPeopleRepository.saveAndFlush(tripPeople);

        // Get all the tripPeopleList
        restTripPeopleMockMvc.perform(get("/api/trip-people?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tripPeople.getId().intValue())))
            .andExpect(jsonPath("$.[*].phoneBookType").value(hasItem(DEFAULT_PHONE_BOOK_TYPE.toString())))
            .andExpect(jsonPath("$.[*].facebookType").value(hasItem(DEFAULT_FACEBOOK_TYPE.toString())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
            .andExpect(jsonPath("$.[*].imageLocalUri").value(hasItem(DEFAULT_IMAGE_LOCAL_URI.toString())))
            .andExpect(jsonPath("$.[*].imageRemoteUri").value(hasItem(DEFAULT_IMAGE_REMOTE_URI.toString())))
            .andExpect(jsonPath("$.[*].type").value(hasItem(DEFAULT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].inTrip").value(hasItem(DEFAULT_IN_TRIP.booleanValue())))
            .andExpect(jsonPath("$.[*].identifier").value(hasItem(DEFAULT_IDENTIFIER.toString())));
    }

    @Test
    @Transactional
    public void getTripPeople() throws Exception {
        // Initialize the database
        tripPeopleRepository.saveAndFlush(tripPeople);

        // Get the tripPeople
        restTripPeopleMockMvc.perform(get("/api/trip-people/{id}", tripPeople.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tripPeople.getId().intValue()))
            .andExpect(jsonPath("$.phoneBookType").value(DEFAULT_PHONE_BOOK_TYPE.toString()))
            .andExpect(jsonPath("$.facebookType").value(DEFAULT_FACEBOOK_TYPE.toString()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.imageLocalUri").value(DEFAULT_IMAGE_LOCAL_URI.toString()))
            .andExpect(jsonPath("$.imageRemoteUri").value(DEFAULT_IMAGE_REMOTE_URI.toString()))
            .andExpect(jsonPath("$.type").value(DEFAULT_TYPE.toString()))
            .andExpect(jsonPath("$.inTrip").value(DEFAULT_IN_TRIP.booleanValue()))
            .andExpect(jsonPath("$.identifier").value(DEFAULT_IDENTIFIER.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingTripPeople() throws Exception {
        // Get the tripPeople
        restTripPeopleMockMvc.perform(get("/api/trip-people/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTripPeople() throws Exception {
        // Initialize the database
        tripPeopleRepository.saveAndFlush(tripPeople);
        int databaseSizeBeforeUpdate = tripPeopleRepository.findAll().size();

        // Update the tripPeople
        TripPeople updatedTripPeople = tripPeopleRepository.findOne(tripPeople.getId());
        updatedTripPeople
            .phoneBookType(UPDATED_PHONE_BOOK_TYPE)
            .facebookType(UPDATED_FACEBOOK_TYPE)
            .name(UPDATED_NAME)
            .email(UPDATED_EMAIL)
            .imageLocalUri(UPDATED_IMAGE_LOCAL_URI)
            .imageRemoteUri(UPDATED_IMAGE_REMOTE_URI)
            .type(UPDATED_TYPE)
            .inTrip(UPDATED_IN_TRIP)
            .identifier(UPDATED_IDENTIFIER);
        TripPeopleDTO tripPeopleDTO = tripPeopleMapper.tripPeopleToTripPeopleDTO(updatedTripPeople);

        restTripPeopleMockMvc.perform(put("/api/trip-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripPeopleDTO)))
            .andExpect(status().isOk());

        // Validate the TripPeople in the database
        List<TripPeople> tripPeopleList = tripPeopleRepository.findAll();
        assertThat(tripPeopleList).hasSize(databaseSizeBeforeUpdate);
        TripPeople testTripPeople = tripPeopleList.get(tripPeopleList.size() - 1);
        assertThat(testTripPeople.getPhoneBookType()).isEqualTo(UPDATED_PHONE_BOOK_TYPE);
        assertThat(testTripPeople.getFacebookType()).isEqualTo(UPDATED_FACEBOOK_TYPE);
        assertThat(testTripPeople.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTripPeople.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testTripPeople.getImageLocalUri()).isEqualTo(UPDATED_IMAGE_LOCAL_URI);
        assertThat(testTripPeople.getImageRemoteUri()).isEqualTo(UPDATED_IMAGE_REMOTE_URI);
        assertThat(testTripPeople.getType()).isEqualTo(UPDATED_TYPE);
        assertThat(testTripPeople.isInTrip()).isEqualTo(UPDATED_IN_TRIP);
        assertThat(testTripPeople.getIdentifier()).isEqualTo(UPDATED_IDENTIFIER);
    }

    @Test
    @Transactional
    public void updateNonExistingTripPeople() throws Exception {
        int databaseSizeBeforeUpdate = tripPeopleRepository.findAll().size();

        // Create the TripPeople
        TripPeopleDTO tripPeopleDTO = tripPeopleMapper.tripPeopleToTripPeopleDTO(tripPeople);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTripPeopleMockMvc.perform(put("/api/trip-people")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripPeopleDTO)))
            .andExpect(status().isCreated());

        // Validate the TripPeople in the database
        List<TripPeople> tripPeopleList = tripPeopleRepository.findAll();
        assertThat(tripPeopleList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTripPeople() throws Exception {
        // Initialize the database
        tripPeopleRepository.saveAndFlush(tripPeople);
        int databaseSizeBeforeDelete = tripPeopleRepository.findAll().size();

        // Get the tripPeople
        restTripPeopleMockMvc.perform(delete("/api/trip-people/{id}", tripPeople.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TripPeople> tripPeopleList = tripPeopleRepository.findAll();
        assertThat(tripPeopleList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TripPeople.class);
    }
}
