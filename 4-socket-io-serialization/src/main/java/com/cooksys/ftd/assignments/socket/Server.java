package com.cooksys.ftd.assignments.socket;

import java.io.File;
import java.io.OutputStreamWriter;
import java.net.ServerSocket;
import java.net.Socket;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

import com.cooksys.ftd.assignments.socket.model.Config;
import com.cooksys.ftd.assignments.socket.model.LocalConfig;
import com.cooksys.ftd.assignments.socket.model.Student;

public class Server extends Utils {

	/**
	 * Reads a {@link Student} object from the given file path
	 *
	 * @param studentFilePath
	 *            the file path from which to read the student config file
	 * @param jaxb
	 *            the JAXB context to use during unmarshalling
	 * @return a {@link Student} object unmarshalled from the given file path
	 */
	public static Student loadStudent(String studentFilePath, JAXBContext jaxb) {
		Unmarshaller unmarshaller = null;
		Student student = null;

		try {
			unmarshaller = jaxb.createUnmarshaller();
			System.out.println(">>>>>>>>>>>>>>>>" + studentFilePath);
			//File studentData = new File(studentFilePath);
			File studentData = new File("config\\student.xml");
			student = (Student) unmarshaller.unmarshal(studentData);
		} catch (JAXBException e) {
			e.printStackTrace();

		}
		return student;
	}

	/**
	 * The server should load a
	 * {@link com.cooksys.ftd.assignments.socket.model.Config} object from the
	 * <project-root>/config/config.xml path, using the "port" property of the
	 * embedded {@link com.cooksys.ftd.assignments.socket.model.LocalConfig} object
	 * to create a server socket that listens for connections on the configured
	 * port.
	 *
	 * Upon receiving a connection, the server should unmarshal a {@link Student}
	 * object from a file location specified by the config's "studentFilePath"
	 * property. It should then re-marshal the object to xml over the socket's
	 * output stream, sending the object to the client.
	 *
	 * Following this transaction, the server may shut down or listen for more
	 * connections.
	 * @throws JAXBException 
	 */
	public static void main(String[] args) throws JAXBException {
		JAXBContext jc = Utils.createJAXBContext();
		Config config = null;
		Socket client = null;
		OutputStreamWriter writer = null;
		Marshaller marshaller = null;
		ServerSocket socket = null;
		try {

			config = Utils.loadConfig(
					"C:\\Users\\ftd-11\\Desktop\\code\\combined-assignments\\4-socket-io-serialization\\config\\config.xml",
					jc);

			LocalConfig localconfig = config.getLocal();
			JAXBContext studentcontext = JAXBContext.newInstance(Student.class);

			socket = new ServerSocket(localconfig.getPort());
			client = socket.accept();

			Student student = loadStudent(config.getStudentFilePath(), studentcontext);
			writer = new OutputStreamWriter(client.getOutputStream());
			marshaller = jc.createMarshaller();
			marshaller.marshal(student, System.out);
			marshaller.marshal(student, writer);
			System.out.println("all done");
		} catch (Exception e) {
			System.out.println("Xml not loaded ");
			e.printStackTrace();
		}
		finally {
			try {
			socket.close();
			}catch (Exception e) {
				//eat this one
			}
			
		}
		
	}

}
