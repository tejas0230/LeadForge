package com.example.leadforge.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.leadforge.entity.CampaignEntity;

public interface CampaignRepository extends JpaRepository<CampaignEntity, UUID> {

}
