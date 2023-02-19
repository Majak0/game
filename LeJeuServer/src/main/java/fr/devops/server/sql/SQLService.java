package fr.devops.server.sql;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.function.Consumer;
import java.util.function.Function;

import javax.sql.DataSource;

public class SQLService implements ISQLService{
	
	private DataSource source;
	
	public SQLService(DataSource source) {
		this.source = source;
	}
	
	@Override
	public boolean authenticate(String username, String password) throws Exception {
		return connectThenQuery("SELECT 1 from \"LEJEU_USER\" WHERE \"USERNAME\"='"+username+"' AND \"PASSWORD\"='"+password+"'", (result) -> {
			try {
				return result.isBeforeFirst();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	@Override
	public boolean register(String username, String password) throws Exception{
		if (checkForSameUsername(username)) {
			return false;
		}else {
			connectThenDo((conn) -> {
				try (var prep = conn.prepareStatement("INSERT INTO \"LEJEU_USER\" (\"USERNAME\", \"PASSWORD\") values (?,?)");){
					prep.setString(1, username);
					prep.setString(2, password);
					prep.execute();
				}catch(Exception e) {
					throw new RuntimeException(e);
				}
			});
			return true;
		}
	}
	
	/**
	 * Return if username already exists in database
	 * @param username
	 * @return
	 * @throws Exception
	 */
	private boolean checkForSameUsername(String username) throws Exception {
		return connectThenQuery("SELECT 1 FROM \"LEJEU_USER\" WHERE \"USERNAME\"='"+username+"'", (result) -> {
			try {
				return result.isBeforeFirst();
			}catch(Exception e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	
	// UTIL ------------------
	
	private void connectThenDo(Consumer<Connection> action) throws Exception {
		try (var conn = source.getConnection()){
			conn.setAutoCommit(false);
			action.accept(conn);
			conn.commit();
		}catch(Exception e) {
			throw e;
		}
	}
	
	private <U> U connectThenQuery(String query, Function<ResultSet,U> action) throws Exception {
		try (var conn = source.getConnection();
				var preparedQuery = conn.prepareStatement(query);
				var set = preparedQuery.executeQuery()){
			return action.apply(set);
		}catch(Exception e) {
			throw e;
		}
	}
	
}