package com.weareholidays.web.rest;

import com.weareholidays.WeareholidaysApp;

import com.weareholidays.domain.TripSummary;
import com.weareholidays.repository.TripSummaryRepository;
import com.weareholidays.service.TripSummaryService;
import com.weareholidays.service.dto.TripSummaryDTO;
import com.weareholidays.service.mapper.TripSummaryMapper;
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
 * Test class for the TripSummaryResource REST controller.
 *
 * @see TripSummaryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeareholidaysApp.class)
public class TripSummaryResourceIntTest {

    private static final Integer DEFAULT_TWITTER = 1;
    private static final Integer UPDATED_TWITTER = 2;

    private static final Integer DEFAULT_FB = 1;
    private static final Integer UPDATED_FB = 2;

    private static final Integer DEFAULT_INSTAGRAM = 1;
    private static final Integer UPDATED_INSTAGRAM = 2;

    private static final Integer DEFAULT_PHOTOS = 1;
    private static final Integer UPDATED_PHOTOS = 2;

    private static final Integer DEFAULT_PUBLIC_PHOTOS = 1;
    private static final Integer UPDATED_PUBLIC_PHOTOS = 2;

    private static final Integer DEFAULT_NOTES = 1;
    private static final Integer UPDATED_NOTES = 2;

    private static final Integer DEFAULT_VIDEOS = 1;
    private static final Integer UPDATED_VIDEOS = 2;

    private static final Integer DEFAULT_CHECK_INS = 1;
    private static final Integer UPDATED_CHECK_INS = 2;

    private static final Double DEFAULT_DISTANCE = 1D;
    private static final Double UPDATED_DISTANCE = 2D;

    @Autowired
    private TripSummaryRepository tripSummaryRepository;

    @Autowired
    private TripSummaryMapper tripSummaryMapper;

    @Autowired
    private TripSummaryService tripSummaryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTripSummaryMockMvc;

    private TripSummary tripSummary;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TripSummaryResource tripSummaryResource = new TripSummaryResource(tripSummaryService);
        this.restTripSummaryMockMvc = MockMvcBuilders.standaloneSetup(tripSummaryResource)
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
    public static TripSummary createEntity(EntityManager em) {
        TripSummary tripSummary = new TripSummary()
            .twitter(DEFAULT_TWITTER)
            .fb(DEFAULT_FB)
            .instagram(DEFAULT_INSTAGRAM)
            .photos(DEFAULT_PHOTOS)
            .publicPhotos(DEFAULT_PUBLIC_PHOTOS)
            .notes(DEFAULT_NOTES)
            .videos(DEFAULT_VIDEOS)
            .checkIns(DEFAULT_CHECK_INS)
            .distance(DEFAULT_DISTANCE);
        return tripSummary;
    }

    @Before
    public void initTest() {
        tripSummary = createEntity(em);
    }

    @Test
    @Transactional
    public void createTripSummary() throws Exception {
        int databaseSizeBeforeCreate = tripSummaryRepository.findAll().size();

        // Create the TripSummary
        TripSummaryDTO tripSummaryDTO = tripSummaryMapper.tripSummaryToTripSummaryDTO(tripSummary);
        restTripSummaryMockMvc.perform(post("/api/trip-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripSummaryDTO)))
            .andExpect(status().isCreated());

        // Validate the TripSummary in the database
        List<TripSummary> tripSummaryList = tripSummaryRepository.findAll();
        assertThat(tripSummaryList).hasSize(databaseSizeBeforeCreate + 1);
        TripSummary testTripSummary = tripSummaryList.get(tripSummaryList.size() - 1);
        assertThat(testTripSummary.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testTripSummary.getFb()).isEqualTo(DEFAULT_FB);
        assertThat(testTripSummary.getInstagram()).isEqualTo(DEFAULT_INSTAGRAM);
        assertThat(testTripSummary.getPhotos()).isEqualTo(DEFAULT_PHOTOS);
        assertThat(testTripSummary.getPublicPhotos()).isEqualTo(DEFAULT_PUBLIC_PHOTOS);
        assertThat(testTripSummary.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testTripSummary.getVideos()).isEqualTo(DEFAULT_VIDEOS);
        assertThat(testTripSummary.getCheckIns()).isEqualTo(DEFAULT_CHECK_INS);
        assertThat(testTripSummary.getDistance()).isEqualTo(DEFAULT_DISTANCE);
    }

    @Test
    @Transactional
    public void createTripSummaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tripSummaryRepository.findAll().size();

        // Create the TripSummary with an existing ID
        tripSummary.setId(1L);
        TripSummaryDTO tripSummaryDTO = tripSummaryMapper.tripSummaryToTripSummaryDTO(tripSummary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTripSummaryMockMvc.perform(post("/api/trip-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripSummaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TripSummary> tripSummaryList = tripSummaryRepository.findAll();
        assertThat(tripSummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTripSummaries() throws Exception {
        // Initialize the database
        tripSummaryRepository.saveAndFlush(tripSummary);

        // Get all the tripSummaryList
        restTripSummaryMockMvc.perform(get("/api/trip-summaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tripSummary.getId().intValue())))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER)))
            .andExpect(jsonPath("$.[*].fb").value(hasItem(DEFAULT_FB)))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM)))
            .andExpect(jsonPath("$.[*].photos").value(hasItem(DEFAULT_PHOTOS)))
            .andExpect(jsonPath("$.[*].publicPhotos").value(hasItem(DEFAULT_PUBLIC_PHOTOS)))
            .andExpect(jsonPath("$.[*].notes").value(hasItem(DEFAULT_NOTES)))
            .andExpect(jsonPath("$.[*].videos").value(hasItem(DEFAULT_VIDEOS)))
            .andExpect(jsonPath("$.[*].checkIns").value(hasItem(DEFAULT_CHECK_INS)))
            .andExpect(jsonPath("$.[*].distance").value(hasItem(DEFAULT_DISTANCE.doubleValue())));
    }

    @Test
    @Transactional
    public void getTripSummary() throws Exception {
        // Initialize the database
        tripSummaryRepository.saveAndFlush(tripSummary);

        // Get the tripSummary
        restTripSummaryMockMvc.perform(get("/api/trip-summaries/{id}", tripSummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tripSummary.getId().intValue()))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER))
            .andExpect(jsonPath("$.fb").value(DEFAULT_FB))
            .andExpect(jsonPath("$.instagram").value(DEFAULT_INSTAGRAM))
            .andExpect(jsonPath("$.photos").value(DEFAULT_PHOTOS))
            .andExpect(jsonPath("$.publicPhotos").value(DEFAULT_PUBLIC_PHOTOS))
            .andExpect(jsonPath("$.notes").value(DEFAULT_NOTES))
            .andExpect(jsonPath("$.videos").value(DEFAULT_VIDEOS))
            .andExpect(jsonPath("$.checkIns").value(DEFAULT_CHECK_INS))
            .andExpect(jsonPath("$.distance").value(DEFAULT_DISTANCE.doubleValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTripSummary() throws Exception {
        // Get the tripSummary
        restTripSummaryMockMvc.perform(get("/api/trip-summaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTripSummary() throws Exception {
        // Initialize the database
        tripSummaryRepository.saveAndFlush(tripSummary);
        int databaseSizeBeforeUpdate = tripSummaryRepository.findAll().size();

        // Update the tripSummary
        TripSummary updatedTripSummary = tripSummaryRepository.findOne(tripSummary.getId());
        updatedTripSummary
            .twitter(UPDATED_TWITTER)
            .fb(UPDATED_FB)
            .instagram(UPDATED_INSTAGRAM)
            .photos(UPDATED_PHOTOS)
            .publicPhotos(UPDATED_PUBLIC_PHOTOS)
            .notes(UPDATED_NOTES)
            .videos(UPDATED_VIDEOS)
            .checkIns(UPDATED_CHECK_INS)
            .distance(UPDATED_DISTANCE);
        TripSummaryDTO tripSummaryDTO = tripSummaryMapper.tripSummaryToTripSummaryDTO(updatedTripSummary);

        restTripSummaryMockMvc.perform(put("/api/trip-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripSummaryDTO)))
            .andExpect(status().isOk());

        // Validate the TripSummary in the database
        List<TripSummary> tripSummaryList = tripSummaryRepository.findAll();
        assertThat(tripSummaryList).hasSize(databaseSizeBeforeUpdate);
        TripSummary testTripSummary = tripSummaryList.get(tripSummaryList.size() - 1);
        assertThat(testTripSummary.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testTripSummary.getFb()).isEqualTo(UPDATED_FB);
        assertThat(testTripSummary.getInstagram()).isEqualTo(UPDATED_INSTAGRAM);
        assertThat(testTripSummary.getPhotos()).isEqualTo(UPDATED_PHOTOS);
        assertThat(testTripSummary.getPublicPhotos()).isEqualTo(UPDATED_PUBLIC_PHOTOS);
        assertThat(testTripSummary.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testTripSummary.getVideos()).isEqualTo(UPDATED_VIDEOS);
        assertThat(testTripSummary.getCheckIns()).isEqualTo(UPDATED_CHECK_INS);
        assertThat(testTripSummary.getDistance()).isEqualTo(UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingTripSummary() throws Exception {
        int databaseSizeBeforeUpdate = tripSummaryRepository.findAll().size();

        // Create the TripSummary
        TripSummaryDTO tripSummaryDTO = tripSummaryMapper.tripSummaryToTripSummaryDTO(tripSummary);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTripSummaryMockMvc.perform(put("/api/trip-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripSummaryDTO)))
            .andExpect(status().isCreated());

        // Validate the TripSummary in the database
        List<TripSummary> tripSummaryList = tripSummaryRepository.findAll();
        assertThat(tripSummaryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTripSummary() throws Exception {
        // Initialize the database
        tripSummaryRepository.saveAndFlush(tripSummary);
        int databaseSizeBeforeDelete = tripSummaryRepository.findAll().size();

        // Get the tripSummary
        restTripSummaryMockMvc.perform(delete("/api/trip-summaries/{id}", tripSummary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TripSummary> tripSummaryList = tripSummaryRepository.findAll();
        assertThat(tripSummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TripSummary.class);
    }
}
