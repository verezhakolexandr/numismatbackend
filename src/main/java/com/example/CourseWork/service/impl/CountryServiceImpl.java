package com.example.CourseWork.service.impl;

import com.example.CourseWork.dto.CountryDTO;
import com.example.CourseWork.dto.responses.CountryResponse;
import com.example.CourseWork.model.Country;
import com.example.CourseWork.repository.CountryRepository;
import com.example.CourseWork.service.CountryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CountryServiceImpl implements CountryService {
    private CountryRepository countryRepository;

    private CountryDTO toCountryDTO(Country country){
        CountryDTO countryDTO;
        countryDTO = new CountryDTO(country.getName());
        return countryDTO;
    }

    public CountryServiceImpl(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }
    @Override
    public CountryDTO createCountry(CountryDTO countryDTO) {
        Country country = new Country();
        country.setName(countryDTO.name());
        return toCountryDTO(countryRepository.save(country));
    }

    @Override
    public CountryDTO findCountryById(Long id) {
        Optional<Country> country = countryRepository.findById(id);
        if(country.isPresent()) {
            return toCountryDTO(country.get());
        }
        return null;
    }

    @Override
    public CountryDTO updateCountryInfo(CountryDTO countryDTO, Long id) {
        Optional<Country> country = countryRepository.findById(id);
        if (country.isPresent()) {
            Country countryModel = country.get();
            countryModel.setName(countryDTO.name());
            return toCountryDTO(countryRepository.save(countryModel));
        }
        return null;
    }

    @Override
    public CountryDTO deleteCountry(Long id) {
        Optional<Country> countryData = countryRepository.findById(id);
        if (countryData.isPresent()) {
            countryRepository.delete(countryData.get());
        }
        return null;
    }

    @Override
    public CountryResponse getAllCountries(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Country> countries = countryRepository.findAll(pageable);
        List<Country> listOfCountries = countries.getContent();

        List<CountryDTO> content = listOfCountries.stream().map(country -> toCountryDTO(country)).collect(Collectors.toList());
        CountryResponse countryResponse = new CountryResponse();
        countryResponse.setContent(content);
        countryResponse.setPageNo(countries.getNumber());
        countryResponse.setPageSize(countries.getSize());
        countryResponse.setTotalElements(countries.getTotalElements());
        countryResponse.setTotalPages(countries.getTotalPages());
        countryResponse.setLast(countries.isLast());

        return countryResponse;
    }
}
