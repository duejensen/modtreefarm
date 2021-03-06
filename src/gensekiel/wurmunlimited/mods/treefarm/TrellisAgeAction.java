package gensekiel.wurmunlimited.mods.treefarm;

import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.Item;
import com.wurmonline.server.items.ItemList;

public class TrellisAgeAction extends TrellisAction
{
//======================================================================
	public TrellisAgeAction(){ this("Water"); }
//======================================================================
	protected TrellisAgeAction(String s)
	{
		super(s, AbstractAction.ActionFlavor.WATER_ACTION);

		cost = 5000;
		time = 30;
		item = ItemList.water;
		skill = 10048;
	}
//======================================================================
	protected boolean checkItemConditions(Creature performer, Item item)
	{
		if(!TrellisAgeTask.canGrow(item)){
			performer.getCommunicator().sendNormalServerMessage("This " + item.getName() + " is too old.", (byte)1);
			return true;
		}

		return false;
	}
//======================================================================
	@Override protected boolean checkItemType(Item item){ return TrellisAgeTask.checkItemType(item); }
//======================================================================
	@Override protected void performItemAction(Item item, double multiplier)
	{
		TaskPoller.addTask(new TrellisAgeTask(item, multiplier));
	}
//======================================================================
}
