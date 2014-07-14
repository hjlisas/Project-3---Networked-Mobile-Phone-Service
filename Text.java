// *******************************************************
// **   Project Title: MobilePhoneNetwork               **
// **   Filename: Text.java 							**
// **   hjlisas, admu, September 2002                   **
// *******************************************************

import java.util.*;

public class Text extends Command
{
	private final int TEXTCOST = BusinessRules.TEXTCOST;
	public theServer ts;
	
	public Text( String p, theServer ts )
	{
		parameter = p;
		this.ts = ts;
	}

	public void process()
	{
		StringTokenizer token = new StringTokenizer( parameter, " " );
		double balance, newBalance;
		String ID, temp, type;
		
		ID = token.nextToken(); 
		
		try
		{
			if ( subscribers.findSubscriber( ID ) )
			{
				type = subscribers.getType( ID );
				
				if ( type.equals( "prepaid" ) )
				{
					balance = prepaid.getBalance( ID );

					if ( balance <= 0 )
					{
						ts.writeln( "text message not sent due to insufficient balance." );
					}
					else if ( balance >= TEXTCOST )
					{	
						newBalance = balance - TEXTCOST;
						prepaid.updateBalance( ID, newBalance );
						temp = "text message sent by " + ID;
						ts.broadcast( "SEND SMS:" );
						ts.broadcast( temp );
					}
				}
				else if ( type.equals( "postpaid" ) )
				{
					balance = postpaid.getBalance( ID );
					
					if ( balance < 1000 )
					{
						newBalance = balance + TEXTCOST;
						postpaid.updateBalance( ID, newBalance );
						temp = "text message sent by " + ID;
						ts.broadcast( "SEND SMS:" );
						ts.broadcast( temp );
					}
					else
					{
						ts.broadcast( "text message not sent. credit limit has been reached." );
					}
				}
			}
			else
			{
				ts.broadcast( "Subscriber has not been activated" );
			}
		}
		catch( Exception e )
		{
			ts.writeln( "ERROR" + e );
		}
	}
}