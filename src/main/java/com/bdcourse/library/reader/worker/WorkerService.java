package com.bdcourse.library.reader.worker;

import com.bdcourse.library.UI.StudentUI.StudentSort;
import com.bdcourse.library.UI.WorkerUI.WorkerSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WorkerService {
    @Autowired
    private WorkerRepository workerRepository;

    public List<Worker> findAll() {
        return (List<Worker>) workerRepository.findAll();
    }

    public Worker save(Worker worker) {
        return workerRepository.save(worker);
    }

    public void delete(Worker worker) {
         workerRepository.delete(worker);
    }

    public WorkerSort createSort(String propertyName, boolean descending) {
        return new WorkerSort(propertyName, descending);
    }
}
