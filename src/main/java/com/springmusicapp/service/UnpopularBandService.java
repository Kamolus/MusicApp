package com.springmusicapp.service;

import com.springmusicapp.DTO.UnpopularBandDTO;
import com.springmusicapp.mapper.UnpopularBandMapper;
import com.springmusicapp.model.UnpopularBand;
import com.springmusicapp.repository.UnpopularBandRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UnpopularBandService extends AbstractBandService<UnpopularBand, UnpopularBandDTO>{

    private final UnpopularBandRepository unpopularbandRepository;
    private final UnpopularBandMapper unpopularBandMapper;

    public UnpopularBandService(UnpopularBandRepository unpopularbandRepository
            , UnpopularBandMapper unpopularBandMapper) {
        super(unpopularbandRepository, unpopularBandMapper);
        this.unpopularbandRepository = unpopularbandRepository;
        this.unpopularBandMapper = unpopularBandMapper;
    }

    public List<UnpopularBandDTO> findByTargetGroup(String targetGroup) {
        List<UnpopularBand> bands = unpopularbandRepository.findByTargetGroup(targetGroup);

        if (bands.isEmpty()) {
            throw new IllegalStateException("There is no band with this target group");
        }

        return bands.stream()
                .map(unpopularBandMapper::toDto)
                .toList();
    }
}
