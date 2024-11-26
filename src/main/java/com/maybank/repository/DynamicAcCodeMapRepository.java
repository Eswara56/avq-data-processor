package com.maybank.repository;

import com.maybank.data.AcCodeMap;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

//T_EGL_AC_CODE_MAP
@Repository
public class DynamicAcCodeMapRepository {

    private final EntityManager entityManager;

    public DynamicAcCodeMapRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Map<String, AcCodeMap> fetchAcCodeMap() {
        String sql = "SELECT COM_GL_AC_CODE_SEG6 AS eglAcCodeSeg6,COM_GL_AC_DESC AS comGlAcDesc, EGL_AC_CODE_SEG3 AS eglAcCodeSeg3, EGL_PROD_CODE_SEG4 AS eglProdCodeSeg4, " +
                "EGL_INT_COM_CODE_SEG5 AS eglIntComCodeSeg5, EGL_FUTURE3_SEG6 AS eglFuture3Seg6, EGL_SUB_AC_SEG7 AS eglSubAcSeg7, EGL_FUTURE5_SEG8 AS eglFuture5Seg8, " +
                "EGL_REV_DUP AS eglRevDup, EGL_CODE_ID AS eglCodeId, STAGE AS stage FROM T_EGL_AC_CODE_MAP";
        Query query = entityManager.createNativeQuery(sql, Map.class);
        List<Map<String, String>> resultList = query.getResultList();
        Map<String, AcCodeMap> resultMap = new HashMap<>();
        for (Map<String, String> result : resultList) {
            resultMap.put(result.get("eglaccodeseg6"), new AcCodeMap(
                    result.get("comglacdesc"),
                    result.get("eglaccodeseg3"),
                    result.get("eglprodcodeseg4"),
                    result.get("eglintcomcodeseg5"),
                    result.get("eglfuture3seg6"),
                    result.get("eglsubacseg7"),
                    result.get("eglfuture5seg8"),
                    null != result.get("eglrevdup") ? String.valueOf(result.get("eglrevdup")).charAt(0) : null,
                    null != result.get("eglcodeid") ? Integer.parseInt(result.get("eglcodeid")) : null,
                    result.get("stage")
            ));
        }
        return resultMap;
    }
}
