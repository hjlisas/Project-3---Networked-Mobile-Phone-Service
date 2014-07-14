import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class UndoDialog extends Dialog 
{

    public UndoDialog( theClient parent, boolean modal) 
    {
        super(parent, modal);
        c = parent;
        initComponents();		
        this.setSize( 535, 270 );
        this.setLocation( 250, 170 );
        this.setResizable( false );
    }

    private void initComponents() 
    {
        label1 = new Label();
        label2 = new Label();
        label3 = new Label();
        label4 = new Label();
        commandString = new TextField();
        okButton = new Button();
        postpaidSubscribers = new Choice();
        calls = new Choice();
        calls.setEnabled( false );

        numbers = subscribers.getAllSubscribers().iterator();
        while( numbers.hasNext() )
        {
        	postpaidSubscribers.add( (String) numbers.next() );
        }
        
        setLayout(new AbsoluteLayout());
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                closeDialog(evt);
            }
        });
        
        label1.setAlignment(Label.CENTER);
        label1.setFont(new Font("Dialog", 1, 14));
        label1.setText("CHOOSE CALL TO REFUTE");
        add(label1, new AbsoluteConstraints(180, 30, -1, -1));
        
        label2.setText("POSTPAID SUBSCRIBER");
        add(label2, new AbsoluteConstraints(40, 80, -1, -1));
        
        label3.setText("CALL TRANSACTIONS");
        add(label3, new AbsoluteConstraints(40, 120, -1, -1));
        
        label4.setText("COMMAND STRING");
        add(label4, new AbsoluteConstraints(40, 180, 160, -1));
        
        commandString.setFont(new Font("Dialog", 1, 14));
        add(commandString, new AbsoluteConstraints(40, 210, 450, -1));
        
        okButton.setLabel("Ok");
        okButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                okButtonMouseClicked(evt);
            }
        });
        
        add(okButton, new AbsoluteConstraints(240, 240, 100, -1));
        
        postpaidSubscribers.setFont(new Font("Dialog", 0, 14));
        postpaidSubscribers.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                postpaidSubscribersItemStateChanged(evt);
            }
        });
        add(postpaidSubscribers, new AbsoluteConstraints(190, 80, 250, -1));

		calls.setFont(new Font("Dialog", 0, 14));
        calls.add( "call list is empty..." );
        add(calls, new AbsoluteConstraints(190, 120, 300, -1));
        
        pack();
    }

    private void callsItemStateChanged(ItemEvent evt) 
    {
    }

    private void postpaidSubscribersItemStateChanged(ItemEvent evt) 
    {
    	commandString.setText( "undo " + extractNumber( ( String ) evt.getItem() ) );
    	calls.removeAll();
    	Vector v = thisCall.getAll( extractNumber( ( String ) evt.getItem() ) );
    	
    	if ( v.isEmpty() )
    	{
			calls.add( "No calls for this subscriber." );
			commandString.setText( "" );
    	}
    	else
    	{
	        listOfCalls = v.iterator();
			while( listOfCalls.hasNext() )
    	    {
	        	String s = (String)listOfCalls.next();
    	    	calls.add( s );
        		StringTokenizer t = new StringTokenizer( s, " " );
        		thisNum = t.nextToken();
        		callRef = t.nextToken();
    	    }
    	}

        calls.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                callsItemStateChanged(evt);
                commandString.setText( commandString.getText() + " " + callRef );
            }
        });

        calls.setEnabled( true );
    }

    private void okButtonMouseClicked(MouseEvent evt) 
    {
		c.theCommand.setText( commandString.getText() );
        setVisible(false);
        dispose();
    }

    private void closeDialog(WindowEvent evt) {
        setVisible(false);
        dispose();
    }

    private String extractNumber( String str ) {
    	StringTokenizer z = new StringTokenizer( str, " " );
    	String s = z.nextToken();
    	return s;
    }

    public static void main(String args[]) {
        new UndoDialog(new  theClient(), true).show();
    }

    theClient c;
    PostpaidHandler subscribers = new PostpaidHandler();
    CallHandler thisCall = new CallHandler();
    String thisNum, callRef;
	Iterator numbers;
	Iterator listOfCalls;   
    private Label label1;
    private Label label2;
    private Label label3;
    private Label label4;
    private TextField commandString;
    private Button okButton;
    private Choice postpaidSubscribers;
    private Choice calls;
}
