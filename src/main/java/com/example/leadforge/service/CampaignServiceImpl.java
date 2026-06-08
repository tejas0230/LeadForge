package com.example.leadforge.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.leadforge.dto.CampaignRequestDTO;
import com.example.leadforge.dto.CampaignResponseDTO;
import com.example.leadforge.repository.CampaignRepository;

@Service
public class CampaignServiceImpl implements CampaignService{

    private final CampaignRepository campaignRepository;

    public CampaignServiceImpl(CampaignRepository campaignRepository) {
        this.campaignRepository = campaignRepository;
    }

    @Override
    public CampaignResponseDTO createCampaign(CampaignRequestDTO campaignRequestDTO) {

    }

    @Override
    public List<CampaignResponseDTO> getAllCampaigns() {

    }
}
