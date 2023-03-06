package fastcampus.part1.fc_chapter4

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import fastcampus.part1.fc_chapter4.databinding.ActivityInputBinding

class InputActivity : AppCompatActivity() {

    private lateinit var binding : ActivityInputBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityInputBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        // intent로 데이터 넘겨받기
        val message = intent.getStringExtra("intentMessage") ?: "없음"
        Log.d("intentMessage", message)

        // 혈액형 스피너 구현
        binding.bloodTypeSpinner.adapter = ArrayAdapter.createFromResource(
            this, R.array.blood_types, android.R.layout.simple_list_item_1
        )

        // 생년월일 캘린더 구현 (month는 +1을 해줘야 함, .show()를 해야 DatePickerDialog가 보여짐)
        binding.birthDateLayer.setOnClickListener {
            val listener = OnDateSetListener { _, year, month, dayOfMonth ->
                binding.birthDateTextView.text = "$year-${month.inc()}-$dayOfMonth"
            }

            // 오늘까지만 선택 가능하도록 변형해보았음
            val datePickerDialog = DatePickerDialog(this, listener, 2000, 1, 1)
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.show()
        }
    }
}