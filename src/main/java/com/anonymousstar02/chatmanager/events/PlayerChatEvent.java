package com.anonymousstar02.chatmanager.events;

import com.anonymousstar02.chatmanager.ChatManager;
import com.anonymousstar02.chatmanager.utils.Message;
import com.anonymousstar02.chatmanager.utils.Permission;
import com.anonymousstar02.chatmanager.utils.TranslateColor;
import com.anonymousstar02.chatmanager.utils.Variables;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEvent implements Listener, TranslateColor, Permission {
	
	private ChatManager plugin;
	public PlayerChatEvent(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGH,ignoreCancelled = false)
	public void onChat(AsyncPlayerChatEvent event) {
		
		Player player = event.getPlayer();
		String message = event.getMessage();
		
		if(!hasPermissions(player,plugin,"chatmanager.*")) {
			if(plugin.config.getBoolean(Variables.Config.PERMISSION_NEEDED.toString())) {
				if(!hasPermissions(player,plugin,"chatmanager.chat.allow")) {
					player.sendMessage(color(plugin.message.getString(Variables.Message.NO_WRITE.toString())));
					event.setCancelled(true);
					return;
				}
			}
			
			//controlla se la chat è mutata
			if(plugin.config.getBoolean(Variables.Config.MUTE_CHAT.toString())) {
				//controlla se il giocatore può bypassare il mute della chat
				if(!hasPermissions(player,plugin,"chatmanager.bypass.mute")) {
					player.sendMessage(color(plugin.message.getString(Variables.Message.CHAT_IS_MUTED.toString())));
					event.setCancelled(true);
					return;
				}
			}
			
			//controlla se il giocatore scrive troppo velocemente
			if(plugin.config.getBoolean(Variables.Config.ANTI_FLOOD.toString())) {
				if(!hasPermissions(player,plugin,"chatmanager.bypass.antiflood")) {
					if (plugin.cooldown.containsKey(player.getUniqueId())) {
		                long time = (System.currentTimeMillis() - plugin.cooldown.get(player.getUniqueId())) / 1000L;
		                if (time < Float.valueOf(String.valueOf(plugin.config.getDouble(Variables.Config.SEND_TIME.toString())))) {
		                	player.sendMessage(color(plugin.message.getString(Variables.Message.NO_FLOOD.toString()).replace("%second%", String.valueOf((int) (Float.valueOf(String.valueOf(plugin.config.getDouble("send-time"))) - time)))));
		                	event.setCancelled(true);
		                    return;
		                }else {
		                    plugin.cooldown.put(player.getUniqueId(), System.currentTimeMillis());
		                }
					}else {
		                plugin.cooldown.put(player.getUniqueId(), System.currentTimeMillis());
		            }
				}
			}
			
			//controlla se il giocatore scrive messaggi uguali
			if (plugin.config.getBoolean(Variables.Config.ANTI_REPEAT.toString())) {
	            if(!hasPermissions(player,plugin,"chatmanager.bypass.antirepeat")) {
	            	if (plugin.repeat.containsKey(player.getUniqueId())) {
	                    String str = plugin.repeat.get(player.getUniqueId());
	                    if (message.equals(str)) {
	                    	player.sendMessage(color(plugin.message.getString(Variables.Message.NO_REPEAT_MESSAGE.toString())));
	                    	event.setCancelled(true);
	                        return;
	                    }else {
	                        plugin.repeat.put(player.getUniqueId(), message);
	                    }
	                }else {
	                    plugin.repeat.put(player.getUniqueId(), message);
	                }
	            }
	        }
			
			//controlla se il giocatore scrive un url in chat
			if(plugin.config.getBoolean(Variables.Config.ANTI_URL.toString())) {
				if(!hasPermissions(player,plugin,"chatmanager.bypass.antiurl")) {
					if(Message.containsUrl(message)) {
						player.sendMessage(color(plugin.message.getString(Variables.Message.NO_WRITE_URL.toString())));
						event.setCancelled(true);
						return;
					}
				}
			}
			
			//controlla se il giocatore scrive un ip in chat
			if(plugin.config.getBoolean(Variables.Config.ANTI_IP.toString())) {
				if(!hasPermissions(player,plugin,"chatmanager.bypass.antiip")) {
					if(Message.containsIp(message)) {
						player.sendMessage(color(plugin.message.getString(Variables.Message.NO_WRITE_IP.toString())));
						event.setCancelled(true);
						return;
					}
				}
			}

			//controlla se il giocatore sta spammando parole bandite
			if(plugin.config.getBoolean(Variables.Config.ANTI_SWEAR.toString())){
				if(!hasPermissions(player,plugin,"chatmanager.bypass.antiswear")){
					String word = Message.containsSwearWords(message,plugin);
					if(!word.equals("null")){
						player.sendMessage(color(plugin.message.getString(Variables.Message.NO_SWEAR.toString()).replace("%word%",word)));
						event.setCancelled(true);
						return;
					}
				}
			}
			
			//anticaps
			if(plugin.config.getBoolean(Variables.Config.ANTI_CAPS.toString())) {
				if(!hasPermissions(player,plugin,"chatmanager.bypass.anticaps")) {
					char[] array = message.toCharArray();
					for(int x = 0;x < array.length;x++) {
						if(x == 0) array[x] = String.valueOf(array[x]).toUpperCase().charAt(0);
						else array[x] = String.valueOf(array[x]).toLowerCase().charAt(0);
					}
					event.setMessage(String.valueOf(array));
				}
			}
		}
	}
}
