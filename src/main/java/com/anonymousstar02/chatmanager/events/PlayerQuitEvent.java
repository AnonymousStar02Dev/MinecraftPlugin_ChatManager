package com.anonymousstar02.chatmanager.events;

import com.anonymousstar02.chatmanager.ChatManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitEvent implements Listener{
	
	private final ChatManager plugin;
	
	public PlayerQuitEvent(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onQuit(org.bukkit.event.player.PlayerQuitEvent event) {
		if(plugin.getCooldownMap().containsKey(event.getPlayer().getUniqueId())) plugin.getCooldownMap().remove(event.getPlayer().getUniqueId());
		if(plugin.getRepeatMap().containsKey(event.getPlayer().getUniqueId())) plugin.getRepeatMap().remove(event.getPlayer().getUniqueId());
	}

}
