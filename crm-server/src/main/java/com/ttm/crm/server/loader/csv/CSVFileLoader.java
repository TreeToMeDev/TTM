package com.ttm.crm.server.loader.csv;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.ttm.crm.server.dao.FileUploadDao;
import com.ttm.crm.server.dao.UserDao;
import com.ttm.crm.server.entity.Account;
import com.ttm.crm.server.entity.Contact;
import com.ttm.crm.server.entity.FileUpload;
import com.ttm.crm.server.entity.User;
import com.ttm.crm.server.security.RandomStringGenerator;
import com.ttm.crm.server.service.AccountService;
import com.ttm.crm.server.service.ContactService;

@Component
public class CSVFileLoader {

	@Value("${app.file.upload-dir}")
	private String uploadFileDirectory;
	
	private List<String> validObjectHeaders;	
	private FileUploadDao fileUploadDao;
	private AccountService accountService;
	private ContactService contactService;
	private UserDao userDao;
	
	@Autowired
	public CSVFileLoader(FileUploadDao fileUploadDao, AccountService accountService, ContactService contactService, UserDao userDao) {
		this.fileUploadDao = fileUploadDao;
		this.accountService = accountService;
		this.contactService = contactService;
		this.userDao = userDao;
		this.validObjectHeaders = new ArrayList<String>();
	}
	
	public CSVFileLoader(Path path) {
		this.validObjectHeaders = new ArrayList<String>();
	}
	
	public void addObjectHeaders(String header) {
		this.validObjectHeaders.add(header);
	}
	
	public CSVUploadResponse uploadFile(MultipartFile file) {
		
		CSVUploadResponse response = new CSVUploadResponse();
		List<String> fileErrors = this.checkFile(file);
		
		if (fileErrors.size() > 0) {
			response.setErrors(fileErrors);
			return response;
		}
		
		FileUpload fileUpload = this.saveFile(file);
		
		if (fileUpload.getStatus().equals(FileStatus.UPLOADED)) {
			this.setObjectHeaders();
			CSVFileDetails fileDetails = this.parseFile(fileUpload.getFileCode());
			List<String> uploadErrors = this.validateFileContents(fileDetails);
			response.setFileDetails(fileDetails);
			response.setErrors(uploadErrors);
		}
		response.setFileUpload(fileUpload);
		
		return response;
	}
	
	public CSVUploadResponse postFile(String fileCode) {
		
		CSVUploadResponse response = new CSVUploadResponse();
		
		FileUpload fileUpload = this.fileUploadDao.findByFileCode(fileCode);
		
		if (fileUpload == null) {
			response.addError("Cannot locate file for posting.  Aborting.");
			return response;
		}
		
		if (fileUpload.getStatus().equals(FileStatus.UPLOADED)) {
			this.setObjectHeaders();
			CSVFileDetails fileDetails = this.parseFile(fileUpload.getFileCode());
			List<String> uploadErrors = this.validateFileContents(fileDetails);
			int postCount = this.createObjects(fileCode, fileDetails.getRecords());
			response.setFileUpload(fileUpload);
			response.setFileDetails(fileDetails);
			response.setErrors(uploadErrors);
			response.setPostCount(postCount);
		}
		
		return response;
		
	}
	
	public void setObjectHeaders() {
		
		this.validObjectHeaders.clear();
		
		this.addObjectHeaders("First");
		this.addObjectHeaders("Last");
		this.addObjectHeaders("Job Title");
		this.addObjectHeaders("Department");
		this.addObjectHeaders("Email Address");
		this.addObjectHeaders("Phone");
		this.addObjectHeaders("Cell Phone");
		this.addObjectHeaders("Account Name");
		this.addObjectHeaders("Street Address");
		this.addObjectHeaders("Postal Code");
		this.addObjectHeaders("City");
		this.addObjectHeaders("Prov/State");
		this.addObjectHeaders("Country");
	}
	
	public List<String> checkFile(MultipartFile file) {
		List<String> errors = new ArrayList<String>();
		
		if (file == null) {
			errors.add("No file uploaded");
		}
		
		String extension = "";
		int index = file.getOriginalFilename().lastIndexOf('.');
		
		if (index > 0) {
			extension = file.getOriginalFilename().substring(index + 1);
		}
		
		if (!extension.equals("csv")) {
			errors.add("Only CSV files are accepted for upload.");
		}
		
		if (file.isEmpty()) {
			errors.add("File is empty.");
		}
		
		return errors;
	}
	
	public FileUpload saveFile(MultipartFile file) {

		FileUpload newFileUpload = new FileUpload();
		newFileUpload.setFileName(file.getOriginalFilename());
		newFileUpload.setFileContentType("CONTACT");
		
		//  Get the directory
		Path directory = Paths.get(uploadFileDirectory).toAbsolutePath().normalize();
		String fileCode = RandomStringGenerator.get(30);
		String savedFileName = fileCode + "-" + file.getOriginalFilename();
		Path absoluteFileName = directory.resolve(savedFileName);
		
		try {
			Files.copy(file.getInputStream(), absoluteFileName, StandardCopyOption.REPLACE_EXISTING);
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			String currentUserId = authentication.getName();
			
			newFileUpload.setFileName(savedFileName);
			newFileUpload.setStatus(FileStatus.UPLOADED);
			newFileUpload.setFileCode(fileCode);
			newFileUpload.setOriginalFileName(file.getOriginalFilename());
			newFileUpload.setLastUser(currentUserId);
  			this.fileUploadDao.add(newFileUpload);
  			
  			return newFileUpload;
		} catch (IOException e) {
			throw new RuntimeException("Unable to save file.  Contact Adminstrator");
		}
	}
	
	public CSVFileDetails parseFile(String fileCode) {
		
		int rowNum = 1;
		List<String> csvHeaders = new ArrayList<String>();
		List<String> matchedHeaders = new ArrayList<String>();
		List<String> unmatchedCSVHeaders = new ArrayList<String>();
		List<String> unmatchedObjectHeaders = new ArrayList<String>();
		
		List<CSVFileRow> records = new ArrayList<CSVFileRow>();
		
		FileUpload fileUpload = this.fileUploadDao.findByFileCode(fileCode);
		
		try (Reader reader = Files.newBufferedReader(Paths.get(uploadFileDirectory + "/" + fileUpload.getFileName())); 
				CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT.withFirstRecordAsHeader().withAllowMissingColumnNames())) {
			
			CSVFileDetails fileDetails = new CSVFileDetails();
				
			csvHeaders = csvParser.getHeaderNames();
			
			/*  Compare CSV Columns to defined object header columns to find matches.
			 * 	Unmatched CSV Headers go into a list for reporting.
			 */
			for (String column : csvHeaders) {
				
				//  Ignore blank headers
				if (column.isBlank())
					continue;
				
				//  The first column name has some hidden special characters which
				//  prevents matching.  This replaceAll removes those characters.
				String formattedCSVColumn = column.trim().replaceAll("\\P{Print}","");
				
				if (this.validObjectHeaders.contains(formattedCSVColumn)) {
					matchedHeaders.add(column);
					
				} else {
					unmatchedCSVHeaders.add(column);
				}
			}
			
			/*  There may be fields in the object that are not defined in the CSV.  Report
			 *  these as well.
			 */
			for (String objectColumn : this.validObjectHeaders) {	
				
				boolean found = false;
				for (int i = 0; i < matchedHeaders.size(); i++) {
					//  The first column name has some hidden special characters which
					//  prevents matching.  This replaceAll removes those characters.
					String formattedCSVColumn = matchedHeaders.get(i).trim().replaceAll("\\P{Print}","");
					
					if (formattedCSVColumn.equals(objectColumn)) {
						found = true;
						break;
					}	
				}
				
				if (!found) {
					unmatchedObjectHeaders.add(objectColumn);		
				}
			}
			
			for (CSVRecord row : csvParser.getRecords()) {
				
				rowNum = rowNum + 1;
				CSVFileRow fileRow = new CSVFileRow(rowNum, FileRowStatus.NEW);
				
				for (String matchedHeader : matchedHeaders) {
					String value = row.get(matchedHeader);
					fileRow.addColumn(matchedHeader, value);
				}
				
				records.add(fileRow);
			}
			
			fileDetails.setColumns(csvParser.getHeaderNames().size());
			
			fileDetails.setRows(records.size());
			fileDetails.setLoadableRows(records.size());
			fileDetails.setColumns(csvHeaders.size());
			fileDetails.setMatchedColumnHeaders(matchedHeaders);
			fileDetails.setUnmatchedCSVHeaders(unmatchedCSVHeaders);
			fileDetails.setUnmatchedObjectHeaders(unmatchedObjectHeaders);
			fileDetails.setRecords(records);
			
			return fileDetails;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<String> validateFile(MultipartFile file) {
		List<String> errors = new ArrayList<String>();
		
		
		return errors;
	}
	
	public List<String> validateFileContents(CSVFileDetails fileDetails) {
		
		List<String> errors = new ArrayList<String>();
		
		if (fileDetails.getMatchedColumnHeaders().size() == 0) {
			errors.add("No matched columns.");
		}
		
		if (fileDetails.getRows() == 0) {
			errors.add("No rows in the file.");
		}
		
		for (CSVFileRow row : fileDetails.getRecords()) {
			boolean isLoadable = true;
			
			CSVFileRowColumn column = row.getColumns().stream()
					.filter(c -> c.getName().trim().replaceAll("\\P{Print}","").equals("Last"))
					.findAny()
					.orElse(null);
			
			if (column == null) {
				row.addError("Missing Column 'Last'. Cannot load record.");
				isLoadable = false;
			} else {
				if (column.getValue().equals("")) {
					row.addError("Column 'Last' is blank. Cannot load record.");		
					isLoadable = false;
				}
			}
			
			column = row.getColumns().stream()
					.filter(c -> c.getName().trim().replaceAll("\\P{Print}","").equals("Account Name"))
					.findAny()
					.orElse(null);
			
			
			if (!isLoadable) {
				fileDetails.setLoadableRows(fileDetails.getLoadableRows() - 1);
			}
		}
		
		return errors;
	}
	
	public int createObjects(String fileCode, List<CSVFileRow> records) {
		
		int count = 0;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String currentUserId = authentication.getName();
		User user = this.userDao.findByAuth0Id(currentUserId);
		FileUpload file = this.fileUploadDao.findByFileCode(fileCode);
		
		for (int i = 0; i < records.size(); i++) {
			CSVFileRow row = records.get(i);
			
			if (row.getStatus() != FileRowStatus.ERROR) {
				
				Contact c = new Contact();
				
				for (int j = 0; j < row.getColumns().size(); j++) {
					CSVFileRowColumn column = row.getColumns().get(j);
							
					switch (column.getName().trim().replaceAll("\\P{Print}","")) {
						case "First":
							c.setFirstName(column.getValue());
							break;
						case "Last":
							c.setLastName(column.getValue());
							break;
						case "Job Title":
							c.setTitle(column.getValue());
							break;
						case "Department":
							c.setDepartment(column.getValue());
							break;
						case "Email Address":
							c.setEmail(column.getValue());
							break;
						case "Phone":
							c.setPhone(column.getValue());
							break;
						case "Cell Phone":
							c.setPhoneCell(column.getValue());
							break;
						case "Account Name":
							if (!column.getValue().isBlank()) {
								Account account = this.accountService.findByName(column.getValue());
								
								if (account != null) {
									c.setAccountId(account.getId());
								} else {
									Account newAccount = new Account();
									newAccount.setName(column.getValue());
									newAccount.setAgentId(user.getId());
									newAccount.setAccountType("Prospect");
									newAccount.setBillingCity("");
									
									this.accountService.add(newAccount);
									c.setAccountId(newAccount.getId());
								}
							}
							break;
						case "Street Address":
							c.setStreet(column.getValue());
							break;
						case "Postal Code":
							c.setPostalCode(column.getValue());
							break;
						case "City":
							c.setCity(column.getValue());
							break;
						case "Prov/State":
							c.setProvinceState(column.getValue());
							break;
						case "Country":
							c.setCountry(column.getValue());
							break;		
					}		
				}
				
				c.setAgentId(user.getId());
				c.setSource("FILE");
				c.setFileId(file.getId());
				
				this.contactService.add(c);
				count++;
			}
		}
		
		return count;
	}
}
