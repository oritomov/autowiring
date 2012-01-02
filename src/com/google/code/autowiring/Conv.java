package com.google.code.autowiring;

import java.util.List;

import com.google.code.autowiring.util.Props;

/**
 * @author	Orlin Tomov
 * @version	1.0
 *
 */
public class Conv {

	protected Props getEnv() throws WiringException {
		try {
			Props props = new Props("etc/wiring.xml");
			//props.load(new FileInputStream("etc/wiring.properties"));
			return props;
		} catch (Exception e) {
			throw new WiringException(e.getMessage(), e);
		}
	}

	public static void main(String[] args) {
		try {
			Conv conv = new Conv();
			Props props = conv.getEnv();
			String designWiring = props.getProperty(args[0]); 
			String designFile = args[1]; 
			String reportWiring = props.getProperty(args[2]);
			String reportFile = args[3];
			Wiring design = WiringFactory.createWiring(designWiring, designFile);
			Wiring report = WiringFactory.createWiring(reportWiring, reportFile);
			List<Bean> beans = design.getBeans();
			report.setBeans(beans);
			report.save();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
