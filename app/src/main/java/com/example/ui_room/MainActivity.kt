package com.example.ui_room

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ui_room.utils.ItemViewModel
import kotlinx.android.synthetic.main.activity_main_new.*
import android.app.Activity
import android.content.Context
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    private lateinit var vm: ItemViewModel
    private lateinit var adapter: ItemAdapter
    private var mode=0
    private var selectedId:Int?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_new)

        supportActionBar?.hide()


        setUpRecyclerView()
        setUpListeners()
        vm=ViewModelProviders.of(this)[ItemViewModel::class.java]
        vm.getAllItems().observe(this,
            {
                adapter.submitList(it)
                more_count.text="${it.size} more"
            })
    }

    private fun setUpListeners() {
        add_notes.setOnClickListener {
            edit_note.visibility=View.VISIBLE
            edit_note_title.text.clear()
            mode=0
        }

        edit_note_cancel.setOnClickListener {
            hideKeyboard()
            edit_note.visibility=View.GONE
        }

        edit_note_save.setOnClickListener {
            hideKeyboard()
            val title=edit_note_title.text.toString()
            val done=edit_note_checkbox.isChecked
            if(mode==1)
            {
                vm.update(ToDo(title,done,selectedId))
                Toast.makeText(this,"Item Updated",Toast.LENGTH_SHORT).show()
            }
            else if(mode==0)
            {
                vm.insert(ToDo(title,done))
                Toast.makeText(this,"Item Inserted",Toast.LENGTH_SHORT).show()
            }

            vm.getAllItems().observe(this,
                {
                    adapter.submitList(it)
                    more_count.text="${it.size} more"
                })
            edit_note.visibility=View.GONE

        }

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(1,
            ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
        )
        {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item=adapter.getItemAt((viewHolder.adapterPosition))
                vm.delete(item)
            }
        }).attachToRecyclerView(ui_recyclerView)
    }

    private fun setUpRecyclerView() {
        ui_recyclerView.layoutManager= LinearLayoutManager(this)
        ui_recyclerView.setHasFixedSize(false)
        ui_recyclerView.isNestedScrollingEnabled=true

        adapter = ItemAdapter { clickedItem ->
            edit_note.visibility=View.VISIBLE
            edit_note_title.setText(clickedItem.Title)
            edit_note_checkbox.isChecked=clickedItem.done
            mode=1
            selectedId=clickedItem.id
        }
        ui_recyclerView.adapter=adapter
    }

    private fun hideKeyboard() {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val view=this.currentFocus
        if (view != null) {
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }
}