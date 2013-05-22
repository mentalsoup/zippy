package archant.appian.com;

import java.io.File;

import org.apache.log4j.Logger;

import com.appiancorp.suiteapi.common.Name;
import com.appiancorp.suiteapi.process.exceptions.SmartServiceException;
import com.appiancorp.suiteapi.process.framework.AppianSmartService;
import com.appiancorp.suiteapi.process.framework.Input;
import com.appiancorp.suiteapi.process.framework.MessageContainer;
import com.appiancorp.suiteapi.process.framework.Required;
import com.appiancorp.suiteapi.process.framework.SmartServiceContext;

import com.appiancorp.suiteapi.process.palette.PaletteInfo; 

@PaletteInfo(paletteCategory = "Integration Services", palette = "FileSystem Services") 
public class MoveFile extends AppianSmartService {

	private static final Logger LOG = Logger.getLogger(MoveFile.class);
	private final SmartServiceContext smartServiceCtx;
	private String filePath;
	private String destination;

	@Override
	public void run() throws SmartServiceException {
		try {
			Path(filePath, destination);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	private void Path(String filePath2, String destination2) {
		// TODO Auto-generated method stub
	}
	
	public void moveFileToDirectory(File filePath2, File destination2, boolean createDestDir) {
		
	}
	
	public MoveFile(SmartServiceContext smartServiceCtx) {
		super();
		this.smartServiceCtx = smartServiceCtx;
	}

	public void onSave(MessageContainer messages) {
	}

	public void validate(MessageContainer messages) {
	}

	@Input(required = Required.OPTIONAL)
	@Name("FilePath")
	public void setFilePath(String val) {
		this.filePath = val;
	}

	@Input(required = Required.OPTIONAL)
	@Name("Destination")
	public void setDestination(String val) {
		this.destination = val;
	}

}
