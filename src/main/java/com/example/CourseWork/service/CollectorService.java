package com.example.CourseWork.service;

import com.example.CourseWork.dto.CollectorDTO;
import com.example.CourseWork.dto.responses.CollectorResponse;
import org.springframework.stereotype.Service;

@Service
public interface CollectorService {
    CollectorDTO createCollector(CollectorDTO collectorDTO);

    CollectorDTO findCollectorById(Long id);

    CollectorDTO updateCollectorInfo(CollectorDTO collectorDTO, Long id);

    CollectorDTO deleteCollector(Long id);

    CollectorResponse getAllCollectors(int pageNo, int pageSize, String sortBy, String sortDir);
}
