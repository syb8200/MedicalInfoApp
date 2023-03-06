package fastcampus.part1.fc_chapter4

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.core.view.isVisible
import fastcampus.part1.fc_chapter4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        binding.goInputActivityButton.setOnClickListener {
            // 명시적 인텐트
            val intent = Intent(this, InputActivity::class.java)
            intent.putExtra("intentMessage", "응급으료정보") // intent로 다른 화면으로 데이터 넘기기
            startActivity(intent)
        }

        binding.deleteButton.setOnClickListener {
            deleteData()
        }

        binding.emergencyContactLayer.setOnClickListener {
            // 암시적 인텐트 ("전화를 할 수 있는 앱을 실행시켜줘!" 명확하게 집어서 지정하지 않음)
            with(Intent(Intent.ACTION_VIEW)) {
                val phoneNumber = binding.emergencyContactValueTextView.text.toString()
                    .replace("-", "")
                data = Uri.parse("tel: $phoneNumber")
                startActivity(this)

            }
        }
    }

    override fun onResume() {
        super.onResume()
        // 다른 화면에 갔다가 해당 화면으로 돌아올 때 : onCreate()이 시작되는 것이 아니라, onResume() 부터 시작된다.
        getDataUiUpdate()
    }

    // SharedPreferences에서 데이터 불러오기
    private fun getDataUiUpdate() {
        with(getSharedPreferences(USER_INFORMATION, Context.MODE_PRIVATE)) {
            binding.nameValueTextView.text = getString(NAME, "미정")
            binding.birthDateValueTextView.text = getString(BIRTHDATE, "미정")
            binding.bloodTypeValueTextView.text = getString(BLOOD_TYPE, "미정")
            binding.emergencyContactValueTextView.text = getString(EMERGENCY_CONTACT, "미정")

            val warning = getString(WARNING, "")

            binding.warningTextView.isVisible = warning.isNullOrEmpty().not()
            binding.warningValueTextView.isVisible = warning.isNullOrEmpty().not()

            if (!warning.isNullOrEmpty()) {
                binding.warningValueTextView.text = warning
            }
        }
    }

    // SharedPreferences의 데이터 삭제하기
    private fun deleteData() {
        with(getSharedPreferences(USER_INFORMATION, MODE_PRIVATE).edit()) {
            clear() // 데이터 전부 삭제
            apply()
        }
        getDataUiUpdate()
        Toast.makeText(this, "초기화를 완료했습니다.", Toast.LENGTH_SHORT).show()
    }
}