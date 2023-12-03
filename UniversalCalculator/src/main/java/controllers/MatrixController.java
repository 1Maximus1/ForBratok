package controllers;


import Actions.MatrixActions;
import clientIp.IpUtils;
import parser.UserSaveDataStruct;
import UniversalCalculator.calculator.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import domain.Matrix;
import domain.SquareMatrix;

@RestController
public class MatrixController {
	private final MatrixActions matrixActions;
	private final IpUtils ipUtils;

	@Autowired
	public MatrixController(MatrixActions matrixActions, IpUtils ipUtils) {
		this.matrixActions = matrixActions;
		this.ipUtils = ipUtils;
	}

	@PostMapping("/add")
	public ResponseEntity<double[][]> addMatrices(HttpServletRequest httpServletRequest, @RequestBody MatrixAddRequest request) {
		try {
			Matrix result = matrixActions.addMatrices(new Matrix(request.getM1()), new Matrix(request.getM2()));
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "addMatrices", new Matrix(request.getM1()), new Matrix(request.getM2()), result);
			userData.saveToJsonFile("file.json");
			return ResponseEntity.ok(result.getMatrix());
		} catch (Exception e) {
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "addMatrices", new Matrix(request.getM1()), new Matrix(request.getM2()), e.getMessage());
			userData.saveToJsonFile("file.json");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping("/multiplyMatrices")
	public ResponseEntity<double[][]> multiplyMatrices(HttpServletRequest httpServletRequest, @RequestBody MatrixMultiplyRequest request) {
		try {
			Matrix result = matrixActions.multiplicationMatrices(new Matrix(request.getM1()), new Matrix(request.getM2()));
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "multiplyMatrices", new Matrix(request.getM1()), new Matrix(request.getM2()), result);
			userData.saveToJsonFile("file.json");
			return ResponseEntity.ok(result.getMatrix());
		}catch (Exception e) {
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "multiplyMatrices", new Matrix(request.getM1()), new Matrix(request.getM2()), e.getMessage());
			userData.saveToJsonFile("file.json");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping("/determinant")
	public ResponseEntity<Double> determinant(HttpServletRequest httpServletRequest, @RequestBody MatrixRequest request) {
		try {
			double result = new SquareMatrix(request.getMatrix()).getDeterminant();
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "determinant", new Matrix(request.getMatrix()), result);
			userData.saveToJsonFile("file.json");
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "determinant", new Matrix(request.getMatrix()), e.getMessage());
			userData.saveToJsonFile("file.json");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}


	@PostMapping("/inverse")
	public ResponseEntity<double[][]> inverse(HttpServletRequest httpServletRequest, @RequestBody MatrixRequest request) {
		try {
			Matrix result = matrixActions.inverse(new Matrix(request.getMatrix()));
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "inverse", new Matrix(request.getMatrix()), result);
			userData.saveToJsonFile("file.json");
			return ResponseEntity.ok(result.getMatrix());
		}catch (Exception e){
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "inverse", new Matrix(request.getMatrix()), e.getMessage());
			userData.saveToJsonFile("file.json");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}

	}

	@PostMapping("/rang")
	public ResponseEntity<Integer> calculateRank(HttpServletRequest httpServletRequest, @RequestBody MatrixRequest request) {
		try {
			int result = new Matrix(request.getMatrix()).getRang();
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "rang", new Matrix(request.getMatrix()), result);
			userData.saveToJsonFile("file.json");
			return ResponseEntity.ok(result);
		} catch (Exception e) {
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "rang", new Matrix(request.getMatrix()), e.getMessage());
			userData.saveToJsonFile("file.json");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
		}
	}

	@PostMapping("/transpose")
	public ResponseEntity<double[][]> transposeMatrix(HttpServletRequest httpServletRequest,@RequestBody MatrixRequest request) {
		try {
			Matrix matrix = new Matrix(request.getMatrix());
			Matrix transposedMatrix = matrixActions.transposeMatrix(matrix);
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "transpose", new Matrix(request.getMatrix()), transposedMatrix);
			userData.saveToJsonFile("file.json");
			return ResponseEntity.ok(transposedMatrix.getMatrix());
		} catch (Exception e) {
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "transpose", new Matrix(request.getMatrix()), e.getMessage());
			userData.saveToJsonFile("file.json");
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PostMapping("/powerMatrix")
	public ResponseEntity<double[][]> powerMatrix(HttpServletRequest httpServletRequest, @RequestBody MatrixPowerRequest matrixPowerRequest) {
		try {
			SquareMatrix result = matrixActions.powerMatrix(
					new SquareMatrix(matrixPowerRequest.getMatrix()),
					matrixPowerRequest.getDegree()
			);
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "power_matrix_degree{%d}".formatted(matrixPowerRequest.getDegree()), new Matrix(matrixPowerRequest.getMatrix()), result);
			userData.saveToJsonFile("file.json");
			return ResponseEntity.ok(result.getMatrix());
		} catch (Exception e) {
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "power_matrix_degree{%d}".formatted(matrixPowerRequest.getDegree()), new Matrix(matrixPowerRequest.getMatrix()), e.getMessage());
			userData.saveToJsonFile("file.json");
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PostMapping("/subtract")
	public ResponseEntity<double[][]> subtractMatrices(HttpServletRequest httpServletRequest, @RequestBody MatrixAddRequest request) {
		try {
			Matrix result = matrixActions.subtractMatrices(
					new Matrix(request.getM1()),
					new Matrix(request.getM2())
			);
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "subtract", new Matrix(request.getM1()), new Matrix(request.getM2()), result);
			userData.saveToJsonFile("file.json");
			return ResponseEntity.ok(result.getMatrix());
		} catch (IllegalArgumentException e) {
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "subtract", new Matrix(request.getM1()), new Matrix(request.getM2()), e.getMessage());
			userData.saveToJsonFile("file.json");
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PostMapping("/triangularShapeLower")
	public ResponseEntity<double[][]> triangularShapeLower(HttpServletRequest httpServletRequest, @RequestBody MatrixRequest request) {
		try {
			Matrix result = matrixActions.triangularShapeLower(new Matrix(request.getMatrix()));
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "triangularShapeLower", new Matrix(request.getMatrix()), result);
			userData.saveToJsonFile("file.json");
			return ResponseEntity.ok(result.getMatrix());
		} catch (IllegalArgumentException e) {
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "triangularShapeLower", new Matrix(request.getMatrix()), e.getMessage());
			userData.saveToJsonFile("file.json");
			return ResponseEntity.badRequest().body(null);
		}
	}

	@PostMapping("/triangularShapeUpper")
	public ResponseEntity<double[][]> triangularShapeUpper(HttpServletRequest httpServletRequest, @RequestBody MatrixRequest request) {
		try {
			Matrix result = matrixActions.triangularShapeUpper(new Matrix(request.getMatrix()));
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "triangularShapeUpper", new Matrix(request.getMatrix()), result);
			userData.saveToJsonFile("file.json");
			return ResponseEntity.ok(result.getMatrix());
		} catch (IllegalArgumentException e) {
			UserSaveDataStruct userData = new UserSaveDataStruct(IpUtils.getClientIp(httpServletRequest), "triangularShapeUpper", new Matrix(request.getMatrix()), e.getMessage());
			userData.saveToJsonFile("file.json");
			return ResponseEntity.badRequest().body(null);
		}
	}

}

