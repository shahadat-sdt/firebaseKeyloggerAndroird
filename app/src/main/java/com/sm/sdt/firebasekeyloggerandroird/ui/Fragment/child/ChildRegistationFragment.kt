package com.sm.sdt.firebasekeyloggerandroird.ui.Fragment.child

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.FirebaseDatabase
import com.sm.sdt.firebasekeyloggerandroird.ConstFun
import com.sm.sdt.firebasekeyloggerandroird.Consts.ACCOUNT_DATA
import com.sm.sdt.firebasekeyloggerandroird.Consts.CHILD_NAME_KEY
import com.sm.sdt.firebasekeyloggerandroird.Consts.DEVICE_MODEL
import com.sm.sdt.firebasekeyloggerandroird.Consts.DEVICE_NAME
import com.sm.sdt.firebasekeyloggerandroird.Consts.PREFERENCE_NAME
import com.sm.sdt.firebasekeyloggerandroird.Consts.TAG
import com.sm.sdt.firebasekeyloggerandroird.Consts.USER_REFRENCE
import com.sm.sdt.firebasekeyloggerandroird.Consts.USER_TYPE
import com.sm.sdt.firebasekeyloggerandroird.Consts.firebaseAuth
import com.sm.sdt.firebasekeyloggerandroird.R
import com.sm.sdt.firebasekeyloggerandroird.databinding.FragmentChildRegistationBinding

class ChildRegistationFragment : Fragment(R.layout.fragment_child_registation) {

    private var _binding: FragmentChildRegistationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChildRegistationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitle("Child Registration")

        val firebaseDatabase = FirebaseDatabase.getInstance()
        val childNameText = binding.childName
        val parentEmailText = binding.parentEmail
        val parentPasswordText = binding.parentPassword
        val userRef = firebaseDatabase.getReference(USER_REFRENCE)
        val registrationBtn = binding.childRegisterBtn

        if (ConstFun.isLoggedIn(firebaseAuth)) {
            Toast.makeText(activity, "You are already Logged In", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(ChildRegistationFragmentDirections.actionChildRegistationFragmentToChildPermissionFragment())
        }

        binding.parentLoginOrRegisterTv.setOnClickListener {
            findNavController().navigate(ChildRegistationFragmentDirections.actionChildRegistationFragmentToParentLoginFragment())
        }

        registrationBtn.setOnClickListener {
            binding.loadingProgress.visibility = View.VISIBLE
            if (childNameText.text.isNullOrEmpty()) {
                Toast.makeText(activity, "Please enter a name", Toast.LENGTH_SHORT).show()
                binding.loadingProgress.visibility = View.GONE
            } else if (parentEmailText.text.isNullOrEmpty()) {
                Toast.makeText(activity, "Please enter a Email", Toast.LENGTH_SHORT).show()
                binding.loadingProgress.visibility = View.GONE
            } else {
                firebaseAuth.signInWithEmailAndPassword(
                    parentEmailText.text.toString(),
                    parentPasswordText.text.toString()
                ).addOnCompleteListener {
                    if (it.isSuccessful) {

                        firebaseAuth.addAuthStateListener {
                            val userUid = it.uid.toString()

                            val sharedPreferences =
                                context?.getSharedPreferences(PREFERENCE_NAME, MODE_PRIVATE)
                            val prefEditor = sharedPreferences!!.edit()
                            prefEditor.remove(CHILD_NAME_KEY)
                            prefEditor.remove(USER_TYPE)
                            prefEditor.putString(CHILD_NAME_KEY, childNameText.text.toString())
                            prefEditor.putString(USER_TYPE,"child")
                            prefEditor.apply()

                            val childName = sharedPreferences.getString(CHILD_NAME_KEY, "")

                            userRef.child(userUid).child(childName.toString()).child(
                                ACCOUNT_DATA
                            ).child(DEVICE_NAME)
                                .setValue(DEVICE_MODEL)
                            findNavController().navigate(ChildRegistationFragmentDirections.actionChildRegistationFragmentToChildPermissionFragment())
                            binding.loadingProgress.visibility = View.GONE
                        }
                    }

                }

            }
        }

    }

}