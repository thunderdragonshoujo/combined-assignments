package com.cooksys.ftd.assignments.socket;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;
/**
 * Shared static methods to be used by both the {@link Client} and
 * {@link Server} classes.
 */
public class Utils {
	/**
	 * @return a {@link JAXBContext} initialized with the classes in the
	 *         com.cooksys.socket.assignment.model package
	 */
	public static JAXBContext createJAXBContext() throws JAXBException  {
		return JAXBContext.newInstance(Config.class);
		/*
		 * JAXBContext jc = null;
		//try {
			//jc =JAXBContext.newInstance(Config.class);
			//jc =JAXBContext.newInstance("student:config:localconfig:remoteconfig");
			//jc = JAXBContext.newInstance("com.cooksys.ftd.assignments.socket.model.Student , com.cooksys.ftd.assignments.socket.model.Config , com.cooksys.ftd.assignments.socket.model.LocalConfig , com.cooksys.ftd.assignments.socket.model.RemoteConfig ");
		} catch (Exception e) {
			System.out.println("ERROR");
			e.printStackTrace();
		}
		return jc;
		*/
	}

	/**
	 * Reads a {@link Config} object from the given file path.
	 *
	 * @param configFilePath
	 *            the file path to the config.xml file
	 * @param jaxb
	 *            the JAXBContext to use
	 * @return a {@link Config} object that was read from the config.xml file
	 */
	public static Config loadConfig(String configFilePath, JAXBContext jaxb) {
		Unmarshaller unmarshaller = null;
		File configFile = null;
		Config config = null;
		try {
			configFile = new File(configFilePath);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		try {
			unmarshaller = jaxb.createUnmarshaller();
			config = (Config) unmarshaller.unmarshal(configFile);
		} catch (JAXBException e) {
			e.printStackTrace();
		}
		
		return config;
	}
}
