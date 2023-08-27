package com.vyarth.team.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.vyarth.team.R
import com.vyarth.team.adapters.TaskListItemsAdapter
import com.vyarth.team.firebase.FirestoreClass
import com.vyarth.team.model.Board
import com.vyarth.team.model.Task
import com.vyarth.team.model.User
import com.vyarth.team.utils.Constants

class TaskListActivity : BaseActivity() {

    // A global variable for Board Details.
    private lateinit var mBoardDetails: Board

    // A global variable for board document id as mBoardDocumentId
    private lateinit var mBoardDocumentId: String

    // A global variable for Assigned Members List.
    lateinit var mAssignedMembersDetailList: ArrayList<User>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_list)

        if (intent.hasExtra(Constants.DOCUMENT_ID)) {
            mBoardDocumentId = intent.getStringExtra(Constants.DOCUMENT_ID)!!
        }

        // Show the progress dialog.
        showProgressDialog(resources.getString(R.string.please_wait))
        FirestoreClass().getBoardDetails(this@TaskListActivity, mBoardDocumentId)
    }

    /**
     * A function to setup action bar
     */
    private fun setupActionBar() {

        val toolbarTaskListActivity=findViewById<Toolbar>(R.id.toolbar_task_list_activity)
        setSupportActionBar(toolbarTaskListActivity)

        val actionBar = supportActionBar
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true)
            actionBar.setHomeAsUpIndicator(R.drawable.ic_white_color_back_24dp)
            actionBar.title = mBoardDetails.name
        }

        toolbarTaskListActivity.setNavigationOnClickListener { onBackPressed() }
    }

    /**
     * A function to get the result of Board Detail.
     */
    fun boardDetails(board: Board) {

        mBoardDetails = board

        hideProgressDialog()

        // Call the function to setup action bar.
        setupActionBar()

        val addTaskList=Task(resources.getString(R.string.add_list))
        board.taskList.add(addTaskList)

        val rvTaskList=findViewById<RecyclerView>(R.id.rv_task_list)
        rvTaskList.layoutManager=LinearLayoutManager(this,LinearLayoutManager.HORIZONTAL,false)
        rvTaskList.setHasFixedSize(true)

        val adapter=TaskListItemsAdapter(this, board.taskList)
        rvTaskList.adapter=adapter

    }
}