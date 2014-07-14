import java.io.File;

public class InputFiles {
	
	public final static String txt = "txt";
	
	public static String getExtension( File f ) {
		String ext = null;
		String filename = f.getName();
		int i = filename.lastIndexOf( '.' );
		
		if ( i > 0 && i < filename.length() - 1 ) {
			ext = filename.substring( i + 1 ).toLowerCase();
		}
		return ext;
	}
}