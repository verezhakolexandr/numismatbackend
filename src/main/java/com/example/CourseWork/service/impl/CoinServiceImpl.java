package com.example.CourseWork.service.impl;

import com.example.CourseWork.dto.CoinDTO;
import com.example.CourseWork.dto.responses.CoinResponse;
import com.example.CourseWork.model.*;
import com.example.CourseWork.repository.CoinRepository;
import com.example.CourseWork.repository.CountryRepository;
import com.example.CourseWork.repository.HistoryRepository;
import com.example.CourseWork.service.CoinService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CoinServiceImpl implements CoinService {
    private CoinRepository coinRepository;
    private CountryRepository countryRepository;
    private HistoryRepository historyRepository;

    private CoinDTO toCoinDTO(Coin coin){
        CoinDTO coinDTO;
        coinDTO = new CoinDTO(coin.getCountry().getName(), coin.getHistory().getHistoryBody());
        return coinDTO;
    }

    public CoinServiceImpl(CoinRepository coinRepository,
                           CountryRepository countryRepository,
                           HistoryRepository historyRepository) {
        this.coinRepository = coinRepository;
        this.countryRepository = countryRepository;
        this.historyRepository = historyRepository;
    }
    @Override
    public CoinDTO createCoin(CoinDTO coinDTO) {
        Optional<Country> countryData = Optional.ofNullable(countryRepository.findByName(coinDTO.country()));
        Optional<History> historyData = Optional.ofNullable(historyRepository.findByHistoryBody(coinDTO.history()));
        if (countryData.isPresent() && historyData.isPresent()) {
            Country country = countryData.get();
            History history = historyData.get();
            Coin coin = new Coin();
            coin.setCountry(country);
            coin.setHistory(history);
            return toCoinDTO(coinRepository.save(coin));
        }
        return null;
    }

    @Override
    public CoinDTO findCoinById(Long id) {
        Optional<Coin> coin = coinRepository.findById(id);
        if(coin.isPresent()) {
            return toCoinDTO(coin.get());
        }
        return null;
    }

    @Override
    public CoinDTO updateCoinInfo(CoinDTO coinDTO, Long id) {
        Optional<Coin> coin = coinRepository.findById(id);
        Optional<Country> countryData = Optional.ofNullable(countryRepository.findByName(coinDTO.country()));
        Optional<History> historyData = Optional.ofNullable(historyRepository.findByHistoryBody(coinDTO.history()));
        if (coin.isPresent()) {
            Country country = countryData.get();
            History history = historyData.get();
            if (countryData.isPresent() && historyData.isPresent()) {
                Coin coinModel = coin.get();
                coinModel.setCountry(country);
                coinModel.setHistory(history);
                return toCoinDTO(coinRepository.save(coinModel));
            }
        }
        return null;
    }

    @Override
    public CoinDTO deleteCoin(Long id) {
        Optional<Coin> coin = coinRepository.findById(id);
        if (coin.isPresent()) {
            coinRepository.delete(coin.get());
        }
        return null;
    }

    @Override
    public CoinResponse getAllCoins(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Coin> coins = coinRepository.findAll(pageable);
        List<Coin> listOfCoins = coins.getContent();

        List<CoinDTO> content = listOfCoins.stream().map(coin -> toCoinDTO(coin))
                .collect(Collectors.toList());
        CoinResponse coinResponse = new CoinResponse();
        coinResponse.setContent(content);
        coinResponse.setPageNo(coins.getNumber());
        coinResponse.setPageSize(coins.getSize());
        coinResponse.setTotalElements(coins.getTotalElements());
        coinResponse.setTotalPages(coins.getTotalPages());
        coinResponse.setLast(coins.isLast());

        return coinResponse;
    }
}
