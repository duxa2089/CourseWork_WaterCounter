package com.corsework.watercounter.service;

import com.corsework.watercounter.entities.Indication;
import com.corsework.watercounter.entities.dto.IndicateDto;
import com.corsework.watercounter.repository.IndicationRepository;
import com.corsework.watercounter.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class IndicatorService {

    @Autowired
    private IndicationRepository indicationRepository;

    @Autowired
    private UserRepository userRepository;

    public boolean setIndicate(IndicateDto indicateDto) {

        Indication indication = indicationRepository.findById(UUID.fromString(indicateDto.getIndicateId())).get();
        indication.setValue(indicateDto.getValue());

        indicationRepository.save(indication);

        return true;
    }

    public boolean confirmedIndicator(String historyId) {

        userRepository.findById(UUID.fromString(historyId));

        return true;
    }
}
