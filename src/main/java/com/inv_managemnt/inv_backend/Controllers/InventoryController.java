package com.inv_managemnt.inv_backend.Controllers;

import com.inv_managemnt.inv_backend.dtos.CreateInvDTO;
import com.inv_managemnt.inv_backend.dtos.GetInvDTO;
import com.inv_managemnt.inv_backend.dtos.UpdateInvDTO;
import com.inv_managemnt.inv_backend.services.InventoryService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class InventoryController {

    private final InventoryService service;

    public InventoryController(InventoryService service) {
        this.service = service;
    }

    @GetMapping("products/{pid}/inventory")
    public ResponseEntity<GetInvDTO> getInv(@PathVariable int pid){
        return ResponseEntity.ok(service.getInv(pid));
    }

    @PostMapping("/products/{pid}/inventory")
    public ResponseEntity<GetInvDTO> createInv(@Valid @RequestBody CreateInvDTO dto,
                                               @PathVariable("pid") int pid){
        return ResponseEntity.ok(service.createInv(dto, pid));
    }

    @PatchMapping("products/{pid}/inventory")
    public ResponseEntity<GetInvDTO> updateInv(@Valid @RequestBody UpdateInvDTO dto,
                                               @PathVariable("pid") int pid){
        return ResponseEntity.ok(service.updateInv(dto, pid));
    }
}
