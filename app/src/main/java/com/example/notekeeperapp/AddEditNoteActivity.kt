package com.example.notekeeperapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

class AddEditNoteActivity : AppCompatActivity() {
    private lateinit var editTextTitle: EditText
    private lateinit var editTextContent: EditText
    private lateinit var buttonSave: Button
    private lateinit var buttonDelete: Button
    private lateinit var dbHelper: DatabaseHelper

    private var noteId: Int = -1
    private var isEditMode = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_edit_note)

        dbHelper = DatabaseHelper(this)

        editTextTitle = findViewById(R.id.editTextTitle)
        editTextContent = findViewById(R.id.editTextContent)
        buttonSave = findViewById(R.id.buttonSave)
        buttonDelete = findViewById(R.id.buttonDelete)

        // Check if editing existing note
        noteId = intent.getIntExtra("NOTE_ID", -1)
        if (noteId != -1) {
            isEditMode = true
            val title = intent.getStringExtra("NOTE_TITLE")
            val content = intent.getStringExtra("NOTE_CONTENT")

            editTextTitle.setText(title)
            editTextContent.setText(content)
            buttonDelete.visibility = Button.VISIBLE
            supportActionBar?.title = "Edit Note"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        } else {
            buttonDelete.visibility = Button.GONE
            supportActionBar?.title = "Add Note"
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
        }

        buttonSave.setOnClickListener {
            saveNote()
        }

        buttonDelete.setOnClickListener {
            showDeleteConfirmation()
        }
    }

    private fun saveNote() {
        val title = editTextTitle.text.toString().trim()
        val content = editTextContent.text.toString().trim()

        if (title.isEmpty()) {
            Toast.makeText(this, "Please enter a title", Toast.LENGTH_SHORT).show()
            return
        }

        if (isEditMode) {
            val result = dbHelper.updateNote(noteId, title, content)
            if (result > 0) {
                Toast.makeText(this, "Note updated successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to update note", Toast.LENGTH_SHORT).show()
            }
        } else {
            val id = dbHelper.addNote(title, content)
            if (id > 0) {
                Toast.makeText(this, "Note saved successfully", Toast.LENGTH_SHORT).show()
                finish()
            } else {
                Toast.makeText(this, "Failed to save note", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showDeleteConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Delete Note")
            .setMessage("Are you sure you want to delete this note?")
            .setPositiveButton("Delete") { _, _ ->
                deleteNote()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun deleteNote() {
        val result = dbHelper.deleteNote(noteId)
        if (result > 0) {
            Toast.makeText(this, "Note deleted successfully", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            Toast.makeText(this, "Failed to delete note", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}