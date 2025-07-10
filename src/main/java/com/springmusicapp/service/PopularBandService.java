package com.springmusicapp.service;

import com.springmusicapp.DTO.PopularBandDTO;
import com.springmusicapp.mapper.PopularBandMapper;
import com.springmusicapp.model.PopularBand;
import com.springmusicapp.repository.PopularBandRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PopularBandService extends AbstractBandService<PopularBand, PopularBandDTO> {

    private final PopularBandRepository popularBandRepository;
    private final PopularBandMapper popularBandMapper;

    public PopularBandService(PopularBandRepository popularbandRepository,PopularBandMapper popularBandMapper) {
        super(popularbandRepository, popularBandMapper);
        this.popularBandRepository = popularbandRepository;
        this.popularBandMapper = popularBandMapper;
    }

    public List<PopularBandDTO> findByEarnedMoneyGreaterThan(double earnedMoney) {
        List<PopularBand> popularBands = popularBandRepository.findByEarnedMoneyGreaterThan(earnedMoney);

        if (popularBands.isEmpty()) {
            throw new IllegalStateException("There is no band with this target group");
        }

        return popularBands.stream()
                .map(popularBandMapper::toDto)
                .collect(Collectors.toList());
    }

    public List<PopularBandDTO> findRichBandsWithManyAlbums(double minMoney, int minAlbums) {
        return popularBandRepository.findAll().stream()
                .filter(band -> band.getEarnedMoney() > minMoney)
                .filter(band -> band.getAlbums().size() > minAlbums)
                .map(popularBandMapper::toDto)
                .collect(Collectors.toList());
    }
}
