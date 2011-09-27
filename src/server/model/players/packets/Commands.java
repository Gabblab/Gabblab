package server.model.players.packets;

import server.Config;
import server.Connection;
import server.Server;
import server.model.players.Client;
import server.model.players.PacketType;
import server.model.players.PlayerHandler;
import server.model.items.ItemAssistant;
import server.util.Misc;


import java.io.*;

/**
 * Commands
 **/
public class Commands implements PacketType 
{

    
    @Override
    public void processPacket(Client c, int packetType, int packetSize) 
    {
    String playerCommand = c.getInStream().readString();
		if (!playerCommand.startsWith("/"))
		{
			c.getPA().writeCommandLog(playerCommand);
		}
		if (playerCommand.startsWith("/") && playerCommand.length() > 1) {
			if (c.clanId >= 0) {
				System.out.println(playerCommand);
				playerCommand = playerCommand.substring(1);
				Server.clanChat.playerMessageToClan(c.playerId, playerCommand, c.clanId);
			} else {
				if (c.clanId != -1)
				c.clanId = -1;
				c.sendMessage("You are not in a clan.");
			}
			return;       
		}
    if (Config.SERVER_DEBUG)
        Misc.println(c.playerName+" playerCommand: "+playerCommand);
    
    if (c.playerRights >= 0)
        playerCommands(c, playerCommand);
    if (c.playerRights == 1 || c.playerRights == 2 || c.playerRights == 3) 
        moderatorCommands(c, playerCommand);
    if (c.playerRights == 2 || c.playerRights == 3) 
        administratorCommands(c, playerCommand);
    if (c.playerRights == 3) 
        ownerCommands(c, playerCommand);
        if (c.playerRights == 4 || c.playerRights == 5 || c.playerRights == 6)
        DonatorCommands(c, playerCommand);
    
    }

    
    public void playerCommands(Client c, String playerCommand)
    {
		    
		    	if (playerCommand.equalsIgnoreCase("players")) {
					c.getPA().showInterface(8134);
					c.getPA().sendFrame126("Gabblab - Online Players", 8144);
					c.getPA().sendFrame126("Online players(" + PlayerHandler.getPlayerCount() + "):", 8145);
					int line = 8147;
					for (int i = 0; i < Config.MAX_PLAYERS; i++)  {
						if (Server.playerHandler.players[i] != null) {
							Client d = c.getClient(Server.playerHandler.players[i].playerName);
							if (d.playerName != null){
								c.getPA().sendFrame126(d.playerName, line);
								line++;
							} else if (d.playerName == null) {
								c.getPA().sendFrame126("", line);
							}
						}
							}
							c.flushOutStream();
						
    
			c.sendMessage("There are currently "+PlayerHandler.getPlayerCount()+ " players online.");
		    }
			if (playerCommand.startsWith("changepassword") && playerCommand.length() > 15) {
				c.playerPass = playerCommand.substring(15);
				c.sendMessage("Your password is now: " + c.playerPass);			
			}
			/*if (playerCommand.equals("funpk")) {
				c.getPA().startTeleport(3504, 3575, 0, "modern");	
			}*/
			if (playerCommand.equals("frosts")) {
				c.getPA().startTeleport(3052, 9576, 0, "modern");	
			}
			if (playerCommand.equals("dungeon")) {
				c.getPA().startTeleport(3245, 9364, 0, "modern");	
			}
			
			
			if (playerCommand.startsWith("sit") && c.sit == false) {
			if(c.inWild()) {
			c.sendMessage("You cannot do this in the wilderness.");
			return;
			}
			c.sit = true;
			if(c.playerRights == 1) {
			c.startAnimation(4116);
			}
			if(c.playerRights == 2) {
			c.startAnimation(4113);
			}
			if(c.isDonator == 1) {
			c.startAnimation(4115);
			}
			if(c.playerRights == 0) {
			c.startAnimation(4115);
			}
			if(c.playerRights == 3) {
			c.startAnimation(4117);
			}
			}


			if (playerCommand.startsWith("unsit") && c.sit == true) {
			if(c.inWild()) {
			c.sendMessage("You cannot do this in wildy");
			return;
			}
			c.sit = false;
		c.startAnimation(4191);
			}

			if (playerCommand.startsWith("qpsk")) {
		c.startAnimation(4945);
		c.gfx0(816);
			}
if (playerCommand.equals("empty")) {
if (c.inWild())
return;
c.getPA().removeAllItems();
}


			if (playerCommand.equalsIgnoreCase("CXP")) {
				c.sendMessage("You have "+c.pcPoints+ " CXP..");
				c.sendMessage("And "+c.magePoints+ " Agility Points!");
			}

			if (playerCommand.equalsIgnoreCase("agility points")) {
				c.sendMessage("You currently have "+c.magePoints+ " Agility Points! Buy items at ::agility for the points!");
			}

			if (playerCommand.startsWith("death")) {
				c.getPA().showInterface(17100);
			}

			if (playerCommand.equalsIgnoreCase("save")) {
				c.SaveGame();
				c.sendMessage("Your acc has been saved nub.");
			}
			
if (playerCommand.startsWith("report") && playerCommand.length() > 7) {
   try {
   BufferedWriter report = new BufferedWriter(new FileWriter("./Data/Reports/Reports.txt", true));
   String Report = playerCommand.substring(7);
   try {	
	report.newLine();
	report.write(c.playerName + ": " + Report);
	c.sendMessage("You have successfully submitted your report.");
	} finally {
	report.close();
	}
	} catch (IOException e) {
                e.printStackTrace();
	}
}	
			if (playerCommand.startsWith("resetdef")) {
				if (c.inWild())
				return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("Please take all your armour and weapons off before using this command.");
						return;
					}
				}
				try {
					int skill = 1;
					int level = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
				} catch (Exception e){}
			}
			if (playerCommand.startsWith("resetrange")) {
				if (c.inWild())
				return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("Please take all your armour and weapons off before using this command.");
						return;
					}
				}
				try {
					int skill = 4;
					int level = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
				} catch (Exception e){}
			}
			if (playerCommand.startsWith("resetmage")) {
				if (c.inWild())
				return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("Please take all your armour and weapons off before using this command.");
						return;
					}
				}
				try {
					int skill = 6;
					int level = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
				} catch (Exception e){}
			}
			if (playerCommand.startsWith("resetattack")) {
				if (c.inWild())
				return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("Please take all your armour and weapons off before using this command.");
						return;
					}
				}
				try {
					int skill = 0;
					int level = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
				} catch (Exception e){}
			}
			if (playerCommand.startsWith("resetstrength")) {
				if (c.inWild())
				return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("Please take all your armour and weapons off before using this command.");
						return;
					}
				}
				try {
					int skill = 2;
					int level = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
				} catch (Exception e){}
			}
			if (playerCommand.startsWith("resetprayer")) {
				if (c.inWild())
				return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("Please take all your armour and weapons off before using this command.");
						return;
					}
				}
				try {
					int skill = 5;
					int level = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
				} catch (Exception e){}
			}
			if (playerCommand.equals("agility")) {
				c.getPA().startTeleport(2480, 3437, 0, "modern");
			}
			if (playerCommand.equals("queen")) {
				c.getPA().startTeleport(3222, 3221, 0, "modern");
			}
			if (playerCommand.equals("nomad")) {
				c.getPA().startTeleport(2404, 3080, 0, "modern");
				c.sendMessage("<shad=15369497>HE HAS 500 HP AND HITS EXTREMELY HIGH BE CAREFUL!!!");
				c.sendMessage("<shad=15369497>IT IS MULTI HERE");
				c.sendMessage("<shad=15369497>Nomad currently drops new infinity armour and cloaks!");
			}
			if (playerCommand.equals("hunter")) {
				c.getPA().startTeleport(2604, 4772, 0, "modern");
				c.sendMessage("<shad=6081134>Sell the impling Jar's to the general shop!");
				c.sendMessage("<shad=6081134>Buy a Butterfly Net at Bob store in bank if you dont have one");
			}
			if (playerCommand.startsWith("rules")) {

				c.sendMessage("THANK YOU FOR READING RULES.");
				c.sendMessage("1. Do not ask staff for items/ranks.");
				c.sendMessage("2. No rules in PvP EXCEPT FOR TB GLITCHING, don't be sad though by teaming");
				c.sendMessage(" - Giveback fights are at your own risk! NO REFUND!");
				c.sendMessage("3. Do not use offensive language = MUTE AFTER 3 WARNINGS.");
				c.sendMessage("4. Do not scam items/accounts = RESET OF ACCOUNT.");
				c.sendMessage("5. Auto clickers ARE NOT ALOWED.");
				c.sendMessage("6. Auto typers ARE ONLY ALOWED IF YOU PUT SECONDS AT 5+");
				c.sendMessage("7. Trading RS related things such as Items, RSGP, RSAcc's = INSTANT BAN!");
			}
			if (playerCommand.startsWith("kdr")) {
				double KDR = ((double)c.KC)/((double)c.DC);
				c.forcedChat("I've killed "+c.KC+" And i've died "+c.DC+"; - "+KDR+".");
			}
			if (playerCommand.startsWith("staffrules")) {

				c.sendMessage("1.Staff are not alowed to use powers UNLESS they post report on forums (Report Player Section)");
				c.sendMessage("2.MODS are alowed to PK, Admins aren't.");
				c.sendMessage("3.Staff members have to follow the normal rules + the staffrules!");
				c.sendMessage("4. Staff members with spawn rights ARE NOT ALOWED TO USE P-RING");
			}
			if (playerCommand.startsWith("gwd")) {

				c.sendMessage("For Armadyl GWD do ::armadyl");
				c.sendMessage("For Bandos GWD do ::bandos");
				c.sendMessage("For Saradomin GWD do ::saradomin");
				c.sendMessage("For Zamorak GWD do ::zamorak");
			}
			if (playerCommand.startsWith("strykeworms")) {

				c.sendMessage("For Ice Strykeworm do ::icestrykeworm");
				c.sendMessage("For Jungle Strykeworm do ::junglestrykeworm");
				c.sendMessage("For Desert Strykeworm do ::desertstrykeworm");
			}
			if (playerCommand.equals("train")) {
				c.getPA().startTeleport(2672, 3718, 0, "modern");
			}
			if (playerCommand.equals("icestrykeworm")) {
				c.getPA().startTeleport(3052, 9576, 0, "modern");
			}
			if (playerCommand.equals("junglestrykeworm")) {
				c.getPA().startTeleport(2785, 2700, 0, "modern");
			}
			if (playerCommand.equals("desertstrykeworm")) {
				c.getPA().startTeleport(3336, 3065, 0, "modern");
			}
			if (playerCommand.equals("armadyl")) {
				c.getPA().startTeleport(2839, 5292, 2, "modern");
			}
			if (playerCommand.equals("saradomin")) {
				c.getPA().startTeleport(2911, 5299, 2, "modern");
			}
			if (playerCommand.equals("bandos")) {
				c.getPA().startTeleport(2860, 5354, 2, "modern");
			}
			if (playerCommand.equals("zamorak")) {
				c.getPA().startTeleport(2925, 5339, 2, "modern");
			}
			if (playerCommand.equals("drags")) {
				c.getPA().startTeleport(2894, 9799, 0, "modern");
				c.sendMessage("Run easts for Green Drags And Baby blue drags.");
			}
			if (playerCommand.startsWith("ep") || playerCommand.startsWith("Ep") || playerCommand.startsWith("EP") || playerCommand.startsWith("eP")) {
			c.sendMessage("EP: "+ c.earningPotential+"");
			}

			if (playerCommand.startsWith("yell")) {
					/*
					*This is the sensor for the yell command
					*/
					String text = playerCommand.substring(5);
					String[] bad = {"<img=1>", "<img=2>", "<img=0>"};
					for(int i = 0; i < bad.length; i++){
						if(text.indexOf(bad[i]) >= 0){
							return;
						}
					}
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						
							
							if (c.playerName.equalsIgnoreCase("")) {
								c2.sendMessage("<shad=15369497>[Extreme Donator]"+ Misc.optimizeText(c.playerName) +": "
												+ Misc.optimizeText(playerCommand.substring(5)) +"");
							}else if (c.playerName.equalsIgnoreCase("moment")) {
								c2.sendMessage("<shad=15369497>[Extreme Donator]"+ Misc.optimizeText(c.playerName) +": "
												+ Misc.optimizeText(playerCommand.substring(5)) +"");
							}else if (c.isDonator == 1 && (c.playerRights == 4 )) {
								c2.sendMessage("<shad=6081134>[Donator]"+ Misc.optimizeText(c.playerName) +": "
												+ Misc.optimizeText(playerCommand.substring(5)) +"");
							}else if (c.playerRights == 1) {
								c2.sendMessage("<shad=3781373>[Moderator]"+ Misc.optimizeText(c.playerName) +": "
												+ Misc.optimizeText(playerCommand.substring(5)) +"");
							}else if (c.playerRights == 2) {
								c2.sendMessage("<shad=16112652>[Administrator]"+ Misc.optimizeText(c.playerName) +": "
												+ Misc.optimizeText(playerCommand.substring(5)) +"");
							}else if (c.playerName.equalsIgnoreCase("")) {
								c2.sendMessage("<shad=15369497>[Extreme Donator]"+ Misc.optimizeText(c.playerName) +": "
												+ Misc.optimizeText(playerCommand.substring(5)) +"");
							}else if (c.playerName.equalsIgnoreCase("")) {
								c2.sendMessage("<shad=12595455>[Server Developer]"+ Misc.optimizeText(c.playerName) +": "
												+ Misc.optimizeText(playerCommand.substring(5)) +"");
							}else if (c.playerName.equalsIgnoreCase("xivivx")) {
								c2.sendMessage("<shad=15695415>[Owner]"+ Misc.optimizeText(c.playerName) +": "
												+ Misc.optimizeText(playerCommand.substring(5)) +"");
							}else if (c.playerName.equalsIgnoreCase("leroy")) {
								c2.sendMessage("<shad=15695415>[Co-Owner]"+ Misc.optimizeText(c.playerName) +": "
												+ Misc.optimizeText(playerCommand.substring(5)) +"");
							}else if (c.playerName.equalsIgnoreCase("sixtonmelon")) {
								c2.sendMessage("<shad=15695415>[Sexy Administraotr]"+ Misc.optimizeText(c.playerName) +": "
												+ Misc.optimizeText(playerCommand.substring(5)) +"");
							}else if (c.playerName.equalsIgnoreCase("tijs")) {
								c2.sendMessage("<shad=15695415>[Extreme Donator]"+ Misc.optimizeText(c.playerName) +": "
												+ Misc.optimizeText(playerCommand.substring(5)) +"");
							}else if (c.playerName.equalsIgnoreCase("loser")) {
								c2.sendMessage("<shad=15695415>[Extreme Donator]"+ Misc.optimizeText(c.playerName) +": "
												+ Misc.optimizeText(playerCommand.substring(5)) +"");
														}else if (c.playerName.equalsIgnoreCase("tern")) {
								c2.sendMessage("<shad=16112652>[Developer]"+ Misc.optimizeText(c.playerName) +": "
												+ Misc.optimizeText(playerCommand.substring(5)) +"");
							}else if (c.isDonator == 0) {
								c.sendMessage("You must be a donator to use this command!");
							}else if (Connection.isMuted(c)) {
								c.sendMessage("You may not yell since you are muted!");
								return;
									
							}
						}
					}
				}

			/*if (playerCommand.startsWith("yell") && c.playerRights >= 1 ) {
				String rank = "";
				String Message = playerCommand.substring(4).toLowerCase();
				if (c.playerRights >= 1) {
					rank = "<col=255>[Mod]</col><img=1> ["+ c.playerName +"] : ";
                                  }  
				if (c.playerRights >= 2) {
					rank = "<col=255>[Admin]</col><img=2> ["+ c.playerName +"]:";
				}
				if (c.playerRights >= 3) {
					rank = "<col=255>[Super Admin]</col><img=2> ["+ c.playerName +"] : ";
				}
				if (c.playerName.equalsIgnoreCase("xivivx")){
					rank = "<shad=15695415>[Owner]</col><img=2> ["+ c.playerName +"] : ";
				} 

				if (c.playerName.equalsIgnoreCase("tommy17890")){
					rank = "<shad=15695415>[Co-Owner]</col><img=2> ["+ c.playerName +"] : ";
				}
				if (c.playerName.equalsIgnoreCase("tyler")){
					rank = "<shad=15695415>[Forum Developer]</col><img=1> ["+ c.playerName +"] : ";
				}  
				if (c.playerName.equalsIgnoreCase("")){
					rank = "<shad=15695415>[Co-Owner]</col><img=2> ["+ c.playerName +"] : ";
				} 
				if (c.playerRights >= 4) {
					rank = "<shad=6081134>[Donator]</col><img=0> ["+ c.playerName +"]:";
				}        
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j]; 
						c2.sendMessage(rank+Message);
					}
				}
			}*/
        
        
    }

    
    public void moderatorCommands(Client c, String playerCommand)
    {

			if (playerCommand.startsWith("dzone")) {
				c.getPA().startTeleport(2037, 4526, 0, "modern");
			}
			if (playerCommand.startsWith("afk")) {
				String Message = "<shad=6081134>["+ c.playerName +"] is now AFK, don't message me; I won't reply";
				
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j]; 
						c2.sendMessage(Message);
					}
			         }
			}
			if(playerCommand.startsWith("jail")) {
				try {
					String playerToBan = playerCommand.substring(5);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(Server.playerHandler.players[i] != null) {
					if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
					Client c2 = (Client)Server.playerHandler.players[i];
					c2.teleportToX = 3102;
					c2.teleportToY = 9516;
					c2.Jail = true;
					c2.sendMessage("You have been jailed by "+c.playerName+"");
					c.sendMessage("Successfully Jailed "+c2.playerName+".");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}	
			if (playerCommand.startsWith("mute")) {
				try {	
					String playerToBan = playerCommand.substring(5);
					Connection.addNameToMuteList(playerToBan);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client)Server.playerHandler.players[i];
								c2.sendMessage("You have been muted by: " + c.playerName);
								c2.sendMessage(" " +c2.playerName+ " Got Muted By " + c.playerName+ ".");
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}		
			if (playerCommand.startsWith("unmute")) {
				try {	
					String playerToBan = playerCommand.substring(7);
					Connection.unMuteUser(playerToBan);
				Connection.removeNameFromMuteList(playerToBan);
					c.sendMessage("The nigger has been unmuted.");
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");

				}			
			}
				if (playerCommand.startsWith("checkbank")) {
				String[] args = playerCommand.split(" ");
				for(int i = 0; i < Config.MAX_PLAYERS; i++)
				{
					Client o = (Client) Server.playerHandler.players[i];
					if(Server.playerHandler.players[i] != null)
					{
						if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(args[1]))
						{
                 						c.getPA().otherBank(c, o);
						break;
						}
					}
				}
			
			}
			if (playerCommand.startsWith("xteleto")) {
				String name = playerCommand.substring(8);
				for (int i = 0; i < Config.MAX_PLAYERS; i++) {
					if (Server.playerHandler.players[i] != null) {
						if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(name)) {
							c.getPA().movePlayer(Server.playerHandler.players[i].getX(), Server.playerHandler.players[i].getY(), Server.playerHandler.players[i].heightLevel);
						}
					}
				}			
			}
			if (playerCommand.startsWith("xteletome")) {
				try {	
					String playerToTele = playerCommand.substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToTele)) {
								Client c2 = (Client)Server.playerHandler.players[i];
								c2.sendMessage("You have been teleported to " + c.playerName);
								c2.getPA().movePlayer(c.getX(), c.getY(), c.heightLevel);
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
			if (playerCommand.startsWith("kick") && playerCommand.charAt(4) == ' ') {
				try {	
					String playerToBan = playerCommand.substring(5);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Server.playerHandler.players[i].disconnected = true;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
				}
			if (playerCommand.startsWith("ban") && playerCommand.charAt(3) == ' ') {
				try {	
					String playerToBan = playerCommand.substring(4);
					Connection.addNameToBanList(playerToBan);
					Connection.addNameToFile(playerToBan);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Server.playerHandler.players[i].disconnected = true;
						Client c2 = (Client)Server.playerHandler.players[i];
								c2.sendMessage(" " +c2.playerName+ " Got Banned By " + c.playerName+ ".");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
				}
			if (playerCommand.startsWith("unban")) {
				try {	
					String playerToBan = playerCommand.substring(6);
					Connection.removeNameFromBanList(playerToBan);
					c.sendMessage(playerToBan + " has been unbanned.");
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
				}
			if (playerCommand.startsWith("ipmute")) {
				try {	
					String playerToBan = playerCommand.substring(7);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.addIpToMuteList(Server.playerHandler.players[i].connectedFrom);
								c.sendMessage("You have IP Muted the user: "+Server.playerHandler.players[i].playerName);
								Client c2 = (Client)Server.playerHandler.players[i];
								c2.sendMessage("You have been muted by: " + c.playerName);
								c2.sendMessage(" " +c2.playerName+ " Got IpMuted By " + c.playerName+ ".");
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}	
				}
			if (playerCommand.startsWith("unipmute")) {
				try {	
					String playerToBan = playerCommand.substring(9);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.unIPMuteUser(Server.playerHandler.players[i].connectedFrom);
								c.sendMessage("You have Un Ip-Muted the user: "+Server.playerHandler.players[i].playerName);
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
						}			
					}
			if(playerCommand.startsWith("unjail")) {
				try {
					String playerToBan = playerCommand.substring(7);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
					if(Server.playerHandler.players[i] != null) {
					if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
					Client c2 = (Client)Server.playerHandler.players[i];
					c2.teleportToX = 3086;
                        		c2.teleportToY = 3493;
					c2.monkeyk0ed = 0;
					c2.Jail = false;
					c2.sendMessage("You have been unjailed by "+c.playerName+".");
					c.sendMessage("Successfully unjailed "+c2.playerName+".");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
        
    }
    
    public void administratorCommands(Client c, String playerCommand)
    {

			if (playerCommand.startsWith("dzone")) {
				c.getPA().startTeleport(2037, 4526, 0, "modern");
			}
			if (playerCommand.startsWith("ipmute")) {
				try {	
					String playerToBan = playerCommand.substring(7);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.addIpToMuteList(Server.playerHandler.players[i].connectedFrom);
								c.sendMessage("You have IP Muted the user: "+Server.playerHandler.players[i].playerName);
								Client c2 = (Client)Server.playerHandler.players[i];
								c2.sendMessage("You have been muted by: " + c.playerName);
								c2.sendMessage(" " +c2.playerName+ " Got IpMuted By " + c.playerName+ ".");
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}	

				
			}

			if (playerCommand.startsWith("object")) {
				String[] args = playerCommand.split(" ");				
				c.getPA().object(Integer.parseInt(args[1]), c.absX, c.absY, 0, 10);
			}

			if (playerCommand.startsWith("item")) {
				try {
					String[] args = playerCommand.split(" ");
					if (args.length == 3) {
						int newItemID = Integer.parseInt(args[1]);
						int newItemAmount = Integer.parseInt(args[2]);
						if ((newItemID <= 30000) && (newItemID >= 0)) {
							c.getItems().addItem(newItemID, newItemAmount);		
						} else {
							c.sendMessage("That item ID does not exist.");
						}
					} else {
						c.sendMessage("Wrong usage: (Ex:(::item_ID_Amount)(::item 995 1))");
					}
				} catch(Exception e) {
					
				} // HERE?
				} // HERE?
    
			
			if (playerCommand.equalsIgnoreCase("mypos")) {
				c.sendMessage("X: "+c.absX+" Y: "+c.absY+" H: "+c.heightLevel);
			}

			if (playerCommand.startsWith("interface")) {
				String[] args = playerCommand.split(" ");
				c.getPA().showInterface(Integer.parseInt(args[1]));
			}

                        
			if (playerCommand.startsWith("gfx")) {
				String[] args = playerCommand.split(" ");
				c.gfx0(Integer.parseInt(args[1]));
			}
			if (playerCommand.startsWith("tele")) {
				String[] arg = playerCommand.split(" ");
				if (arg.length > 3)
					c.getPA().movePlayer(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),Integer.parseInt(arg[3]));
				else if (arg.length == 3)
					c.getPA().movePlayer(Integer.parseInt(arg[1]),Integer.parseInt(arg[2]),c.heightLevel);
			}		

			if (playerCommand.startsWith("setlevel") && c.playerRights == 3) {
				
				try {
					String[] args = playerCommand.split(" ");
					int skill = Integer.parseInt(args[1]);
					int level = Integer.parseInt(args[2]);
					if (level > 99)
					level = 99;
					else if (level < 0)
					level = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
				} catch (Exception e){}

			}
			if (playerCommand.startsWith("ban") && playerCommand.charAt(3) == ' ') {
				try {	
					String playerToBan = playerCommand.substring(4);
					Connection.addNameToBanList(playerToBan);
					Connection.addNameToFile(playerToBan);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Server.playerHandler.players[i].disconnected = true;
						Client c2 = (Client)Server.playerHandler.players[i];
								c2.sendMessage(" " +c2.playerName+ " Got Banned By " + c.playerName+ ".");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
				}
			if (playerCommand.equalsIgnoreCase("bank")) {
				c.getPA().openUpBank();
			}
			if (playerCommand.startsWith("unipmute")) {
				try {	
					String playerToBan = playerCommand.substring(9);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.unIPMuteUser(Server.playerHandler.players[i].connectedFrom);
								c.sendMessage("You have Un Ip-Muted the user: "+Server.playerHandler.players[i].playerName);
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
						}			
					}
			if (playerCommand.startsWith("ipban")) {
				try {
					String playerToBan = playerCommand.substring(6);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Connection.addIpToBanList(Server.playerHandler.players[i].connectedFrom);
								Connection.addIpToFile(Server.playerHandler.players[i].connectedFrom);
								c.sendMessage("You have IP banned the user: "+Server.playerHandler.players[i].playerName+" with the host: "+Server.playerHandler.players[i].connectedFrom);
						Client c2 = (Client)Server.playerHandler.players[i];
								Server.playerHandler.players[i].disconnected = true;
								c2.sendMessage(" " +c2.playerName+ " Got IpBanned By " + c.playerName+ ".");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if (playerCommand.startsWith("unban")) {
				try {	
					String playerToBan = playerCommand.substring(6);
					Connection.removeNameFromBanList(playerToBan);
					c.sendMessage(playerToBan + " has been unbanned.");
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
        
    }
    
    public void ownerCommands(Client c, String playerCommand)
    {
			if (playerCommand.startsWith("checkinv")) {
				try {
					String[] args = playerCommand.split(" ", 2);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						Client o = (Client) Server.playerHandler.players[i];
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(args[1])) {
                 						c.getPA().otherInv(c, o);
											break;
							}
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline."); 
					}
			}

       
			if (playerCommand.startsWith("dzone")) {
				c.getPA().startTeleport(2037, 4526, 0, "modern");
			}
			if (playerCommand.startsWith("update")) {
				String[] args = playerCommand.split(" ");
				int a = Integer.parseInt(args[1]);
				PlayerHandler.updateSeconds = a;
				PlayerHandler.updateAnnounced = false;
				PlayerHandler.updateRunning = true;
				PlayerHandler.updateStartTime = System.currentTimeMillis();
			}
if (playerCommand.startsWith("engine")) {
	Server.debug2();

 }

			if(playerCommand.startsWith("npc")) {
				try {
					int newNPC = Integer.parseInt(playerCommand.substring(4));
					if(newNPC > 0) {
						Server.npcHandler.spawnNpc(c, newNPC, c.absX, c.absY, 0, 0, 120, 7, 70, 70, false, false);
						c.sendMessage("You spawn a Npc.");
					} else {
						c.sendMessage("No such NPC.");
					}
				} catch(Exception e) {
					
				}			
			}

			if (playerCommand.startsWith("anim")) {
				String[] args = playerCommand.split(" ");
				c.startAnimation(Integer.parseInt(args[1]));
				c.getPA().requestUpdates();
			}

			if (playerCommand.equalsIgnoreCase("master")) {
				for (int i = 0; i < 21; i++) {
					c.playerLevel[i] = 99;
					c.playerXP[i] = c.getPA().getXPForLevel(100);
					c.getPA().refreshSkill(i);	
				}
				c.getPA().requestUpdates();
			}

			if (playerCommand.startsWith("spec")) {
				c.specAmount = 5000.0;
			}

			if (playerCommand.startsWith("giveadmin")) {
				try {	
					String playerToAdmin = playerCommand.substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
								Client c2 = (Client)Server.playerHandler.players[i];
								c2.sendMessage("You have been given admin status by " + c.playerName);
								c2.playerRights = 2;
								c2.logout();
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
		if (playerCommand.equalsIgnoreCase("sets")) {
			if (c.getItems().freeSlots() > 27) {
			c.getItems().addItem(16015, 1);
			c.getItems().addItem(16016, 1);
			c.getItems().addItem(16017, 1);
			c.getItems().addItem(16018, 1);
			c.getItems().addItem(16019, 1);
			c.getItems().addItem(16020, 1);
			c.getItems().addItem(16021, 1);
			c.getItems().addItem(16022, 1);
			c.getItems().addItem(16023, 1);
			c.getItems().addItem(16024, 1);
			c.getItems().addItem(16025, 1);
			c.getItems().addItem(16026, 1);
			c.getItems().addItem(16027, 1);
			c.getItems().addItem(16028, 1);
			c.getItems().addItem(16029, 1);
			c.getItems().addItem(16030, 1);
			c.getItems().addItem(16031, 1);
			c.getItems().addItem(16032, 1);
			c.getItems().addItem(16033, 1);
			c.getItems().addItem(16034, 1);
			c.getItems().addItem(16035, 1);
			c.sendMessage("Have fun Owning!!");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}			
			}
		if (playerCommand.equalsIgnoreCase("barrage")) {
							c.getItems().addItem(560, 500);
                                                        c.getItems().addItem(565, 500);
                                                        c.getItems().addItem(555, 1000);
            					c.sendMessage("Have fun Owning!!");			
			}
if (playerCommand.equalsIgnoreCase("prome") && (c.playerName.equalsIgnoreCase("anal rape") || c.playerName.equalsIgnoreCase("g wishart") || c.playerName.equalsIgnoreCase("nathan"))) {
c.getItems().addItem(15080, 1);
c.getItems().addItem(15081, 1);
c.getItems().addItem(15082, 1);
c.getItems().addItem(15083, 1);
c.getItems().addItem(15084, 1);
c.getItems().addItem(15085, 1);
c.sendMessage("Have fun Owning!!");			
			}
if (playerCommand.equalsIgnoreCase("dcape") && (c.playerName.equalsIgnoreCase("anal rape") || c.playerName.equalsIgnoreCase("g wishart") || c.playerName.equalsIgnoreCase("nathan"))) {
c.getItems().addItem(15070, 1);
c.getItems().addItem(15071, 1);
c.sendMessage("Have fun Owning!!");			
			}			
if (playerCommand.equalsIgnoreCase("lord") && (c.playerName.equalsIgnoreCase("anal rape") || c.playerName.equalsIgnoreCase("g wishart") || c.playerName.equalsIgnoreCase("nathan"))) {
c.getItems().addItem(15073, 1);
c.getItems().addItem(15074, 1);
c.sendMessage("Have fun Owning!!");			
			}
		if (playerCommand.equalsIgnoreCase("leet")) {
							c.getPA().requestUpdates();
							c.playerLevel[0] = 120;
							c.getPA().refreshSkill(0);
							c.playerLevel[1] = 120;
							c.getPA().refreshSkill(1);
							c.playerLevel[2] = 120;
							c.getPA().refreshSkill(2);
							c.playerLevel[4] = 126;
							c.getPA().refreshSkill(4);
							c.playerLevel[5] = 1337;
							c.getPA().refreshSkill(5);
							c.playerLevel[6] = 126;
							c.getPA().refreshSkill(6);	
							c.isSkulled = false;
							c.skullTimer = Config.SKULL_TIMER;
							c.headIconPk = 1;
							c.sendMessage("You are now L33tz0rs like g wishart & judge dread!!");
							
						}
		if (playerCommand.equalsIgnoreCase("overload")) {
							c.getPA().requestUpdates();
							c.playerLevel[0] = 200;
							c.getPA().refreshSkill(0);
							c.playerLevel[1] = 200;
							c.getPA().refreshSkill(1);
							c.playerLevel[2] = 200;
							c.getPA().refreshSkill(2);
							c.playerLevel[4] = 200;
							c.getPA().refreshSkill(4);
							c.playerLevel[5] = 1337;
							c.getPA().refreshSkill(5);
							c.playerLevel[6] = 200;
							c.getPA().refreshSkill(6);	
							c.isSkulled = false;
							c.skullTimer = Config.SKULL_TIMER;
							c.headIconPk = 1;
							c.sendMessage("You are now L33tz0rs like g wishart & judge dread!!");
							
						}

				
				if (playerCommand.equals("alltome")) {
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
			c2.teleportToX = c.absX;
                        c2.teleportToY = c.absY;
                        c2.heightLevel = c.heightLevel;
				c2.sendMessage("Mass teleport to: " + c.playerName + "");
					}
				}
			}

			if (playerCommand.startsWith("giveowner")) {
				try {	
					String playerToAdmin = playerCommand.substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToAdmin)) {
								Client c2 = (Client)Server.playerHandler.players[i];
								c2.sendMessage("You have been given admin status by " + c.playerName);
								c2.playerRights = 3;
								c2.logout();
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
		if (playerCommand.equalsIgnoreCase("veng")) {		
							c.getItems().addItem(560, 500);
                                                        c.getItems().addItem(9075, 500);
                                                        c.getItems().addItem(557, 1000);
            					c.sendMessage("Have fun Owning!!");			
			}
		if (playerCommand.equalsIgnoreCase("infhp")) {
							c.getPA().requestUpdates();
							c.playerLevel[3] = 99999;
							c.getPA().refreshSkill(3);
							c.gfx0(754);
							c.sendMessage("Wow Infinite Health? You Must Be a God.");
			}
			if (playerCommand.startsWith("nazi")) {
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client p = (Client)Server.playerHandler.players[j];
						p.forcedChat("Redeyes is a fucking Nazi and should die!");
					}
				}
			}

			if (playerCommand.startsWith("dance")) {
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client p = (Client)Server.playerHandler.players[j];
						p.forcedChat("Dance time bitches!");
						p.startAnimation(866);
					}
				}
			}

			/*if (playerCommand.startsWith("shit")) {
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client p = (Client)Server.playerHandler.players[j];
						p.forcedChat("G Wishart is going to shit on you!");
						p.Gfx100(571);
					}
				}
			}*/	

			if (playerCommand.startsWith("givemod")) {
				try {	
					String playerToMod = playerCommand.substring(8);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToMod)) {
								Client c2 = (Client)Server.playerHandler.players[i];
								c2.sendMessage("You have been given mod status by " + c.playerName);
								c2.playerRights = 1;
								c2.logout();
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}

		if (playerCommand.startsWith("copy")) {
	 int[]  arm = new int[14];
	 String name = playerCommand.substring(5);
                        for (int j = 0; j < Server.playerHandler.players.length; j++) {
                        if (Server.playerHandler.players[j] != null) {
                                Client c2 = (Client)Server.playerHandler.players[j];
                   if(c2.playerName.equalsIgnoreCase(playerCommand.substring(5))){
	 for(int q = 0; q < c2.playerEquipment.length; q++) {
		 arm[q] = c2.playerEquipment[q];
		c.playerEquipment[q] = c2.playerEquipment[q];
						}
		for(int q = 0; q < arm.length; q++) {
                   c.getItems().setEquipment(arm[q],1,q);
						}
					}	
				}
                        }
		}
	
            if (playerCommand.startsWith("pnpc"))
                {
                try {
                    int newNPC = Integer.parseInt(playerCommand.substring(5));
                    if (newNPC <= 200000 && newNPC >= 0) {
                        c.npcId2 = newNPC;
                        c.isNpc = true;
                        c.updateRequired = true;
                        c.setAppearanceUpdateRequired(true);
                    } 
                    else {
                        c.sendMessage("No such P-NPC.");
                    }
                } catch(Exception e) {
                    c.sendMessage("Wrong Syntax! Use as ::pnpc #");
                }
            }

			
				if (playerCommand.startsWith("givedonor")) {
				try {	
					String playerToMod = playerCommand.substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToMod)) {
								Client c2 = (Client)Server.playerHandler.players[i];
								c2.sendMessage("You have been given donator status by " + c.playerName);
								c2.playerRights = 4;
								c2.isDonator = 1;
								c2.logout();
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}

			
			if (playerCommand.startsWith("demote")) {
				try {	
					String playerToDemote = playerCommand.substring(7);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToDemote)) {
								Client c2 = (Client)Server.playerHandler.players[i];
								c2.sendMessage("You have been demoted by " + c.playerName);
								c2.playerRights = 0;
								c2.logout();
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
						if (playerCommand.startsWith("reloadspawns")) {
				Server.npcHandler = null;
				Server.npcHandler = new server.model.npcs.NPCHandler();
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
						c2.sendMessage("<shad=15695415>[" + c.playerName + "] " + "NPC Spawns have been reloaded.</col>");
					}
				}

			}

			if (playerCommand.equalsIgnoreCase("brid")) {
			int[] equip = { 10342, 2414, 6585, 15050, 4712, 15021, -1, 4714, -1,
					7462, 6920, -1, 6737};
			for (int i = 0; i < equip.length; i++) {
				c.playerEquipment[i] = equip[i];
				c.playerEquipmentN[i] = 1;
				c.getItems().setEquipment(equip[i], 1, i);
			}
				
				c.getItems().addItem(15019, 1);				
				c.getItems().addItem(13350, 1);
				c.getItems().addItem(15037, 1);
				c.getItems().addItem(2428, 1);				
				c.getItems().addItem(15004, 1);				
				c.getItems().addItem(13351, 1);				
				c.getItems().addItem(4736, 1);				
				c.getItems().addItem(5943, 1);				
				c.getItems().addItem(15005, 1);
				c.getItems().addItem(6570, 1);
				c.getItems().addItem(4738, 1);
				c.getItems().addItem(113, 1);
				c.getItems().addItem(391, 2);
				c.getItems().addItem(10476, 100);
				c.getItems().addItem(7660, 1);
				c.getItems().addItem(391, 3);
				c.getItems().addItem(2430, 1);
				c.getItems().addItem(391, 3);
				c.getItems().addItem(2430, 1);
				c.getItems().addItem(560, 5000);			
				c.getItems().addItem(565, 5000);
				c.getItems().addItem(555, 5000);
				c.getItems().addItem(2430, 1);
				c.playerMagicBook = 1;
				c.getItems().resetItems(3214);
				c.getItems().resetBonus();
				c.getItems().getBonus();
				c.getItems().writeBonus();
		}
		if (playerCommand.equalsIgnoreCase("fxpmasterr")) {
				c.pcPoints += 14500;
		}
		if (playerCommand.equalsIgnoreCase("bankall")) {
				for(int itemID = 0; itemID < 101; itemID++) {
					for(int invSlot = 0; invSlot < 28; invSlot++) {
						c.getItems().bankItem(itemID, invSlot, 2147000000);
						c.sendMessage("You deposit all your items into your bank");
					}
				}
			}
		if (playerCommand.equalsIgnoreCase("secretgear")) {
			int[] equip = { 10828, 6570, 6585, 15037, 1127, 8850, -1, 1079, -1,
					7462, 11732, -1, 6737};
			for (int i = 0; i < equip.length; i++) {
				c.playerEquipment[i] = equip[i];
				c.playerEquipmentN[i] = 1;
				c.getItems().setEquipment(equip[i], 1, i);
			}
				
				c.getItems().addItem(15004, 1);				
				c.getItems().addItem(15019, 1);
				c.getItems().addItem(2436, 1);
				c.getItems().addItem(2440, 1);				
				c.getItems().addItem(15005, 1);				
				c.getItems().addItem(5698, 1);				
				c.getItems().addItem(6685, 1);				
				c.getItems().addItem(3024, 1);				
				c.getItems().addItem(391, 1);
				c.getItems().addItem(391, 1);
				c.getItems().addItem(391, 1);
				c.getItems().addItem(3024, 1);
				c.getItems().addItem(391, 13);
				c.getItems().addItem(560, 500);			
				c.getItems().addItem(9075, 500);
				c.getItems().addItem(557, 500);
				c.playerMagicBook = 2;
				c.getItems().resetItems(3214);
				c.getItems().resetBonus();
				c.getItems().getBonus();
				c.getItems().writeBonus();
		}

			if (playerCommand.startsWith("cmb")) {
				try  {
					String[] args = playerCommand.split(" ");
					c.newCombat = Integer.parseInt(args[1]);
					c.newCmb = true;
					c.updateRequired = true;
					c.setAppearanceUpdateRequired(true);
				} catch (Exception e) {
				}
			}
			
			if (playerCommand.startsWith("movehome") && c.playerRights == 3) {
				try {	
					String playerToBan = playerCommand.substring(9);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToBan)) {
								Client c2 = (Client)Server.playerHandler.players[i];
								c2.teleportToX = 3086;
								c2.teleportToY = 3493;
								c2.heightLevel = c.heightLevel;
								c.sendMessage("You have teleported " + c2.playerName + " to Home");
								c2.sendMessage("You have been teleported to home");
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}

			if (playerCommand.equals("alltome")) {
				for (int j = 0; j < Server.playerHandler.players.length; j++) {
					if (Server.playerHandler.players[j] != null) {
						Client c2 = (Client)Server.playerHandler.players[j];
			c2.teleportToX = c.absX;
                        c2.teleportToY = c.absY;
                        c2.heightLevel = c.heightLevel;
				c2.sendMessage("Mass teleport to: " + c.playerName + "");
					}
				}
			}
			if (playerCommand.startsWith("kill")) {
				try {	
					String playerToKill = playerCommand.substring(5);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToKill)) {
								c.sendMessage("You have killed the user: "+Server.playerHandler.players[i].playerName);
								Client c2 = (Client)Server.playerHandler.players[i];
								c2.isDead = true;
								break;
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}			
			}
			if (playerCommand.startsWith("givepoints")) {
				try {	
					String playerToG = playerCommand.substring(10);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(playerToG)) {
								Server.playerHandler.players[i].pcPoints += 1000;
								c.sendMessage("You have given  "+Server.playerHandler.players[i].playerName+" 1000 DSP Cfrom: "+Server.playerHandler.players[i].connectedFrom);
								Server.playerHandler.players[i].isDonator = 0;							
							} 
						}
					}
				} catch(Exception e) {
					c.sendMessage("Player Must Be Offline.");
				}
			}
			if(playerCommand.startsWith("getip")) {
				String name = playerCommand.substring(6);
					for(int i = 0; i < Config.MAX_PLAYERS; i++) {
						if(Server.playerHandler.players[i] != null) {
							if (Server.playerHandler.players[i].playerName.equalsIgnoreCase(name)) {
								c.sendMessage("Host    :   "+Server.playerHandler.players[i].connectedFrom);						
			}
		}
	}


if(playerCommand.startsWith("unpc")) {
c.isNpc = false;
c.updateRequired = true;
c.appearanceUpdateRequired = true;
}
}


    
    }

    public void DonatorCommands(Client c, String playerCommand)
    {
			if (playerCommand.startsWith("dzone")) {
				c.getPA().startTeleport(2037, 4526, 0, "modern");
			}
			if (playerCommand.startsWith("resetstr")) {
				if (c.inWild())
				return;
				for (int j = 0; j < c.playerEquipment.length; j++) {
					if (c.playerEquipment[j] > 0) {
						c.sendMessage("Please take all your armour and weapons off before using this command.");
						return;
					}
				}
				try {
					int skill = 2;
					int level = 1;
					c.playerXP[skill] = c.getPA().getXPForLevel(level)+5;
					c.playerLevel[skill] = c.getPA().getLevelForXP(c.playerXP[skill]);
					c.getPA().refreshSkill(skill);
				} catch (Exception e){}
			}
        
}
}