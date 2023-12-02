package response;

import UniversalCalculator.UniversalCalculator.*;

import java.util.Arrays;

public class Matrix implements IMatrix {
    private int rows;
    private int columns;
    private double[][] matrix;

    public Matrix() {
        this.rows = 0;
        this.columns = 0;
        this.matrix = new double[0][0];
    }

    public Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.matrix = new double[rows][columns];
    }

    public Matrix(Matrix otherMatrix) {
        this.rows = otherMatrix.getRows();
        this.columns = otherMatrix.getColumns();
        this.matrix = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(otherMatrix.matrix[i], 0, this.matrix[i], 0, columns);
        }
    }

    public Matrix(double[][] data) {
        this.rows = data.length;
        this.columns = data[0].length;
        this.matrix = new double[rows][columns];
        for (int i = 0; i < rows; i++) {
            System.arraycopy(data[i], 0, this.matrix[i], 0, columns);
        }
    }

    public void setElement(int row, int column, double value) {
        matrix[row][column] = value;
    }

    public double getElement(int row, int column) {
        return matrix[row][column];
    }

    public int getRows() {
        return rows;
    }

    public int getColumns() {
        return columns;
    }

    public double[][] getMatrix()
    {
        return matrix;
    }

    public Matrix getRow(int row) {

        row = row - 1;

        if (row < 0 || row > rows) {
            throw new IllegalArgumentException("Invalid row index.");
        }

        Matrix matrixRow = new Matrix(1, columns);

        for (int i = row; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                matrixRow.setElement(0, j, matrix[row][j]);
            }
        }
        return matrixRow;
    }

    public Matrix getColumn(int column) {

        column = column - 1;

        if (column < 0 || column > columns) {
            throw new IllegalArgumentException("Invalid column index.");
        }

        Matrix matrixColumn = new Matrix(rows, 1);

        for (int i = 0; i < getRows(); i++) {
            for (int j = column; j < getColumns(); j++) {
                matrixColumn.setElement(i, 0, matrix[i][column]);
            }
        }
        return matrixColumn;
    }


    @Override
    public int getRang(){
        MatrixActions matrixActions = new MatrixActions();
        Matrix matrix_copy = new Matrix(matrix);
        Matrix mat = matrixActions.triangularShapeUpper(matrix_copy);

        int LowestLength;
        if (this.getColumns() < this.getRows()) {
            LowestLength = this.getColumns();
        } else {
            LowestLength = this.getRows();
        }

        int rang = 0;
        for (int i = 0; i < LowestLength; i++) {
            if (mat.matrix[i][i] != 0) {
                rang++;
            }
        }

        return rang;
    }

    @Override
    public double getDeterminant(){
        if (getColumns()==getRows()) {
            MatrixActions matrixActions = new MatrixActions();
            Matrix matrix_copy = new Matrix(matrix);
            Matrix mat = matrixActions.transformToLowerTriangular(matrix_copy);
            return getDeterminantR(mat.matrix);
        }else {
            throw new IllegalArgumentException("Determinant cannot be found");
        }
    }

    protected double getDeterminantR(double[][] matrix)
    {
        if(matrix.length == 1){
            return matrix[0][0];
        }

        if(matrix.length != 2){
            return matrix[0][0] * Math.pow(-1, 1) * getDeterminantR(getMinorOfMatrix(matrix, 0, 0));
        }

        return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
    }


    protected static double[][] getMinorOfMatrix(double[][] matrix, int row, int column)
    {
        double[][] minor = new double[matrix.length - 1][matrix.length - 1];


        int r = 0, c = 0;

        for (int i = 0; i < matrix.length; i++) {
            if (i != row) {
                c = 0;
                for (int j = 0; j < matrix.length; j++) {
                    if (j != column) {
                        minor[r][c] = matrix[i][j];
                        c++;
                    }
                }
                r++;
            }
        }

        return minor;

    }

    public String getDimension(){
        return rows + " x " + columns;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rows; i++) {
            result.append("[ ");
            for (int j = 0; j < columns; j++) {
                result.append(matrix[i][j]);
                if (j < columns - 1) {
                    result.append(", ");
                }
            }
            result.append(" ]\n");
        }
        return result.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (o == this) {
            return true;
        }
        if (!(o instanceof Matrix)) {
            return false;
        }
        Matrix m = (Matrix) o;
        return this.getRows() == m.getRows()
                && this.getColumns() == m.getColumns()
                && Arrays.deepEquals(this.matrix, m.matrix);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + getRows();
        result = 31 * result + getColumns();

        for (int i = 0; i < rows; i++) {
            result = 31 * result + Double.hashCode(matrix[i][0]);
        }

        return result;
    }
}

