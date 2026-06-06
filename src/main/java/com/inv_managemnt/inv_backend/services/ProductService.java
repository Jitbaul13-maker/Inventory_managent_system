package com.inv_managemnt.inv_backend.services;

import com.inv_managemnt.inv_backend.dtos.CreateProductDTO;
import com.inv_managemnt.inv_backend.dtos.GetProductDTO;
import com.inv_managemnt.inv_backend.models.Product;
import com.inv_managemnt.inv_backend.repos.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {

    private final ProductRepo repo;

    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }

    public GetProductDTO getProdById(int pid) {
        Product p = repo.findById(pid).orElseThrow();

        return new GetProductDTO(p.getName(), p.getSku(), p.getPrice());
    }

    public GetProductDTO createProd(CreateProductDTO dto) {

        Product p = new Product();

        p.setSku(dto.getSku());
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());

        repo.save(p);

        return new GetProductDTO(p.getName(), p.getSku(), p.getPrice());
    }
}
