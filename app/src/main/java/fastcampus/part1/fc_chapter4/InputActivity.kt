package fastcampus.part1.fc_chapter4

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
    }
}