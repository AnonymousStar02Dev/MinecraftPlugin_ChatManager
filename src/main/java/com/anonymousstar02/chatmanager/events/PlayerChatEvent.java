package com.anonymousstar02.chatmanager.events;

import com.anonymousstar02.chatmanager.ChatManager;
import com.anonymousstar02.chatmanager.utils.Messages;
import com.anonymousstar02.chatmanager.utils.Permission;
import com.anonymousstar02.chatmanager.utils.TranslateColor;
import com.anonymousstar02.chatmanager.utils.Utils;
import com.anonymousstar02.chatmanager.utils.enums.Config;
import com.anonymousstar02.chatmanager.utils.enums.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatEvent implements Listener, TranslateColor, Permission {
	
	private final ChatManager plugin;
	public PlayerChatEvent(ChatManager plugin) {
		this.plugin = plugin;
	}
	
	@EventHandler(priority = EventPriority.HIGH)
	public void onChat(AsyncPlayerChatEvent event) {
		
		Player player = event.getPlayer();
		String message = event.getMessage();
		
		if(!hasPermissions(player,plugin.getPermissionService(),"chatmanager.*")) {
			if(plugin.getMainConfig().getBoolean(Config.PERMISSION_NEEDED.toString())) {
				if(!hasPermissions(player,plugin.getPermissionService(),"chatmanager.chat.allow")) {
					player.sendMessage(color(plugin.getMessagesConfig().getString(Message.NO_WRITE.toString())));
					event.setCancelled(true);
					return;
				}
			}
			
			//controlla se la chat è mutata
			if(plugin.getMainConfig().getBoolean(Config.MUTE_CHAT.toString())) {
				//controlla se il giocatore può bypassare il mute della chat
				if(!hasPermissions(player,plugin.getPermissionService(),"chatmanager.bypass.mute")) {
					player.sendMessage(color(plugin.getMessagesConfig().getString(Message.CHAT_IS_MUTED.toString())));
					event.setCancelled(true);
					return;
				}
			}
			
			//controlla se il giocatore scrive troppo velocemente
			if(plugin.getMainConfig().getBoolean(Config.ANTI_FLOOD.toString())) {
				if(!hasPermissions(player,plugin.getPermissionService(),"chatmanager.bypass.antiflood")) {
					if (plugin.getCooldownMap().containsKey(player.getUniqueId())) {
		                long time = (System.currentTimeMillis() - plugin.getCooldownMap().get(player.getUniqueId())) / 1000L;
		                if (time < Float.parseFloat(String.valueOf(plugin.getMainConfig().getDouble(Config.SEND_TIME.toString())))) {
		                	player.sendMessage(color(plugin.getMessagesConfig().getString(Message.NO_FLOOD.toString()).replace("%second%", String.valueOf((int) (Float.parseFloat(String.valueOf(plugin.getMainConfig().getDouble("send-time"))) - time)))));
		                	event.setCancelled(true);
		                    return;
		                }else {
		                    plugin.getCooldownMap().put(player.getUniqueId(), System.currentTimeMillis());
		                }
					}else {
		                plugin.getCooldownMap().put(player.getUniqueId(), System.currentTimeMillis());
		            }
				}
			}
			
			//controlla se il giocatore scrive messaggi uguali
			if (plugin.getMainConfig().getBoolean(Config.ANTI_REPEAT.toString())) {
	            if(!hasPermissions(player,plugin.getPermissionService(),"chatmanager.bypass.antirepeat")) {
	            	if (plugin.getRepeatMap().containsKey(player.getUniqueId())) {
	                    String str = plugin.getRepeatMap().get(player.getUniqueId());
	                    if (message.equals(str)) {
	                    	player.sendMessage(color(plugin.getMessagesConfig().getString(Message.NO_REPEAT_MESSAGE.toString())));
	                    	event.setCancelled(true);
	                        return;
	                    }else {
	                        plugin.getRepeatMap().put(player.getUniqueId(), message);
	                    }
	                }else {
	                    plugin.getRepeatMap().put(player.getUniqueId(), message);
	                }
	            }
	        }
			
			//controlla se il giocatore scrive un url in chat
			if(plugin.getMainConfig().getBoolean(Config.ANTI_URL.toString())) {
				if(!hasPermissions(player,plugin.getPermissionService(),"chatmanager.bypass.antiurl")) {
					if(Messages.containsUrl(message,plugin.getAllowedUrls())) {
						player.sendMessage(color(plugin.getMessagesConfig().getString(Message.NO_WRITE_URL.toString())));
						event.setCancelled(true);
						return;
					}
				}
			}
			
			//controlla se il giocatore scrive un ip in chat
			if(plugin.getMainConfig().getBoolean(Config.ANTI_IP.toString())) {
				if(!hasPermissions(player,plugin.getPermissionService(),"chatmanager.bypass.antiip")) {
					if(Messages.containsIp(message)) {
						player.sendMessage(color(plugin.getMessagesConfig().getString(Message.NO_WRITE_IP.toString())));
						event.setCancelled(true);
						return;
					}
				}
			}

			//controlla se il giocatore sta spammando parole bandite
			if(plugin.getMainConfig().getBoolean(Config.ANTI_SWEAR.toString())){
				if(!hasPermissions(player,plugin.getPermissionService(),"chatmanager.bypass.antiswear")){
					String word = Messages.containsSwearWords(message,plugin.getBlockedWords());
					if(!word.equals("null")){
						player.sendMessage(color(plugin.getMessagesConfig().getString(Message.NO_SWEAR.toString()).replace("%word%",word)));
						event.setCancelled(true);
						return;
					}
				}
			}
			
			//anticaps
			if(plugin.getMainConfig().getBoolean(Config.ANTI_CAPS.toString())) {
				if(!hasPermissions(player,plugin.getPermissionService(),"chatmanager.bypass.anticaps")) {
					for(String word : message.split("\\s+")){
						Player p = Bukkit.getPlayer(word);
						if(p != null) continue;
						message = message.replace(word,word.toLowerCase());
					}
					char ch = message.charAt(0);
					if(Utils.isAlphabetChar(ch)) message = message.replaceFirst(String.valueOf(ch),String.valueOf(ch).toUpperCase());
					ch = message.charAt(message.length()-1);
					if(ch != '!' && ch != '.' && ch != '?') message = message.concat(".");
					event.setMessage(message);
				}
			}
		}
	}
}
