package com.example.mitecmm.ui;

import android.app.AlertDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ArrayAdapter;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.mitecmm.R;
import com.example.mitecmm.dao.AvisoDAO;
import com.example.mitecmm.dao.CarreraDAO;
import com.example.mitecmm.dao.ProfesorDAO;
import com.example.mitecmm.model.Aviso;
import com.example.mitecmm.model.Carrera;
import com.example.mitecmm.model.Profesor;
import com.google.android.material.button.MaterialButtonToggleGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class AdminPanelActivity extends BaseMActivity {

    // VARIÁVEIS DE CONTROLO - Atualizado para RecyclerView
    private RecyclerView recyclerView;
    private CarreraDAO carreraDAO;
    private ProfesorDAO profesorDAO;
    private AvisoDAO avisoDAO;
    private MaterialButtonToggleGroup tabGroup;
    private FloatingActionButton fabAgregar;

    // Estado da Aba Ativa (0=Carreras, 1=Profesores, 2=Avisos)
    private int currentTab = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_panel);

        // Inicializar DAOs
        carreraDAO = new CarreraDAO(this);
        profesorDAO = new ProfesorDAO(this);
        avisoDAO = new AvisoDAO(this);

        // Vincular Componentes XML
        tabGroup = findViewById(R.id.tabGroup);
        recyclerView = findViewById(R.id.recyclerAdminGeneric);
        fabAgregar = findViewById(R.id.fabAgregar);
        ImageView btnMenu = findViewById(R.id.menuham);

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Lógica dos Botões Segmentados (Tabs)
        tabGroup.addOnButtonCheckedListener((group, checkedId, isChecked) -> {
            if (isChecked) {
                if (checkedId == R.id.tabCarreras) {
                    mostrarTab(0);
                } else if (checkedId == R.id.tabProfesores) {
                    mostrarTab(1);
                } else if (checkedId == R.id.tabAvisos) {
                    mostrarTab(2);
                }
            }
        });

        // Configurar Botão FAB (Agregar)
        fabAgregar.setOnClickListener(v -> {
            if (currentTab == 0) dialogCarrera(null);
            else if (currentTab == 1) dialogProfesor(null);
            else if (currentTab == 2) dialogAviso(null);
        });

        // Configurar Menu Lateral
        btnMenu.setOnClickListener(v -> abrirMenuLateral());

        // Carregar Aba por defeito
        mostrarTab(0);
    }

    private void mostrarTab(int tab) {
        currentTab = tab;
        if (tab == 0) cargarCarreras();
        if (tab == 1) cargarProfesores();
        if (tab == 2) cargarAvisos();
    }

    // ==========================================
    // LÓGICA DE CARRERAS
    // ==========================================
    private void cargarCarreras() {
        List<Carrera> lista = carreraDAO.showAll();
            AdminCarreraAdapter adapter = new AdminCarreraAdapter(lista);
            recyclerView.setAdapter(adapter);

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
            }
            cargarCarreras();
        }).setNegativeButton("Cancelar", null).show();
    }

    private void opcionesCarrera(Carrera c) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar")
                .setMessage("¿Desea Eliminar " + c.getNombre() + "?")
                .setPositiveButton("Eliminar", (d, w) -> {
                    carreraDAO.eliminar(c.getIdCarrera());
                    cargarCarreras();
                    Toast.makeText(this, "Carrera Eliminada", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    // ==========================================
    // LÓGICA DE PROFESORES
    // ==========================================
    private void cargarProfesores() {
        List<Profesor> lista = profesorDAO.showAll();
           AdminProfesorAdapter adapter = new AdminProfesorAdapter(lista);
           recyclerView.setAdapter(adapter);

    }

    private void dialogProfesor(Profesor profesor) {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_profesor, null);
        EditText etNombre = view.findViewById(R.id.etNombreProfesor);
        Spinner spinnerCarrera = view.findViewById(R.id.spinnerCarrera);
        EditText etUrlHorario = view.findViewById(R.id.etUrlHorario);

        List<Carrera> carreras = carreraDAO.showAll();
        String[] nombresCarreras = new String[carreras.size()];
        for (int i = 0; i < carreras.size(); i++) {
            nombresCarreras[i] = carreras.get(i).getNombre();
        }
        spinnerCarrera.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, nombresCarreras));

        if (profesor != null) {
            etNombre.setText(profesor.getNombre());
            etUrlHorario.setText(profesor.getUrlHorario());
            for (int i = 0; i < carreras.size(); i++) {
                if (carreras.get(i).getIdCarrera() == profesor.getCarrera()) {
                    spinnerCarrera.setSelection(i);
                    break;
                }
            }
        }

        new AlertDialog.Builder(this)
                .setTitle(profesor == null ? "Nuevo profesor" : "Editar profesor")
                .setView(view)
                .setPositiveButton("Guardar", (d, w) -> {

            String nombre = etNombre.getText().toString().trim();
            String linkPdf = etUrlHorario.getText().toString().trim();

            if (nombre.isEmpty()) {
                Toast.makeText(this, "Escribe el nombre", Toast.LENGTH_SHORT).show();
                return;
            }
            int idCarrera = carreras.get(spinnerCarrera.getSelectedItemPosition()).getIdCarrera();
            if (profesor == null) {
                profesorDAO.insertarP(nombre, idCarrera, linkPdf);
            } else {
                profesorDAO.actualizar(profesor.getIdProfesor(), nombre, idCarrera, linkPdf);
            }
            cargarProfesores();
        }).setNegativeButton("Cancelar", null).show();
    }

    private void opcionesProfesor(Profesor p) {
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

    // ==========================================
    // LÓGICA DE AVISOS
    // ==========================================
    private void cargarAvisos() {
        recyclerView.setAdapter(null);
        List<Aviso> lista = avisoDAO.showAll();

        android.util.Log.d("AVISO_DEBUG", "Total avisos encontrados: " + lista.size());

        AdminAvisoAdapter adapter = new AdminAvisoAdapter(lista);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

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

            for(int i = 0; i < categorias.length; i++){
                if(categorias[i].equals(aviso.getCategoria())){
                    spinnerCategoria.setSelection(i);
                    break;
                }
            }
        }

        new AlertDialog.Builder(this).setTitle(aviso == null ? "Nuevo Aviso" : "Editar aviso").setView(view).setPositiveButton("Guardar", (d, w) -> {
            String titulo = etTitulo.getText().toString().trim();
            String descripcion = etDesc.getText().toString().trim();
            String fecha = etFecha.getText().toString().trim();
            String categoria = spinnerCategoria.getSelectedItem().toString();

            if(titulo.isEmpty() || descripcion.isEmpty() || fecha.isEmpty()){
                Toast.makeText(this, "Complete los campos", Toast.LENGTH_SHORT).show();
                return;
            }
            if(aviso == null){
                boolean ok = avisoDAO.insert(titulo, descripcion, fecha, categoria);
                android.util.Log.d("AVISO_DEBUG", "Insert resultado: " + ok +
                        " | titulo=" + titulo +
                        " | desc=" + descripcion +
                        " | fecha=" + fecha +
                        " | cat=" + categoria);
                Toast.makeText(this, ok ? "Guardado ✓" : "ERROR al guardar", Toast.LENGTH_SHORT).show();
            } else {
                avisoDAO.update(aviso.getIdAviso(), titulo, descripcion, fecha, categoria);
            }
            cargarAvisos();
        }).setNegativeButton("Cancelar", null).show();
    }

    private void opcionesAviso(Aviso a) {
        new AlertDialog.Builder(this)
                .setTitle("Confirmar")
                .setMessage("¿Eliminar el aviso " + a.getTitulo() + "?")
                .setPositiveButton("Eliminar", (d, w) -> {
                    avisoDAO.delete(a.getIdAviso());
                    cargarAvisos();
                    Toast.makeText(this, "Aviso eliminado", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    // =========================================================================
    // ADAPTADORES INTERNOS (El motor de las tarjetas bonitas)
    // =========================================================================

    // View Holder Genérico (Liga os IDs do XML item_admin_generic)
    class AdminGenericViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo, tvSubtitulo;
        ImageView imgIcono;
        com.google.android.material.button.MaterialButton btnEditar, btnEliminar;

        public AdminGenericViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvItemTitle);
            tvSubtitulo = itemView.findViewById(R.id.tvItemSubtitle);
            imgIcono = itemView.findViewById(R.id.imgItemIcon);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }

    // 1. Adaptador de Carreras
    class AdminCarreraAdapter extends RecyclerView.Adapter<AdminGenericViewHolder> {
        List<Carrera> lista;
        public AdminCarreraAdapter(List<Carrera> lista) { this.lista = lista; }
        @NonNull @Override public AdminGenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_generic, parent, false);
            return new AdminGenericViewHolder(v);
        }
        @Override public void onBindViewHolder(@NonNull AdminGenericViewHolder holder, int position) {
            Carrera c = lista.get(position);
            holder.tvTitulo.setText(c.getNombre());
            holder.tvSubtitulo.setText("Siglas: " + c.getSiglas());


            holder.imgIcono.setImageResource(android.R.drawable.ic_menu_compass); // Placeholder

            // Configurar botões EDIT e DELETE
            holder.btnEditar.setOnClickListener(v -> dialogCarrera(c));
            holder.btnEliminar.setOnClickListener(v -> opcionesCarrera(c));
        }
        @Override public int getItemCount() { return lista.size(); }

        public void actualizarDatos(List<Carrera> nuevaLista){
            this.lista = nuevaLista;
            notifyDataSetChanged();
        }
    }

    // 2. Adaptador de Profesores
    class AdminProfesorAdapter extends RecyclerView.Adapter<AdminGenericViewHolder> {
        List<Profesor> lista;
        public AdminProfesorAdapter(List<Profesor> lista) { this.lista = lista; }
        @NonNull @Override public AdminGenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_generic, parent, false);
            return new AdminGenericViewHolder(v);
        }
        @Override public void onBindViewHolder(@NonNull AdminGenericViewHolder holder, int position) {
            Profesor p = lista.get(position);
            holder.tvTitulo.setText(p.getNombre());
            holder.tvSubtitulo.setText("ID Carrera: " + p.getCarrera());

            // --- IMPORTANTE: Imagens Vectoriais ---
            // Substitui isto pelo teu vector de Docente do drawable
            holder.imgIcono.setImageResource(android.R.drawable.ic_menu_gallery); // Placeholder

            holder.btnEditar.setOnClickListener(v -> dialogProfesor(p));
            holder.btnEliminar.setOnClickListener(v -> opcionesProfesor(p));
        }
        @Override public int getItemCount() { return lista.size(); }

        public void actualizarDatos(List<Profesor> nuevaLista){
            this.lista = nuevaLista;
            notifyDataSetChanged();
        }
    }

    // 3. Adaptador de Avisos
    class AdminAvisoAdapter extends RecyclerView.Adapter<AdminGenericViewHolder> {
        List<Aviso> lista;
        public AdminAvisoAdapter(List<Aviso> lista) { this.lista = lista; }
        @NonNull @Override public AdminGenericViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_admin_generic, parent, false);
            return new AdminGenericViewHolder(v);
        }
        @Override public void onBindViewHolder(@NonNull AdminGenericViewHolder holder, int position) {
            Aviso a = lista.get(position);
            holder.tvTitulo.setText(a.getTitulo());
            holder.tvSubtitulo.setText(a.getCategoria() + " | Fecha: " + a.getFecha());

            // --- IMPORTANTE: Imagens Vectoriais ---
            holder.imgIcono.setImageResource(android.R.drawable.ic_menu_agenda); // Placeholder

            holder.btnEditar.setOnClickListener(v -> dialogAviso(a));
            holder.btnEliminar.setOnClickListener(v -> opcionesAviso(a));
        }
        @Override public int getItemCount() { return lista.size(); }

        public void actualizarDatos(List<Aviso> nuevaLista){
            this.lista = nuevaLista;
            notifyDataSetChanged();
        }
    }
}