package com.inv_managemnt.inv_backend.Controllers;

import com.inv_managemnt.inv_backend.dtos.CreateProductDTO;
import com.inv_managemnt.inv_backend.dtos.GetProductDTO;
import com.inv_managemnt.inv_backend.services.ProductService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    @GetMapping("/products/{pid}")
    public ResponseEntity<GetProductDTO> getProdById(@PathVariable int pid){
        return ResponseEntity.ok(service.getProdById(pid));
    }

    @PostMapping("/products")
    public ResponseEntity<GetProductDTO> createProd(@Valid @RequestBody CreateProductDTO dto){
        GetProductDTO prod = service.createProd(dto);
        return ResponseEntity.ok().body(prod);
    }
}
