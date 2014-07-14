// *******************************************************
// **   Project Title: MobilePhoneNetwork               **
// **   Filename: SubscriberHandler.java 1.0 02/09/13   **
// **   hjlisas, admu 2002                              **
// *******************************************************

import java.sql.*;
import java.util.*;

public class SubscriberHandler
{
	Connection c = null;
	PreparedStatement addToList, findInList, type, getNumbers;
	String ID;

	public SubscriberHandler()
	{
		ID = null;
		type = null;

		try
		{
			Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );
		}
		catch( ClassNotFoundException c )
		{
			printError( "ClassNotFound Error: " + c.getMessage() );
		}
	}

	public void addSubscriber( String ID, String type, String first, String last ) throws SQLException
	{
		try
		{
			c = DriverManager.getConnection( "jdbc:odbc:MobileDB" );
			addToList = c.prepareStatement( "INSERT INTO SUBSCRIBERLIST VALUES(?,?,?,?)" );
			addToList.setString( 1, ID );
			addToList.setString( 2, type );
			addToList.setString( 3, first );
			addToList.setString( 4, last );
			addToList.executeUpdate();
			c.close();
		}
		catch( SQLException e )
		{
			printError( "SQL Error: " + e.getMessage() );
		}
	}
	
	public boolean findSubscriber( String ID )
	{
		ResultSet rs;
		boolean b;
		String s;
		
		s = "";
		b = false;
		try
		{
			c = DriverManager.getConnection( "jdbc:odbc:MobileDB" );
			findInList = c.prepareStatement( "SELECT * FROM SUBSCRIBERLIST WHERE ID=?" );
			findInList.setString( 1, ID );
			rs = findInList.executeQuery();

			while ( rs.next() )
			{
				s = rs.getString( 1 );
			}

			if ( s.equals( ID ) ) b = true;
			else b = false;
			findInList.close();
			c.close();
		}
		catch( SQLException e )
		{
			printError( "SQL Error: " + e.getMessage() );
		}
		return b;
	}
	
	public String getType( String ID )
	{
		ResultSet rs;
		String s;
		
		s = "";
		try
		{
			c = DriverManager.getConnection( "jdbc:odbc:MobileDB" );
			type = c.prepareStatement( "SELECT * FROM SUBSCRIBERLIST WHERE ID=?" );
			type.setString( 1, ID );
			rs = type.executeQuery();

			while ( rs.next() )
			{
				s = rs.getString( 2 );
			}

			type.close();
			c.close();
		}
		catch( SQLException e )
		{
			printError( "SQL Error: " + e.getMessage() );
		}
		return s;
	}

	public Vector getAll()
	{
		ResultSet rs;
		Vector v = new Vector();
		
		try
		{
			c = DriverManager.getConnection( "jdbc:odbc:MobileDB" );
			getNumbers = c.prepareStatement( "SELECT * FROM SUBSCRIBERLIST" );
			rs = getNumbers.executeQuery();
			while( rs.next() )
			{
				v.addElement( rs.getString( 1 ) + " " + rs.getString( 3 ) 
							  + " " + rs.getString( 4 ) );
			}
			getNumbers.close();
			c.close();
		}
		catch( SQLException e )
		{
			printError( "SQL Error: " + e.getMessage() );
		}
		return v;
	}
	
	public void printError( String s ) {
		System.out.println( s );
	}
}
	
