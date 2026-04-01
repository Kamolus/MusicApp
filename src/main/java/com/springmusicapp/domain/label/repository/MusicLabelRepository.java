package com.springmusicapp.domain.label.repository;

import com.springmusicapp.domain.label.model.MusicLabel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MusicLabelRepository extends JpaRepository<MusicLabel, UUID> {
}
