package com.seva.NamoVenkatesaya;

import java.awt.EventQueue;

public class SevaBooking {
	SevaBooking() {
		retrieveInputValues();
	}
	
	public static void main(String... args) throws InterruptedException {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				SevaBookingWindow sevaWindow = new SevaBookingWindow();
				sevaWindow.frame.setVisible(true);
			}
		});
		
	}

	protected void retrieveInputValues() {
		new SevaBookingWindow();
	}
	
	
	

}
