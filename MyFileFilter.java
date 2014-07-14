import java.io.File;
import javax.swing.*;
import javax.swing.filechooser.*;

public class MyFileFilter extends FileFilter {
	
	public boolean accept( File f ) {
		if ( f.isDirectory() ) {
			return true;
		}
		
		String ext = InputFiles.getExtension( f );
		
		if ( ext != null ) {
			if ( ext.equals( InputFiles.txt) ) return true;
		}
		else {
			return false;
		}
		return false;
	}
	
	public String getDescription() {
		return "Input file";
	}
}
		