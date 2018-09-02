package com;

public class Illumio_Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Firewall fw = new Firewall("/Users/vijayaharshavardhanpalla/Desktop/IIIuminoCodingChallenge/illumio_data.csv");  // Change the path of csv file as needed
		run_test_cases(fw); // Function to execute test cases
		
	}
	public static void run_test_cases(Firewall fw) {
		System.out.println("[4] Executing user defined test cases");
		print_result("outbound", "udp", 20000, "192.168.10.11",fw);  // Should return false;
		print_result("outbound", "tcp", 20000, "192.168.10.11",fw);  // Should return true;  // Port in range
		print_result("inbound", "udp", 53, "192.168.2.5",fw);  // Should return true; // Address in range
		print_result("outbound", "tcp", 120, "192.168.10.45",fw);  // Should return true; // This value matches with the 700th row in the csv file. To check if the code is responsive
		
		// Pdf test cases
		System.out.println("[4] Executing pre-defined test cases from the pdf");
		print_result("inbound", "tcp", 80, "192.168.1.2",fw); // Matches first rule;
		print_result("inbound", "udp", 53, "192.168.2.1",fw); // Matches third rule;
		print_result("outbound", "tcp", 10234, "192.168.10.11",fw); // Matches second rule;
	}
	public static void print_result(String direction,String protocol,int port, String ip_address,Firewall fw) {
		System.out.println("Packet Status : "+fw.accept_packet(direction, protocol, port, ip_address));
	}


}
