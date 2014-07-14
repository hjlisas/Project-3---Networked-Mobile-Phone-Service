// *******************************************************
// **   Project Title: MobilePhoneNetwork               **
// **   Filename: PostpaidHandler.java 1.0 02/09/13     **
// **   hjlisas, admu 2002                              **
// *******************************************************

import java.sql.*;
import java.util.*;

public class PostpaidHandler
{
	Connection c = null;
	PreparedStatement addSubscriber, getSubscribers, updateBalance,
					  getBalance, all;
	String ID, lastName, firstName;
	double balance;

	public PostpaidHandler()
	{
		ID = null;
		lastName = null;
		firstName = null;
		this.balance = 0.00;

		try
		{
			Class.forName( "sun.jdbc.odbc.JdbcOdbcDriver" );
		}
		catch( ClassNotFoundException c )
		{
			printError( "ClassNotFound Error: " + c.getMessage() );
		}
	}

	public void addSubscriber( String ID, String firstName, String lastName, double balance )
	{
		try
		{
			c = DriverManager.getConnection( "jdbc:odbc:MobileDB" );
			addSubscriber = c.prepareStatement( "INSERT INTO POSTPAID VALUES(?,?,?,?)" );
			addSubscriber.setString( 1, ID );
			addSubscriber.setString( 2, firstName );
			addSubscriber.setString( 3, lastName );
			addSubscriber.setDouble( 4, balance );
			addSubscriber.executeUpdate();
			addSubscriber.close();
			c.close();
		}
		catch( SQLException e )
		{
			printError( "SQL Error: " + e.getMessage() );
		}
	}
	
	public double getBalance( String ID )
	{
		ResultSet rs;
		try
		{
			c = DriverManager.getConnection( "jdbc:odbc:MobileDB" );
			getBalance = c.prepareStatement( "SELECT * FROM POSTPAID WHERE ID=?" );
			getBalance.setString( 1, ID );
			rs = getBalance.executeQuery();
			while ( rs.next() )
			{
				this.balance = rs.getDouble( "Balance" );
			}
			getBalance.close();
		}
		catch( SQLException e )
		{
			printError( "SQL Error: " + e.getMessage() );
		}
		return this.balance;
	}
	
	public void updateBalance( String ID, double value )
	{
		double temp = value;
		
		try
		{
			c = DriverManager.getConnection( "jdbc:odbc:MobileDB" );
			updateBalance = c.prepareStatement( "UPDATE POSTPAID SET BALANCE=" + temp + " WHERE ID=?" );
			updateBalance.setString( 1, ID );
			updateBalance.executeUpdate();
			updateBalance.close();
			c.close();
		}
		catch( SQLException e )
		{
			printError( "SQL Error: " + e.getMessage() );
		}
	}
	
	public Vector getAll()
	{
		ResultSet rs;
		Vector v = new Vector();
		
		try
		{
			c = DriverManager.getConnection( "jdbc:odbc:MobileDB" );
			getSubscribers = c.prepareStatement( "SELECT * FROM POSTPAID" );
			rs = getSubscribers.executeQuery();
			while( rs.next() )
			{
				ID = rs.getString( 1 );
				firstName = rs.getString( 2 );
				lastName = rs.getString( 3 );
				this.balance = rs.getDouble( 4 );
				String s = ID + " " + firstName + " " + lastName + " " + this.balance;
				v.addElement( s );
			}
			getSubscribers.close();
			c.close();
		}
		catch( SQLException e )
		{
			printError( "SQL Error: " + e.getMessage() );
		}
		return v;
	}

	public Vector getAllSubscribers()
	{
		ResultSet rs;
		Vector x = new Vector();
		
		try
		{
			c = DriverManager.getConnection( "jdbc:odbc:MobileDB" );
			all = c.prepareStatement( "SELECT * FROM POSTPAID" );
			rs = all.executeQuery();
			while( rs.next() )
			{
				x.addElement( rs.getString( 1 ) + " " 
							  + " " + rs.getString( 2 ) + " " + rs.getString( 3 ) );
			}
			all.close();
			c.close();
		}
		catch( SQLException e )
		{
			printError( "SQL Error: " + e.getMessage() );
		}
		return x;
	}
	
	public void printError( String s ) {
		System.out.println( s );
	}
}
	
