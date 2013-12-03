package edu.engagement.server;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.apache.thrift.TException;
import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

import edu.engagement.thrift.*;

public class EngagementServiceImpl implements EngagementService.Iface {
	private EngagementServerDataAccessObject dao;
	
	public EngagementServiceImpl(Jdbc3PoolingDataSource source) 
			throws FileNotFoundException, IOException {
		super();
		dao = new EngagementServerDataAccessObject(source);
	}

	@Override
	public void syncEngagementInformation(EngagementInformation info)
			throws EngagementServiceUnavailable, TException {
		// TODO(mtbarnes): Make a logging system.
		System.out.print("Got data from: ");
		System.out.println(info.getGoogleId());
		dao.addEngagmentInfo(info);
	}

	@Override
	public EngagementResponse getEngagmentInformation(EngagementQuery query)
			throws EngagementServiceUnavailable, TException {
		EngagementResponse resp = new EngagementResponse();
		for(Event e: query.getEvents()) {
			// TODO(mtbarnes): store event data for future processing.
			List<EegPower> powerData = dao.getPowerData(query.getGoogleId(), e);
			List<HeartRate> heartRateData = dao.getHeartRateData(
					query.getGoogleId(), e);
			EventInfo userEventInfo = new EventInfo();
			userEventInfo.setName(e.getName());
			userEventInfo.setAverageEngagement(
					calculateMeanEngagement(powerData));
			userEventInfo.setHeartRateStandardDeviation(
					calculateHeartRateStdDev(heartRateData));
			resp.addToEventInformation(userEventInfo);
		}
		return resp;
	}
	
	private double calculateMeanEngagement(List<EegPower> powerData) {
		double sum = 0.0;
		for(EegPower e: powerData) {
			sum += (double)(e.getAlpha() + e.getBeta()) / e.getTheta();
		}
		return sum / powerData.size();
	}
	
	private double calculateHeartRateStdDev(List<HeartRate> heartRateData) {
		double mean, variance, difMeanSqrd = 0.0, sum = 0.0;
		int size = heartRateData.size();
		for(HeartRate hr: heartRateData) {
			sum += hr.getBpm();
		}
		mean = sum / size;
		for(HeartRate hr: heartRateData) {
			difMeanSqrd += Math.pow(hr.getBpm() - mean, 2);
		}
		variance = difMeanSqrd / size;
		return Math.sqrt(variance);
	}
}
