package com.tecnocampus.kingdoms.api;

import com.tecnocampus.kingdoms.application.KingdomService;
import com.tecnocampus.kingdoms.application.dto.KingdomDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/kingdoms")
public class KingdomRestController {
    private final KingdomService service;

    public KingdomRestController(KingdomService service) {
        this.service = service;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public KingdomDTO create(@RequestBody KingdomDTO dto) {
        return service.createKingdom(dto);
    }

    @PostMapping("/{id}")
    public KingdomDTO startProduction(@PathVariable String id) {
        return service.startProduction(id);
    }

    @PostMapping("/{id}/invest")
    public KingdomDTO invest(@PathVariable String id, @RequestParam String type, @RequestBody Map<String, Integer> body) {
        return service.invest(id, type, body.getOrDefault("gold",0));
    }

    @GetMapping("/{id}")
    public KingdomDTO getStatus(@PathVariable String id) {
        return service.getStatus(id);
    }

    @GetMapping("/richest")
    public KingdomDTO getRichest() {
        return service.getRichest();
    }

    @PostMapping("/{id}/attack/{targetId}")
    public KingdomDTO attack(@PathVariable String id, @PathVariable("targetId") String targetId) {
        return service.attack(id, targetId);
    }
}
