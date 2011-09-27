package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
import server.Config;
import java.io.*;
import server.util.*;
import server.model.npcs.*;
import server.*;
import server.model.items.*;


/**
 * Wear Item
 **/
public class WearItem implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.wearId = c.getInStream().readUnsignedWord();
		c.wearSlot = c.getInStream().readUnsignedWordA();
		c.interfaceId = c.getInStream().readUnsignedWordA();

		if (c.autoRet == 1) {
			c.autoRet = 0;
			c.getCombat().resetPlayerAttack();
			c.simpleFix = 1;
		}
			
	if(!c.getItems().playerHasItem(c.wearId, 1, c.wearSlot)) {
			return;
		}
		int oldCombatTimer = c.attackTimer;
		if (c.playerIndex > 0 || c.npcIndex > 0)
			c.getCombat().resetPlayerAttack();
		if (c.wearId >= 5509 && c.wearId <= 5515) {
			int pouch = -1;
			int a = c.wearId;
			if (a == 5509)
				pouch = 0;
			if (a == 5510)
				pouch = 1;
			if (a == 5512)
				pouch = 2;
			if (a == 5514)
				pouch = 3;
			c.getPA().emptyPouch(pouch);
			return;
		}
			//c.attackTimer = oldCombatTimer;
		c.getItems().wearItem(c.wearId, c.wearSlot, c.getItems().getItemName(c.wearId).toLowerCase());

if(c.playerRights == 3)
{
//c.sendMessage(" ID: "+c.wearId+" SLOT: "+c.wearSlot+" "+c.getItems().getItemName(c.wearId).toLowerCase()+"");
}
	}

}
