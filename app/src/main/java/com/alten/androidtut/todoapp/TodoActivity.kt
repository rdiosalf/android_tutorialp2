package com.alten.androidtut.todoapp

import android.app.Dialog
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.WindowInsets
import android.view.WindowInsetsController
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import android.widget.RadioGroup
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alten.androidtut.R
import com.alten.androidtut.firstApp.FirstAppActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoActivity : AppCompatActivity() {

  //para la rv de categorias
  private lateinit var rvCategories: RecyclerView
  private lateinit var categoriesAdapter: CategoriesAdapter


  //para la rv de tasks
  private lateinit var rvTasks: RecyclerView
  private lateinit var tasksAdapter: TasksAdapter


  private lateinit var fabAddTask: FloatingActionButton


  //creación de un listado con las tres opciones que quiero q muestre
  private val categories = listOf(
    TaskCategory.Business,
    TaskCategory.Personal,
    TaskCategory.Other
  )


  //creación de un listado mutable porque es el que vamos a ir actualizando
  private val tasks = mutableListOf(
    Task("PruebaBusiness", TaskCategory.Business),
    Task("PruebaPersonal", TaskCategory.Personal),
    Task("PruebaOther", TaskCategory.Other)
  )


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    enableEdgeToEdge()
    setContentView(R.layout.activity_todo)

    initComponent()
    initUI()
    initListeners()
    //estoo oculta la barra de navegacion de android
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
      window.setDecorFitsSystemWindows(false)
      val controller = window.insetsController
      controller?.hide(WindowInsets.Type.navigationBars() or WindowInsets.Type.statusBars())
      controller?.systemBarsBehavior = WindowInsetsController.BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE
    } else {
      @Suppress("DEPRECATION")
      window.decorView.systemUiVisibility = (
          View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
              or View.SYSTEM_UI_FLAG_FULLSCREEN
              or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
          )
    }
    //esto ajusta para que no quede debajo de las barras del sistema
    ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
      val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
      v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
      insets
    }
  }


  /**recogida de las referencias de los xml
   *
   */
  private fun initComponent() {
    rvCategories = findViewById(R.id.rvCategories)
    rvTasks = findViewById(R.id.rvTasks)
    fabAddTask = findViewById(R.id.fabAddTask)
  }

  /**
   * inicializa la ui
   */
  private fun initUI() {
    //antes de la lambda categoriesAdapter = CategoriesAdapter(categories)
    //con llamada a lambda categoriesAdapter = CategoriesAdapter(categories, { position -> updateCategories(position) })
    categoriesAdapter =
      CategoriesAdapter(categories) { position -> updateCategories(position) }//saco la lambda
    //layoutmanager es el q se encarga de que la lista sea horizontal o vertical
    //paso contexto this y el tipo de layout
    rvCategories.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
    rvCategories.adapter = categoriesAdapter


    //tasksAdapter = TasksAdapter(tasks) antes de hacer las func lambda en el adapter
    //tasksAdapter = TasksAdapter(tasks, {onItemSelected(it)} )
    //tasksAdapter = TasksAdapter(tasks, {position->onItemSelected(position)} ) saco la lambda fuera del paréntesis
    tasksAdapter = TasksAdapter(tasks) { position -> onItemSelected(position) }
    rvTasks.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
    rvTasks.adapter = tasksAdapter
  }


  /**
   * si estaba seleccionado , lo desselecciono y viceversa
   * volvemos a refrescar la lista de tasks
   *
   */
  private fun onItemSelected(position: Int) {
    tasks[position].isSelected =
      !tasks[position].isSelected //seteo lo opuesto a lo que había  antes
    updateTasks()
  }


  private fun initListeners() {
    fabAddTask.setOnClickListener {
      showAddTaskDialog()
    }
  }


  private fun showAddTaskDialog() {
    val dialog = Dialog(this)
    dialog.setContentView(R.layout.dialog_task)//enganchar

    //el acceso find...no es al genral sino al dialogo por eso accedemos con el objeto dialog
    val btnAddTask: Button = dialog.findViewById(R.id.btnAddTask)
    val etTask: EditText = dialog.findViewById(R.id.etTask)
    val rgCategories: RadioGroup = dialog.findViewById(R.id.rgCategories)
    //cuando pulso es cuando quiero saber q radiobutton está seleccionado y no antes
    //pq el usuario puede clicar cien veces
    //escucho al clicar y obtengo el id del radiobutton seleccionado
    btnAddTask.setOnClickListener {
      val currentTask = etTask.text
      if (currentTask.isNotEmpty()) {
        val selectedId = rgCategories.checkedRadioButtonId
        val selectedRadioButton: RadioButton = rgCategories.findViewById(selectedId)
        Log.i(FirstAppActivity.TAG, "Radiobutton seleccionado ${selectedRadioButton.id}")

        val currentCategory: TaskCategory = when (selectedRadioButton.text) {
          getString(R.string.todo_category_business) -> TaskCategory.Business
          getString(R.string.todo_category_personal) -> TaskCategory.Personal
          getString(R.string.todo_category_others) -> TaskCategory.Other
          else -> {
            TaskCategory.Other
          }
        }
        //añadir la tarea al objeto de tareas --- añade la lista al RV
        tasks.add(Task(etTask.text.toString(), currentCategory))
        //pero aunque la he añadido al RV el adapter no se ha enterado de eso
        //teng q comunicar al adapter de estos nuevos items añadidos
        updateTasks()
        //una vez pulsado oculto dialogo
        dialog.hide()
      }//si está vacío la tarea o sea, le he dado al botón si añadir nada,  el listener no hace nada
    }

    dialog.show()//mostrar

  }

  /**
   * avisa al adapter de que se han añadido nuevos items
   */
  private fun updateTasks() {
    //filtrado para que aparezcan o no tareas segun el filtrado de categorias
    val selectedCategories: List<TaskCategory> = categories.filter{ it.isSelected }
    //filtrado de entre todas las tareas aquellas de las categorias seleccionadas
    //ej. pruebabussines es tarea, y pertenece a categoria bussines,
    //¿cat bussines está seleccionada? sí, entonces la meto, no, entonces no la meto
    val newTasks = tasks.filter { selectedCategories.contains(it.category) }

    //esto antes de filtrado lo hacía así con el adapter directamente notificaba ahora digo
    //actualizame las tareas en el adapter y luego notifica al adapter de todos los cambios
    tasksAdapter.tasks = newTasks
    Log.i(FirstAppActivity.TAG, "actualizando tareas $newTasks")
    tasksAdapter.notifyDataSetChanged()//esto comprueba uno a uno todos q no es lo mas optimo

  }

  private fun updateCategories(position: Int) {
    categories[position].isSelected = !categories[position].isSelected
    categoriesAdapter.notifyItemChanged(position)//solo cambia un objeto
    updateTasks()
  }

}