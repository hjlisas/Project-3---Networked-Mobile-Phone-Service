import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class PayDialog extends Dialog {

    public PayDialog(theClient parent, boolean modal) {
        super(parent, modal);
        c = parent;
        initComponents();		
        this.setSize( 535, 270 );
        this.setLocation( 250, 170 );
        this.setResizable( false );
    }

    private void initComponents() {
        label1 = new Label();
        label2 = new Label();
        label3 = new Label();
        label4 = new Label();
        commandString = new TextField();
        amount = new TextField();
        okButton = new Button();
        postpaidSubscribers = new Choice();
        postpaidSubscribers.requestFocus();
        amount.setEnabled( false );

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
        label1.setText("INPUT PAYMENT INFORMATION");
        add(label1, new AbsoluteConstraints(150, 30, -1, -1));
        
        label2.setText("POSTPAID SUBSCRIBER");
        add(label2, new AbsoluteConstraints(100, 80, -1, -1));
        
        label3.setText("COMMAND STRING");
        add(label3, new AbsoluteConstraints(50, 150, 190, -1));
        
        commandString.setFont(new Font("Dialog", 1, 14));
        add(commandString, new AbsoluteConstraints(50, 180, 440, -1));
        
        okButton.setLabel("Ok");
        okButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                okButtonMouseClicked(evt);
            }
        });
        
        add(okButton, new AbsoluteConstraints(220, 220, 100, -1));
        
        postpaidSubscribers.setFont(new Font("Dialog", 0, 14));
        postpaidSubscribers.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                postpaidSubscribersItemStateChanged(evt);
            }
        });
        
        add(postpaidSubscribers, new AbsoluteConstraints(250, 80, 250, -1));
        
        label4.setText("AMOUNT");
        add(label4, new AbsoluteConstraints(100, 120, 70, -1));

        amount.setFont(new java.awt.Font("Dialog", 0, 14));
        amount.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                amountActionPerformed(evt);
            }
        });
        
        add(amount, new AbsoluteConstraints(250, 120, 100, -1));

        pack();
    }

    private void amountActionPerformed(ActionEvent evt) 
    {
		commandString.setText( commandString.getText() + " " + amount.getText() );
    }

    private void postpaidSubscribersItemStateChanged(ItemEvent evt) 
    {
		commandString.setText( "pay " + extractNumber( ( String ) evt.getItem() ) );

		amount.setEnabled( true );
		amount.requestFocus();
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

    public static void main(String args[]) 
    {
        new PayDialog(new theClient(), true).show();
    }

    theClient c;
    PostpaidHandler subscribers = new PostpaidHandler();
	Iterator numbers;
    private Label label1;
    private Label label2;
    private Label label3;
    private TextField commandString;
    private TextField amount;
    private Button okButton;
    private Choice postpaidSubscribers;
    private Label label4;
}

