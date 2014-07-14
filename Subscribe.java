// *******************************************************
// **   Project Title: MobilePhoneNetwork               **
// **   Filename: Subscribe.java                		**
// **   hjlisas, admu, September 2002                   **
// *******************************************************

import java.util.*;

public class Subscribe extends Command
{
	public theServer ts;
	
	public Subscribe( String p, theServer ts )
	{
		parameter = p;
		this.ts = ts;
	}
	
	public void process()
	{
		StringTokenizer token = new StringTokenizer( parameter, " " );
		String ID, fName, lName, type;
		
		ID = token.nextToken();
		fName = token.nextToken();
		lName = token.nextToken();
		type = token.nextToken();
		temp = "";
		
		try
		{
			if ( subscribers.findSubscriber( ID ) )
			{
				ts.broadcast( "Subscriber is already activated!" );
			}
			else 
			{
				if ( type.equals( "prepaid" ) )
				{
					subscribers.addSubscriber( ID, type, fName, lName );
					prepaid.addSubscriber( ID, fName, lName, 0 );
					temp = type + " number " + ID + " for " + fName + " " + lName + " activated";
					ts.broadcast( "SIM ACTIVATION:" );
					ts.broadcast( temp );
				}
				else
				{
					subscribers.addSubscriber( ID, type, fName, lName );
					postpaid.addSubscriber( ID, fName, lName, 0 );
					temp = type + " number " + ID + " for " + fName + " " + lName + " activated";
					ts.broadcast( "SIM ACTIVATION:" );
					ts.broadcast( temp );
				}
			}
		}
		catch ( Exception e )
		{
			ts.writeln( "ERROR: " + e );
		}
	}
}