package com.weareholidays.web.rest;

import com.weareholidays.WeareholidaysApp;

import com.weareholidays.domain.Timeline;
import com.weareholidays.repository.TimelineRepository;
import com.weareholidays.service.TimelineService;
import com.weareholidays.service.dto.TimelineDTO;
import com.weareholidays.service.mapper.TimelineMapper;
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
 * Test class for the TimelineResource REST controller.
 *
 * @see TimelineResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeareholidaysApp.class)
public class TimelineResourceIntTest {

    private static final String DEFAULT_ALBUM_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_ALBUM_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_DAY_SUMMARY_DUMMY_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_DAY_SUMMARY_DUMMY_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_INTERCITY_TRAVEL_LOCATION_PIN = "AAAAAAAAAA";
    private static final String UPDATED_INTERCITY_TRAVEL_LOCATION_PIN = "BBBBBBBBBB";

    private static final String DEFAULT_DAY_LOCATION_PIN = "AAAAAAAAAA";
    private static final String UPDATED_DAY_LOCATION_PIN = "BBBBBBBBBB";

    private static final String DEFAULT_CHECK_IN_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_CHECK_IN_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_NOTE_CONTENT = "AAAAAAAAAA";
    private static final String UPDATED_NOTE_CONTENT = "BBBBBBBBBB";

    private static final String DEFAULT_CONTENT_TYPE = "AAAAAAAAAA";
    private static final String UPDATED_CONTENT_TYPE = "BBBBBBBBBB";

    private static final Long DEFAULT_CONTENT_TIME_STAMP = 1L;
    private static final Long UPDATED_CONTENT_TIME_STAMP = 2L;

    private static final Integer DEFAULT_DISPLAY_ORDER = 1;
    private static final Integer UPDATED_DISPLAY_ORDER = 2;

    private static final String DEFAULT_SOURCE = "AAAAAAAAAA";
    private static final String UPDATED_SOURCE = "BBBBBBBBBB";

    private static final String DEFAULT_THIRD_PARTY_ID = "AAAAAAAAAA";
    private static final String UPDATED_THIRD_PARTY_ID = "BBBBBBBBBB";

    private static final Integer DEFAULT_DAY_ORDER = 1;
    private static final Integer UPDATED_DAY_ORDER = 2;

    private static final Long DEFAULT_DATE_IN_MILLI = 1L;
    private static final Long UPDATED_DATE_IN_MILLI = 2L;

    @Autowired
    private TimelineRepository timelineRepository;

    @Autowired
    private TimelineMapper timelineMapper;

    @Autowired
    private TimelineService timelineService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restTimelineMockMvc;

    private Timeline timeline;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        TimelineResource timelineResource = new TimelineResource(timelineService);
        this.restTimelineMockMvc = MockMvcBuilders.standaloneSetup(timelineResource)
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
    public static Timeline createEntity(EntityManager em) {
        Timeline timeline = new Timeline()
            .albumContent(DEFAULT_ALBUM_CONTENT)
            .daySummaryDummyContent(DEFAULT_DAY_SUMMARY_DUMMY_CONTENT)
            .intercityTravelLocationPin(DEFAULT_INTERCITY_TRAVEL_LOCATION_PIN)
            .dayLocationPin(DEFAULT_DAY_LOCATION_PIN)
            .checkInContent(DEFAULT_CHECK_IN_CONTENT)
            .noteContent(DEFAULT_NOTE_CONTENT)
            .contentType(DEFAULT_CONTENT_TYPE)
            .contentTimeStamp(DEFAULT_CONTENT_TIME_STAMP)
            .displayOrder(DEFAULT_DISPLAY_ORDER)
            .source(DEFAULT_SOURCE)
            .thirdPartyId(DEFAULT_THIRD_PARTY_ID)
            .dayOrder(DEFAULT_DAY_ORDER)
            .dateInMilli(DEFAULT_DATE_IN_MILLI);
        return timeline;
    }

    @Before
    public void initTest() {
        timeline = createEntity(em);
    }

    @Test
    @Transactional
    public void createTimeline() throws Exception {
        int databaseSizeBeforeCreate = timelineRepository.findAll().size();

        // Create the Timeline
        TimelineDTO timelineDTO = timelineMapper.timelineToTimelineDTO(timeline);
        restTimelineMockMvc.perform(post("/api/timelines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timelineDTO)))
            .andExpect(status().isCreated());

        // Validate the Timeline in the database
        List<Timeline> timelineList = timelineRepository.findAll();
        assertThat(timelineList).hasSize(databaseSizeBeforeCreate + 1);
        Timeline testTimeline = timelineList.get(timelineList.size() - 1);
        assertThat(testTimeline.getAlbumContent()).isEqualTo(DEFAULT_ALBUM_CONTENT);
        assertThat(testTimeline.getDaySummaryDummyContent()).isEqualTo(DEFAULT_DAY_SUMMARY_DUMMY_CONTENT);
        assertThat(testTimeline.getIntercityTravelLocationPin()).isEqualTo(DEFAULT_INTERCITY_TRAVEL_LOCATION_PIN);
        assertThat(testTimeline.getDayLocationPin()).isEqualTo(DEFAULT_DAY_LOCATION_PIN);
        assertThat(testTimeline.getCheckInContent()).isEqualTo(DEFAULT_CHECK_IN_CONTENT);
        assertThat(testTimeline.getNoteContent()).isEqualTo(DEFAULT_NOTE_CONTENT);
        assertThat(testTimeline.getContentType()).isEqualTo(DEFAULT_CONTENT_TYPE);
        assertThat(testTimeline.getContentTimeStamp()).isEqualTo(DEFAULT_CONTENT_TIME_STAMP);
        assertThat(testTimeline.getDisplayOrder()).isEqualTo(DEFAULT_DISPLAY_ORDER);
        assertThat(testTimeline.getSource()).isEqualTo(DEFAULT_SOURCE);
        assertThat(testTimeline.getThirdPartyId()).isEqualTo(DEFAULT_THIRD_PARTY_ID);
        assertThat(testTimeline.getDayOrder()).isEqualTo(DEFAULT_DAY_ORDER);
        assertThat(testTimeline.getDateInMilli()).isEqualTo(DEFAULT_DATE_IN_MILLI);
    }

    @Test
    @Transactional
    public void createTimelineWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = timelineRepository.findAll().size();

        // Create the Timeline with an existing ID
        timeline.setId(1L);
        TimelineDTO timelineDTO = timelineMapper.timelineToTimelineDTO(timeline);

        // An entity with an existing ID cannot be created, so this API call must fail
        restTimelineMockMvc.perform(post("/api/timelines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timelineDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Timeline> timelineList = timelineRepository.findAll();
        assertThat(timelineList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllTimelines() throws Exception {
        // Initialize the database
        timelineRepository.saveAndFlush(timeline);

        // Get all the timelineList
        restTimelineMockMvc.perform(get("/api/timelines?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(timeline.getId().intValue())))
            .andExpect(jsonPath("$.[*].albumContent").value(hasItem(DEFAULT_ALBUM_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].daySummaryDummyContent").value(hasItem(DEFAULT_DAY_SUMMARY_DUMMY_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].intercityTravelLocationPin").value(hasItem(DEFAULT_INTERCITY_TRAVEL_LOCATION_PIN.toString())))
            .andExpect(jsonPath("$.[*].dayLocationPin").value(hasItem(DEFAULT_DAY_LOCATION_PIN.toString())))
            .andExpect(jsonPath("$.[*].checkInContent").value(hasItem(DEFAULT_CHECK_IN_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].noteContent").value(hasItem(DEFAULT_NOTE_CONTENT.toString())))
            .andExpect(jsonPath("$.[*].contentType").value(hasItem(DEFAULT_CONTENT_TYPE.toString())))
            .andExpect(jsonPath("$.[*].contentTimeStamp").value(hasItem(DEFAULT_CONTENT_TIME_STAMP.intValue())))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER)))
            .andExpect(jsonPath("$.[*].source").value(hasItem(DEFAULT_SOURCE.toString())))
            .andExpect(jsonPath("$.[*].thirdPartyId").value(hasItem(DEFAULT_THIRD_PARTY_ID.toString())))
            .andExpect(jsonPath("$.[*].dayOrder").value(hasItem(DEFAULT_DAY_ORDER)))
            .andExpect(jsonPath("$.[*].dateInMilli").value(hasItem(DEFAULT_DATE_IN_MILLI.intValue())));
    }

    @Test
    @Transactional
    public void getTimeline() throws Exception {
        // Initialize the database
        timelineRepository.saveAndFlush(timeline);

        // Get the timeline
        restTimelineMockMvc.perform(get("/api/timelines/{id}", timeline.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(timeline.getId().intValue()))
            .andExpect(jsonPath("$.albumContent").value(DEFAULT_ALBUM_CONTENT.toString()))
            .andExpect(jsonPath("$.daySummaryDummyContent").value(DEFAULT_DAY_SUMMARY_DUMMY_CONTENT.toString()))
            .andExpect(jsonPath("$.intercityTravelLocationPin").value(DEFAULT_INTERCITY_TRAVEL_LOCATION_PIN.toString()))
            .andExpect(jsonPath("$.dayLocationPin").value(DEFAULT_DAY_LOCATION_PIN.toString()))
            .andExpect(jsonPath("$.checkInContent").value(DEFAULT_CHECK_IN_CONTENT.toString()))
            .andExpect(jsonPath("$.noteContent").value(DEFAULT_NOTE_CONTENT.toString()))
            .andExpect(jsonPath("$.contentType").value(DEFAULT_CONTENT_TYPE.toString()))
            .andExpect(jsonPath("$.contentTimeStamp").value(DEFAULT_CONTENT_TIME_STAMP.intValue()))
            .andExpect(jsonPath("$.displayOrder").value(DEFAULT_DISPLAY_ORDER))
            .andExpect(jsonPath("$.source").value(DEFAULT_SOURCE.toString()))
            .andExpect(jsonPath("$.thirdPartyId").value(DEFAULT_THIRD_PARTY_ID.toString()))
            .andExpect(jsonPath("$.dayOrder").value(DEFAULT_DAY_ORDER))
            .andExpect(jsonPath("$.dateInMilli").value(DEFAULT_DATE_IN_MILLI.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingTimeline() throws Exception {
        // Get the timeline
        restTimelineMockMvc.perform(get("/api/timelines/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateTimeline() throws Exception {
        // Initialize the database
        timelineRepository.saveAndFlush(timeline);
        int databaseSizeBeforeUpdate = timelineRepository.findAll().size();

        // Update the timeline
        Timeline updatedTimeline = timelineRepository.findOne(timeline.getId());
        updatedTimeline
            .albumContent(UPDATED_ALBUM_CONTENT)
            .daySummaryDummyContent(UPDATED_DAY_SUMMARY_DUMMY_CONTENT)
            .intercityTravelLocationPin(UPDATED_INTERCITY_TRAVEL_LOCATION_PIN)
            .dayLocationPin(UPDATED_DAY_LOCATION_PIN)
            .checkInContent(UPDATED_CHECK_IN_CONTENT)
            .noteContent(UPDATED_NOTE_CONTENT)
            .contentType(UPDATED_CONTENT_TYPE)
            .contentTimeStamp(UPDATED_CONTENT_TIME_STAMP)
            .displayOrder(UPDATED_DISPLAY_ORDER)
            .source(UPDATED_SOURCE)
            .thirdPartyId(UPDATED_THIRD_PARTY_ID)
            .dayOrder(UPDATED_DAY_ORDER)
            .dateInMilli(UPDATED_DATE_IN_MILLI);
        TimelineDTO timelineDTO = timelineMapper.timelineToTimelineDTO(updatedTimeline);

        restTimelineMockMvc.perform(put("/api/timelines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timelineDTO)))
            .andExpect(status().isOk());

        // Validate the Timeline in the database
        List<Timeline> timelineList = timelineRepository.findAll();
        assertThat(timelineList).hasSize(databaseSizeBeforeUpdate);
        Timeline testTimeline = timelineList.get(timelineList.size() - 1);
        assertThat(testTimeline.getAlbumContent()).isEqualTo(UPDATED_ALBUM_CONTENT);
        assertThat(testTimeline.getDaySummaryDummyContent()).isEqualTo(UPDATED_DAY_SUMMARY_DUMMY_CONTENT);
        assertThat(testTimeline.getIntercityTravelLocationPin()).isEqualTo(UPDATED_INTERCITY_TRAVEL_LOCATION_PIN);
        assertThat(testTimeline.getDayLocationPin()).isEqualTo(UPDATED_DAY_LOCATION_PIN);
        assertThat(testTimeline.getCheckInContent()).isEqualTo(UPDATED_CHECK_IN_CONTENT);
        assertThat(testTimeline.getNoteContent()).isEqualTo(UPDATED_NOTE_CONTENT);
        assertThat(testTimeline.getContentType()).isEqualTo(UPDATED_CONTENT_TYPE);
        assertThat(testTimeline.getContentTimeStamp()).isEqualTo(UPDATED_CONTENT_TIME_STAMP);
        assertThat(testTimeline.getDisplayOrder()).isEqualTo(UPDATED_DISPLAY_ORDER);
        assertThat(testTimeline.getSource()).isEqualTo(UPDATED_SOURCE);
        assertThat(testTimeline.getThirdPartyId()).isEqualTo(UPDATED_THIRD_PARTY_ID);
        assertThat(testTimeline.getDayOrder()).isEqualTo(UPDATED_DAY_ORDER);
        assertThat(testTimeline.getDateInMilli()).isEqualTo(UPDATED_DATE_IN_MILLI);
    }

    @Test
    @Transactional
    public void updateNonExistingTimeline() throws Exception {
        int databaseSizeBeforeUpdate = timelineRepository.findAll().size();

        // Create the Timeline
        TimelineDTO timelineDTO = timelineMapper.timelineToTimelineDTO(timeline);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restTimelineMockMvc.perform(put("/api/timelines")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(timelineDTO)))
            .andExpect(status().isCreated());

        // Validate the Timeline in the database
        List<Timeline> timelineList = timelineRepository.findAll();
        assertThat(timelineList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteTimeline() throws Exception {
        // Initialize the database
        timelineRepository.saveAndFlush(timeline);
        int databaseSizeBeforeDelete = timelineRepository.findAll().size();

        // Get the timeline
        restTimelineMockMvc.perform(delete("/api/timelines/{id}", timeline.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Timeline> timelineList = timelineRepository.findAll();
        assertThat(timelineList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Timeline.class);
    }
}
