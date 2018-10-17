package com.devdream.nightly.ui;

import java.util.Date;

public class WorldTime {

	public Date startReferenceDate;


	public WorldTime() {
		startReferenceDate = new Date();
	}

	public void update() {
		System.out.println((int) new Date().getTime() - startReferenceDate.getTime() / 1000);
	}
	
}
