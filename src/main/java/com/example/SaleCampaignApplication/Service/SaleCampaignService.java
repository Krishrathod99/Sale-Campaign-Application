package com.example.SaleCampaignApplication.Service;

import com.example.SaleCampaignApplication.Entities.CampaignDiscount;
import com.example.SaleCampaignApplication.Entities.PriceHistory;
import com.example.SaleCampaignApplication.Entities.Product;
import com.example.SaleCampaignApplication.Entities.SaleCampaign;
import com.example.SaleCampaignApplication.Exception.ResourceNotFoundException;
import com.example.SaleCampaignApplication.Payloads.CampaignDiscountDTO;
import com.example.SaleCampaignApplication.Payloads.SaleCampaignDTO;
import com.example.SaleCampaignApplication.Repositories.CampaignDiscountRepository;
import com.example.SaleCampaignApplication.Repositories.PriceHistoryRepository;
import com.example.SaleCampaignApplication.Repositories.ProductRepository;
import com.example.SaleCampaignApplication.Repositories.SaleCampaignRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SaleCampaignService {

    @Autowired
    private SaleCampaignRepository saleCampaignRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CampaignDiscountRepository campaignDiscountRepository;

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Scheduled(cron = "0 0 0 * * *")
    public void isCampaignStartingDateReached(){

        List<SaleCampaign> saleCampaigns = saleCampaignRepository.findAll();

        for (SaleCampaign saleCampaign : saleCampaigns){

            SaleCampaign campaign = saleCampaignRepository.findById(saleCampaign.getSaleId())
                    .orElseThrow(() -> new ResourceNotFoundException("SaleCampaign", "SaleId", saleCampaign.getSaleId()));

            LocalDate startDate = campaign.getStartDate();
            LocalDate todayDate = LocalDate.now();

            if (todayDate.isEqual(startDate)){
                startCampaign(saleCampaign);
            }
        }
    }

    @Scheduled(cron = "0 0 0 * * *")
    public void isCampaignEndingDateReached(){

        List<SaleCampaign> saleCampaigns = saleCampaignRepository.findAll();

        for (SaleCampaign saleCampaign : saleCampaigns){

            SaleCampaign campaign = saleCampaignRepository.findById(saleCampaign.getSaleId())
                    .orElseThrow(() -> new ResourceNotFoundException("SaleCampaign", "SaleId", saleCampaign.getSaleId()));

            LocalDate endDate = campaign.getEndDate();
            LocalDate todayDate = LocalDate.now();

            if (todayDate.isAfter(endDate)){
                endCampaign(saleCampaign);
            }
        }
    }

    public SaleCampaignDTO saveCampaign(SaleCampaignDTO saleCampaignDTO) {

        SaleCampaign saleCampaign = new SaleCampaign();
        saleCampaign.setTitle(saleCampaignDTO.getTitle());
        saleCampaign.setStartDate(saleCampaignDTO.getStartDate());
        saleCampaign.setEndDate(saleCampaignDTO.getEndDate());

        LocalDate startDate = saleCampaign.getStartDate();
        LocalDate endDate = saleCampaign.getEndDate();
        LocalDate todayDate = LocalDate.now();

        if (todayDate.isEqual(startDate) || (todayDate.isAfter(startDate) && todayDate.isBefore(endDate))){
            saleCampaign.setStatus("Current Campaign");
        }
        else if (todayDate.isBefore(startDate)){
            saleCampaign.setStatus("Upcoming Campaign");
        }
        else if (todayDate.isAfter(endDate)){
            saleCampaign.setStatus("Past Campaign");
        }

//        save SaleCampaign
        this.saleCampaignRepository.save(saleCampaign);


        CampaignDiscountDTO[] campaignDiscountDTOS = saleCampaignDTO.getCampaignDiscountDTOS();

        for (CampaignDiscountDTO campaignDiscountDTO : campaignDiscountDTOS){

            Product product = productRepository.findById(campaignDiscountDTO.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", campaignDiscountDTO.getProductId()));
            double discount = campaignDiscountDTO.getDiscount();

//            Save CampaignDiscount
            CampaignDiscount campaignDiscount = new CampaignDiscount();
            campaignDiscount.setProductId(campaignDiscountDTO.getProductId());
            campaignDiscount.setProduct(product);
            campaignDiscount.setPDiscount(discount);
            campaignDiscount.setSaleCampaign(saleCampaign);

            this.campaignDiscountRepository.save(campaignDiscount);

        }

        return this.modelMapper.map(saleCampaign , SaleCampaignDTO.class);
    }


    public void startCampaign(SaleCampaign saleCampaign){

        saleCampaign.setStatus("Current Campaign");
        this.saleCampaignRepository.save(saleCampaign);

        CampaignDiscount[] campaignDiscounts = this.campaignDiscountRepository.findBySaleCampaign(saleCampaign);

        for (CampaignDiscount campaignDiscount : campaignDiscounts) {

            int productId = campaignDiscount.getProduct().getProductId();

            Product product = this.productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));

            double pDiscount = campaignDiscount.getPDiscount();
            double currentPrice = product.getCurrentPrice();
            double discountPrice = currentPrice / pDiscount;
            int currentPriceAfterDiscount = (int) (currentPrice - discountPrice);

//                      save Product
            product.setCurrentPrice(currentPriceAfterDiscount);
            product.setDiscount(pDiscount);
            this.productRepository.save(product);

        }
    }


    public void endCampaign(SaleCampaign saleCampaign){

        saleCampaign.setStatus("Past Campaign");
        this.saleCampaignRepository.save(saleCampaign);

        CampaignDiscount[] campaignDiscounts = this.campaignDiscountRepository.findBySaleCampaign(saleCampaign);

        for (CampaignDiscount campaignDiscount : campaignDiscounts) {

            int productId = campaignDiscount.getProduct().getProductId();

            Product product = this.productRepository.findById(productId)
                    .orElseThrow(() -> new ResourceNotFoundException("Product", "ProductId", productId));

            PriceHistory history = this.priceHistoryRepository.findByProductId(productId);
            product.setCurrentPrice(history.getCurrentPrice());
            product.setDiscount(history.getDiscount());

//                 save Product
            this.productRepository.save(product);
        }
    }

    public List<SaleCampaignDTO> getAllSaleCampaigns() {

        List<SaleCampaign> saleCampaigns = this.saleCampaignRepository.findAll();
        return saleCampaigns.stream().map(saleCampaign -> this.modelMapper.map(saleCampaign, SaleCampaignDTO.class)).toList();
    }

}
