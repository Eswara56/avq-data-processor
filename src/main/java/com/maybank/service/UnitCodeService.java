package com.maybank.service;

import com.maybank.data.AcCodeMap;
import com.maybank.data.CompanyCode;
import com.maybank.data.CompanyCodeKey;
import com.maybank.data.UnitCodeMap;
import com.maybank.repository.DynamicAcCodeMapRepository;
import com.maybank.repository.DynamicCompanyCodeRepository;
import com.maybank.repository.DynamicUnitCodeMapRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UnitCodeService {
    @Autowired
    private DynamicUnitCodeMapRepository repo;
    @Autowired
    private DynamicAcCodeMapRepository acRepo;
    @Autowired
    private DynamicCompanyCodeRepository companyRepo;
    public Map<String, UnitCodeMap> fetchUnitCodeMap() {
        return repo.fetchUnitCodeMap();
    }

    public Map<String, AcCodeMap> fetchAcCodeMap() {
        return acRepo.fetchAcCodeMap();
    }

    public Map<CompanyCodeKey, CompanyCode> fetchCompanyCodeMap() {
        return companyRepo.fetchCompanyCodeMap();
    }
}
