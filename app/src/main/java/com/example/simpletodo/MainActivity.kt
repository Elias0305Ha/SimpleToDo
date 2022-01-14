package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset

class MainActivity : AppCompatActivity() {

    var listOfTasks = mutableListOf<String>()

    lateinit var adapter: TaskItemAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                // 1. remove the item from the list
                listOfTasks.removeAt(position)
                // 2. notify the adapter about the update

                adapter.notifyDataSetChanged()

                saveItems()

            }

        }


        // 1. let's detect when the user clicks on the add button

//        findViewById<Button>(R.id.button).setOnClickListener {
//            // code in here is going to be executed when the user clicks on a button
//
//        }

        loadItems()


        // look up recyclerview in layout
        var recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOfTasks, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)


        // set up the button and input field

        val inputTextField = findViewById<EditText>(R.id.addTaskField)


        // get reference to the button
        // and then set an onclicklistener
        findViewById<Button>(R.id.button).setOnClickListener {
            // 1. grab text the user inputted in to @id/addTaskField
            val userInputtedTask = inputTextField.text.toString()


            //2. add string.... listOfTaskField

            listOfTasks.add(userInputtedTask)
            // notify the adaptor that our data has been updated

            adapter.notifyItemInserted(listOfTasks.size -1 )


            // 3. reset the ext field
            inputTextField.setText("")

            saveItems()

        }
    }


    // get the file we need
    fun getDataFile(): File{

        // every line represent every line in the date file
        return File(filesDir, "data.txt")
    }

    // load the items...
    fun loadItems() {
        try {
            listOfTasks = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }

    // save  items
    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOfTasks)
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }

    }

}