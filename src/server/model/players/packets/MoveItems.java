package server.model.players.packets;

import server.model.players.Client;
import server.model.players.PacketType;

/**
 * Move Items
 **/
public class MoveItems implements PacketType {

	@Override
	public void processPacket(Client c, int packetType, int packetSize) {
		int somejunk = c.getInStream().readUnsignedWordA(); //junk
		int itemFrom =  c.getInStream().readUnsignedWordA();// slot1
		int itemTo = (c.getInStream().readUnsignedWordA() -128);// slot2
		//c.sendMessage("junk: " + somejunk);
if(somejunk != 32668)
{
c.getItems().moveItems(itemFrom, itemTo, somejunk);

}
if(somejunk == 32668)
{

int v[] = new int[27];
int b[] = new int[27];
v[itemFrom] = c.storeditems[itemTo];
b[itemFrom] = c.amount[itemTo];

c.storeditems[itemTo] = c.storeditems[itemFrom];
c.amount[itemTo] = c.amount[itemFrom];
c.amount[itemFrom] = b[itemFrom];

c.occupied[itemTo] = true;

c.storeditems[itemFrom] = v[itemFrom];


if(v[itemFrom] > 0) {
c.occupied[itemFrom] = true;
}

if(v[itemFrom] < 1) {
c.occupied[itemFrom] = false;
}



}
		if(c.inTrade) {
			c.getTradeAndDuel().declineTrade();
                             		return;
                        	}
		if(c.tradeStatus == 1) {
			c.getTradeAndDuel().declineTrade();
                             		return;
                        	}
		if(c.duelStatus == 1) {
			c.getTradeAndDuel().declineDuel();
			return;
		}
	}
}
