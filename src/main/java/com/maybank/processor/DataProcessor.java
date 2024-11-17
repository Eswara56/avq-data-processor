package com.maybank.processor;

import com.maybank.repository.EGLDetailRepository;
import com.maybank.repository.SystemConfigRepository;
import com.maybank.service.DetailFileService;
import com.maybank.service.HeaderFileService;
import com.maybank.service.TrailerFileService;
import com.maybank.entity.SystemConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DataProcessor {
	@Autowired
	private SystemConfigRepository systemConfigRepository;
	@Autowired
	private HeaderFileService headerFileService;
	@Autowired
	private DetailFileService detailFileService;
	@Autowired
	private TrailerFileService trailerFileService;
	@Autowired
	private	EGLDetailRepository eglDetailRepository;  
	

    public void processFiles( String applCode) {
        // Assuming we are retrieving for APPL_CODE = "PW"
//        String applCode = "PW"; 
        // Retrieve the system configuration for the specified APPL_CODE
        SystemConfig systemConfig = systemConfigRepository.findByApplCode(applCode);
        if (systemConfig == null) {
            throw new RuntimeException("System configuration for '" + applCode + "' not found in database");
        }  
        // Retrieve paths for each file type
        String baseDirectory = "D:/Work/AvqFiles/"; // Base directory where files are located
        String headerFilePath = baseDirectory + systemConfig.getHeaderFile();
        String detailFilePath = baseDirectory + systemConfig.getDetailFile();
        String trailerFilePath = baseDirectory + systemConfig.getFooterFile();
        System.out.println("Header File Path: " + headerFilePath);
        System.out.println("Detail File Path: " + detailFilePath);
        System.out.println("Footer File Path: " + trailerFilePath);
        String   detailTable=systemConfig.getTBL_DTL();
        String  headerTable=systemConfig.getTBL_HDR();
        String   TrailerTable=systemConfig.getTBL_TRLR();
        System.out.println();
        //procees each file and table respected 
        detailFileService. processFilesWithTable(detailTable,detailFilePath);
    }
	
    
    
    
//	public void processFilesWithTable(String detailTable, String detailFilePath) {
//		// TODO Auto-generated method stub
//		 try (BufferedReader headerReader = new BufferedReader(new FileReader(detailFilePath))) {
//	           
//	        } catch (FileNotFoundException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		
//		
//	}



//
//	public void processFile(String headerFilePath1, String detailFilePath1, String trailerFilePath1) {
//	    try {
////	         Process Header File
//	        try (BufferedReader headerReader = new BufferedReader(new FileReader(headerFilePath1))) {
//	            headerFileService.processAndMapHeaders(headerReader);
//	        }
//
//	        
//	        
//	        
//	        // Process Detail File
//
////	    	try (BufferedReader detailReader = new BufferedReader(new FileReader(detailFilePath1))) {
////	          detailFileService.processAndMapDetails(detailReader);
////	            
////	        }
////
////	        // Process Footer File
////	        try (BufferedReader footerReader = new BufferedReader(new FileReader(trailerFilePath1))) {
////	           trailerFileService.processAndMapTrailers(footerReader);
////	           
////	        }
//
//	    } catch (IOException e) {
//	        System.err.println("Error processing files: " + e.getMessage());
//	    }
//	    
//	}
//	    
//	
	
		
	}

	

