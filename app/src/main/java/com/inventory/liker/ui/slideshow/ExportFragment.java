package com.inventory.liker.ui.slideshow;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileUtils;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inventory.liker.Api.SharePrefrancClass;
import com.inventory.liker.DashboardNavigationDrawer;
import com.inventory.liker.LoginActivity;
import com.inventory.liker.Model.Inventory;
import com.inventory.liker.R;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import au.com.bytecode.opencsv.CSVWriter;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class ExportFragment extends Fragment {

    private static final int PERMISSION_REQUEST_CODE = 200;

    Button confirmBtnId;
    ArrayList<Inventory> transactionList;
    private DatabaseReference myRef;
    String firebasekey;
    String renameFile;
    static final int FILE_PATH_CODE = 0;
    EditText renameEt;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);


        firebasekey = SharePrefrancClass.getInstance(getActivity()).getPref("firebasekey");
        myRef = FirebaseDatabase.getInstance().getReference().child("Location").child(firebasekey).child("Records");

        transactionList = new ArrayList<Inventory>();
        confirmBtnId = root.findViewById(R.id.confirmBtnId);

        confirmBtnId.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Code for intent share
//                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
//                intent.setType("*/*");
//                intent.addCategory(Intent.CATEGORY_OPENABLE);
//                startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_PATH_CODE);

                //code for rename file dialog box
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setTitle("Enter File Name");

                final View customLayout = LayoutInflater.from(getActivity()).inflate(R.layout.cusotm_dialog_rename, null);
                builder.setView(customLayout);

                renameEt = customLayout.findViewById(R.id.renameEt);

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        if (validation()) {
                            renameFile = renameEt.getText().toString();
                            showFileChooser();
//                            Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_managebrand);
                        }
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();
                    }
                });
                builder.show();

            }
        });
        return root;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        switch (requestCode) {
            case FILE_PATH_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    data.getData().getPath();
                    Toast.makeText(getActivity(), data.getData().getPath(), Toast.LENGTH_SHORT).show();
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
        super.onActivityResult(requestCode, resultCode, data);

    }

    private void showItemsFromDatabase() {
        myRef.addValueEventListener(new ValueEventListener() {
            @RequiresApi(api = Build.VERSION_CODES.R)
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                transactionList.clear();
                for (DataSnapshot childSnapshot : snapshot.getChildren()) {
                    Inventory inventory = childSnapshot.getValue(Inventory.class);
                    transactionList.add(inventory);
                }

                if (snapshot.getValue() != null) {


                    writeListObjects2CsvFile2(transactionList);


//                    String str_path = String.valueOf(Environment.getStorageDirectory());
//                    File file = new File(str_path, getString(R.string.app_name) + ".csv");
//                    writeListObjects2CsvFile(transactionList, file);

                    /*String root = Environment.getExternalStorageDirectory().toString();
                    Random generator = new Random();
                    int n = 10000;
                    n = generator.nextInt(n);
                    String a = root + "/Inventory" + n + ".csv";
                    writeListObjects2CsvFile(transactionList, a);*/
                } else {
                    Toast.makeText(getActivity(), "Please Import data", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //      Write List Objects to CSV File
    private void writeListObjects2CsvFile(ArrayList<Inventory> inventory) {

//        final String[] CSV_HEADER = { "artikel", "dodatek", "polnih", "odprtih", "teza", "dodatkov", "dodatna",
//                "knjizno", "popisano", "enota", "nabavna"};
        final String[] CSV_HEADER = {"Artikel", "Dodatek", "Polnih (kos)", "Odprtih", "Teža", "Dodatkov", "Dodatna kol. (lit/kg)",
                "Knjižno stanje pred inventuro", "Popisano skupaj", "Enota", "Nabavna cena"};

        BufferedWriter fileWriter = null;
        CSVPrinter csvPrinter = null;

        try {

            String rootPath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/Inventory/";
            File root = new File(rootPath);
            if (!root.exists()) {
                root.mkdirs();
            }
            Random generator = new Random();
            int n = 100;
            n = generator.nextInt(n);
            File f = new File(rootPath + "inventory" + n + ".csv");
            f.createNewFile();


            fileWriter = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(f), "Cp1252"));
            csvPrinter = new CSVPrinter(fileWriter, CSVFormat.DEFAULT.withHeader(CSV_HEADER));

            for (Inventory inventory1 : inventory) {
                List<String> data = Arrays.asList(
                        inventory1.getArtikel(),
                        inventory1.getDodatek(),
                        inventory1.getPolnih(),
                        inventory1.getOdprtih(),
                        inventory1.getTeza(),
                        inventory1.getDodatkov(),
                        inventory1.getDodatna(),
                        inventory1.getKnjizno(),
                        inventory1.getPopisano(),
                        inventory1.getEnota(),
                        inventory1.getNabavna());
                csvPrinter.printRecord(data);
            }

            Toast.makeText(getActivity(), "SuccessFully Export", Toast.LENGTH_SHORT).show();

        } catch (Exception e) {
            System.out.println("Writing CSV error!");
            e.printStackTrace();
        } finally {
            try {
                assert fileWriter != null;
                fileWriter.flush();
                fileWriter.close();
                assert csvPrinter != null;
                csvPrinter.close();
            } catch (IOException e) {
                System.out.println("Flushing/closing error!");
                e.printStackTrace();
            }
        }
    }


    private void writeListObjects2CsvFile2(ArrayList<Inventory> inventory/*, File pathFile*/) {
        try {
            String rootPath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/Inventory/";
            File root = new File(rootPath);
            if (!root.exists()) {
                root.mkdirs();
            }
            Random generator = new Random();
            int n = 100;
            n = generator.nextInt(n);

            File f = new File(rootPath + renameFile + ".xls");

//            File f = new File(rootPath + "inventory" + n + ".xls");
            f.createNewFile();


            //Iterate over data and write to sheet


            HSSFWorkbook workbook = new HSSFWorkbook();

            HSSFSheet sheet = workbook.createSheet("sheet1");// creating a blank sheet

            // Create a Row
            Row headerRow = sheet.createRow(0);

            // Create cells
            for (int i = 0; i < inventory.size(); i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(String.valueOf(inventory.get(i)));
            }

            // Create Other rows and cells with employees data
            int rowNum = 0;
            for (Inventory employee : inventory) {
                Row row = sheet.createRow(rowNum++);

                row.createCell(0)
                        .setCellValue(employee.getArtikel());

                row.createCell(1)
                        .setCellValue(employee.getDodatek());
                row.createCell(2)
                        .setCellValue(employee.getPolnih());
                row.createCell(3)
                        .setCellValue(employee.getOdprtih());
                row.createCell(4)
                        .setCellValue(employee.getTeza());
                row.createCell(5)
                        .setCellValue(employee.getDodatkov());
                row.createCell(6)
                        .setCellValue(employee.getDodatna());
                row.createCell(7)
                        .setCellValue(employee.getKnjizno());
                row.createCell(8)
                        .setCellValue(employee.getPopisano());
                row.createCell(9)
                        .setCellValue(employee.getEnota());
                row.createCell(10)
                        .setCellValue(employee.getNabavna());

                /*Cell dateOfBirthCell = row.createCell(2);
                dateOfBirthCell.setCellValue(employee.getDateOfBirth());
                dateOfBirthCell.setCellStyle(dateCellStyle);

                row.createCell(3)
                        .setCellValue(employee.getSalary());*/
            }


            FileOutputStream out = new FileOutputStream(f);
            workbook.write(out);
            shareFile(f);
            Toast.makeText(getActivity(), "SuccessFully Export", Toast.LENGTH_SHORT).show();
            out.close();


        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void showFileChooser() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            //File write logic here
        } else {
            showItemsFromDatabase();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "Storage permission granted", Toast.LENGTH_LONG).show();
                showItemsFromDatabase();
            } else {
                Toast.makeText(getActivity(), "Storage permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    boolean validation() {
        if (renameEt.getText().toString().equalsIgnoreCase("")) {
            renameEt.setError("please Enter Name");
            return false;
        }
        return true;
    }

    private void shareFile(File file) {

        Intent intentShareFile = new Intent(Intent.ACTION_SEND);

        if(file.exists()) {
//            intentShareFile.setType(URLConnection.guessContentTypeFromName(file.getName()));
            intentShareFile.setType("application/excel");

            intentShareFile.putExtra(Intent.EXTRA_STREAM, Uri.parse(file.toString()));
//            intentShareFile.putExtra(Intent.EXTRA_STREAM, "file://"+file.toString());
//            intentShareFile.putExtra(Intent.EXTRA_STREAM, path);
            intentShareFile.putExtra(Intent.EXTRA_SUBJECT, "Sharing File...");
            startActivity(intentShareFile);
//            startActivity(Intent.createChooser(intentShareFile, "Share File"));
        }

    }

}