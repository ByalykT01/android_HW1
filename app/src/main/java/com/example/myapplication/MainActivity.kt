package com.example.myapplication

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity(),
    LoginFragment.LoginEventHandler,
    RegisterFragment.RegisterEventHandler {

    val credentialsManager by lazy { CredentialsManager(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add(R.id.fragment_container_view, LoginFragment())
            }
        }
    }

    override fun onLoginSuccess() {
        val intent = Intent(this, EmptyActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onRegisterClicked() {
        supportFragmentManager.commit {
            setReorderingAllowed(true)
            replace(R.id.fragment_container_view, RegisterFragment())
            addToBackStack(null)
        }
    }

    override fun onRegisterSuccess() {
        supportFragmentManager.popBackStack()
    }
}