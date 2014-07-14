import javax.swing.*;
import java.awt.event.*;
import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class theServer extends JFrame {

    public theServer() {
        initComponents();
        setSize( 375, 355 );
        setLocation( 10,120 );
        show();
        
        try{
        	ss = new ServerSocket( NetworkSetup.PORT );
    	    writeln( "Waiting for connections..." );
        	socket = ss.accept();
        	writeln( "Connection with " + socket.getInetAddress() + " established.\n" );
        	in = new BufferedReader( new InputStreamReader( socket.getInputStream() ) );
        	out = new PrintStream( socket.getOutputStream() );
        	
        	Thread thread = new Thread() {
        		public void run() {
        			while ( true){
        				try {
      						processCommand( in.readLine() ).process();
      						updateTextArea();
   						}
        				catch ( Exception e ) {
        				}
        			}
        		}};
        	thread.start();
        }
        catch ( Exception e ){
        	writeln( "ERROR: " + e );
        }
    }

    private void initComponents() {
        closeButton = new JButton();
       	jScrollPane1 = new JScrollPane();
        outputArea = new JTextArea();
        
        getContentPane().setLayout(new AbsoluteLayout());
        
        //setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setTitle("Mobile Phone Server");
        setName("theServer");
        //setResizable(false);
        outputArea.requestFocus();
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                exitForm(evt);
            }
        });
        
        closeButton.setFont(new Font("Dialog", 0, 12));
        closeButton.setText("CLOSE SERVER");
        closeButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                closeButtonMouseClicked(evt);
            }
        });
        
        getContentPane().add(closeButton, new AbsoluteConstraints(115, 265, -1, 30));
        
   		outputArea.setColumns(50);
        outputArea.setEditable(false);
        jScrollPane1.setViewportView(outputArea);
        
        getContentPane().add(jScrollPane1, new AbsoluteConstraints(20, 30, 330, 210));
                
        
        pack();
    }

    private void closeButtonMouseClicked(MouseEvent evt) {
    	try {
	    	ss.close();
    		dispose();
        	System.exit(0);
    	}
    	catch ( Exception e ) {
    	}
    }

    private void exitForm(WindowEvent evt) {
        System.exit(0);
    }
    
    public void broadcast( String s ) {
    	writeln( s );
    	toClient( s );
		updateTextArea();
    }
    
    public void updateTextArea() {
		BoundedRangeModel model = jScrollPane1.getVerticalScrollBar().getModel();
		model.setValue(model.getMaximum());											
    	outputArea.repaint();
    }
    
    public void writeln( String s ){
    	outputArea.append( s + newline );
    	updateTextArea();
    }
    
	public void toClient( String s ) {
   		out.println( s );
    	updateTextArea();
   		out.flush();
   	}

   public Command processCommand( String inputString )
   {
   	  StringTokenizer tokens;
   	  String temp, command, parameters; 
   	  Command c = new Command();
   	  
   	  command = "";
   	  parameters = "";

   	  temp = inputString.toLowerCase();
   	  tokens  = new StringTokenizer( temp, " " ); 
   	  command = tokens.nextToken();
   	  
   	  while( tokens.hasMoreTokens() )
   	  {
   	  	 parameters += tokens.nextToken() + " ";
   	  }

          // **************************************************
          // THIS IS WHERE CHANGES HAVE BEEN MADE TO ACCOMODATE
          // THE FORMAT OF THE TEST CASE INPUT FILE
          // **************************************************
	  if ( command.equals( "//" ) )
	  {
                broadcast( parameters );
	  }
          // **************************************************
          // **************************************************
	  else if ( command.equals( "due" ) )
	  {
	  	 c = new Due( parameters, this  );
	  	 broadcast( blank );
	  }
	  else if ( command.equals( "balance" ) )
	  {
	  	 c = new Balance( parameters, this  );
	  	 broadcast( blank );
	  }
	  else if ( command.equals( "subscribe" ) )
	  {
	  	 c = new Subscribe( parameters, this  );
	  	 broadcast( blank );
	  }
	  else if ( command.equals( "load" ) )
	  {
	  	 c = new Load( parameters, this  );
	  	 broadcast( blank );
	  }
	  else if ( command.equals( "call" ) )
	  {	
	  	 c = new Call( parameters, this  );
	  	 broadcast( blank );
	  }
	  else if ( command.equals( "history" ) )
	  {
	  	 c = new History( parameters, this  );
	  	 broadcast( blank );
  	  }
	  else if ( command.equals( "text" ) )
	  {
	  	 c = new Text( parameters, this  );
	  	 broadcast( blank );
	  }
	  else if ( command.equals( "pay" ) )
	  {
	  	 c = new Pay( parameters, this  );
	  	 broadcast( blank );
	  }
	  else if ( command.equals( "undo" ) )
	  {
	  	 c = new Undo( parameters, this  );
	  	 broadcast( blank );
	  }
	  else 
	  {
	  	 broadcast( "COMMAND: " + temp );
	  	 broadcast( "Unrecognized command or invalid input!" );
	  	 broadcast( blank );
	  }
	  parameters = "";
	  command = "";
   	  updateTextArea();
	  return c;
   }

    public static void main(String args[]) {
        new theServer();
    }
	
	private boolean go = true;
	private static final String newline = "\n";
	private static final String blank = "";
    public Socket socket;
    public ServerSocket ss;
    public JButton closeButton;
    public JScrollPane jScrollPane1;    
    public JTextArea outputArea;
    public PrintStream out;
    BufferedReader in;
}

