package pingball;

public enum WallType {
		
	/*
	 * Clients are allowed to create only four types of wall.
	 */
	TOP("top"),
	BOTTOM("bottom"),
	LEFT("left"),
	RIGHT("right");

	public final String location;

	/**
	 * location can be chosen only from "top", "bottom", "left", "right"
	 * @param location represents location of the walltype
	 */
	WallType(String location) {
		this.location = location;
	}
}
