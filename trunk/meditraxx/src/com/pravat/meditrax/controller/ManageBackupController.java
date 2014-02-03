package com.pravat.meditrax.controller;



import java.io.File;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Dialogs;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import net.lingala.zip4j.core.ZipFile;
import net.lingala.zip4j.model.ZipParameters;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pravat.meditrax.util.AppContextPropertyLookUp;
import com.pravat.meditrax.util.ApplicationContextUtil;
import com.pravat.meditrax.util.UxUtils;
import com.pravat.meditrax.validator.Validator;

public class ManageBackupController implements Initializable{
	Log log = LogFactory.getLog(getClass());
	@FXML
	TextField pathTf;
	
	@FXML
	Text restoreLabel;
	@FXML
	public void onBrowseAction(ActionEvent e) {

		Stage owner = UxUtils.getStage(e);

		DirectoryChooser  dirChooser = new DirectoryChooser ();
		dirChooser.setTitle("Choose folder");
		dirChooser.setInitialDirectory(new File(System.getProperty("user.home")));
		File showDialog = dirChooser.showDialog(owner);
		if(null != showDialog) { // it may return null if nothing is selected by closing the Filechooser window
			String absolutePath = showDialog.getAbsolutePath();
			pathTf.setText(absolutePath);
		}
	}
	AppContextPropertyLookUp instance = ApplicationContextUtil.getInstance(AppContextPropertyLookUp.class);
	@FXML
	public void onOkAction(ActionEvent e) {
		Stage owner = UxUtils.getStage(e);
		if(!Validator.isBlank(pathTf.getText())) {
			String text = pathTf.getText();
			// check that path is directory. should never come here ideally as the file chooser 
			// is a directory chooser 
			Path toPath = FileSystems.getDefault().getPath(text);
			boolean isDir = toPath.toFile().isDirectory();
			if(!isDir) {
				Dialogs.showErrorDialog(owner, "Path should be a directory!", "Error");
			} else {

				// get the database path and copy to the selected directory
				String dbPath = instance.getDbPath();
				Path srcPath = FileSystems.getDefault().getPath(dbPath);
				try {
					log.debug("Creating backup from :'" + dbPath + "' to '" + toPath + "'");
					SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy-hh-mm-ss");
					String format = simpleDateFormat.format(new Date());
					String newDirPath = text + "/meditrax-" + format + ".zip";
					ZipFile file = new ZipFile(newDirPath);
					file.addFolder(srcPath.toFile(), new ZipParameters());
					
					/*File destDir = new File(newDirPath);
					FileUtils.copyDirectory(srcPath.toFile(), destDir);*/
					Dialogs.showInformationDialog(owner, "Back up created successfully", "Success");
					owner.close();
					
				} catch (Exception e1) {
					log.error("Database copy error", e1);
					Dialogs.showErrorDialog(owner, "An error occurred while creating backup", "Error");
				}
			}
		} else {
			Dialogs.showErrorDialog(owner, "Please select a directory for creating backup", "Error");
		}
	}
	@FXML
	public void onCancelAction(ActionEvent e) {
		Stage owner = UxUtils.getStage(e);
		owner.close();
	}
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		restoreLabel.setText("For restoring the backup, please unzip the previously "
				+ "\ncreated backup to the following location: \n\"" + instance.getDbPath() + "\"");
		
	}



}
