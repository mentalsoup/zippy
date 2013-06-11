package archant.appian.com;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.log4j.Logger;

import com.appiancorp.suiteapi.common.Name;
import com.appiancorp.suiteapi.knowledge.Document;
import com.appiancorp.suiteapi.process.exceptions.SmartServiceException;
import com.appiancorp.suiteapi.process.framework.AppianSmartService;
import com.appiancorp.suiteapi.process.framework.Input;
import com.appiancorp.suiteapi.process.framework.MessageContainer;
import com.appiancorp.suiteapi.process.framework.Required;
import com.appiancorp.suiteapi.process.framework.SmartServiceContext;

import com.appiancorp.suiteapi.process.palette.PaletteInfo; 

@PaletteInfo(paletteCategory = "Integration Services", palette = "FileSystem Services") 
public class Unzip extends AppianSmartService {

	private final static int BUFFER_SIZE = 2048;
	private final static String ZIP_EXTENSION = ".zip";
	private static final Logger LOG = Logger.getLogger(Unzip.class);
	private final SmartServiceContext smartServiceCtx;
	private String fileToUnzip;
	private String targetOutputPath;

	@Override
	public void run() throws SmartServiceException {
		try {
			unzip(fileToUnzip, targetOutputPath);
		} catch (Exception e) {
		// TODO Auto-generated method stub
		e.printStackTrace();}
	}
	 
	 public static boolean unzip(String fileToUnzip2, String targetOutputPath2) {
	  try {
	   BufferedInputStream bufIS = null;
	   // create the destination directory structure (if needed)
	   File destDirectory = new File(targetOutputPath2);
	   destDirectory.mkdirs();
	 
	   // open archive for reading
	   File file = new File(fileToUnzip2);
	   ZipFile zipFile = new ZipFile(file, ZipFile.OPEN_READ);
	 
	   //for every zip archive entry do
	   Enumeration<? extends ZipEntry> zipFileEntries = zipFile.entries();
	   while (zipFileEntries.hasMoreElements()) {
	    ZipEntry entry = (ZipEntry) zipFileEntries.nextElement();
	    System.out.println("\tExtracting entry: " + entry);
	 
	    //create destination file
	    File destFile = new File(destDirectory, entry.getName());
	 
	    //create parent directories if needed
	    File parentDestFile = destFile.getParentFile();    
	    parentDestFile.mkdirs();    
	     
	    if (!entry.isDirectory()) {
	     bufIS = new BufferedInputStream(
	       zipFile.getInputStream(entry));
	     int currentByte;
	 
	     // buffer for writing file
	     byte data[] = new byte[BUFFER_SIZE];
	 
	     // write the current file to disk
	     FileOutputStream fOS = new FileOutputStream(destFile);
	     BufferedOutputStream bufOS = new BufferedOutputStream(fOS, BUFFER_SIZE);
	 
	     while ((currentByte = bufIS.read(data, 0, BUFFER_SIZE)) != -1) {
	      bufOS.write(data, 0, currentByte);
	     }
	 
	     // close BufferedOutputStream
	     bufOS.flush();
	     bufOS.close();
	 
	     // recursively unzip files
	     if (entry.getName().toLowerCase().endsWith(ZIP_EXTENSION)) {
	      String zipFilePath = destDirectory.getPath() + File.separatorChar + entry.getName();
	 
	      unzip(zipFilePath, zipFilePath.substring(0, 
	              zipFilePath.length() - ZIP_EXTENSION.length()));
	     }
	    }
	   }
	   bufIS.close();
	   return true;
	  } catch (Exception e) {
	   e.printStackTrace();
	   return false;
	  }
	 } 
	
	
	public Unzip(SmartServiceContext smartServiceCtx) {
		super();
		this.smartServiceCtx = smartServiceCtx;
	}

	public void onSave(MessageContainer messages) {
	}

	public void validate(MessageContainer messages) {
	}

	@Input(required = Required.OPTIONAL)
	@Name("FileToUnzip")
	public void setFileToUnzip(String val) {
		this.fileToUnzip = val;
	}

	@Input(required = Required.OPTIONAL)
	@Name("TargetOutputPath")
	public void setTargetOutputPath(String val) {
		this.targetOutputPath = val;
	}

}
