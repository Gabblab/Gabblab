package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;
import server.Connection;
import server.util.Misc;

/**
 * Chat 
 **/
public class Chat implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		c.setChatTextEffects(c.getInStream().readUnsignedByteS());
		c.setChatTextColor(c.getInStream().readUnsignedByteS());
        c.setChatTextSize((byte)(c.packetSize - 2));
        c.inStream.readBytes_reverseA(c.getChatText(), c.getChatTextSize(), 0);
			String[] stuffz = {".net" , ".com" , ".info" , ".org" , ".tk" , ".9hz",".c0m",".scape" , ".biz", "gay", "server sucks", "sucks ass", "servers gay", "gay server", "cant ban me", "ban me"}; //add more!
					String term = Misc.textUnpack(c.getChatText(), c.packetSize - 2).toLowerCase();
			if(c.said >= 10){
			c.sendMessage("You have been muted!");
			Connection.addNameToMuteList(c.playerName);
                        c.teleportToX = 3102;
                        c.teleportToY = 9516;
			}
			for(int i = 0; i < stuffz.length; i++) {
					if(term.contains(stuffz[i])) {
					c.said++;
					c.sendMessage("Please, do not use that term, you will be muted after 10 times!");
						return;
					}
					}
		if (!Connection.isMuted(c))
			c.setChatTextUpdateRequired(true);
	}	
}