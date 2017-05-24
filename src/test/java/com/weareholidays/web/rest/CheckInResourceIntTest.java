package com.weareholidays.web.rest;

import com.weareholidays.WeareholidaysApp;

import com.weareholidays.domain.CheckIn;
import com.weareholidays.repository.CheckInRepository;
import com.weareholidays.service.CheckInService;
import com.weareholidays.service.dto.CheckInDTO;
import com.weareholidays.service.mapper.CheckInMapper;
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
 * Test class for the CheckInResource REST controller.
 *
 * @see CheckInResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeareholidaysApp.class)
public class CheckInResourceIntTest {

    private static final Double DEFAULT_LOCATION_LAT = 1D;
    private static final Double UPDATED_LOCATION_LAT = 2D;

    private static final Double DEFAULT_LOCATION_LONG = 1D;
    private static final Double UPDATED_LOCATION_LONG = 2D;

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final String DEFAULT_LOCATION_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_TEXT = "BBBBBBBBBB";

    private static final String DEFAULT_PLACE_ID = "AAAAAAAAAA";
    private static final String UPDATED_PLACE_ID = "BBBBBBBBBB";

    private static final String DEFAULT_PHOTO_REFERENCE = "AAAAAAAAAA";
    private static final String UPDATED_PHOTO_REFERENCE = "BBBBBBBBBB";

    private static final String DEFAULT_MAP_IMAGE_LOCAL_URI = "AAAAAAAAAA";
    private static final String UPDATED_MAP_IMAGE_LOCAL_URI = "BBBBBBBBBB";

    private static final String DEFAULT_MAP_IMAGE_REMOTE_URI = "AAAAAAAAAA";
    private static final String UPDATED_MAP_IMAGE_REMOTE_URI = "BBBBBBBBBB";

    @Autowired
    private CheckInRepository checkInRepository;

    @Autowired
    private CheckInMapper checkInMapper;

    @Autowired
    private CheckInService checkInService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCheckInMockMvc;

    private CheckIn checkIn;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CheckInResource checkInResource = new CheckInResource(checkInService);
        this.restCheckInMockMvc = MockMvcBuilders.standaloneSetup(checkInResource)
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
    public static CheckIn createEntity(EntityManager em) {
        CheckIn checkIn = new CheckIn()
            .locationLat(DEFAULT_LOCATION_LAT)
            .locationLong(DEFAULT_LOCATION_LONG)
            .name(DEFAULT_NAME)
            .locationText(DEFAULT_LOCATION_TEXT)
            .placeId(DEFAULT_PLACE_ID)
            .photoReference(DEFAULT_PHOTO_REFERENCE)
            .mapImageLocalUri(DEFAULT_MAP_IMAGE_LOCAL_URI)
            .mapImageRemoteUri(DEFAULT_MAP_IMAGE_REMOTE_URI);
        return checkIn;
    }

    @Before
    public void initTest() {
        checkIn = createEntity(em);
    }

    @Test
    @Transactional
    public void createCheckIn() throws Exception {
        int databaseSizeBeforeCreate = checkInRepository.findAll().size();

        // Create the CheckIn
        CheckInDTO checkInDTO = checkInMapper.checkInToCheckInDTO(checkIn);
        restCheckInMockMvc.perform(post("/api/check-ins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkInDTO)))
            .andExpect(status().isCreated());

        // Validate the CheckIn in the database
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeCreate + 1);
        CheckIn testCheckIn = checkInList.get(checkInList.size() - 1);
        assertThat(testCheckIn.getLocationLat()).isEqualTo(DEFAULT_LOCATION_LAT);
        assertThat(testCheckIn.getLocationLong()).isEqualTo(DEFAULT_LOCATION_LONG);
        assertThat(testCheckIn.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testCheckIn.getLocationText()).isEqualTo(DEFAULT_LOCATION_TEXT);
        assertThat(testCheckIn.getPlaceId()).isEqualTo(DEFAULT_PLACE_ID);
        assertThat(testCheckIn.getPhotoReference()).isEqualTo(DEFAULT_PHOTO_REFERENCE);
        assertThat(testCheckIn.getMapImageLocalUri()).isEqualTo(DEFAULT_MAP_IMAGE_LOCAL_URI);
        assertThat(testCheckIn.getMapImageRemoteUri()).isEqualTo(DEFAULT_MAP_IMAGE_REMOTE_URI);
    }

    @Test
    @Transactional
    public void createCheckInWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = checkInRepository.findAll().size();

        // Create the CheckIn with an existing ID
        checkIn.setId(1L);
        CheckInDTO checkInDTO = checkInMapper.checkInToCheckInDTO(checkIn);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCheckInMockMvc.perform(post("/api/check-ins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkInDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCheckIns() throws Exception {
        // Initialize the database
        checkInRepository.saveAndFlush(checkIn);

        // Get all the checkInList
        restCheckInMockMvc.perform(get("/api/check-ins?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(checkIn.getId().intValue())))
            .andExpect(jsonPath("$.[*].locationLat").value(hasItem(DEFAULT_LOCATION_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].locationLong").value(hasItem(DEFAULT_LOCATION_LONG.doubleValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].locationText").value(hasItem(DEFAULT_LOCATION_TEXT.toString())))
            .andExpect(jsonPath("$.[*].placeId").value(hasItem(DEFAULT_PLACE_ID.toString())))
            .andExpect(jsonPath("$.[*].photoReference").value(hasItem(DEFAULT_PHOTO_REFERENCE.toString())))
            .andExpect(jsonPath("$.[*].mapImageLocalUri").value(hasItem(DEFAULT_MAP_IMAGE_LOCAL_URI.toString())))
            .andExpect(jsonPath("$.[*].mapImageRemoteUri").value(hasItem(DEFAULT_MAP_IMAGE_REMOTE_URI.toString())));
    }

    @Test
    @Transactional
    public void getCheckIn() throws Exception {
        // Initialize the database
        checkInRepository.saveAndFlush(checkIn);

        // Get the checkIn
        restCheckInMockMvc.perform(get("/api/check-ins/{id}", checkIn.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(checkIn.getId().intValue()))
            .andExpect(jsonPath("$.locationLat").value(DEFAULT_LOCATION_LAT.doubleValue()))
            .andExpect(jsonPath("$.locationLong").value(DEFAULT_LOCATION_LONG.doubleValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.locationText").value(DEFAULT_LOCATION_TEXT.toString()))
            .andExpect(jsonPath("$.placeId").value(DEFAULT_PLACE_ID.toString()))
            .andExpect(jsonPath("$.photoReference").value(DEFAULT_PHOTO_REFERENCE.toString()))
            .andExpect(jsonPath("$.mapImageLocalUri").value(DEFAULT_MAP_IMAGE_LOCAL_URI.toString()))
            .andExpect(jsonPath("$.mapImageRemoteUri").value(DEFAULT_MAP_IMAGE_REMOTE_URI.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCheckIn() throws Exception {
        // Get the checkIn
        restCheckInMockMvc.perform(get("/api/check-ins/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCheckIn() throws Exception {
        // Initialize the database
        checkInRepository.saveAndFlush(checkIn);
        int databaseSizeBeforeUpdate = checkInRepository.findAll().size();

        // Update the checkIn
        CheckIn updatedCheckIn = checkInRepository.findOne(checkIn.getId());
        updatedCheckIn
            .locationLat(UPDATED_LOCATION_LAT)
            .locationLong(UPDATED_LOCATION_LONG)
            .name(UPDATED_NAME)
            .locationText(UPDATED_LOCATION_TEXT)
            .placeId(UPDATED_PLACE_ID)
            .photoReference(UPDATED_PHOTO_REFERENCE)
            .mapImageLocalUri(UPDATED_MAP_IMAGE_LOCAL_URI)
            .mapImageRemoteUri(UPDATED_MAP_IMAGE_REMOTE_URI);
        CheckInDTO checkInDTO = checkInMapper.checkInToCheckInDTO(updatedCheckIn);

        restCheckInMockMvc.perform(put("/api/check-ins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkInDTO)))
            .andExpect(status().isOk());

        // Validate the CheckIn in the database
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeUpdate);
        CheckIn testCheckIn = checkInList.get(checkInList.size() - 1);
        assertThat(testCheckIn.getLocationLat()).isEqualTo(UPDATED_LOCATION_LAT);
        assertThat(testCheckIn.getLocationLong()).isEqualTo(UPDATED_LOCATION_LONG);
        assertThat(testCheckIn.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testCheckIn.getLocationText()).isEqualTo(UPDATED_LOCATION_TEXT);
        assertThat(testCheckIn.getPlaceId()).isEqualTo(UPDATED_PLACE_ID);
        assertThat(testCheckIn.getPhotoReference()).isEqualTo(UPDATED_PHOTO_REFERENCE);
        assertThat(testCheckIn.getMapImageLocalUri()).isEqualTo(UPDATED_MAP_IMAGE_LOCAL_URI);
        assertThat(testCheckIn.getMapImageRemoteUri()).isEqualTo(UPDATED_MAP_IMAGE_REMOTE_URI);
    }

    @Test
    @Transactional
    public void updateNonExistingCheckIn() throws Exception {
        int databaseSizeBeforeUpdate = checkInRepository.findAll().size();

        // Create the CheckIn
        CheckInDTO checkInDTO = checkInMapper.checkInToCheckInDTO(checkIn);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCheckInMockMvc.perform(put("/api/check-ins")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(checkInDTO)))
            .andExpect(status().isCreated());

        // Validate the CheckIn in the database
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCheckIn() throws Exception {
        // Initialize the database
        checkInRepository.saveAndFlush(checkIn);
        int databaseSizeBeforeDelete = checkInRepository.findAll().size();

        // Get the checkIn
        restCheckInMockMvc.perform(delete("/api/check-ins/{id}", checkIn.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<CheckIn> checkInList = checkInRepository.findAll();
        assertThat(checkInList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckIn.class);
    }
}
