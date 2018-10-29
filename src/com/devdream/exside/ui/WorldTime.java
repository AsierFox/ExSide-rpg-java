package com.devdream.exside.ui;

import com.devdream.exside.types.DayPart;

public class WorldTime {

	private static final int DAYPARTS_MINUTES_DURATION = 10;
	private static final int SECONDS_TO_MINUTES = 60;

	public long startTimeReference;
	
	private int secondsCounter;
	private int minutesCounter;

	private DayPart currentDayPart;


	public WorldTime() {
		startTimeReference = System.currentTimeMillis();
		secondsCounter = 0;
		minutesCounter = 0;

		currentDayPart = DayPart.MORNING;
	}

	public void update() {
		secondsCounter = (int) (System.currentTimeMillis() - startTimeReference) / 1000;

		if (secondsCounter > SECONDS_TO_MINUTES) {
			minutesCounter++;

			startTimeReference = System.currentTimeMillis();

			if (minutesCounter > DAYPARTS_MINUTES_DURATION) {
				changeDayPart();

				minutesCounter = 0;
			}
		}
	}

	private void changeDayPart() {
		switch (currentDayPart) {
			case MORNING:
				currentDayPart = DayPart.AFTERNOON;
				break;
			case AFTERNOON:
				currentDayPart = DayPart.EVENING;
				break;
			case EVENING:
				currentDayPart = DayPart.NIGHT;
				break;
			case NIGHT:
				currentDayPart = DayPart.MORNING;
		}
	}
	
	public String getTime() {
		// TODO Think this better
		switch (currentDayPart) {
			case MORNING:
				if (minutesCounter >= 0 && minutesCounter >= 1) {
					return "06:00";
				}
				else if (minutesCounter >= 2 && minutesCounter >= 3) {
					return "07:00";
				}
				else if (minutesCounter >= 4 && minutesCounter <= 5) {
					return "08:00";
				}
				else if (minutesCounter >= 6 && minutesCounter <= 7) {
					return "09:00";
				}
				else if (minutesCounter >= 8 && minutesCounter <= 9) {
					return "10:00";
				}
				else {
					return "11:00";
				}
			case AFTERNOON:
				if (minutesCounter >= 0 && minutesCounter >= 1) {
					return "12:00";
				}
				else if (minutesCounter >= 2 && minutesCounter >= 3) {
					return "13:00";
				}
				else if (minutesCounter >= 4 && minutesCounter <= 5) {
					return "14:00";
				}
				else if (minutesCounter >= 6 && minutesCounter <= 7) {
					return "15:00";
				}
				else if (minutesCounter >= 8 && minutesCounter <= 9) {
					return "16:00";
				}
			case EVENING:
				if (minutesCounter >= 0 && minutesCounter >= 1) {
					return "17:00";
				}
				else if (minutesCounter >= 2 && minutesCounter >= 3) {
					return "18:00";
				}
				else if (minutesCounter >= 4 && minutesCounter <= 5) {
					return "19:00";
				}
				else if (minutesCounter >= 6 && minutesCounter <= 7) {
					return "20:00";
				}
				else if (minutesCounter >= 8 && minutesCounter <= 9) {
					return "21:00";
				}
			case NIGHT:
				if (minutesCounter >= 0 && minutesCounter >= 1) {
					return "22:00";
				}
				else if (minutesCounter >= 2 && minutesCounter <= 3) {
					return "23:00";
				}
				else if (minutesCounter == 4) {
					return "00:00";
				}
				else if (minutesCounter == 5) {
					return "01:00";
				}
				else if (minutesCounter == 6) {
					return "02:00";
				}
				else if (minutesCounter == 7) {
					return "03:00";
				}
				else if (minutesCounter == 8) {
					return "04:00";
				}
				else if (minutesCounter == 9) {
					return "05:00";
				}
		}
		return null;
	}

}
