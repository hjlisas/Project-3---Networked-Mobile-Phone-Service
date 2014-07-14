import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class LoadDialog extends Dialog {

    public LoadDialog( theClient parent, boolean modal ) 
    {
        super(parent, modal);
        c = parent;
        initComponents();		
        this.setSize( 535, 300 );
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
        prepaidSubscribers = new Choice();
        amountToLoad = new Choice();
        amountToLoad.setEnabled( false );
    
        numbers = subscribers.getAllSubscribers().iterator();
        while( numbers.hasNext() )
        {
        	prepaidSubscribers.add( (String) numbers.next() );
        }
        
        for ( int i = 0; i < 3 ; i++ )
        {
        	amountToLoad.add( amounts[ i ] );
        }
        
        setLayout(new AbsoluteLayout());
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                closeDialog(evt);
            }
        });
        
        label1.setAlignment(Label.CENTER);
        label1.setFont(new Font("Dialog", 1, 14));
        label1.setText("INPUT LOAD INFORMATION");
        add(label1, new AbsoluteConstraints(170, 40, -1, -1));
        
        label2.setText("SIM CARD NUMBER");
        add(label2, new AbsoluteConstraints(50, 90, -1, -1));
        
        label3.setText("AMOUNT");
        add(label3, new AbsoluteConstraints(50, 130, -1, -1));
        
        label4.setText("COMMAND STRING");
        add(label4, new AbsoluteConstraints(40, 190, -1, -1));
        
        commandString.setFont(new Font("Dialog", 1, 14));
        add(commandString, new AbsoluteConstraints(40, 220, 450, -1));
        
        okButton.setLabel("Ok");
        okButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent evt) {
                okButtonMouseClicked(evt);
            }
        });
        
        add(okButton, new AbsoluteConstraints(220, 260, 100, -1));
        
        prepaidSubscribers.setFont(new Font("Dialog", 0, 14));
        prepaidSubscribers.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                prepaidSubscribersItemStateChanged(evt);
            }
        });
        
        add(prepaidSubscribers, new AbsoluteConstraints(200, 90, 250, -1));
        
        amountToLoad.setFont(new Font("Dialog", 0, 14));
        amountToLoad.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                amountToLoadItemStateChanged(evt);
            }
        });
        
        add(amountToLoad, new AbsoluteConstraints(200, 130, 150, -1));
        
        pack();
    }

    private void amountToLoadItemStateChanged(ItemEvent evt) 
    {
		commandString.setText( commandString.getText() 
			+ " " + extractNumber( ( String ) evt.getItem() ) );
    }

    private void prepaidSubscribersItemStateChanged(ItemEvent evt) 
    {
		commandString.setText( "load " + evt.getItem() );
		amountToLoad.setEnabled( true );
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
        new LoadDialog(new theClient(), true).show();
    }

    theClient c;
    private PrepaidHandler subscribers = new PrepaidHandler();
	private Iterator numbers;
	private final String[] amounts = { "300", "500", "1000" };
    private Label label1;
    private Label label2;
    private Label label3;
    private Label label4;
    private TextField commandString;
    private Button okButton;
    private Choice prepaidSubscribers;
    private Choice amountToLoad;
}
