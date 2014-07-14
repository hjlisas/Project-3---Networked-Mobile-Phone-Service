// *******************************************************
// **   Project Title: MobilePhoneNetwork               **
// **   Filename: History.java 							**
// **   hjlisas, admu, September 2002                   **
// *******************************************************

import java.util.*;

public class History extends Command
{
	public theServer ts;
	
	public History( String p, theServer ts )
	{
		parameter = p;
		this.ts = ts;
	}

	public void process()
	{
		StringTokenizer token = new StringTokenizer( parameter, " " );
		String ID, numCalled, length, ref, type, outStr, str, cost;
		
		try
		{
			ID = token.nextToken();
			if ( subscribers.findSubscriber( ID ) )
			{
				if ( subscribers.getType( ID ).equals( "postpaid" ) )
				{
					Iterator callList = calls.getAll( ID ).iterator();
					ts.broadcast( "CALL HISTORY FOR " + ID );
					while ( callList.hasNext() )
					{	
						str = (String)callList.next();
						StringTokenizer st = new StringTokenizer( str, " " );
						ID = st.nextToken(); 
						ref = st.nextToken(); 
						numCalled = st.nextToken(); 
						cost = st.nextToken(); 
						length = st.nextToken(); 
						outStr = ref + ": " + numCalled + " " + length + " minute(s)";
						ts.broadcast( outStr );
						outStr = ""; ID = ""; ref = ""; numCalled = ""; 
						cost = ""; length = ""; 
					}
				}
				else
				{
					ts.broadcast( ID + " is not a postpaid subscriber." );
				}
			}
			else
			{
				ts.broadcast( "Subscriber has not been activated." );
			}
		}
		catch( Exception e )
		{
			ts.writeln( "ERROR" + e );
		}
	}
}