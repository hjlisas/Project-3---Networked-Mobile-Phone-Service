// *******************************************************
// **   Project Title: MobilePhoneNetwork               **
// **   Filename: Due.java 								**
// **   hjlisas, admu, September 2002                   **
// *******************************************************

import java.util.*;

public class Due extends Command
{
	public theServer ts;
	
	public Due( String p, theServer ts )
	{
		parameter = p;
		this.ts = ts;
	}
	
	public void process()
	{
		Iterator postpaidSubscribers = postpaid.getAll().iterator();
		StringTokenizer token = new StringTokenizer( parameter, " " );
		String ID, str, sID, sFName, sLName, sBalance, outStr;
		
		ID = token.nextToken();
		temp = ""; str = ""; outStr = ""; sID = ""; 
		sFName = ""; sLName = ""; sBalance = "";
		
		try
		{
			if ( ID.equals( "allpostpaid" ) )
			{
				ts.broadcast( "BALANCE DUE REPORT:" );
				while ( postpaidSubscribers.hasNext() )
				{
					str = (String)postpaidSubscribers.next();
					StringTokenizer st = new StringTokenizer( str, " " );
					sID = st.nextToken(); sFName = st.nextToken(); 
					sLName = st.nextToken(); sBalance = st.nextToken();
					outStr = sID + " " + sFName + " " + sLName + " " + sBalance;
					ts.broadcast( outStr );
					str = ""; outStr = ""; sID = ""; sFName = "";
					sLName = ""; sBalance = "";
				}
			}
			else 
			{
				if ( subscribers.findSubscriber( ID ) )
				{
					if ( subscribers.getType( ID ).equals( "postpaid" ) )
					{
						ts.broadcast( "BALANCES DUE REPORT:" );
						ts.broadcast( "balance due for " + ID + " is P" + postpaid.getBalance( ID ) );
					}
					else
					{
						ts.broadcast( ID + "is not a postpaid number," );
						ts.broadcast( "this transaction is not applicable to the subscriber." );
					}
				}
				else
				{
					ts.broadcast( "Invalid SIM number, subscriber does not exist!" );
				}
			}
		}
		catch( Exception e)
		{
			ts.writeln( "ERROR: " + e );
		}
	}
}