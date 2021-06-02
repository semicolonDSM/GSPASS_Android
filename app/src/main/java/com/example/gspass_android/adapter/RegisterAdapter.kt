package com.example.gspass_android.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.gspass_android.ui.RegisterIdFragment
import com.example.gspass_android.ui.RegisterPasswordFragment
import com.example.gspass_android.ui.RegisterSchoolCodeFragment
import com.example.gspass_android.ui.RegisterUserInfoFragment

class RegisterAdapter(fragmentActivity: FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    companion object {
        // 페이지 개수를 정적 변수로 선언
        private const val NUM_PAGES = 4
    }

    override fun getItemCount(): Int {
        return NUM_PAGES
    }

    override fun createFragment(position: Int): Fragment {
        if(position == 0) {
            return RegisterSchoolCodeFragment()
        }else if(position == 1) {
            return RegisterUserInfoFragment()
        }else if(position==2){
            return RegisterIdFragment()
        }else{
            return RegisterPasswordFragment()
        }
    }

}