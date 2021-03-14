package com.bdcourse.library.distribution;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DistributionService {
    @Autowired
    private DistributionRepository distributionRepository;

    public List<Distribution> findAll() {
        return (List<Distribution>) distributionRepository.findAll();
    }

    public Distribution save(Distribution distribution) {
        return distributionRepository.save(distribution);
    }

    public void delete(Distribution distribution) {
        distributionRepository.delete(distribution);
    }

    public Distribution findDistributionFetch(Distribution distribution) {
        if (distribution == null) {
            return null;
        }
        else {
            return distributionRepository.findDistributionByIdFetch(distribution.getId());
        }
    }

    public Distribution findDistributionFetchEdition(Distribution distribution) {
        if (distribution == null) return null;
        else return distributionRepository.findDistributionByIdFetchEdition(distribution.getId());
    }

    public Distribution findDistributionFetchStaff(Distribution distribution) {
        if (distribution == null) return null;
        else return distributionRepository.findDistributionByIdFetchStaff(distribution.getId());
    }

    public Distribution findDistributionFetchReader(Distribution distribution) {
        if (distribution == null) return null;
        else return distributionRepository.findDistributionByIdFetchReader(distribution.getId());
    }
}
