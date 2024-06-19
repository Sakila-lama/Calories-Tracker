package ca.georgiancollege.mid_term3025

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat


class MainActivity : AppCompatActivity() {
    private lateinit var etName: EditText
    private lateinit var etAge: EditText
    private lateinit var etWeight: EditText
    private lateinit var etHeight: EditText
    private lateinit var switchToggle: Switch
    private lateinit var tvImperial: TextView
    private lateinit var tvMetric: TextView
    private lateinit var radioGroup: RadioGroup
    private lateinit var btnSubmit: Button
    private lateinit var btnCancel: Button
    private lateinit var etExerciseTime: EditText
    private lateinit var tvTotalCalories: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        // Initialize views
        etName = findViewById(R.id.et_name)
        etAge = findViewById(R.id.et_age)
        etWeight = findViewById(R.id.et_weight)
        etHeight = findViewById(R.id.et_height)
        switchToggle = findViewById(R.id.switch_toggle)
        tvImperial = findViewById(R.id.tv_imperial)
        tvMetric = findViewById(R.id.tv_metric)
        radioGroup = findViewById(R.id.radio_group)
        btnSubmit = findViewById(R.id.btn_submit)
        btnCancel = findViewById(R.id.btn_cancel)
        etExerciseTime = findViewById(R.id.et_exercise_time)
        tvTotalCalories = findViewById(R.id.tv_total_calories)

        // Set up the UI elements and event listeners
        setupUI()
    }

    private fun setupUI() {
        // Set up the switch toggle listener
        switchToggle.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                tvImperial.text = getString(R.string.imperial)
                tvMetric.text = getString(R.string.metric)
            } else {
                tvImperial.text = getString(R.string.metric)
                tvMetric.text = getString(R.string.imperial)
            }
        }

        // Set up the submit button click listener
        btnSubmit.setOnClickListener {
            calculateCalories()
        }

        // Set up the cancel button click listener
        btnCancel.setOnClickListener {
            resetUI()
        }
    }

    private fun calculateCalories() {
        // Get the input values
        val weightStr = etWeight.text.toString()
        val heightStr = etHeight.text.toString()
        val exerciseTimeStr = etExerciseTime.text.toString()

        // Check if the input values are not empty
        if (weightStr.isNotEmpty() && heightStr.isNotEmpty() && exerciseTimeStr.isNotEmpty()) {
            val weight = weightStr.toDouble()
            val height = heightStr.toDouble() // Even though height is not used, keep it for completeness
            val exerciseTime = exerciseTimeStr.toDouble()

            // Determine the MET value based on the selected radio button
            val metValue = when (radioGroup.checkedRadioButtonId) {
                R.id.rb_light -> 2.25
                R.id.rb_moderate -> 4.5
                R.id.rb_vigorous -> 9.0
                else -> {
                    Toast.makeText(this, "Please select an exercise intensity", Toast.LENGTH_SHORT).show()
                    return
                }
            }

            // Calculate the calories burned per minute
            val caloriesPerMinute = metValue * weight * 0.0175
            // Calculate the total calories burned
            val totalCaloriesBurned = caloriesPerMinute * exerciseTime

            // Display the total calories
            tvTotalCalories.text = String.format("Total Calories: %.2f", totalCaloriesBurned)
        } else {
            Toast.makeText(this, "Please fill out all fields", Toast.LENGTH_SHORT).show()
        }
    }

    private fun resetUI() {
        // Clear all input fields and reset the UI
        etName.text.clear()
        etAge.text.clear()
        etWeight.text.clear()
        etHeight.text.clear()
        radioGroup.clearCheck()
        etExerciseTime.text.clear()
        tvTotalCalories.text = ""
    }
}