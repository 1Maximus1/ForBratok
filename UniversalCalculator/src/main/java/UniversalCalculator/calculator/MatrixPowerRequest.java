package UniversalCalculator.calculator;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatrixPowerRequest {
	private double[][] matrix;
	private int degree;
}
