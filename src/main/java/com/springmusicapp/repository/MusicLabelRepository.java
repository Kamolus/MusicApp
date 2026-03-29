package com.springmusicapp.repository;

import com.springmusicapp.model.MusicLabel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MusicLabelRepository extends JpaRepository<MusicLabel, Long> {
}
