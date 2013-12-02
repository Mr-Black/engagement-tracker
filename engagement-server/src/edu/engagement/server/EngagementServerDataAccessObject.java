package edu.engagement.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Properties;

import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

import edu.engagement.thrift.EegAttention;
import edu.engagement.thrift.EegPower;
import edu.engagement.thrift.EegRaw;
import edu.engagement.thrift.EngagementInformation;
import edu.engagement.thrift.HeartRate;

public class EngagementServerDataAccessObject {
	/*
	 * These constants are the keys required for getting the needed queries from
	 * the server_sql_statements.properties file. If that doesn't mean anything
	 * to you, please look at their usage for further guidance.
	 */
	private static final String INSERTUSERKEY = "InsertUser";
	private static final String GETUSERKEY = "GetUser";
	private static final String INSERTPOWERKEY = "InsertEegPower";
	private static final String INSERTATTENTIONKEY = "InsertEegAttention";
	private static final String INSERTRAWKEY = "InsertEegRaw";
	private static final String INSERTHEARTRATEKEY = "InsertHeartRate";

	private Properties prop;
	private Jdbc3PoolingDataSource source;

	public EngagementServerDataAccessObject(Jdbc3PoolingDataSource newSource)
			throws FileNotFoundException, IOException {
		prop = new Properties();
		prop.load(new FileInputStream("server_sql_statements.properties"));
		source = newSource;
	}

	public void addEngagmentInfo(EngagementInformation info) {
		Connection con = null;
		try {
			con = source.getConnection();
			if (!userExists(con, info.getGoogleId()))
				addUser(con, info.getGoogleId());
			con.setAutoCommit(false);
			addEegPowerMessages(con, info.getGoogleId(),
					info.getEegPowerMessages());
			addEegRawMessages(con, info.getGoogleId(), info.getEegRawMessages());
			addHeartRateMessages(con, info.getGoogleId(),
					info.getHeartRateMessages());
			addEegAttentionMessages(con, info.getGoogleId(),
					info.getEegAttentionMessages());
			con.commit();
			con.close();
		} catch (SQLException e) {
			// TODO(mtbarnes): log and throw an exception.
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					if(!con.isClosed()) {
						con.rollback();
						con.close();
					}
				} catch (SQLException e) {
					// TODO(mtbarnes): log and throw an exception.
					e.printStackTrace();
				}
			}
		}
	}

	private boolean userExists(Connection con, String username) 
			throws SQLException {
		PreparedStatement getUser = con.prepareStatement(prop
				.getProperty(GETUSERKEY));
		getUser.setString(1, username);
		ResultSet rs = getUser.executeQuery();
		rs.next();
		return rs.getBoolean(1);
	}

	private void addUser(Connection con, String username)
			throws SQLException {
		PreparedStatement addUser = con.prepareStatement(prop
				.getProperty(INSERTUSERKEY));
		addUser.setString(1, username);
		addUser.executeUpdate();
	}

	private void addEegPowerMessages(Connection con, String username,
			List<EegPower> messages)
					throws SQLException {
		long time;
		Timestamp ts;
		PreparedStatement addPowerMessage;	
		addPowerMessage = con.prepareStatement(prop
				.getProperty(INSERTPOWERKEY));
		for(EegPower pow: messages) {
			addPowerMessage.setString(1, username);
			time = Long.parseLong(pow.getMillisecondTimeStamp());
			ts = new Timestamp(time);
			addPowerMessage.setTimestamp(2, ts);
			addPowerMessage.setInt(3, pow.getAlpha());
			addPowerMessage.setInt(4, pow.getBeta());
			addPowerMessage.setInt(5, pow.getTheta());
			addPowerMessage.executeUpdate();
		}
	}

	private void addEegRawMessages(Connection con, String username,
			List<EegRaw> messages) throws SQLException {
		long time;
		Timestamp ts;
		PreparedStatement addRawMessage;	
		addRawMessage = con.prepareStatement(prop
				.getProperty(INSERTRAWKEY));
		for(EegRaw raw: messages) {
			addRawMessage.setString(1, username);
			time = Long.parseLong(raw.getMillisecondTimeStamp());
			ts = new Timestamp(time);
			addRawMessage.setTimestamp(2, ts);
			addRawMessage.setInt(3, raw.getRaw());
			addRawMessage.executeUpdate();
		}
	}

	private void addEegAttentionMessages(Connection con, String username,
			List<EegAttention> messages) throws SQLException {
		long time;
		Timestamp ts;
		PreparedStatement addAttentionMessage;	
		addAttentionMessage = con.prepareStatement(prop
				.getProperty(INSERTATTENTIONKEY));
		for(EegAttention att: messages) {
			addAttentionMessage.setString(1, username);
			time = Long.parseLong(att.getMillisecondTimeStamp());
			ts = new Timestamp(time);
			addAttentionMessage.setTimestamp(2, ts);
			addAttentionMessage.setInt(3, att.getAttention());
			addAttentionMessage.executeUpdate();
		}
	}

	private void addHeartRateMessages(Connection con, String username,
			List<HeartRate> messages) throws SQLException {
		long time;
		Timestamp ts;
		PreparedStatement addHeartRateMessage;	
		addHeartRateMessage = con.prepareStatement(prop
				.getProperty(INSERTHEARTRATEKEY));
		for(HeartRate hr: messages) {
			addHeartRateMessage.setString(1, username);
			time = Long.parseLong(hr.getMillsecondTimeStamp());
			ts = new Timestamp(time);
			addHeartRateMessage.setTimestamp(2, ts);
			addHeartRateMessage.setInt(3, hr.getBpm());
			addHeartRateMessage.executeUpdate();
		}
	}
}
