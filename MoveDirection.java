/**
 * 
 * Defines eight directions for movement by the computer player. Each direction
 * 
 * defines an x and y offset for moving a single space in that direction as well
 * 
 * as referencing what directions are clockwise and counter-clockwise from it.
 * 
 * 
 * 
 * @author arleydecker
 *
 * 
 * 
 */

public enum MoveDirection {

	NORTH(0, -1) {

		@Override

		public MoveDirection clockWise() {

			return NORTH_EAST;

		}

		@Override

		public MoveDirection counterClockWise() {

			return NORTH_WEST;

		}

	},

	NORTH_EAST(1, -1) {

		@Override

		public MoveDirection clockWise() {

			return EAST;

		}

		@Override

		public MoveDirection counterClockWise() {

			return NORTH;

		}

	},

	EAST(1, 0) {

		@Override

		public MoveDirection clockWise() {

			return SOUTH_EAST;

		}

		@Override

		public MoveDirection counterClockWise() {

			return NORTH_EAST;

		}

	},

	SOUTH_EAST(1, 1) {

		@Override

		public MoveDirection clockWise() {

			return SOUTH;

		}

		@Override

		public MoveDirection counterClockWise() {

			return EAST;

		}

	},

	SOUTH(0, 1) {

		@Override

		public MoveDirection clockWise() {

			return SOUTH_WEST;

		}

		@Override

		public MoveDirection counterClockWise() {

			return SOUTH_EAST;

		}

	},

	SOUTH_WEST(-1, 1) {

		@Override

		public MoveDirection clockWise() {

			return WEST;

		}

		@Override

		public MoveDirection counterClockWise() {

			return SOUTH;

		}

	},

	WEST(-1, 0) {

		@Override

		public MoveDirection clockWise() {

			return NORTH_WEST;

		}

		@Override

		public MoveDirection counterClockWise() {

			return SOUTH_WEST;

		}

	},

	NORTH_WEST(-1, -1) {

		@Override

		public MoveDirection clockWise() {

			return NORTH;

		}

		@Override

		public MoveDirection counterClockWise() {

			return WEST;

		}

	};

	/**
	 * 
	 * Gets the next direction clockwise from this one.
	 * 
	 * 
	 * 
	 * @return Next direction clockwise
	 * 
	 */

	public abstract MoveDirection clockWise();

	/**
	 * 
	 * Gets the next direction counter-clockwise from this one.
	 * 
	 * 
	 * 
	 * @return
	 * 
	 */

	public abstract MoveDirection counterClockWise();

	/**
	 * 
	 * The x offset of movement in this direction. Positive is right.
	 * 
	 */

	public final int xOffset;

	/**
	 * 
	 * The y offset of movement in this direction. Positive is down.
	 * 
	 */

	public final int yOffset;

	MoveDirection(int x, int y) {

		this.xOffset = x;

		this.yOffset = y;

	}

}