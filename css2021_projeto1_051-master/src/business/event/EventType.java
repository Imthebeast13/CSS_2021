package business.event;

/**
 * Enum that represents an event type.
 */
public enum EventType {

	TeteATete, BandoSentado, MultidaoEmPe;

	private static final int TETEATETE_MAX_CAPACITY = 6;
	private static final int BANDO_SENTADO_MAX_CAPACITY = 1000;
	private static final int MULTIDAO_EM_PE_MAX_CAPACITY = 500000;

	/**
	 * Returns the max capacity of the event type
	 * @return The max capacity of the event type
	 */
	public int getCapacity () {
		int c = 0;
		
		switch(this) {
		case TeteATete:
			c = TETEATETE_MAX_CAPACITY;
			break;
		case BandoSentado:
			c = BANDO_SENTADO_MAX_CAPACITY;
			break;
		case MultidaoEmPe:
			c = MULTIDAO_EM_PE_MAX_CAPACITY;
			break;
		}
		
		return c;
	}
	
	/**
	 * Checks if the event type is a seated type.
	 * @return True if it's seated, false otherwise
	 */
	public boolean isSeated () {
		return !this.equals(MultidaoEmPe);
	}

}