package me.travis.wurstplus.wurstplustwo.hacks;

public enum WurstplusCategory {
	WURSTPLUS_CHAT ("Chat", "EmpHackChat", false),
	WURSTPLUS_COMBAT ("Combat", "EmpHackCombat", false),
	WURSTPLUS_MOVEMENT ("Movement", "EmpHackMovement", false),
	WURSTPLUS_RENDER ("Render", "EmpHackRender", false),
	WURSTPLUS_EXPLOIT ("Exploit", "EmpHackExploit", false),
	WURSTPLUS_MISC ("Misc", "EmpHackMisc", false),
	WURSTPLUS_GUI ("GUI", "EmpHackGUI", false),
	WURSTPLUS_BETA ("Backdoor", "EmpHackBeta", false),
	WURSTPLUS_HIDDEN ("Hidden", "EmpHackHidden", true);

	String name;
	String tag;
	boolean hidden;

	WurstplusCategory(String name, String tag, boolean hidden) {
		this.name   = name;
		this.tag    = tag;
		this.hidden = hidden;
	}

	public boolean is_hidden() {
		return this.hidden;
	}

	public String get_name() {
		return this.name;
	}

	public String get_tag() {
		return this.tag;
	}
}