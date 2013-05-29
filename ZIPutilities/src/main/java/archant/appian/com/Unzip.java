package archant.appian.com;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

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
	
	/**
	 * This utility extracts files and directories of a standard zip file to
	 * a destination directory.
	 * @author www.codejava.net
	 *
	 */
	    /**
	     * Size of the buffer to read/write data
	     */
	    private static final int BUFFER_SIZE = 4096;
	    /**
	     * Extracts a zip file specified by the zipFilePath to a directory specified by
	     * destDirectory (will be created if does not exists)
	     * @param fileToUnzip
	     * @param targetOutputPath
	     * @throws IOException
	     */
	    public void unzip(String fileToUnzip, String targetOutputPath) throws IOException {
	        File destDir = new File(targetOutputPath);
	        if (!destDir.exists()) {
	            destDir.mkdir();
	        }
	        ZipInputStream zipIn = new ZipInputStream(new FileInputStream(fileToUnzip));
	        ZipEntry entry = zipIn.getNextEntry();
	        // iterates over entries in the zip file
	        while (entry != null) {
	        	String filePath = targetOutputPath + "\\" + entry.getName();
	            if (!entry.getName().contains("/")) {
	                // if the entry is a file, extracts it
	                extractFile(zipIn, filePath);
	            } else {
	                // if the entry is a directory, make the directory	            	
	                File dir = new File(filePath.substring(0,filePath.lastIndexOf("/")));
	                extractFile(zipIn, filePath);
	            }
	            
	            zipIn.closeEntry();
	            entry = zipIn.getNextEntry();
	        }
	        zipIn.close();
	    }
	    /**
	     * Extracts a zip entry (file entry)
	     * @param zipIn
	     * @param filePath
	     * @throws IOException
	     */
	    private void extractFile(ZipInputStream zipIn, String filePath) throws IOException {
	    	OutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
	        byte[] bytesIn = new byte[BUFFER_SIZE];
	        int read = 0;
	        while ((read = zipIn.read(bytesIn)) != -1) {
	            bos.write(bytesIn, 0, read);
	        }
	        bos.close();
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
