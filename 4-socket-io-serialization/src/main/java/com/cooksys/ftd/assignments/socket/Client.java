package com.cooksys.ftd.assignments.socket;

import java.net.InetAddress;
import java.net.Socket;

import javax.xml.bind.JAXBContext;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Client {

	/**
     * The client should load a {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
     * <project-root>/config/config.xml path, using the "port" and "host" properties of the embedded
     * {@link com.cooksys.ftd.assignments.socket.model.RemoteConfig} object to create a socket that connects to
     * a {@link Server} listening on the given host and port.
     *
     * The client should expect the server to send a {@link com.cooksys.ftd.assignments.socket.model.Student} object
     * over the socket as xml, and should unmarshal that object before printing its details to the console.
     */
    public static void main(String[] args) {
    	Socket socket = null;
    	JAXBContext jc = null;
    	Student student = null;
    	String studentFilePath = null;
    	Config config = null;
    	try {
    		jc = Utils.createJAXBContext();
    		config = Utils.loadConfig(
					"C:\\Users\\ftd-11\\Desktop\\code\\combined-assignments\\4-socket-io-serialization\\config\\"
							+ "config.xml",
					jc);
    		JAXBContext studentcontext = JAXBContext.newInstance(Student.class);
        socket = new Socket(InetAddress.getLocalHost() , config.getLocal().getPort());
        studentFilePath = config.getStudentFilePath();
        System.out.println("!!!!!!!!!!!!!!!!!!!!!" + studentFilePath);
       student = Server.loadStudent("C:\\Users\\ftd-11\\Desktop\\code\\combined-assignments\\4-socket-io-serialization\\config\\student.xml" , studentcontext);
       System.out.println(student.toString());
    	} catch(Exception e) {
        	e.printStackTrace();
        }
        finally {
        	try {
        	socket.close();
        	} catch(Exception e) {
        	//eat this one 
        	}
        }
    }
}
