package com.inv_managemnt.inv_backend.repos;

import com.inv_managemnt.inv_backend.models.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface InventoryRepo extends JpaRepository<Inventory, Integer> {
    Optional<Inventory> findByProductPid(int pid);

    boolean existsByProductPid(int pid);

}
