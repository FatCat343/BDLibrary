package com.bdcourse.library.reader.worker.profession;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProfessionService {
    @Autowired
    private ProfessionRepository professionRepository;

    public List<Profession> findAll(){
        return (List<Profession>) professionRepository.findAll();
    }

    public void delete(Profession profession) {
        professionRepository.delete(profession);
    }

    public void save(Profession profession) {
        professionRepository.save(profession);
    }

    public boolean exist(Profession profession) {
        return professionRepository.existsProfessionByName(profession.getName());
    }
}
