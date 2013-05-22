package archant.appian.com;

import java.io.*;

import org.apache.log4j.Logger;
import com.appiancorp.suiteapi.common.Name;
import com.appiancorp.suiteapi.process.exceptions.SmartServiceException;
import com.appiancorp.suiteapi.process.framework.AppianSmartService;
import com.appiancorp.suiteapi.process.framework.Input;
import com.appiancorp.suiteapi.process.framework.MessageContainer;
import com.appiancorp.suiteapi.process.framework.Required;
import com.appiancorp.suiteapi.process.framework.SmartServiceContext;
import com.appiancorp.suiteapi.process.palette.PaletteInfo;
import com.appiancorp.suiteapi.type.AppianType;
import com.appiancorp.suiteapi.process.palette.PaletteInfo; 

 
@PaletteInfo(paletteCategory = "Integration Services", palette = "Connectivity Services") 

public class deleteDIR extends AppianSmartService {
      private static final Logger LOG = Logger.getLogger(deleteDIR.class);
      private final SmartServiceContext smartServiceCtx;
      private String DirectoryPath;//this the file path to be deleted in the server set given in the the input field in the Appian  
		  
	 @Override
      public void run() throws SmartServiceException {
			try {
			  removeDirectory(DirectoryPath);
			} catch (Exception e) {
				// TODO Auto-generated method stub
			}
	  }
	   public static void removeDirectory(File dir) {
		  DirectoryPath="D:/Dir/To/Remove"; //hard coded at the movement
		  if (dir.isDirectory()) {
				File[] files = dir.listFiles();
				if (files != null && files.length > 0) {
					  for (File aFile : files) {
							removeDirectory(aFile);
					  }
				}
				dir.delete();
		  } else {
				dir.delete();
		  }
		}
		
		public deleteDIR(SmartServiceContext smartServiceCtx) { super(); this.smartServiceCtx = smartServiceCtx;}
		public void onSave(MessageContainer messages) {}
		public void validate(MessageContainer messages) {}

		@Input(required = Required.OPTIONAL)
		@Name("DirectoryPath")
		public void setDirectoryPath(String val) {
			this.DirectoryPath = val;
		}

 }