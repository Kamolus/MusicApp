package com.springmusicapp.repository;

import com.springmusicapp.model.Band;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BandRepository<T extends Band> extends JpaRepository<T, Long> {

}
