package com.bdcourse.library.reader.worker.company;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> findAll() {
        return (List<Company>) companyRepository.findAll();
    }

    public void delete(Company company) {
        companyRepository.delete(company);
    }

    public void save(Company company) {
        companyRepository.save(company);
    }
}
