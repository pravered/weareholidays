package com.weareholidays.web.rest;

import com.weareholidays.WeareholidaysApp;

import com.weareholidays.domain.Trip;
import com.weareholidays.repository.TripRepository;
import com.weareholidays.service.TripService;
import com.weareholidays.service.dto.TripDTO;
import com.weareholidays.service.mapper.TripMapper;
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
 * Test class for the TripResource REST controller.
 *
 * @see TripResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeareholidaysApp.class)
public class TripResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Double DEFAULT_START_LOCATION_LAT = 1D;
    private static final Double UPDATED_START_LOCATION_LAT = 2D;

    private static final Double DEFAULT_START_LOCATION_LONG = 1D;
    private static final Double UPDATED_START_LOCATION_LONG = 2D;

    private static final Double DEFAULT_END_LOCATION_LAT = 1D;
    private static final Double UPDATED_END_LOCATION_LAT = 2D;

    private static final Double DEFAULT_END_LOCATION_LONG = 1D;
    private static final Double UPDATED_END_LOCATION_LONG = 2D;

    private static final Long DEFAULT_START_TIME = 1L;
    private static final Long UPDATED_START_TIME = 2L;

    private static final Long DEFAULT_END_TIME = 1L;
    private static final Long UPDATED_END_TIME = 2L;

    private static final Boolean DEFAULT_IS_UPLOADED = false;
    private static final Boolean UPDATED_IS_UPLOADED = true;

    private static final Boolean DEFAULT_IS_FINISHED = false;
    private static final Boolean UPDATED_IS_FINISHED = true;

    private static final Boolean DEFAULT_IS_PUBLISHED = false;
    private static final Boolean UPDATED_IS_PUBLISHED = true;

    private static final Long DEFAULT_PUBLISHED_TIME = 1L;
    private static final Long UPDATED_PUBLISHED_TIME = 2L;

    private static final Boolean DEFAULT_IS_DELETED = false;
    private static final Boolean UPDATED_IS_DELETED = true;

    private static final String DEFAULT_FEATURE_IMAGE_LOCAL_URI = "AAAAAAAAAA";
    private static final String UPDATED_FEATURE_IMAGE_LOCAL_URI = "BBBBBBBBBB";

    private static final String DEFAULT_FEATURE_IMAGE_REMOTE_URI = "AAAAAAAAAA";
    private static final String UPDATED_FEATURE_IMAGE_REMOTE_URI = "BBBBBBBBBB";

    private static final Integer DEFAULT_NO_OF_DAYS = 1;
    private static final Integer UPDATED_NO_OF_DAYS = 2;

    private static final String DEFAULT_CREATED_AT = "AAAAAAAAAA";
    private static final String UPDATED_CREATED_AT = "BBBBBBBBBB";

    private static final Integer DEFAULT_VIEW_COUNT = 1;
    private static final Integer UPDATED_VIEW_COUNT = 2;

    private static final String DEFAULT_SECRET_KEY = "AAAAAAAAAA";
    private static final String UPDATED_SECRET_KEY = "BBBBBBBBBB";

    private static final Integer DEFAULT_FEATURED = 1;
    private static final Integer UPDATED_FEATURED = 2;

    private static final Boolean DEFAULT_IS_HIDDEN = false;
    private static final Boolean UPDATED_IS_HIDDEN = true;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TripMapper tripMapper;

    @Autowired
    private TripService tripService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTripMockMvc;

    private Trip trip;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TripResource tripResource = new TripResource(tripService);
        this.restTripMockMvc = MockMvcBuilders.standaloneSetup(tripResource)
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
    public static Trip createEntity(EntityManager em) {
        Trip trip = new Trip()
            .name(DEFAULT_NAME)
            .startLocationLat(DEFAULT_START_LOCATION_LAT)
            .startLocationLong(DEFAULT_START_LOCATION_LONG)
            .endLocationLat(DEFAULT_END_LOCATION_LAT)
            .endLocationLong(DEFAULT_END_LOCATION_LONG)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .isUploaded(DEFAULT_IS_UPLOADED)
            .isFinished(DEFAULT_IS_FINISHED)
            .isPublished(DEFAULT_IS_PUBLISHED)
            .publishedTime(DEFAULT_PUBLISHED_TIME)
            .isDeleted(DEFAULT_IS_DELETED)
            .featureImageLocalUri(DEFAULT_FEATURE_IMAGE_LOCAL_URI)
            .featureImageRemoteUri(DEFAULT_FEATURE_IMAGE_REMOTE_URI)
            .noOfDays(DEFAULT_NO_OF_DAYS)
            .createdAt(DEFAULT_CREATED_AT)
            .viewCount(DEFAULT_VIEW_COUNT)
            .secretKey(DEFAULT_SECRET_KEY)
            .featured(DEFAULT_FEATURED)
            .isHidden(DEFAULT_IS_HIDDEN);
        return trip;
    }

    @Before
    public void initTest() {
        trip = createEntity(em);
    }

    @Test
    @Transactional
    public void createTrip() throws Exception {
        int databaseSizeBeforeCreate = tripRepository.findAll().size();

        // Create the Trip
        TripDTO tripDTO = tripMapper.tripToTripDTO(trip);
        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isCreated());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeCreate + 1);
        Trip testTrip = tripList.get(tripList.size() - 1);
        assertThat(testTrip.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testTrip.getStartLocationLat()).isEqualTo(DEFAULT_START_LOCATION_LAT);
        assertThat(testTrip.getStartLocationLong()).isEqualTo(DEFAULT_START_LOCATION_LONG);
        assertThat(testTrip.getEndLocationLat()).isEqualTo(DEFAULT_END_LOCATION_LAT);
        assertThat(testTrip.getEndLocationLong()).isEqualTo(DEFAULT_END_LOCATION_LONG);
        assertThat(testTrip.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testTrip.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testTrip.isIsUploaded()).isEqualTo(DEFAULT_IS_UPLOADED);
        assertThat(testTrip.isIsFinished()).isEqualTo(DEFAULT_IS_FINISHED);
        assertThat(testTrip.isIsPublished()).isEqualTo(DEFAULT_IS_PUBLISHED);
        assertThat(testTrip.getPublishedTime()).isEqualTo(DEFAULT_PUBLISHED_TIME);
        assertThat(testTrip.isIsDeleted()).isEqualTo(DEFAULT_IS_DELETED);
        assertThat(testTrip.getFeatureImageLocalUri()).isEqualTo(DEFAULT_FEATURE_IMAGE_LOCAL_URI);
        assertThat(testTrip.getFeatureImageRemoteUri()).isEqualTo(DEFAULT_FEATURE_IMAGE_REMOTE_URI);
        assertThat(testTrip.getNoOfDays()).isEqualTo(DEFAULT_NO_OF_DAYS);
        assertThat(testTrip.getCreatedAt()).isEqualTo(DEFAULT_CREATED_AT);
        assertThat(testTrip.getViewCount()).isEqualTo(DEFAULT_VIEW_COUNT);
        assertThat(testTrip.getSecretKey()).isEqualTo(DEFAULT_SECRET_KEY);
        assertThat(testTrip.getFeatured()).isEqualTo(DEFAULT_FEATURED);
        assertThat(testTrip.isIsHidden()).isEqualTo(DEFAULT_IS_HIDDEN);
    }

    @Test
    @Transactional
    public void createTripWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tripRepository.findAll().size();

        // Create the Trip with an existing ID
        trip.setId(1L);
        TripDTO tripDTO = tripMapper.tripToTripDTO(trip);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTripMockMvc.perform(post("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTrips() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get all the tripList
        restTripMockMvc.perform(get("/api/trips?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(trip.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].startLocationLat").value(hasItem(DEFAULT_START_LOCATION_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].startLocationLong").value(hasItem(DEFAULT_START_LOCATION_LONG.doubleValue())))
            .andExpect(jsonPath("$.[*].endLocationLat").value(hasItem(DEFAULT_END_LOCATION_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].endLocationLong").value(hasItem(DEFAULT_END_LOCATION_LONG.doubleValue())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.intValue())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.intValue())))
            .andExpect(jsonPath("$.[*].isUploaded").value(hasItem(DEFAULT_IS_UPLOADED.booleanValue())))
            .andExpect(jsonPath("$.[*].isFinished").value(hasItem(DEFAULT_IS_FINISHED.booleanValue())))
            .andExpect(jsonPath("$.[*].isPublished").value(hasItem(DEFAULT_IS_PUBLISHED.booleanValue())))
            .andExpect(jsonPath("$.[*].publishedTime").value(hasItem(DEFAULT_PUBLISHED_TIME.intValue())))
            .andExpect(jsonPath("$.[*].isDeleted").value(hasItem(DEFAULT_IS_DELETED.booleanValue())))
            .andExpect(jsonPath("$.[*].featureImageLocalUri").value(hasItem(DEFAULT_FEATURE_IMAGE_LOCAL_URI.toString())))
            .andExpect(jsonPath("$.[*].featureImageRemoteUri").value(hasItem(DEFAULT_FEATURE_IMAGE_REMOTE_URI.toString())))
            .andExpect(jsonPath("$.[*].noOfDays").value(hasItem(DEFAULT_NO_OF_DAYS)))
            .andExpect(jsonPath("$.[*].createdAt").value(hasItem(DEFAULT_CREATED_AT.toString())))
            .andExpect(jsonPath("$.[*].viewCount").value(hasItem(DEFAULT_VIEW_COUNT)))
            .andExpect(jsonPath("$.[*].secretKey").value(hasItem(DEFAULT_SECRET_KEY.toString())))
            .andExpect(jsonPath("$.[*].featured").value(hasItem(DEFAULT_FEATURED)))
            .andExpect(jsonPath("$.[*].isHidden").value(hasItem(DEFAULT_IS_HIDDEN.booleanValue())));
    }

    @Test
    @Transactional
    public void getTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);

        // Get the trip
        restTripMockMvc.perform(get("/api/trips/{id}", trip.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(trip.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.startLocationLat").value(DEFAULT_START_LOCATION_LAT.doubleValue()))
            .andExpect(jsonPath("$.startLocationLong").value(DEFAULT_START_LOCATION_LONG.doubleValue()))
            .andExpect(jsonPath("$.endLocationLat").value(DEFAULT_END_LOCATION_LAT.doubleValue()))
            .andExpect(jsonPath("$.endLocationLong").value(DEFAULT_END_LOCATION_LONG.doubleValue()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.intValue()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.intValue()))
            .andExpect(jsonPath("$.isUploaded").value(DEFAULT_IS_UPLOADED.booleanValue()))
            .andExpect(jsonPath("$.isFinished").value(DEFAULT_IS_FINISHED.booleanValue()))
            .andExpect(jsonPath("$.isPublished").value(DEFAULT_IS_PUBLISHED.booleanValue()))
            .andExpect(jsonPath("$.publishedTime").value(DEFAULT_PUBLISHED_TIME.intValue()))
            .andExpect(jsonPath("$.isDeleted").value(DEFAULT_IS_DELETED.booleanValue()))
            .andExpect(jsonPath("$.featureImageLocalUri").value(DEFAULT_FEATURE_IMAGE_LOCAL_URI.toString()))
            .andExpect(jsonPath("$.featureImageRemoteUri").value(DEFAULT_FEATURE_IMAGE_REMOTE_URI.toString()))
            .andExpect(jsonPath("$.noOfDays").value(DEFAULT_NO_OF_DAYS))
            .andExpect(jsonPath("$.createdAt").value(DEFAULT_CREATED_AT.toString()))
            .andExpect(jsonPath("$.viewCount").value(DEFAULT_VIEW_COUNT))
            .andExpect(jsonPath("$.secretKey").value(DEFAULT_SECRET_KEY.toString()))
            .andExpect(jsonPath("$.featured").value(DEFAULT_FEATURED))
            .andExpect(jsonPath("$.isHidden").value(DEFAULT_IS_HIDDEN.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTrip() throws Exception {
        // Get the trip
        restTripMockMvc.perform(get("/api/trips/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);
        int databaseSizeBeforeUpdate = tripRepository.findAll().size();

        // Update the trip
        Trip updatedTrip = tripRepository.findOne(trip.getId());
        updatedTrip
            .name(UPDATED_NAME)
            .startLocationLat(UPDATED_START_LOCATION_LAT)
            .startLocationLong(UPDATED_START_LOCATION_LONG)
            .endLocationLat(UPDATED_END_LOCATION_LAT)
            .endLocationLong(UPDATED_END_LOCATION_LONG)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .isUploaded(UPDATED_IS_UPLOADED)
            .isFinished(UPDATED_IS_FINISHED)
            .isPublished(UPDATED_IS_PUBLISHED)
            .publishedTime(UPDATED_PUBLISHED_TIME)
            .isDeleted(UPDATED_IS_DELETED)
            .featureImageLocalUri(UPDATED_FEATURE_IMAGE_LOCAL_URI)
            .featureImageRemoteUri(UPDATED_FEATURE_IMAGE_REMOTE_URI)
            .noOfDays(UPDATED_NO_OF_DAYS)
            .createdAt(UPDATED_CREATED_AT)
            .viewCount(UPDATED_VIEW_COUNT)
            .secretKey(UPDATED_SECRET_KEY)
            .featured(UPDATED_FEATURED)
            .isHidden(UPDATED_IS_HIDDEN);
        TripDTO tripDTO = tripMapper.tripToTripDTO(updatedTrip);

        restTripMockMvc.perform(put("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isOk());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate);
        Trip testTrip = tripList.get(tripList.size() - 1);
        assertThat(testTrip.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testTrip.getStartLocationLat()).isEqualTo(UPDATED_START_LOCATION_LAT);
        assertThat(testTrip.getStartLocationLong()).isEqualTo(UPDATED_START_LOCATION_LONG);
        assertThat(testTrip.getEndLocationLat()).isEqualTo(UPDATED_END_LOCATION_LAT);
        assertThat(testTrip.getEndLocationLong()).isEqualTo(UPDATED_END_LOCATION_LONG);
        assertThat(testTrip.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testTrip.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testTrip.isIsUploaded()).isEqualTo(UPDATED_IS_UPLOADED);
        assertThat(testTrip.isIsFinished()).isEqualTo(UPDATED_IS_FINISHED);
        assertThat(testTrip.isIsPublished()).isEqualTo(UPDATED_IS_PUBLISHED);
        assertThat(testTrip.getPublishedTime()).isEqualTo(UPDATED_PUBLISHED_TIME);
        assertThat(testTrip.isIsDeleted()).isEqualTo(UPDATED_IS_DELETED);
        assertThat(testTrip.getFeatureImageLocalUri()).isEqualTo(UPDATED_FEATURE_IMAGE_LOCAL_URI);
        assertThat(testTrip.getFeatureImageRemoteUri()).isEqualTo(UPDATED_FEATURE_IMAGE_REMOTE_URI);
        assertThat(testTrip.getNoOfDays()).isEqualTo(UPDATED_NO_OF_DAYS);
        assertThat(testTrip.getCreatedAt()).isEqualTo(UPDATED_CREATED_AT);
        assertThat(testTrip.getViewCount()).isEqualTo(UPDATED_VIEW_COUNT);
        assertThat(testTrip.getSecretKey()).isEqualTo(UPDATED_SECRET_KEY);
        assertThat(testTrip.getFeatured()).isEqualTo(UPDATED_FEATURED);
        assertThat(testTrip.isIsHidden()).isEqualTo(UPDATED_IS_HIDDEN);
    }

    @Test
    @Transactional
    public void updateNonExistingTrip() throws Exception {
        int databaseSizeBeforeUpdate = tripRepository.findAll().size();

        // Create the Trip
        TripDTO tripDTO = tripMapper.tripToTripDTO(trip);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTripMockMvc.perform(put("/api/trips")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripDTO)))
            .andExpect(status().isCreated());

        // Validate the Trip in the database
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTrip() throws Exception {
        // Initialize the database
        tripRepository.saveAndFlush(trip);
        int databaseSizeBeforeDelete = tripRepository.findAll().size();

        // Get the trip
        restTripMockMvc.perform(delete("/api/trips/{id}", trip.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Trip> tripList = tripRepository.findAll();
        assertThat(tripList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Trip.class);
    }
}
