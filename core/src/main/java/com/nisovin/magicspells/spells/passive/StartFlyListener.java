package com.nisovin.magicspells.spells.passive;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerToggleFlightEvent;

import com.nisovin.magicspells.Spellbook;
import com.nisovin.magicspells.MagicSpells;
import com.nisovin.magicspells.util.OverridePriority;
import com.nisovin.magicspells.spells.passive.util.PassiveListener;

// No trigger variable is currently used
public class StartFlyListener extends PassiveListener {
	
	@Override
	public void initialize(String var) {

	}
	
	@OverridePriority
	@EventHandler
	public void onFly(PlayerToggleFlightEvent event) {
		Player player = event.getPlayer();
		Spellbook spellbook = MagicSpells.getSpellbook(player);
		if (!event.isFlying()) return;

		if (!isCancelStateOk(event.isCancelled())) return;
		if (!spellbook.hasSpell(passiveSpell, false)) return;
		boolean casted = passiveSpell.activate(player);
		if (!cancelDefaultAction(casted)) return;
		event.setCancelled(true);
	}

}
