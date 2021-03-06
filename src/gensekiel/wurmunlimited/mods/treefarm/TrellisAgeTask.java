package gensekiel.wurmunlimited.mods.treefarm;

import com.wurmonline.mesh.FoliageAge;
import com.wurmonline.server.items.Item;

public class TrellisAgeTask extends ItemTask
{
	private static final long serialVersionUID = 4L;
//======================================================================
	private static int allowed_ids[] = {919, 920, 1018, 1274};
	private int age;
//======================================================================
	private static double growthMultiplier = 1.0;
	public static void setGrowthMultiplier(double d){ growthMultiplier = d; }
	public static double getGrowthMultiplier(){ return growthMultiplier; }
//----------------------------------------------------------------------
	protected static byte ageLimit = 1;
	public static void setAgeLimit(byte b){ ageLimit = b; }
	public static byte getAgeLimit(){ return ageLimit; }
//======================================================================
	public TrellisAgeTask(Item item, double multiplier)
	{
		super(item, multiplier);
		age = getAge(item);
		tasktime *= growthMultiplier;
	}
//======================================================================
	public static boolean checkItemType(Item item)
	{
		return checkItemType(allowed_ids, item);
	}
//======================================================================
	public static int getAge(Item item)
	{
		return item.getLeftAuxData();
	}
//======================================================================
	public static boolean canGrow(Item item)
	{
		int age = getAge(item);
		if(age < FoliageAge.SHRIVELLED.getAgeId()) return true;
		else return false;
	}
//======================================================================
	@Override
	public boolean performCheck()
	{
		Item item = getItem();
		if(item == null) return true;

		if(!checkItemType(item)) return true;

		int newage = getAge(item);

		if(checkForWUPoll && newage > ageLimit){
			if(newage != age) return true;
		}

		if(age < ageLimit && newage == ageLimit) return true;

		if(!canGrow(item)) return true;

		return false;
	}
//======================================================================
	@Override
	public boolean performTask()
	{
		Item item = getItem();
		if(item != null){
			item.setLeftAuxData(item.getLeftAuxData() + 1);
			item.updateName();

			if(getAge(item) < ageLimit){
				resetTimestamp();
				return false;
			}
		}
		return true;
	}
//======================================================================
	@Override public String getDescription(){ return getDescription("watered"); }
//======================================================================
}
