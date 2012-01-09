package com.google.code.autowiring;

import java.util.List;

import org.apache.log4j.Logger;

import com.google.code.autowiring.config.Config;
import com.google.code.autowiring.config.CfgEng;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Conv {

	private static Logger log = Logger.getRootLogger();

	public static Logger log() {
		return log;
	}
	
	protected Config getEnv() throws WiringException {
		try {
			Config config = new Config("etc/wiring.xml");
			//props.load(new FileInputStream("etc/wiring.properties"));
			return config;
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		try {
			Conv conv = new Conv();
			Config config = conv.getEnv();
			CfgEng designEngine = config.getEngine(args[0]); 
			String designFile = args[1]; 
			CfgEng reportEngine = config.getEngine(args[2]);
			String reportFile = args[3];
			Wiring design = WiringFactory.createWiring(designEngine, designFile);
			Wiring report = WiringFactory.createWiring(reportEngine, reportFile);
			List<Bean> beans = design.getBeans();
			report.setBeans(beans);
			report.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
