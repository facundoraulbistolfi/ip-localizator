package com.fbis.iplocalizator.models;

public class DistanceInfo {

	private Double max, min, avg;

	public DistanceInfo(Double max, Double min, Double avg) {
		this.max = max;
		this.min = min;
		this.avg = avg;
	}

	public Double getMax() {
		return max;
	}

	public void setMax(Double max) {
		this.max = max;
	}

	public Double getMin() {
		return min;
	}

	public void setMin(Double min) {
		this.min = min;
	}

	public Double getAvg() {
		return avg;
	}

	public void setAvg(Double avg) {
		this.avg = avg;
	}
	
	
}
