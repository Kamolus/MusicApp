package com.springmusicapp.service;

import com.springmusicapp.model.BandManager;
import com.springmusicapp.repository.BandManagerRepository;
import com.springmusicapp.repository.BandRepository;
import org.springframework.stereotype.Service;

@Service
public class BandManagerService extends AbstractEmployeeService<BandManager> {

    private final BandRepository bandRepository;
    private final BandManagerRepository bandManagerRepository;

    public BandManagerService(BandRepository bandRepository, BandManagerRepository bandManagerRepository) {
        super(bandManagerRepository);
        this.bandManagerRepository = bandManagerRepository;
        this.bandRepository = bandRepository;
    }
}
