package com.springmusicapp.repository;

import com.springmusicapp.model.UnpopularBand;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnpopularBandRepository extends BandRepository<UnpopularBand>{
    List<UnpopularBand> findByTargetGroup(String targetGroup);
}
