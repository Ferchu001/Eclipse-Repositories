
package com.hcsinergia.delver.server;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;


import org.apache.log4j.Logger;
import org.springframework.context.support.GenericXmlApplicationContext;
import org.springframework.core.env.MapPropertySource;
import org.springframework.integration.ip.tcp.connection.AbstractServerConnectionFactory;
import org.springframework.integration.ip.util.TestingUtilities;
import org.springframework.util.SocketUtils;


/**
 * Starter Main TCP-Server
 * 
 *
 */
public final class Main {
	
	private static final Logger logger = Logger.getLogger(Main.class);

	private Main() {}

	/**
	 * Load TCP-Server with system integration
	 *
	 * @param args - port
	 */
	
	private static Scanner scanner2;

	public static void main(String... args) {
		
		String eol = System.getProperty("line.separator");
		scanner2 = new Scanner(System.in);
		final Scanner scanner = scanner2;
		try {
			logger.info("Server TCP Socket Starting");
	
			final GenericXmlApplicationContext context = Main.setupContext(args);
			final SimpleGateway gateway = context.getBean(SimpleGateway.class);
			final AbstractServerConnectionFactory PesosServer = context.getBean(AbstractServerConnectionFactory.class);
			
			logger.info("Waiting for server to accept connections...");
			TestingUtilities.waitListening(PesosServer, 10000L);
			logger.info("running.Fernando 004\n\n");
			public String stringCr;
			while (true) {
				
				final String input = stringCr;
				input.replaceall("$",eol);
				logger.info(input);

				if("q".equals(input.trim())) {
					break;
				}
				else {
					final String result = gateway.send(input);
					logger.info(result);
				}
			}
	
			logger.info("Exiting application...bye.");
			System.exit(0);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		} finally {
			if (scanner != null) {
				scanner.close();
			}
		}
		
	}

	public static GenericXmlApplicationContext setupContext(String... args) {
		final GenericXmlApplicationContext context = new GenericXmlApplicationContext();

		logger.info("Detect open server socket...");
		
		int availableServerSocket = args.length > 0 ? Integer.valueOf(args[0]) : SocketUtils.findAvailableTcpPort(5678);

		final Map<String, Object> sockets = new HashMap<String, Object>();
		sockets.put("availableServerSocket", availableServerSocket);

		final MapPropertySource propertySource = new MapPropertySource("sockets", sockets);
		context.getEnvironment().getPropertySources().addLast(propertySource);
		logger.info("using port " + context.getEnvironment().getProperty("availableServerSocket"));

		context.load("classpath:tcpServer-context.xml");
		context.registerShutdownHook();
		context.refresh();

		return context;
	}
}
