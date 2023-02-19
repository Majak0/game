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
				return result.first();
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
		});
	}
	
	private void connectThenDo(Consumer<Connection> action) throws Exception {
		try (var conn = source.getConnection()){
			action.accept(conn);
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