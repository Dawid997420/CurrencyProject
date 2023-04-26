package com.example.CurrencyProject.controller;

import com.example.CurrencyProject.model.Material;
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

    public MaterialController(MaterialService materialService) {
        this.materialService = materialService;
    }

    @GetMapping
    public ResponseEntity<List<Material>> getAllMaterials() {

       return  ResponseEntity.ok(materialService.getAllMaterials());
    }

    @GetMapping("days/{name}/{days}")
    public ResponseEntity<List<Material>> getMaterialForDays(@PathVariable String name,
                                                             @PathVariable int days) {

        return ResponseEntity.ok(materialService.getMaterialForDays(name,days));
   }

    @GetMapping("years/{name}/{years}")
    public ResponseEntity<List<Material>> getMaterialForYears(@PathVariable String name,
                                                             @PathVariable int years) {

        return ResponseEntity.ok(materialService.getMaterialForYears(name,years));
    }

}
