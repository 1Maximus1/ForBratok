package UniversalCalculator.UniversalCalculator;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import response.Matrix;
import response.SquareMatrix;

@RestController
public class MatrixController {

	@PostMapping("/add")
	public ResponseEntity<Object> addMatrices(@RequestBody MatrixAddRequest request) {
		try {
			MatrixActions matrixActions = new MatrixActions();
			Matrix result = matrixActions.addMatrices(new Matrix(request.getM1()), new Matrix(request.getM2()));
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Internal server error: " + e.getMessage());
		}
	}

	@PostMapping("/multiplyMatrices")
	public Matrix multiplyMatrices(@RequestBody MatrixMultiplyRequest request) {
		return MatrixActions.multiplicationMatrices(new Matrix(request.getM1()),new Matrix(request.getM2()));
	}

	@PostMapping("/determinant")
	public double determinant(@RequestBody MatrixRequest request) {
		return new SquareMatrix(request.getMatrix()).getDeterminant();
	}

	@PostMapping("/inverse")
	public SquareMatrix inverse(@RequestBody MatrixRequest request) {
		MatrixActions matrixActions = new MatrixActions();
		return matrixActions.inverse(new Matrix(request.getMatrix()));
	}


}

