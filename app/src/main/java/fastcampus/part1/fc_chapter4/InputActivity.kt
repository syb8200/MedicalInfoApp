package fastcampus.part1.fc_chapter4

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import fastcampus.part1.fc_chapter4.databinding.ActivityInputBinding

class InputActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputBinding

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
                binding.birthDateValueTextView.text = "$year-${month.inc()}-$dayOfMonth"
            }

            // 오늘까지만 선택 가능하도록 변형해보았음
            val datePickerDialog = DatePickerDialog(this, listener, 2000, 1, 1)
            datePickerDialog.datePicker.maxDate = System.currentTimeMillis()
            datePickerDialog.show()
        }

        // 주의사항 체크박스
        binding.warningCheckBox.setOnCheckedChangeListener { _, isChecked ->
            binding.warningEditText.isVisible = isChecked
        }
        binding.warningEditText.isVisible = binding.warningCheckBox.isChecked

        // 저장 버튼
        binding.saveButton.setOnClickListener {
            saveData()
            finish() // Activity 종료
        }

    }

    private fun saveData() {
        // Context.MODE_PRIVATE : 이 파일을 생성한 앱에서만 접근 가능
        // commit()과 apply()로 데이터를 저장할 수 있지만 -> apply() 권장!
        // commit() : 메인 스레드에서 작업을 하다보니 사용자가 데이터를 저장하는 동안 다른 작업을 진행할 수 없음
        // apply() : commit()과 달리 다른 스레드에서 작업이 이루어짐 (비동기적)
        with(getSharedPreferences("userInformation", Context.MODE_PRIVATE).edit()) {
            putString("name", binding.nameEditText.text.toString())
            putString("bloodType", getBloodType())
            putString("emergencyContact", binding.emergencyContactEditText.text.toString())
            putString("birthDate", binding.birthDateValueTextView.text.toString())
            putString("warning", getWarning())
            apply()
        }

        Toast.makeText(this, "저장을 완료했습니다!", Toast.LENGTH_SHORT).show()
    }

    private fun getBloodType(): String {
        val bloodAlphabet = binding.bloodTypeSpinner.selectedItem.toString()
        val bloodSign = if (binding.bloodTypePlus.isChecked) "+" else "-"
        return "$bloodSign $bloodAlphabet"
    }

    private fun getWarning(): String {
        return if (binding.warningCheckBox.isChecked) binding.warningEditText.text.toString() else ""
    }
}