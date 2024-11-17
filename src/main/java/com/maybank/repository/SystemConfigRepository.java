package com.maybank.repository;
import com.maybank.config.QueryConfig;
import com.maybank.data.SystemConfigResponse;
import com.maybank.data.UpstreamFieldResponse;
import com.maybank.data.UpstreamResponseData;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Repository
public class SystemConfigRepository {


    private final EntityManager entityManager;

    private final QueryConfig queryConfig;


    public SystemConfigRepository(EntityManager entityManager, QueryConfig queryConfig) {
        this.entityManager = entityManager;
        this.queryConfig = queryConfig;
    }

    public SystemConfigResponse fetchSystemConfig(String applCode) {
        SystemConfigResponse response = new SystemConfigResponse();
        //Named query to fetch system configuration based on application code and directly map to SystemConfigResponse without any entity
        String sql = "select APPL_CODE as applcode, HEADER_FILE as headerfile, DETAIL_FILE as detailfile, FOOTER_FILE as footerfile, TBL_HDR as tblhdr, TBL_DTL as tbldtl, TBL_TRLR as tbltrlr from AVQFile.dbo.T_EGL_BASE_SYSTEMS where APPL_CODE = :applCode";
                //queryConfig.getQuery("systemConfig");
        try {
            Query query = entityManager.createNativeQuery(sql, Map.class);

            query.setParameter("applCode", applCode);
            Map<String, String> result = (Map<String, String>) query.getSingleResult();
            response.setApplCode(result.get("applcode"));
            response.setHeaderFile(result.get("headerfile"));
            response.setDetailFile(result.get("detailfile"));
            response.setFooterFile(result.get("footerfile"));
            response.setHeaderTable(result.get("tblhdr"));
            response.setTrailerTable(result.get("tbltrlr"));
            response.setDetailTable(result.get("tbldtl"));
        } catch (Exception e) {
            log.error("Error while fetching system configuration for application code: {}", applCode, e);
        }
        return response;
    }

    /**
     * This method to map {@link com.maybank.data.UpstreamResponseData} from database for Header, Detail and Trailer files
     */
    public UpstreamResponseData fetchUpstreamConfig(String systemId, String fileType) {
        UpstreamResponseData upstreamResponseData = new UpstreamResponseData();
        Map<String, UpstreamFieldResponse> field = null;
        String sql = "select SYSTEM_ID as systemid, FIELD_TYPE as fieldtype, FIELD_NAME as fieldname, FIELD_START as fieldstart, FIELD_END as fieldend, FIELD_LENGTH as fieldlength, UP_STREAM_ORDER upstreamorder from AVQFile.dbo.T_EGL_UPSTREAM_CONFIG where SYSTEM_ID=:systemId and FILE_TYPE=:fileType order by UP_STREAM_ORDER";
        try {
            Query query = entityManager.createNativeQuery(sql, Map.class);
            query.setParameter("systemId", systemId);
            query.setParameter("fileType", fileType);
            field = new HashMap<>();
            List<Map<String, Object>> queryResult = query.getResultList();
            for(Map<String, Object> result : queryResult) {
                UpstreamFieldResponse upstreamFieldResponse = new UpstreamFieldResponse();
                upstreamFieldResponse.setName((String)result.get("fieldname"));
                upstreamFieldResponse.setType((String)result.get("fieldtype"));
                upstreamFieldResponse.setStart((Integer) result.get("fieldstart"));
                upstreamFieldResponse.setEnd((Integer)result.get("fieldend"));
                upstreamFieldResponse.setLength((Integer)result.get("fieldlength"));
                upstreamFieldResponse.setOrder((Integer)result.get("upstreamorder"));
                field.put((String)result.get("fieldname"), upstreamFieldResponse);
            }
        } catch (Exception e) {
            log.error("Error while fetching upstream configuration for systemId: {} and fileType: {}", systemId, fileType, e);
        }
        upstreamResponseData.setFieldMap(field);
        upstreamResponseData.setFileType(fileType);
        return upstreamResponseData;
    }

    /**
     * This method to insert the data into header table.
     */
    public void insertHeaderData() {

    }

    /**
     * This method to insert the data into detail table.
     */
    public void insertDetailData() {

    }

    /**
     * This method to insert the data into trailer table.
     */
    public void insertTrailerData() {

    }

}
