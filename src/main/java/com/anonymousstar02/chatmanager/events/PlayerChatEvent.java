package com.anonymousstar02.chatmanager.events;

import com.anonymousstar02.chatmanager.ChatManager;
import com.anonymousstar02.chatmanager.utils.Message;
import com.anonymousstar02.chatmanager.utils.Permission;
import com.anonymousstar02.chatmanager.utils.TranslateColor;
import com.anonymousstar02.chatmanager.utils.Variables;
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
		
		if(!hasPermissions(event.getPlayer(),plugin,"chatmanager.*")) {
			//controlla che c' bisogno di un permesso per scrivere in chat
			if(plugin.config.getBoolean(Variables.Config.PERMISSION_NEEDED.toString())) {
				//controlla se il giocatore ha il permesso di scrivere in chat		
				if(!hasPermissions(event.getPlayer(),plugin,"chatmanager.chat.allow")) {
					event.getPlayer().sendMessage(color(plugin.message.getString(Variables.Message.NO_WRITE.toString())));
					event.setCancelled(true);
					return;
				}
			}
			
			//controlla se la chat è mutata
			if(plugin.config.getBoolean(Variables.Config.MUTE_CHAT.toString())) {
				//controlla se il giocatore può bypassare il mute della chat
				if(!hasPermissions(event.getPlayer(),plugin,"chatmanager.bypass.mute")) {
					event.getPlayer().sendMessage(color(plugin.message.getString(Variables.Message.CHAT_IS_MUTED.toString())));
					event.setCancelled(true);
					return;
				}
			}
			
			//controlla se il giocatore scrive troppo velocemente
			if(plugin.config.getBoolean(Variables.Config.ANTI_FLOOD.toString())) {
				if(!hasPermissions(event.getPlayer(),plugin,"chatmanager.bypass.antiflood")) {
					if (plugin.cooldown.containsKey(event.getPlayer().getUniqueId())) {
		                long time = (System.currentTimeMillis() - plugin.cooldown.get(event.getPlayer().getUniqueId())) / 1000L;
		                if (time < Float.valueOf(String.valueOf(plugin.config.getDouble("send-time")))) {
		                	event.getPlayer().sendMessage(color(plugin.message.getString(Variables.Message.NO_FLOOD.toString()).replace("%second%", String.valueOf((int) (Float.valueOf(String.valueOf(plugin.config.getDouble("send-time"))) - time)))));
		                	event.setCancelled(true);
		                    return;
		                }else {
		                    plugin.cooldown.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
		                }
					}else {
		                plugin.cooldown.put(event.getPlayer().getUniqueId(), System.currentTimeMillis());
		            }
				}
			}
			
			//controlla se il giocatore scrive messaggi uguali
			if (plugin.config.getBoolean(Variables.Config.ANTI_REPEAT.toString())) {
	            if(!hasPermissions(event.getPlayer(),plugin,"chatmanager.bypass.antirepeat")) {
	            	if (plugin.repeat.containsKey(event.getPlayer().getUniqueId())) {
	                    String str = plugin.repeat.get(event.getPlayer().getUniqueId());
	                    if (event.getMessage().equals(str)) {
	                    	event.getPlayer().sendMessage(color(plugin.message.getString(Variables.Message.NO_REPEAT_MESSAGE.toString())));
	                    	event.setCancelled(true);
	                        return;
	                    }else {
	                        plugin.repeat.put(event.getPlayer().getUniqueId(), event.getMessage());
	                    }
	                }else {
	                    plugin.repeat.put(event.getPlayer().getUniqueId(), event.getMessage());
	                }
	            }
	        }
			
			//controlla se il giocatore scrive un url in chat
			if(plugin.config.getBoolean(Variables.Config.ANTI_URL.toString())) {
				if(!hasPermissions(event.getPlayer(),plugin,"chatmanager.bypass.antiurl")) {
					if(Message.isUrl(event.getMessage())) {
						event.getPlayer().sendMessage(color(plugin.message.getString(Variables.Message.NO_WRITE_URL.toString())));
						event.setCancelled(true);
						return;
					}
				}
			}
			
			//controlla se il giocatore scrive un ip in chat
			if(plugin.config.getBoolean(Variables.Config.ANTI_IP.toString())) {
				if(!hasPermissions(event.getPlayer(),plugin,"chatmanager.bypass.antiip")) {
					if(Message.isIp(event.getMessage())) {
						event.getPlayer().sendMessage(color(plugin.message.getString(Variables.Message.NO_WRITE_IP.toString())));
						event.setCancelled(true);
						return;
					}
				}
			}
			
			//controlla se scrive una parole in blacklist
			if(plugin.config.getBoolean(Variables.Config.ANTI_SWEAR.toString())) {
				if(!hasPermissions(event.getPlayer(),plugin,"chatmanager.bypass.antiswear")) {
					String str = Message.isSpam(event.getMessage(), plugin);
					if(!str.equals("null")) {
						event.getPlayer().sendMessage(color(plugin.message.getString(Variables.Message.NO_SWEAR.toString()).replace("%word%", str)));
						event.setCancelled(true);
					}
				}
			}
			
			//anticaps
			if(plugin.config.getBoolean(Variables.Config.ANTI_CAPS.toString())) {
				if(!hasPermissions(event.getPlayer(),plugin,"chatmanager.bypass.anticaps")) {
					char[] array = event.getMessage().toCharArray();
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
