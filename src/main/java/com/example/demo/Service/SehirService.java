package com.example.demo.Service;

import com.example.demo.Entity.SehirEntity;
import com.example.demo.Repository.SehirRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SehirService {

    @Autowired
    private SehirRepository sehirRepository;

    public List<SehirEntity> getAllSehirler() {
        return sehirRepository.findAll();
    }
}
