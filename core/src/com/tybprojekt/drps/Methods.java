package com.tybprojekt.drps;

import com.tybprojekt.drps.data.Choice;

public class Methods {

	public static int battle(Choice playerChoice, Choice enemyChoice) {

		if (playerChoice == Choice.ROCK) {
			switch (enemyChoice) {
			case ROCK:
				return 0;
			case PAPER:
				return -1;
			case SCISSORS:
				return 1;
			}
		}
		
		if (playerChoice == Choice.PAPER) {
			switch (enemyChoice) {
			case ROCK:
				return 1;
			case PAPER:
				return 0;
			case SCISSORS:
				return -1;
			}
		}
		
		if (playerChoice == Choice.SCISSORS) {
			switch (enemyChoice) {
			case ROCK:
				return -1;
			case PAPER:
				return 1;
			case SCISSORS:
				return 0;
			}
		}
		
		return -2;
	}
	
}
