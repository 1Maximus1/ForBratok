package UniversalCalculator.UniversalCalculator.Parser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import response.Matrix;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Getter
public class UserSaveDataStruct {
    private final String ip;
    private final String action;
    private final Matrix matrixA;
    private final Matrix matrixB;
    private final Matrix resultMatrix;
    private final String error;

    public UserSaveDataStruct(String ip, String action, Matrix matrixA, Matrix matrixB, Matrix resultMatrix, String error){
        this.ip = ip;
        this.action = action;
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.resultMatrix = resultMatrix;
        this.error = error;
    }

    public UserSaveDataStruct(String ip, String action, Matrix matrixA, Matrix matrixB, Matrix resultMatrix){
        this.ip = ip;
        this.action = action;
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.resultMatrix = resultMatrix;
        this.error = "None";
    }

    public UserSaveDataStruct(String ip, String action, Matrix matrixA, Matrix resultMatrix){
        this.ip = ip;
        this.action = action;
        this.matrixA = matrixA;
        this.matrixB = new Matrix();
        this.resultMatrix = resultMatrix;
        this.error = "None";
    }

    public void saveToJsonFile(String filePath) {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        File file = new File(filePath);
        try (FileWriter writer = new FileWriter(file, true);
             BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

            if (file.exists()) {
                bufferedWriter.write(",\n");
                gson.toJson(this, bufferedWriter);
                System.out.println("Data saved in: " + filePath);
            } else {
                gson.toJson(this, bufferedWriter);
                System.out.println("Data saved in new file: " + filePath);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
