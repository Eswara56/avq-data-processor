package com.maybank.repository;

import java.util.List;

import com.maybank.entity.EGLHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EGLHeaderRepository extends JpaRepository<EGLHeader, Integer>{

	 @Query("SELECT f FROM EGLHeader f WHERE f.SYSTEM_ID = :systemId AND f.FILE_TYPE = :fileType ORDER BY f.UP_STREAM_ORDER")
	    List<EGLHeader> findBySystemIdAndFileType(String systemId, String fileType);

}
