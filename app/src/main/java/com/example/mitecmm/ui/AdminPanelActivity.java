package com.example.mitecmm.ui;


import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.mitecmm.R;
import com.example.mitecmm.dao.AvisoDAO;
import com.example.mitecmm.dao.CarreraDAO;
import com.example.mitecmm.dao.ProfesorDAO;
import com.example.mitecmm.model.Aviso;
import com.example.mitecmm.model.Carrera;
import com.example.mitecmm.model.Profesor;

import java.util.List;

public class AdminPanelActivity extends BaseMActivity {

    //variables de control
    private Button tabCarreras, tabProfesores, tabAvisos;
    private LinearLayout panelCarreras, panelProfesores, panelAvisos;
    private ListView listCarreras, listProfesores, listAvisos;
    private CarreraDAO carreraDAO;
    private ProfesorDAO profesorDAO;
    private AvisoDAO avisoDAO;

    //Inicialización
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        carreraDAO = new CarreraDAO(this);
        profesorDAO = new ProfesorDAO(this);
        avisoDAO = new AvisoDAO(this);

        tabCarreras = findViewById(R.id.tabCarreras);
        tabProfesores = findViewById(R.id.tabProfesores);
        tabAvisos = findViewById(R.id.tabAvisos);

        panelCarreras = findViewById(R.id.panelCarreras);
        panelProfesores = findViewById(R.id.panelCarreras);
        panelAvisos = findViewById(R.id.panelAvisos);

        listCarreras = findViewById(R.id.listCarreras);
        listProfesores = findViewById(R.id.listProfesores);
        listAvisos = findViewById(R.id.listAvisos);

        ImageView btnMenu = findViewById(R.id.menuAdmin);
        btnMenu.setOnClickListener(v -> abrirMenuLateral());

        findViewById(R.id.fabAgregarCarrera).setOnClickListener(v -> dialogCarrera(null));
        findViewById(R.id.fabAgregarProfesor).setOnClickListener(v -> dialogProfesor(null));
        findViewById(R.id.fabAgregarAviso).setOnClickListener(v -> dialogAviso(null));

        tabCarreras.setOnClickListener(v -> mostrarTab(0));
        tabProfesores.setOnClickListener(v -> mostrarTab(1));
        tabAvisos.setOnClickListener(v -> mostrarTab(2));

        mostrarTab(0);
    }

    private void mostrarTab(int tab) {
        panelCarreras.setVisibility(tab == 0 ? View.VISIBLE : View.GONE);
        panelProfesores.setVisibility(tab == 1 ? View.VISIBLE : View.GONE);
        panelAvisos.setVisibility(tab == 2 ? View.VISIBLE : View.GONE);

        if (tab == 0) cargarCarreras();
        if (tab == 1) cargarProfesores();
        if (tab == 2) cargarAvisos();
    }

    //cargar carreras
    private void cargarCarreras() {
        List<Carrera> lista = carreraDAO.showAll();
        String[] items = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            items[i] = lista.get(i).getNombre() + " (" + lista.get(i).getSiglas() + ")";
        }

        listCarreras.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items));
        listCarreras.setOnItemLongClickListener((parent, view, position, id) -> {
            opcionesCarrera(lista.get(position));
            return true;
        });
    }

    private void opcionesCarrera(Carrera c) {
        new AlertDialog.Builder(this).setTitle(c.getNombre()).setItems(new String[]{"Editar", "Eliminar"}, (dialog, which) -> {
            if (which == 0) {
                dialogCarrera(c);
            } else {
                new AlertDialog.Builder(this).setTitle("Confirmar").setMessage("¿Desea Eliminar" + c.getNombre() + "?").setPositiveButton("Elimianr", (d, w) -> {
                    carreraDAO.eliminar(c.getIdCarrera());
                    cargarCarreras();
                    Toast.makeText(this, "Carrera Eliminada", Toast.LENGTH_SHORT).show();
                }).setNegativeButton("Cancelar", null).show();
            }
        }).show();
    }

    private void dialogCarrera(Carrera carrera) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_carreras, null);
        EditText etNombre = view.findViewById(R.id.etNombreCarrera);
        EditText etsiglas = view.findViewById(R.id.etSiglasCarrera);

        if (carrera != null) {
            etNombre.setText(carrera.getNombre());
            etsiglas.setText(carrera.getSiglas());
        }
        new AlertDialog.Builder(this).setTitle(carrera == null ? "Nueva carrera" : "Editar carrera").setView(view).setPositiveButton("Guardar", (d, w) -> {
            String nombre = etNombre.getText().toString().trim();
            String siglas = etsiglas.getText().toString().trim();

            if (nombre.isEmpty() || siglas.isEmpty()) {
                Toast.makeText(this, "Completa los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            if (carrera == null) {
                carreraDAO.insertar(nombre, siglas);
            } else {
                carreraDAO.actualizar(carrera.getIdCarrera(), nombre, siglas);
                cargarCarreras();
            }
        }).setNegativeButton("Cancelar", null).show();
    }

    //Profesores
    private void cargarProfesores() {
        List<Profesor> lista = profesorDAO.showAll();
        String[] items = new String[lista.size()];
        for (int i = 0; i < lista.size(); i++) {
            items[i] = lista.get(i).getNombre() + "  —  " + lista.get(i).getNombreCarrera();
        }
        listProfesores.setAdapter(new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, items));

        listProfesores.setOnItemLongClickListener((parent, view, pos, id) -> {
            opcionesProfesor(lista.get(pos));
            return true;
        });
    }

    private void opcionesProfesor(Profesor p) {
        new AlertDialog.Builder(this)
                .setTitle(p.getNombre())
                .setItems(new String[]{"Editar", "Eliminar"}, (dialog, which) -> {
                    if (which == 0) {
                        dialogProfesor(p);
                    } else {
                        new AlertDialog.Builder(this)
                                .setTitle("Confirmar")
                                .setMessage("¿Eliminar a " + p.getNombre() + "?")
                                .setPositiveButton("Eliminar", (d, w) -> {
                                    profesorDAO.eliminar(p.getIdProfesor());
                                    cargarProfesores();
                                    Toast.makeText(this, "Profesor eliminado", Toast.LENGTH_SHORT).show();
                                })
                                .setNegativeButton("Cancelar", null)
                                .show();
                    }
                }).show();
    }

    private void dialogProfesor(Profesor profesor) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_profesor, null);
        EditText etNombre = view.findViewById(R.id.etNombreProfesor);
        Spinner spinnerCarrera = view.findViewById(R.id.spinnerCarrera);

        List<Carrera> carreras = carreraDAO.showAll();
        String[] nombresCarreras = new String[carreras.size()];
        for (int i = 0; i < carreras.size(); i++) {
            nombresCarreras[i] = carreras.get(i).getNombre();
        }
        spinnerCarrera.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombresCarreras));
        if (profesor != null) {
            etNombre.setText(profesor.getNombre());
            for (int i = 0; i < carreras.size(); i++) {
                if (carreras.get(i).getIdCarrera() == profesor.getCarrera()) {
                    spinnerCarrera.setSelection(i);
                    break;
                }
            }
        }

        new AlertDialog.Builder(this).setTitle(profesor == null ? "Nuevo profesor" : "Editar profesor").setView(view).setPositiveButton("Guardar", (d, w) -> {
            String nombre = etNombre.getText().toString().trim();
            if (nombre.isEmpty()) {
                Toast.makeText(this, "Escribe el nombre", Toast.LENGTH_SHORT).show();
                return;
            }
            int idCarrera = carreras.get(spinnerCarrera.getSelectedItemPosition()).getIdCarrera();
            if (profesor == null) {
                profesorDAO.insertarP(nombre, idCarrera);
            } else {
                profesorDAO.actualizar(profesor.getIdProfesor(), nombre, idCarrera);
            }
            cargarProfesores();
        }).setNegativeButton("Cancelar", null).show();
    }

    //avisos
    private void cargarAvisos() {
        List<Aviso> lista = avisoDAO.showAll();
        String[] items = new String[lista.size()];
        for (int i = 0; i <= lista.size(); i++) {
            items[i] = " [" + lista.get(i).getCategoria() + "] " + lista.get(i).getTitulo();
        }
        listAvisos.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, items));

        listAvisos.setOnItemLongClickListener((parent, view, position, id) -> {
            opcionesAviso(lista.get(position));
            return true;
        });
    }

    private void opcionesAviso(Aviso a) {
        new AlertDialog.Builder(this).setTitle(a.getTitulo()).setItems(new String[]{"Editar", "Eliminar"}, (dialog, which) -> {
            if (which == 0) {
                dialogAviso(a);
            } else {
                new AlertDialog.Builder(this).setTitle("Confirmar").setMessage("¿Eliminar el aviso " + a.getTitulo() + "?").setPositiveButton("Eliminar", (d, w) -> {
                    avisoDAO.delete(a.getIdAviso());
                    cargarAvisos();
                    Toast.makeText(this, "Aviso eliminado", Toast.LENGTH_SHORT).show();
                }).setNegativeButton("Cancelar", null).show();
            }
        }).show();
    }

    private void dialogAviso(Aviso aviso){
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_avisos, null);
        EditText etTitulo = view.findViewById(R.id.etTituloAviso);
        EditText etDesc = view.findViewById(R.id.etDescripcionAviso);
        EditText etFecha = view.findViewById(R.id.etFechaAviso);
        Spinner spinnerCategoria = view.findViewById(R.id.spinnerCategoria);

        String[] categorias = {"Evento", "Urgente", "Trámite", "General"};
        spinnerCategoria.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, categorias));

        if(aviso != null){
            etTitulo.setText(aviso.getTitulo());
            etDesc.setText(aviso.getDescripcion());
            etFecha.setText(aviso.getFecha());

            for(int i = 0; i <= categorias.length; i++){
                if(categorias[i].equals(aviso.getCategoria())){
                    spinnerCategoria.setSelection(i);
                    break;
                }
            }
        }

        new AlertDialog.Builder(this).setTitle(aviso == null ? "Nuevo Aviso" : "Editar aviso").setView(view).setPositiveButton("Guardar", (d, w) -> {
            String titulo = etTitulo.getText().toString().trim();
            String descripción = etDesc.getText().toString().trim();
            String fecha = etFecha.getText().toString().trim();
            String categoría = spinnerCategoria.getSelectedItem().toString();
            if(titulo.isEmpty() || descripción.isEmpty() || fecha.isEmpty()){
                Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show();;
                return;
            }
            if(aviso == null){
                avisoDAO.insert(titulo, descripción, fecha, categoría);
            } else {
                avisoDAO.update(aviso.getIdAviso(), titulo, descripción, fecha, categoría);
                cargarAvisos();
            }
        } ).setNegativeButton("Cancelar", null).show();

    }


}

