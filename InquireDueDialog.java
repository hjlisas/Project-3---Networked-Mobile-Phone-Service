import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class InquireDueDialog extends Dialog {

    public InquireDueDialog( theClient parent, boolean modal) {
        super(parent, modal);
        c = parent;
        initComponents();		
        this.setSize( 535, 270 );
        this.setLocation( 250, 170 );
        this.setResizable( false );
    }

    private void initComponents() {
        label1 = new java.awt.Label();
        label2 = new java.awt.Label();
        label3 = new java.awt.Label();
        commandString = new java.awt.TextField();
        okButton = new java.awt.Button();
        postpaidSubscribers = new java.awt.Choice();

		postpaidSubscribers.add( "allpostpaid" );
        numbers = subscribers.getAllSubscribers().iterator();
        while( numbers.hasNext() )
        {
        	postpaidSubscribers.add( (String) numbers.next() );
        }
        
        
        setLayout(new AbsoluteLayout());
        
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                closeDialog(evt);
            }
        });
        
        label1.setAlignment(java.awt.Label.CENTER);
        label1.setFont(new java.awt.Font("Dialog", 1, 14));
        label1.setText("CHOOSE POSTPAID SUBSCRIBER");
        add(label1, new AbsoluteConstraints(160, 40, -1, -1));
        
        label2.setText("POSTPAID SUBSCRIBER");
        add(label2, new AbsoluteConstraints(100, 80, -1, -1));
        
        label3.setText("COMMAND STRING");
        add(label3, new AbsoluteConstraints(60, 150, -1, -1));
        
        commandString.setFont(new java.awt.Font("Dialog", 1, 14));
        add(commandString, new AbsoluteConstraints(60, 180, 420, -1));
        
        okButton.setLabel("Ok");
        okButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                okButtonMouseClicked(evt);
            }
        });
        
        add(okButton, new AbsoluteConstraints(230, 220, 90, -1));
        
        postpaidSubscribers.setFont(new java.awt.Font("Dialog", 0, 14));
        postpaidSubscribers.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                postpaidSubscribersItemStateChanged(evt);
            }
        });
        
        add(postpaidSubscribers, new AbsoluteConstraints(260, 80, 250, -1));
        
        pack();
    }

    private void postpaidSubscribersItemStateChanged(java.awt.event.ItemEvent evt) 
    {
    	commandString.setText( "due " + extractNumber( ( String ) evt.getItem() ) );
    }

    private void okButtonMouseClicked(java.awt.event.MouseEvent evt) 
    {
		c.theCommand.setText( commandString.getText() );
        setVisible(false);
        dispose();
    }

    private void closeDialog(java.awt.event.WindowEvent evt) {
        setVisible(false);
        dispose();
    }

    private String extractNumber( String str ) {
    	StringTokenizer z = new StringTokenizer( str, " " );
    	String s = z.nextToken();
    	return s;
    }

    public static void main(String args[]) 
    {
        new InquireDueDialog( new theClient(), true ).show();
    }

    theClient c;
    PostpaidHandler subscribers = new PostpaidHandler();
	Iterator numbers;
    private java.awt.Label label1;
    private java.awt.Label label2;
    private java.awt.Label label3;
    private java.awt.TextField commandString;
    private java.awt.Button okButton;
    private java.awt.Choice postpaidSubscribers;
}
