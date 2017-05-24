package com.weareholidays.web.rest;

import com.weareholidays.WeareholidaysApp;

import com.weareholidays.domain.Album;
import com.weareholidays.repository.AlbumRepository;
import com.weareholidays.service.AlbumService;
import com.weareholidays.service.dto.AlbumDTO;
import com.weareholidays.service.mapper.AlbumMapper;
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
 * Test class for the AlbumResource REST controller.
 *
 * @see AlbumResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeareholidaysApp.class)
public class AlbumResourceIntTest {

    private static final String DEFAULT_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT = "BBBBBBBBBB";

    private static final Integer DEFAULT_MEDIA_COUNT = 1;
    private static final Integer UPDATED_MEDIA_COUNT = 2;

    private static final Integer DEFAULT_PUBLIC_MEDIA_COUNT = 1;
    private static final Integer UPDATED_PUBLIC_MEDIA_COUNT = 2;

    private static final Double DEFAULT_LOCATION_LAT = 1D;
    private static final Double UPDATED_LOCATION_LAT = 2D;

    private static final Double DEFAULT_LOCATION_LONG = 1D;
    private static final Double UPDATED_LOCATION_LONG = 2D;

    private static final String DEFAULT_LOCATION_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_TEXT = "BBBBBBBBBB";

    private static final Long DEFAULT_START_TIME = 1L;
    private static final Long UPDATED_START_TIME = 2L;

    private static final Long DEFAULT_END_TIME = 1L;
    private static final Long UPDATED_END_TIME = 2L;

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_CATEGORY = "AAAAAAAAAA";
    private static final String UPDATED_CATEGORY = "BBBBBBBBBB";

    @Autowired
    private AlbumRepository albumRepository;

    @Autowired
    private AlbumMapper albumMapper;

    @Autowired
    private AlbumService albumService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restAlbumMockMvc;

    private Album album;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        AlbumResource albumResource = new AlbumResource(albumService);
        this.restAlbumMockMvc = MockMvcBuilders.standaloneSetup(albumResource)
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
    public static Album createEntity(EntityManager em) {
        Album album = new Album()
            .content(DEFAULT_CONTENT)
            .mediaCount(DEFAULT_MEDIA_COUNT)
            .publicMediaCount(DEFAULT_PUBLIC_MEDIA_COUNT)
            .locationLat(DEFAULT_LOCATION_LAT)
            .locationLong(DEFAULT_LOCATION_LONG)
            .locationText(DEFAULT_LOCATION_TEXT)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .source(DEFAULT_SOURCE)
            .category(DEFAULT_CATEGORY);
        return album;
    }

    @Before
    public void initTest() {
        album = createEntity(em);
    }

    @Test
    @Transactional
    public void createAlbum() throws Exception {
        int databaseSizeBeforeCreate = albumRepository.findAll().size();

        // Create the Album
        AlbumDTO albumDTO = albumMapper.albumToAlbumDTO(album);
        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isCreated());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeCreate + 1);
        Album testAlbum = albumList.get(albumList.size() - 1);
        assertThat(testAlbum.getContent()).isEqualTo(DEFAULT_CONTENT);
        assertThat(testAlbum.getMediaCount()).isEqualTo(DEFAULT_MEDIA_COUNT);
        assertThat(testAlbum.getPublicMediaCount()).isEqualTo(DEFAULT_PUBLIC_MEDIA_COUNT);
        assertThat(testAlbum.getLocationLat()).isEqualTo(DEFAULT_LOCATION_LAT);
        assertThat(testAlbum.getLocationLong()).isEqualTo(DEFAULT_LOCATION_LONG);
        assertThat(testAlbum.getLocationText()).isEqualTo(DEFAULT_LOCATION_TEXT);
        assertThat(testAlbum.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testAlbum.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testAlbum.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testAlbum.getCategory()).isEqualTo(DEFAULT_CATEGORY);
    }

    @Test
    @Transactional
    public void createAlbumWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = albumRepository.findAll().size();

        // Create the Album with an existing ID
        album.setId(1L);
        AlbumDTO albumDTO = albumMapper.albumToAlbumDTO(album);

        // An entity with an existing ID cannot be created, so this API call must fail
        restAlbumMockMvc.perform(post("/api/albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllAlbums() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get all the albumList
        restAlbumMockMvc.perform(get("/api/albums?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(album.getId().intValue())))
            .andExpect(jsonPath("$.[*].content").value(hasItem(DEFAULT_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].mediaCount").value(hasItem(DEFAULT_MEDIA_COUNT)))
            .andExpect(jsonPath("$.[*].publicMediaCount").value(hasItem(DEFAULT_PUBLIC_MEDIA_COUNT)))
            .andExpect(jsonPath("$.[*].locationLat").value(hasItem(DEFAULT_LOCATION_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].locationLong").value(hasItem(DEFAULT_LOCATION_LONG.doubleValue())))
            .andExpect(jsonPath("$.[*].locationText").value(hasItem(DEFAULT_LOCATION_TEXT.toString())))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.intValue())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.intValue())))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].category").value(hasItem(DEFAULT_CATEGORY.toString())));
    }

    @Test
    @Transactional
    public void getAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);

        // Get the album
        restAlbumMockMvc.perform(get("/api/albums/{id}", album.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(album.getId().intValue()))
            .andExpect(jsonPath("$.content").value(DEFAULT_CONTENT.toString()))
            .andExpect(jsonPath("$.mediaCount").value(DEFAULT_MEDIA_COUNT))
            .andExpect(jsonPath("$.publicMediaCount").value(DEFAULT_PUBLIC_MEDIA_COUNT))
            .andExpect(jsonPath("$.locationLat").value(DEFAULT_LOCATION_LAT.doubleValue()))
            .andExpect(jsonPath("$.locationLong").value(DEFAULT_LOCATION_LONG.doubleValue()))
            .andExpect(jsonPath("$.locationText").value(DEFAULT_LOCATION_TEXT.toString()))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.intValue()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.intValue()))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.category").value(DEFAULT_CATEGORY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingAlbum() throws Exception {
        // Get the album
        restAlbumMockMvc.perform(get("/api/albums/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);
        int databaseSizeBeforeUpdate = albumRepository.findAll().size();

        // Update the album
        Album updatedAlbum = albumRepository.findOne(album.getId());
        updatedAlbum
            .content(UPDATED_CONTENT)
            .mediaCount(UPDATED_MEDIA_COUNT)
            .publicMediaCount(UPDATED_PUBLIC_MEDIA_COUNT)
            .locationLat(UPDATED_LOCATION_LAT)
            .locationLong(UPDATED_LOCATION_LONG)
            .locationText(UPDATED_LOCATION_TEXT)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .source(UPDATED_SOURCE)
            .category(UPDATED_CATEGORY);
        AlbumDTO albumDTO = albumMapper.albumToAlbumDTO(updatedAlbum);

        restAlbumMockMvc.perform(put("/api/albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isOk());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeUpdate);
        Album testAlbum = albumList.get(albumList.size() - 1);
        assertThat(testAlbum.getContent()).isEqualTo(UPDATED_CONTENT);
        assertThat(testAlbum.getMediaCount()).isEqualTo(UPDATED_MEDIA_COUNT);
        assertThat(testAlbum.getPublicMediaCount()).isEqualTo(UPDATED_PUBLIC_MEDIA_COUNT);
        assertThat(testAlbum.getLocationLat()).isEqualTo(UPDATED_LOCATION_LAT);
        assertThat(testAlbum.getLocationLong()).isEqualTo(UPDATED_LOCATION_LONG);
        assertThat(testAlbum.getLocationText()).isEqualTo(UPDATED_LOCATION_TEXT);
        assertThat(testAlbum.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testAlbum.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testAlbum.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testAlbum.getCategory()).isEqualTo(UPDATED_CATEGORY);
    }

    @Test
    @Transactional
    public void updateNonExistingAlbum() throws Exception {
        int databaseSizeBeforeUpdate = albumRepository.findAll().size();

        // Create the Album
        AlbumDTO albumDTO = albumMapper.albumToAlbumDTO(album);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restAlbumMockMvc.perform(put("/api/albums")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(albumDTO)))
            .andExpect(status().isCreated());

        // Validate the Album in the database
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteAlbum() throws Exception {
        // Initialize the database
        albumRepository.saveAndFlush(album);
        int databaseSizeBeforeDelete = albumRepository.findAll().size();

        // Get the album
        restAlbumMockMvc.perform(delete("/api/albums/{id}", album.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Album> albumList = albumRepository.findAll();
        assertThat(albumList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Album.class);
    }
}
