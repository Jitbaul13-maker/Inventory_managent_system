package com.inv_managemnt.inv_backend.services;

import com.inv_managemnt.inv_backend.dtos.CreateInvDTO;
import com.inv_managemnt.inv_backend.dtos.GetInvDTO;
import com.inv_managemnt.inv_backend.dtos.UpdateInvDTO;
import com.inv_managemnt.inv_backend.exceptions.ProductNotFoundException;
import com.inv_managemnt.inv_backend.models.Inventory;
import com.inv_managemnt.inv_backend.models.Product;
import com.inv_managemnt.inv_backend.repos.InventoryRepo;
import com.inv_managemnt.inv_backend.repos.ProductRepo;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@Transactional
public class InventoryService {

    private static final Logger log =
            LoggerFactory.getLogger(
                    InventoryService.class
            );

    @Value("${cache.inventory.ttl}")
    private long ttl;

    private final RedisTemplate<String, Object> invCache;
    private final InventoryRepo invRepo;
    private final ProductRepo productRepo;

    public InventoryService(
            RedisTemplate<String, Object> invCache,
            InventoryRepo invRepo,
            ProductRepo productRepo) {
        this.invCache = invCache;
        this.invRepo = invRepo;
        this.productRepo = productRepo;
    }

    public String keyHelper(){
        return "product:inventory:";
    }

    public GetInvDTO createInv(CreateInvDTO dto, int pid){

        Product p = productRepo
                .findById(pid)
                .orElseThrow(() -> new ProductNotFoundException("No Product found!"));

        if(invRepo.existsByProductPid(pid)){
            throw new RuntimeException("Inventory already exists");
        }

        if (dto.getReservedQuantity() <= dto.getAvailableQuantity()){

            Inventory inv = new Inventory();
            inv.setProduct(p);
            inv.setAvailableQuantity(dto.getAvailableQuantity());
            inv.setReservedQuantity(dto.getReservedQuantity());

            invRepo.save(inv);
            return new GetInvDTO(inv.getAvailableQuantity(), inv.getReservedQuantity());

        } else {
            throw new RuntimeException("Reserved quantity can not be more that available quantity");
        }
    }

    public GetInvDTO getInv(int pid){
        String key = keyHelper() + pid;

        Object cached = invCache
                        .opsForValue()
                        .get(key);
        if(cached instanceof GetInvDTO dto){
            log.info("Hit -> {}",key);
            return dto;
        } else {
            log.info("Miss -> {}",key);
        }

        Inventory inv = invRepo
                .findByProductPid(pid)
                .orElseThrow();

        GetInvDTO dto = new GetInvDTO(inv.getAvailableQuantity(), inv.getReservedQuantity());

        invCache
                .opsForValue()
                .set(key, dto, Duration.ofMinutes(ttl));

        log.info(
                "CACHE SET → {}",
                key
        );

        return dto;
    }

    public GetInvDTO updateInv(UpdateInvDTO dto, int pid){

        Inventory inv = invRepo.findByProductPid(pid).orElseThrow(() -> new RuntimeException("No inv found!"));

        if (dto.getReservedQuantity() <= dto.getAvailableQuantity()) {

            String key = keyHelper() + pid;

            inv.setAvailableQuantity(dto.getAvailableQuantity());
            inv.setReservedQuantity(dto.getReservedQuantity());

            invCache.delete(key);

            return new GetInvDTO(inv.getAvailableQuantity(), inv.getReservedQuantity());

        } else {
            throw new RuntimeException("Reserved quantity can not be more that available quantity");
        }
    }
}
