package com.weareholidays.web.rest;

import com.weareholidays.WeareholidaysApp;

import com.weareholidays.domain.TripSettings;
import com.weareholidays.repository.TripSettingsRepository;
import com.weareholidays.service.TripSettingsService;
import com.weareholidays.service.dto.TripSettingsDTO;
import com.weareholidays.service.mapper.TripSettingsMapper;
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
 * Test class for the TripSettingsResource REST controller.
 *
 * @see TripSettingsResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeareholidaysApp.class)
public class TripSettingsResourceIntTest {

    private static final Boolean DEFAULT_FACEBOOK = false;
    private static final Boolean UPDATED_FACEBOOK = true;

    private static final Boolean DEFAULT_TWITTER = false;
    private static final Boolean UPDATED_TWITTER = true;

    private static final Boolean DEFAULT_INSTAGRAM = false;
    private static final Boolean UPDATED_INSTAGRAM = true;

    private static final Boolean DEFAULT_LOCATION = false;
    private static final Boolean UPDATED_LOCATION = true;

    private static final Boolean DEFAULT_SYNC = false;
    private static final Boolean UPDATED_SYNC = true;

    private static final Boolean DEFAULT_CHECK_IN = false;
    private static final Boolean UPDATED_CHECK_IN = true;

    private static final Boolean DEFAULT_CAMER_ROLL = false;
    private static final Boolean UPDATED_CAMER_ROLL = true;

    private static final Long DEFAULT_CAMERA_ROLL_SYNC_TIME = 1L;
    private static final Long UPDATED_CAMERA_ROLL_SYNC_TIME = 2L;

    private static final Long DEFAULT_FACEBOOK_SYNC_TIME = 1L;
    private static final Long UPDATED_FACEBOOK_SYNC_TIME = 2L;

    private static final Long DEFAULT_TWITTER_SINCE_ID = 1L;
    private static final Long UPDATED_TWITTER_SINCE_ID = 2L;

    private static final Long DEFAULT_INSTAGRAM_SYNC_TIME = 1L;
    private static final Long UPDATED_INSTAGRAM_SYNC_TIME = 2L;

    @Autowired
    private TripSettingsRepository tripSettingsRepository;

    @Autowired
    private TripSettingsMapper tripSettingsMapper;

    @Autowired
    private TripSettingsService tripSettingsService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTripSettingsMockMvc;

    private TripSettings tripSettings;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TripSettingsResource tripSettingsResource = new TripSettingsResource(tripSettingsService);
        this.restTripSettingsMockMvc = MockMvcBuilders.standaloneSetup(tripSettingsResource)
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
    public static TripSettings createEntity(EntityManager em) {
        TripSettings tripSettings = new TripSettings()
            .facebook(DEFAULT_FACEBOOK)
            .twitter(DEFAULT_TWITTER)
            .instagram(DEFAULT_INSTAGRAM)
            .location(DEFAULT_LOCATION)
            .sync(DEFAULT_SYNC)
            .checkIn(DEFAULT_CHECK_IN)
            .camerRoll(DEFAULT_CAMER_ROLL)
            .cameraRollSyncTime(DEFAULT_CAMERA_ROLL_SYNC_TIME)
            .facebookSyncTime(DEFAULT_FACEBOOK_SYNC_TIME)
            .twitterSinceId(DEFAULT_TWITTER_SINCE_ID)
            .instagramSyncTime(DEFAULT_INSTAGRAM_SYNC_TIME);
        return tripSettings;
    }

    @Before
    public void initTest() {
        tripSettings = createEntity(em);
    }

    @Test
    @Transactional
    public void createTripSettings() throws Exception {
        int databaseSizeBeforeCreate = tripSettingsRepository.findAll().size();

        // Create the TripSettings
        TripSettingsDTO tripSettingsDTO = tripSettingsMapper.tripSettingsToTripSettingsDTO(tripSettings);
        restTripSettingsMockMvc.perform(post("/api/trip-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripSettingsDTO)))
            .andExpect(status().isCreated());

        // Validate the TripSettings in the database
        List<TripSettings> tripSettingsList = tripSettingsRepository.findAll();
        assertThat(tripSettingsList).hasSize(databaseSizeBeforeCreate + 1);
        TripSettings testTripSettings = tripSettingsList.get(tripSettingsList.size() - 1);
        assertThat(testTripSettings.isFacebook()).isEqualTo(DEFAULT_FACEBOOK);
        assertThat(testTripSettings.isTwitter()).isEqualTo(DEFAULT_TWITTER);
        assertThat(testTripSettings.isInstagram()).isEqualTo(DEFAULT_INSTAGRAM);
        assertThat(testTripSettings.isLocation()).isEqualTo(DEFAULT_LOCATION);
        assertThat(testTripSettings.isSync()).isEqualTo(DEFAULT_SYNC);
        assertThat(testTripSettings.isCheckIn()).isEqualTo(DEFAULT_CHECK_IN);
        assertThat(testTripSettings.isCamerRoll()).isEqualTo(DEFAULT_CAMER_ROLL);
        assertThat(testTripSettings.getCameraRollSyncTime()).isEqualTo(DEFAULT_CAMERA_ROLL_SYNC_TIME);
        assertThat(testTripSettings.getFacebookSyncTime()).isEqualTo(DEFAULT_FACEBOOK_SYNC_TIME);
        assertThat(testTripSettings.getTwitterSinceId()).isEqualTo(DEFAULT_TWITTER_SINCE_ID);
        assertThat(testTripSettings.getInstagramSyncTime()).isEqualTo(DEFAULT_INSTAGRAM_SYNC_TIME);
    }

    @Test
    @Transactional
    public void createTripSettingsWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = tripSettingsRepository.findAll().size();

        // Create the TripSettings with an existing ID
        tripSettings.setId(1L);
        TripSettingsDTO tripSettingsDTO = tripSettingsMapper.tripSettingsToTripSettingsDTO(tripSettings);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTripSettingsMockMvc.perform(post("/api/trip-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripSettingsDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<TripSettings> tripSettingsList = tripSettingsRepository.findAll();
        assertThat(tripSettingsList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTripSettings() throws Exception {
        // Initialize the database
        tripSettingsRepository.saveAndFlush(tripSettings);

        // Get all the tripSettingsList
        restTripSettingsMockMvc.perform(get("/api/trip-settings?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(tripSettings.getId().intValue())))
            .andExpect(jsonPath("$.[*].facebook").value(hasItem(DEFAULT_FACEBOOK.booleanValue())))
            .andExpect(jsonPath("$.[*].twitter").value(hasItem(DEFAULT_TWITTER.booleanValue())))
            .andExpect(jsonPath("$.[*].instagram").value(hasItem(DEFAULT_INSTAGRAM.booleanValue())))
            .andExpect(jsonPath("$.[*].location").value(hasItem(DEFAULT_LOCATION.booleanValue())))
            .andExpect(jsonPath("$.[*].sync").value(hasItem(DEFAULT_SYNC.booleanValue())))
            .andExpect(jsonPath("$.[*].checkIn").value(hasItem(DEFAULT_CHECK_IN.booleanValue())))
            .andExpect(jsonPath("$.[*].camerRoll").value(hasItem(DEFAULT_CAMER_ROLL.booleanValue())))
            .andExpect(jsonPath("$.[*].cameraRollSyncTime").value(hasItem(DEFAULT_CAMERA_ROLL_SYNC_TIME.intValue())))
            .andExpect(jsonPath("$.[*].facebookSyncTime").value(hasItem(DEFAULT_FACEBOOK_SYNC_TIME.intValue())))
            .andExpect(jsonPath("$.[*].twitterSinceId").value(hasItem(DEFAULT_TWITTER_SINCE_ID.intValue())))
            .andExpect(jsonPath("$.[*].instagramSyncTime").value(hasItem(DEFAULT_INSTAGRAM_SYNC_TIME.intValue())));
    }

    @Test
    @Transactional
    public void getTripSettings() throws Exception {
        // Initialize the database
        tripSettingsRepository.saveAndFlush(tripSettings);

        // Get the tripSettings
        restTripSettingsMockMvc.perform(get("/api/trip-settings/{id}", tripSettings.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(tripSettings.getId().intValue()))
            .andExpect(jsonPath("$.facebook").value(DEFAULT_FACEBOOK.booleanValue()))
            .andExpect(jsonPath("$.twitter").value(DEFAULT_TWITTER.booleanValue()))
            .andExpect(jsonPath("$.instagram").value(DEFAULT_INSTAGRAM.booleanValue()))
            .andExpect(jsonPath("$.location").value(DEFAULT_LOCATION.booleanValue()))
            .andExpect(jsonPath("$.sync").value(DEFAULT_SYNC.booleanValue()))
            .andExpect(jsonPath("$.checkIn").value(DEFAULT_CHECK_IN.booleanValue()))
            .andExpect(jsonPath("$.camerRoll").value(DEFAULT_CAMER_ROLL.booleanValue()))
            .andExpect(jsonPath("$.cameraRollSyncTime").value(DEFAULT_CAMERA_ROLL_SYNC_TIME.intValue()))
            .andExpect(jsonPath("$.facebookSyncTime").value(DEFAULT_FACEBOOK_SYNC_TIME.intValue()))
            .andExpect(jsonPath("$.twitterSinceId").value(DEFAULT_TWITTER_SINCE_ID.intValue()))
            .andExpect(jsonPath("$.instagramSyncTime").value(DEFAULT_INSTAGRAM_SYNC_TIME.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTripSettings() throws Exception {
        // Get the tripSettings
        restTripSettingsMockMvc.perform(get("/api/trip-settings/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTripSettings() throws Exception {
        // Initialize the database
        tripSettingsRepository.saveAndFlush(tripSettings);
        int databaseSizeBeforeUpdate = tripSettingsRepository.findAll().size();

        // Update the tripSettings
        TripSettings updatedTripSettings = tripSettingsRepository.findOne(tripSettings.getId());
        updatedTripSettings
            .facebook(UPDATED_FACEBOOK)
            .twitter(UPDATED_TWITTER)
            .instagram(UPDATED_INSTAGRAM)
            .location(UPDATED_LOCATION)
            .sync(UPDATED_SYNC)
            .checkIn(UPDATED_CHECK_IN)
            .camerRoll(UPDATED_CAMER_ROLL)
            .cameraRollSyncTime(UPDATED_CAMERA_ROLL_SYNC_TIME)
            .facebookSyncTime(UPDATED_FACEBOOK_SYNC_TIME)
            .twitterSinceId(UPDATED_TWITTER_SINCE_ID)
            .instagramSyncTime(UPDATED_INSTAGRAM_SYNC_TIME);
        TripSettingsDTO tripSettingsDTO = tripSettingsMapper.tripSettingsToTripSettingsDTO(updatedTripSettings);

        restTripSettingsMockMvc.perform(put("/api/trip-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripSettingsDTO)))
            .andExpect(status().isOk());

        // Validate the TripSettings in the database
        List<TripSettings> tripSettingsList = tripSettingsRepository.findAll();
        assertThat(tripSettingsList).hasSize(databaseSizeBeforeUpdate);
        TripSettings testTripSettings = tripSettingsList.get(tripSettingsList.size() - 1);
        assertThat(testTripSettings.isFacebook()).isEqualTo(UPDATED_FACEBOOK);
        assertThat(testTripSettings.isTwitter()).isEqualTo(UPDATED_TWITTER);
        assertThat(testTripSettings.isInstagram()).isEqualTo(UPDATED_INSTAGRAM);
        assertThat(testTripSettings.isLocation()).isEqualTo(UPDATED_LOCATION);
        assertThat(testTripSettings.isSync()).isEqualTo(UPDATED_SYNC);
        assertThat(testTripSettings.isCheckIn()).isEqualTo(UPDATED_CHECK_IN);
        assertThat(testTripSettings.isCamerRoll()).isEqualTo(UPDATED_CAMER_ROLL);
        assertThat(testTripSettings.getCameraRollSyncTime()).isEqualTo(UPDATED_CAMERA_ROLL_SYNC_TIME);
        assertThat(testTripSettings.getFacebookSyncTime()).isEqualTo(UPDATED_FACEBOOK_SYNC_TIME);
        assertThat(testTripSettings.getTwitterSinceId()).isEqualTo(UPDATED_TWITTER_SINCE_ID);
        assertThat(testTripSettings.getInstagramSyncTime()).isEqualTo(UPDATED_INSTAGRAM_SYNC_TIME);
    }

    @Test
    @Transactional
    public void updateNonExistingTripSettings() throws Exception {
        int databaseSizeBeforeUpdate = tripSettingsRepository.findAll().size();

        // Create the TripSettings
        TripSettingsDTO tripSettingsDTO = tripSettingsMapper.tripSettingsToTripSettingsDTO(tripSettings);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTripSettingsMockMvc.perform(put("/api/trip-settings")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(tripSettingsDTO)))
            .andExpect(status().isCreated());

        // Validate the TripSettings in the database
        List<TripSettings> tripSettingsList = tripSettingsRepository.findAll();
        assertThat(tripSettingsList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTripSettings() throws Exception {
        // Initialize the database
        tripSettingsRepository.saveAndFlush(tripSettings);
        int databaseSizeBeforeDelete = tripSettingsRepository.findAll().size();

        // Get the tripSettings
        restTripSettingsMockMvc.perform(delete("/api/trip-settings/{id}", tripSettings.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<TripSettings> tripSettingsList = tripSettingsRepository.findAll();
        assertThat(tripSettingsList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TripSettings.class);
    }
}
