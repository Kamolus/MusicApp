package com.springmusicapp.repository;

import com.springmusicapp.model.Band;
import com.springmusicapp.model.BandStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BandRepository extends JpaRepository<Band, Long> {
    List<Band> findByName(String name);

}
