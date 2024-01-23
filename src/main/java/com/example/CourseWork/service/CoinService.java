package com.example.CourseWork.service;

import com.example.CourseWork.dto.CoinDTO;
import com.example.CourseWork.dto.responses.CoinResponse;
import org.springframework.stereotype.Service;

@Service
public interface CoinService {
    CoinDTO createCoin(CoinDTO coinDTO);

    CoinDTO findCoinById(Long id);

    CoinDTO updateCoinInfo(CoinDTO coinDTO, Long id);

    CoinDTO deleteCoin(Long id);

    CoinResponse getAllCoins(int pageNo, int pageSize, String sortBy, String sortDir);
}
