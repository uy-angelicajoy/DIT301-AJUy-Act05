package com.example.notekeeperapp

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton
    private lateinit var notesAdapter: NotesAdapter
    private lateinit var dbHelper: DatabaseHelper
    private var notesList = mutableListOf<Note>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.title = "My Notes"

        dbHelper = DatabaseHelper(this)

        recyclerView = findViewById(R.id.recyclerViewNotes)
        fab = findViewById(R.id.fabAddNote)

        recyclerView.layoutManager = LinearLayoutManager(this)

        notesAdapter = NotesAdapter(notesList) { note ->
            // Handle note click - open edit activity
            val intent = Intent(this, AddEditNoteActivity::class.java)
            intent.putExtra("NOTE_ID", note.id)
            intent.putExtra("NOTE_TITLE", note.title)
            intent.putExtra("NOTE_CONTENT", note.content)
            startActivity(intent)
        }

        recyclerView.adapter = notesAdapter

        fab.setOnClickListener {
            val intent = Intent(this, AddEditNoteActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        loadNotes()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun loadNotes() {
        notesList.clear()
        notesList.addAll(dbHelper.getAllNotes())
        notesAdapter.notifyDataSetChanged()
    }
}