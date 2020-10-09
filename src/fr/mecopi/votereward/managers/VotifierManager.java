package fr.mecopi.votereward.managers;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.vexsoftware.votifier.model.VotifierEvent;

public class VotifierManager implements Listener
{
	@SuppressWarnings("deprecation")
	@EventHandler
	public void voteListener(VotifierEvent e)
	{
		if(Bukkit.getOfflinePlayer(e.getVote().getUsername()).hasPlayedBefore())
		{
			VoteRewardManager.sendItem(Bukkit.getOfflinePlayer(e.getVote().getUsername()).getUniqueId(), 1);
		}
	}
}
