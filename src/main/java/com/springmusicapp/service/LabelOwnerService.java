package com.springmusicapp.service;

import com.springmusicapp.repository.LabelOwnerRepository;
import org.springframework.stereotype.Service;

@Service
public class LabelOwnerService extends AbstractEmployeeService {

    private final LabelOwnerRepository labelOwnerRepository;

    public LabelOwnerService(LabelOwnerRepository labelOwnerRepository) {
        super(labelOwnerRepository);
        this.labelOwnerRepository = labelOwnerRepository;
    }


}
