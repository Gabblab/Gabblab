package server.model.players;

import server.model.players.packets.*;


public class PacketHandler{

	public static final int MAX_PACKETS = 256;
	public static PacketType[] packet = new PacketType[MAX_PACKETS];
	
	static {
		
		SilentPacket u = new SilentPacket();
		packet[3] = u;
		packet[202] = u;
		packet[77] = u;
		packet[86] = u;
		packet[78] = u;
		packet[36] = u;
		packet[226] = u;
		packet[246] = u;
		packet[148] = u;
		packet[183] = u;
		packet[230] = u;
		packet[136] = u;
		packet[189] = u;
		packet[152] = u;
		packet[200] = u;
		packet[85] = u;
		packet[165] = u;
		packet[238] = u;
		packet[150] = u;
		packet[40] = new Dialogue();
		ClickObject co = new ClickObject();
		packet[132] = co;
		packet[252] = co;
		packet[70] = co;
		packet[57] = new ItemOnNpc();
		ClickNPC cn = new ClickNPC();
		packet[72] = cn;
		packet[131] = cn;
		packet[155] = cn;
		packet[17] = cn;
		packet[21] = cn;
		packet[16] = new ItemClick2();		
		packet[75] = new ItemClick3();	
		packet[122] = new ClickItem();
		packet[241] = new ClickingInGame();
		packet[4] = new Chat();
		packet[236] = new PickupItem();
		packet[87] = new DropItem();
		packet[185] = new ClickingButtons();
		packet[130] = new ClickingStuff();
		packet[103] = new Commands();
		packet[214] = new MoveItems();
		packet[237] = new MagicOnItems();
		packet[181] = new MagicOnFloorItems();
		packet[202] = new IdleLogout();
		AttackPlayer ap = new AttackPlayer();
		packet[73] = ap;
		packet[249] = ap;
		packet[128] = new ChallengePlayer();
		packet[139] = new Trade();
		packet[39] = new FollowPlayer();
		packet[41] = new WearItem();
		packet[145] = new RemoveItem();
		packet[117] = new Bank5();
		packet[43] = new Bank10();
		packet[129] = new BankAll();
		packet[101] = new ChangeAppearance();
		PrivateMessaging pm = new PrivateMessaging();
		packet[188] = pm;
		packet[126] = pm;
		packet[215] = pm;
		packet[95] = pm;
		packet[133] = pm;
		packet[135] = new BankX1();
		packet[208] = new BankX2();
		Walking w = new Walking();
		packet[98] = w;
		packet[164] = w;
		packet[248] = w;
		packet[53] = new ItemOnItem();
		packet[192] = new ItemOnObject();
		packet[25] = new ItemOnGroundItem();
		ChangeRegions cr = new ChangeRegions();
		packet[121] = cr;
		packet[210] = cr;
		packet[60] = new ClanChat();
	}


	public static void processPacket(Client c, int packetType, int packetSize) {
		try {
			//c.getActionSender().sendMessage(packetType+"");
			if(packet[packetType] != null && packetType <= MAX_PACKETS && packetType >= 0) {
                            packet[packetType].processPacket(c, packetType, packetSize);
			} else {
				//System.out.println("Unhandled Packet: " + packetType);
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	

}