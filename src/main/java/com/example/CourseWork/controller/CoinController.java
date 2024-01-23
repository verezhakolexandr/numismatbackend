package com.example.CourseWork.controller;

import com.example.CourseWork.dto.CoinDTO;
import com.example.CourseWork.dto.responses.CoinResponse;
import com.example.CourseWork.service.CoinService;
import com.example.CourseWork.utils.ApplicationConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:0001")
@RestController
@RequestMapping("/api/coins")
public class CoinController {
    private CoinService coinService;

    public CoinController(CoinService coinService) {
        this.coinService = coinService;
    }
    @GetMapping("")
    public CoinResponse getAllCoins(
            @RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir)
    {
        return coinService.getAllCoins(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CoinDTO> getCoinById(@PathVariable("id") Long id) {
        Optional<CoinDTO> coinDTO = Optional.ofNullable(coinService.findCoinById(id));
        if (coinDTO.isPresent()) {
            return new ResponseEntity<>(coinDTO.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<CoinDTO> createCoin(@RequestBody CoinDTO coinDTO) {
        return new ResponseEntity<>(coinService.createCoin(coinDTO), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CoinDTO> updateCoin(@PathVariable("id") Long id, @RequestBody CoinDTO coinDTO) {
        Optional<CoinDTO> coinData = Optional.ofNullable(coinService.findCoinById(id));
        if (coinData.isPresent()) {
            coinService.updateCoinInfo(coinDTO, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCoin(@PathVariable("id") Long id) {
        try {
            coinService.deleteCoin(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
