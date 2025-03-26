package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.SearchedTrademarkDto;
import gov.saip.applicationservice.common.model.SearchedTrademark;
import gov.saip.applicationservice.common.repository.SearchedTrademarkRepository;
import gov.saip.applicationservice.common.service.SearchedTrademarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SearchedTrademarkServiceImpl implements SearchedTrademarkService {

    private final SearchedTrademarkRepository searchedTrademarkRepository;

    @Override
    public SearchedTrademarkDto saveSearchedTrademark(SearchedTrademarkDto searchedTrademarkDto) {
        SearchedTrademark searchedTrademark = convertToEntity(searchedTrademarkDto);
        SearchedTrademark savedTrademark = searchedTrademarkRepository.save(searchedTrademark);
        return convertToDto(savedTrademark);
    }

    @Override
    public List<SearchedTrademarkDto> getSearchedTrademarksByApplication(Long applicationInfoId) {
        List<SearchedTrademark> trademarks = searchedTrademarkRepository.findByApplicationInfoId(applicationInfoId);
        return trademarks.stream().map(this::convertToDto).collect(Collectors.toList());
    }
    @Override
    public void deleteSearchedTrademark(Long id) {
        if (!searchedTrademarkRepository.existsById(id)) {
            throw new IllegalArgumentException("Searched trademark not found with ID: " + id);
        }
        searchedTrademarkRepository.deleteById(id);
    }

    private SearchedTrademarkDto convertToDto(SearchedTrademark searchedTrademark) {
        return SearchedTrademarkDto.builder()
                .id(searchedTrademark.getId())
                .applicationInfoId(searchedTrademark.getApplicationInfoId())
                .requestNumber(searchedTrademark.getRequestNumber())
                .registrationNumber(searchedTrademark.getRegistrationNumber())
                .nameAr(searchedTrademark.getNameAr())
                .nameEn(searchedTrademark.getNameEn())
                .markImage(searchedTrademark.getMarkImage())
                .countryCode(searchedTrademark.getCountryCode())
                .publicationDate(searchedTrademark.getPublicationDate())
                .registrationDate(searchedTrademark.getRegistrationDate())
                .filingDate(searchedTrademark.getFilingDate())
                .markStatus(searchedTrademark.getMarkStatus())
                .trademarkType(searchedTrademark.getTrademarkType())
                .description(searchedTrademark.getDescription())
                .owner(searchedTrademark.getOwner())
                .representative(searchedTrademark.getRepresentative())
                .niceClassification(searchedTrademark.getNiceClassification()) // Already overridden in entity
                .build();
    }

    private SearchedTrademark convertToEntity(SearchedTrademarkDto dto) {
        return SearchedTrademark.builder()
                .applicationInfoId(dto.getApplicationInfoId())
                .requestNumber(dto.getRequestNumber())
                .registrationNumber(dto.getRegistrationNumber())
                .nameAr(dto.getNameAr())
                .nameEn(dto.getNameEn())
                .markImage(dto.getMarkImage())
                .countryCode(dto.getCountryCode())
                .publicationDate(dto.getPublicationDate())
                .registrationDate(dto.getRegistrationDate())
                .filingDate(dto.getFilingDate())
                .markStatus(dto.getMarkStatus())
                .trademarkType(dto.getTrademarkType())
                .description(dto.getDescription())
                .owner(dto.getOwner())
                .representative(dto.getRepresentative())
                .niceClassification(
                        dto.getNiceClassification() != null
                                ? String.join(",", dto.getNiceClassification())
                                : null
                )                    // This will be stored as a comma-separated string in the DB
                .build();
    }
}
