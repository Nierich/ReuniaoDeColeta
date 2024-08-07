package com.example.reuniaodecoleta.TelaRequisito;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.Manifest;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.reuniaodecoleta.BaseActivity;
import com.example.reuniaodecoleta.DatabaseManager;
import com.example.reuniaodecoleta.Entidade.Projeto;
import com.example.reuniaodecoleta.Entidade.Requisito;
import com.example.reuniaodecoleta.R;
import com.example.reuniaodecoleta.TelaProjeto.ListagemProjeto;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;


import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class CadastroRequisito extends BaseActivity {

    private EditText etDescricao = null;
    private Spinner spn_tipo = null;
    private Spinner spn_importancia = null;
    private Spinner spn_dificuldade = null;
    private EditText et_horas = null;
    private Spinner spn_desenvolvedores = null;
    private Spinner spn_projeto = null;
    private SQLiteDatabase bancoDeDados;
    private DatabaseManager dataBaseManager;
    private List<Projeto> projetoList;
    private TextView tv_latitude;
    private TextView tv_longitude;
    private Location localizacao;
    private static final int REQUEST_LOCATION_PERMISSION = 1001;
    private static final int CAMERA_REQUEST_1 = 1888;
    private static final int CAMERA_REQUEST_2 = 1889;
    private ImageView img_foto_1;
    private ImageView img_foto_2;
    private int currentCameraRequest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cadastro_requisito);

        setUpToolbar(R.id.myToolbar);

        confirmaPermissaoDeLocalizacao();

        // Inicializa o DatabaseManager e o banco de dados
        dataBaseManager = new DatabaseManager(this, "aplicacaodb", 5);
        bancoDeDados = dataBaseManager.getWritableDatabase();

        etDescricao = (EditText) findViewById(R.id.et_descricao_requisito);
        et_horas = (EditText) findViewById(R.id.et_horas_estimadas);
        spn_tipo = (Spinner) findViewById(R.id.spn_tipo_requisito);
        spn_importancia = (Spinner) findViewById(R.id.spn_importancia_requisito);
        spn_dificuldade = (Spinner) findViewById(R.id.spn_dificuldade_requisito);
        spn_desenvolvedores = (Spinner) findViewById(R.id.spn_desenvolvedores_requisito);
        spn_projeto = (Spinner) findViewById(R.id.spn_projeto);

        tv_latitude = (TextView) findViewById(R.id.tv_latitude);
        tv_longitude = (TextView) findViewById(R.id.tv_longitude);

        img_foto_1 = (ImageView) findViewById(R.id.img_foto1);
        img_foto_2 = (ImageView) findViewById(R.id.img_foto2);

        configurarSpinner();

        Button bt_criar_requisito = findViewById(R.id.bt_criar_requisito);
        bt_criar_requisito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String descricao = etDescricao.getText().toString();
                String tipo = spn_tipo.getSelectedItem().toString();
                String importancia = spn_importancia.getSelectedItem().toString();
                String dificuldade = spn_dificuldade.getSelectedItem().toString();
                String horas = et_horas.getText().toString();
                String desenvolvedores = spn_desenvolvedores.getSelectedItem().toString();
                String latitude = tv_latitude.getText().toString();
                String longitude = tv_longitude.getText().toString();

                if (descricao.isEmpty() || horas.isEmpty()) {
                    Toast.makeText(CadastroRequisito.this, "Por favor, preencha todos os campos!", Toast.LENGTH_LONG).show();
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                    String currentDateAndTime = sdf.format(new Date());

                    Requisito requisito = new Requisito();
                    requisito.setData_hora_registro(currentDateAndTime);
                    requisito.setDescricao(descricao);
                    requisito.setTipo(tipo);
                    requisito.setNivel_importancia(importancia);
                    requisito.setNivel_dificuldade(dificuldade);
                    requisito.setHoras_estimadas(horas);
                    requisito.setQntd_desenvolvedores(desenvolvedores);
                    requisito.setLatitude(latitude);
                    requisito.setLongitude(longitude);

                    salvarRequisitoNoBanco(requisito);
                }
            }
        });

        Button bt_tirar_foto_1 = findViewById(R.id.btn_tirar_foto1);
        bt_tirar_foto_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCameraRequest = CAMERA_REQUEST_1;
                TirarUmaFoto();
            }
        });

        Button bt_tirar_foto_2 = findViewById(R.id.btn_tirar_foto2);
        bt_tirar_foto_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                currentCameraRequest = CAMERA_REQUEST_2;
                TirarUmaFoto();
            }
        });
    }

    private void salvarRequisitoNoBanco(Requisito requisito) {
        ContentValues valores = criarContentValuesParaRequisito(requisito);

        long resultado = bancoDeDados.insert("requisito", null, valores);

        if (resultado != -1) {
            Toast.makeText(CadastroRequisito.this, "Requisito criado com sucesso!", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(CadastroRequisito.this, "Erro ao criar requisito!", Toast.LENGTH_LONG).show();
        }
    }

    private ContentValues criarContentValuesParaRequisito(Requisito requisito) {
        ContentValues valores = new ContentValues();
        valores.put("data_hora_registro", requisito.getData_hora_registro());
        valores.put("descricao", requisito.getDescricao());
        valores.put("tipo", requisito.getTipo());
        valores.put("nivel_importancia", requisito.getNivel_importancia());
        valores.put("nivel_dificuldade", requisito.getNivel_dificuldade());
        valores.put("horas_estimadas", requisito.getHoras_estimadas());
        valores.put("qntd_desenvolvedores", requisito.getQntd_desenvolvedores());
        valores.put("latitude", requisito.getLatitude());
        valores.put("longitude", requisito.getLongitude());

        // Insere o projeto selecionado pelo usuário
        Projeto projetoSelecionado = (Projeto) spn_projeto.getSelectedItem();
        valores.put("projeto_id", projetoSelecionado.getId());

        // Insere a imagem 1 convertida em array de bytes
        Bitmap bitmap = ((BitmapDrawable) img_foto_1.getDrawable()).getBitmap();
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray1 = stream.toByteArray();
        valores.put("foto1", byteArray1);

        // Insere a imagem 2 convertida em array de bytes
        Bitmap bitmap2 = ((BitmapDrawable) img_foto_2.getDrawable()).getBitmap();
        ByteArrayOutputStream stream2 = new ByteArrayOutputStream();
        bitmap2.compress(Bitmap.CompressFormat.PNG, 100, stream2);
        byte[] byteArray2 = stream2.toByteArray();
        valores.put("foto2", byteArray2);

        return valores;
    }

    public Cursor fazerConsulta() {
        SQLiteDatabase db = dataBaseManager.getReadableDatabase();
        return db.rawQuery("SELECT * FROM projeto ORDER BY id", null);
    }

    // Método para configurar o Spinner e carregar as categorias
    private void configurarSpinner() {
        // Consulta as categorias do banco de dados
        Cursor cursor = fazerConsulta();

        // Cria uma lista de categorias
        projetoList = new ArrayList<>();
        if (cursor != null && cursor.moveToFirst()) {
            int idIndex = cursor.getColumnIndex("id");
            int nomeIndex = cursor.getColumnIndex("nome");

            if (idIndex != -1 && nomeIndex != -1) {
                do {
                    int id = cursor.getInt(idIndex);
                    String nome = cursor.getString(nomeIndex);
                    Projeto projeto = new Projeto(id, nome);
                    projetoList.add(projeto);
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        // Cria um ArrayAdapter para o Spinner
        ArrayAdapter<Projeto> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, projetoList);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spn_projeto.setAdapter(adapter);
    }

    private void confirmaPermissaoDeLocalizacao() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // Permissões concedidas, pode acessar a localização
            marcarPosicao();
        } else {
            // Permissões não concedidas, solicita ao usuário
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                    REQUEST_LOCATION_PERMISSION);
        }
    }

    private void marcarPosicao() {
        FusedLocationProviderClient localizacaoService = LocationServices.getFusedLocationProviderClient(this);

        try {
            localizacaoService.getLastLocation().addOnSuccessListener(this, new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    if (location != null) {
                        localizacao = location;
                        tv_latitude.setText(String.format(Locale.getDefault(), "%.6f", location.getLatitude()));
                        tv_longitude.setText(String.format(Locale.getDefault(), "%.6f", location.getLongitude()));
                    } else {
                        Toast.makeText(CadastroRequisito.this, "Localização não encontrada", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        } catch (SecurityException e) {
            e.printStackTrace();
            Toast.makeText(this, "Permissão de localização não concedida", Toast.LENGTH_SHORT).show();
        }
    }

    // Método para tratar o resultado da solicitação de permissões
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // Chamada ao método da classe pai

        if (requestCode == REQUEST_LOCATION_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permissões concedidas, tenta acessar a localização novamente
                marcarPosicao();
            } else {
                // Permissões negadas pelo usuário, trate aqui conforme necessário
                Toast.makeText(this, "Permissão de localização não concedida", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void TirarUmaFoto() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
            Toast.makeText(this, "Tente novamente!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            Toast.makeText(this, "Não foi possível liberar a funcionalidade de câmera", Toast.LENGTH_SHORT).show();
        } else {
            Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(cameraIntent, currentCameraRequest);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK && data != null) {
            Bitmap photo = (Bitmap) data.getExtras().get("data");
            if (requestCode == CAMERA_REQUEST_1) {
                img_foto_1.setImageBitmap(photo);
            } else if (requestCode == CAMERA_REQUEST_2) {
                img_foto_2.setImageBitmap(photo);
            }
        }
    }
}
