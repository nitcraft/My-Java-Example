package me.nitcraft.drops;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LuckyBlockDropManager {
	
	public static List<LuckyBlockDrop> dropList = new ArrayList<>();
	
	
	
	public static LuckyBlockDrop getDrop() {
		int random = new Random().nextInt(100);
		LuckyBlockDrop luckyBlockDrop = dropList.get(new Random().nextInt(dropList.size()));
		
		while(random > luckyBlockDrop.getChance()) {
			random = new Random().nextInt(100);
			luckyBlockDrop = dropList.get(new Random().nextInt(dropList.size()));
		}
		
		return luckyBlockDrop;
	}

}
