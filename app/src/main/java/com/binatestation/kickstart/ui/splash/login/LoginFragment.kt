/*
 * Created By RKR
 * Last Updated at 14/12/19 7:12 PM.
 *
 * Copyright (c) 2019. Binate Station Private Limited. All rights reserved.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.binatestation.kickstart.ui.splash.login

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.binatestation.kickstart.R
import com.binatestation.kickstart.databinding.FragmentLoginBinding
import com.binatestation.kickstart.ui.main.MainActivity
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel


    private val isValidInput: Boolean
        get() {
            if (TextUtils.isEmpty(viewModel.loginModel.get()?.username)) {
                field_username_layout?.error = getString(R.string.error_invalid_username)
                field_username_layout?.editText?.requestFocus()
                return false
            }
            if (TextUtils.isEmpty(viewModel.loginModel.get()?.password)) {
                field_password_layout?.error = getString(R.string.error_invalid_password)
                field_password_layout?.editText?.requestFocus()
                return false
            }
            return true
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[LoginViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val fragmentLoginBinding = DataBindingUtil.inflate<FragmentLoginBinding>(
            inflater,
            R.layout.fragment_login,
            container,
            false
        )
        fragmentLoginBinding.viewModel = viewModel
        return fragmentLoginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        action_login.setOnClickListener { actionLogin() }
    }

    private fun actionLogin() {
        if (isValidInput) login()
    }

    private fun login() {
        navigateToHome()
    }


    /**
     * navigate to home
     * If you have some thing to download for app use do that before calling this method.
     */
    private fun navigateToHome() {
        val intent = Intent(context, MainActivity::class.java)
        startActivity(intent)
        activity?.finishAffinity()
    }

}
