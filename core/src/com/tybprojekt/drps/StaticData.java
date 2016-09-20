package com.tybprojekt.drps;

import com.tybprojekt.drps.data.Choice;

public class StaticData {
	
	public static int LEVEL = 0;
	public static Choice[] ENEMY_CHOICES = null;
	public static int HIT = 100;
	public static int SCORE = 0;
	
	public static void RESET() {
		LEVEL = 0;
		ENEMY_CHOICES = null;
		HIT = 100;
		SCORE = 0;
	}
	
}
