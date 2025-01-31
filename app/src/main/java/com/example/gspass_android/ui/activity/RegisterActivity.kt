package com.example.gspass_android.ui.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.viewpager2.widget.ViewPager2
import com.example.gspass_android.R
import com.example.gspass_android.adapter.RegisterAdapter
import com.example.gspass_android.databinding.ActivityRegisterBinding
import com.example.gspass_android.viewmodel.RegisterViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class RegisterActivity : AppCompatActivity() {

    lateinit var binding : ActivityRegisterBinding
    private val viewModel : RegisterViewModel by viewModel()
    lateinit var viewPager2: ViewPager2

    lateinit var schoolCode : String
    lateinit var userGcn : String
    lateinit var schoolYear : String
    lateinit var id : String
    lateinit var password : String
    lateinit var checkPassword : String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_register)
        binding.vm = viewModel
        binding.lifecycleOwner = this
        setContentView(binding.root)

        val nextButton : ImageButton = findViewById(R.id.nextBtn)
        val backButton : ImageButton = findViewById(R.id.backBtn)

        viewPager2 = findViewById(R.id.viewPager2)

        val registerAdapter = RegisterAdapter(this)
        viewPager2.adapter = registerAdapter

        fun plusPage(){
            viewPager2.currentItem = viewPager2.currentItem + 1
        }
        nextButton.setOnClickListener {
            when(viewPager2.currentItem){
                0 ->{
                    schoolCode = registerAdapter.registerSchoolCodeFragment.getSchoolCode()
                    if(schoolCode != ""){
                        plusPage()
                    }
                }
                1 ->{
                    userGcn = registerAdapter.registerUserInfoFragment.getUserGcn()
                    schoolYear = registerAdapter.registerUserInfoFragment.getSchoolYear()
                    if(userGcn != "" && schoolYear != ""){
                        plusPage()
                    }else{
                        Toast.makeText(this,"학번과 입학년도를 입력해주세요", Toast.LENGTH_SHORT).show()
                    }
                }
                2 ->{
                    id = registerAdapter.registerIdFragment.getPassId()
                    if(id != ""){
                        plusPage()
                    }else{
                        Toast.makeText(this,"아이디를 입력해주세요", Toast.LENGTH_SHORT).show()
                    }
                }
                3->{
                    password = registerAdapter.registerPasswordFragment.getPassword()
                    checkPassword = registerAdapter.registerPasswordFragment.getCheckPassword()
                    if(password == "" && checkPassword ==""){
                        Toast.makeText(this,"비밀번호와 확인을 입력해주세요", Toast.LENGTH_SHORT).show()
                    }else if(password != checkPassword){
                        Toast.makeText(this,"비밀번호와 확인이 일치 하지 않습니다", Toast.LENGTH_SHORT).show()
                    }else{
                        viewModel.register(id,password,userGcn,schoolYear,schoolCode)
                        val intent = Intent(this, MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
        viewModel.successEvent.observe(this,{
            Toast.makeText(this,"회원가입에 성공하였습니다",Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        })
        viewModel.failEvent.observe(this,{
            Toast.makeText(this,it,Toast.LENGTH_SHORT).show()
        })

        backButton.setOnClickListener {
            if(viewPager2.currentItem == 0) {
                super.onBackPressed()
            }else {
                viewPager2.currentItem = viewPager2.currentItem - 1
            }
        }
    }
    override fun onBackPressed() {
        if(viewPager2.currentItem == 0) {
            super.onBackPressed()
        }else {
            viewPager2.currentItem = viewPager2.currentItem - 1
        }
    }
}