// *******************************************************
// **   Project Title: MobilePhoneNetwork               **
// **   Filename: Balance.java 1.0 02/09/13             **
// **   hjlisas, admu 2002                              **
// *******************************************************

import java.util.*;

public class Balance extends Command
{
	public theServer ts;
	
	public Balance( String p, theServer ts )
	{
		parameter = p;
		this.ts = ts;
	}
	
	public void process()
	{
		Iterator prepaidSubscribers = prepaid.getAll().iterator();
		StringTokenizer token = new StringTokenizer( parameter, " " );
		String ID, str, sID, sFName, sLName, sBalance, outStr;
		
		ID = token.nextToken();
		temp = ""; str = ""; outStr = ""; sID = ""; 
		sFName = ""; sLName = ""; sBalance = "";

		try
		{
			if ( ID.equals( "allprepaid" ) )
			{
				ts.broadcast( "AVAILABLE BALANCE REPORT:" );
				while ( prepaidSubscribers.hasNext() )
				{
					str = (String) prepaidSubscribers.next();
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
					if ( subscribers.getType( ID ).equals( "prepaid" ) )
					{
						ts.broadcast( "AVAILABLE BALANCE REPORT:" );
						ts.broadcast( "balance for " + ID + " is P" + prepaid.getBalance( ID ) );
					}
					else
					{
						ts.broadcast( ID + "is not a prepaid number," );
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
		}
	}
}