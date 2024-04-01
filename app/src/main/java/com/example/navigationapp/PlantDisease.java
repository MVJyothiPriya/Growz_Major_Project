package com.example.navigationapp;

import android.app.Activity;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import org.tensorflow.lite.Interpreter;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.List;

public class PlantDisease extends AppCompatActivity {

    private static final int PICK_IMAGE_REQUEST = 1;
    private ImageView selectedImageView;
    private TextView resultTextView;
    private Bitmap selectedImageBitmap;
    private Interpreter tflite;
    private List<String> labels;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plantdisease);

        Button selectImageButton = findViewById(R.id.selectImageButton);
        selectedImageView = findViewById(R.id.selectedImageView);
        resultTextView = findViewById(R.id.resultTextView);

        try {
            tflite = new Interpreter(loadModelFile());
            labels = loadLabelsFile();
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("PlantDisease", "Error loading TensorFlow Lite model: " + e.getMessage());
            resultTextView.setText("Error loading model");
        }

        selectImageButton.setOnClickListener(v -> openGallery());
    }

    private MappedByteBuffer loadModelFile() throws IOException {
        AssetFileDescriptor fileDescriptor = this.getAssets().openFd("model.tflite");
        FileInputStream inputStream = new FileInputStream(fileDescriptor.getFileDescriptor());
        FileChannel fileChannel = inputStream.getChannel();
        long startOffset = fileDescriptor.getStartOffset();
        long declaredLength = fileDescriptor.getDeclaredLength();
        return fileChannel.map(FileChannel.MapMode.READ_ONLY, startOffset, declaredLength);
    }

    private List<String> loadLabelsFile() throws IOException {
        List<String> labels = new ArrayList<>();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(getAssets().open("labels.txt")));
            String line;
            while ((line = reader.readLine()) != null) {
                labels.add(line);
            }
        } catch (IOException e) {
            Log.e("PlantDisease", "Error reading labels file: " + e.getMessage());
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("PlantDisease", "Error closing labels file: " + e.getMessage());
                }
            }
        }
        return labels;
    }

    private void openGallery() {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK && data != null) {
            Uri selectedImageUri = data.getData();
            try {
                selectedImageBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                if (isLeafOrPlantImage(selectedImageBitmap)) {
                    selectedImageView.setImageBitmap(selectedImageBitmap);
                    classifyPlantDisease(selectedImageBitmap);
                } else {
                    resultTextView.setText("Please select a leaf or plant image");
                }
            } catch (IOException e) {
                e.printStackTrace();
                Log.e("PlantDisease", "Error loading selected image: " + e.getMessage());
                resultTextView.setText("Error loading image");
            }
        }
    }

    private boolean isLeafOrPlantImage(Bitmap bitmap) {
        // Check if the bitmap is not null
        if (bitmap != null) {
            // Define threshold values for green color intensity and aspect ratio
            int greenThreshold = 100; // You can adjust this threshold as needed
            double aspectRatioThreshold = 1.0; // Aspect ratio close to square

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            // Calculate the aspect ratio of the image
            double aspectRatio = (double) width / height;

            // Initialize counters for green pixels and total pixels
            int greenPixels = 0;
            int totalPixels = width * height;

            // Iterate through each pixel in the bitmap
            for (int y = 0; y < height; y++) {
                for (int x = 0; x < width; x++) {
                    // Get the color of the pixel at position (x, y)
                    int pixelColor = bitmap.getPixel(x, y);
                    // Extract the red, green, and blue components of the color
                    int red = Color.red(pixelColor);
                    int green = Color.green(pixelColor);
                    int blue = Color.blue(pixelColor);

                    // Check if the pixel color indicates green (you can adjust this condition)
                    if (green > red && green > blue && green > greenThreshold) {
                        // Increment the count of green pixels
                        greenPixels++;
                    }
                }
            }

            // Calculate the ratio of green pixels to total pixels
            double greenRatio = (double) greenPixels / totalPixels;

            // Check if the aspect ratio is close to square and the green ratio is above a threshold
            return aspectRatio > aspectRatioThreshold && greenRatio > 0.1; // You can adjust the green ratio threshold
        }
        return false;
    }

    private void classifyPlantDisease(Bitmap bitmap) {
        if (tflite != null && bitmap != null) {
            Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 256, 256, true);
            ByteBuffer inputBuffer = ByteBuffer.allocateDirect(1 * 256 * 256 * 3 * 4);
            inputBuffer.order(ByteOrder.nativeOrder());
            int[] intValues = new int[256 * 256];
            resizedBitmap.getPixels(intValues, 0, resizedBitmap.getWidth(), 0, 0, resizedBitmap.getWidth(), resizedBitmap.getHeight());
            int pixel = 0;
            for (int i = 0; i < 256; ++i) {
                for (int j = 0; j < 256; ++j) {
                    final int val = intValues[pixel++];
                    inputBuffer.putFloat(((val >> 16) & 0xFF) / 255.0f);
                    inputBuffer.putFloat(((val >> 8) & 0xFF) / 255.0f);
                    inputBuffer.putFloat((val & 0xFF) / 255.0f);
                }
            }

            float[][] output = new float[1][labels.size()];
            tflite.run(inputBuffer, output);

            int maxIndex = getMaxIndex(output[0]);
            String disease = labels.get(maxIndex);
            resultTextView.setText(disease);
        } else {
            Log.e("PlantDisease", "Interpreter is null or bitmap is null");
        }
    }

    private int getMaxIndex(float[] probabilities) {
        int maxIndex = 0;
        float maxProbability = probabilities[0];
        for (int i = 1; i < probabilities.length; i++) {
            if (probabilities[i] > maxProbability) {
                maxProbability = probabilities[i];
                maxIndex = i;
            }
        }
        return maxIndex;
    }
}
