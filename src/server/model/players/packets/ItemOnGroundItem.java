package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
import server.util.Misc;
import server.*;

public class ItemOnGroundItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int a1 = c.getInStream().readSignedWordBigEndian();
		int itemUsed = c.getInStream().readSignedWordA();
		int groundItem = c.getInStream().readUnsignedWord();
		int gItemY = c.getInStream().readSignedWordA();
		int itemUsedSlot = c.getInStream().readSignedWordBigEndianA();
		int gItemX = c.getInStream().readUnsignedWord();
		if(!c.getItems().playerHasItem(itemUsed, 1, itemUsedSlot)) {
			return;
		}
		if(!Server.itemHandler.itemExists(groundItem, gItemX, gItemY)) {
			return;
		}
		
		switch(itemUsed) {
		
		default:
			if(c.playerRights == 3)
				Misc.println("ItemUsed "+itemUsed+" on Ground Item "+groundItem);
			break;
		}
	}

}
