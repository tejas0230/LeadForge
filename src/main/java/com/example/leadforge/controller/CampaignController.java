package com.example.leadforge.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.leadforge.dto.CampaignRequestDTO;
import com.example.leadforge.dto.CampaignResponseDTO;
import com.example.leadforge.service.CampaignService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v1/campaigns")
public class CampaignController {

    private final CampaignService campaignService;

    public CampaignController(CampaignService campaignService) {
        this.campaignService = campaignService;
    }

    @PostMapping
    public ResponseEntity<CampaignResponseDTO> createCampaign(@Valid @RequestBody CampaignRequestDTO campaignRequestDTO) {
        CampaignResponseDTO campaignResponseDTO = campaignService.createCampaign(campaignRequestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(campaignResponseDTO);
    }

    @GetMapping()
    public ResponseEntity<List<CampaignResponseDTO>> getAllCampaigns() {
        List<CampaignResponseDTO> campaignResponseDTOs = campaignService.getAllCampaigns();
        return ResponseEntity.status(HttpStatus.OK).body(campaignResponseDTOs);
    }

}
