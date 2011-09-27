package server.model.players;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Future;
import java.net.URL;
import java.net.MalformedURLException;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

import org.apache.mina.common.IoSession;
import server.Config;
import server.Server;
import server.model.items.ItemAssistant;
import server.model.shops.ShopAssistant;
import server.net.HostList;
import server.net.Packet;
import server.net.StaticPacketBuilder;
import server.util.Misc;
import server.util.Stream;
import server.model.players.skills.*;
import server.event.CycleEventHandler;
import server.event.EventManager;
import server.event.Event;
import server.model.players.Hit.CombatType;
import server.model.players.PlayerSave;
import server.model.players.PlayerHandler;
import server.event.EventContainer;
import server.model.minigames.WarriorsGuild;
import server.model.minigames.Gambling;
import server.util.HiscoresHandler;

public class Client extends Player {

	public long lastSaved = 0;
	public byte buffer[] = null;
	public Stream inStream = null, outStream = null;
	private IoSession session;
	public static PlayerSave save;
	public boolean summon;
	public static Client cliento2;
	private ItemAssistant itemAssistant = new ItemAssistant(this);
	private ShopAssistant shopAssistant = new ShopAssistant(this);
	private TradeAndDuel tradeAndDuel = new TradeAndDuel(this);
	private PlayerAssistant playerAssistant = new PlayerAssistant(this);
	private CombatAssistant combatAssistant = new CombatAssistant(this);
	private ActionHandler actionHandler = new ActionHandler(this);
	private PlayerKilling playerKilling = new PlayerKilling(this);
	private DialogueHandler dialogueHandler = new DialogueHandler(this);
	private Potions potion = new Potions(this);
	private Queue<Packet> queuedPackets = new LinkedList<Packet>();
	private WarriorsGuild warriorsGuild = new WarriorsGuild();
	private PotionMixing potionMixing = new PotionMixing(this);
	private Food food = new Food(this);
	private TradeLog tradeLog = new TradeLog(this);
	private Gambling gamble = new Gambling(this);
	public int s;
	public boolean needsToSpawn;
	/**
	 * Skill instances
	 */
	private Slayer slayer = new Slayer(this);
	private Runecrafting runecrafting = new Runecrafting(this);
	public Summoning Summoning = new Summoning(this);

	
	public Agility ag = new Agility(this);
	private Cooking cooking = new Cooking(this);
	private Fishing fish = new Fishing(this);
	private Crafting crafting = new Crafting(this);
	private Smithing smith = new Smithing(this);
	private Prayer prayer = new Prayer(this);
	private Curse curse = new Curse(this);
	private Fletching fletching = new Fletching(this);
	private SmithingInterface smithInt = new SmithingInterface(this);
	private Farming farming = new Farming(this);
	private Thieving thieving = new Thieving(this);
	private Firemaking firemaking = new Firemaking(this);
	private Herblore herblore = new Herblore(this);
	private int somejunk;
	public int lowMemoryVersion = 0;
	public int timeOutCounter = 0;
	public int returnCode = 2;
	public int clawDamage;
	public int clawIndex;
	public int clawType = 0;
	public int protectItem = 0;
	public int totalstored;
	public int npcslot;
	public int given;
	public int summoningnpcid;
	private Future<?> currentTask;
	public boolean officialClient = true;

	public Client(IoSession s, int _playerId) {
		super(_playerId);
		this.session = s;
		// synchronized(this) {
		outStream = new Stream(new byte[Config.BUFFER_SIZE]);
		outStream.currentOffset = 0;
		// }
		inStream = new Stream(new byte[Config.BUFFER_SIZE]);
		inStream.currentOffset = 0;
		buffer = new byte[Config.BUFFER_SIZE];
	}

	public int getCombatLevel() {
		int mag = (int) ((getLevelForXP(playerXP[6])) * 1.5);
		int ran = (int) ((getLevelForXP(playerXP[4])) * 1.5);
		int attstr = (int) ((double) (getLevelForXP(playerXP[0])) + (double) (getLevelForXP(playerXP[2])));
		if (inWild()) {

			if (ran > attstr) {
				combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
						+ ((getLevelForXP(playerXP[3])) * 0.25)
						+ ((getLevelForXP(playerXP[5])) * 0.125) + ((getLevelForXP(playerXP[4])) * 0.4875));
			} else if (mag > attstr) {
				combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
						+ ((getLevelForXP(playerXP[3])) * 0.25)
						+ ((getLevelForXP(playerXP[5])) * 0.125) + ((getLevelForXP(playerXP[6])) * 0.4875));
			} else {
				combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
						+ ((getLevelForXP(playerXP[3])) * 0.25)
						+ ((getLevelForXP(playerXP[5])) * 0.125)
						+ ((getLevelForXP(playerXP[0])) * 0.325) + ((getLevelForXP(playerXP[2])) * 0.325));
			}
		} else {
			if (ran > attstr) {
				combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
						+ ((getLevelForXP(playerXP[3])) * 0.25)
						+ ((getLevelForXP(playerXP[5])) * 0.125)
						+ ((getLevelForXP(playerXP[4])) * 0.4875) + ((getLevelForXP(playerXP[24])) * 0.125));
			} else if (mag > attstr) {
				combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
						+ ((getLevelForXP(playerXP[3])) * 0.25)
						+ ((getLevelForXP(playerXP[5])) * 0.125)
						+ ((getLevelForXP(playerXP[6])) * 0.4875) + ((getLevelForXP(playerXP[24])) * 0.125));
			} else {
				combatLevel = (int) (((getLevelForXP(playerXP[1])) * 0.25)
						+ ((getLevelForXP(playerXP[3])) * 0.25)
						+ ((getLevelForXP(playerXP[5])) * 0.125)
						+ ((getLevelForXP(playerXP[0])) * 0.325)
						+ ((getLevelForXP(playerXP[2])) * 0.325) + ((getLevelForXP(playerXP[24])) * 0.125));
			}
		}
		return combatLevel;
	}

	public void frame1() // cancels all player and npc emotes within area!
	{
		for (Player p : PlayerHandler.players) {
			if (p != null) {
				Client c = (Client) p;
				c.outStream.createFrame(1);
			}
		}
		updateRequired = true;
		appearanceUpdateRequired = true;
	}

	public void stackableItemSlot(int itemId) {
		for (summoningslot = 0; occupied[summoningslot] == true; summoningslot += 1) {
			if (storeditems[summoningslot] == itemId) {
				break;
			}
		}
	}

	public int maxstore = 0;

	public void storesummon(int npcType) {
		switch (npcType) {

		case 6807:

			if (lastsummon > 0) {
				for (int i = 0; i < Server.npcHandler.maxNPCs; i++) {
					if (Server.npcHandler.npcs[i] != null) {
						if (Server.npcHandler.npcs[i].summon == true) {
							if (Server.npcHandler.npcs[i].spawnedBy == getId()
									&& Server.npcHandler.npcs[i].npcId == npcslot) {
								sendMessage("You are now storing items inside your npc");
								Summoning().store();
							}
						}
					}
				}

			}
			break;

		}
	}

	public void firstslot() {
		for (summoningslot = 0; occupied[summoningslot] == true; summoningslot += 1) {

		}
	}

	public int attackingplayer;
	public int lastsummon;
	public int summoningslot = 0;

	public int storeditems[] = new int[29];

	public boolean picking = false;

	public int amount[] = new int[29];
	public boolean occupied[] = new boolean[29];

	public boolean storing = false;

	public void jadSpawn() {
		// getPA().movePlayer(absX, absY, playerId * 4);
		getDH().sendDialogues(41, 2618);
		EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer c) {
				Server.fightCaves.spawnNextWave((Client) Server.playerHandler.players[playerId]);
				c.stop();
			}
		}, 10000);
	}

	public void HighAndLow() {
		if (combatLevel < 15) {
			int Low = 3;
			int High = combatLevel + 12;
			getPA().sendFrame126("@gre@" + Low + "@yel@ - @red@" + High + "",
					199);

		}
		if (combatLevel > 15 && combatLevel < 114) {
			int Low = combatLevel - 12;
			int High = combatLevel + 12;
			getPA().sendFrame126("@gre@" + Low + "@yel@ - @red@" + High + "",
					199);

		}
		if (combatLevel > 114) {
			int Low = combatLevel - 12;
			int High = 126;
			getPA().sendFrame126("@gre@" + Low + "@yel@ - @red@" + High + "",
					199);

		}
	}

	public void flushOutStream() {
		if (disconnected || outStream.currentOffset == 0)
			return;
		// synchronized(this) {
		StaticPacketBuilder out = new StaticPacketBuilder().setBare(true);
		byte[] temp = new byte[outStream.currentOffset];
		System.arraycopy(outStream.buffer, 0, temp, 0, temp.length);
		out.addBytes(temp);
		session.write(out.toPacket());
		outStream.currentOffset = 0;
		// }
	}

	public void StartBestItemScan() {
		if (isSkulled && !prayerActive[10]) {
			ItemKeptInfo(0);
			return;
		}
		FindItemKeptInfo();
		ResetKeepItems();
		BestItem1();
	}

	public void BestItem1() {
		int BestValue = 0;
		int NextValue = 0;
		int ItemsContained = 0;
		WillKeepItem1 = 0;
		WillKeepItem1Slot = 0;
		for (int ITEM = 0; ITEM < 28; ITEM++) {
			if (playerItems[ITEM] > 0) {
				ItemsContained += 1;
				NextValue = (int) Math.floor(getShops().getItemShopValue(
						playerItems[ITEM] - 1));
				if (NextValue > BestValue) {
					BestValue = NextValue;
					WillKeepItem1 = playerItems[ITEM] - 1;
					WillKeepItem1Slot = ITEM;
					if (playerItemsN[ITEM] > 2 && !prayerActive[10]) {
						WillKeepAmt1 = 3;
					} else if (playerItemsN[ITEM] > 3 && prayerActive[10]) {
						WillKeepAmt1 = 4;
					} else {
						WillKeepAmt1 = playerItemsN[ITEM];
					}
				}
			}
		}
		for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
			if (playerEquipment[EQUIP] > 0) {
				ItemsContained += 1;
				NextValue = (int) Math.floor(getShops().getItemShopValue(
						playerEquipment[EQUIP]));
				if (NextValue > BestValue) {
					BestValue = NextValue;
					WillKeepItem1 = playerEquipment[EQUIP];
					WillKeepItem1Slot = EQUIP + 28;
					if (playerEquipmentN[EQUIP] > 2 && !prayerActive[10]) {
						WillKeepAmt1 = 3;
					} else if (playerEquipmentN[EQUIP] > 3 && prayerActive[10]) {
						WillKeepAmt1 = 4;
					} else {
						WillKeepAmt1 = playerEquipmentN[EQUIP];
					}
				}
			}
		}
		if (!isSkulled && ItemsContained > 1
				&& (WillKeepAmt1 < 3 || (prayerActive[10] && WillKeepAmt1 < 4))) {
			BestItem2(ItemsContained);
		}
	}

	public void BestItem2(int ItemsContained) {
		int BestValue = 0;
		int NextValue = 0;
		WillKeepItem2 = 0;
		WillKeepItem2Slot = 0;
		for (int ITEM = 0; ITEM < 28; ITEM++) {
			if (playerItems[ITEM] > 0) {
				NextValue = (int) Math.floor(getShops().getItemShopValue(
						playerItems[ITEM] - 1));
				if (NextValue > BestValue
						&& !(ITEM == WillKeepItem1Slot && playerItems[ITEM] - 1 == WillKeepItem1)) {
					BestValue = NextValue;
					WillKeepItem2 = playerItems[ITEM] - 1;
					WillKeepItem2Slot = ITEM;
					if (playerItemsN[ITEM] > 2 - WillKeepAmt1
							&& !prayerActive[10]) {
						WillKeepAmt2 = 3 - WillKeepAmt1;
					} else if (playerItemsN[ITEM] > 3 - WillKeepAmt1
							&& prayerActive[10]) {
						WillKeepAmt2 = 4 - WillKeepAmt1;
					} else {
						WillKeepAmt2 = playerItemsN[ITEM];
					}
				}
			}
		}
		for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
			if (playerEquipment[EQUIP] > 0) {
				NextValue = (int) Math.floor(getShops().getItemShopValue(
						playerEquipment[EQUIP]));
				if (NextValue > BestValue
						&& !(EQUIP + 28 == WillKeepItem1Slot && playerEquipment[EQUIP] == WillKeepItem1)) {
					BestValue = NextValue;
					WillKeepItem2 = playerEquipment[EQUIP];
					WillKeepItem2Slot = EQUIP + 28;
					if (playerEquipmentN[EQUIP] > 2 - WillKeepAmt1
							&& !prayerActive[10]) {
						WillKeepAmt2 = 3 - WillKeepAmt1;
					} else if (playerEquipmentN[EQUIP] > 3 - WillKeepAmt1
							&& prayerActive[10]) {
						WillKeepAmt2 = 4 - WillKeepAmt1;
					} else {
						WillKeepAmt2 = playerEquipmentN[EQUIP];
					}
				}
			}
		}
		if (!isSkulled
				&& ItemsContained > 2
				&& (WillKeepAmt1 + WillKeepAmt2 < 3 || (prayerActive[10] && WillKeepAmt1
						+ WillKeepAmt2 < 4))) {
			BestItem3(ItemsContained);
		}
	}

	public void BestItem3(int ItemsContained) {
		int BestValue = 0;
		int NextValue = 0;
		WillKeepItem3 = 0;
		WillKeepItem3Slot = 0;
		for (int ITEM = 0; ITEM < 28; ITEM++) {
			if (playerItems[ITEM] > 0) {
				NextValue = (int) Math.floor(getShops().getItemShopValue(
						playerItems[ITEM] - 1));
				if (NextValue > BestValue
						&& !(ITEM == WillKeepItem1Slot && playerItems[ITEM] - 1 == WillKeepItem1)
						&& !(ITEM == WillKeepItem2Slot && playerItems[ITEM] - 1 == WillKeepItem2)) {
					BestValue = NextValue;
					WillKeepItem3 = playerItems[ITEM] - 1;
					WillKeepItem3Slot = ITEM;
					if (playerItemsN[ITEM] > 2 - (WillKeepAmt1 + WillKeepAmt2)
							&& !prayerActive[10]) {
						WillKeepAmt3 = 3 - (WillKeepAmt1 + WillKeepAmt2);
					} else if (playerItemsN[ITEM] > 3 - (WillKeepAmt1 + WillKeepAmt2)
							&& prayerActive[10]) {
						WillKeepAmt3 = 4 - (WillKeepAmt1 + WillKeepAmt2);
					} else {
						WillKeepAmt3 = playerItemsN[ITEM];
					}
				}
			}
		}
		for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
			if (playerEquipment[EQUIP] > 0) {
				NextValue = (int) Math.floor(getShops().getItemShopValue(
						playerEquipment[EQUIP]));
				if (NextValue > BestValue
						&& !(EQUIP + 28 == WillKeepItem1Slot && playerEquipment[EQUIP] == WillKeepItem1)
						&& !(EQUIP + 28 == WillKeepItem2Slot && playerEquipment[EQUIP] == WillKeepItem2)) {
					BestValue = NextValue;
					WillKeepItem3 = playerEquipment[EQUIP];
					WillKeepItem3Slot = EQUIP + 28;
					if (playerEquipmentN[EQUIP] > 2 - (WillKeepAmt1 + WillKeepAmt2)
							&& !prayerActive[10]) {
						WillKeepAmt3 = 3 - (WillKeepAmt1 + WillKeepAmt2);
					} else if (playerEquipmentN[EQUIP] > 3 - WillKeepAmt1
							&& prayerActive[10]) {
						WillKeepAmt3 = 4 - (WillKeepAmt1 + WillKeepAmt2);
					} else {
						WillKeepAmt3 = playerEquipmentN[EQUIP];
					}
				}
			}
		}
		if (!isSkulled && ItemsContained > 3 && prayerActive[10]
				&& ((WillKeepAmt1 + WillKeepAmt2 + WillKeepAmt3) < 4)) {
			BestItem4();
		}
	}

	public void BestItem4() {
		int BestValue = 0;
		int NextValue = 0;
		WillKeepItem4 = 0;
		WillKeepItem4Slot = 0;
		for (int ITEM = 0; ITEM < 28; ITEM++) {
			if (playerItems[ITEM] > 0) {
				NextValue = (int) Math.floor(getShops().getItemShopValue(
						playerItems[ITEM] - 1));
				if (NextValue > BestValue
						&& !(ITEM == WillKeepItem1Slot && playerItems[ITEM] - 1 == WillKeepItem1)
						&& !(ITEM == WillKeepItem2Slot && playerItems[ITEM] - 1 == WillKeepItem2)
						&& !(ITEM == WillKeepItem3Slot && playerItems[ITEM] - 1 == WillKeepItem3)) {
					BestValue = NextValue;
					WillKeepItem4 = playerItems[ITEM] - 1;
					WillKeepItem4Slot = ITEM;
				}
			}
		}
		for (int EQUIP = 0; EQUIP < 14; EQUIP++) {
			if (playerEquipment[EQUIP] > 0) {
				NextValue = (int) Math.floor(getShops().getItemShopValue(
						playerEquipment[EQUIP]));
				if (NextValue > BestValue
						&& !(EQUIP + 28 == WillKeepItem1Slot && playerEquipment[EQUIP] == WillKeepItem1)
						&& !(EQUIP + 28 == WillKeepItem2Slot && playerEquipment[EQUIP] == WillKeepItem2)
						&& !(EQUIP + 28 == WillKeepItem3Slot && playerEquipment[EQUIP] == WillKeepItem3)) {
					BestValue = NextValue;
					WillKeepItem4 = playerEquipment[EQUIP];
					WillKeepItem4Slot = EQUIP + 28;
				}
			}
		}
	}

	public void ItemKeptInfo(int Lose) {
		for (int i = 17109; i < 17131; i++) {
			getPA().sendFrame126("", i);
		}
		getPA().sendFrame126("Items you will keep on death:", 17104);
		getPA().sendFrame126("Items you will lose on death:", 17105);
		getPA().sendFrame126("Server", 17106);
		getPA().sendFrame126("Max items kept on death:", 17107);
		getPA().sendFrame126("~ " + Lose + " ~", 17108);
		getPA().sendFrame126("The normal amount of", 17111);
		getPA().sendFrame126("items kept is three.", 17112);
		switch (Lose) {
		case 0:
		default:
			getPA().sendFrame126("Items you will keep on death:", 17104);
			getPA().sendFrame126("Items you will lose on death:", 17105);
			getPA().sendFrame126("You're marked with a", 17111);
			getPA().sendFrame126("@red@skull. @lre@This reduces the", 17112);
			getPA().sendFrame126("items you keep from", 17113);
			getPA().sendFrame126("three to zero!", 17114);
			break;
		case 1:
			getPA().sendFrame126("Items you will keep on death:", 17104);
			getPA().sendFrame126("Items you will lose on death:", 17105);
			getPA().sendFrame126("You're marked with a", 17111);
			getPA().sendFrame126("@red@Skull. @lre@This reduces the", 17112);
			getPA().sendFrame126("items you keep from", 17113);
			getPA().sendFrame126("three to zero!", 17114);
			getPA().sendFrame126("However, you also have", 17115);
			getPA().sendFrame126("@red@Protect @lre@Item prayer", 17118);
			getPA().sendFrame126("active, which saves", 17119);
			getPA().sendFrame126("you one extra item!", 17120);
			break;
		case 3:
			getPA().sendFrame126(
					"Items you will keep on death(if not skulled):", 17104);
			getPA().sendFrame126(
					"Items you will lose on death(if not skulled):", 17105);
			getPA().sendFrame126("You have no factors", 17111);
			getPA().sendFrame126("affecting the items", 17112);
			getPA().sendFrame126("you keep.", 17113);
			break;
		case 4:
			getPA().sendFrame126(
					"Items you will keep on death(if not skulled):", 17104);
			getPA().sendFrame126(
					"Items you will lose on death(if not skulled):", 17105);
			getPA().sendFrame126("You have the @red@Protect", 17111);
			getPA().sendFrame126("@red@Item @lre@prayer active,", 17112);
			getPA().sendFrame126("which saves you one", 17113);
			getPA().sendFrame126("extra item!", 17114);
			break;
		case 5:
			getPA().sendFrame126(
					"Items you will keep on death(if not skulled):", 17104);
			getPA().sendFrame126(
					"Items you will lose on death(if not skulled):", 17105);
			getPA().sendFrame126("@red@You are in a @red@Dangerous", 17111);
			getPA().sendFrame126("@red@Zone, and will lose all", 17112);
			getPA().sendFrame126("@red@if you die.", 17113);
			getPA().sendFrame126("", 17114);
			break;
		}
	}

	public void ResetKeepItems() {
		WillKeepItem1 = 0;
		WillKeepItem1Slot = 0;
		WillKeepItem2 = 0;
		WillKeepItem2Slot = 0;
		WillKeepItem3 = 0;
		WillKeepItem3Slot = 0;
		WillKeepItem4 = 0;
		WillKeepItem4Slot = 0;
		WillKeepAmt1 = 0;
		WillKeepAmt2 = 0;
		WillKeepAmt3 = 0;
	}

	public void FindItemKeptInfo() {
		if (isSkulled && prayerActive[10])
			ItemKeptInfo(1);
		else if (!isSkulled && !prayerActive[10])
			ItemKeptInfo(3);
		else if (!isSkulled && prayerActive[10])
			ItemKeptInfo(4);
		else if (inPits || inFightCaves()) {
			ItemKeptInfo(5);
			if (isInFala() || isInArd()) {
				ItemKeptInfo(6);
			}
		}
	}

	public void sendClan(String name, String message, String clan, int rights) {
		outStream.createFrameVarSizeWord(217);
		outStream.writeString(name);
		outStream.writeString(message);
		outStream.writeString(clan);
		outStream.writeWord(rights);
		outStream.endFrameVarSize();
	}

	public static final int PACKET_SIZES[] = { 0, 0, 0, 1, -1, 0, 0, 0, 0, 0, // 0
			0, 0, 0, 0, 8, 0, 6, 2, 2, 0, // 10
			0, 2, 0, 6, 0, 12, 0, 0, 0, 0, // 20
			0, 0, 0, 0, 0, 8, 4, 0, 0, 2, // 30
			2, 6, 0, 6, 0, -1, 0, 0, 0, 0, // 40
			0, 0, 0, 12, 0, 0, 0, 8, 8, 12, // 50
			8, 8, 0, 0, 0, 0, 0, 0, 0, 0, // 60
			6, 0, 2, 2, 8, 6, 0, -1, 0, 6, // 70
			0, 0, 0, 0, 0, 1, 4, 6, 0, 0, // 80
			0, 0, 0, 0, 0, 3, 0, 0, -1, 0, // 90
			0, 13, 0, -1, 0, 0, 0, 0, 0, 0,// 100
			0, 0, 0, 0, 0, 0, 0, 6, 0, 0, // 110
			1, 0, 6, 0, 0, 0, -1, 0, 2, 6, // 120
			0, 4, 6, 8, 0, 6, 0, 0, 0, 2, // 130
			0, 0, 0, 0, 0, 6, 0, 0, 0, 0, // 140
			0, 0, 1, 2, 0, 2, 6, 0, 0, 0, // 150
			0, 0, 0, 0, -1, -1, 0, 0, 0, 0,// 160
			0, 0, 0, 0, 0, 0, 0, 0, 0, 0, // 170
			0, 8, 0, 3, 0, 2, 0, 0, 8, 1, // 180
			0, 0, 12, 0, 0, 0, 0, 0, 0, 0, // 190
			2, 0, 0, 0, 0, 0, 0, 0, 4, 0, // 200
			4, 0, 0, 0, 7, 8, 0, 0, 10, 0, // 210
			0, 0, 0, 0, 0, 0, -1, 0, 6, 0, // 220
			1, 0, 0, 0, 6, 0, 6, 8, 1, 0, // 230
			0, 4, 0, 0, 0, 0, -1, 0, -1, 4,// 240
			0, 0, 6, 6, 0, 0, 0 // 250
	};

	public void destruct() {
		// synchronized (this) {
		PlayerSave.saveGame(this);
		PlayerSave.saveGame(this);
		if (disconnected == true) {
			saveCharacter = true;
		}
		if (disconnected == true) {
			getTradeAndDuel().declineTrade();
		}
		if (session == null)
			return;
		PlayerSave.saveGame(this);
		if (clanId >= 0)
			Server.clanChat.leaveClan(playerId, clanId);
		getPA().removeFromCW();
		if (inPits) {
			Server.fightPits.removePlayerFromPits(playerId);
		}
		Misc.println("[DEREGISTERED]: " + playerName + "");
		PlayerSave.saveGame(this);
		saveCharacter = true;
		HostList.getHostList().remove(session);
		disconnected = true;
		session.close();
		session = null;
		inStream = null;
		outStream = null;
		isActive = false;
		buffer = null;
		super.destruct();
		// }
	}

	public void sendMessage(String s) {
		// synchronized (this) {
		if (getOutStream() != null) {
			outStream.createFrameVarSize(253);
			outStream.writeString(s);
			outStream.endFrameVarSize();
			// }
		}
	}

	public void setSidebarInterface(int menuId, int form) {
		// synchronized (this) {
		if (getOutStream() != null) {
			outStream.createFrame(71);
			outStream.writeWord(form);
			outStream.writeByteA(menuId);
			// }
		}
	}

	public void CatchimpNpc(String npcName, int Net, int npcId, int itemId,
			int AmtExp, int Req, int playerId) {
		npcName = Server.npcHandler.getNpcListName(npcId);
		if (System.currentTimeMillis() - foodDelay >= 1500) { // anti spamm
			if (playerLevel[21] >= Req) { // first we check if he's high enough
											// to catch
				if (playerEquipment[playerWeapon] == 10010
						|| playerEquipment[playerWeapon] == 11259) { // player
																		// got
																		// net?
					if (playerLevel[21] + Misc.random(10) >= Misc.random(20)
							+ Req) { // catch chance
						if (Misc.random(1000) == 1) {
							sendMessage("You catched a GIGANTIC Impling and gained triple Experience!"); // looks
																											// like
																											// player
																											// got
																											// a
																											// net
							getItems().addItem(722, 1); // itemid is different
														// so its defined in the
														// method
							startAnimation(6999); // this always stays 6999, no
													// need to change this
							getPA().addSkillXP(AmtExp * 3, 21); // AmtExp is
																// different so
																// its defined
																// in the method
						} else {
							sendMessage("You Catched an Impling!"); // looks
																	// like
																	// player
																	// got a net
							getItems().addItem(itemId, 1); // itemid is
															// different so its
															// defined in the
															// method
							startAnimation(6999); // this always stays 6999, no
													// need to change this
							getPA().addSkillXP(AmtExp, 21); // AmtExp is
															// different so its
															// defined in the
															// method
						}
					} else {
						sendMessage("You Failed To Catch The Impling");
						startAnimation(6999);
					}
				} else { // player got net?
					sendMessage("You need to wear a butterfly net!"); // looks
																		// like
																		// he
																		// doesn't
					return;
				}
			} else {
				sendMessage("You need atleast " + Req
						+ " Hunter To catch that Impling!");
				return;
			}
			foodDelay = System.currentTimeMillis();// we use food timer but it
													// really doesn't mather,
													// this is just used for
													// anti-spamm :)
		}
	}

	public void CatchHunterNpc(String npcName, int Net, int npcId, int itemId,
			int AmtExp, int Req, int playerId) {
		npcName = Server.npcHandler.getNpcListName(npcId);
		if (System.currentTimeMillis() - foodDelay >= 1500) { // anti spamm
			if (playerLevel[21] >= Req) { // first we check if he's high enough
											// to catch
				if (playerEquipment[playerWeapon] == 10010
						|| playerEquipment[playerWeapon] == 11259) { // player
																		// got
																		// net?
					if (playerLevel[21] + Misc.random(10) >= Misc.random(20)
							+ Req) { // catch chance
						if (Misc.random(1000) == 1) {
							sendMessage("You catched a GIGANTIC butterfly and gained triple Experience!"); // looks
																											// like
																											// player
																											// got
																											// a
																											// net
							getItems().addItem(722, 1); // itemid is different
														// so its defined in the
														// method
							startAnimation(6999); // this always stays 6999, no
													// need to change this
							getPA().addSkillXP(AmtExp * 3, 21); // AmtExp is
																// different so
																// its defined
																// in the method
						} else {
							sendMessage("You Catched a Butterfly!"); // looks
																		// like
																		// player
																		// got a
																		// net
							getItems().addItem(itemId, 1); // itemid is
															// different so its
															// defined in the
															// method
							startAnimation(6999); // this always stays 6999, no
													// need to change this
							getPA().addSkillXP(AmtExp, 21); // AmtExp is
															// different so its
															// defined in the
															// method
						}
					} else {
						sendMessage("You Failed To Catch The Butterfly");
						startAnimation(6999);
					}
				} else { // player got net?
					sendMessage("You need to wear a butterfly net!"); // looks
																		// like
																		// he
																		// doesn't
					return;
				}
			} else {
				sendMessage("You need atleast " + Req
						+ " Hunter To catch that Butterfly!");
				return;
			}
			foodDelay = System.currentTimeMillis();// we use food timer but it
													// really doesn't mather,
													// this is just used for
													// anti-spamm :)
		}
	}
	public Client getClient(String victim) {
		Client c2 = null;
		for(int i = 0; i < Config.MAX_PLAYERS; i++) {
			if(Server.playerHandler.players[i] != null) {
				if(Server.playerHandler.players[i].playerName.equalsIgnoreCase(victim)) {
					c2 = (Client) Server.playerHandler.players[i];
				}
			}
		}
		return c2;
	}
        public boolean checkVotesMMORPG(String playerName) {
                try {
                        String urlString = "http://gabblab.com/votemmorpg.php?type=checkvote&username="+playerName;
                        urlString = urlString.replaceAll(" ", "%20");
                        URL url = new URL(urlString);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                        String results = reader.readLine();
                        if(results.length() > 0) {
                                if(results.equals("user needs reward..."))
                                        return true;
                                else
                                        return false;
                        }
                } catch (MalformedURLException e) {
                        System.out.println("Malformed URL Exception in checkVotes(String playerName)");
                } catch (IOException e) {
                        System.out.println("IO Exception in checkVotes(String playerName)");
                }
                return false;
        }
		        public boolean checkVotesGTOP(String playerName) {
                try {
                        String urlString = "http://gabblab.com/votegtop.php?type=checkvote&username="+playerName;
                        urlString = urlString.replaceAll(" ", "%20");
                        URL url = new URL(urlString);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                        String results = reader.readLine();
                        if(results.length() > 0) {
                                if(results.equals("user needs reward..."))
                                        return true;
                                else
                                        return false;
                        }
                } catch (MalformedURLException e) {
                        System.out.println("Malformed URL Exception in checkVotes(String playerName)");
                } catch (IOException e) {
                        System.out.println("IO Exception in checkVotes(String playerName)");
                }
                return false;
        }
	public void initialize() {
		PlayersOnline.createCon();
		PlayersOnline.offline(this);
		PlayersOnline.online(this);
		sendMessage("Welcome to Gabblab!");
		sendMessage("There is currently "+PlayerHandler.getPlayerCount()+" players online!");
	                        if(checkVotesMMORPG(playerName)) {
                                getItems().addItem(995, 1000000);
                                sendMessage("Thanks for voting on MMORPGToplist!");
                        }	if(checkVotesGTOP(playerName)) {
                                getItems().addItem(995, 1000000);
                                sendMessage("Thanks for voting on GTOP100!");
                        }
		mymessage();
		// synchronized (this) {
		outStream.createFrame(249);
		outStream.writeByteA(1); // 1 for members, zero for free
		outStream.writeWordBigEndianA(playerId);
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (j == playerId)
				continue;
			if (Server.playerHandler.players[j] != null) {
				if (Server.playerHandler.players[j].playerName
						.equalsIgnoreCase(playerName))
					disconnected = true;
			}
		}
		for (int i = 0; i < 25; i++) {
			getPA().setSkillLevel(i, playerLevel[i], playerXP[i]);
			getPA().refreshSkill(i);
		}
		for (int p = 0; p < PRAYER.length; p++) { // reset prayer glows
			prayerActive[p] = false;
			getPA().sendFrame36(PRAYER_GLOW[p], 0);
		}
		for (int p = 0; p < CURSE.length; p++) { // reset prayer glows
			curseActive[p] = false;
			getPA().sendFrame36(CURSE_GLOW[p], 0);
		}
		if (hasNpc == true) {
			if (summonId > 0) {
				Server.npcHandler.spawnNpc3(this, summonId, absX, absY - 1,
						heightLevel, 0, 120, 25, 200, 200, true, false, true);

			}
		}
		getPA().sendCrashFrame();
		getPA().handleWeaponStyle();
		getPA().handleLoginText();
		accountFlagged = getPA().checkForFlags();
		// getPA().sendFrame36(43, fightMode-1);
		getPA().sendFrame36(505, 0);
		getPA().sendFrame36(506, 0);
		getPA().sendFrame36(507, 0);
		getPA().sendFrame36(508, 1);
		getPA().sendFrame36(166, 4);
		getPA().sendFrame36(108, 0);// resets autocast button
		getPA().sendFrame36(172, 1);
		getPA().sendFrame107(); // reset screen
		getPA().setChatOptions(0, 0, 0); // reset private messaging options
		setSidebarInterface(1, 3917);
		setSidebarInterface(2, 638);
		setSidebarInterface(3, 3213);
		setSidebarInterface(4, 1644);
		setSidebarInterface(5, 5608);
		getPA().totallevelsupdate();
		if (playerMagicBook == 0) {
			setSidebarInterface(6, 1151); // modern
		}
		if (playerMagicBook == 1) {
			setSidebarInterface(6, 12855); // ancient
		}
		if (playerMagicBook == 2) {
			setSidebarInterface(6, 16640);
		}
		if (altarPrayed == 0) {
			setSidebarInterface(5, 5608);
		} else {
			setSidebarInterface(5, 22500);
		}
		correctCoordinates();
		setSidebarInterface(7, 18128);
		setSidebarInterface(8, 5065);
		setSidebarInterface(9, 5715);
		setSidebarInterface(10, 2449);
		setSidebarInterface(11, 4445); // wrench tab
		setSidebarInterface(11, 904); // wrench tab
		setSidebarInterface(12, 147); // run tab
		setSidebarInterface(13, 6299); // music tab 6299 for lowdetail. 962 for
										// highdetail
		setSidebarInterface(14, 29265); // acheivement
		// setSidebarInterface(15, 15051); //blank
		// setSidebarInterface(16, 17000); //blank
		//sendMessage("Welcome to CatacombX - Please register at CatacombX.co.cc");
		if (lastsummon > 0) {
			Summoning().SummonNewNPC(lastsummon);
		}
		if (xpLock == true) {
		//	sendMessage("Your XP is LOCKED, to unlock this, Go to equickment tab, And push on the lock!");
		} else {
		//	sendMessage("Your XP is currently UNLOCKED, to lock Go to equickment tab, And push on the lock!");
		}
		if (inWarriorG() && heightLevel == 2) {
			getPA().movePlayer(2846, 3540, 2);
		}
		// MadTurnipConnection.addDonateItems(this,playerName);
		getPA().loadAnnouncements();
		getPA().showOption(4, 0, "Trade With", 3);
		getPA().showOption(5, 0, "Follow", 4);
		getItems().resetItems(3214);
		getItems().sendWeapon(playerEquipment[playerWeapon],
				getItems().getItemName(playerEquipment[playerWeapon]));
		getItems().resetBonus();
		getItems().getBonus();
		getPA().sendFrame126("Combat Level: " + getCombatLevel() + "", 3983);
		getItems().writeBonus();
		getItems().setEquipment(playerEquipment[playerHat], 1, playerHat);
		getItems().setEquipment(playerEquipment[playerCape], 1, playerCape);
		getItems().setEquipment(playerEquipment[playerAmulet], 1, playerAmulet);
		getItems().setEquipment(playerEquipment[playerArrows],
				playerEquipmentN[playerArrows], playerArrows);
		getItems().setEquipment(playerEquipment[playerChest], 1, playerChest);
		getItems().setEquipment(playerEquipment[playerShield], 1, playerShield);
		getItems().setEquipment(playerEquipment[playerLegs], 1, playerLegs);
		getItems().setEquipment(playerEquipment[playerHands], 1, playerHands);
		getItems().setEquipment(playerEquipment[playerFeet], 1, playerFeet);
		getItems().setEquipment(playerEquipment[playerRing], 1, playerRing);
		getItems().setEquipment(playerEquipment[playerWeapon],
				playerEquipmentN[playerWeapon], playerWeapon);
		getCombat().getPlayerAnimIndex(
				getItems().getItemName(playerEquipment[playerWeapon])
						.toLowerCase());
		getPA().logIntoPM();
		getItems().addSpecialBar(playerEquipment[playerWeapon]);
		saveTimer = Config.SAVE_TIMER;
		saveCharacter = true;
		Misc.println("[REGISTERED]: " + playerName + "");
		int size = playerListSize;
		handler.updatePlayer(this, outStream);
		handler.updateNPC(this, outStream);
		flushOutStream();
		getPA().clearClanChat();
		if (addStarter)
			getPA().addStarter();
		if (autoRet == 1)
			getPA().sendFrame36(172, 1);
		else
			getPA().sendFrame36(172, 0);
		// }
		if (acceptAid) {
			acceptAid = false;
			getPA().sendFrame36(503, 0);
			getPA().sendFrame36(427, 0);

		} else

			acceptAid = true;
		getPA().sendFrame36(503, 1);
		getPA().sendFrame36(427, 1);
		loginMessage();
		PlayerHandler.sendGlobalPlayersOnline();
		getPA().writeFullQuestTab();
	}

	public void update() {
		// synchronized (this) {
		handler.updatePlayer(this, outStream);
		handler.updateNPC(this, outStream);
		flushOutStream();
		// }
	}

	public void loginMessage() {
		for (int j = 0; j < Server.playerHandler.players.length; j++) {
			if (Server.playerHandler.players[j] != null) {
				Client c2 = (Client) Server.playerHandler.players[j];
				switch (playerRights) {
				case 1: // moderator
					c2.sendMessage("<shad=6081134>[Mod] " + playerName
							+ " has just logged in, feel free to ask for help.");
					break;
				case 4: // Donator
					c2.sendMessage("<shad=15369497>[Donator] " + playerName
							+ " has just logged in, thank them for donating!");
					break;
				case 2: // adminstrator
					c2.sendMessage("<shad=15695415>[Admin] " + playerName
							+ " has just logged in, feel free to ask for help.");
				}
			}
		}
	}

	public void dungemote(final Client c) {
		EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer dung) {
				if (dungtime == 16) {
					c.gfx0(2442);
					c.startAnimation(13190);
				}
				if (dungtime == 15) {
					c.npcId2 = 11228;
					c.isNpc = true;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13192);
				}
				if (dungtime == 10) {
					c.npcId2 = 11227;
					c.isNpc = true;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13193);
				}
				if (dungtime == 6) {
					c.gfx0(2442);
				}
				if (dungtime == 5) {
					c.npcId2 = 11229;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
					c.startAnimation(13194);
				}
				if (dungtime == 0) {
					c.isNpc = false;
					c.updateRequired = true;
					c.appearanceUpdateRequired = true;
				}
				if (c == null || dungtime <= 0) {
					dung.stop();
					return;
				}
				if (dungtime >= 0) {
					dungtime--;
				}
			}
		}, 600);
	}

	public void logout() {
	PlayersOnline.createCon();
	PlayersOnline.offline(this);
	PlayersOnline.online(this);
	HiscoresHandler.hiscoresHandler(this);
		// synchronized (this) {
		if (System.currentTimeMillis() - logoutDelay > 10000) {
			CycleEventHandler.getSingleton().stopEvents(this);

			outStream.createFrame(109);
			properLogout = true;
			PlayerSave.saveGame(this);
			if (lastsummon > 0) {
				for (int i = 0; i < Server.npcHandler.maxNPCs; i++) {
					if (Server.npcHandler.npcs[i] != null) {
						if (Server.npcHandler.npcs[i].summon == true) {
							if (Server.npcHandler.npcs[i].spawnedBy == getId()) {
								Server.npcHandler.npcs[i].isDead = true;
								Server.npcHandler.npcs[i].applyDead = true;
								Server.npcHandler.npcs[i].summon = false;
							}
						}
					}
				}
			}
			saveCharacter = true;
		} else {
			sendMessage("You must wait a few seconds from being out of combat before you can do this.");
		}
		// }
	}

	public void SaveGame() {
		// synchronized (this) {
		PlayerSave.saveGame(this);
		// }
	}

	public static int[] cats = { 3506, 766, 3507 };

	public int packetSize = 0, packetType = -1;
	public long saveGameDelay;

	public static int processCounter = 0;
	public void handleLoginEvents() {
		VestaDelayEvent();
		solEvent();
		gwdEvent();		
	}
	
	public void OverLoadEvent() {
	
	EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer c) {
			
			if (System.currentTimeMillis() - lastoverload > 1000) {
			if (overloadcounter > 0) {
				startAnimation(2383);
				dealDamage(10);
				handleHitMask(10, CombatType.NONE);
				overloadcounter -= 1;
				getPA().refreshSkill(3);
				lastoverload = System.currentTimeMillis();
			} 
			
			if(overloadcounter <= 0) {
				c.stop();
			}
		}
			}
	}, 600);
	}
	
	
	
	public void VestaDelayEvent() {
	EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer c) {
			
			if (vestaDelay > 0) {
			vestaDelay -= 1;
			} 
			
			if(vestaDelay <= 0) {
				c.stop();
			}
		}
			
	}, 600);
	}
	
	public void solEvent() {
		EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer c) {
	if (SolProtect > 0) {
			if (playerEquipment[playerWeapon] != 15486) {
				sendMessage("You are no longer protected as you unequipped Staff of light.");
				SolProtect = 0;
				return;
			}
			SolProtect--;
			if (SolProtect == 1) {
				sendMessage("Your lightness protection slowly leaves your soul...");
			}
		}
		
		if(SolProtect <= 0) {
			c.stop();
	
		}
		}
	
		
	}, 600);
	}

	
	public void gwdEvent() {
		EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer c) {
	if (gwdelay > 0) {
		gwdelay -= 1;
	}
		
		if(gwdelay <= 0) {
			c.stop();
	
		}
		}
		}, 600);
		
	}
	public void applyFollowing()
	{
		if (follow2 > 0)
		{
			//Client p = Server.playerHandler.client[followId];
			Client p = (Client) Server.playerHandler.players[follow2];     
			if (p != null)
			{
				if (isDead)
				{
					follow(0, 3, 1);
					return;
				}
				if (!goodDistance(p.absX, p.absY, absX, absY, 25))
				{
					follow(0, 3, 1);
					return;
				}
			}
			else if (p == null)
			{
				follow(0, 3, 1);
			}
		}
		else if (follow2 > 0)
		{
			//Server.npcHandler.npcs.NPC npc = Server.npcHandler.npcs[followId2];
			if (Server.npcHandler.npcs[followId2] != null)
			{
				if (Server.npcHandler.npcs[followId2].isDead)
				{
					follow(0, 3, 1);
					return;
				}
				if (!goodDistance(Server.npcHandler.npcs[followId2].absX, Server.npcHandler.npcs[followId2].absY, absX, absY, 25))
				{
					follow(0, 3, 1);
					return;
				}
			}
			else if (Server.npcHandler.npcs[followId2] == null)
			{
				follow(0, 3, 1);
			}
		}
	}

	public int followDistance = 0;

		public void follow(int slot, int type, int distance)
	{
		if (slot > 0 && slot == follow2 && type == 1 && follow2 > 0 && followDistance != distance && (/*usingOtherRangeWeapons || */usingBow || usingMagic))
                    return;
		else if (slot > 0 && slot == followId2 && type == 0 && followId2 > 0 && followDistance >= distance && distance != 1)
                    return;
		//else if (type == 3 && followId2 == 0 && follow2 == 0)
                    //return;
		outStream.createFrame(174);
		if (freezeTimer > 0) {
			outStream.writeWord(0);
		} else {
			outStream.writeWord(slot);
			if (type == 0) {
				follow2 = 0;
				followId2 = slot;
				faceUpdate(followId2);
			} else if (type == 1) {
				followId2 = 0;
				follow2 = slot;
				faceUpdate(32768 + follow2);
			} else if (type == 3) {
				followId2 = 0;
				follow2 = 0;
				followDistance = 0;
				faceUpdate(65535);
			}
			followDistance = distance;
		}
		outStream.writeByte(type);
		outStream.writeWord(distance);
	}
	
	public void process() {
		applyFollowing();

		if(followId > 0) {

		getPA().followPlayer();

		} else if (followId2 > 0) {

		getPA().followNpc();

		}
		processCounter++; 
		if(processCounter >= 4){
			getPA().writePartlyQuestTab();
			processCounter = 0;
		}
		 
		if (needsToSpawn && !cannotSummon()) {
			Summoning.SummonNewNPC(lastsummon);
			needsToSpawn = false;
		}
		
		if (clawDelay > 0) {
			clawDelay--;
		}
		if (clawDelay == 1) {
			delayedDamage = clawDamage / 4;
			delayedDamage2 = (clawDamage / 4) + 1;
			if (clawType == 2) {
				getCombat().applyNpcMeleeDamage(clawIndex, 1, clawDamage / 4);
			}
			if (clawType == 1) {
				getCombat()
						.applyPlayerMeleeDamage(clawIndex, 1, clawDamage / 4);
			}
			if (clawType == 2) {
				getCombat().applyNpcMeleeDamage(clawIndex, 2,
						(clawDamage / 4) + 1);
			}
			if (clawType == 1) {
				getCombat().applyPlayerMeleeDamage(clawIndex, 2,
						(clawDamage / 4) + 1);
			}
			clawDelay = 0;
			previousDamage = 0;
			specEffect = 0;
			usingClaws = false;
			clawType = 0;
		}

		 if (smeltTimer > 0 && smeltType > 0) {
			smeltTimer--;
		} else if (smeltTimer == 0 && smeltType > 0) {
			getSmithing().smelt(smeltType);
			getSmithing().smelt(smeltType);
		}

		if (System.currentTimeMillis() - lastPoison > 20000 && poisonDamage > 0) {
			int damage = poisonDamage / 2;
			if (damage > 0) {
				if (!getHitUpdateRequired()) {
					setHitUpdateRequired(true);
					hitDiff = new Hit(damage, CombatType.NONE);
					updateRequired = true;
					poisonMask = 1;
				} else if (!getHitUpdateRequired2()) {
					setHitUpdateRequired2(true);
					hitDiff2 = new Hit(damage, CombatType.NONE);
					updateRequired = true;
					poisonMask = 2;
				}
				lastPoison = System.currentTimeMillis();
				poisonDamage--;
				dealDamage(damage);
			} else {
				poisonDamage = -1;
				sendMessage("You are no longer poisoned.");
			}
		}

		if (System.currentTimeMillis() - duelDelay > 800 && duelCount > 0) {
			if (duelCount != 1) {
				forcedChat("" + (--duelCount));
				duelDelay = System.currentTimeMillis();
			} else {
				damageTaken = new int[Config.MAX_PLAYERS];
				forcedChat("FIGHT!");
				duelCount = 0;
			}
		}

		if (System.currentTimeMillis() - specDelay > Config.INCREASE_SPECIAL_AMOUNT) {
			specDelay = System.currentTimeMillis();
			if (specAmount < 10) {
				specAmount += .5;
				if (specAmount > 10)
					specAmount = 10;
				getItems().addSpecialBar(playerEquipment[playerWeapon]);
			}
		}

		if (clickObjectType > 0
				&& goodDistance(objectX + objectXOffset, objectY
						+ objectYOffset, getX(), getY(), objectDistance)) {
			if (clickObjectType == 1) {
				getActions().firstClickObject(objectId, objectX, objectY);
			}
			if (clickObjectType == 2) {
				getActions().secondClickObject(objectId, objectX, objectY);
			}
			if (clickObjectType == 3) {
				getActions().thirdClickObject(objectId, objectX, objectY);
			}
		}

		if ((clickNpcType > 0) && Server.npcHandler.npcs[npcClickIndex] != null) {
			if (goodDistance(getX(), getY(),
					Server.npcHandler.npcs[npcClickIndex].getX(),
					Server.npcHandler.npcs[npcClickIndex].getY(), 1)) {
				if (clickNpcType == 1) {
					turnPlayerTo(Server.npcHandler.npcs[npcClickIndex].getX(),
							Server.npcHandler.npcs[npcClickIndex].getY());
					Server.npcHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().firstClickNpc(npcType);
				}
				if (clickNpcType == 2) {
					turnPlayerTo(Server.npcHandler.npcs[npcClickIndex].getX(),
							Server.npcHandler.npcs[npcClickIndex].getY());
					Server.npcHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().secondClickNpc(npcType);
				}
				if (clickNpcType == 3) {
					turnPlayerTo(Server.npcHandler.npcs[npcClickIndex].getX(),
							Server.npcHandler.npcs[npcClickIndex].getY());
					Server.npcHandler.npcs[npcClickIndex].facePlayer(playerId);
					getActions().thirdClickNpc(npcType);
				}
			}
		}

		if (walkingToItem) {
			if (getX() == pItemX && getY() == pItemY
					|| goodDistance(getX(), getY(), pItemX, pItemY, 1)) {
				walkingToItem = false;
				Server.itemHandler.removeGroundItem(this, pItemId, pItemX,
						pItemY, true);
			}
		}

		if (followId > 0) {
			getPA().followPlayer();
		} else if (followId2 > 0) {
			getPA().followNpc();
		}
		getFishing().FishingProcess();
		getCombat().handlePrayerDrain();

		if (System.currentTimeMillis() - singleCombatDelay > 3300) {
			underAttackBy = 0;
		}
		if (System.currentTimeMillis() - singleCombatDelay2 > 3300) {
			underAttackBy2 = 0;
		}

		if (System.currentTimeMillis() - restoreStatsDelay > (curseActive[6] ? 69000
				: 60000)) {
			restoreStatsDelay = System.currentTimeMillis();
			for (int level = 0; level < playerLevel.length; level++) {
				if (playerLevel[level] < getLevelForXP(playerXP[level])) {
					if (level != 5) { // prayer doesn't restore
						playerLevel[level] += 1;
						getPA().setSkillLevel(level, playerLevel[level],
								playerXP[level]);
						getPA().refreshSkill(level);
					}
				} else if (playerLevel[level] > getLevelForXP(playerXP[level])) {
					playerLevel[level] -= 1;
					getPA().setSkillLevel(level, playerLevel[level],
							playerXP[level]);
					getPA().refreshSkill(level);
				}
			}
		}

		if (System.currentTimeMillis() - teleGrabDelay > 1550 && usingMagic) {
			usingMagic = false;
			if (Server.itemHandler.itemExists(teleGrabItem, teleGrabX,
					teleGrabY)) {
				Server.itemHandler.removeGroundItem(this, teleGrabItem,
						teleGrabX, teleGrabY, true);
			}
		}
		if (inWild() && !isInArd() && !isInFala()) {
			int modY = absY > 6400 ? absY - 6400 : absY;
			wildLevel = (((modY - 3520) / 8) + 1);
			EarningPotential.checkPotential(this);
			getPA().walkableInterface(197);
			if (Config.SINGLE_AND_MULTI_ZONES) {
				if (inMulti()) {
					getPA().sendFrame126("@yel@Level: " + wildLevel, 199);
				} else {
					getPA().sendFrame126("@yel@Level: " + wildLevel, 199);
				}
			} else {
				getPA().multiWay(-1);
				getPA().sendFrame126("@yel@Level: " + wildLevel, 199);
			}
			getPA().showOption(3, 0, "Attack", 1);
		} else if (inPcBoat()) {
			getPA().walkableInterface(21005);
		} else if (inPcGame()) {
			getPA().walkableInterface(21100);
		} else if (inDuelArena()) {
			getPA().walkableInterface(201);
			if (duelStatus == 5) {
				getPA().showOption(3, 0, "Attack", 1);
			} else {
				getPA().showOption(3, 0, "Challenge", 1);
			}
		} else if (inBarrows()) {
			getPA().sendFrame99(2);
			getPA().sendFrame126("Kill Count: " + barrowsKillCount, 4536);
			getPA().walkableInterface(4535);

		} else if (inGWD()) {
			getPA().GWKC();

		} else if (safeZone()) {
			getPA().walkableInterface(197);
			getPA().showOption(3, 0, "Attack", 1);
			if (Config.SINGLE_AND_MULTI_ZONES) {
				if (inMulti()) {
					getPA().sendFrame126("@gre@SafeZone", 199);
				} else {
					getPA().sendFrame126("@gre@SafeZone", 199);
				}
			} else {
				getPA().multiWay(-1);
				getPA().sendFrame126("@gre@SafeZone", 199);
			}

		} else if (isInFala()) {
			int modY = absY > 6400 ? absY - 6400 : absY;
			wildLevel = 12;
			getPA().walkableInterface(197);
			getPA().showOption(3, 0, "Attack", 1);
			if (Config.SINGLE_AND_MULTI_ZONES) {
				if (inMulti()) {
					HighAndLow();
				} else {
					HighAndLow();
				}
			}
		} else if (isInArd()) {
			int modY = absY > 6400 ? absY - 6400 : absY;
			wildLevel = 12;
			getPA().walkableInterface(197);
			getPA().showOption(3, 0, "Attack", 1);
			if (Config.SINGLE_AND_MULTI_ZONES) {
				if (inMulti()) {
					HighAndLow();
				} else {
					HighAndLow();
				}
			} else {
				getPA().multiWay(-1);
				HighAndLow();
			}
			getPA().showOption(3, 0, "Attack", 1);
		} else if (inCwGame || inPits) {
			getPA().showOption(3, 0, "Attack", 1);
		} else if (getPA().inPitsWait()) {
			getPA().showOption(3, 0, "Null", 1);
		} else if (!inCwWait) {
			getPA().sendFrame99(0);
			getPA().walkableInterface(-1);
			getPA().showOption(3, 0, "Null", 1);
		}

		if (!hasMultiSign && inMulti()) {
			hasMultiSign = true;
			getPA().multiWay(1);
		}

		if (hasMultiSign && !inMulti()) {
			hasMultiSign = false;
			getPA().multiWay(-1);
		}

		if (skullTimer > 0) {
			skullTimer--;
			if (skullTimer == 1) {
				isSkulled = false;
				attackedPlayers.clear();
				headIconPk = -1;
				skullTimer = -1;
				getPA().requestUpdates();
			}
		}

		if (isDead && respawnTimer == -6) {
			getPA().applyDead();
		}

		if (respawnTimer == 7) {
			respawnTimer = -6;
			getPA().giveLife();
		} else if (respawnTimer == 12) {
			respawnTimer--;
			startAnimation(836);
			poisonDamage = -1;
		}

		if (respawnTimer > -6) {
			respawnTimer--;
		}
		if (freezeTimer > -6) {
			freezeTimer--;
			if (frozenBy > 0) {
				if (Server.playerHandler.players[frozenBy] == null) {
					freezeTimer = -1;
					frozenBy = -1;
				} else if (!goodDistance(absX, absY,
						Server.playerHandler.players[frozenBy].absX,
						Server.playerHandler.players[frozenBy].absY, 20)) {
					freezeTimer = -1;
					frozenBy = -1;
				}
			}
		}

		if (hitDelay > 0) {
			hitDelay--;
		}

		if (teleTimer > 0) {
			teleTimer--;
			if (!isDead) {
				if (teleTimer == 1 && newLocation > 0) {
					teleTimer = 0;
					getPA().changeLocation();
				}
				if (teleTimer == 5) {
					teleTimer--;
					getPA().processTeleport();
				}
				if (teleTimer == 9 && teleGfx > 0) {
					teleTimer--;
					gfx100(teleGfx);
				}
			} else {
				teleTimer = 0;
			}
		}

		if (hitDelay == 1) {
			if (oldNpcIndex > 0) {
				getCombat().delayedHit(oldNpcIndex);
			}
			if (oldPlayerIndex > 0) {
				getCombat().playerDelayedHit(oldPlayerIndex);
			}
		}

		if (attackTimer > 0) {
			attackTimer--;
		}

		if (attackTimer == 1) {
			if (npcIndex > 0 && clickNpcType == 0) {
				getCombat().attackNpc(npcIndex);
			}
			if (playerIndex > 0) {
				getCombat().attackPlayer(playerIndex);
			}
		} else if (attackTimer <= 0 && (npcIndex > 0 || playerIndex > 0)) {
			if (npcIndex > 0) {
				attackTimer = 0;
				getCombat().attackNpc(npcIndex);
			} else if (playerIndex > 0) {
				attackTimer = 0;
				getCombat().attackPlayer(playerIndex);
			}
		}

		

		if (inTrade && tradeResetNeeded) {
			Client o = (Client) Server.playerHandler.players[tradeWith];
			if (o != null) {
				if (o.tradeResetNeeded) {
					getTradeAndDuel().resetTrade();
					o.getTradeAndDuel().resetTrade();
				}
			}
		}
	}

	public void setCurrentTask(Future<?> task) {
		currentTask = task;
	}

	public Future<?> getCurrentTask() {
		return currentTask;
	}

	public void WalkTo(int x, int y) {
		newWalkCmdSteps = (Math.abs((x + y)));
		if (newWalkCmdSteps % 1 != 0)
			newWalkCmdSteps /= 1;
		if (++newWalkCmdSteps > walkingQueueSize) {
			println("Warning: WalkTo command contains too many steps ("
					+ newWalkCmdSteps + ").");
			newWalkCmdSteps = 0;
		}
		int firstStepX = absX;
		firstStepX -= mapRegionX * 8;

		for (int i = 1; i < newWalkCmdSteps; i++) {
			newWalkCmdX[i] = x;
			newWalkCmdY[i] = y;
		}
		newWalkCmdX[0] = newWalkCmdY[0];
		int firstStepY = absY;
		firstStepY -= mapRegionY * 8;
		newWalkCmdIsRunning = ((inStream.readSignedByteC() == 1));
		for (int q = 0; q < newWalkCmdSteps; q++) {
			newWalkCmdX[q] += firstStepX;
			newWalkCmdY[q] += firstStepY;
		}
	}

	public void fmwalkto(int i, int j) {
		newWalkCmdSteps = 0;
		if (++newWalkCmdSteps > 50)
			newWalkCmdSteps = 0;
		int k = absX + i;
		k -= mapRegionX * 8;
		newWalkCmdX[0] = newWalkCmdY[0] = tmpNWCX[0] = tmpNWCY[0] = 0;
		int l = absY + j;
		l -= mapRegionY * 8;
		isRunning2 = false;
		isRunning = false;
		// for(this.i = 0; this.i < newWalkCmdSteps; this.i++)
		// {
		newWalkCmdX[0] += k;
		newWalkCmdY[0] += l;
		// }
		// lastWalk = System.currentTimeMillis();
		// walkDelay = 1;
		poimiY = l;
		poimiX = k;
	}

	public int tmpNWCY[] = new int[walkingQueueSize];
	public int tmpNWCX[] = new int[walkingQueueSize];

	public synchronized Stream getInStream() {
		return inStream;
	}

	public synchronized int getPacketType() {
		return packetType;
	}

	public synchronized int getPacketSize() {
		return packetSize;
	}

	public synchronized Stream getOutStream() {
		return outStream;
	}

	public ItemAssistant getItems() {
		return itemAssistant;
	}

	public PlayerAssistant getPA() {
		return playerAssistant;
	}

	public DialogueHandler getDH() {
		return dialogueHandler;
	}

	public TradeLog getTradeLog() {
		return tradeLog;
	}

	public WarriorsGuild getWarriorsGuild() {
		return warriorsGuild;
	}

	public ShopAssistant getShops() {
		return shopAssistant;
	}

	public Crafting getCrafting() {
		return crafting;
	}

	public TradeAndDuel getTradeAndDuel() {
		return tradeAndDuel;
	}

	public CombatAssistant getCombat() {
		return combatAssistant;
	}

	public ActionHandler getActions() {
		return actionHandler;
	}

	public PlayerKilling getKill() {
		return playerKilling;
	}

	public IoSession getSession() {
		return session;
	}

	public Potions getPotions() {
		return potion;
	}

	public PotionMixing getPotMixing() {
		return potionMixing;
	}

	public Food getFood() {
		return food;
	}

	/**
	 * Skill Constructors
	 */
	public Slayer getSlayer() {
		return slayer;
	}

	public Runecrafting getRunecrafting() {
		return runecrafting;
	}



	

	public Cooking getCooking() {
		return cooking;
	}

	public Gambling getGamble() {
		return gamble;
	}

	public Agility getAgil() {
		return ag;
	}

	public Fishing getFishing() {
		return fish;
	}

	public Smithing getSmithing() {
		return smith;
	}

	public Farming getFarming() {
		return farming;
	}

	public Thieving getThieving() {
		return thieving;
	}

	public Herblore getHerblore() {
		return herblore;
	}

	public Summoning Summoning() {
		return Summoning;
	}

	public Firemaking getFiremaking() {
		return firemaking;
	}

	public SmithingInterface getSmithingInt() {
		return smithInt;
	}

	public Prayer getPrayer() {
		return prayer;
	}

	public Curse getCurse() {
		return curse;
	}

	public Fletching getFletching() {
		return fletching;
	}

	/**
	 * Gets the prospecting class.
	 * 
	 * @return The prospecting class.
	 */
	public Prospecting getProspecting() {
		return prospecting;
	}

	/**
	 * End of Skill Constructors
	 */

	/**
	 * Second skill instances.
	 */
	private Prospecting prospecting = new Prospecting();
	public boolean korasiSpec; 
	public int fishitem;//kk sec testing

	public void queueMessage(Packet arg1) {
		synchronized (queuedPackets) {
			// if (arg1.getId() != 41)
			queuedPackets.add(arg1);
			// else
			// processPacket(arg1);
		}
	}

	public synchronized boolean processQueuedPackets() {
		Packet p = null;
		synchronized (queuedPackets) {
			p = queuedPackets.poll();
		}
		if (p == null) {
			return false;
		}
		inStream.currentOffset = 0;
		packetType = p.getId();
		packetSize = p.getLength();
		inStream.buffer = p.getData();
		if (packetType > 0) {
			// sendMessage("PacketType: " + packetType);
			PacketHandler.processPacket(this, packetType, packetSize);
			processPackets++;
		}
		timeOutCounter = 0;
		if (processPackets > Config.MAX_PROCESS_PACKETS) {
			return false;
		}
		return true;
	}

	public synchronized boolean processPacket(Packet p) {
		synchronized (this) {
			if (p == null) {
				return false;
			}
			inStream.currentOffset = 0;
			packetType = p.getId();
			packetSize = p.getLength();
			inStream.buffer = p.getData();
			if (packetType > 0) {
				// sendMessage("PacketType: " + packetType);
				PacketHandler.processPacket(this, packetType, packetSize);
			}
			timeOutCounter = 0;
			return true;
		}
	}

	public void mymessage() {
		EventManager.getSingleton().addEvent(new Event() {
			public void execute(EventContainer c) {
				//disconnected = true;
			}
		}, 700000); // Time it takes to send the message
	};

	public void correctCoordinates() {
		if (inPcGame()) {
			getPA().movePlayer(2657, 2639, 0);
		}
		if (inRFD()) {
			getPA().movePlayer(1899, 5363, playerId * 4 + 2);
			sendMessage("Your wave will start in 10 seconds Good Luck.");
			EventManager.getSingleton().addEvent(new Event() {
				public void execute(EventContainer c) {
					Server.rfd.spawnNextWave((Client) Server.playerHandler.players[playerId]);
					c.stop();
				}
			}, 10000);

		}

	}
}

