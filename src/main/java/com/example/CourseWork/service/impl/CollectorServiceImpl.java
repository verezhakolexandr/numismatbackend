package com.example.CourseWork.service.impl;

import com.example.CourseWork.dto.CollectorDTO;
import com.example.CourseWork.dto.responses.CollectorResponse;
import com.example.CourseWork.model.Collector;
import com.example.CourseWork.repository.CollectorRepository;
import com.example.CourseWork.service.CollectorService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CollectorServiceImpl implements CollectorService {
    private CollectorRepository collectorRepository;

    private CollectorDTO toCollectorDTO(Collector collector){
        CollectorDTO collectorDTO;
        collectorDTO = new CollectorDTO(collector.getName(), collector.getSurname(), collector.getNumberOfCoins(),
                collector.getNumberOfRareCoins());
        return collectorDTO;
    }

    public CollectorServiceImpl(CollectorRepository collectorRepository) {
        this.collectorRepository = collectorRepository;
    }
    @Override
    public CollectorDTO createCollector(CollectorDTO collectorDTO) {
        Collector collector = new Collector();
        collector.setName(collectorDTO.name());
        collector.setSurname(collectorDTO.surname());
        collector.setNumberOfCoins(collectorDTO.numberOfCoins());
        collector.setNumberOfRareCoins(collectorDTO.numberOfRareCoins());
        return toCollectorDTO(collectorRepository.save(collector));
    }

    @Override
    public CollectorDTO findCollectorById(Long id) {
        Optional<Collector> collector = collectorRepository.findById(id);
        if(collector.isPresent()) {
            return toCollectorDTO(collector.get());
        }
        return null;
    }

    @Override
    public CollectorDTO updateCollectorInfo(CollectorDTO collectorDTO, Long id) {
        Optional<Collector> collector = collectorRepository.findById(id);
        if (collector.isPresent()) {
            Collector collectorModel = collector.get();
            collectorModel.setName(collectorDTO.name());
            collectorModel.setSurname(collectorDTO.surname());
            collectorModel.setNumberOfCoins(collectorDTO.numberOfCoins());
            collectorModel.setNumberOfRareCoins(collectorDTO.numberOfRareCoins());
            return toCollectorDTO(collectorRepository.save(collectorModel));
        }
        return null;
    }

    @Override
    public CollectorDTO deleteCollector(Long id) {
        Optional<Collector> collectorData = collectorRepository.findById(id);
        if (collectorData.isPresent()) {
            collectorRepository.delete(collectorData.get());
        }
        return null;
    }

    @Override
    public CollectorResponse getAllCollectors(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();

        Pageable pageable = PageRequest.of(pageNo, pageSize, sort);

        Page<Collector> collectors = collectorRepository.findAll(pageable);
        List<Collector> listOfCollectors = collectors.getContent();

        List<CollectorDTO> content = listOfCollectors.stream().map(collector -> toCollectorDTO(collector)).collect(Collectors.toList());
        CollectorResponse collectorResponse = new CollectorResponse();
        collectorResponse.setContent(content);
        collectorResponse.setPageNo(collectors.getNumber());
        collectorResponse.setPageSize(collectors.getSize());
        collectorResponse.setTotalElements(collectors.getTotalElements());
        collectorResponse.setTotalPages(collectors.getTotalPages());
        collectorResponse.setLast(collectors.isLast());

        return collectorResponse;
    }
}
