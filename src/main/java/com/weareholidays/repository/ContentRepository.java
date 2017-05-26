package com.weareholidays.repository;

import com.weareholidays.domain.Content;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Content entity.
 */
@SuppressWarnings("unused")
public interface ContentRepository extends JpaRepository<Content,Long> {

}
