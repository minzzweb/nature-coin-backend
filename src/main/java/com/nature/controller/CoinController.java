package com.nature.controller;



import java.util.List;
import java.util.Locale;
import java.util.Optional;

import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nature.domain.ChargeCoin;
import com.nature.service.CoinService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@RestController
@CrossOrigin(origins = { "http://localhost:8081", "http://localhost:3000" })
@RequestMapping("/coins")
public class CoinController {

	private final CoinService coinService;
	
	private final MessageSource messageSource;
	

	// 코인 충전 
	@PostMapping
	public ResponseEntity<String> grantCoin(@RequestBody ChargeCoin chargeCoin) throws Exception{
		
		try {
			
			Long imageId = chargeCoin.getImageId();
			String imagewriter = chargeCoin.getImagewriter();
			Integer amount = chargeCoin.getAmount();
			
			log.info("imageId " + imageId + " : grantCoin " + imagewriter +"님에게 " + amount + "지급");
			
			ChargeCoin chargeCoinEntity = new ChargeCoin();
			
			chargeCoinEntity.setImageId(imageId);
			chargeCoinEntity.setImagewriter(imagewriter);
			chargeCoinEntity.setAmount(amount);
			
			coinService.grant(chargeCoinEntity);
			
			
		   String message = messageSource.getMessage("coin.chargingComplete", null, Locale.KOREAN);
		   return new ResponseEntity<>(message, HttpStatus.OK);
		    
		}catch (Exception e) {
			
			return new ResponseEntity<>("Error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}	
	}
	
	
	@GetMapping
	public ResponseEntity<List<ChargeCoin>> list() throws Exception {

       List<ChargeCoin> chargeCoins = this.coinService.list();  
       
       for(ChargeCoin coin : chargeCoins) {
    	   System.out.println("response : " + coin.getHistoryNo());
       }
	   return new ResponseEntity<>(chargeCoins, HttpStatus.OK);
     
	}
	
	@GetMapping("/{imageId}")
	public ResponseEntity<Boolean> granted(@PathVariable("imageId") Long imageId) throws Exception {
	    
		log.info("granted image");
	  
	    ChargeCoin chargeCoinOptional = coinService.readGranted(imageId);
	    log.info("granted image : " +  chargeCoinOptional);
	    
	    boolean hasChargeCoin = chargeCoinOptional != null;;
	   
	     return new ResponseEntity<>(hasChargeCoin, HttpStatus.OK);
	  
	}
	
}
