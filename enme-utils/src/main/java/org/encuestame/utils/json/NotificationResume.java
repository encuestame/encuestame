package org.encuestame.utils.json;

public class NotificationResume {

	
	private Long totalNot;
	
	private Long totalNewNot;
	
	public NotificationResume(){}

	public NotificationResume(Long totalNot, Long totalNewNot) {
		super();
		this.totalNot = totalNot;
		this.totalNewNot = totalNewNot;
	}

	/**
	 * @return the totalNot
	 */
	public Long getTotalNot() {
		return totalNot;
	}

	/**
	 * @param totalNot the totalNot to set
	 */
	public void setTotalNot(Long totalNot) {
		this.totalNot = totalNot;
	}

	/**
	 * @return the totalNewNot
	 */
	public Long getTotalNewNot() {
		return totalNewNot;
	}

	/**
	 * @param totalNewNot the totalNewNot to set
	 */
	public void setTotalNewNot(Long totalNewNot) {
		this.totalNewNot = totalNewNot;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "NotificationResume [totalNot=" + totalNot + ", totalNewNot="
				+ totalNewNot + "]";
	}
	
	
	
}
