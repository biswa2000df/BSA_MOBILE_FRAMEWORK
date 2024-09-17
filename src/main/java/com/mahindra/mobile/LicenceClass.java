package com.mahindra.mobile;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;


import com.codoid.products.exception.FilloException;

public class LicenceClass {

	public static void LicenceCheck() throws ParseException, FilloException, InterruptedException, IOException {
			
			   try {
		            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
		            String macAddress = null;
		            while (networkInterfaces.hasMoreElements()) {
		                NetworkInterface networkInterface = networkInterfaces.nextElement();
		                byte[] macAddressBytes = networkInterface.getHardwareAddress();

		                if (macAddressBytes != null) {
		                    StringBuilder macAddressBuilder = new StringBuilder();
		                    for (byte b : macAddressBytes) {
		                        macAddressBuilder.append(String.format("%02X:", b));
		                    }
		                    macAddress = macAddressBuilder.toString().substring(0, macAddressBuilder.length() - 1).replace(":", "");

//		                    System.out.println("MAC Address for " + networkInterface.getName() + ": " + macAddress);
		                }
		            }
		        


//			System.out.println("MAC Address: " + macAddress);  //print the MAC address to check the comparision

			Boolean validateMac = validatemacid(macAddress);

			Date dt = new Date();
			
			SimpleDateFormat smdt = new SimpleDateFormat("dd/MM/yyyy");
			String sDate1 = "03/08/2025";
			Date date1 = smdt.parse(sDate1);
			if (dt.before(date1) && validateMac == true) {
				System.out.println();
				System.out.println("**********Biswajit Scriptless Automation Tool is a node based License.**********");
				System.out.println("            ********" + "Your License Validity is till " + sDate1 + "********");
				System.out.println("\n");
				System.out.println("                ********** Welcome " +System.getProperty("user.name") + " **********");
				System.out.println();
				System.out.println(dt);
				
//				UtilScreenshotAndReport.configureLog4j();//call to generate the logs
				ConnectToMainController.mainControllerSheet();

			} else {
				JFrame parent = new JFrame();
				JOptionPane.showMessageDialog(parent, "License has expired.\n please contact Biswajit");
				System.exit(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean validatemacid(String SystemMac) {
		List<String> al = new ArrayList<String>();
		al.add("00155D432100");
		al.add("00155DF11BCE");
		al.add("00155DEA6828");
		al.add("6EBAEF2C3B23");
		al.add("5CBAEF2C3B23");
		al.add(SystemMac);
		String macId = SystemMac;
		for (int i = 0; i < al.size(); i++) {
			if (al.get(i).equalsIgnoreCase(macId)) {
				return true;
			}
		}
		JFrame parent = new JFrame();
		JOptionPane.showMessageDialog(parent, "You are not licensed for this Framework ! \n please contact Biswajit");
		System.exit(1);
		return false;
	}

}
