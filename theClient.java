import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class theClient extends JFrame {

    public theClient() {
        initComponents();
        setSize( 395, 435 );
        setLocation( 395, 120 );
        show();
    }

    private void initComponents() {
        scrollPane = new JScrollPane();
        jTextArea1 = new JTextArea();
        disconnectButton = new JButton();
        connectButton = new JButton();
        jList1 = new JList();
        commandChoices = new Choice();
        selectOpsLabel = new Label();
        theCommand = new JTextField();
        jLabel1 = new JLabel();
        submitButton = new JButton();
        
        commandChoices.setEnabled( false );
        submitButton.setEnabled( false );
        for ( int x = 0; x < CommandList.NUMBEROFCOMMANDS; x++ ) {
        	commandChoices.add( CommandList.options[ x ] );
        }
        
        getContentPane().setLayout(new AbsoluteLayout());
        setTitle("Mobile Phone Client");
        setName("theClient");
        setResizable( false );
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                exitForm(evt);
            }
        });
        
        disconnectButton.setFont(new Font("Dialog", 0, 12));
        disconnectButton.setText("CLOSE");
        disconnectButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                disconnectButtonMouseClicked(evt);
            }
        });
        
        getContentPane().add(disconnectButton, new AbsoluteConstraints(210, 360, -1, -1));
        
        connectButton.setFont(new Font("Dialog", 0, 12));
        connectButton.setText("CONNECT");
        connectButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                connectButtonMouseClicked(evt);
            }
        });
        
        getContentPane().add(connectButton, new AbsoluteConstraints(60, 360, 110, -1));
        
        jList1.setFixedCellHeight(10);
        getContentPane().add(jList1, new AbsoluteConstraints(190, 30, -1, -1));
        
        commandChoices.addItemListener(new ItemListener() {
            public void itemStateChanged( ItemEvent evt) {
                commandChoicesItemStateChanged(evt);
            }
        });
      
        commandChoices.setFont(new Font("Dialog", 1, 12));
        getContentPane().add(commandChoices, new AbsoluteConstraints(20, 40, 350, -1));
        
        selectOpsLabel.setText("SELECT OPERATION");
        getContentPane().add(selectOpsLabel, new AbsoluteConstraints(20, 20, -1, -1));
        
        theCommand.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                theCommandActionPerformed(evt);
            }
        });
        
        theCommand.setFont(new Font("Dialog", 1, 12));
        theCommand.setEditable( false );
        getContentPane().add(theCommand, new AbsoluteConstraints(20, 90, 270, -1));
        
        jLabel1.setFont(new Font("Dialog", 0, 12));
        jLabel1.setText("COMMAND STRING");
        getContentPane().add(jLabel1, new AbsoluteConstraints(20, 70, -1, -1));
        
        submitButton.setFont(new Font("Dialog", 0, 12));
        submitButton.setText("SUBMIT");
        submitButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                submitButtonMouseClicked(evt);
            }
        });
        
        getContentPane().add(submitButton, new AbsoluteConstraints(290, 90, 80, 20));

        jTextArea1.setEditable( true );
      	jTextArea1.setColumns(30);
        scrollPane.setViewportView(jTextArea1);
        scrollPane.requestFocus();
        
        getContentPane().add(scrollPane, new AbsoluteConstraints(20, 130, 350, 220));
        
        pack();
    }

    private void submitButtonMouseClicked(MouseEvent evt) {
    	try {
    		if ( !theCommand.equals( null ) ) {
    			out.println( theCommand.getText() );
    			out.flush();
				updateTextArea();
    		}
    		else {
    			writeln( "Command string is null." );
    		}
    	}
    	catch ( Exception e ) {
    		writeln( "ERROR: " + e );
    	}
    }

    private void theCommandActionPerformed(ActionEvent evt) {
    }

    private void disconnectButtonMouseClicked(MouseEvent evt) {
    	try {
    		System.exit( 0 );
    	}
    	catch ( Exception e ) {
    		writeln( "ERROR: " + e );
    	}
    }

    private void connectButtonMouseClicked(MouseEvent evt) {
    	try {
    		s = new Socket( NetworkSetup.HOST, NetworkSetup.PORT );
    		in = new BufferedReader( new InputStreamReader( s.getInputStream() ) );
    		out = new PrintStream( s.getOutputStream() );
    		
	        commandChoices.setEnabled( true );
    	    submitButton.setEnabled( true );
    	    
    		thread = new Thread() {
    			public void run() {
    				while( true ) {
        				try {
        					writeln( in.readLine() );
					    	updateTextArea();
        				}
        				catch( Exception e ) {
        				}
        			}
    			}};
    		thread.start();
    	}
    	catch ( Exception e ) {
    		writeln( "ERROR: " + e );
    	}
    }

    private void commandChoicesItemStateChanged(java.awt.event.ItemEvent evt) {
		String s = "" + evt.getItem();
		
		if ( s.equals( CommandList.options[ 0 ] ) ){
			new GetInputFile( this );
		}
		else if ( s.equals( CommandList.options[ 1 ] ) ){
			new SubscribeDialog( this, true ).show();
		}
		else if ( s.equals( CommandList.options[ 2 ] ) ){
			new CallDialog( this, true ).show();
		}
		else if ( s.equals( CommandList.options[ 3 ] ) ){
			new TextDialog( this, true ).show();
		}
		else if ( s.equals( CommandList.options[ 4 ] ) ){
			new LoadDialog( this, true ).show();
		}
		else if ( s.equals( CommandList.options[ 5 ] ) ){
			new InquireBalanceDialog( this, true ).show();
		}
		else if ( s.equals( CommandList.options[ 6 ] ) ){
			new InquireDueDialog( this, true ).show();
		}
		else if ( s.equals( CommandList.options[ 7 ] ) ){
			new PayDialog( this, true ).show();
		}
		else if ( s.equals( CommandList.options[ 8 ] ) ){
			new HistoryDialog( this, true ).show();
		}
		else if ( s.equals( CommandList.options[ 9 ] ) ){
			new UndoDialog( this, true ).show();
		}
		updateTextArea();
    }

    public void processFile( String inputFile )
    {
    	temp = "";
    	try
    	{
    		input = new BufferedReader( new FileReader( inputFile ) );
    		while ( temp != null )
    		{
    			try
    			{
    				temp = input.readLine();
    				if( temp != null )
    				{
    					out.println( temp );
    					out.flush();
						updateTextArea();
    				}
    			}
    			catch( Exception e )
    			{
    				writeln( "ERROR: " + e );
    			}
    		}
    	}
    	catch( Exception e )
    	{
    		writeln( "ERROR: " + e );
    	}
    }
    
    private void updateTextArea() {
		BoundedRangeModel model = scrollPane.getVerticalScrollBar().getModel();
		model.setValue(model.getMaximum());											
    	jTextArea1.repaint();
	}
    
    private void writeln( String s ) {
    	jTextArea1.append( s + "\n" );
    	updateTextArea();
    }
    
    private void exitForm(WindowEvent evt) {
        System.exit(0);
    }

    public static void main(String args[]) {
        new theClient();
    }

	private Thread thread;
	private static final String newline = "\n";
	private Socket s;
	private BufferedReader in, input;
	private PrintStream out;
    private String temp;
	private JScrollPane scrollPane = new JScrollPane();
    private JTextArea jTextArea1;
    private JButton disconnectButton;
    private JButton connectButton;
    private JList jList1;
    private Choice commandChoices;
    private Label selectOpsLabel;
    private JLabel jLabel1;
    private JButton submitButton;
    public JTextField theCommand;
}
