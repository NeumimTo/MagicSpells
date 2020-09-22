package com.nisovin.magicspells.spells.passive;

import java.util.EnumSet;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

import com.nisovin.magicspells.util.Util;
import com.nisovin.magicspells.Spellbook;
import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.util.OverridePriority;
import com.nisovin.magicspells.spells.passive.util.PassiveListener;

// Optional trigger variable of a comma separated list of blocks to accept
public class BlockBreakListener extends PassiveListener {

	private final EnumSet<Material> materials = EnumSet.noneOf(Material.class);

	@Override
	public void initialize(String var) {
		if (var == null || var.isEmpty()) return;

		String[] split = var.split(",");
		for (String s : split) {
			s = s.trim();
			Material m = Util.getMaterial(s);
			if (m == null) continue;
			materials.add(m);
		}
	}
	
	@OverridePriority
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		Player player = event.getPlayer();
		Spellbook spellbook = MagicSpells.getSpellbook(player);
		Block block = event.getBlock();

		// all blocks if its empty
		if (materials.isEmpty()) {
			if (!isCancelStateOk(event.isCancelled())) return;
			if (!spellbook.hasSpell(passiveSpell, false)) return;
			boolean casted = passiveSpell.activate(event.getPlayer(), block.getLocation().add(0.5, 0.5, 0.5));
			if (cancelDefaultAction(casted)) event.setCancelled(true);

			return;
		}

		// check if block type is valid
		if (!materials.contains(block.getType())) return;

		if (!isCancelStateOk(event.isCancelled())) return;
		if (!spellbook.hasSpell(passiveSpell, false)) return;
		boolean casted = passiveSpell.activate(event.getPlayer(), event.getBlock().getLocation().add(0.5, 0.5, 0.5));
		if (cancelDefaultAction(casted)) event.setCancelled(true);

	}

}
