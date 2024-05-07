package com.example.SaleCampaignApplication.Controller;

import com.example.SaleCampaignApplication.Payloads.SaleCampaignDTO;
import com.example.SaleCampaignApplication.Service.SaleCampaignService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saleCampaign")
public class SaleCampaignController {

    @Autowired
    private SaleCampaignService saleCampaignService;


    @PostMapping("/saveCampaign")
    public ResponseEntity<SaleCampaignDTO> saveCampaign(@RequestBody SaleCampaignDTO saleCampaignDTO){

        SaleCampaignDTO savedCampaign = this.saleCampaignService.saveCampaign(saleCampaignDTO);
        return new ResponseEntity<>(savedCampaign , HttpStatus.CREATED);
    }

    @GetMapping("/getCampaigns")
    public ResponseEntity<List<SaleCampaignDTO>> getAllSaleCampaigns(){
        List<SaleCampaignDTO> saleCampaignDTO = this.saleCampaignService.getAllSaleCampaigns();
        return new ResponseEntity<>(saleCampaignDTO , HttpStatus.OK);
    }



}
