package com.springmusicapp.domain.musician;

import com.springmusicapp.domain.user.repository.UserRepository;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface MusicianRepository extends UserRepository<Musician> {
    Optional<Musician> findByStageName(String stageName);
    List<Musician> findAllByMembershipsIsNotEmpty();
    @Query("SELECT m FROM Musician m JOIN m.memberships bm WHERE bm.band.id = :bandId")
    List<Musician> findAllByBandId(@Param("bandId") UUID bandId);
    List<Musician> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
