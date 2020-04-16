package com.example.organizze.activity.ui.lancamentos;

import android.content.DialogInterface;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.organizze.R;
import com.example.organizze.adapter.RecyclerViewAdapter;
import com.example.organizze.config.ConfiguracaoFirebase;
import com.example.organizze.model.Movimentacao;
import com.example.organizze.model.Usuario;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;

import java.util.ArrayList;
import java.util.List;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class LancamentosFragment extends Fragment {

    private Toolbar toolbarLancamentos;
    private MaterialCalendarView calendarView;
    private CharSequence meses[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio",
            "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
    private CharSequence diasSemana[] = {"Seg", "Terç", "Qua", "Qui", "Sex", "Sáb", "Dom"};
    private String mesDoAno, mes, ano;
    private RecyclerView recyclerView;
    private RecyclerViewAdapter adapter;
    private List<Movimentacao> movimentacao = new ArrayList<>();
    private DatabaseReference movimentacaoRef, usuarioRef = ConfiguracaoFirebase.getEmailCodificado();;
    private Double receita = 0.0, despesa = 0.0, despesaAtualizada = 0.0, receitaAtualizada = 0.0;
    private Movimentacao movim;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lancamentos, null);

        toolbarLancamentos = view.findViewById(R.id.toolbarLancamentos);
        calendarView = view.findViewById(R.id.calendarView);
        recyclerView = view.findViewById(R.id.recyclerViewLancamentos);

        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbarLancamentos);

        calendarView.setTitleMonths(meses);
        calendarView.setWeekDayLabels(diasSemana);

        mes = String.valueOf(calendarView.getCurrentDate().getMonth());
        ano = String.valueOf(calendarView.getCurrentDate().getYear());
        mesDoAno = mes + ano;

        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                mes = String.valueOf(date.getMonth());
                ano = String.valueOf(date.getYear());
                mesDoAno = mes + ano;
                recuperarMovimentacoes();
            }
        });

        recuperarSaldo();

        swipe();

        adapter = new RecyclerViewAdapter(movimentacao, getContext());
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        return view;
    }

    public void swipe(){
        ItemTouchHelper.SimpleCallback itemTouch = new ItemTouchHelper.SimpleCallback(
                0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                excluirMovimentacao(viewHolder);
            }

            @Override
            public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addBackgroundColor(getResources().getColor(R.color.colorCinza))
                        .addActionIcon(R.drawable.ic_delete_branco_24dp)
                        .setSwipeLeftLabelColor(getResources().getColor(R.color.colorAccentBranco))
                        .addSwipeLeftLabel("Deletar")
                        .create()
                        .decorate();

                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };

        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerView);
    }

    public void excluirMovimentacao(RecyclerView.ViewHolder viewHolder){
        AlertDialog.Builder dialog = new AlertDialog.Builder(getContext());
        dialog.setTitle("Confirma a exclusão?");
        dialog.setCancelable(false);
        dialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                int posicao = viewHolder.getAdapterPosition();

                movim = movimentacao.get(posicao);

                movimentacaoRef = ConfiguracaoFirebase.getMovimentacao(mesDoAno);
                movimentacaoRef.child(movim.getId()).removeValue();

                adapter.notifyItemRemoved(posicao);

                atualizarSaldo();
            }
        });

        dialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                adapter.notifyDataSetChanged();
            }
        });
        dialog.create();
        dialog.show();
    }

    public void recuperarSaldo(){
        usuarioRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario usuario = dataSnapshot.getValue(Usuario.class);
                despesa = usuario.getDespesaTotal();
                receita = usuario.getReceitaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    public void atualizarSaldo(){
        if(movim.getTipo().equals("Despesa")){
            despesaAtualizada = despesa - movim.getValor();
            usuarioRef.child("despesaTotal").setValue(despesaAtualizada);
        }else if(movim.getTipo().equals("Receita")){
            receitaAtualizada = receita - movim.getValor();
            usuarioRef.child("receitaTotal").setValue(receitaAtualizada);
        }
    }

    public void recuperarMovimentacoes(){
        movimentacaoRef = ConfiguracaoFirebase.getMovimentacao(mesDoAno);
        movimentacaoRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                movimentacao.clear();
                for(DataSnapshot dados: dataSnapshot.getChildren()){
                    Movimentacao m = dados.getValue(Movimentacao.class);
                    m.setId(dados.getKey());
                    movimentacao.add(m);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_lancamentos, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuPesquisar:
                Toast.makeText(getContext(), "teste search", Toast.LENGTH_SHORT).show();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        recuperarMovimentacoes();
    }
}
