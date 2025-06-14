package com.tecnocampus.kingdoms.application.dto;

import java.time.LocalDateTime;

public record KingdomDTO(String id, LocalDateTime createdAt, int gold, int citizens, int food) {}
