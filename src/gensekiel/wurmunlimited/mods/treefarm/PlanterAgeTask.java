package gensekiel.wurmunlimited.mods.treefarm;

import com.wurmonline.server.items.Item;

public class PlanterAgeTask extends ItemTask
{
	private static final long serialVersionUID = 4L;
//======================================================================
	private int age;
//======================================================================
	private static int planterAgeStep = 1;
	public static void setPlanterAgeStep(int i){ planterAgeStep = i; }
	public static int getPlanterAgeStep(){ return planterAgeStep; }
//----------------------------------------------------------------------
	private static double growthMultiplier = 1.0;
	public static void setGrowthMultiplier(double d){ growthMultiplier = d; }
	public static double getGrowthMultiplier(){ return growthMultiplier; }
//----------------------------------------------------------------------
	protected static byte ageLimit = 1;
	public static void setAgeLimit(byte b){ ageLimit = b; }
	public static byte getAgeLimit(){ return ageLimit; }
//======================================================================
	public PlanterAgeTask(Item item, double multiplier)
	{
		super(item, multiplier);
		age = PlanterTask.getPlanterAge(item);
		tasktime *= growthMultiplier;
	}
//======================================================================
	public static boolean canGrow(Item item)
	{
		int age = PlanterTask.getPlanterAge(item);
		if(age < 127) return true;
		else return false;
	}
//======================================================================
	@Override
	public boolean performCheck()
	{
		Item item = getItem();
		if(item == null) return true;

		if(!PlanterTask.checkItemType(item)) return true;

		int newage = PlanterTask.getPlanterAge(item);

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
			byte aux = item.getAuxData();
			item.setAuxData((byte)( (aux & 0x80) | ((aux & 0x7F) + planterAgeStep) ));

			if(PlanterTask.getPlanterAge(item) < ageLimit){
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
