package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
import server.Server;


/**
 * Clicking an item, bury bone, eat food etc
 **/
public class ClickItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int junk = c.getInStream().readSignedWordBigEndianA();
		int itemSlot = c.getInStream().readUnsignedWordA();
		int itemId = c.getInStream().readUnsignedWordBigEndian();
		if (itemId != c.playerItems[itemSlot] - 1) {
			return;
		}
		if (itemId == 15055) {
			if(c.inDuelArena()) {
				c.sendMessage("Rocktails are disabled in duels.");
				return;
			}
			if (System.currentTimeMillis() - c.foodDelay >= 1500 && c.playerLevel[3] > 0) {
				c.getCombat().resetPlayerAttack();
				c.attackTimer += 2;
				c.startAnimation(829);
				c.getItems().deleteItem(15055, 1);
				if (c.playerLevel[3] < c.getLevelForXP(c.playerXP[3])) {
					c.playerLevel[3] += 23;
					if (c.playerLevel[3] > c.getLevelForXP(c.playerXP[3]))
						c.playerLevel[3] = c.getLevelForXP(c.playerXP[3]);
				}
				c.foodDelay = System.currentTimeMillis();
				c.getPA().refreshSkill(3);
				c.sendMessage("You eat the Rocktail.");
			}
			c.playerLevel[3] += 10;
			if (c.playerLevel[3] > (c.getLevelForXP(c.playerXP[3])*1.11 + 1)) {
				c.playerLevel[3] = (int)(c.getLevelForXP(c.playerXP[3])*1.11);
			}
			c.getPA().refreshSkill(3);
				return;
		}
		if(itemId >= 15052 && itemId <= 15053) {
			if(c.inDuelArena()) {
				c.sendMessage("Pouches are disabled in duels!");
				return;
		}
		if(itemId >= 15015 && itemId <= 15016) {
			c.getItems().deleteItem(15015,999999999);
			c.getItems().deleteItem(15016,999999999);
		}
		if(itemId >= 15052 && itemId <= 15053) {
		    if(c.playerLevel[8] >= 99 && c.playerLevel[11] >= 99 && c.playerLevel[10] >= 99 && c.playerLevel[7] >= 99
        	&& c.playerLevel[12] >= 99 && c.playerLevel[21] >= 99 && c.playerLevel[21] >= 99 && c.playerLevel[18] >= 99 && c.playerLevel[17] >= 99 && c.playerLevel[16] >= 99 && c.playerLevel[15] >= 99 && c.playerLevel[9] >= 99) {
			if (!c.hasNpc) {
				c.gfx0(1738);
				c.hasNpc = true;
				Server.npcHandler.spawnNpc3(c, Server.npcHandler.summonItemId(itemId), c.absX, c.absY-1, c.heightLevel, 0, 120, 25, 200, 200, true, false, true);
				c.getItems().deleteItem(itemId, itemSlot, c.playerItemsN[itemSlot]);
		} else {
				c.sendMessage("You alredy summoned an NPC");
					}
				}
			}
		}
		/*
		*Start Of Box Sets
		*/
		if (itemId >= 1 && itemId <= 16035) {
			int a = itemId;
			String YEYAF = "<col=1532693>You Exchanged Your Set For</col> ";
			if (a == 1){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(15080,1);
			c.getItems().addItem(15081,1);
			c.getItems().addItem(15082,1);
			c.getItems().addItem(15083,1);
			c.getItems().addItem(15084,1);
			c.getItems().addItem(15085,1);
			c.sendMessage(YEYAF + "<col=1532693>the Promethium Armour!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			}
			if (a == 16016){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(16005,1);
			c.getItems().addItem(16006,1);
			c.getItems().addItem(16007,1);
			c.getItems().addItem(16008,1);
			c.getItems().addItem(16009,1);
			c.getItems().addItem(16010,1);
			c.sendMessage(YEYAF + "<col=1532693>the Primal Armour!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			}
			if (a == 16017){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(15004,1);
			c.getItems().addItem(15005,1);
			c.getItems().addItem(15006,1);
			c.getItems().addItem(15007,1);
			c.sendMessage(YEYAF + "<col=1532693>the Vesta Armour and Weapons!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			}
			if (a == 16018){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(15017,1);
			c.getItems().addItem(15018,1);
			c.getItems().addItem(15019,1);
			c.getItems().addItem(15020,1);
			c.sendMessage(YEYAF + "<col=1532693>the Statius Armour and Weapons!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			}
			if (a == 16019){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(15012,1);
			c.getItems().addItem(15013,1);
			c.getItems().addItem(15014,1);
			c.getItems().addItem(15015,5);
			c.getItems().addItem(15016,5);
			c.sendMessage(YEYAF + "<col=1532693>for Morrigans Armour and Weapons!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			}
			if (a == 16020){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(15008,1);
			c.getItems().addItem(15009,1);
			c.getItems().addItem(15011,1);
			c.getItems().addItem(15050,1);
			c.sendMessage(YEYAF + "<col=1532693>for Zuriel's Armour and a Staff of Light!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			}
			if (a == 16021){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(11724,1);
			c.getItems().addItem(11726,1);
			c.getItems().addItem(11728,1);
			c.sendMessage(YEYAF + "<col=1532693>for Bandos Armour!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			}
			if (a == 16022){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(11718,1);
			c.getItems().addItem(11720,1);
			c.getItems().addItem(11722,1);
			c.sendMessage(YEYAF + "<col=1532693>for Armdyal Armour!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			}
			if (a == 16023){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(11724,1);
			c.getItems().addItem(11726,1);
			c.getItems().addItem(11728,1);
			c.getItems().addItem(11696,1);
			c.sendMessage(YEYAF + "<col=1532693>for Bandos Armour and the Godsword!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			}
			if (a == 16024){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(11718,1);
			c.getItems().addItem(11720,1);
			c.getItems().addItem(11722,1);
			c.getItems().addItem(11694,1);
			c.sendMessage(YEYAF + "<col=1532693>for Armdyal Armour and the Godsword!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			}
			if (a == 16025){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(15037,1);
			c.getItems().addItem(15038,1);
			c.getItems().addItem(15039,1);
			c.getItems().addItem(15040,1);
			c.sendMessage(YEYAF + "<col=1532693>for a Chaotic Set!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			}
			if (a == 16026){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(13352,1);
			c.getItems().addItem(13353,1);
			c.getItems().addItem(13354,1);
			c.getItems().addItem(13356,1);
			c.sendMessage(YEYAF + "<col=1532693>for Dragon (G) Armour!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			}
			if (a == 16027){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(15021,1);
			c.getItems().addItem(15022,1);
			c.getItems().addItem(15023,1);
			c.getItems().addItem(15026,1);
			c.sendMessage(YEYAF + "<col=1532693>for a Spirit Shield Set!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			}
			if (a == 16028){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(15019,1);
			c.getItems().addItem(11694,1);
			c.getItems().addItem(15004,1);
			c.getItems().addItem(15005,1);
			c.getItems().addItem(15027,1);
			c.sendMessage(YEYAF + "<col=1532693>for Killer Armour!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			}
			if (a == 16029){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(15019,1);
			c.getItems().addItem(15004,1);
			c.getItems().addItem(15005,1);
			c.getItems().addItem(6570,1);
			c.getItems().addItem(6585,1);
			c.getItems().addItem(17002,1);
			c.getItems().addItem(4151,1);
			c.getItems().addItem(13351,1);
			c.sendMessage(YEYAF + "<col=1532693>for Maxed Strength Gear with a Shield!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			}
			if (a == 16030){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(15019,1);
			c.getItems().addItem(15004,1);
			c.getItems().addItem(15005,1);
			c.getItems().addItem(6570,1);
			c.getItems().addItem(6585,1);
			c.getItems().addItem(17002,1);
			c.getItems().addItem(11694,1);;
			c.sendMessage(YEYAF + "<col=1532693>for Maxed Strength Gear!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			} 
			if (a == 16031){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(15042,1);
			c.getItems().addItem(15013,1);
			c.getItems().addItem(15012,1);
			c.getItems().addItem(17005,1);
			c.getItems().addItem(15014,1);
			c.getItems().addItem(6585,1);
			c.getItems().addItem(11732,1);
			c.sendMessage(YEYAF + "<col=1532693>for a Range Tank Set!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			}
			if (a == 16032){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(15019,1);
			c.getItems().addItem(15004,1);
			c.getItems().addItem(15005,1);
			c.getItems().addItem(6570,1);
			c.getItems().addItem(6585,1);
			c.getItems().addItem(17002,1);
			c.getItems().addItem(15027,1);
			c.getItems().addItem(11732,1);
			c.sendMessage(YEYAF + "<col=1532693>for a Rushing Set!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			}
			if (a == 16033){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(4151,1);
			c.getItems().addItem(6585,1);
			c.getItems().addItem(17002,1);
			c.getItems().addItem(15005,1);
			c.getItems().addItem(15004,1);
			c.getItems().addItem(6570,1);
			c.getItems().addItem(15042,1);
			c.getItems().addItem(15019,1);
			c.sendMessage(YEYAF + "<col=1532693>for the Boss Set!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			}
			if (a == 16034){
			if (c.getItems().freeSlots() > 10) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(15019,1);
			c.getItems().addItem(15004,1);
			c.getItems().addItem(15005,1);
			c.getItems().addItem(6570,1);
			c.getItems().addItem(15037,1);
			c.getItems().addItem(13350,1);
			c.getItems().addItem(15042,1);
			c.getItems().addItem(15008,1);
			c.getItems().addItem(15009,1);
			c.getItems().addItem(15040,1);
			c.getItems().addItem(2412,1);
			c.sendMessage(YEYAF + "<col=1532693>for the Ultimate Hybrid Set!</col>");
			} else {
			c.sendMessage("You need 10 free slots to open this set!");
			}
			}
			if (a == 16035){
			if (c.getItems().freeSlots() > 15) {
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(15019,1);
			c.getItems().addItem(15004,1);
			c.getItems().addItem(15005,1);
			c.getItems().addItem(6570,1);
			c.getItems().addItem(15037,1);
			c.getItems().addItem(13350,1);
			c.getItems().addItem(15042,1);
			c.getItems().addItem(15008,1);
			c.getItems().addItem(15009,1);
			c.getItems().addItem(15040,1);
			c.getItems().addItem(2412,1);
			c.getItems().addItem(15013,1);
			c.getItems().addItem(15012,1);
			c.sendMessage(YEYAF + "<col=1532693>for the Ultimate Trybrid Set!</col>");
			} else {
			c.sendMessage("You need 15 free slots to open this set!");
			}
			}
			
		}
		
		
/*ClueScrollStart Casket's first*/

/*if(itemId == 6000) {
if (c.getItems().playerHasItem(6000,1) && c.getItems().freeSlots() >= 1) {
c.getItems().deleteItem(6000, 1);
c.getItems().addItem(c.getPA().randomEliteClue(), 1);
c.getPA().showInterface(17050);
}
}
if(itemId == 6001) {
if (c.getItems().playerHasItem(6001,1) && c.getItems().freeSlots() >= 1) {
c.getItems().deleteItem(6001, 1);
c.getItems().addItem(c.getPA().randomHardClue(), 1);
c.getPA().showInterface(17050);
}
}
if(itemId == 6002) {
if (c.getItems().playerHasItem(6002,1) && c.getItems().freeSlots() >= 1) {
c.getItems().deleteItem(6002, 1);
c.getItems().addItem(c.getPA().randomMediumClue(), 1);
c.getPA().showInterface(17050);
}
}
if(itemId == 6003) {
if (c.getItems().playerHasItem(6003,1) && c.getItems().freeSlots() >= 1) {
c.getItems().deleteItem(6003, 1);
c.getItems().addItem(c.getPA().randomEasyClue(), 1);
c.getPA().showInterface(17050);
}
}*/
/*CluescrollEnd*/


		if(itemId == 4447) {
						for (int i = 0; i < 5; i++) {
					c.playerLevel[0] = 99;
					c.playerLevel[1] = 99;
					c.playerLevel[2] = 99;
					c.playerLevel[3] = 99;
					c.playerLevel[4] = 99;
					c.playerLevel[6] = 99;
					c.playerXP[i] = c.getPA().getXPForLevel(100);
					c.playerXP[6] = c.getPA().getXPForLevel(100);
					c.getPA().refreshSkill(i);
					c.getPA().refreshSkill(6);	
					c.getItems().deleteItem(4447, 1);
					c.logout();
				}
				c.getPA().requestUpdates();
			}
			
		if(itemId == 6796) {
			c.playerLevel[0] = 99;
			c.playerLevel[2] = 99;
			c.playerLevel[3] = 99;
			c.playerLevel[4] = 99;
			c.playerLevel[6] = 99;
			c.playerXP[0] = c.getPA().getXPForLevel(100);
			c.playerXP[2] = c.getPA().getXPForLevel(100);
			c.playerXP[3] = c.getPA().getXPForLevel(100);
			c.playerXP[4] = c.getPA().getXPForLevel(100);
			c.playerXP[6] = c.getPA().getXPForLevel(100);
			c.getPA().refreshSkill(0);
			c.getPA().refreshSkill(2);
			c.getPA().refreshSkill(3);
			c.getPA().refreshSkill(4);
			c.getPA().refreshSkill(6);
			c.getItems().deleteItem(6796, 1);
			c.logout();
			}

if (itemId == 9721) {
c.getPA().showInterface(6965);
c.getPA().sendFrame126("@red@~ Overload Instruction Manual ~",6968);
c.getPA().sendFrame126("",6969);
c.getPA().sendFrame126("@gre@Step 1: @cya@Get a Herblore level of 99.",6970);
c.getPA().sendFrame126("@gre@Step 2: @cya@Have all Extreme Potions and",6971);
c.getPA().sendFrame126("@cya@Super Prayer Potion in Inventory.",6972);
c.getPA().sendFrame126("@gre@Step 3: @cya@Use any Extreme Potion",6973);
c.getPA().sendFrame126("@cya@together.",6974);
c.getPA().sendFrame126("@gre@Step 4: @cya@You now have an overload!",6975);
c.getPA().sendFrame126("",6976);
c.getPA().sendFrame126("",6977);
c.getPA().sendFrame126("",6978);
c.getPA().sendFrame126("",6979);
c.getPA().sendFrame126("",6980);
}

		if(itemId == 3008) {
		if (c.inWild())
		if (c.inDuelArena())
		return;
		if (c.specAmount >= 5.0 ){
		c.sendMessage("You can not use this potion if you Special is greater than 50%.");
		return;
		} else {
		c.specAmount += 5.0;
		c.startAnimation(829);
		c.forcedText = "I just drank a special restore potion to restore 50% spec!";
		c.sendMessage("Your Special has gone up 50%!");
		c.getItems().deleteItem(3008, 1);
		c.getItems().addItem(229, 1);
		}
	}

		/*if(itemId == 3008) {
		if (c.specAmount > 5 && c.inWild()) {
		c.sendMessage("You have  50% special Alredy! Fucking noob rushers");
		}else {
		c.specAmount += 5.0;
		c.startAnimation(829);
		c.getItems().deleteItem(3008, 1);
		c.getItems().addItem(229, 1);
		}
	}*/
	
	

		if (itemId == 2528) {
		c.getItems().deleteItem(2528,1);
		c.getPA().showInterface(2808);
		}
		//Begin artifacts by Hirukos
		/*if (itemId >= 14876 && itemId <= 14892) {
			int a = itemId;
			String YEYAF = "<col=1532693>You Exchanged Your Artifact For</col> ";
			if (a == 14876){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,10000000);
			c.sendMessage(YEYAF + "<col=1532693>10 million Coins!</col>");
			}
			if (a == 14877){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,2000000);
			c.sendMessage(YEYAF + "<col=1532693>2 million Coins!</col>");
			}
			if (a == 14878){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,1500000);
			c.sendMessage(YEYAF + "<col=1532693>1.5 million Coins!</col>");
			}
			if (a == 14879){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,1000000);
			c.sendMessage(YEYAF + "<col=1532693>1 million Coins!</col>");
			}
			if (a == 14880){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,800000);
			c.sendMessage(YEYAF + "<col=1532693>800,000 Coins!</col>");
			}
			if (a == 14881){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,600000);
			c.sendMessage(YEYAF + "<col=1532693>600,000 Coins!</col>");
			}
			if (a == 14882){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,540000);
			c.sendMessage(YEYAF + "<col=1532693>540,000 Coins!</col>");
			}
			if (a == 14883){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,400000);
			c.sendMessage(YEYAF + "<col=1532693>400,000 Coins!</col>");
			}
			if (a == 14884){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,300000);
			c.sendMessage(YEYAF + "<col=1532693>300,000 Coins!</col>");
			}
			if (a == 14885){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,200000);
			c.sendMessage(YEYAF + "<col=1532693>200,000 Coins!</col>");
			}
			if (a == 14886){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,150000);
			c.sendMessage(YEYAF + "<col=1532693>150,000 Coins!</col>");
			}
			if (a == 14887){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,100000);
			c.sendMessage(YEYAF + "<col=1532693>100,000 Coins!</col>");
			}
			if (a == 14888){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,80000);
			c.sendMessage(YEYAF + "<col=1532693>80,000 Coins!</col>");
			}
			if (a == 14889){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,60000);
			c.sendMessage(YEYAF + "<col=1532693>60,000 Coins!</col>");
			}
			if (a == 14890){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,40000);
			c.sendMessage(YEYAF + "<col=1532693>40,000 Coins!</col>");
			}
			if (a == 14891){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,20000);
			c.sendMessage(YEYAF + "<col=1532693>20,000 Coins!</col>");
			} 
			if (a == 14892){
			c.getItems().deleteItem(a,1);
			c.getItems().addItem(995,10000);
			c.sendMessage(YEYAF + "<col=1532693>10,000 Coins!</col>");
			}
			
		}*/
		//End of artifacts By Hirukos
		
		
		if (itemId >= 5509 && itemId <= 5514) {
			int pouch = -1;
			int a = itemId;
			if (a == 5509)
				pouch = 0;
			if (a == 5510)
				pouch = 1;
			if (a == 5512)
				pouch = 2;
			if (a == 5514)
				pouch = 3;
			c.getPA().fillPouch(pouch);
			return;
		}
		if (c.getHerblore().isUnidHerb(itemId))
			c.getHerblore().handleHerbClick(itemId);
		if (c.getFood().isFood(itemId))
			c.getFood().eat(itemId,itemSlot);
		//ScriptManager.callFunc("itemClick_"+itemId, c, itemId, itemSlot);
		if (c.getPotions().isPotion(itemId))
			c.getPotions().handlePotion(itemId,itemSlot);
		if (c.getPrayer().isBone(itemId))
			c.getPrayer().buryBone(itemId, itemSlot);
		if (itemId == 952) {
			if(c.inArea(3553, 3301, 3561, 3294)) {
				c.teleTimer = 3;
				c.newLocation = 1;
			} else if(c.inArea(3550, 3287, 3557, 3278)) {
				c.teleTimer = 3;
				c.newLocation = 2;
			} else if(c.inArea(3561, 3292, 3568, 3285)) {
				c.teleTimer = 3;
				c.newLocation = 3;
			} else if(c.inArea(3570, 3302, 3579, 3293)) {
				c.teleTimer = 3;
				c.newLocation = 4;
			} else if(c.inArea(3571, 3285, 3582, 3278)) {
				c.teleTimer = 3;
				c.newLocation = 5;
			} else if(c.inArea(3562, 3279, 3569, 3273)) {
				c.teleTimer = 3;
				c.newLocation = 6;
			} else if(c.absX == 3572 && c.absY == 3313) { // Easy Clue
				c.getItems().deleteItem(15023,1);
				c.getItems().deleteItem(15050,100);
			}
		}
	}

}
