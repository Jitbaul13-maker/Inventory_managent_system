package com.inv_managemnt.inv_backend.services;

import com.inv_managemnt.inv_backend.dtos.CreateProductDTO;
import com.inv_managemnt.inv_backend.dtos.GetProductDTO;
import com.inv_managemnt.inv_backend.dtos.UpdateProductDTO;
import com.inv_managemnt.inv_backend.exceptions.ProductNotFoundException;
import com.inv_managemnt.inv_backend.models.Product;
import com.inv_managemnt.inv_backend.repos.ProductRepo;
import jakarta.transaction.Transactional;
import org.jspecify.annotations.Nullable;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class ProductService {

    private final ProductRepo repo;

    public ProductService(ProductRepo repo) {
        this.repo = repo;
    }

    @Cacheable(
            value = "products",
            key = "#pid"
    )
    public GetProductDTO getProdById(int pid) {
        Product p = repo.findById(pid).orElseThrow(() -> new ProductNotFoundException("No product found!"));

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

    @CacheEvict(
            value = "products",
            key = "#pid"
    )
    public @Nullable GetProductDTO updateProd(int pid, UpdateProductDTO dto) {
        Product p = repo.findById(pid).orElseThrow(() -> new ProductNotFoundException("No product found"));

        p.setSku(dto.getSku());
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());

        return new GetProductDTO(p.getSku(), p.getName(), p.getPrice());
    }
}
