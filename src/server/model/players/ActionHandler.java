package server.model.players;

import server.Config;

import server.Server;
import server.util.Misc;
import server.model.objects.Object;
import server.model.players.skills.Mining;
import server.model.players.skills.Woodcutting;
import server.util.ScriptManager;



public class ActionHandler {
	private Client c;
	
	public ActionHandler(Client Client) {
		this.c = Client;
	}

	public void wildyditch() {
		{
	if (c.absY <= c.objectY){
		
		c.startAnimation(6132);
		c.getPA().walkTo(0, +3);
		} else if (c.objectY < c.absY) {
		c.startAnimation(6132);
				c.getPA().walkTo(0, -3);
				} 
		}
		}
	
	public void firstClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		
		if(Mining.miningRocks(c, objectType)) {
			Mining.attemptData(c, objectType, obX, obY);
			return;
		} 
		if(Woodcutting.playerTrees(c, objectType)) {
			Woodcutting.attemptData(c, objectType, obX, obY);
			return;
		}
		switch(objectType) {		
		case 1765:
			c.getPA().movePlayer(2271, 4680, 0);
		break;
		case 481:
			c.getPA().showInterface(802);
			break;
		
		case 4132:
		c.sendMessage("On the signpost you manage to read.... YOUR GAY SO GO VOTE!");
		break;
		case 27669:
		c.sendMessage("I'll let ya in if you go vote 10 times! ::vote ");
		break;
		case 11402:
		case 26972:
		case 27663:
						c.getPA().openUpBank();
	break;
		case 2115:
		//c.playerRights = 3;
                    c.sendMessage("Lol nice try jackass");
		break;
 		case 23271:
		wildyditch();
		break;
		case 27254:
			c.getPA().movePlayer(2576, 9893, 0);
			c.sendMessage("The queen drops barbarien assult items such as fighter helmets");
			c.sendMessage("NOTE: SHE HAS 282 HP (3 WAVES hp bar) ");
			break;
		

				case 2295:
					c.getAgil().AgilityLog(c, "Log", 1, 0, -7, 2474, 3436, 60);
					c.ag1 = 1;
					break;
				case 2285:
					c.startAnimation(828);
					c.getAgil().AgilityNet(c, "Net", 1, 2471, 3426, 1, 2471, 3424, 828, 60);
					c.getAgil().AgilityNet(c, "Net", 1, 2472, 3426, 1, 2472, 3424, 828, 60);
					c.getAgil().AgilityNet(c, "Net", 1, 2473, 3426, 1, 2473, 3424, 828, 60);
					c.getAgil().AgilityNet(c, "Net", 1, 2474, 3426, 1, 2474, 3424, 828, 60);
					c.getAgil().AgilityNet(c, "Net", 1, 2475, 3426, 1, 2475, 3424, 828, 60);
					c.getAgil().AgilityNet(c, "Net", 1, 2476, 3426, 1, 2476, 3424, 828, 60);
					c.ag2 = 1;
					break;

				case 2313:
					c.startAnimation(828);
					c.getAgil().AgilityBranch(c, "Branch", 1, 2473, 3420, 2, 2473, 3423, 828, 60);
					c.getAgil().AgilityBranch(c, "Branch", 1, 2473, 3420, 2, 2474, 3422, 828, 60);
					c.getAgil().AgilityBranch(c, "Branch", 1, 2473, 3420, 2, 2472, 3422, 828, 60);
					c.ag3 = 1;
					break;
				case 2312:
					c.getAgil().AgilityLog(c, "Log", 1, +6, 0, 2477, 3420, 60);
					c.ag4 = 1;
					break;
				case 2314:
					c.startAnimation(828);
					c.sendMessage("You slipped and fell.");
					c.getAgil().AgilityBranch(c, "Branch", 1, 2486, 3420, 0, 2485, 3419, 828, 60);
					c.getAgil().AgilityBranch(c, "Branch", 1, 2486, 3420, 0, 2485, 3420, 828, 60);
					c.getAgil().AgilityBranch(c, "Branch", 1, 2486, 3420, 0, 2486, 3420, 828, 60);
					c.ag5 = 1;
					break;
				case 2286:
					c.startAnimation(828);
					c.getAgil().AgilityNet(c, "Net", 1, 2483, 3425, 0, 2483, 3427, 828, 60);
					c.getAgil().AgilityNet(c, "Net", 1, 2484, 3425, 0, 2484, 3427, 828, 60);
					c.getAgil().AgilityNet(c, "Net", 1, 2485, 3425, 0, 2485, 3427, 828, 60);
					c.getAgil().AgilityNet(c, "Net", 1, 2486, 3425, 0, 2486, 3427, 828, 60);
					c.getAgil().AgilityNet(c, "Net", 1, 2487, 3425, 0, 2487, 3427, 828, 60);
					c.getAgil().AgilityNet(c, "Net", 1, 2488, 3425, 0, 2488, 3427, 828, 60);
					c.ag6 = 1;
					c.getAgil().bonus = true;
					break;
				case 154:
					c.fmwalkto(0, 1);
					c.startAnimation(749);
					c.getAgil().AgilityPipe(c, "Pipe", 1, 2484, 3430, 0, +7, 2996, 10, 60);
					break;
				case 4058:
					c.fmwalkto(0, 1);
					c.startAnimation(749);
					c.getAgil().AgilityPipe(c, "Pipe", 1, 2487, 3430, 0, +7, 2996, 10, 60);
					break;
		case 30941:
		//c.playerRights = 3;
                    c.sendMessage("Lol nice try jackass.");
		break;

			case 26288:
			case 26287:
			case 26286:
			case 26289:	
			case 27661:			
				if(c.gwdelay > 1) {
				c.sendMessage("You can only do this once every 5 minute!");
				return;
				}
		if(c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
				c.startAnimation(645);
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.sendMessage("You recharge your prayer points.");
				c.getPA().refreshSkill(5);
				c.gwdelay = 600;
c.gwdEvent();
			} else {
				c.sendMessage("You already have full prayer points.");
			}
break;	
			
			case 9356:
				c.getPA().enterCaves();
			break;

		case 15638:
		c.getPA().movePlayer(2841, 3538, 0);
		break;
				case 411:
			c.sendMessage("(Note: Everything works just like RS!");
			if(c.altarPrayed == 0) {
				c.altarPrayed = 1;
				c.setSidebarInterface(5, 22500);
				c.startAnimation(645);
				c.sendMessage("You sense a surge of power flow through your body!");
				c.getCombat().resetPrayers();
			} else {
				c.altarPrayed = 0;
				c.setSidebarInterface(5, 5608);
				c.startAnimation(645);
				c.sendMessage("You sense a surge of purity flow through your body!");
 				c.getCurse().resetCurse();
			}
		break;

		case 13619:
		c.getPA().movePlayer(2717, 9801, 4);
		c.sendMessage("You teleported to tormented demons donator only NPC's!");
		c.sendMessage("You'll only be able to see Donators here, Sorta like world 2...");
		break;
		case 2913:
			 c.sendMessage("Your account has been succesfully saved!!");
			 c.sendMessage("This prevents rollbacks.");
		break;
		case 6452:
			if (c.absX == 3304 && c.absY == 9376) {
		c.getPA().movePlayer(3305, 9376, 4);
		c.sendMessage("Prepare for the strongest monster in the game!");
		c.sendMessage("Note: It has 3 waves on it's hp bar!");
				} else {
			c.autoRet = 0;
			c.getCombat().resetPlayerAttack();
		        c.getPA().movePlayer(3304, 9376, 0);
				}
		break;
		case 6451:
			if (c.absX == 3304 && c.absY == 9375) {
		c.getPA().movePlayer(3305, 9375, 4);
		c.sendMessage("Prepare for the strongest monster in the game!");
		c.sendMessage("Note: It has 3 waves on it's hp bar!");
				} else {
			c.autoRet = 0;
			c.getCombat().resetPlayerAttack();
		        c.getPA().movePlayer(3304, 9375, 0);
				}
		break;
		case 13620:
		c.getPA().movePlayer(2721, 9450, 4);
		c.sendMessage("You teleported to steel/iron donator only NPC's!");
		c.sendMessage("You'll only be able to see Donators here, this makes it alot easier to train.");
		break;
		case 13615:
		c.getPA().movePlayer(3115, 9838, 4);
		c.sendMessage("You teleported to Hill Giants donator only NPC's!");
		c.sendMessage("You'll only be able to see Donators here, this makes it alot easier to train.");
		break;
		case 1738:
		c.getPA().movePlayer(2840, 3539, 2);
		c.startAnimation(828);
		break;


		case 2882:
		case 2883:
			if (c.objectX == 3268) {
				if (c.absX < c.objectX) {
					c.getPA().walkTo(1,0);
				} else {
					c.getPA().walkTo(-1,0);
				}
			}
		break;
		case 272:
			c.getPA().movePlayer(c.absX, c.absY, 1);
		break;
		
		case 273:
			c.getPA().movePlayer(c.absX, c.absY, 0);
		break;

		case 60:
		        c.getPA().movePlayer(3086, 3493, 0);
		break;
		case 26428:
		      if (c.Zammy < 15 && c.absX == 2925 && c.absY == 5332) {
		       c.sendMessage("You need atleast 15 Zamorak KC to enter here!");
		       return;
		       }	
		       if(c.absX == 2925 && c.absY == 5332) {
		        c.getPA().movePlayer(2925, 5331, 6);
		       c.Zammy -= 15;
		       c.sendMessage("A magical force reseted your Zamorak kill count!");
		      }
		        if(c.absX == 2925 && c.absY == 5331) {
		        c.getPA().movePlayer(2925, 5332, 2);
			c.autoRet = 0;
			c.getCombat().resetPlayerAttack();
		      }
		break;
		case 26425:
		      if (c.Band < 15 && c.absX == 2863 && c.absY == 5354) {
		       c.sendMessage("You need atleast 15 Bandos KC to enter here!");
		       return;
		       }	
		      if(c.absX == 2863 && c.absY == 5354) {
	     	    	 c.getPA().movePlayer(2864, 5354, 6);
		       c.Band -= 15;
		       c.sendMessage("A magical force reseted your Bandos kill count!");
		      }
		       if(c.absX == 2864 && c.absY == 5354) {
	     	      c.getPA().movePlayer(2863, 5354, 2);
			c.autoRet = 0;
			c.getCombat().resetPlayerAttack();
		      }
		break;
		case 26303:
		      c.getPA().movePlayer(2872, 5279, 2);
		break;
		case 26426:
		      if (c.Arma < 15 && c.absX == 2839 && c.absY == 5295) {
		       c.sendMessage("You need atleast 15 Armadyl KC to enter here!");
		       return;
		       }		       
		       if(c.absX == 2839 && c.absY == 5295) {
		        c.getPA().movePlayer(2839, 5296, 6);
		       c.Arma -= 15;
		       c.sendMessage("A magical force reseted your Armadyl kill count!");
		      }
		       if(c.absX == 2839 && c.absY == 5296) {
		        c.getPA().movePlayer(2839, 5295, 2);
			c.autoRet = 0;
			c.getCombat().resetPlayerAttack();
		      }
		break;
		case 26427:
		      if (c.Sara < 15 && c.absX == 2908 && c.absY == 5265) {
		       c.sendMessage("You need atleast 15 Saradomin KC to enter here!");
		       return;
		       }	
		       if(c.absX == 2908 && c.absY == 5265) {
		       c.Sara -= 15;
		       c.sendMessage("A magical force reseted your Saradomin kill count!");
		        c.getPA().movePlayer(2907, 5265, 4);
		      }
		       if(c.absX == 2907 && c.absY == 5265) {
		        c.getPA().movePlayer(2908, 5265, 0);
			c.autoRet = 0;
			c.getCombat().resetPlayerAttack();
		      }
		break;
		case 6420:
					
	c.sendMessage("You currently have " + c.dungpoint + " Dungeoneering Points.");
				c.getShops().openShop(25);
			break;
	
			
		case 2403:
			if (c.Culin == true) {
				c.getShops().openShop(65);
			return;
			}
			if (c.Agrith == true && c.Flambeed == false) {
				c.getShops().openShop(61);
		return;
			} 
		if(c.Flambeed == true && c.Karamel == false) {
				c.getShops().openShop(62);
		return;
			} 
		if(c.Karamel == true && c.Dessourt == false) {
				c.getShops().openShop(63);
		return;
			} 
		if(c.Dessourt == true && c.Culin == false) {
				c.getShops().openShop(64);
			return;
			} 
			if (c.Agrith == false) {
				c.getShops().openShop(60);
	}
			break;
		case 26293:
			c.getPA().startTeleport(3086, 3493, 0, "modern");
		break;
		case 246:
			c.getPA().movePlayer(c.absX, c.absY - 2, 1);
		break;
		case 1766:
			c.getPA().movePlayer(3016, 3849, 0);
		break;
	case 410:
			if (c.playerMagicBook == 0) {
				if(c.playerEquipment[c.playerWeapon] == 4675 || c.playerEquipment[c.playerWeapon] == 15050 || c.playerEquipment[c.playerWeapon] == 15040) {
				c.setSidebarInterface(0, 328);
				}
				c.playerMagicBook = 2;
				c.setSidebarInterface(6, 16640);
				c.sendMessage("Your mind becomes stirred with thoughs of dreams.");
				c.getPA().resetAutocast();
			} else {
				if(c.playerEquipment[c.playerWeapon] == 4675 || c.playerEquipment[c.playerWeapon] == 15050 || c.playerEquipment[c.playerWeapon] == 15040) {
				c.setSidebarInterface(0, 328);
				}
				c.setSidebarInterface(6, 1151);
				c.playerMagicBook = 0;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
			}
		break; 

	case 6552:
			if (c.playerMagicBook == 0) {
				if(c.playerEquipment[c.playerWeapon] == 4675 || c.playerEquipment[c.playerWeapon] == 15050 || c.playerEquipment[c.playerWeapon] == 15040) {
				c.setSidebarInterface(0, 328);
				}
				c.playerMagicBook = 1;
				c.setSidebarInterface(6, 12855);
				c.sendMessage("An ancient wisdomin fills your mind.");
				c.getPA().resetAutocast();
			} else {
				if(c.playerEquipment[c.playerWeapon] == 4675 || c.playerEquipment[c.playerWeapon] == 15050 || c.playerEquipment[c.playerWeapon] == 15040) {
				c.setSidebarInterface(0, 328);
				}
				c.setSidebarInterface(6, 1151); //modern
				c.playerMagicBook = 0;
				c.sendMessage("You feel a drain on your memory.");
				c.autocastId = -1;
				c.getPA().resetAutocast();
			}	
		break;
		
		case 1816:
			c.getPA().startTeleport2(2271, 4680, 0);			
		break;
		case 1817:
			c.getPA().startTeleport(3087, 3500, 0, "modern");
		break;
		case 1814:
			//ardy lever
			c.getPA().startTeleport(3153, 3923, 0, "modern");
		break;
		
		case 12356:
			if (c.Culin == true) {
			c.sendMessage("You have already finished this minigame!");
			return;
			}
			if (c.getY() < 3500) {
			c.getPA().enterRFD();
			c.sendMessage("Note: this is not a Safe Minigame, you'll lose your items on death!");
			for(int p = 0; p < c.PRAYER.length; p++) { // reset prayer glows
				c.playerLevel[5] = 0;
				c.getPA().refreshSkill(5);
				c.prayerActive[p] = false;
				c.getPA().sendFrame36(c.PRAYER_GLOW[p], 0);	
			}
			} else {
			c.getPA().resetRFD();
			}
		break;
		case 1733:
			c.getPA().movePlayer(c.absX, c.absY + 6393, 0);
		break;
		
		case 1734:
			c.getPA().movePlayer(c.absX, c.absY - 6396, 0);
		break;
		
		case 9357:
			c.getPA().resetTzhaar();
		break;
		
		case 8959:
			if (c.getX() == 2490 && (c.getY() == 10146 || c.getY() == 10148)) {
				if (c.getPA().checkForPlayer(2490, c.getY() == 10146 ? 10148 : 10146)) {
					new Object(6951, c.objectX, c.objectY, c.heightLevel, 1, 10, 8959, 15);	
				}			
			}
		break;
				case 15644:
				case 15641:
				c.sendMessage("Use the NPC's to navigate around in the Warrior's Guild!");
				break;
		
		case 2213:
		case 14367:
		case 11758:
		case 3193:	
				c.getItems().deleteItem(15015,c.getItems().getItemSlot(15015),999999999);
				c.getItems().deleteItem(15016,c.getItems().getItemSlot(15016),999999999);
				c.getPA().openUpBank();
		break;
		
		case 10177:
			c.getPA().movePlayer(1890, 4407, 0);
		break;
		case 10230:
			c.getPA().movePlayer(2900, 4449, 0);
		break;
		case 10229:
			c.getPA().movePlayer(1912, 4367, 0);
		break;
		case 2623:
			if (c.absX >= c.objectX)
				c.getPA().walkTo(-1,0);
			else
				c.getPA().walkTo(1,0);
		break;
		//pc boat
		case 14315:
			c.getPA().movePlayer(2661,2639,0);
		break;
		case 14314:
			c.getPA().movePlayer(2657,2639,0);
		break;
		
		case 1596:
		case 1597:
		if (c.getY() >= c.objectY)
			c.getPA().walkTo(0,-1);
		else
			c.getPA().walkTo(0,1);
		break;
		
		case 14235:
		case 14233:
			if (c.objectX == 2670)
				if (c.absX <= 2670)
					c.absX = 2671;
				else
					c.absX = 2670;
			if (c.objectX == 2643)
				if (c.absX >= 2643)
					c.absX = 2642;
				else
					c.absX = 2643;
			if (c.absX <= 2585)
				c.absY += 1;
			else c.absY -= 1;
			c.getPA().movePlayer(c.absX, c.absY, 0);
		break;
		case 14829: case 14830: case 14827: case 14828: case 14826: case 14831:
	
			Server.objectManager.startObelisk(objectType);
		break;
		case 4387:
			
		break;
		
		case 4388:
			
		break;
		
		case 4408:
			
		break;
		
		case 9369:
			if (c.getY() > 5175)
				c.getPA().movePlayer(2399, 5175, 0);
			else
				c.getPA().movePlayer(2399, 5177, 0);
		break;
		
		case 9368:
			if (c.getY() < 5169) {
				Server.fightPits.removePlayerFromPits(c.playerId);
				c.getPA().movePlayer(2399, 5169, 0);
			}	
		break;
		case 4411:
		case 4415:
		case 4417:
		case 4418:
		case 4419:
		case 4420:
		case 4469:
		case 4470:
		case 4911:
		case 4912:
		case 1747:
		case 1757:
 			
		break;

		
		//barrows
		//Chest
		case 10284:
			if(c.barrowsKillCount < 5) {
				c.sendMessage("You haven't killed all the brothers.");
			}
			if(c.barrowsKillCount == 5 && c.barrowsNpcs[c.randomCoffin][1] == 1) {
				c.sendMessage("I have already summoned this npc.");
			}
			if(c.barrowsNpcs[c.randomCoffin][1] == 0 && c.barrowsKillCount >= 5) {
				Server.npcHandler.spawnNpc(c, c.barrowsNpcs[c.randomCoffin][0], 3551, 9694-1, 0, 0, 120, 30, 200, 200, true, true);
				c.barrowsNpcs[c.randomCoffin][1] = 1;
			}
			if((c.barrowsKillCount > 5 || c.barrowsNpcs[c.randomCoffin][1] == 2) && c.getItems().freeSlots() >= 2) {
				c.getPA().resetBarrows();
				c.getItems().addItem(c.getPA().randomRunes(), Misc.random(150) + 100);
				if (Misc.random(2) == 1)
					c.getItems().addItem(c.getPA().randomBarrows(), 1);
				c.getPA().startTeleport(3564, 3288, 0, "modern");
			} else if(c.barrowsKillCount > 5 && c.getItems().freeSlots() <= 1) {
				c.sendMessage("You need at least 2 inventory slot opened.");
			}
			break;
		//doors
		case 6749:
			if(obX == 3562 && obY == 9678) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if(obX == 3558 && obY == 9677) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;
		case 6730:
			if(obX == 3558 && obY == 9677) {
				c.getPA().object(3562, 9678, 6749, -3, 0);
				c.getPA().object(3562, 9677, 6730, -1, 0);
			} else if(obX == 3558 && obY == 9678) {
				c.getPA().object(3558, 9677, 6749, -1, 0);
				c.getPA().object(3558, 9678, 6730, -3, 0);
			}
			break;
		case 6727:
			if(obX == 3551 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;
		case 6746:
			if(obX == 3552 && obY == 9684) {
				c.sendMessage("You cant open this door..");
			}
			break;
		case 6748:
			if(obX == 3545 && obY == 9678) {
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if(obX == 3541 && obY == 9677) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;
		case 6729:
			if(obX == 3545 && obY == 9677){
				c.getPA().object(3545, 9678, 6748, -3, 0);
				c.getPA().object(3545, 9677, 6729, -1, 0);
			} else if(obX == 3541 && obY == 9678) {
				c.getPA().object(3541, 9677, 6748, -1, 0);
				c.getPA().object(3541, 9678, 6729, -3, 0);
			}
			break;
		case 6726:
			if(obX == 3534 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if(obX == 3535 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;
		case 6745:
			if(obX == 3535 && obY == 9684) {
				c.getPA().object(3534, 9684, 6726, -4, 0);
				c.getPA().object(3535, 9684, 6745, -2, 0);
			} else if(obX == 3534 && obY == 9688) {
				c.getPA().object(3535, 9688, 6726, -2, 0);
				c.getPA().object(3534, 9688, 6745, -4, 0);
			}
			break;
		case 6743:
			if(obX == 3545 && obY == 9695) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if(obX == 3541 && obY == 9694) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break;
		case 6724:
			if(obX == 3545 && obY == 9694) {
				c.getPA().object(3545, 9694, 6724, -1, 0);
				c.getPA().object(3545, 9695, 6743, -3, 0);
			} else if(obX == 3541 && obY == 9695) {
				c.getPA().object(3541, 9694, 6724, -1, 0);
				c.getPA().object(3541, 9695, 6743, -3, 0);
			}
			break; 
		//end doors
		//coffins
		case 6707: // verac
			c.getPA().movePlayer(3556, 3298, 0);
			break;
			
		case 6823:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[0][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2030, c.getX(), c.getY()-1, -1, 0, 120, 25, 200, 200, true, true);
				c.barrowsNpcs[0][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;

		case 6706: // torag 
			c.getPA().movePlayer(3553, 3283, 0);
			break;
			
		case 6772:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[1][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2029, c.getX()+1, c.getY(), -1, 0, 120, 20, 200, 200, true, true);
				c.barrowsNpcs[1][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
			
		case 6705: // karil stairs
			c.getPA().movePlayer(3565, 3276, 0);
			break;
		case 6822:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[2][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2028, c.getX(), c.getY()-1, -1, 0, 90, 17, 200, 200, true, true);
				c.barrowsNpcs[2][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
		case 6704: // guthan stairs
			c.getPA().movePlayer(3578, 3284, 0);
			break;
		case 6773:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[3][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2027, c.getX(), c.getY()-1, -1, 0, 120, 23, 200, 200, true, true);
				c.barrowsNpcs[3][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
		case 6703: // dharok stairs
			c.getPA().movePlayer(3574, 3298, 0);
			break;
		case 6771:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[4][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2026, c.getX(), c.getY()-1, -1, 0, 120, 45, 250, 250, true, true);
				c.barrowsNpcs[4][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
		case 6702: // ahrim stairs
			c.getPA().movePlayer(3565, 3290, 0);
			break;
		case 6821:
			if(server.model.minigames.Barrows.selectCoffin(c, objectType)) {
				return;
			}
			if(c.barrowsNpcs[5][1] == 0) {
				Server.npcHandler.spawnNpc(c, 2025, c.getX(), c.getY()-1, -1, 0, 90, 19, 200, 200, true, true);
				c.barrowsNpcs[5][1] = 1;
			} else {
				c.sendMessage("You have already searched in this sarcophagus.");
			}
			break;
			
		
		

		
		
		
		case 8143:
			if (c.farm[0] > 0 && c.farm[1] > 0) {
				c.getFarming().pickHerb();
			}
		break;
	
			// DOORS
		case 1516:
		case 1519:
			if (c.objectY == 9698) {
				if (c.absY >= c.objectY)
					c.getPA().walkTo(0,-1);
				else
					c.getPA().walkTo(0,1);
				break;
			}
		case 1530:
		case 1531:
		case 1533:
		case 1534:
		case 11712:
		case 11711:
		case 11707:
		case 11708:
		case 6725:
		case 3198:

		case 3197:
			Server.objectHandler.doorHandling(objectType, c.objectX, c.objectY, 0);	
			break;

		
		case 9319:
			if (c.heightLevel == 0)
				c.getPA().movePlayer(c.absX, c.absY, 1);
			else if (c.heightLevel == 1)
				c.getPA().movePlayer(c.absX, c.absY, 2);
		break;
		
		case 9320:
			if (c.heightLevel == 1)
				c.getPA().movePlayer(c.absX, c.absY, 0);
			else if (c.heightLevel == 2)
				c.getPA().movePlayer(c.absX, c.absY, 1);
		break;
		
		case 4496:
		case 4494:
			if (c.heightLevel == 2) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 0);
			}
		break;
		
		case 4493:
			if (c.heightLevel == 0) {
				c.getPA().movePlayer(c.absX - 5, c.absY, 1);
			} else if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
		break;
		
		case 4495:
			if (c.heightLevel == 1) {
				c.getPA().movePlayer(c.absX + 5, c.absY, 2);
			}
		break;
		
		case 5126:
			if (c.absY == 3554)
				c.getPA().walkTo(0,1);
			else
				c.getPA().walkTo(0,-1);
		break;
		
		case 1755:
		        c.getPA().movePlayer(3086, 3493, 0);
		break;
		case 1759:
			if (c.objectX == 2884 && c.objectY == 3397)
				c.getPA().movePlayer(c.absX, c.absY + 6400, 0);				
		break;
	
			
		case 409:
if(c.playerLevel[3] < c.getPA().getLevelForXP(c.playerXP[3])) {
c.playerLevel[3] = c.getPA().getLevelForXP(c.playerXP[3]);
c.getPA().refreshSkill(3);
c.sendMessage("Your HP Has Been Restored, and you've also been un teleblocked!");
c.teleBlockLength = 0;
 } else {
c.sendMessage("You already have full hp idiot.");
}

			if(c.playerLevel[5] < c.getPA().getLevelForXP(c.playerXP[5])) {
				c.startAnimation(645);
				c.playerLevel[5] = c.getPA().getLevelForXP(c.playerXP[5]);
				c.sendMessage("You recharge your prayer points.");
				c.getPA().refreshSkill(5);
			} else {
				c.sendMessage("You already have full prayer points.");
			}
			break;
		case 2873:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Saradomin blesses you with a cape.");
				c.getItems().addItem(2412, 1);
			}	
		break;
		case 2875:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Guthix blesses you with a cape.");
				c.getItems().addItem(2413, 1);
			}
		break;
		case 2874:
			if (!c.getItems().ownsCape()) {
				c.startAnimation(645);
				c.sendMessage("Zamorak blesses you with a cape.");
				c.getItems().addItem(2414, 1);
			}
		break;
		case 2879:
			c.getPA().movePlayer(2538, 4716, 0);
		break;
		case 2878:
			c.getPA().movePlayer(2509, 4689, 0);
		break;
		case 5960:
			c.getPA().startTeleport2(3090, 3956, 0);
		break;
		
		case 1815:
			c.getPA().startTeleport2(Config.EDGEVILLE_X, Config.EDGEVILLE_Y, 0);
		break;
		
		case 9706:
			c.getPA().startTeleport2(3105, 3951, 0);
		break;
		case 9707:
			c.getPA().startTeleport2(3105, 3956, 0);
		break;
		
		case 5959:
			c.getPA().startTeleport2(2539, 4712, 0);
		break;
		
		case 2558:
			c.sendMessage("This door is locked.");	
		break;
		
		case 9294:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(c.objectX + 1, c.absY, 0);
			} else if (c.absX > c.objectX) {
				c.getPA().movePlayer(c.objectX - 1, c.absY, 0);
			}
		break;
		
		case 9293:
			if (c.absX < c.objectX) {
				c.getPA().movePlayer(2892, 9799, 0);
			} else {
				c.getPA().movePlayer(2886, 9799, 0);
			}
		break;
		case 10529:
		case 10527:
			if (c.absY <= c.objectY)
				c.getPA().walkTo(0,1);
			else
				c.getPA().walkTo(0,-1);
		break;
		case 3044:
		case 26814:
			c.getSmithing().sendSmelting();
		break;
		case 733:
			c.startAnimation(451);
			/*if (Misc.random(1) == 1) {
				c.getPA().removeObject(c.objectX, c.objectY);
				c.sendMessage("You slash the web.");
			} else {
				c.sendMessage("You fail to slash the webs.");
			}*/
			if (c.objectX == 3158 && c.objectY == 3951) {
				new Object(734, c.objectX, c.objectY, c.heightLevel, 1, 10, 733, 50);
			} else {
				new Object(734, c.objectX, c.objectY, c.heightLevel, 0, 10, 733, 50);
			}
		break;
		
		default:
			ScriptManager.callFunc("objectClick1_"+objectType, c, objectType, obX, obY);
			break;

		}
	}
	
	public void secondClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		//c.sendMessage("Object type: " + objectType);
		switch(objectType) {
			case 11666:
			case 3044:
			case 26814:
				c.getSmithing().sendSmelting();
			break;
			case 26288:
			case 26287:
			case 26286:
			case 26289:
			c.autoRet = 0;
			c.getCombat().resetPlayerAttack();
			c.getPA().movePlayer(2882, 5310, 2);
			c.sendMessage("You teleported out of the god's chamber.");
			break;
			case 2213:
			case 14367:
			case 11758:
			case 26972:
			case 11402:
				c.getItems().deleteItem(15015,c.getItems().getItemSlot(15015),999999999);
				c.getItems().deleteItem(15016,c.getItems().getItemSlot(15016),999999999);
				c.getPA().openUpBank();
			break;
			case 4874:
				c.getThieving().stealFromStall(995, 3000 + Misc.random(5000), 100, 1);
			
			break;
			case 4875:
				c.getThieving().stealFromStall(995,4000 + Misc.random(8000), 130, 25);
			
			break;
			case 4876:
				c.getThieving().stealFromStall(995, 5000 + Misc.random(8500), 160, 50);

			break;
			case 4877:
				c.getThieving().stealFromStall(995,6000 +  Misc.random(9000), 180, 75);

			break;
			case 4878:
				c.getThieving().stealFromStall(995, 7000 + Misc.random(10000), 250, 90);
				
			break;




	
			case 2558:
				if (System.currentTimeMillis() - c.lastLockPick < 3000 || c.freezeTimer > 0)
					break;
				if (c.getItems().playerHasItem(1523,1)) {
						c.lastLockPick = System.currentTimeMillis();
						if (Misc.random(10) <= 3){
							c.sendMessage("You fail to pick the lock.");
							break;
						}
					if (c.objectX == 3044 && c.objectY == 3956) {
						if (c.absX == 3045) {
							c.getPA().walkTo2(-1,0);
						} else if (c.absX == 3044) {
							c.getPA().walkTo2(1,0);
						}
					
					} else if (c.objectX == 3038 && c.objectY == 3956) {
						if (c.absX == 3037) {
							c.getPA().walkTo2(1,0);
						} else if (c.absX == 3038) {
							c.getPA().walkTo2(-1,0);
						}				
					} else if (c.objectX == 3041 && c.objectY == 3959) {
						if (c.absY == 3960) {
							c.getPA().walkTo2(0,-1);
						} else if (c.absY == 3959) {
							c.getPA().walkTo2(0,1);
						}					
					}
				} else {
					c.sendMessage("I need a lockpick to pick this lock.");
				}
			break;
		default:
			ScriptManager.callFunc("objectClick2_"+objectType, c, objectType, obX, obY);
			break;
		}
	}
	
	
	public void thirdClickObject(int objectType, int obX, int obY) {
		c.clickObjectType = 0;
		c.sendMessage("Object type: " + objectType);
		switch(objectType) {
		default:
			ScriptManager.callFunc("objectClick3_"+objectType, c, objectType, obX, obY);
			break;
		}
	}
	
	public void firstClickNpc(int npcType) {
	c.fishitem = -1;
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		if (c.fishitem != -1) {
                    if (!c.getItems().playerHasItem(c.fishitem)) {
                        c.sendMessage("You need a " + c.getItems().getItemName(c.fishitem) + " to fish for " + c.getItems().getItemName(c.fishies));
                        c.fishing = false;
                        return;
                    }
                    if (c.getItems().freeSlots() == 0) {
                        c.sendMessage("Your inventory is full.");
                        c.fishing = false;
                        return;
                    }
                    if (c.playerFishing < c.fishreqt) {
                        c.sendMessage("You need a fishing level of " + c.fishreqt + " to fish here.");
                        c.fishing = false;
                        return;
                    }
                    c.fishtimer = c.getFishing().fishtime(c.fishies, c.fishreqt);
                }
		switch(npcType) {
			case 706:
				c.getDH().sendDialogues(9, npcType);
			break;
			case 9713:
				c.getDH().sendDialogues(205, npcType);
	    break;
case 456: 
c.getDH().sendDialogues(456, npcType); 
break;
			case 1113:
				c.getPA().movePlayer(3448, 3517, 0);
				c.sendMessage("You have teleported to the summmoning shops!");
			break;
case 946: 
c.getDH().sendDialogues(20, npcType); 
break;
                case 316:
                    c.fishing = true;
					c.fishXP = 350;
                    c.fishies = 317;
                    c.fishreqt = 0;
                    c.fishitem = 303;
                    c.fishemote = 621;
                    c.fishies2 = 0;
                    c.fishreq2 = 0;
                break;
                case 334:
                    c.fishing = true;
					c.fishXP = 350;
                    c.fishies = 317;
                    c.fishreqt = 0;
                    c.fishitem = 303;
                    c.fishemote = 621;
                    c.fishies2 = 0;
                    c.fishreq2 = 0;
                break;
                case 324://cage-harpoon spot choice cage
                    c.fishing = true;
                    c.fishXP = 700;
                    c.fishies = 377;
                    c.fishreqt = 40;
                    c.fishitem = 301;
                    c.fishemote = 619;
                    c.fishies2 = 389;
                    c.fishreq2 = 81;
                break;
		case 325:
		c.fishing = true;
                    c.fishXP = 2000;
                    c.fishies = 15273;
                    c.fishreqt = 40;
                    c.fishitem = 301;
                    c.fishemote = 619;
                    c.fishies2 = 15273;
                    c.fishreq2 = 99;
                break;
		case 320:
		c.fishing = true;
                    c.fishXP = 2000;
                    c.fishies = 15273;
                    c.fishreqt = 40;
                    c.fishitem = 301;
                    c.fishemote = 619;
                    c.fishies2 = 15273;
                    c.fishreq2 = 95;
                break;
		case 326:
                    c.fishing = true;
                    c.fishXP = 600;
                    c.fishies = 341;
                    c.fishreqt = 23;
                    c.fishitem = 303;
                    c.fishemote = 621;
                    c.fishies2 = 363;
                    c.fishreq2 = 46;
                break;
		case 313:
                    c.fishing = true;
                    c.fishXP = 600;
                    c.fishies = 341;
                    c.fishreqt = 23;
                    c.fishitem = 303;
                    c.fishemote = 621;
                    c.fishies2 = 363;
                    c.fishreq2 = 46;
                break;
		case 4514: 
		c.getPA().movePlayer(2717, 9801, 0);
		c.sendMessage("Best thing to do is range them!");
		break;
			case 4289:
			c.kamfreenaDone = true;
			c.getDH().sendDialogues(47, 4289);
				break;
			case 1061:
			c.inCyclops = true;
			c.getWarriorsGuild().handleKamfreena(c, true);
			break;
			case 1062:
			c.kamfreenaDone = false;
			c.inCyclops = false;
			c.getWarriorsGuild().handleKamfreena(c, true);
			break;
			case 2258:
				c.getDH().sendDialogues(17, npcType);
			break;
			case 2261:
				c.getPA().walkableInterface(-1);
				c.getPA().movePlayer(2885, 5330, 2);
			break;
			case 2259:
				c.getPA().movePlayer(2885, 5345, 2);
				c.getPA().walkableInterface(12418);
				c.sendMessage("You have entered Zamorak, To leave talk to me on the other side.");
			break;
			case 398:
				c.getPA().movePlayer(2918, 5273, 0);
				c.sendMessage("You have entered Saradomin, To leave talk to me on the other side.");
			break;
		case 262:
		c.getPA().movePlayer(2855, 3542, 0);
		break;
			case 399:
				c.getPA().movePlayer(2911, 5299, 2);
			break;
			case 1064:
				c.getPA().movePlayer(2852, 5333, 2);
			break;
			


			case 1063:
				c.getPA().movePlayer(2849, 5333, 2);
				c.sendMessage("You have entered Bandos, To leave talk to me on the other side.");
			break;

			case 70:
				c.getPA().movePlayer(2872, 5269, 2);
				c.sendMessage("You have entered Armadyl, To leave click the Pillar.");
				c.sendMessage("Note: Ruby bolts (e) and Diamond bolts (e) are recommended!");
			break;
			case 1597:
				if (c.slayerTask <= 0) {
					c.getDH().sendDialogues(11,npcType);
				} else {
					c.getDH().sendDialogues(13,npcType);
				}
			break;
			case 1683:
					c.getDH().sendDialogues(20,npcType);
			case 500:
			if (c.monkeyk0ed >= 20) {
					c.getDH().sendDialogues(30,npcType);
				} else {
					c.getDH().sendDialogues(32,npcType);
				}			
			break;
			case 919:
				c.getShops().openShop(10);
			break;
			case 2952:
				c.SaveGame();
				c.sendMessage("Your account has been Succesfully saved!");
				c.sendMessage("This prevents your account from being rollback'd!!");
				c.sendMessage("@gre@Please do this as often as you can.");
			break;
		case 244:
		if(!c.getItems().playerHasItem(8851, 100)) {
		c.sendMessage("You need 100 tokens to go in!");
	} else {
		c.kamfreenaDone = true;
		c.getDH().sendDialogues(47, 4289);
		c.inCyclops = true;
		c.getWarriorsGuild().handleKamfreena(c, true);
		c.getPA().movePlayer(2850, 3541, 2);
		}
		break;
		case 242:
		c.kamfreenaDone = false;
		c.getPA().movePlayer(2840, 3540, 2);
		c.inCyclops = false;
		break;
		case 251:
		c.getPA().movePlayer(2851, 3548, 0);
		break;
			case 437:
				c.getShops().openShop(21);
				c.sendMessage("You currently have "+c.magePoints+" Agility points! Gain these from the agility course.");
			break;
			case 586:
				c.getShops().openShop(19);
			break;
			case 542:
				c.getShops().openShop(9);
     			break;
			case 1167:
			if(c.playerLevel[21] >= 99) {
				c.getShops().openShop(89);
				c.sendMessage("Since you're level 99 in hunter, you can purchase the cape!");
			} else {
				c.sendMessage("Yo broski, you need 99 Hunter to open this shop!");
			}
			break;
			case 823:
			if(c.playerLevel[24] >= 99) {
				c.getShops().openShop(99);
				c.sendMessage("Since you're level 99 in Summoning, you can purchase the cape!");
			} else {
				c.sendMessage("Yo broski, you need 99 Summoning to open this shop!");
			}
			break;
			case 648:
			if(c.playerLevel[1] >= 99 && c.playerLevel[2] >= 99 && c.playerLevel[3] >= 99 && c.playerLevel[4] >= 99 && c.playerLevel[5] >= 99 && c.playerLevel[6] >= 99 && c.playerLevel[7] >= 99 && c.playerLevel[8] >= 99 && c.playerLevel[9] >= 99 && c.playerLevel[10] >= 99 && c.playerLevel[11] >= 99 && c.playerLevel[12] >= 99 && c.playerLevel[13] >= 99 && c.playerLevel[14] >= 99 && c.playerLevel[15] >= 99 && c.playerLevel[16] >= 99 && c.playerLevel[17] >= 99 && c.playerLevel[18] >= 99 && c.playerLevel[19] >= 99 && c.playerLevel[20] >= 99 && c.playerLevel[21] >= 99 && c.playerLevel[24] >= 99) {
				c.getShops().openShop(77);
				c.sendMessage("Enjoy the overpowered Max Cape/Hood!!");
			} else {
				c.sendMessage("Noob, you need to be 99 in all stats to open this shop!");
			}
			break;
			case 541:
				c.getShops().openShop(5);
			break;
case 6970:
c.sendMessage("TO TRAIN SUMMONING, KILL MONSTERS FOR CHARMS!");
c.sendMessage("When you have charms, buy items from the summon shop and use on pouches!");
c.getShops().openShop(12); 
break;
case 6971: 
c.sendMessage("TO TRAIN SUMMONING, KILL MONSTERS FOR CHARMS!");
c.sendMessage("When you have charms, buy items from the summon shop and use on pouches!");
c.getShops().openShop(13); 
break;
			case 4290:
				c.getShops().openShop(66);
			break;
			
			case 461:
				c.getShops().openShop(2);
			break;
			
			case 683:
				c.getShops().openShop(3);
			break;
			
			case 549:
				c.getShops().openShop(4);
			break;
			
			case 2538:
				c.getShops().openShop(6);
			break;
			
			case 519:
				c.getShops().openShop(8);
			break;
			case 1282:
				c.getShops().openShop(7);
			break;
			case 1152:
				c.getDH().sendDialogues(16,npcType);
			break;
			case 494:
				c.getPA().openUpBank();
			break;
			case 2566:
				c.getShops().openSkillCape();
			break;

                        case 1688:
			c.getShops().openShop(20);
			break;

                        case 1866:
			c.getShops().openShop(21);
			break;

                        case 1301:
			c.getShops().openShop(23);
			break;

                        case 1303:
			c.getShops().openShop(24);
			break;

                        case 971:
			c.getShops().openShop(25);
			break;

                        case 2270:
			c.getShops().openShop(26);
			break;
			
                        case 540:
			c.getShops().openShop(27);
			break;
			
                        case 544:
			c.getShops().openShop(28);
			break;

			case 3789:
				c.sendMessage("You currently have " + c.pcPoints + " FXP.");
			break;
			case 924:
				c.sendMessage("You currently have " + c.pcPoints + " FXP.");
				c.getShops().openShop(20);
			break;
			case 3788:
				c.getShops().openVoid();
			break;
			case 905:
				c.getDH().sendDialogues(5, npcType);
			break;
			case 460:
				c.getDH().sendDialogues(3, npcType);
			break;
			case 462:
				c.getDH().sendDialogues(7, npcType);
			break;
			case 522:
			case 523:
			c.getItems().deleteItem(15015,c.getItems().getItemSlot(15015),999999999);
			c.getItems().deleteItem(15016,c.getItems().getItemSlot(15016),999999999);
				c.getShops().openShop(1);
			break;
			//hunter
			
			//implin's
			case 6055:
			c.CatchimpNpc("Baby Impling", 10010, 6055, 11238, 1500, 1, c.playerId);
			break;
			case 6056:
			c.CatchimpNpc("Young Impling", 10010, 6056, 11240, 3500, 17, c.playerId);
			break;
			case 6057:
			c.CatchimpNpc("Gourmet Impling", 10010, 6057, 11242, 4000, 20, c.playerId);
			break;
			case 6058:
			c.CatchimpNpc("Earth Impling", 10010, 6058, 11244, 5000, 34, c.playerId);
			break;
			case 6059:
			c.CatchimpNpc("Essence impling", 10010, 6059, 11246, 6000, 40, c.playerId);
			break;
			case 6060:
			c.CatchimpNpc("Electic impling", 10010, 6060, 11248, 8000, 50, c.playerId);
			break;
			case 6061:
			c.CatchimpNpc("Nature impling", 10010, 6061, 11250, 10000, 58, c.playerId);
			break;
			case 6062:
			c.CatchimpNpc("Magpie impling", 10010, 6062, 11252, 12500, 65, c.playerId);
			break;
			case 6063:
			c.CatchimpNpc("Ninja impling", 10010, 6063, 11254, 14000, 74, c.playerId);
			break;
			case 6064:
			c.CatchimpNpc("Dragon Impling", 10010, 6064, 11256, 25000, 90, c.playerId);
			break;
			
			//end of implin's!
			
			//butterfly's
							case 5082:
				c.CatchHunterNpc("Black Warlock", 10010, 5082, 10014, 18000, 85, c.playerId);
				break;
				case 5083:
				c.CatchHunterNpc("Snowy Knight", 10010, 5083, 10016, 15000, 75, c.playerId);
				break;
				case 5084:
				c.CatchHunterNpc("Sapphire Glacialis", 10010, 5084, 10018, 7500, 45, c.playerId);
				break;
				case 5085:
				c.CatchHunterNpc("Ruby Harvest", 10010, 5085, 10020, 5000, 30, c.playerId);
				break;
			//end of butterfly's	
				
		// end of hunter :)
			case 599:
				c.getPA().showInterface(3559);
				c.canChangeAppearance = true;
			break;
			case 904:
				c.sendMessage("You have " + c.mbPoints + " Mage Arena Points.");
			break;
		}
	}
	public void secondClickNpc(int npcType) {
	c.fishitem = -1;
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		if (c.fishitem != -1) {
                    if (!c.getItems().playerHasItem(c.fishitem)) {
                        c.sendMessage("You need a " + c.getItems().getItemName(c.fishitem) + " to fish for " + c.getItems().getItemName(c.fishies));
                        c.fishing = false;
                        return;
                    }
                    if (c.getItems().freeSlots() == 0) {
                       c. sendMessage("Your inventory is full.");
                        c.fishing = false;
                        return;
                    }
                    if (c.playerFishing < c.fishreqt) {
                        c.sendMessage("You need a fishing level of " + c.fishreqt + " to fish here.");
                        c.fishing = false;
                        return;
                    }
                    c.fishtimer = c.getFishing().fishtime(c.fishies, c.fishreqt);
                }
		switch(npcType) {
			case 1282:
				c.getShops().openShop(7);
			break;
			case 333:
                    c.fishing = true;
                    c.fishXP = 650;
                    c.fishies = 359;
                    c.fishreqt = 35;
                    c.fishitem = 311;
                    c.fishemote = 618;
                    c.fishies2 = 371;
                    c.fishreq2 = 50;
					break;
                case 312:
                    c.fishing = true;
                    c.fishXP = 650;
                    c.fishies = 359;
                    c.fishreqt = 35;
                    c.fishitem = 311;
                    c.fishemote = 618;
                    c.fishies2 = 371;
                    c.fishreq2 = 50;
					break;
                case 324:
                    c.fishing = true;
                    c.fishXP = 650;
                    c.fishies = 359;
                    c.fishreqt = 35;
                    c.fishitem = 311;
                    c.fishemote = 618;
                    c.fishies2 = 371;
                    c.fishreq2 = 50;
		break;
                case 334:
                    c.fishing = true;
                    c.fishXP = 650;
                    c.fishies = 359;
                    c.fishreqt = 35;
                    c.fishitem = 311;
                    c.fishemote = 618;
                    c.fishies2 = 371;
                    c.fishreq2 = 50;
		break;
                case 316:
                    c.fishing = true;
                    c.fishXP = 630;
					c.fishies = 327;
					c.fishreqt = 5;
                    c.fishitem = 307;
                    c.fishemote = 622;
                    c.fishies2 = 345;
                    c.fishreq2 = 10;
					break;
                case 326:
                    c.fishing = true;
                    c.fishXP = 530;
					c.fishies = 327;
					c.fishreqt = 5;
                    c.fishitem = 307;
                    c.fishemote = 622;
                    c.fishies2 = 345;
                    c.fishreq2 = 10;
					break;
               case 331:
                    c.fishing = true;
                    c.fishXP = 770;
                    c.fishies = 349;
                    c.fishreqt = 25;
                    c.fishitem = 307;
                    c.fishemote = 622;
                    c.fishies2 = 0;
                    c.fishreq2 = 0;
                case 313:
                    c.fishing = true;
                    c.fishXP = 1000;
                    c.fishies = 383;
                    c.fishreqt = 79;
                    c.fishitem = 311;
                    c.fishemote = 618;
                    c.fishies2 = 0;
                    c.fishreq2 = 0;
                break;
			case 3788:
				c.getShops().openVoid();
			break;
			case 494:
				c.getItems().deleteItem(15015,c.getItems().getItemSlot(15015),999999999);
				c.getItems().deleteItem(15016,c.getItems().getItemSlot(15016),999999999);
				c.getPA().openUpBank();
			break;
			case 904:
				c.getShops().openShop(52);
			break;
			case 522:
			case 523:
				c.getShops().openShop(1);
			break;
			case 541:
				c.getShops().openShop(5);
			break;
			
			case 461:
				c.getShops().openShop(2);
			break;
			
			case 683:
				c.getShops().openShop(3);
			break;
			
			case 549:
				c.getShops().openShop(4);
			break;
			
			case 2538:
				c.getShops().openShop(6);
			break;
			
			case 519:
				c.getShops().openShop(8);
			break;
			case 924:
				c.getShops().openShop(20);
				c.sendMessage("You have " + c.pcPoints + " FXP. Gain these from Slayer/Pc/Pk/Duel arena!");
				c.sendMessage("<shad=15369497>Ring of Vigour makes ur spec restore faster!");
			break;
			case 3789:
				c.getShops().openShop(18);
				c.sendMessage("You have " + c.pcPoints + " FXP. Gain these from Slayer/Pc/Pk/Duel arena!");
				c.sendMessage("<shad=15369497>Ring of Vigour makes ur spec restore faster!");
			break;
			case 1:
			case 9:
			case 18:
			case 20:
			case 26:
			case 21:
				c.getThieving().stealFromNPC(npcType);
			break;
			
		}
	}
	
	public void thirdClickNpc(int npcType) {
		c.clickNpcType = 0;
		c.npcClickIndex = 0;
		switch(npcType) {


		}
	}
	

}