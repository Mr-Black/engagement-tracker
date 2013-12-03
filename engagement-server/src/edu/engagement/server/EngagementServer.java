package edu.engagement.server;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TThreadPoolServer;
import org.apache.thrift.server.TThreadPoolServer.Args;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TTransportException;

import org.postgresql.jdbc3.Jdbc3PoolingDataSource;

import edu.engagement.thrift.EngagementService;

public class EngagementServer implements Runnable {
	private static final int PORT = 7911;

	@Override
	public void run() {
		try {
			// Set up DB connection pool.
			Jdbc3PoolingDataSource source = new Jdbc3PoolingDataSource();

            // Load DB configuration options.                                                               
			Properties prop = new Properties();
            prop.load(new FileInputStream("database.properties"));
 
            // Set up data source.
	        source.setDataSourceName("Engagement Database");
	        source.setServerName(prop.getProperty("DbAddress"));
	        source.setDatabaseName(prop.getProperty("DbName"));
	        source.setUser(prop.getProperty("DbUser"));
	        source.setPassword(prop.getProperty("DbPassword"));
	        source.setMaxConnections(
	        		Integer.parseInt(prop.getProperty("DbMaxConnections")));
	        
	        // Start Thrift server.
			TServerSocket serverTransport = new TServerSocket(PORT);
			EngagementService.Processor<EngagementServiceImpl> processor = 
					new EngagementService.Processor<EngagementServiceImpl>(
							new EngagementServiceImpl(source));
			TServer server = new TThreadPoolServer(
					new Args(serverTransport).processor(processor));
			System.out.println("Starting server on port " + PORT);
			server.serve();
		} catch(TTransportException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	 
	public static void main(String args[]) throws ClassNotFoundException {
		// Intialize DB driver
		Class.forName("org.postgresql.Driver");

        // Start engagement server
        new Thread(new EngagementServer()).run();
    }		
}
