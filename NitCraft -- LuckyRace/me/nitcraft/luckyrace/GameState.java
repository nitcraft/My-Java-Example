package me.nitcraft.luckyrace;

import org.bukkit.Bukkit;

import cloud.timo.TimoCloud.api.TimoCloudAPI;
import de.slimeplex.nitcore.NitCore;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public enum GameState {

	LOBBY, INGAME, RESTART, STOP, PVP, WINNER;

	private static GameState state;

	public static void setState(GameState state) {
		GameState.state = state;
		String s = "&&cError";
		switch (state) {
		case LOBBY:
	        NitCore.getServerManager().setMotd("§aLobby");
			break;
		case INGAME:
	        NitCore.getServerManager().setMotd("§cIngame");
		      Bukkit.getScheduler().scheduleAsyncDelayedTask(LuckyRace.getInstance(), new Runnable() {
		            @Override
		            public void run() {
	                TimoCloudAPI.getBukkitAPI().getThisServer().setState("INGAME");
		            }
		        }, 20);
//	                TimoCloudAPI.getBukkitAPI().getThisServer().setState("ingame");
//	                SystemListener.sendUpdateToLobbyGroup();
	            break;
		case PVP:
			MinecraftServer.getServer().setMotd("§aLobby");
//	                TimoCloudAPI.getBukkitAPI().getThisServer().setState("lobby");
//	                SystemListener.sendUpdateToLobbyGroup();
			break;
		case RESTART:
	        NitCore.getServerManager().setMotd("§aStartet neu...");
                     Bukkit.getScheduler().scheduleAsyncDelayedTask(LuckyRace.getInstance(), new Runnable() {
            @Override
            public void run() {
            TimoCloudAPI.getBukkitAPI().getThisServer().setState("END");
            }
        }, 20);
			break;
		case STOP:
			MinecraftServer.getServer().setMotd("§aStartet neu...");
//	                TimoCloudAPI.getBukkitAPI().getThisServer().setState("end");
//	                SystemListener.sendUpdateToLobbyGroup();
			break;
		default:
			break;
		}
	}

	public static boolean isState(GameState state) {
		return GameState.state == state;
	}

	public static GameState getState() {
		return state;
	}
}
