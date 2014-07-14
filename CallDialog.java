import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class CallDialog extends Dialog {

    theClient c;
    SubscriberHandler subscribers = new SubscriberHandler();
    
    public CallDialog( theClient parent, boolean modal) {
        super(parent, modal);
        c = parent;
        initComponents();
        this.setSize( 535, 380 );
        this.setLocation( 250, 170 );
        this.setResizable( false );
    }

    private void initComponents() 
    {
        label1 = new Label();
        label2 = new Label();
        label3 = new Label();
        label4 = new Label();
        label6 = new Label();
        num2Call = new TextField();
        okButton = new Button();
        label5 = new Label();
        callLength = new TextField();
        choice1 = new Choice();
        commandString = new TextField();
        callRef = new java.awt.TextField();

        num2Call.setEnabled( false );
        callLength.setEnabled( false );
        callRef.setEnabled( false );

		numbers = subscribers.getAll().iterator();
		while ( numbers.hasNext() )
		{	
			choice1.add( (String)numbers.next() );
		}

        
        setLayout(new AbsoluteLayout());
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                closeDialog(evt);
            }
        });
        
        label1.setFont(new java.awt.Font("Dialog", 1, 14));
        label1.setText("INPUT CALL INFORMATION");
        add(label1, new AbsoluteConstraints(180, 40, -1, -1));
        
        label2.setText("SIM CARD NUMBER");
        add(label2, new AbsoluteConstraints(50, 90, -1, -1));
        
        label3.setText("NUMBER TO CALL");
        add(label3, new AbsoluteConstraints(50, 140, -1, -1));
        
        label4.setText("COMMAND STRING");
        add(label4, new AbsoluteConstraints(40, 260, -1, -1));
        
        num2Call.setFont(new Font("Dialog", 0, 14));
        num2Call.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                num2CallActionPerformed(evt);
            }
        });
        
        add(num2Call, new AbsoluteConstraints(190, 140, 130, -1));
        
        okButton.setLabel("Ok");
        okButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                okButtonMouseClicked(evt);
            }
        });
        
        add(okButton, new AbsoluteConstraints(200, 330, 110, -1));
        
        label5.setText("LENGTH OF CALL");
        add(label5, new AbsoluteConstraints(50, 180, -1, -1));
        
        callLength.setFont(new Font("Dialog", 0, 14));
        callLength.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                callLengthActionPerformed(evt);
            }
        });
        
        add(callLength, new AbsoluteConstraints(190, 180, 50, -1));
        
        choice1.setFont(new Font("Dialog", 0, 14));
        choice1.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                choice1ItemStateChanged(evt);
            }
        });
        
        add(choice1, new AbsoluteConstraints(190, 90, 250, -1));

        label6.setText("CALL REFERENCE");
        add(label6, new AbsoluteConstraints(50, 220, -1, -1));
        
        callRef.setFont(new Font("Dialog", 0, 14));
        callRef.addActionListener(new ActionListener() {
            public void actionPerformed( ActionEvent evt) {
                callRefActionPerformed(evt);
            }
        });
        
        add(callRef, new AbsoluteConstraints(190, 220, 50, -1));
        
        commandString.setFont(new java.awt.Font("Dialog", 1, 14));
        add(commandString, new AbsoluteConstraints(40, 290, 450, -1));

        pack();
    }

    private void num2CallActionPerformed(ActionEvent evt) 
    {
		commandString.setText( commandString.getText() + " " + num2Call.getText() );
        callLength.setEnabled( true );
        callLength.requestFocus();
    }

    private void choice1ItemStateChanged(ItemEvent evt) 
    {
  		try
        {
        	commandString.setText( "call " + extractNumber( ( String ) evt.getItem() ) );
       	}
        catch( Exception e ){}
		num2Call.setEnabled( true );
		num2Call.requestFocus();
    }

    private void okButtonMouseClicked(MouseEvent evt) 
    {
    	c.theCommand.setText( commandString.getText() );
    	setVisible( false );
    	dispose();
    }

    private void callLengthActionPerformed(ActionEvent evt) 
    {
		commandString.setText( commandString.getText() + " " + callLength.getText() );
		callRef.setEnabled( true );
		callRef.requestFocus();
    }

    private void closeDialog(WindowEvent evt) 
    {
        setVisible(false);
        dispose();
    }

    private void callRefActionPerformed(ActionEvent evt) 
    {
		commandString.setText( commandString.getText() + " " + callRef.getText() );
		okButton.requestFocus();
    }

    private String extractNumber( String str ) {
    	StringTokenizer z = new StringTokenizer( str, " " );
    	String s = z.nextToken();
    	return s;
    }

    public static void main(String args[]) {
        new CallDialog(new theClient(), true).show();
    }

    Iterator numbers;
    private Label label1;
    private Label label2;
    private Label label3;
    private Label label4;
    private TextField num2Call;
    private Button okButton;
    private Label label5;
    private TextField callLength;
    private Choice choice1;
    private TextField commandString;
    private Label label6;
    private TextField callRef;
}
