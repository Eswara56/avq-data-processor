package org.maybank.com.Repository;

import org.maybank.com.entity.EGLUPStream;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface  EGLUPStreamRepository extends JpaRepository<EGLUPStream, Integer> {
	 @Query("SELECT f FROM EGLUPStream f WHERE f.FILE_TYPE = :fileType AND f.SYSTEM_ID = :systemId AND f.FIELD_NAME = :fieldName")
	    EGLUPStream findBySystemIdAndFieldNameAndFileType(@Param("systemId") String systemId, 
	                                                      @Param("fieldName") String fieldName,
	                                                      @Param("fileType") String fileType);
}
