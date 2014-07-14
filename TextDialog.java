import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class TextDialog extends Dialog {

    public TextDialog( theClient parent, boolean modal) {
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
        commandString = new TextField();
        okButton = new Button();
        numberChoice = new Choice();
        
        numbers = subscribers.getAll().iterator();
        while( numbers.hasNext() )
        {
        	numberChoice.add( (String) numbers.next() );
        }
        
        setLayout(new AbsoluteLayout());
        
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent evt) {
                closeDialog(evt);
            }
        });
        
        label1.setAlignment(Label.CENTER);
        label1.setFont(new Font("Dialog", 1, 14));
        label1.setText("INPUT TEXT INFORMATION");
        add(label1, new AbsoluteConstraints(160, 30, 210, -1));
        
        label2.setText("SIM CARD NUMBER");
        add(label2, new AbsoluteConstraints(50, 80, -1, -1));
        
        label3.setText("COMMAND STRING");
        add(label3, new AbsoluteConstraints(40, 139, -1, 20));
        
        commandString.setFont(new Font("Dialog", 1, 14));
        add(commandString, new AbsoluteConstraints(40, 170, 450, -1));
        
        okButton.setLabel("Ok");
        okButton.addMouseListener(new MouseAdapter() {
            public void mouseClicked( MouseEvent evt) {
                okButtonMouseClicked(evt);
            }
        });
        
        add(okButton, new AbsoluteConstraints(220, 210, 100, -1));
        
        numberChoice.setFont( new Font( "Dialog", 0, 14 ) );
        numberChoice.addItemListener(new  ItemListener() {
            public void itemStateChanged( ItemEvent evt) {
                numberChoiceItemStateChanged(evt);
                try
                {
                	commandString.setText( "text " 
                		+ extractNumber( ( String ) evt.getItem() ) );
                	System.out.println( extractNumber( (String) evt.getItem() ) );
                }
                catch( Exception e ) {}
            }
        });
        
        add(numberChoice, new AbsoluteConstraints(180, 80, 240, -1));
        
        pack();
    }

    private void numberChoiceItemStateChanged( ItemEvent evt) {
    }

    private void okButtonMouseClicked( MouseEvent evt) 
    {
        c.theCommand.setText( commandString.getText() );
        setVisible( false );
        dispose();
    }

    private void closeDialog( WindowEvent evt) {
        setVisible(false);
        dispose();
    }
    
    private String extractNumber( String str ) {
    	StringTokenizer z = new StringTokenizer( str, " " );
    	String s = z.nextToken();
    	return s;
    }

    public static void main(String args[]) {
        new TextDialog(new theClient(), true).show();
    }

    theClient c;
    SubscriberHandler subscribers = new SubscriberHandler();
	Iterator numbers;   
    private Label label1;
    private Label label2;
    private Label label3;
    private TextField commandString;
    private Button okButton;
    private Choice numberChoice;
}
