package handler.bbs;

import org.mmocore.commons.database.dbutils.DbUtils;
import org.mmocore.gameserver.configuration.config.community.CBasicConfig;
import org.mmocore.gameserver.data.htm.HtmCache;
import org.mmocore.gameserver.database.DatabaseFactory;
import org.mmocore.gameserver.network.lineage.serverpackets.ShowBoard;
import org.mmocore.gameserver.object.Player;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;
import java.util.StringTokenizer;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;

public class ManageFavorites extends ScriptBbsHandler
{
	//private static final Logger LOGGER = LoggerFactory.getLogger(ManageFavorites.class);

	@Override
	public String[] getBypassCommands()
	{
		return new String[] { "_bbsgetfav", "_bbsaddfav_List", "_bbsdelfav_" };
	}

	@Override
	public void onBypassCommand(Player player, String bypass)
	{
		StringTokenizer st = new StringTokenizer(bypass, "_");
		String cmd = st.nextToken();

		if("bbsgetfav".equals(cmd))
		{
			Connection con = null;
			PreparedStatement statement = null;
			ResultSet rset = null;
			StringBuilder fl = new StringBuilder("");
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("SELECT * FROM `bbs_favorites` WHERE `object_id` = ? ORDER BY `add_date` DESC");
				statement.setInt(1, player.getObjectId());
				rset = statement.executeQuery();
				String tpl = HtmCache.getInstance().getHtml(CBasicConfig.BBS_PATH + "/bbs_favoritetpl.htm", player);
				while(rset.next())
				{
					String fav = tpl.replace("%fav_title%", rset.getString("fav_title"));
					fav = fav.replace("%fav_bypass%", rset.getString("fav_bypass"));
					fav = fav.replace("%add_date%", String.format("%1$te.%1$tm.%1$tY %1$tH:%1tM", new Date(rset.getInt("add_date") * 1000L)));
					fav = fav.replace("%fav_id%", String.valueOf(rset.getInt("fav_id")));
					fl.append(fav);
				}
			}
			catch(Exception e)
			{
			}
			finally
			{
				DbUtils.closeQuietly(con, statement, rset);
			}

			String html = HtmCache.getInstance().getHtml(CBasicConfig.BBS_PATH + "/bbs_getfavorite.htm", player);
			html = html.replace("%FAV_LIST%", fl.toString());

			ShowBoard.separateAndSend(html, player);
		}
		else if("bbsaddfav".equals(cmd))
		{
			String fav = player.getSessionVar("add_fav");
			player.setSessionVar("add_fav", null);
			if(fav != null)
			{
				String favs[] = fav.split("&");
				if(favs.length > 1)
				{
					Connection con = null;
					PreparedStatement statement = null;
					try
					{
						con = DatabaseFactory.getInstance().getConnection();
						statement = con.prepareStatement("REPLACE INTO `bbs_favorites`(`object_id`, `fav_bypass`, `fav_title`, `add_date`) VALUES(?, ?, ?, ?)");
						statement.setInt(1, player.getObjectId());
						statement.setString(2, favs[0]);
						statement.setString(3, favs[1]);
						statement.setInt(4, (int) (System.currentTimeMillis() / 1000));
						statement.execute();
					}
					catch(Exception e)
					{
						e.printStackTrace();
					}
					finally
					{
						DbUtils.closeQuietly(con, statement);
					}
				}
			}
			onBypassCommand(player, "_bbsgetfav");
		}
		else if("bbsdelfav".equals(cmd))
		{
			int fav_id = Integer.parseInt(st.nextToken());

			Connection con = null;
			PreparedStatement statement = null;
			try
			{
				con = DatabaseFactory.getInstance().getConnection();
				statement = con.prepareStatement("DELETE FROM `bbs_favorites` WHERE `fav_id` = ? and `object_id` = ?");
				statement.setInt(1, fav_id);
				statement.setInt(2, player.getObjectId());
				statement.execute();
			}
			catch(Exception e)
			{
			}
			finally
			{
				DbUtils.closeQuietly(con, statement);
			}
			onBypassCommand(player, "_bbsgetfav");
		}
	}

	@Override
	public void onWriteCommand(Player player, String bypass, String arg1, String arg2, String arg3, String arg4, String arg5)
	{}
}
