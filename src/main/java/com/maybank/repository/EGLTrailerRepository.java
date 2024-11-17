package org.maybank.com.Repository;

import java.util.List;

import org.maybank.com.entity.EGLHeader;
import org.maybank.com.entity.EGLTrailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EGLTrailerRepository extends JpaRepository<EGLTrailer, Integer>{

	 @Query("SELECT f FROM EGLTrailer f WHERE f.SYSTEM_ID = :systemId AND f.FILE_TYPE = :fileType ORDER BY f.UP_STREAM_ORDER")
	    List<EGLTrailer> findBySystemIdAndFileType(String systemId, String fileType);

}
