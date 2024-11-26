package com.maybank.repository;

import com.maybank.data.AcCodeMap;
import com.maybank.data.CompanyCode;
import com.maybank.data.CompanyCodeKey;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DynamicCompanyCodeRepository {
    private final EntityManager entityManager;

    public DynamicCompanyCodeRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Map<CompanyCodeKey, CompanyCode> fetchCompanyCodeMap() {

        String sql = "SELECT COM_COA_SEG1 AS comcoaseg1,COM_UNIT_SEG2 AS comUnitSeg2, COM_DESC AS comDesc, EGL_ENT_SEG1 AS eglEntSeg1, EGL_ENT_NAME AS eglEntName, IS_ENABLED AS isEnabled, COMPANY_CODE_ID AS companyCodeId, STAGE AS stage FROM T_EGL_COMPANY_CODE_MAP";
        Query query = entityManager.createNativeQuery(sql, Map.class);
        // Initialize the result map
        Map<CompanyCodeKey, CompanyCode> resultMap = new HashMap<>();

        List<Map<String, String>> resultList = query.getResultList();
        for (Map<String, String> result : resultList) {
            resultMap.put(new CompanyCodeKey(result.get("comcoaseg1"), result.get("comunitseg2")), new CompanyCode(
                    result.get("comdesc"),
                    result.get("eglentname"),
                    null != result.get("isenabled") ? String.valueOf(result.get("isenabled")).charAt(0) : null,
                    null != result.get("companycodeid") ? Integer.parseInt(result.get("companycodeid")) : null,
                    result.get("stage")
            ));
        }
        return resultMap;
    }
}