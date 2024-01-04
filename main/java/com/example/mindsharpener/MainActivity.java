package com.example.mindsharpener;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private RadioGroup radioGroup;
    private TextView textView4, textView5, textView6, textView8;
    private EditText editText1;
    private Button button1;
    private int points = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        radioGroup = findViewById(R.id.radioGroup);
        textView4 = findViewById(R.id.textView4);
        textView5 = findViewById(R.id.textView5);
        textView6 = findViewById(R.id.textView6);
        editText1 = findViewById(R.id.editText1);
        button1 = findViewById(R.id.button1);
        textView8 = findViewById(R.id.textView8);

        // Set a listener for changes in the selected level
        radioGroup.setOnCheckedChangeListener((group, checkedId) -> updateQuestionBasedOnLevel());

        // Set default level (i3)
        radioGroup.check(R.id.radioButton1);
        updateQuestionBasedOnLevel();

        // Set a listener for the Check button
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkAnswer();
            }
        });
    }

    private void updateQuestionBasedOnLevel() {
        int selectedId = radioGroup.getCheckedRadioButtonId();

        if (selectedId == R.id.radioButton1) {
            // Level i3
            setQuestion(generateRandomNumber(10) + " " + generateRandomOperator() + " " + generateRandomNumber(10));
        } else if (selectedId == R.id.radioButton2) {
            // Level i5
            setQuestion(generateRandomNumber(100) + " " + generateRandomOperator() + " " + generateRandomNumber(100));
        } else if (selectedId == R.id.radioButton3) {
            // Level i7
            setQuestion(generateRandomNumber(1000) + " " + generateRandomOperator() + " " + generateRandomNumber(1000));
        }
    }

    private int generateRandomNumber(int max) {
        Random random = new Random();
        return random.nextInt(max + 1);
    }

    private String generateRandomOperator() {
        Random random = new Random();
        int operatorIndex = random.nextInt(4); // 0 to 3
        String[] operators = {"+", "-", "*", "/"};
        return operators[operatorIndex];
    }

    private void setQuestion(String question) {
        // Split the question into three parts (operand1, operator, operand2)
        String[] parts = question.split(" ");
        textView4.setText(parts[0]);
        textView5.setText(parts[1]);
        textView6.setText(parts[2]);
        editText1.setText(""); // Clear previous user input
    }

    private void checkAnswer() {
        // Get user's answer from EditText
        String userAnswerStr = editText1.getText().toString().trim();

        if (!userAnswerStr.isEmpty()) {
            // Convert user's answer to integer
            int userAnswer = Integer.parseInt(userAnswerStr);

            // Get operands and operator
            int operand1 = Integer.parseInt(textView4.getText().toString());
            int operand2 = Integer.parseInt(textView6.getText().toString());
            String operator = textView5.getText().toString();

            // Calculate the correct answer
            int correctAnswer = calculateAnswer(operand1, operand2, operator);

            // Compare the answer with the user's answer
            if (userAnswer == correctAnswer) {
                // Correct answer
                points++;
                Toast.makeText(this, "Correct! Points: " + points, Toast.LENGTH_SHORT).show();
            } else {
                // Incorrect answer
                points--;
                Toast.makeText(this, "Incorrect! Points: " + points, Toast.LENGTH_SHORT).show();
            }

            // Display another question
            updateQuestionBasedOnLevel();
        } else {
            // Display a message if the user's answer is empty
            Toast.makeText(this, "Please enter an answer", Toast.LENGTH_SHORT).show();
        }
    }

    private int calculateAnswer(int operand1, int operand2, String operator) {
        switch (operator) {
            case "+":
                return operand1 + operand2;
            case "-":
                return operand1 - operand2;
            case "*":
                return operand1 * operand2;
            case "/":
                return operand1 / operand2;
            default:
                return 0;
        }
    }
}
