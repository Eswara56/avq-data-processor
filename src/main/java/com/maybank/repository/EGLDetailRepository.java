package org.maybank.com.Repository;

import java.util.List;

import org.maybank.com.entity.EGLDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EGLDetailRepository extends JpaRepository<EGLDetail, Integer> {

	 @Query("SELECT f FROM EGLDetail f WHERE f.SYSTEM_ID = :systemId AND f.FILE_TYPE = :fileType ORDER BY f.UP_STREAM_ORDER")
	    List<EGLDetail> findBySystemIdAndFileTypeOrderByFieldOrder(String systemId, String fileType);
	 
	}

