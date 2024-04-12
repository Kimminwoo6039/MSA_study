package com.example.catalogsservice.controller;

import com.example.catalogsservice.dto.ResponseCatalog;
import com.example.catalogsservice.entity.CatalogEntity;
import com.example.catalogsservice.service.CatalogService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/catalog-service")
public class CatalogController {

    @Autowired
    private Environment environment;

    @Autowired
    CatalogService catalogService;

    @GetMapping("/health_check")
    public String status(HttpServletRequest request) {
        return "Its Port %s".formatted(request.getServerPort());
    }

    @GetMapping("/catalogs")
    public ResponseEntity<List<ResponseCatalog>> getCatalogs() {
        Iterable<CatalogEntity> allCatalogs = catalogService.getAllCatalogs();

        List<ResponseCatalog> result = new ArrayList<>();

        allCatalogs.forEach(catalogEntity -> {
            result.add(
                    ResponseCatalog.builder()
                            .productId(catalogEntity.getProductId())
                            .productName(catalogEntity.getProductName())
                            .stock(catalogEntity.getStock())
                            .unitPrice(catalogEntity.getUnitPrice())
                            .createdAt(catalogEntity.getCreatedAt())
                            .build()
            );
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }
}
