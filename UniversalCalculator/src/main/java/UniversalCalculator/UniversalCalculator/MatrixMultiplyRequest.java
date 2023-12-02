package UniversalCalculator.UniversalCalculator;

import lombok.Getter;

@Getter
public class MatrixMultiplyRequest {
	private double[][] m1;
	private double[][] m2;

	public void setM1(double[][] m1) {
		this.m1 = m1;
	}

	public void setM2(double[][] m2) {
		this.m2 = m2;
	}

}

