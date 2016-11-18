package gensekiel.wurmunlimited.mods.treefarm;

import com.wurmonline.mesh.FoliageAge;
import com.wurmonline.mesh.Tiles;
import com.wurmonline.mesh.TreeData;
import com.wurmonline.server.creatures.Creature;
import com.wurmonline.server.items.ItemList;

public class FertilizingAction extends AbstractAction 
{
//======================================================================
	public FertilizingAction()
	{
		this("Fertilize");
	}
//======================================================================
	public FertilizingAction(String s)
	{
		super(s, "fertilize", "fertilizing", "Fertilizing");

		cost = 1000;
		time = 50;
		item = ItemList.ash;
	}
//======================================================================
	@Override
	protected boolean checkConditions(Creature performer, int rawtile)
	{
		byte data = Tiles.decodeData(rawtile);
		int age = TreeTile.getAge(data);

		Tiles.Tile tt = TreeTile.getTile(rawtile);
		String tilename = TreeTile.getTileName(rawtile);
		
		if(age <= FoliageAge.YOUNG_FOUR.getAgeId()){
			performer.getCommunicator().sendNormalServerMessage("This " + tilename + " is too young to bear fruit.", (byte)1);
			return true;
		}else if (age >= FoliageAge.OVERAGED.getAgeId()){
			performer.getCommunicator().sendNormalServerMessage("This " + tilename + " is too old to bear fruit.", (byte)1);
			return true;
		}

		if(TreeData.hasFruit(data)){
			performer.getCommunicator().sendNormalServerMessage("This " + tilename + " already bears fruit.", (byte)1);
			return true;
		}

		if(tt.isMycelium()){
			performer.getCommunicator().sendNormalServerMessage("This " + tilename + " already bears fruit.", (byte)1);
			return true;
		}

		return false;
	}
//======================================================================
	@Override
	public void performTileAction(int rawtile, int tilex, int tiley)
	{
		TreeTilePoller.addTreeTile(rawtile, tilex, tiley, new FruitTask());
	}
//======================================================================
	@Override
	protected boolean checkTileType(int rawtile)
	{
		return FruitTask.checkTileType(rawtile);
	}
//======================================================================
}