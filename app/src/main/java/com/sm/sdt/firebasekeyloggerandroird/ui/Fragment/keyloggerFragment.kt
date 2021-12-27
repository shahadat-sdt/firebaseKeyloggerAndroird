package com.sm.sdt.firebasekeyloggerandroird.ui.Fragment

import android.os.Bundle
import android.view.*
import android.view.View.inflate
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import com.sm.sdt.firebasekeyloggerandroird.R
import com.sm.sdt.firebasekeyloggerandroird.databinding.ActivityMainBinding.inflate
import com.sm.sdt.firebasekeyloggerandroird.databinding.FragmentKeyloggerBinding
import com.sm.sdt.firebasekeyloggerandroird.ui.adapter.keylogAdapter
import java.util.ArrayList
import java.util.zip.Inflater


class keyloggerFragment : Fragment(R.layout.fragment_keylogger){

    val firebaseAuth = FirebaseAuth.getInstance()
    val firebaseDatabase = FirebaseDatabase.getInstance()
    var _binding: FragmentKeyloggerBinding?= null
    val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentKeyloggerBinding.inflate(inflater,container,false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.setTitle("Keylogger")
        setHasOptionsMenu(true)
        val list = ArrayList<String>()
        val dataRef = firebaseDatabase.getReference("data")
        val recyclerView = binding.recyclerView
        recyclerView.adapter = keylogAdapter(list)
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


        dataRef.addChildEventListener(object : ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.children.forEach {
                    list.add(it.getValue().toString())
                }
                list.reverse()
                recyclerView.adapter = keylogAdapter(list)            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                snapshot.children.forEach {
                    list.add(it.getValue().toString())
                }
                list.reverse()
                recyclerView.adapter = keylogAdapter(list)
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

        } )

    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.keylog_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.logout-> {
                firebaseAuth.signOut()
                findNavController().navigate(keyloggerFragmentDirections.actionKeyloggerFragmentToLoginFragment())
                Toast.makeText(activity, "You are logged out", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)

    }
}




