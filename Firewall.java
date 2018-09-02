package com;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class Firewall {
	ArrayList<String> rules = new ArrayList<String>();
	public Firewall(String csvFile) {
		System.out.println("[1] Loading CSV file from "+csvFile);
		BufferedReader br = null;
		String line="";
		try {
            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
            		rules.add(line);
            }
        System.out.println("[2] Loading done!");  
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	
	/* accept_packet function accepts following parameters
	 * @param direction - 'inbound/outbound'
	 * @param protocol - 'tcp/udp'
	 * @param port - '[1-65535]'
	 * @param ip_address - [Range/single ip address]
	 */
	public boolean accept_packet(String direction, String protocol, int port,String ip_address) {
		boolean packet_status=false;
		String csvSplitter=",";
		String port_str=""+port; // Converting Integer port to String port to compare it with the port range
		for(String rule_set : rules) {
			String[] rule_inputs= rule_set.split(csvSplitter);
			if(!match_rule(direction,rule_inputs[0])) // Match input direction with the rule
				continue;
			if(!match_rule(protocol,rule_inputs[1])) // Match protocol direction with the rule
				continue;
			if(!match_rule(port_str,rule_inputs[2])) // Match port with the rule range
				continue;
			if(!match_rule(ip_address,rule_inputs[3])) // Match ip address with the rule range
				continue;
			packet_status=true; // If the code reaches here then it must have matched all the four conditions which states that the given packet satisifies one of the rules in the csv file
			break;
		}
		return packet_status;
	}
	
	/* match_rule  function takes in input and rule  and matches
	 */
	private boolean match_rule(String input, String rule) {
		String range_indicator="-";
		if(rule.indexOf(range_indicator)<0) {  // if the rule is not a range
			if(rule.equals(input))
				return true;
		}
		else {
			String[] range = rule.split(range_indicator);
			int input_value = calc_ascii_value(input);
			int start_value=calc_ascii_value(range[0]);
			int end_value=calc_ascii_value(range[1]);
			if(input_value>=start_value && input_value<=end_value)
				return true;
		}
		return false;
	}
	
	/* calc_ascii_value function is used to calculate the ascii value of a given string
	 * 
	 */
	private int calc_ascii_value(String input) {
		String dot_identifier = ".";
		if(input.indexOf(dot_identifier)<0)   // That is given input is a port range and not ip address
			return (Integer.parseInt(input)); 
		int value=0;
		char[] chars = input.toCharArray();
		for(char c : chars) {
			value+=(byte)c;
		}
		return value;
	}
}
