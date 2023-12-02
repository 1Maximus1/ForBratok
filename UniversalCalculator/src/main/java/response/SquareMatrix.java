package response;


import UniversalCalculator.UniversalCalculator.MatrixActions;

public class SquareMatrix extends Matrix
{
    public SquareMatrix(int size) {
        super(size, size);
    }

    public SquareMatrix(Matrix matrix) {
        super(matrix);
        if (matrix.getColumns() != matrix.getRows()) {
            throw new IllegalArgumentException("Invalid matrix dimensions for a square matrix.");
        }
    }

    public SquareMatrix(double[][] result) {
        super(result);
        if (getColumns() != getRows()) {
            throw new IllegalArgumentException("Invalid matrix dimensions for a square matrix.");
        }
    }


    public double getDeterminant(){
            MatrixActions matrixActions = new MatrixActions();
            Matrix matrix_copy = new Matrix(super.getMatrix());
            Matrix mat = matrixActions.transformToLowerTriangular(matrix_copy);
            return getDeterminantR(mat.getMatrix());
    }




}