import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class SubscribeDialog extends Dialog {

    public SubscribeDialog( theClient parent, boolean modal ) 
    {
        super(parent, modal);
        c = parent;
        initComponents();		
        this.setSize( 535, 320 );
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
        newSIMNumber = new TextField();
        firstname = new TextField();
        lastname = new TextField();
        label5 = new Label();
        label6 = new Label();
        okButton = new Button();
        subscriptionType = new Choice();

        newSIMNumber.requestFocus();
        firstname.setEnabled( false );
        lastname.setEnabled( false );
        subscriptionType.setEnabled( false );
        
        for ( int i = 0; i < 2 ; i++ )
        {
        	subscriptionType.add( type[ i ] );
        }
        
        setLayout(new AbsoluteLayout());
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                closeDialog(evt);
            }
        });
        
        label1.setFont(new Font("Dialog", 1, 14));
        label1.setText("INPUT SUBSCRIBER INFORMATION");
        add(label1, new AbsoluteConstraints(140, 40, -1, -1));
        
        label2.setText("SIM CARD NUMBER");
        label3.setText("SUBSCRIPTION TYPE");
        
        label4.setText("COMMAND STRING");
        add(label4, new AbsoluteConstraints(40, 180, -1, -1));
        
        commandString.setFont(new Font("Dialog", 1, 14));
        add(commandString, new AbsoluteConstraints(40, 210, 470, -1));
        
        newSIMNumber.setFont(new Font("Dialog", 0, 14));
        newSIMNumber.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                newSIMNumberActionPerformed(evt);
            }
        });
        
        okButton.setLabel("Ok");
        okButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                okButtonMouseClicked(evt);
            }
        });
        
        add(okButton, new AbsoluteConstraints(220, 260, 110, -1));
        
        subscriptionType.setFont(new Font("Dialog", 0, 14));
        subscriptionType.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                subscriptionTypeItemStateChanged(evt);
            }
        });
        
        
        firstname.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                firstnameActionPerformed(evt);
            }
        });
        
        lastname.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                lastnameActionPerformed(evt);
            }
        });
        
        label5.setText("LAST NAME");
        label6.setText("FIRST NAME");

        add(label2, new AbsoluteConstraints(70, 65, -1, -1)); //SIM CARD NUMBER
        add(newSIMNumber, new AbsoluteConstraints(210, 65, 110, -1));
        add(label3, new AbsoluteConstraints(70, 140, -1, -1)); //SUBSCRIPTION TYPE
        add(subscriptionType, new AbsoluteConstraints(210, 140, 130, -1));
        add(label6, new AbsoluteConstraints(70, 90, -1, -1));
        add(lastname, new AbsoluteConstraints(210, 90, 160, -1));
        add(label5, new AbsoluteConstraints(70, 115, -1, -1));
        add(firstname, new AbsoluteConstraints(210, 115, 160, -1));

        pack();
    }

    private void lastnameActionPerformed(ActionEvent evt) {
		commandString.setText( commandString.getText() + " " + lastname.getText() );
		firstname.setEnabled( true );
		firstname.requestFocus();
    }

    private void firstnameActionPerformed(ActionEvent evt) {
		commandString.setText( commandString.getText() + " " + firstname.getText() );
		subscriptionType.setEnabled( true );
		subscriptionType.requestFocus();
    }

    private void subscriptionTypeItemStateChanged(ItemEvent evt) 
    {
		commandString.setText( commandString.getText() 
			+ " " + extractNumber( ( String ) evt.getItem() ) );
		okButton.requestFocus();
    }

    private void newSIMNumberActionPerformed(ActionEvent evt) 
    {
		commandString.setText( "subscribe " + newSIMNumber.getText() );
		lastname.setEnabled( true );
		lastname.requestFocus();
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
        new SubscribeDialog(new theClient(), true).show();
    }

    theClient c;
	private final String[] type = { "prepaid", "postpaid" };
    private Label label1;
    private Label label2;
    private Label label3;
    private Label label4;
    private TextField commandString;
    private TextField newSIMNumber;
    private Button okButton;
    private Choice subscriptionType;
    private TextField firstname;
    private TextField lastname;
    private Label label5;
    private Label label6;
}
