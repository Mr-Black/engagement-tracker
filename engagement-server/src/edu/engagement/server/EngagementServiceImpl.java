package edu.engagement.server;

import java.io.FileNotFoundException;
import java.io.IOException;

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
		// TODO(mtbarnes): Implement Database Logic
		System.out.print("Got data from: ");
		System.out.println(info.getGoogleId());
		dao.addEngagmentInfo(info);
	}
}
