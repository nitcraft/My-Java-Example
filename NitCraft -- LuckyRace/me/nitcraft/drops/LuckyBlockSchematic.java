package me.nitcraft.drops;

import java.io.File;

import me.nitcraft.luckyrace.LuckyRace;

public class LuckyBlockSchematic {

	private String schematicName;
	private File file;
	private boolean spawnByPlayer;
	
	
	public LuckyBlockSchematic(String schematicName, boolean spawnByPlayer) {
		this.schematicName = schematicName;
		this.spawnByPlayer = spawnByPlayer;
		this.file = new File(LuckyRace.getInstance().getDataFolder().getPath() + "/LBS/", schematicName + ".schematic");
	}
	


	public String getSchematicName() {
		return schematicName;
	}

	public File getFile() {
		return file;
	}

	public boolean isSpawnByPlayer() {
		return spawnByPlayer;
	}
	
	
}
