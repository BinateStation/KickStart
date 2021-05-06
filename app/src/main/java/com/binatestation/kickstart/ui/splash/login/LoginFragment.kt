/*
 * Copyright (c) 2021. Binate Station Private Limited. All rights reserved.
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
import com.binatestation.kickstart.utils.Session

class LoginFragment : Fragment() {

    private lateinit var viewModel: LoginViewModel
    private lateinit var fragmentLoginBinding: FragmentLoginBinding

    private val isValidInput: Boolean
        get() {
            if (TextUtils.isEmpty(viewModel.loginModel.get()?.username)) {
                fragmentLoginBinding.fieldUsernameLayout.error =
                    getString(R.string.error_invalid_username)
                fragmentLoginBinding.fieldUsernameLayout.editText?.requestFocus()
                return false
            }
            if (TextUtils.isEmpty(viewModel.loginModel.get()?.password)) {
                fragmentLoginBinding.fieldPasswordLayout.error =
                    getString(R.string.error_invalid_password)
                fragmentLoginBinding.fieldPasswordLayout.editText?.requestFocus()
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
    ): View {
        fragmentLoginBinding = DataBindingUtil.inflate(
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
        fragmentLoginBinding.actionLogin.setOnClickListener { actionLogin() }
        if (Session.isLoggedIn(requireContext())) {
            navigateToHome()
        }
    }

    private fun actionLogin() {
        if (isValidInput) login()
    }

    private fun login() {
        Session.setLogin(requireContext())
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
