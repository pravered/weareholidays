package com.weareholidays.web.rest;

import com.weareholidays.WeareholidaysApp;

import com.weareholidays.domain.Coupon;
import com.weareholidays.repository.CouponRepository;
import com.weareholidays.service.CouponService;
import com.weareholidays.service.dto.CouponDTO;
import com.weareholidays.service.mapper.CouponMapper;
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
 * Test class for the CouponResource REST controller.
 *
 * @see CouponResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = WeareholidaysApp.class)
public class CouponResourceIntTest {

    private static final String DEFAULT_OBJECT_ID = "AAAAAAAAAA";
    private static final String UPDATED_OBJECT_ID = "BBBBBBBBBB";

    private static final String DEFAULT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_CODE = "BBBBBBBBBB";

    private static final String DEFAULT_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_MESSAGE = "BBBBBBBBBB";

    private static final Long DEFAULT_VALID_FROM = 1L;
    private static final Long UPDATED_VALID_FROM = 2L;

    private static final Long DEFAULT_VALID_TILL = 1L;
    private static final Long UPDATED_VALID_TILL = 2L;

    private static final Boolean DEFAULT_IS_ACTIVE = false;
    private static final Boolean UPDATED_IS_ACTIVE = true;

    private static final String DEFAULT_PUBLISHED_MESSAGE = "AAAAAAAAAA";
    private static final String UPDATED_PUBLISHED_MESSAGE = "BBBBBBBBBB";

    @Autowired
    private CouponRepository couponRepository;

    @Autowired
    private CouponMapper couponMapper;

    @Autowired
    private CouponService couponService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restCouponMockMvc;

    private Coupon coupon;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        CouponResource couponResource = new CouponResource(couponService);
        this.restCouponMockMvc = MockMvcBuilders.standaloneSetup(couponResource)
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
    public static Coupon createEntity(EntityManager em) {
        Coupon coupon = new Coupon()
            .objectId(DEFAULT_OBJECT_ID)
            .code(DEFAULT_CODE)
            .message(DEFAULT_MESSAGE)
            .validFrom(DEFAULT_VALID_FROM)
            .validTill(DEFAULT_VALID_TILL)
            .isActive(DEFAULT_IS_ACTIVE)
            .publishedMessage(DEFAULT_PUBLISHED_MESSAGE);
        return coupon;
    }

    @Before
    public void initTest() {
        coupon = createEntity(em);
    }

    @Test
    @Transactional
    public void createCoupon() throws Exception {
        int databaseSizeBeforeCreate = couponRepository.findAll().size();

        // Create the Coupon
        CouponDTO couponDTO = couponMapper.couponToCouponDTO(coupon);
        restCouponMockMvc.perform(post("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(couponDTO)))
            .andExpect(status().isCreated());

        // Validate the Coupon in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeCreate + 1);
        Coupon testCoupon = couponList.get(couponList.size() - 1);
        assertThat(testCoupon.getObjectId()).isEqualTo(DEFAULT_OBJECT_ID);
        assertThat(testCoupon.getCode()).isEqualTo(DEFAULT_CODE);
        assertThat(testCoupon.getMessage()).isEqualTo(DEFAULT_MESSAGE);
        assertThat(testCoupon.getValidFrom()).isEqualTo(DEFAULT_VALID_FROM);
        assertThat(testCoupon.getValidTill()).isEqualTo(DEFAULT_VALID_TILL);
        assertThat(testCoupon.isIsActive()).isEqualTo(DEFAULT_IS_ACTIVE);
        assertThat(testCoupon.getPublishedMessage()).isEqualTo(DEFAULT_PUBLISHED_MESSAGE);
    }

    @Test
    @Transactional
    public void createCouponWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = couponRepository.findAll().size();

        // Create the Coupon with an existing ID
        coupon.setId(1L);
        CouponDTO couponDTO = couponMapper.couponToCouponDTO(coupon);

        // An entity with an existing ID cannot be created, so this API call must fail
        restCouponMockMvc.perform(post("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(couponDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Alice in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllCoupons() throws Exception {
        // Initialize the database
        couponRepository.saveAndFlush(coupon);

        // Get all the couponList
        restCouponMockMvc.perform(get("/api/coupons?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(coupon.getId().intValue())))
            .andExpect(jsonPath("$.[*].objectId").value(hasItem(DEFAULT_OBJECT_ID.toString())))
            .andExpect(jsonPath("$.[*].code").value(hasItem(DEFAULT_CODE.toString())))
            .andExpect(jsonPath("$.[*].message").value(hasItem(DEFAULT_MESSAGE.toString())))
            .andExpect(jsonPath("$.[*].validFrom").value(hasItem(DEFAULT_VALID_FROM.intValue())))
            .andExpect(jsonPath("$.[*].validTill").value(hasItem(DEFAULT_VALID_TILL.intValue())))
            .andExpect(jsonPath("$.[*].isActive").value(hasItem(DEFAULT_IS_ACTIVE.booleanValue())))
            .andExpect(jsonPath("$.[*].publishedMessage").value(hasItem(DEFAULT_PUBLISHED_MESSAGE.toString())));
    }

    @Test
    @Transactional
    public void getCoupon() throws Exception {
        // Initialize the database
        couponRepository.saveAndFlush(coupon);

        // Get the coupon
        restCouponMockMvc.perform(get("/api/coupons/{id}", coupon.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(coupon.getId().intValue()))
            .andExpect(jsonPath("$.objectId").value(DEFAULT_OBJECT_ID.toString()))
            .andExpect(jsonPath("$.code").value(DEFAULT_CODE.toString()))
            .andExpect(jsonPath("$.message").value(DEFAULT_MESSAGE.toString()))
            .andExpect(jsonPath("$.validFrom").value(DEFAULT_VALID_FROM.intValue()))
            .andExpect(jsonPath("$.validTill").value(DEFAULT_VALID_TILL.intValue()))
            .andExpect(jsonPath("$.isActive").value(DEFAULT_IS_ACTIVE.booleanValue()))
            .andExpect(jsonPath("$.publishedMessage").value(DEFAULT_PUBLISHED_MESSAGE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingCoupon() throws Exception {
        // Get the coupon
        restCouponMockMvc.perform(get("/api/coupons/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateCoupon() throws Exception {
        // Initialize the database
        couponRepository.saveAndFlush(coupon);
        int databaseSizeBeforeUpdate = couponRepository.findAll().size();

        // Update the coupon
        Coupon updatedCoupon = couponRepository.findOne(coupon.getId());
        updatedCoupon
            .objectId(UPDATED_OBJECT_ID)
            .code(UPDATED_CODE)
            .message(UPDATED_MESSAGE)
            .validFrom(UPDATED_VALID_FROM)
            .validTill(UPDATED_VALID_TILL)
            .isActive(UPDATED_IS_ACTIVE)
            .publishedMessage(UPDATED_PUBLISHED_MESSAGE);
        CouponDTO couponDTO = couponMapper.couponToCouponDTO(updatedCoupon);

        restCouponMockMvc.perform(put("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(couponDTO)))
            .andExpect(status().isOk());

        // Validate the Coupon in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeUpdate);
        Coupon testCoupon = couponList.get(couponList.size() - 1);
        assertThat(testCoupon.getObjectId()).isEqualTo(UPDATED_OBJECT_ID);
        assertThat(testCoupon.getCode()).isEqualTo(UPDATED_CODE);
        assertThat(testCoupon.getMessage()).isEqualTo(UPDATED_MESSAGE);
        assertThat(testCoupon.getValidFrom()).isEqualTo(UPDATED_VALID_FROM);
        assertThat(testCoupon.getValidTill()).isEqualTo(UPDATED_VALID_TILL);
        assertThat(testCoupon.isIsActive()).isEqualTo(UPDATED_IS_ACTIVE);
        assertThat(testCoupon.getPublishedMessage()).isEqualTo(UPDATED_PUBLISHED_MESSAGE);
    }

    @Test
    @Transactional
    public void updateNonExistingCoupon() throws Exception {
        int databaseSizeBeforeUpdate = couponRepository.findAll().size();

        // Create the Coupon
        CouponDTO couponDTO = couponMapper.couponToCouponDTO(coupon);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restCouponMockMvc.perform(put("/api/coupons")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(couponDTO)))
            .andExpect(status().isCreated());

        // Validate the Coupon in the database
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteCoupon() throws Exception {
        // Initialize the database
        couponRepository.saveAndFlush(coupon);
        int databaseSizeBeforeDelete = couponRepository.findAll().size();

        // Get the coupon
        restCouponMockMvc.perform(delete("/api/coupons/{id}", coupon.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Coupon> couponList = couponRepository.findAll();
        assertThat(couponList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Coupon.class);
    }
}
