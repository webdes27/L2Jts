package handler.items;

import org.mmocore.gameserver.model.items.etcitems.AttributeStone.AttributeStoneManager;
import org.mmocore.gameserver.network.lineage.components.SystemMsg;
import org.mmocore.gameserver.network.lineage.serverpackets.ItemModification.AttributeItem.ExChooseInventoryAttributeItem;
import org.mmocore.gameserver.object.Playable;
import org.mmocore.gameserver.object.Player;
import org.mmocore.gameserver.object.components.items.ItemInstance;

/**
 * @author SYS
 * skill_begin	skill_name = [s_scroll_of_enchant_attr_af_dw1]		skill_id = 2329	level = 1	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_fire;ia_water;3}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_af_dw2]		skill_id = 2329	level = 2	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_fire;ia_water;6}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_af_dw3]		skill_id = 2329	level = 3	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_fire;ia_water;9}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_af_dw4]		skill_id = 2329	level = 4	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_fire;ia_water;9}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_aw_df1]		skill_id = 2330	level = 1	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_water;ia_fire;3}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_aw_df2]		skill_id = 2330	level = 2	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_water;ia_fire;6}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_aw_df3]		skill_id = 2330	level = 3	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_water;ia_fire;9}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_aw_df4]		skill_id = 2330	level = 4	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_water;ia_fire;9}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_aw_de1]		skill_id = 2331	level = 1	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_wind;ia_earth;3}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_aw_de2]		skill_id = 2331	level = 2	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_wind;ia_earth;6}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_aw_de3]		skill_id = 2331	level = 3	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_wind;ia_earth;9}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_aw_de4]		skill_id = 2331	level = 4	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_wind;ia_earth;9}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_ae_dw1]		skill_id = 2332	level = 1	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_earth;ia_wind;3}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_ae_dw2]		skill_id = 2332	level = 2	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_earth;ia_wind;6}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_ae_dw3]		skill_id = 2332	level = 3	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_earth;ia_wind;9}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_ae_dw4]		skill_id = 2332	level = 4	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_earth;ia_wind;9}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_ah_du1]		skill_id = 2333	level = 1	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_holy;ia_unholy;3}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_ah_du2]		skill_id = 2333	level = 2	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_holy;ia_unholy;6}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_ah_du3]		skill_id = 2333	level = 3	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_holy;ia_unholy;9}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_ah_du4]		skill_id = 2333	level = 4	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_holy;ia_unholy;9}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_au_dh1]		skill_id = 2334	level = 1	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_unholy;ia_holy;3}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_au_dh2]		skill_id = 2334	level = 2	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_unholy;ia_holy;6}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_au_dh3]		skill_id = 2334	level = 3	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_unholy;ia_holy;9}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 * sskill_begin	skill_name = [s_scroll_of_enchant_attr_au_dh4]		skill_id = 2334	level = 4	operate_type = A1	magic_level = 1	self_effect = {}	effect = {{i_enchant_attribute;ia_unholy;ia_holy;9}}	operate_cond = {{can_enchant_attribute}}	is_magic = 2	mp_consume2 = 0	cast_range = -1	effective_range = -1	skill_hit_time = 0	skill_cool_time = 0	skill_hit_cancel_time = 0	reuse_delay = 0	attribute = {attr_none;0}	trait = {trait_none}	effect_point = 0	target_type = self	affect_scope = single	affect_limit = {0;0}	next_action = none	ride_state = {@ride_none;@ride_strider;@ride_wyvern;@ride_wolf}	multi_class = 0	olympiad_use = 1	skill_end	
 */
public class AttributeStones extends ScriptItemHandler
{
	@Override
	public boolean useItem(Playable playable, ItemInstance item, boolean ctrl)
	{
		if(playable == null || !playable.isPlayer())
			return false;
		Player player = (Player) playable;

		if(player.getPrivateStoreType() != Player.STORE_PRIVATE_NONE)
		{
			player.sendPacket(SystemMsg.YOU_CANNOT_ADD_ELEMENTAL_POWER_WHILE_OPERATING_A_PRIVATE_STORE_OR_PRIVATE_WORKSHOP);
			return false;
		}

		if(player.getEnchantScroll() != null)
			return false;

		player.setEnchantScroll(item);
		player.sendPacket(SystemMsg.PLEASE_SELECT_ITEM_TO_ADD_ELEMENTAL_POWER);
		player.sendPacket(new ExChooseInventoryAttributeItem(item));
		return true;
	}

	@Override
	public final int[] getItemIds()
	{
		return AttributeStoneManager.getAttributeStoneIds();
	}
}