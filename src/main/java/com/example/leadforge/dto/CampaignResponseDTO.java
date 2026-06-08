package com.example.leadforge.dto;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.example.leadforge.utils.CampaignStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CampaignResponseDTO {

    private UUID id;
    private String name;
    private List<String> queries;
    private CampaignStatus status;
    private int totalQueries;
    private int completedQueries;
    private int totalBusinesses;
    private int scrappedBusinesses;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
