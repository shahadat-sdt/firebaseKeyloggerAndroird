package com.sm.sdt.firebasekeyloggerandroird.ui.Fragment.child

import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.sm.sdt.firebasekeyloggerandroird.Consts.TAG
import com.sm.sdt.firebasekeyloggerandroird.Consts.USER_REFRENCE
import com.sm.sdt.firebasekeyloggerandroird.Consts.firebaseAuth
import com.sm.sdt.firebasekeyloggerandroird.Consts.firebaseDatabase
import com.sm.sdt.firebasekeyloggerandroird.R
import com.sm.sdt.firebasekeyloggerandroird.databinding.FragmentChildListBinding
import com.sm.sdt.firebasekeyloggerandroird.ui.adapter.UserAdapter
import java.util.*


class ChildListFragment : Fragment(R.layout.fragment_child_list),
    UserAdapter.OnUserItemClicked {

    var _binding: FragmentChildListBinding? = null
    val binding get() = _binding!!



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChildListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.progressBar.visibility = View.VISIBLE
        var parentUid = ""
        if (firebaseAuth.currentUser != null) {
            parentUid = firebaseAuth.currentUser!!.uid
        }

        activity?.setTitle("Child List")
        setHasOptionsMenu(true)
        val list = ArrayList<String>()
        val childRef = firebaseDatabase.getReference(USER_REFRENCE).child(parentUid)

        val recyclerView = binding.recyclerViewUser
        recyclerView.adapter = UserAdapter(list, this)
        val adapter = recyclerView.adapter
        recyclerView.setHasFixedSize(true)



        binding.userEmail.text = firebaseAuth.currentUser!!.email

        childRef.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                binding.progressBar.visibility = View.INVISIBLE
                list.clear()
                snapshot.children.forEach {
                    list.add(it.key.toString())
                    adapter!!.notifyDataSetChanged()

                }
//                list.add(snapshot.toString())
//                adapter?.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }


        })
//        childRef.addChildEventListener(object :ChildEventListener{
//            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
//                snapshot.children.forEach {
//                    list.add(it.key.toString())
//                    adapter?.notifyDataSetChanged()
//
//                }
//            }
//
//            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onChildRemoved(snapshot: DataSnapshot) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
//                TODO("Not yet implemented")
//            }
//
//            override fun onCancelled(error: DatabaseError) {
//                TODO("Not yet implemented")
//            }
//
//        })

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
                findNavController().navigate(ChildListFragmentDirections.actionChildListFragmentToParentLoginFragment())
            }
        }
        return super.onOptionsItemSelected(item)

    }


    override fun itemClicked(adapterPosition: Int, item: String) {
        findNavController().navigate(ChildListFragmentDirections.actionChildListFragmentToChildKeylogFragment(item))
    }




}




