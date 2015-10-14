package com.hygenics.beans;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hygenics.beancleaner.SpringCleaner;
import com.hygenics.xmlcleaner.XMLTagCleaner;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;

/**
 * The main access point.
 * 
 * @author aevans
 *
 */
public class MainApp {

	public static final Logger log = LoggerFactory.getLogger(MainApp.class);

	public static void main(String[] args) {
		log.info("Starting to Clean");
		try {
			ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext(
					("file:" + System.getProperty("beansfile").trim()));
			int currIndex = 0;
			int currXMLIndex=0;
			boolean run = true;
			do {
				if (context.containsBean("Cleaners" + currIndex)) {
					((SpringCleaner) context.getBean("Cleaners" + currIndex)).run();
				} else if (currIndex == 0 && context.containsBean("Cleaners")) {
					((SpringCleaner) context.getBean("Cleaners")).run();
				} else {
					log.info("Terminating at Run: "+currIndex);
					run = false;
				}
				currIndex += 1;
			} while (run == true);
			
			run=true;
			
			do{
				if (context.containsBean("XMLCleaners" + currIndex)) {
					((XMLTagCleaner) context.getBean("XMLCleaners" + currIndex)).run();
				} else if (currIndex == 0 && context.containsBean("Cleaners")) {
					((XMLTagCleaner) context.getBean("XMLCleaners")).run();
				} else {
					log.info("Terminating at Run: "+currXMLIndex);
					run = false;
				}
				currXMLIndex += 1;
			}while (run ==true);
			
		} catch (Exception e) {
			log.error("Failed to Clean All XML Bean Files");
			e.printStackTrace();
		}
		log.info("Finished Cleaning Files. Check for Errors!");

	}
}
