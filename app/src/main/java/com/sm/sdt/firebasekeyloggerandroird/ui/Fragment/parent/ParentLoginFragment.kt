package com.sm.sdt.firebasekeyloggerandroird.ui.Fragment.parent

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sm.sdt.firebasekeyloggerandroird.ConstFun.isLoggedIn
import com.sm.sdt.firebasekeyloggerandroird.Consts
import com.sm.sdt.firebasekeyloggerandroird.Consts.PREFERENCE_NAME
import com.sm.sdt.firebasekeyloggerandroird.Consts.TAG
import com.sm.sdt.firebasekeyloggerandroird.Consts.USER_TYPE
import com.sm.sdt.firebasekeyloggerandroird.Consts.firebaseAuth
import com.sm.sdt.firebasekeyloggerandroird.R
import com.sm.sdt.firebasekeyloggerandroird.databinding.FragmentParentLoginBinding

class ParentLoginFragment : Fragment(R.layout.fragment_parent_login) {

    var _binding: FragmentParentLoginBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentParentLoginBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        activity?.setTitle("Parent Registration Or Login")
        val signingBtn = binding.paretnSigninOrRegisterBtn
        val email = binding.parentEmail
        val password = binding.parentPassword
        val progressBar = binding.loadingProgress

        if (isLoggedIn(firebaseAuth)) {
            Toast.makeText(activity, "You are already Logged In", Toast.LENGTH_SHORT)
                .show()

            val sharedPreferences = context?.getSharedPreferences(PREFERENCE_NAME,MODE_PRIVATE)
            val userType = sharedPreferences?.getString(USER_TYPE,"")

            if (userType == "parent"){
                findNavController().navigate(ParentLoginFragmentDirections.actionParentLoginFragmentToChildListFragment())
            }else if ( userType == "child"){
                findNavController().navigate(ParentLoginFragmentDirections.actionParentLoginFragmentToChildPermissionFragment())
            }else{
                findNavController().navigate(ParentLoginFragmentDirections.actionParentLoginFragmentToChildListFragment())
            }

        }


        binding.childRegisterTv.setOnClickListener {
            findNavController().navigate(ParentLoginFragmentDirections.actionParentLoginFragmentToChildRegistationFragment())
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


                                    firebaseAuth.signInWithEmailAndPassword(emailText, passwordText)
                                        .addOnCompleteListener {
                                            if (it.isSuccessful) {
                                                Toast.makeText(
                                                    context,
                                                    "Login Successful",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                Log.i(TAG, "loginFirebase: Login Successful")

                                                val sharedPreferences =
                                                    context?.getSharedPreferences(
                                                        PREFERENCE_NAME,
                                                        MODE_PRIVATE
                                                    )
                                                val prefEditor = sharedPreferences!!.edit()
                                                prefEditor.putString(USER_TYPE,"parent")
                                                prefEditor.apply()


                                                findNavController().navigate(
                                                    ParentLoginFragmentDirections.actionParentLoginFragmentToChildListFragment()
                                                )
                                                progressBar.visibility = View.INVISIBLE

                                            } else {
                                                Toast.makeText(
                                                    context,
                                                    "Incorrect Password",
                                                    Toast.LENGTH_SHORT
                                                ).show()

                                            }
                                        }

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
                            findNavController().navigate(ParentLoginFragmentDirections.actionParentLoginFragmentToChildListFragment())
                        } else {
                            firebaseAuth.signInWithEmailAndPassword(emailText, passwordText)
                                .addOnCompleteListener {
                                    if (it.isSuccessful) {
                                        Toast.makeText(
                                            context,
                                            "Login Successful",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                        val sharedPreferences =
                                            context?.getSharedPreferences(
                                                PREFERENCE_NAME,
                                                MODE_PRIVATE
                                            )
                                        val prefEditor = sharedPreferences!!.edit()
                                        prefEditor.putString(USER_TYPE,"parent")
                                        prefEditor.apply()

                                        findNavController().navigate(
                                            ParentLoginFragmentDirections.actionParentLoginFragmentToChildListFragment()
                                        )
                                        progressBar.visibility = View.INVISIBLE

                                    } else {
                                        Toast.makeText(
                                            context,
                                            "Incorrect Password",
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }
                        }
                        progressBar.visibility = View.INVISIBLE
                    }

                }

            }
        }


    }


}