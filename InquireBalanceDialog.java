import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class InquireBalanceDialog extends Dialog {

    public InquireBalanceDialog( theClient parent, boolean modal ) 
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
        commandString = new TextField();
        okButton = new Button();
        prepaidSubscribers = new Choice();
        
		prepaidSubscribers.add( "allprepaid" );
        numbers = subscribers.getAllSubscribers().iterator();
        while( numbers.hasNext() )
        {
        	prepaidSubscribers.add( (String) numbers.next() );
        }
        
        setLayout(new AbsoluteLayout());
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                closeDialog(evt);
            }
        });
        
        label1.setAlignment(Label.CENTER);
        label1.setFont(new Font("Dialog", 1, 14));
        label1.setText("CHOOSE PREPAID SUBSCRIBER");
        add(label1, new AbsoluteConstraints(160, 40, -1, -1));
        
        label2.setText("PREPAID SUBSCRIBER");
        add(label2, new AbsoluteConstraints(100, 80, -1, -1));
        
        label3.setText("COMMAND STRING");
        add(label3, new AbsoluteConstraints(50, 140, -1, -1));
        
        commandString.setFont(new Font("Dialog", 1, 14));
        add(commandString, new AbsoluteConstraints(50, 170, 440, -1));
        
        okButton.setLabel("Ok");
        okButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                okButtonMouseClicked(evt);
            }
        });
        
        add(okButton, new AbsoluteConstraints(220, 220, 100, -1));
        
        prepaidSubscribers.setFont(new Font("Dialog", 0, 14));
        prepaidSubscribers.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                prepaidSubscribersItemStateChanged(evt);
            }
        });
        
        add(prepaidSubscribers, new AbsoluteConstraints(260, 80, 250, -1));
        
        pack();
    }

    private void prepaidSubscribersItemStateChanged(ItemEvent evt) 
    {
    	commandString.setText( "balance " + extractNumber( ( String ) evt.getItem() ) );
    }

    private void okButtonMouseClicked(MouseEvent evt) 
    {
		c.theCommand.setText( commandString.getText() );
        setVisible(false);
        dispose();
    }

    private void closeDialog(WindowEvent evt) 
    {
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
        new InquireBalanceDialog(new theClient(), true).show();
    }


    theClient c;
    PrepaidHandler subscribers = new PrepaidHandler();
	Iterator numbers;
    private Label label1;
    private Label label2;
    private Label label3;
    private TextField commandString;
    private Button okButton;
    private Choice prepaidSubscribers;
    // End of variables declaration

}
