package com.sm.sdt.firebasekeyloggerandroird.ui.Fragment

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.sm.sdt.firebasekeyloggerandroird.R
import com.sm.sdt.firebasekeyloggerandroird.databinding.FragmentKeyloggerUserBinding
import com.sm.sdt.firebasekeyloggerandroird.ui.adapter.UserAdapter
import java.util.*


class keyloggerUserFragment : Fragment(R.layout.fragment_keylogger_user),
    UserAdapter.OnUserItemClicked {

    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseDatabase = FirebaseDatabase.getInstance()
    var _binding: FragmentKeyloggerUserBinding? = null
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKeyloggerUserBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitle("User")
        setHasOptionsMenu(true)
        val list = ArrayList<String>()
        val userRef = firebaseDatabase.getReference("users")
        val recyclerView = binding.recyclerViewUser
        recyclerView.adapter = UserAdapter(list, this)
        val adapter = recyclerView.adapter
        recyclerView.setHasFixedSize(true)


        userRef.addChildEventListener(object : ChildEventListener {

            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.children.forEach {
                    list.add(it.key.toString())
                }
                list.reverse()
                adapter?.notifyDataSetChanged()
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                TODO("Not yet implemented")
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                TODO("Not yet implemented")
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
        when (item.itemId) {
            R.id.logout -> {
                firebaseAuth.signOut()
                findNavController().navigate(keyloggerUserFragmentDirections.actionKeyloggerUserFragmentToLoginFragment())
                Toast.makeText(activity, "You are logged out", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)

    }


    override fun itemClicked(adapterPosition: Int, item: String) {
        Toast.makeText(activity, item, Toast.LENGTH_SHORT).show()
        findNavController().navigate(keyloggerUserFragmentDirections.actionKeyloggerUserFragmentToKeyloggerFragment())
    }

}




