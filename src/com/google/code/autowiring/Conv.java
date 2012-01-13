package com.google.code.autowiring;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import com.google.code.autowiring.config.Config;
import com.google.code.autowiring.config.CfgEng;
import com.google.code.autowiring.util.WireTool;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Conv {

	private static Logger log;

	static {
		log = Logger.getRootLogger();
		reloadProperties();
	}

	public static synchronized void reloadProperties() {
		try {
			String propFileName = "etc/log4j.properties";
			FileInputStream fis=null;
			fis = new FileInputStream(propFileName);
			Properties logProperties = new Properties();
			logProperties.load(fis);
			PropertyConfigurator.configure(logProperties);
		} catch (Exception e) {
			throw new WiringException("Log4j Properties Not Found!", e);
		}
	}

	public static Logger log() {
		return log;
	}

	protected Config getEnv() throws WiringException {
		try {
			Config config = new Config("etc/wiring.xml");
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
			@SuppressWarnings("unchecked")
			List<Bean> defs = (List<Bean>) WireTool.colorLines(beans);
			report.setBeans(defs, beans);
			report.save();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
	}
}
