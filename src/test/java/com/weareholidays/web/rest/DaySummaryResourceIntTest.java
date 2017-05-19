package com.weareholidays.web.rest;

import com.weareholidays.WeareholidaysApp;

import com.weareholidays.domain.DaySummary;
import com.weareholidays.repository.DaySummaryRepository;
import com.weareholidays.service.DaySummaryService;
import com.weareholidays.service.dto.DaySummaryDTO;
import com.weareholidays.service.mapper.DaySummaryMapper;
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
 * Test class for the DaySummaryResource REST controller.
 *
 * @see DaySummaryResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeareholidaysApp.class)
public class DaySummaryResourceIntTest {

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
    private DaySummaryRepository daySummaryRepository;

    @Autowired
    private DaySummaryMapper daySummaryMapper;

    @Autowired
    private DaySummaryService daySummaryService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDaySummaryMockMvc;

    private DaySummary daySummary;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DaySummaryResource daySummaryResource = new DaySummaryResource(daySummaryService);
        this.restDaySummaryMockMvc = MockMvcBuilders.standaloneSetup(daySummaryResource)
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
    public static DaySummary createEntity(EntityManager em) {
        DaySummary daySummary = new DaySummary()
            .twitter(DEFAULT_TWITTER)
            .fb(DEFAULT_FB)
            .instagram(DEFAULT_INSTAGRAM)
            .photos(DEFAULT_PHOTOS)
            .publicPhotos(DEFAULT_PUBLIC_PHOTOS)
            .notes(DEFAULT_NOTES)
            .videos(DEFAULT_VIDEOS)
            .checkIns(DEFAULT_CHECK_INS)
            .distance(DEFAULT_DISTANCE);
        return daySummary;
    }

    @Before
    public void initTest() {
        daySummary = createEntity(em);
    }

    @Test
    @Transactional
    public void createDaySummary() throws Exception {
        int databaseSizeBeforeCreate = daySummaryRepository.findAll().size();

        // Create the DaySummary
        DaySummaryDTO daySummaryDTO = daySummaryMapper.daySummaryToDaySummaryDTO(daySummary);
        restDaySummaryMockMvc.perform(post("/api/day-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(daySummaryDTO)))
            .andExpect(status().isCreated());

        // Validate the DaySummary in the database
        List<DaySummary> daySummaryList = daySummaryRepository.findAll();
        assertThat(daySummaryList).hasSize(databaseSizeBeforeCreate + 1);
        DaySummary testDaySummary = daySummaryList.get(daySummaryList.size() - 1);
        assertThat(testDaySummary.getTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testDaySummary.getFb()).isEqualTo(DEFAULT_FB);
        assertThat(testDaySummary.getInstagram()).isEqualTo(DEFAULT_INSTAGRAM);
        assertThat(testDaySummary.getPhotos()).isEqualTo(DEFAULT_PHOTOS);
        assertThat(testDaySummary.getPublicPhotos()).isEqualTo(DEFAULT_PUBLIC_PHOTOS);
        assertThat(testDaySummary.getNotes()).isEqualTo(DEFAULT_NOTES);
        assertThat(testDaySummary.getVideos()).isEqualTo(DEFAULT_VIDEOS);
        assertThat(testDaySummary.getCheckIns()).isEqualTo(DEFAULT_CHECK_INS);
        assertThat(testDaySummary.getDistance()).isEqualTo(DEFAULT_DISTANCE);
    }

    @Test
    @Transactional
    public void createDaySummaryWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = daySummaryRepository.findAll().size();

        // Create the DaySummary with an existing ID
        daySummary.setId(1L);
        DaySummaryDTO daySummaryDTO = daySummaryMapper.daySummaryToDaySummaryDTO(daySummary);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDaySummaryMockMvc.perform(post("/api/day-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(daySummaryDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<DaySummary> daySummaryList = daySummaryRepository.findAll();
        assertThat(daySummaryList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDaySummaries() throws Exception {
        // Initialize the database
        daySummaryRepository.saveAndFlush(daySummary);

        // Get all the daySummaryList
        restDaySummaryMockMvc.perform(get("/api/day-summaries?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(daySummary.getId().intValue())))
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
    public void getDaySummary() throws Exception {
        // Initialize the database
        daySummaryRepository.saveAndFlush(daySummary);

        // Get the daySummary
        restDaySummaryMockMvc.perform(get("/api/day-summaries/{id}", daySummary.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(daySummary.getId().intValue()))
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
    public void getNonExistingDaySummary() throws Exception {
        // Get the daySummary
        restDaySummaryMockMvc.perform(get("/api/day-summaries/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDaySummary() throws Exception {
        // Initialize the database
        daySummaryRepository.saveAndFlush(daySummary);
        int databaseSizeBeforeUpdate = daySummaryRepository.findAll().size();

        // Update the daySummary
        DaySummary updatedDaySummary = daySummaryRepository.findOne(daySummary.getId());
        updatedDaySummary
            .twitter(UPDATED_TWITTER)
            .fb(UPDATED_FB)
            .instagram(UPDATED_INSTAGRAM)
            .photos(UPDATED_PHOTOS)
            .publicPhotos(UPDATED_PUBLIC_PHOTOS)
            .notes(UPDATED_NOTES)
            .videos(UPDATED_VIDEOS)
            .checkIns(UPDATED_CHECK_INS)
            .distance(UPDATED_DISTANCE);
        DaySummaryDTO daySummaryDTO = daySummaryMapper.daySummaryToDaySummaryDTO(updatedDaySummary);

        restDaySummaryMockMvc.perform(put("/api/day-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(daySummaryDTO)))
            .andExpect(status().isOk());

        // Validate the DaySummary in the database
        List<DaySummary> daySummaryList = daySummaryRepository.findAll();
        assertThat(daySummaryList).hasSize(databaseSizeBeforeUpdate);
        DaySummary testDaySummary = daySummaryList.get(daySummaryList.size() - 1);
        assertThat(testDaySummary.getTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testDaySummary.getFb()).isEqualTo(UPDATED_FB);
        assertThat(testDaySummary.getInstagram()).isEqualTo(UPDATED_INSTAGRAM);
        assertThat(testDaySummary.getPhotos()).isEqualTo(UPDATED_PHOTOS);
        assertThat(testDaySummary.getPublicPhotos()).isEqualTo(UPDATED_PUBLIC_PHOTOS);
        assertThat(testDaySummary.getNotes()).isEqualTo(UPDATED_NOTES);
        assertThat(testDaySummary.getVideos()).isEqualTo(UPDATED_VIDEOS);
        assertThat(testDaySummary.getCheckIns()).isEqualTo(UPDATED_CHECK_INS);
        assertThat(testDaySummary.getDistance()).isEqualTo(UPDATED_DISTANCE);
    }

    @Test
    @Transactional
    public void updateNonExistingDaySummary() throws Exception {
        int databaseSizeBeforeUpdate = daySummaryRepository.findAll().size();

        // Create the DaySummary
        DaySummaryDTO daySummaryDTO = daySummaryMapper.daySummaryToDaySummaryDTO(daySummary);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDaySummaryMockMvc.perform(put("/api/day-summaries")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(daySummaryDTO)))
            .andExpect(status().isCreated());

        // Validate the DaySummary in the database
        List<DaySummary> daySummaryList = daySummaryRepository.findAll();
        assertThat(daySummaryList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDaySummary() throws Exception {
        // Initialize the database
        daySummaryRepository.saveAndFlush(daySummary);
        int databaseSizeBeforeDelete = daySummaryRepository.findAll().size();

        // Get the daySummary
        restDaySummaryMockMvc.perform(delete("/api/day-summaries/{id}", daySummary.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<DaySummary> daySummaryList = daySummaryRepository.findAll();
        assertThat(daySummaryList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(DaySummary.class);
    }
}
