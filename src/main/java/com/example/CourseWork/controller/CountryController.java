package com.example.CourseWork.controller;

import com.example.CourseWork.dto.CountryDTO;
import com.example.CourseWork.dto.responses.CountryResponse;
import com.example.CourseWork.service.CountryService;
import com.example.CourseWork.utils.ApplicationConstants;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:0001")
@RestController
@RequestMapping("/api/countries")
public class CountryController {
    private CountryService countryService;
    
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }
    @GetMapping("")
    public CountryResponse getAllCountries(
            @RequestParam(value = "pageNo", defaultValue = ApplicationConstants.DEFAULT_PAGE_NUMBER, required = false) int pageNo,
            @RequestParam(value = "pageSize", defaultValue = ApplicationConstants.DEFAULT_PAGE_SIZE, required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = ApplicationConstants.DEFAULT_SORT_BY, required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = ApplicationConstants.DEFAULT_SORT_DIRECTION, required = false) String sortDir)
    {
        return countryService.getAllCountries(pageNo, pageSize, sortBy, sortDir);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CountryDTO> getCountryById(@PathVariable("id") Long id) {
        Optional<CountryDTO> countryDTO = Optional.ofNullable(countryService.findCountryById(id));
        if (countryDTO.isPresent()) {
            return new ResponseEntity<>(countryDTO.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("")
    public ResponseEntity<CountryDTO> createCountry(@RequestBody CountryDTO countryDTO) {
        return new ResponseEntity<>(countryService.createCountry(countryDTO), HttpStatus.CREATED);
    }


    @PutMapping("/{id}")
    public ResponseEntity<CountryDTO> updateCountry(@PathVariable("id") Long id, @RequestBody CountryDTO countryDTO) {
        Optional<CountryDTO> countryData = Optional.ofNullable(countryService.findCountryById(id));
        if (countryData.isPresent()) {
            countryService.updateCountryInfo(countryDTO, id);
            return new ResponseEntity<>(HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteCountry(@PathVariable("id") Long id) {
        try {
            countryService.deleteCountry(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
