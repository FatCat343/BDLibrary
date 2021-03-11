package com.bdcourse.library.staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StaffService {
    @Autowired
    private StaffRepository staffRepository;

    public List<Staff> findAll(){
        return (List<Staff>) staffRepository.findAll();
    }

    public Staff findStaffByIdFetch(Staff staff) {
        if (staff == null) return null;
        else return staffRepository.findStaffByIdFetch(staff.getId());
    }

    public List<Staff> findAllFetch() {
        return staffRepository.findAllFetch();
    }

    public Staff save(Staff staff) {
        return staffRepository.save(staff);
    }

    public void delete(Staff staff) {
        staffRepository.delete(staff);
    }
}
