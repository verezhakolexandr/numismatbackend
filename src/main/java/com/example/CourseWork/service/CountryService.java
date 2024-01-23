package com.example.CourseWork.service;

import com.example.CourseWork.dto.CountryDTO;
import com.example.CourseWork.dto.HistoryDTO;
import com.example.CourseWork.dto.responses.CountryResponse;
import com.example.CourseWork.dto.responses.HistoryResponse;
import org.springframework.stereotype.Service;

@Service
public interface CountryService {
    CountryDTO createCountry(CountryDTO countryDTO);

    CountryDTO findCountryById(Long id);

    CountryDTO updateCountryInfo(CountryDTO countryDTO, Long id);

    CountryDTO deleteCountry(Long id);

    CountryResponse getAllCountries(int pageNo, int pageSize, String sortBy, String sortDir);
}
