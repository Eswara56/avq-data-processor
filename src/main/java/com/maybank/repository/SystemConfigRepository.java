package com.maybank.repository;

import com.maybank.config.FileConfig;
import com.maybank.config.QueryConfig;
import com.maybank.data.SystemConfigResponse;
import com.maybank.data.UpstreamFieldResponse;
import com.maybank.data.UpstreamResponseData;
import com.maybank.service.AuditLogService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Repository
public class SystemConfigRepository {


    private final EntityManager entityManager;

    private final QueryConfig queryConfig;
    private final FileConfig fileConfig;
    private final AuditLogService auditLogService;
    private static final Logger log = LoggerFactory.getLogger(SystemConfigRepository.class);
    public SystemConfigRepository(EntityManager entityManager, QueryConfig queryConfig, FileConfig fileConfig,AuditLogService auditLogService) {
        this.entityManager = entityManager;
        this.queryConfig = queryConfig;
        this.fileConfig = fileConfig;
       this.auditLogService = auditLogService;
    }

    public SystemConfigResponse fetchSystemConfig(String applCode) {
        SystemConfigResponse response = new SystemConfigResponse();
        // Dynamically load the database table prefix and base folder path from the configuration
        String databaseType = fileConfig.getDatabaseType();
        String baseFolderPath = fileConfig.getBaseFolderPath();
        //Named query to fetch system configuration based on application code and directly map to SystemConfigResponse without any entity
        String sql = "select APPL_CODE as applcode, FILE_FOLDER as filefolder, FILE_TYPE as filetype, HEADER_FILE as headerfile, DETAIL_FILE as detailfile, FOOTER_FILE as footerfile, TBL_HDR as tblhdr, TBL_DTL as tbldtl, TBL_TRLR as tbltrlr from AVQFile.dbo.T_EGL_BASE_SYSTEMS where APPL_CODE = :applCode";
        try {
            Query query = entityManager.createNativeQuery(sql, Map.class);

            query.setParameter("applCode", applCode);
            Map<String, String> result = (Map<String, String>) query.getSingleResult();
            //Map the result to SystemConfigResponse
            response.setAPPL_CODE(result.get("applcode"));
            response.setHEADER_FILE(result.get("headerfile"));
            response.setDETAIL_FILE(result.get("detailfile"));
            response.setFOOTER_FILE(result.get("footerfile"));
            response.setTBL_HDR(result.get("tblhdr"));
            response.setBL_TRLR(result.get("tbltrlr"));
            response.setTBL_DTL(result.get("tbldtl"));
            response.setFILE_FOLDER(result.get("filefolder"));
            response.setFILE_TYPE(result.get("filetype"));
        } catch (Exception e) {
            log.error("Error while fetching system configuration for application code: {}", applCode, e);
            auditLogService.log("fetchSystemConfig", "SystemConfigResponse", " UserID: " + applCode, "processing","Error while fetching system configuration for application code" +e.getMessage());
        }
        return response;
    }

    private String buildFilePath(String folderPath, String folder,String fileName) {
        if (folderPath != null && fileName != null&& folder!=null) {
            return folderPath + File.separator + folder+ File.separator+fileName;
        }
        return null; // Return null if folder or file name is missing
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
            field = new LinkedHashMap<>();
            List<Map<String, Object>> queryResult = query.getResultList();
            for (Map<String, Object> result : queryResult) {
                UpstreamFieldResponse upstreamFieldResponse = new UpstreamFieldResponse();
                upstreamFieldResponse.setFIELD_NAME((String) result.get("fieldname"));
                upstreamFieldResponse.setFILED_TYPE((String) result.get("fieldtype"));
                upstreamFieldResponse.setFIELD_START((Integer) result.get("fieldstart"));
                upstreamFieldResponse.setFIELD_END((Integer) result.get("fieldend"));
                upstreamFieldResponse.setFIELD_LENGTH((Integer) result.get("fieldlength"));
                upstreamFieldResponse.setUP_STREAM_ORDER((Integer) result.get("upstreamorder"));
                field.put((String) result.get("fieldname"), upstreamFieldResponse);
            }
        } catch (Exception e) {
            auditLogService.log("fetchUpstreamConfig", "UpstreamResponseData", " UserID: " + systemId, "processing","Error while fetching upstream configuration for systemId"+systemId+" " +e.getMessage());
            log.error("Error while fetching upstream configuration for systemId: {} and fileType: {}", systemId, fileType, e);

        }
        upstreamResponseData.setFieldMap(field);
        upstreamResponseData.setFILE_TYPE(fileType);
        return upstreamResponseData;
    }

}
