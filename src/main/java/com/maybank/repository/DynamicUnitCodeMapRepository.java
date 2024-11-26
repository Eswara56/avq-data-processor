package com.maybank.repository;

import com.maybank.config.FileConfig;
import com.maybank.data.UnitCodeMap;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.Tuple;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class DynamicUnitCodeMapRepository {
    private final EntityManager entityManager;


    public DynamicUnitCodeMapRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Map<String, UnitCodeMap> fetchUnitCodeMap() {
        String sql = "SELECT COM_UNIT_CODE_SEG4 AS comUnitCodeSeg4 , EGL_RC_SEG2 AS eglRcSeg2, UNIT_CODE_DESC AS unitCodeDesc, RC_DESC AS rcDesc, UNIT_CODE_ID AS unitCodeId, STAGE AS stage FROM T_EGL_UNIT_CODE_MAP";
//        Map<String, UnitCodeMap> map = new HashMap<>();
        Query query = entityManager.createNativeQuery(sql, Map.class);
        Map<String, UnitCodeMap> resultMap = new HashMap<>();
        List<Map<String, String>> resultList = query.getResultList();
        for (Map<String, String> result : resultList) {
            resultMap.put(result.get("comunitcodeseg4"), new UnitCodeMap(
                    result.get("eglrcseg2"),
                    result.get("unitcodedesc"),
                    result.get("rcdesc"),
                    null != result.get("unitcodeid") ? Integer.parseInt(result.get("unitcodeid")) : null,
                    result.get("stage")
            ));
        }
        return resultMap;
    }
}
