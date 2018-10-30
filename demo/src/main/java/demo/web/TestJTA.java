package demo.web;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;
import javax.transaction.TransactionManager;
import javax.transaction.UserTransaction;

public class TestJTA {

	public boolean testUT() {
		DataSource dataSource = null;
		UserTransaction userTransaction = null;

		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;

		final String ds = "jdbc/test";
		try {
			Context initCtx = new InitialContext();
			dataSource = (DataSource) initCtx.lookup(ds);

			userTransaction = (UserTransaction) initCtx.lookup("java:comp/UserTransaction");

			System.out.println("################# " + userTransaction.getClass().getName());

			con = dataSource.getConnection();

			userTransaction.begin();

			statement = con.createStatement();
			statement.execute("insert into test (id,name) values ('1','a')");

			rs = statement.executeQuery("select * from test where id='1'");
			if (rs.next()) {
				System.out.println("############## " + rs.getString(2));
			}

			userTransaction.rollback();
			rs = statement.executeQuery("select * from test where id='1'");
			if (!rs.next()) {
				return true;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

		public boolean testTM() {
		DataSource dataSource = null;
		TransactionManager transactionManager = null;

		Connection con = null;
		Statement statement = null;
		ResultSet rs = null;

		final String ds = "jdbc/test";
		try {
			Context initCtx = new InitialContext();
			dataSource = (DataSource) initCtx.lookup(ds);

			transactionManager = (TransactionManager) initCtx.lookup("java:appserver/TransactionManager");

			System.out.println("################# " + transactionManager.getClass().getName());

			con = dataSource.getConnection();

			transactionManager.begin();

			statement = con.createStatement();
			statement.execute("insert into test (id,name) values ('2','b')");

			rs = statement.executeQuery("select * from test where id='2'");
			if (rs.next()) {
				System.out.println("############## " + rs.getString(2));
			}

			transactionManager.rollback();
			rs = statement.executeQuery("select * from test where id='2'");
			if (!rs.next()) {
				return true;
			}
		} catch (Exception e1) {
			e1.printStackTrace();
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return false;
	}

}
