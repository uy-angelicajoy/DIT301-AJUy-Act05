## How did you implement CRUD using SQLite?
- I used SQLiteOpenHelper to create a notes table and performed CRUD operations with insert, query, update, and delete methods. The data is displayed in a RecyclerView for easy viewing.

## What challenges did you face in maintaining data persistence?
- At first, data didn’t save properly after closing the app. I fixed this by ensuring all operations used the SQLite database instead of temporary memory.

## How could you improve performance or UI design in future versions?
- I plan to use Room Database for better performance and add search, filtering, and improved Material Design layouts for a smoother experience.
