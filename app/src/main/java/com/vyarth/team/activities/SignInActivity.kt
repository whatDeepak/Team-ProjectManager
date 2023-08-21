package com.vyarth.team.activities

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.WindowManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.Toolbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.vyarth.team.R

class SignInActivity : BaseActivity() {

    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        // Initialize Firebase Auth
        auth = Firebase.auth

        // This is used to hide the status bar and make the splash screen as a full screen activity.
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )

        setupActionBar()

        val btnSignIn=findViewById<Button>(R.id.btn_sign_in)
        btnSignIn.setOnClickListener {
            signInRegisteredUser()
        }
    }

    /**
     * A function for actionBar Setup.
     */
    private fun setupActionBar() {

        val toolbarSignInActivity=findViewById<Toolbar>(R.id.toolbar_sign_in_activity)
        setSupportActionBar(toolbarSignInActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_black_color_back_24dp)
        }

        toolbarSignInActivity.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * A function for Sign-In using the registered user using the email and password.
     */
    private fun signInRegisteredUser() {
        // Here we get the text from editText and trim the space
        val etEmail = findViewById<AppCompatEditText>(R.id.et_email)
        val etPassword = findViewById<AppCompatEditText>(R.id.et_password)
        val email: String = etEmail.text.toString().trim { it <= ' ' }
        val password: String = etPassword.text.toString().trim { it <= ' ' }

        if (validateForm(email, password)) {
            // Show the progress dialog.
            showProgressDialog(resources.getString(R.string.please_wait))

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    hideProgressDialog()
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Log.d("Sign in", "signInWithEmail:success")
                        val user = auth.currentUser
                        startActivity(Intent(this, MainActivity::class.java))
                    } else {
                        // If sign in fails, display a message to the user.
                        Log.w("Sign in", "signInWithEmail:failure", task.exception)
                        Toast.makeText(
                            baseContext,
                            "Authentication failed.",
                            Toast.LENGTH_SHORT,
                        ).show()
                    }
                }
        }
    }
    /**
     * A function to validate the entries of a user.
     */
    private fun validateForm(email: String, password: String): Boolean {
        return if (TextUtils.isEmpty(email)) {
            showErrorSnackBar("Please enter email.")
            false
        } else if (TextUtils.isEmpty(password)) {
            showErrorSnackBar("Please enter password.")
            false
        } else {
            true
        }
    }

    /**
     * A function to get the user details from the firestore database after authentication.
     */
    fun signInSuccess(user: com.vyarth.team.model.User?) {

        hideProgressDialog()

        startActivity(Intent(this@SignInActivity, MainActivity::class.java))
        this.finish()
    }
}