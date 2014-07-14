import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;

public class GetInputFile extends JFrame {
    theClient client;

    public GetInputFile( theClient parent ) {
        super("Get Input File");
        client = parent;

		JFileChooser fc = new JFileChooser();
        fc.addChoosableFileFilter(new MyFileFilter());

        int returnVal = fc.showOpenDialog(this);

        if (returnVal == JFileChooser.APPROVE_OPTION) {
        	File file = fc.getSelectedFile();
        	client.processFile( file.getName() );
        	setVisible( false );
        	dispose();
		}
		else {
        	setVisible( false );
        	dispose();
        }
	}

    public static void main(String[] args) {
        JFrame frame = new GetInputFile( new theClient());
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        frame.pack();
        frame.setVisible(true);
    }
}
