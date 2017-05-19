package com.weareholidays.web.rest;

import com.weareholidays.WeareholidaysApp;

import com.weareholidays.domain.Day;
import com.weareholidays.repository.DayRepository;
import com.weareholidays.service.DayService;
import com.weareholidays.service.dto.DayDTO;
import com.weareholidays.service.mapper.DayMapper;
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
 * Test class for the DayResource REST controller.
 *
 * @see DayResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeareholidaysApp.class)
public class DayResourceIntTest {

    private static final String DEFAULT_NAME = "AAAAAAAAAA";
    private static final String UPDATED_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_DISPLAY_ORDER = 1;
    private static final Integer UPDATED_DISPLAY_ORDER = 2;

    private static final Long DEFAULT_START_TIME = 1L;
    private static final Long UPDATED_START_TIME = 2L;

    private static final Long DEFAULT_END_TIME = 1L;
    private static final Long UPDATED_END_TIME = 2L;

    private static final Double DEFAULT_LOCATION_LAT = 1D;
    private static final Double UPDATED_LOCATION_LAT = 2D;

    private static final Double DEFAULT_LOCATION_LONG = 1D;
    private static final Double UPDATED_LOCATION_LONG = 2D;

    private static final String DEFAULT_CITY = "AAAAAAAAAA";
    private static final String UPDATED_CITY = "BBBBBBBBBB";

    private static final String DEFAULT_COUNTRY = "AAAAAAAAAA";
    private static final String UPDATED_COUNTRY = "BBBBBBBBBB";

    @Autowired
    private DayRepository dayRepository;

    @Autowired
    private DayMapper dayMapper;

    @Autowired
    private DayService dayService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restDayMockMvc;

    private Day day;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        DayResource dayResource = new DayResource(dayService);
        this.restDayMockMvc = MockMvcBuilders.standaloneSetup(dayResource)
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
    public static Day createEntity(EntityManager em) {
        Day day = new Day()
            .name(DEFAULT_NAME)
            .displayOrder(DEFAULT_DISPLAY_ORDER)
            .startTime(DEFAULT_START_TIME)
            .endTime(DEFAULT_END_TIME)
            .locationLat(DEFAULT_LOCATION_LAT)
            .locationLong(DEFAULT_LOCATION_LONG)
            .city(DEFAULT_CITY)
            .country(DEFAULT_COUNTRY);
        return day;
    }

    @Before
    public void initTest() {
        day = createEntity(em);
    }

    @Test
    @Transactional
    public void createDay() throws Exception {
        int databaseSizeBeforeCreate = dayRepository.findAll().size();

        // Create the Day
        DayDTO dayDTO = dayMapper.dayToDayDTO(day);
        restDayMockMvc.perform(post("/api/days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayDTO)))
            .andExpect(status().isCreated());

        // Validate the Day in the database
        List<Day> dayList = dayRepository.findAll();
        assertThat(dayList).hasSize(databaseSizeBeforeCreate + 1);
        Day testDay = dayList.get(dayList.size() - 1);
        assertThat(testDay.getName()).isEqualTo(DEFAULT_NAME);
        assertThat(testDay.getDisplayOrder()).isEqualTo(DEFAULT_DISPLAY_ORDER);
        assertThat(testDay.getStartTime()).isEqualTo(DEFAULT_START_TIME);
        assertThat(testDay.getEndTime()).isEqualTo(DEFAULT_END_TIME);
        assertThat(testDay.getLocationLat()).isEqualTo(DEFAULT_LOCATION_LAT);
        assertThat(testDay.getLocationLong()).isEqualTo(DEFAULT_LOCATION_LONG);
        assertThat(testDay.getCity()).isEqualTo(DEFAULT_CITY);
        assertThat(testDay.getCountry()).isEqualTo(DEFAULT_COUNTRY);
    }

    @Test
    @Transactional
    public void createDayWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = dayRepository.findAll().size();

        // Create the Day with an existing ID
        day.setId(1L);
        DayDTO dayDTO = dayMapper.dayToDayDTO(day);

        // An entity with an existing ID cannot be created, so this API call must fail
        restDayMockMvc.perform(post("/api/days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Day> dayList = dayRepository.findAll();
        assertThat(dayList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllDays() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);

        // Get all the dayList
        restDayMockMvc.perform(get("/api/days?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(day.getId().intValue())))
            .andExpect(jsonPath("$.[*].name").value(hasItem(DEFAULT_NAME.toString())))
            .andExpect(jsonPath("$.[*].displayOrder").value(hasItem(DEFAULT_DISPLAY_ORDER)))
            .andExpect(jsonPath("$.[*].startTime").value(hasItem(DEFAULT_START_TIME.intValue())))
            .andExpect(jsonPath("$.[*].endTime").value(hasItem(DEFAULT_END_TIME.intValue())))
            .andExpect(jsonPath("$.[*].locationLat").value(hasItem(DEFAULT_LOCATION_LAT.doubleValue())))
            .andExpect(jsonPath("$.[*].locationLong").value(hasItem(DEFAULT_LOCATION_LONG.doubleValue())))
            .andExpect(jsonPath("$.[*].city").value(hasItem(DEFAULT_CITY.toString())))
            .andExpect(jsonPath("$.[*].country").value(hasItem(DEFAULT_COUNTRY.toString())));
    }

    @Test
    @Transactional
    public void getDay() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);

        // Get the day
        restDayMockMvc.perform(get("/api/days/{id}", day.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(day.getId().intValue()))
            .andExpect(jsonPath("$.name").value(DEFAULT_NAME.toString()))
            .andExpect(jsonPath("$.displayOrder").value(DEFAULT_DISPLAY_ORDER))
            .andExpect(jsonPath("$.startTime").value(DEFAULT_START_TIME.intValue()))
            .andExpect(jsonPath("$.endTime").value(DEFAULT_END_TIME.intValue()))
            .andExpect(jsonPath("$.locationLat").value(DEFAULT_LOCATION_LAT.doubleValue()))
            .andExpect(jsonPath("$.locationLong").value(DEFAULT_LOCATION_LONG.doubleValue()))
            .andExpect(jsonPath("$.city").value(DEFAULT_CITY.toString()))
            .andExpect(jsonPath("$.country").value(DEFAULT_COUNTRY.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingDay() throws Exception {
        // Get the day
        restDayMockMvc.perform(get("/api/days/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateDay() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);
        int databaseSizeBeforeUpdate = dayRepository.findAll().size();

        // Update the day
        Day updatedDay = dayRepository.findOne(day.getId());
        updatedDay
            .name(UPDATED_NAME)
            .displayOrder(UPDATED_DISPLAY_ORDER)
            .startTime(UPDATED_START_TIME)
            .endTime(UPDATED_END_TIME)
            .locationLat(UPDATED_LOCATION_LAT)
            .locationLong(UPDATED_LOCATION_LONG)
            .city(UPDATED_CITY)
            .country(UPDATED_COUNTRY);
        DayDTO dayDTO = dayMapper.dayToDayDTO(updatedDay);

        restDayMockMvc.perform(put("/api/days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayDTO)))
            .andExpect(status().isOk());

        // Validate the Day in the database
        List<Day> dayList = dayRepository.findAll();
        assertThat(dayList).hasSize(databaseSizeBeforeUpdate);
        Day testDay = dayList.get(dayList.size() - 1);
        assertThat(testDay.getName()).isEqualTo(UPDATED_NAME);
        assertThat(testDay.getDisplayOrder()).isEqualTo(UPDATED_DISPLAY_ORDER);
        assertThat(testDay.getStartTime()).isEqualTo(UPDATED_START_TIME);
        assertThat(testDay.getEndTime()).isEqualTo(UPDATED_END_TIME);
        assertThat(testDay.getLocationLat()).isEqualTo(UPDATED_LOCATION_LAT);
        assertThat(testDay.getLocationLong()).isEqualTo(UPDATED_LOCATION_LONG);
        assertThat(testDay.getCity()).isEqualTo(UPDATED_CITY);
        assertThat(testDay.getCountry()).isEqualTo(UPDATED_COUNTRY);
    }

    @Test
    @Transactional
    public void updateNonExistingDay() throws Exception {
        int databaseSizeBeforeUpdate = dayRepository.findAll().size();

        // Create the Day
        DayDTO dayDTO = dayMapper.dayToDayDTO(day);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restDayMockMvc.perform(put("/api/days")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(dayDTO)))
            .andExpect(status().isCreated());

        // Validate the Day in the database
        List<Day> dayList = dayRepository.findAll();
        assertThat(dayList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteDay() throws Exception {
        // Initialize the database
        dayRepository.saveAndFlush(day);
        int databaseSizeBeforeDelete = dayRepository.findAll().size();

        // Get the day
        restDayMockMvc.perform(delete("/api/days/{id}", day.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Day> dayList = dayRepository.findAll();
        assertThat(dayList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Day.class);
    }
}
