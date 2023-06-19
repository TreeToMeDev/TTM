package com.ttm.crm.server.filestorage.googledrive.service;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;

@Service
public class GoogleDriveService {
	
	private final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
	private final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE);

	private final String CREDENTIALS_FILE_PATH;
	private final String APPLICATION_NAME;
	private final String ROOT_FOLDER;
	
	@Autowired
	public GoogleDriveService(/*@Value("${app.file.google-drive.credentials}") String credentials,
							  @Value("${app.file.google-drive.app-name)") String appName,
							  @Value("${app.file.google-drive.root-folder}") String rootFolder*/) {
		/*this.CREDENTIALS_FILE_PATH = credentials;
		this.APPLICATION_NAME = appName;
		this.ROOT_FOLDER = rootFolder;*/
		this.CREDENTIALS_FILE_PATH = "";
		this.APPLICATION_NAME = "";
		this.ROOT_FOLDER = "";
	}
	
	private Drive getInstance() {
		// Build a new authorized API client service.
	    try {
			final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
		    Drive service = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, new HttpCredentialsAdapter(getCredentials(HTTP_TRANSPORT)))
		           .setApplicationName(APPLICATION_NAME)
		           .build();
		    return service;
	    } catch (IOException e) {
	    	e.printStackTrace();
	    	throw new RuntimeException("Error connecting to Storage API");
	    } catch (GeneralSecurityException e) {
	    	e.printStackTrace();
	    	throw new RuntimeException("Error connecting to Storage API");
	    }
	}
	
	public String addFileToAccount(int accountId, MultipartFile file) {
		//  Find folder for account id
		String folderId = findAccountFolderId(accountId);
		
		//  Add File to folder
		String fileId = addFileToFolder(folderId, file);
		
		return fileId;
	}
	
	public void deleteFileFromAccount(String storageId) {
		Drive drive = this.getInstance();
		
		boolean found = findFileById(storageId);
		
		//  Only execute the delete if the file is found, otherwise as error is thrown.
		//  I debated about throwing the error if the file was not in storage (because
		//  the db table and the storage should be in sync) but decided against this.
		//  Let's say someone deleted the file manually from storage and we now want to
		//  delete the record from the CRM.  Unless we let this check pass, the record
		//  cannot be deleted without more work (e.g. manual deletion, adding extra
		//  checks).  It just did not seem worth it.
		if (found) { 
			try {
		      drive.files().delete(storageId).execute();
		    } catch (IOException e) {
		    	e.printStackTrace();
		    	throw new RuntimeException("Unable to delete storage file");
		    }
		}
	}
	
	public String getFile(String storageId) {
		Drive drive = this.getInstance();
		
		try {
	      File gFile = drive.files().get(storageId)
	    		  		.setFields("id, name, webContentLink, webViewLink")
	    		  		.execute();
	      
	      return gFile.getWebContentLink();
	    } catch (IOException e) {
	      throw new RuntimeException("Unable to retrieve file");
	    }	
	}
	
	private boolean findFileById(String storageId) {
		
		if (storageId.isBlank()) {
			return false;
		}
		
		Drive drive = this.getInstance();
		try {
			File file = drive.files().get(storageId).execute();
			
			if (file != null) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error getting File Storage list");
		}
	}
	
	private String findAccountFolderId(int accountId) {
		String rootFolderId = findRootFolderId();
		
		Drive drive = this.getInstance();
		try {
			FileList fileList = drive.files().list()
					.setQ("mimeType='application/vnd.google-apps.folder' and trashed=false and '" + rootFolderId +"' in parents")
					.setFields("nextPageToken, files(id, name)")
					.execute();
			
			File accountFile = fileList.getFiles().stream().filter(f -> f.getName().equals(Long.toString(accountId))).findFirst().orElse(null);
			
			if (accountFile != null) {
				return accountFile.getId();
			} else {
				String accountFolderId = createAccountFolder(rootFolderId, accountId);
				return accountFolderId;
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error getting File Storage list");
		}
	}
	
	/*  Get folder ID for the 'CRM' folder.  This is the root folder to
	 *  hold the folders for the different accounts.  Each account folder will
	 *  have the account attachments in it.
	 *  
	 *  Example Folder structure (main folder 'CRM' with attachments for two
	 *  	account IDs (51 and 67):
	 *  	CRM
	 *  	 |
	 *  	  -> 51
	 *  		-> blue.jpg
	 *  		-> test.docx
	 *  	  -> 67
	 *  		-> green.jpg
	 *  		-> bill.xlsx
	 *  		-> instructions.pdf
	 */
	private String findRootFolderId() {
		Drive drive = this.getInstance();
		try {
			FileList fileList = drive.files().list()
					.setQ("mimeType='application/vnd.google-apps.folder' and trashed=false")
					.setFields("nextPageToken, files(id, name)")
					.execute();
			
			File accountFile = fileList.getFiles().stream().filter(f -> f.getName().equals(ROOT_FOLDER)).findFirst().orElse(null);
			
			if (accountFile != null) {
				return accountFile.getId();
			} else {
				throw new RuntimeException("Error finding root storage folder.");
			}
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException("Error getting root storage folder");
		}
	}
	
	private String addFileToFolder(String folderId, MultipartFile mfile) {
		Drive drive = this.getInstance();
		
		File fileMetaData = new File();
		fileMetaData.setName(mfile.getOriginalFilename());
		fileMetaData.setParents(Collections.singletonList(folderId));
		try {
			java.io.File tempFile = java.io.File.createTempFile("fa_", ".tmp");
			try (OutputStream os = new FileOutputStream(tempFile)) {
				os.write(mfile.getBytes());
			}
			FileContent mediaContent = new FileContent(mfile.getContentType(), tempFile);
			
			File file = drive.files().create(fileMetaData, mediaContent)
					  .setFields("id, parents")
			          .execute();
			return file.getId();
			      
		} catch (IOException e) {
			throw new RuntimeException("Could Not Store File");
		}
		
	}
	
	private String createAccountFolder(String rootFolderId, int accountId) {
		Drive drive = this.getInstance();
		
		// File's metadata.
	    File fileMetaData = new File();
	    fileMetaData.setName(Integer.toString(accountId));
	    fileMetaData.setParents(Collections.singletonList(rootFolderId));
		fileMetaData.setMimeType("application/vnd.google-apps.folder");
	    try {
	      File file = drive.files().create(fileMetaData)
	          .setFields("id")
	          .execute();
	      return file.getId();
	    } catch (IOException e) {
	      e.printStackTrace();
	      throw new RuntimeException("Error creating account storage folder");
	    }
	}
	
	/**
	 * Creates an authorized Credential object.
	 *
	 * @param HTTP_TRANSPORT The network HTTP Transport.
	 * @return An authorized Credential object.
	 * @throws IOException If the credentials.json file cannot be found.
	*/
	private GoogleCredentials getCredentials(final NetHttpTransport HTTP_TRANSPORT)
			throws IOException {
	
		// Load client secrets.
	    InputStream in = GoogleDriveService.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
	    if (in == null) {
	      throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
	    }
	    
	    GoogleCredentials credentials = GoogleCredentials.fromStream(in)
	    	    .createScoped(SCOPES);
	    
	    return credentials;
	}
}
