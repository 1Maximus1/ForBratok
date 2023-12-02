package UniversalCalculator.UniversalCalculator;

import response.Matrix;
import response.SquareMatrix;
import response.IdentityMatrix;

import java.lang.NumberFormatException;

public class MatrixActions
{


    public Matrix addMatrices(Matrix m1, Matrix m2){
        Matrix newMatrix = new Matrix(m1);
        for (int i = 0; i < m1.getMatrix().length; i++) {
            for (int j = 0; j < m1.getMatrix()[0].length; j++) {
                newMatrix.getMatrix()[i][j] = m1.getMatrix()[i][j] + m2.getMatrix()[i][j];
            }
        }
        return newMatrix;
    }

    protected static Matrix multiplicationByScalar(Matrix a, double scalar) {
        Matrix result = new Matrix(a.getRows(), a.getColumns());
        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < a.getColumns(); j++) {
                result.setElement(i, j, a.getElement(i, j) * scalar);
            }
        }
        return result;
    }
    public static Matrix multiplicationMatrices(Matrix a, Matrix b) {
        if (a.getMatrix().length == 0 || b.getMatrix().length == 0) {
            throw new IllegalArgumentException("No matrices provided for addition.");
        }

        if (a.getColumns() != b.getRows()) {
            throw new IllegalArgumentException("Such matrices cannot be multiplied, since the number of columns of matrix 'a' is not equal to the number of rows of matrix 'b'.");
        }

        Matrix result = new Matrix(a.getRows(), b.getColumns());

        for (int i = 0; i < a.getRows(); i++) {
            for (int j = 0; j < b.getColumns(); j++) {
                double sum = 0;
                for (int k = 0, l = 0; k < a.getColumns() && l < b.getRows(); k++, l++) {
                    double number1 = a.getMatrix()[i][k];
                    double number2 = b.getMatrix()[l][j];
                    double mult = number1 * number2;
                    sum = sum + mult;
                }
                result.setElement(i, j, sum);
            }
        }
        return result;

    }

    public Matrix triangularShapeUpper(Matrix matrix){
        int LowestLength;
        if (matrix.getColumns() < matrix.getRows()) {
            LowestLength = matrix.getColumns();
        } else {
            LowestLength = matrix.getRows();
        }

        Matrix newMatrix = new Matrix(matrix);
        for (int i = 0; i < LowestLength; i++) {
            boolean matrix_swaped = false;
            for (int j = i; j < matrix.getRows(); j++) {
                if (matrix_swaped) {

                } else {
                    if (newMatrix.getMatrix()[j][i] != 0) {
                        double[] temp = newMatrix.getMatrix()[i];
                        newMatrix.getMatrix()[i] = newMatrix.getMatrix()[j];
                        newMatrix.getMatrix()[j] = temp;
                        matrix_swaped = true;
                    }
                }
            }
            double first_d = newMatrix.getMatrix()[i][i];
            for (int j = 1 + i; j < newMatrix.getRows(); j++) {
                double other_d = newMatrix.getMatrix()[j][i];
                if (first_d != 0) {
                    double alpha = other_d / first_d;
                    for (int k = 0; k < newMatrix.getColumns(); k++) {
                        newMatrix.getMatrix()[j][k] = newMatrix.getMatrix()[j][k] - alpha*newMatrix.getMatrix()[i][k];
                    }
                }
            }
        }

        for (int centalR = 0, centalC = 0; centalR < matrix.getRows() && centalC < matrix.getColumns(); centalR++, centalC++) {
            for (int i = centalC; i < matrix.getRows() - 1; i++) {
                newMatrix.setElement(i + 1, centalC, Math.round(matrix.getMatrix()[i + 1][centalC]));
            }
        }

        return newMatrix;
    }

    public Matrix transformToLowerTriangular(Matrix matrix) {
        int counterOfPermute = 0;
        for (int centalR = 0, centalC = 0; centalR < matrix.getRows() && centalC < matrix.getColumns(); centalR++, centalC++) {
            if (matrix.getMatrix()[centalR][centalC] != 0) {
                for (int i = centalC; i < matrix.getRows(); i++) {
                    Matrix divideM = matrix.getRow(centalR + 1);
                    if ((i + 1) < matrix.getRows()) {
                        divideM = multiplicationByScalar(divideM, -1 * matrix.getMatrix()[i + 1][centalC] / matrix.getMatrix()[centalR][centalC]);
                        matrix = addRowToMatrix(matrix, divideM, i + 1);
                    } else {
                        break;
                    }
                }
            } else {
                for (int j = centalC; j < matrix.getColumns(); j++) {
                    if ((j + 1) < matrix.getColumns()) {
                        matrix = permuteColumn(matrix,j + 1, j + 2);
                        counterOfPermute++;
                    }
                    if (matrix.getMatrix()[centalR][centalC] != 0) {
                        break;
                    }
                }
                if (matrix.getMatrix()[centalR][centalC] == 0) {
                    break;
                }
                centalR--;
                centalC--;

            }
        }

        for (int centalR = 0, centalC = 0; centalR < matrix.getRows() && centalC < matrix.getColumns(); centalR++, centalC++) {
            for (int i = centalC; i < matrix.getRows() - 1; i++) {
                matrix.setElement(i + 1, centalC, Math.round(matrix.getMatrix()[i + 1][centalC]));

            }
        }

        if (Math.abs(counterOfPermute) % 2 == 0) {
        } else {
            matrix = multiplicationByScalar(matrix, -1);
        }
        return new Matrix(matrix);
    }

    public Matrix addRowToMatrix(Matrix matrix, Matrix row, int index){
        if (index < 0 || index >= matrix.getRows() || row.getColumns() != matrix.getColumns()) {
            throw new IllegalArgumentException("Invalid index or row dimensions.");
        }

        for (int i = 0; i < row.getColumns(); i++) {
            matrix.getMatrix()[index][i] += row.getElement(0, i);
        }
        return matrix;
    }

    public Matrix permuteColumn(Matrix matrix, int column1, int column2) {
        Matrix matrixColumn_1 = matrix.getColumn(column1);
        Matrix matrixColumn_2 = matrix.getColumn(column2);

        for (int j = 0; j < matrix.getRows(); j++) {
            matrix.getMatrix()[j][column2 - 1] = matrixColumn_1.getMatrix()[j][0];
        }
        for (int j = 0; j < matrix.getRows(); j++) {
            matrix.getMatrix()[j][column1 - 1] = matrixColumn_2.getMatrix()[j][0];
        }
        return matrix;
    }


    public SquareMatrix inverse(Matrix matrix) {
        if (matrix.getRows() != matrix.getColumns())
        {
            throw new NumberFormatException("NoN square matrix");
        }

        int dim = matrix.getRows();
        double[][] result = new double[dim][dim];
        double[][] buffer = new double[dim][dim];


        for (int i = 0; i < buffer.length; i++)
        {
            result[i][i] = 1;
            System.arraycopy(matrix.getMatrix()[i], 0, buffer[i], 0, buffer[0].length);
        }

        for (int j = 0; j < dim; j++)
        {
            int nonZeroI = j;
            while (nonZeroI < dim && buffer[nonZeroI][j] == 0)
            {
                nonZeroI++;
            }
            if (nonZeroI == dim)
            {
                throw new NumberFormatException("Inverse matrix does not exist. Determinant is 0");
            }

            swapRows(buffer, nonZeroI, j);
            swapRows(result, nonZeroI, j);

            double scalar = buffer[j][j];
            for (int j2 = 0; j2 < dim; j2++)
            {
                result[j][j2] /= scalar;
                buffer[j][j2] /= scalar;
            }

            for (int i = j+1; i < dim; i++) {
                scalar = buffer[i][j];
                for (int j2 = 0; j2 < dim; j2++) {
                    result[i][j2] -= result[j][j2] * scalar;
                    buffer[i][j2] -= buffer[j][j2] * scalar;
                }
            }
        }

        for (int j = dim-1; j > 0; j--)
        {
            for (int i = j-1; i >= 0; i--)
            {
                double scalar = buffer[i][j];
                for (int j2 = 0; j2 < dim; j2++)
                {
                    result[i][j2] -= result[j][j2] * scalar;
                }
            }
        }

        return new SquareMatrix(result);
    }

    private void swapRows(double[][] array, int r1, int r2) {
        double[] temp = array[r1];
        array[r1] = array[r2];
        array[r2] = temp;
    }

    public Matrix transposeMatrix(Matrix matrix) {
        Matrix transposedMatrix = new Matrix(matrix.getColumns(), matrix.getRows());

        for (int i = 0; i < matrix.getRows(); i++) {
            for (int j = 0; j < matrix.getColumns(); j++) {
                transposedMatrix.setElement(j, i, matrix.getMatrix()[i][j]);
            }
        }

        return transposedMatrix;
    }


    public static SquareMatrix powerMatrix(SquareMatrix matrix, int degree) {
        if (degree < 0) {
            throw new IllegalArgumentException("Matrix cannot be raised to a negative power.");
        }

        if (degree == 0) {
            return new IdentityMatrix(matrix.getRows());
        }

        SquareMatrix result = new SquareMatrix(matrix);

        for (int i = 1; i < degree; i++) {
            result = (SquareMatrix) multiplicationMatrices(result, new SquareMatrix(matrix));
        }

        return result;
    }



}




