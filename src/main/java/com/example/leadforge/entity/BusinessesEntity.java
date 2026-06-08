package com.example.leadforge.entity;

import java.util.UUID;

import org.springframework.data.annotation.Id;

import com.example.leadforge.utils.ScrapeStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "businesses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class BusinessesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name="campaign_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "campaign_id" , nullable = false)
    private CampaignEntity campaign;

    @Column(name="query_id", nullable = false)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "query_id" , nullable = false)
    private QueriesEntity query;

    @Column(name="business_place_id", nullable = false)
    private String businessPlaceId;

    @Column(name="business_name", nullable = false)
    private String businessName;

    @Column(name="business_website", nullable = false)
    private String businessWebsite;

    @Column(name="business_phone", nullable = false)
    private String businessPhone;

    @Column(name="business_email", nullable = false)
    private String businessEmail;

    @Column(name="scrape_status", nullable = false)
    @Enumerated(EnumType.STRING)
    private ScrapeStatus scrapeStatus;
    
}
