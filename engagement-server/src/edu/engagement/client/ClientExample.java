package edu.engagement.client;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;

import edu.engagement.thrift.EegAttention;
import edu.engagement.thrift.EegPower;
import edu.engagement.thrift.EegRaw;
import edu.engagement.thrift.EngagementInformation;
import edu.engagement.thrift.EngagementQuery;
import edu.engagement.thrift.EngagementService;
import edu.engagement.thrift.EngagementServiceUnavailable;
import edu.engagement.thrift.Event;
import edu.engagement.thrift.HeartRate;

/**
 * @author Michael Barnes
 *
 * Example thrift client code for making server queries. This can be used as a
 * general guide for interacting with the server.
 */
public class ClientExample {
	private static final int PORT = 7911;
	
	public static void main(String[] args) {
		try {
			TTransport transport = new TSocket("localhost", PORT);
			TBinaryProtocol protocol = new TBinaryProtocol(transport);
			EngagementService.Client client = 
					new EngagementService.Client(protocol);
			transport.open();
			EngagementInformation info = new EngagementInformation();
			
			// Add fake user data
			info.setGoogleId("code.mr.black");
			
			// Add fake attention data.
			EegAttention att = new EegAttention();
			att.setMillisecondTimeStamp(
					Long.toString(System.currentTimeMillis()));
			att.setAttention(100);
			info.addToEegAttentionMessages(att);
			
			// Add fake raw data.
			EegRaw raw = new EegRaw();
			raw.setMillisecondTimeStamp(
					Long.toString(System.currentTimeMillis()));
			raw.setRaw(101);
			info.addToEegRawMessages(raw);
			
			// Add fake power data
			EegPower pow = new EegPower();
			pow.setMillisecondTimeStamp(
					Long.toString(System.currentTimeMillis()));
			pow.setAlpha(102);
			pow.setBeta(103);
			pow.setTheta(104);
			info.addToEegPowerMessages(pow);
			
			// Add fake HR data
			HeartRate hr = new HeartRate();
			hr.setMillsecondTimeStamp(
					Long.toString(System.currentTimeMillis()));
			hr.setBpm(105);
			info.addToHeartRateMessages(hr);
			
			client.syncEngagementInformation(info);
			
			// Basic engagement query
			EngagementQuery query = new EngagementQuery();
			query.setGoogleId("code.mr.black");
			Event e = new Event();
			e.setName("Mock Event");
			e.setStart(Long.toString(0));
			e.setFinish(Long.toString(System.currentTimeMillis()));
			query.addToEvents(e);
			client.getEngagmentInformation(query);
			
			transport.close();
		} catch(TTransportException e) {
			// TODO(mr-black): Fail intelligently.
			e.printStackTrace();
		} catch (EngagementServiceUnavailable e) {
			// TODO(mr-black): Fail intelligently.
			e.printStackTrace();
		} catch (TException e) {
			// TODO(mr-black): Fail intelligently.
			e.printStackTrace();
		}
	}
}
