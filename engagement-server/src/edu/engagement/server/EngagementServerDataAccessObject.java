package edu.engagement.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

import edu.engagement.thrift.EegAttention;
import edu.engagement.thrift.EegPower;
import edu.engagement.thrift.EegRaw;
import edu.engagement.thrift.EngagementInformation;
import edu.engagement.thrift.Event;
import edu.engagement.thrift.HeartRate;

/**
 * @author Michael Barnes
 *
 * Object for accessing the engagement data stored on the server.
 */
public class EngagementServerDataAccessObject {
	/*
	 * These constants are the keys required for getting the needed queries from
	 * the server_sql_statements.properties file. If that doesn't mean anything
	 * to you, please look at their usage for further guidance.
	 */
	private static final String INSERTUSERKEY = "InsertUser";
	private static final String GETUSERKEY = "GetUser";
	private static final String INSERTPOWERKEY = "InsertEegPower";
	private static final String GETPOWERKEY = "GetEegPowerDuringRange";
	private static final String INSERTATTENTIONKEY = "InsertEegAttention";
	private static final String INSERTRAWKEY = "InsertEegRaw";
	private static final String INSERTHEARTRATEKEY = "InsertHeartRate";
	private static final String GETHEARTRATEKEY = "GetHeartRateDuringRange";

	private Properties prop;
	private Jdbc3PoolingDataSource source;

	/**
	 * Constructor for data access object. It loads the SQL used by the server
	 * from the server_sql_statements.properties file in the engagement-server/
	 * directory.
	 * 
	 * @param newSource		The database connection pool.
	 * @throws FileNotFoundException	If the server_sql_statements.properties
	 * 		can't be found in the engagement-server/ directory.
	 * @throws IOException		When an I/O exception occurs loading the
	 * 		properties file.
	 */
	public EngagementServerDataAccessObject(Jdbc3PoolingDataSource newSource)
			throws FileNotFoundException, IOException {
		prop = new Properties();
		prop.load(new FileInputStream("server_sql_statements.properties"));
		source = newSource;
	}
	
	/**
	 * @param username
	 * @param userEvent
	 * @return
	 */
	public List<EegPower> getPowerData(String username, Event userEvent) {
		Connection con = null;
		ResultSet powerData = null;
		List<EegPower> result = new ArrayList<EegPower>();
		
		try {
			con = source.getConnection();
			powerData = getPowerData(con, username, userEvent);
			while (powerData.next()) {
				EegPower powerReading = new EegPower();
				Timestamp ts = powerData.getTimestamp(3);
				Long time = ts.getTime();
				powerReading.setMillisecondTimeStamp(Long.toString(time));
				powerReading.setAlpha(powerData.getInt(4));
				powerReading.setBeta(powerData.getInt(5));
				powerReading.setTheta(powerData.getInt(6));
				result.add(powerReading);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private ResultSet getPowerData(Connection con, String username,
			Event userEvent) throws SQLException {
		Long time;
		Timestamp startingTime, finishTime;
		PreparedStatement getPowerData;
		
		time = Long.parseLong(userEvent.getStart());
		startingTime = new Timestamp(time);
		time = Long.parseLong(userEvent.getFinish());
		finishTime = new Timestamp(time);
		
		getPowerData = con.prepareStatement(prop
				.getProperty(GETPOWERKEY));
		getPowerData.setString(1, username);
		getPowerData.setTimestamp(2, startingTime);
		getPowerData.setTimestamp(3, finishTime);
		return getPowerData.executeQuery();
	}
	
	public List<HeartRate> getHeartRateData(String username, Event userEvent) {
		Connection con = null;
		ResultSet heartRateData = null;
		List<HeartRate> result = new ArrayList<HeartRate>();
		
		try {
			con = source.getConnection();
			heartRateData = getHeartRateData(con, username, userEvent);
			while (heartRateData.next()) {
				HeartRate heartRateReading = new HeartRate();
				Timestamp ts = heartRateData.getTimestamp(3);
				Long time = ts.getTime();
				heartRateReading.setMillsecondTimeStamp(Long.toString(time));
				heartRateReading.setBpm(heartRateData.getInt(4));
				result.add(heartRateReading);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				if(con != null) {
					con.close();
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}
	
	private ResultSet getHeartRateData(Connection con, String username,
			Event userEvent) throws SQLException {
		Long time;
		Timestamp startingTime, finishTime;
		PreparedStatement getHeartRateData;
		
		time = Long.parseLong(userEvent.getStart());
		startingTime = new Timestamp(time);
		time = Long.parseLong(userEvent.getFinish());
		finishTime = new Timestamp(time);
		
		getHeartRateData = con.prepareStatement(prop
				.getProperty(GETHEARTRATEKEY));
		getHeartRateData.setString(1, username);
		getHeartRateData.setTimestamp(2, startingTime);
		getHeartRateData.setTimestamp(3, finishTime);
		return getHeartRateData.executeQuery();
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
			addEegRawMessages(con, info.getGoogleId(),
					info.getEegRawMessages());
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

	/*
	 *  TODO(mtbarnes): Each of these should catch their sql exception and
	 *  		log them intelligently. This is something I will need to spend
	 *  		time on.
	 */
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