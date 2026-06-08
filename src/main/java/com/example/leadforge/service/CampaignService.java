package com.example.leadforge.service;

import java.util.List;

import com.example.leadforge.dto.CampaignRequestDTO;
import com.example.leadforge.dto.CampaignResponseDTO;

public interface CampaignService {
    CampaignResponseDTO createCampaign(CampaignRequestDTO campaignRequestDTO);
    List<CampaignResponseDTO> getAllCampaigns();
} 
