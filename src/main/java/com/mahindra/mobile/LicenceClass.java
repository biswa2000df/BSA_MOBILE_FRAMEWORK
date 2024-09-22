package com.mahindra.mobile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
	
	
	public static void Run() throws Exception {
		LicenceCheck();
	}

	public static void LicenceCheck() throws Exception {

		try {

			String IP = null;
			// Execute ADB command to check the status of the wlan0 interface (Wi-Fi)
			Process process = Runtime.getRuntime().exec("adb shell ip addr show wlan0");
			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
			String line;
			boolean wifiConnected = false;

			// First, check if Wi-Fi (wlan0) is UP
			while ((line = reader.readLine()) != null) {
				if (line.contains("wlan0")) {
					if (line.contains("state UP")) {
						wifiConnected = true; // Wi-Fi is ON and connected
						break;
					} else if (line.contains("state DOWN")) {
						wifiConnected = false; // Wi-Fi is OFF or disconnected
						break;
					}
				}
			}

			if (wifiConnected) {
//	                System.out.println("Wi-Fi is connected. Fetching IP address...");
				// Now get the IP address of the Wi-Fi interface
				process = Runtime.getRuntime().exec("adb shell ip addr show wlan0");
				reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
				while ((line = reader.readLine()) != null) {
					if (line.contains("inet ")) { // Look for the line containing the IP address
						IP = line.trim().split(" ")[1].split("/")[0]; // Extract the IP address
//	                        System.out.println("Mobile Device IP Address: " + IP);
						break;
					}
				}
			} else {
				JFrame parent = new JFrame();
				JOptionPane.showMessageDialog(parent,
						"Your Device is not connected to Wi-Fi !!! \n Please Connect your device to Wi-Fi.\n"
								+ "                       \uD83D\uDE0A\uD83D\uDE0A\uD83D\uDE0A");
				System.exit(1);
//	                System.out.println("Device is not connected to Wi-Fi.");
			}

			Boolean validateIP = validateIPid(IP);

			Date dt = new Date();
			SimpleDateFormat smdt = new SimpleDateFormat("dd/MM/yyyy");
			String sDate1 = "03/08/2025";
			Date date1 = smdt.parse(sDate1);

			if (dt.before(date1) && validateIP == true) {
				System.out.println();
				System.out.println("********** Biswajit Scriptless Automation Tool is a node based License **********");
				System.out.println("            ********" + " Your License Validity is till " + sDate1 + " ********");
				System.out.println("\n");
				System.out.println("                ********** Welcome " + System.getProperty("user.name").toUpperCase()
						+ " **********");
				System.out.println();
				System.out.println(dt);

//					UtilScreenshotAndReport.configureLog4j();//call to generate the logs
				ConnectToMainController.mainControllerSheet();

			} else {
				JFrame parent = new JFrame();
				JOptionPane.showMessageDialog(parent,
						"License has Expired.\nKindly contact Biswajit Sahoo. \uD83D\uDE0A\uD83D\uDE0A\uD83D\uDE0A");
				System.exit(1);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static boolean validateIPid(String MobileIP) {
		String IP = MobileIP.replaceAll("\\.", "_");
		List<String> al = new ArrayList<String>();
		al.add("192_168_1_101");
		al.add("192_168_1_106");
		al.add(IP);
		
		for (int i = 0; i < al.size(); i++) {
			if (al.get(i).equalsIgnoreCase(IP)) {
				return true;
			}
		}
		JFrame parent = new JFrame();
		JOptionPane.showMessageDialog(parent,
				"You are not licensed for this Framework ! \nPlease contact Biswajit Sahoo.\uD83D\uDE0A\uD83D\uDE0A\uD83D\uDE0A");
		System.exit(1);
		return false;
	}

}
