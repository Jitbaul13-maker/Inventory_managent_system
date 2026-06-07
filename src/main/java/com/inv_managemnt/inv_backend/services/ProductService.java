package com.inv_managemnt.inv_backend.services;

import com.inv_managemnt.inv_backend.dtos.CreateProductDTO;
import com.inv_managemnt.inv_backend.dtos.GetProductDTO;
import com.inv_managemnt.inv_backend.dtos.UpdateProductDTO;
import com.inv_managemnt.inv_backend.exceptions.ProductNotFoundException;
import com.inv_managemnt.inv_backend.models.Product;
import com.inv_managemnt.inv_backend.repos.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Transactional
public class ProductService {

    private final ProductRepo repo;
    private final RedisTemplate<String, Object> redisTemplate;

    public ProductService(ProductRepo repo, RedisTemplate<String, Object> redisTemplate) {
        this.repo = repo;
        this.redisTemplate = redisTemplate;
    }

    public String keyHelper(){
        return "product:";
    }

    public GetProductDTO getProdById(int pid) {
        Product p = repo.findById(pid).orElseThrow(() -> new ProductNotFoundException("No product found!"));

        String key  = keyHelper() + pid;

        Object cache = redisTemplate.opsForValue().get(key);

        if (cache instanceof GetProductDTO dto){
            return dto;
        }

        GetProductDTO dto = new GetProductDTO(p.getName(), p.getSku(), p.getPrice());

        redisTemplate.opsForValue().set(key, dto, Duration.ofMinutes(1));

        return dto;
    }

    public GetProductDTO createProd(CreateProductDTO dto) {

        Product p = new Product();

        p.setSku(dto.getSku());
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());

        repo.save(p);

        return new GetProductDTO(p.getName(), p.getSku(), p.getPrice());
    }

    public GetProductDTO updateProd(int pid, UpdateProductDTO dto) {
        Product p = repo.findById(pid).orElseThrow(() -> new ProductNotFoundException("No product found"));

        String key = keyHelper() + pid;

        p.setSku(dto.getSku());
        p.setName(dto.getName());
        p.setPrice(dto.getPrice());

        redisTemplate.delete(key);

        return new GetProductDTO(p.getSku(), p.getName(), p.getPrice());
    }
}
