package com.sm.sdt.firebasekeyloggerandroird.ui.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthEmailException
import com.sm.sdt.firebasekeyloggerandroird.ConstFun.loginFirebase
import com.sm.sdt.firebasekeyloggerandroird.R
import com.sm.sdt.firebasekeyloggerandroird.databinding.FragmentLoginBinding
import java.lang.Exception

class LoginFragment : Fragment(R.layout.fragment_login) {
    val TAG = "singin tag"

    var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.setTitle("Login")
        val firebaseAuth = FirebaseAuth.getInstance()
        val signingBtn = binding.signinBtn
        val email = binding.email
        val password = binding.password
        val progressBar = binding.loading

        if (firebaseAuth.currentUser != null) {
            Toast.makeText(activity, "You are alredy Logged In", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToKeyloggerUserFragment())
        }


        binding.registerTv.setOnClickListener {
            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToRegistationFragment())
        }
        signingBtn.setOnClickListener {
            val emailText = email.text.toString()
            val passwordText = password.text.toString()
            progressBar.visibility = View.VISIBLE
            if (email.text.isNullOrEmpty() && password.text.isNullOrEmpty()) {
                Toast.makeText(activity, "email or password empty", Toast.LENGTH_SHORT).show()
                progressBar.visibility = View.INVISIBLE
            } else {
                firebaseAuth.fetchSignInMethodsForEmail(emailText).addOnCompleteListener {
                    if (it.result!!.signInMethods!!.isEmpty()) {
                        firebaseAuth.createUserWithEmailAndPassword(emailText, passwordText)
                            .addOnCompleteListener {
                                if (it.isSuccessful) {
                                    Toast.makeText(activity, "Account Created", Toast.LENGTH_SHORT)
                                        .show()

                                    loginFirebase(
                                        firebaseAuth,
                                        emailText,
                                        passwordText,
                                        requireActivity()
                                    )
                                    findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToKeyloggerUserFragment())
                                    progressBar.visibility = View.INVISIBLE
                                }

                            }.addOnFailureListener {
                                Toast.makeText(activity, it.message.toString(), Toast.LENGTH_SHORT)
                                    .show()
                                progressBar.visibility = View.INVISIBLE
                            }
                    } else {

                        if (firebaseAuth.currentUser != null) {
                            Toast.makeText(
                                activity,
                                "You are already Logged In",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToKeyloggerUserFragment())
                        } else {
                            loginFirebase(firebaseAuth, emailText, passwordText, requireActivity())
                            findNavController().navigate(LoginFragmentDirections.actionLoginFragmentToKeyloggerUserFragment())
                        }
                        progressBar.visibility = View.INVISIBLE
                    }

                }

            }
        }


    }


}