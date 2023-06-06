package com.example.CurrencyProject.controller;

import com.example.CurrencyProject.dto.material.MaterialSymbol;
import com.example.CurrencyProject.model.material.Material;
import com.example.CurrencyProject.scraper.material.MaterialScrapper;
import com.example.CurrencyProject.scraper.metal.enums.Metal;
import com.example.CurrencyProject.scraper.metal.MetalScrapper;
import com.example.CurrencyProject.service.MaterialService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("material")
public class MaterialController {


    private final MaterialService materialService ;


    private final MetalScrapper metalScrapper ;

    private final MaterialScrapper materialScrapper;

    public MaterialController(MaterialService materialService, MetalScrapper metalScrapper,
                              MaterialScrapper materialScrapper) {

        this.materialService = materialService;
        this.metalScrapper = metalScrapper;
        this.materialScrapper = materialScrapper;
    }

    @GetMapping
    public ResponseEntity<List<Material>> getAllMaterials() throws Exception {

       return  ResponseEntity.ok(materialService.getAllMaterials());
    }

    @GetMapping("days/{material}/{days}")
    public ResponseEntity<?> getMaterialForDays(@PathVariable String material,
                                                @PathVariable String days) {

        if ( days.equals("max")) {
          return ResponseEntity.ok( materialService.getMaterialMaximum(MaterialSymbol.valueOf(material)));
        }

        try {
            MaterialSymbol symbol = MaterialSymbol.valueOf(material) ;
            long daysNumber = Long.parseLong(days);

            return ResponseEntity.ok(materialService
                    .getMaterialForDays(symbol,daysNumber));
        } catch (Exception e) {

            return ResponseEntity.badRequest().body("Material doesn't exist, or wrong days value");
        }


   }

    @GetMapping("years/{material}/{years}")
    public ResponseEntity<List<Material>> getMaterialForYears(@PathVariable String material,
                                                             @PathVariable int years) {
        MaterialSymbol symbol = MaterialSymbol.valueOf(material);

        return ResponseEntity.ok(materialService.getMaterialForYears(symbol,years));
    }


    @GetMapping("/test")
    public ResponseEntity<String> getTest() throws Exception {
        return  ResponseEntity.ok(materialScrapper.getSite().outerHtml());
    }




}
