package sweeper;

public class Coord {

	 	public int x;
	 	public int y;
	 	
	 	
	    @Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null || getClass() != obj.getClass()) {
				return false;
			}
			Coord other = (Coord) obj;
			if (x != other.x || y != other.y)
				return false;
			return true;
		}


	    public Coord (int x, int y) {
	        this.x = x;
	        this.y = y;
	    }
}
