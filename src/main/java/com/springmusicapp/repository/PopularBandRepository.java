package com.springmusicapp.repository;

import com.springmusicapp.model.PopularBand;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PopularBandRepository extends BandRepository<PopularBand> {
    List<PopularBand> findByEarnedMoneyGreaterThan(double earnedMoney);

    @Query("SELECT b FROM PopularBand b WHERE b.earnedMoney > :minMoney AND SIZE(b.albums) > :minAlbums")
    List<PopularBand> findRichBandsWithManyAlbums(@Param("minMoney") double minMoney, @Param("minAlbums") int minAlbums);
}
