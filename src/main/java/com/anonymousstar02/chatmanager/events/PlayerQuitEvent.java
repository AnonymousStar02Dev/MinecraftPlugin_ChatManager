package com.anonymousstar02.chatmanager.events;

import com.anonymousstar02.chatmanager.ChatManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerQuitEvent implements Listener{
	
	private ChatManager plugin;
	
	public PlayerQuitEvent(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler
	public void onQuit(org.bukkit.event.player.PlayerQuitEvent event) {
		if(plugin.cooldown.containsKey(event.getPlayer().getUniqueId())) plugin.cooldown.remove(event.getPlayer().getUniqueId());
		if(plugin.repeat.containsKey(event.getPlayer().getUniqueId())) plugin.repeat.remove(event.getPlayer().getUniqueId());
	}

}
