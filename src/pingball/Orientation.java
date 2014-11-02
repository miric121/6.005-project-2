package pingball;

public enum Orientation {

	/*
	 * Clients are allowed to create only four types of orientation.
	 */
	rotate0   (0),
	rotate90  (90),
	rotate180 (180),
	rotate270 (270);

	public final int rotation;

	/**
	 * represents the orientation of the gadget
	 * @param orientation (0, 90, 180, 270 degrees)
	 */
	Orientation(int orientation) {
		this.rotation = orientation;
	}
}
