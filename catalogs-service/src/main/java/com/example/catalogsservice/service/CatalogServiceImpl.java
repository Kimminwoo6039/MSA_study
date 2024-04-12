package com.example.catalogsservice.service;

import com.example.catalogsservice.entity.CatalogEntity;
import com.example.catalogsservice.repository.CatalogRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class CatalogServiceImpl implements CatalogService{

    @Autowired
    CatalogRepository catalogRepository;

    @Autowired
    Environment environment;

    @Override
    public Iterable<CatalogEntity> getAllCatalogs() {
        return catalogRepository.findAll();
    }
}
