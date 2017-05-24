package com.weareholidays.web.rest;

import com.weareholidays.WeareholidaysApp;

import com.weareholidays.domain.Media;
import com.weareholidays.repository.MediaRepository;
import com.weareholidays.service.MediaService;
import com.weareholidays.service.dto.MediaDTO;
import com.weareholidays.service.mapper.MediaMapper;
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
 * Test class for the MediaResource REST controller.
 *
 * @see MediaResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeareholidaysApp.class)
public class MediaResourceIntTest {

    private static final String DEFAULT_CONTENT_LOCAL_URL = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_LOCAL_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_REMOTE_URL = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_REMOTE_URL = "BBBBBBBBBB";

    private static final String DEFAULT_CAPTION = "AAAAAAAAAA";
    private static final String UPDATED_CAPTION = "BBBBBBBBBB";

    private static final Double DEFAULT_LOCATION_LAT = 1D;
    private static final Double UPDATED_LOCATION_LAT = 2D;

    private static final Double DEFAULT_LOCATION_LONG = 1D;
    private static final Double UPDATED_LOCATION_LONG = 2D;

    private static final Boolean DEFAULT_IS_PRIVATE = false;
    private static final Boolean UPDATED_IS_PRIVATE = true;

    private static final String DEFAULT_ADDRESS = "AAAAAAAAAA";
    private static final String UPDATED_ADDRESS = "BBBBBBBBBB";

    private static final Long DEFAULT_CONTENT_CREATION_TIME = 1L;
    private static final Long UPDATED_CONTENT_CREATION_TIME = 2L;

    private static final Long DEFAULT_CONTENT_SIZE = 1L;
    private static final Long UPDATED_CONTENT_SIZE = 2L;

    private static final Integer DEFAULT_MEDIA_WIDTH = 1;
    private static final Integer UPDATED_MEDIA_WIDTH = 2;

    private static final Integer DEFAULT_MEDIA_HEIGHT = 1;
    private static final Integer UPDATED_MEDIA_HEIGHT = 2;

    private static final String DEFAULT_THIRD_PARTY_ID = "AAAAAAAAAA";
    private static final String UPDATED_THIRD_PARTY_ID = "BBBBBBBBBB";

    private static final String DEFAULT_THIRD_PARTY_URL = "AAAAAAAAAA";
    private static final String UPDATED_THIRD_PARTY_URL = "BBBBBBBBBB";

    private static final String DEFAULT_MEDIA_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_MEDIA_SOURCE = "BBBBBBBBBB";

    private static final Boolean DEFAULT_FETCHING_ADDRESS = false;
    private static final Boolean UPDATED_FETCHING_ADDRESS = true;

    @Autowired
    private MediaRepository mediaRepository;

    @Autowired
    private MediaMapper mediaMapper;

    @Autowired
    private MediaService mediaService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMediaMockMvc;

    private Media media;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        MediaResource mediaResource = new MediaResource(mediaService);
        this.restMediaMockMvc = MockMvcBuilders.standaloneSetup(mediaResource)
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
    public static Media createEntity(EntityManager em) {
        Media media = new Media()
            .contentLocalUrl(DEFAULT_CONTENT_LOCAL_URL)
            .contentRemoteUrl(DEFAULT_CONTENT_REMOTE_URL)
            .caption(DEFAULT_CAPTION)
            .locationLat(DEFAULT_LOCATION_LAT)
            .locationLong(DEFAULT_LOCATION_LONG)
            .isPrivate(DEFAULT_IS_PRIVATE)
            .address(DEFAULT_ADDRESS)
            .contentCreationTime(DEFAULT_CONTENT_CREATION_TIME)
            .contentSize(DEFAULT_CONTENT_SIZE)
            .mediaWidth(DEFAULT_MEDIA_WIDTH)
            .mediaHeight(DEFAULT_MEDIA_HEIGHT)
            .thirdPartyId(DEFAULT_THIRD_PARTY_ID)
            .thirdPartyUrl(DEFAULT_THIRD_PARTY_URL)
            .mediaSource(DEFAULT_MEDIA_SOURCE)
            .fetchingAddress(DEFAULT_FETCHING_ADDRESS);
        return media;
    }

    @Before
    public void initTest() {
        media = createEntity(em);
    }

    @Test
    @Transactional
    public void createMedia() throws Exception {
        int databaseSizeBeforeCreate = mediaRepository.findAll().size();

        // Create the Media
        MediaDTO mediaDTO = mediaMapper.mediaToMediaDTO(media);
        restMediaMockMvc.perform(post("/api/media")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isCreated());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeCreate + 1);
        Media testMedia = mediaList.get(mediaList.size() - 1);
        assertThat(testMedia.getContentLocalUrl()).isEqualTo(DEFAULT_CONTENT_LOCAL_URL);
        assertThat(testMedia.getContentRemoteUrl()).isEqualTo(DEFAULT_CONTENT_REMOTE_URL);
        assertThat(testMedia.getCaption()).isEqualTo(DEFAULT_CAPTION);
        assertThat(testMedia.getLocationLat()).isEqualTo(DEFAULT_LOCATION_LAT);
        assertThat(testMedia.getLocationLong()).isEqualTo(DEFAULT_LOCATION_LONG);
        assertThat(testMedia.isIsPrivate()).isEqualTo(DEFAULT_IS_PRIVATE);
        assertThat(testMedia.getAddress()).isEqualTo(DEFAULT_ADDRESS);
        assertThat(testMedia.getContentCreationTime()).isEqualTo(DEFAULT_CONTENT_CREATION_TIME);
        assertThat(testMedia.getContentSize()).isEqualTo(DEFAULT_CONTENT_SIZE);
        assertThat(testMedia.getMediaWidth()).isEqualTo(DEFAULT_MEDIA_WIDTH);
        assertThat(testMedia.getMediaHeight()).isEqualTo(DEFAULT_MEDIA_HEIGHT);
        assertThat(testMedia.getThirdPartyId()).isEqualTo(DEFAULT_THIRD_PARTY_ID);
        assertThat(testMedia.getThirdPartyUrl()).isEqualTo(DEFAULT_THIRD_PARTY_URL);
        assertThat(testMedia.getMediaSource()).isEqualTo(DEFAULT_MEDIA_SOURCE);
        assertThat(testMedia.isFetchingAddress()).isEqualTo(DEFAULT_FETCHING_ADDRESS);
    }

    @Test
    @Transactional
    public void createMediaWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = mediaRepository.findAll().size();

        // Create the Media with an existing ID
        media.setId(1L);
        MediaDTO mediaDTO = mediaMapper.mediaToMediaDTO(media);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMediaMockMvc.perform(post("/api/media")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMedia() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);

        // Get all the mediaList
        restMediaMockMvc.perform(get("/api/media?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(media.getId().intValue())))
            .andExpect(jsonPath("$.[*].contentLocalUrl").value(hasItem(DEFAULT_CONTENT_LOCAL_URL.toString())))
            .andExpect(jsonPath("$.[*].contentRemoteUrl").value(hasItem(DEFAULT_CONTENT_REMOTE_URL.toString())))
            .andExpect(jsonPath("$.[*].caption").value(hasItem(DEFAULT_CAPTION.toString())))
            .andExpect(jsonPath("$.[*].locationLat").value(hasItem(DEFAULT_LOCATION_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].locationLong").value(hasItem(DEFAULT_LOCATION_LONG.doubleValue())))
            .andExpect(jsonPath("$.[*].isPrivate").value(hasItem(DEFAULT_IS_PRIVATE.booleanValue())))
            .andExpect(jsonPath("$.[*].address").value(hasItem(DEFAULT_ADDRESS.toString())))
            .andExpect(jsonPath("$.[*].contentCreationTime").value(hasItem(DEFAULT_CONTENT_CREATION_TIME.intValue())))
            .andExpect(jsonPath("$.[*].contentSize").value(hasItem(DEFAULT_CONTENT_SIZE.intValue())))
            .andExpect(jsonPath("$.[*].mediaWidth").value(hasItem(DEFAULT_MEDIA_WIDTH)))
            .andExpect(jsonPath("$.[*].mediaHeight").value(hasItem(DEFAULT_MEDIA_HEIGHT)))
            .andExpect(jsonPath("$.[*].thirdPartyId").value(hasItem(DEFAULT_THIRD_PARTY_ID.toString())))
            .andExpect(jsonPath("$.[*].thirdPartyUrl").value(hasItem(DEFAULT_THIRD_PARTY_URL.toString())))
            .andExpect(jsonPath("$.[*].mediaSource").value(hasItem(DEFAULT_MEDIA_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].fetchingAddress").value(hasItem(DEFAULT_FETCHING_ADDRESS.booleanValue())));
    }

    @Test
    @Transactional
    public void getMedia() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);

        // Get the media
        restMediaMockMvc.perform(get("/api/media/{id}", media.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(media.getId().intValue()))
            .andExpect(jsonPath("$.contentLocalUrl").value(DEFAULT_CONTENT_LOCAL_URL.toString()))
            .andExpect(jsonPath("$.contentRemoteUrl").value(DEFAULT_CONTENT_REMOTE_URL.toString()))
            .andExpect(jsonPath("$.caption").value(DEFAULT_CAPTION.toString()))
            .andExpect(jsonPath("$.locationLat").value(DEFAULT_LOCATION_LAT.doubleValue()))
            .andExpect(jsonPath("$.locationLong").value(DEFAULT_LOCATION_LONG.doubleValue()))
            .andExpect(jsonPath("$.isPrivate").value(DEFAULT_IS_PRIVATE.booleanValue()))
            .andExpect(jsonPath("$.address").value(DEFAULT_ADDRESS.toString()))
            .andExpect(jsonPath("$.contentCreationTime").value(DEFAULT_CONTENT_CREATION_TIME.intValue()))
            .andExpect(jsonPath("$.contentSize").value(DEFAULT_CONTENT_SIZE.intValue()))
            .andExpect(jsonPath("$.mediaWidth").value(DEFAULT_MEDIA_WIDTH))
            .andExpect(jsonPath("$.mediaHeight").value(DEFAULT_MEDIA_HEIGHT))
            .andExpect(jsonPath("$.thirdPartyId").value(DEFAULT_THIRD_PARTY_ID.toString()))
            .andExpect(jsonPath("$.thirdPartyUrl").value(DEFAULT_THIRD_PARTY_URL.toString()))
            .andExpect(jsonPath("$.mediaSource").value(DEFAULT_MEDIA_SOURCE.toString()))
            .andExpect(jsonPath("$.fetchingAddress").value(DEFAULT_FETCHING_ADDRESS.booleanValue()));
    }

    @Test
    @Transactional
    public void getNonExistingMedia() throws Exception {
        // Get the media
        restMediaMockMvc.perform(get("/api/media/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMedia() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);
        int databaseSizeBeforeUpdate = mediaRepository.findAll().size();

        // Update the media
        Media updatedMedia = mediaRepository.findOne(media.getId());
        updatedMedia
            .contentLocalUrl(UPDATED_CONTENT_LOCAL_URL)
            .contentRemoteUrl(UPDATED_CONTENT_REMOTE_URL)
            .caption(UPDATED_CAPTION)
            .locationLat(UPDATED_LOCATION_LAT)
            .locationLong(UPDATED_LOCATION_LONG)
            .isPrivate(UPDATED_IS_PRIVATE)
            .address(UPDATED_ADDRESS)
            .contentCreationTime(UPDATED_CONTENT_CREATION_TIME)
            .contentSize(UPDATED_CONTENT_SIZE)
            .mediaWidth(UPDATED_MEDIA_WIDTH)
            .mediaHeight(UPDATED_MEDIA_HEIGHT)
            .thirdPartyId(UPDATED_THIRD_PARTY_ID)
            .thirdPartyUrl(UPDATED_THIRD_PARTY_URL)
            .mediaSource(UPDATED_MEDIA_SOURCE)
            .fetchingAddress(UPDATED_FETCHING_ADDRESS);
        MediaDTO mediaDTO = mediaMapper.mediaToMediaDTO(updatedMedia);

        restMediaMockMvc.perform(put("/api/media")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isOk());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeUpdate);
        Media testMedia = mediaList.get(mediaList.size() - 1);
        assertThat(testMedia.getContentLocalUrl()).isEqualTo(UPDATED_CONTENT_LOCAL_URL);
        assertThat(testMedia.getContentRemoteUrl()).isEqualTo(UPDATED_CONTENT_REMOTE_URL);
        assertThat(testMedia.getCaption()).isEqualTo(UPDATED_CAPTION);
        assertThat(testMedia.getLocationLat()).isEqualTo(UPDATED_LOCATION_LAT);
        assertThat(testMedia.getLocationLong()).isEqualTo(UPDATED_LOCATION_LONG);
        assertThat(testMedia.isIsPrivate()).isEqualTo(UPDATED_IS_PRIVATE);
        assertThat(testMedia.getAddress()).isEqualTo(UPDATED_ADDRESS);
        assertThat(testMedia.getContentCreationTime()).isEqualTo(UPDATED_CONTENT_CREATION_TIME);
        assertThat(testMedia.getContentSize()).isEqualTo(UPDATED_CONTENT_SIZE);
        assertThat(testMedia.getMediaWidth()).isEqualTo(UPDATED_MEDIA_WIDTH);
        assertThat(testMedia.getMediaHeight()).isEqualTo(UPDATED_MEDIA_HEIGHT);
        assertThat(testMedia.getThirdPartyId()).isEqualTo(UPDATED_THIRD_PARTY_ID);
        assertThat(testMedia.getThirdPartyUrl()).isEqualTo(UPDATED_THIRD_PARTY_URL);
        assertThat(testMedia.getMediaSource()).isEqualTo(UPDATED_MEDIA_SOURCE);
        assertThat(testMedia.isFetchingAddress()).isEqualTo(UPDATED_FETCHING_ADDRESS);
    }

    @Test
    @Transactional
    public void updateNonExistingMedia() throws Exception {
        int databaseSizeBeforeUpdate = mediaRepository.findAll().size();

        // Create the Media
        MediaDTO mediaDTO = mediaMapper.mediaToMediaDTO(media);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMediaMockMvc.perform(put("/api/media")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(mediaDTO)))
            .andExpect(status().isCreated());

        // Validate the Media in the database
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMedia() throws Exception {
        // Initialize the database
        mediaRepository.saveAndFlush(media);
        int databaseSizeBeforeDelete = mediaRepository.findAll().size();

        // Get the media
        restMediaMockMvc.perform(delete("/api/media/{id}", media.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Media> mediaList = mediaRepository.findAll();
        assertThat(mediaList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Media.class);
    }
}
