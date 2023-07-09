package com.inventory.liker.ui.gallery;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.inventory.liker.Api.SharePrefrancClass;
import com.inventory.liker.BuildConfig;
import com.inventory.liker.FileUtils;
import com.inventory.liker.Model.Inventory;
import com.inventory.liker.R;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class ImportFragment extends Fragment {

    Button Import;
    TextView file_path;
    String status;
    private DatabaseReference myRef;
    private static final int FILE_SELECT_CODE = 0;
    String jsonString;
    private static final int PERMISSION_REQUEST_CODE = 200;


    @RequiresApi(api = Build.VERSION_CODES.M)
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_import, container, false);
//        myRef = FirebaseDatabase.getInstance().getReference().child("Records");

        String firebasekey = SharePrefrancClass.getInstance(getActivity()).getPref("firebasekey");

        myRef = FirebaseDatabase.getInstance().getReference().child("Location").child(firebasekey).child("Records");
        Import = root.findViewById(R.id.btn_import);
        file_path = root.findViewById(R.id.file_path);

        Import.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        return root;
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(getActivity(), "permission granted", Toast.LENGTH_LONG).show();
                Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                intent.setType("*/*");
                intent.addCategory(Intent.CATEGORY_OPENABLE);
                try {
                    startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
                } catch (android.content.ActivityNotFoundException ex) {
                    Toast.makeText(getActivity(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getActivity(), "Storage permission denied", Toast.LENGTH_LONG).show();
            }

        }

    }

    private void showFileChooser() {

        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
            //File write logic here
        } else {
            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("*/*");
            intent.addCategory(Intent.CATEGORY_OPENABLE);
            try {
                startActivityForResult(Intent.createChooser(intent, "Select a File to Upload"), FILE_SELECT_CODE);
            } catch (android.content.ActivityNotFoundException ex) {
                Toast.makeText(getActivity(), "Please install a File Manager.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case FILE_SELECT_CODE:
                if (resultCode == RESULT_OK) {
                    // Get the Uri of the selected file
                    Uri uri = data.getData();
                    Log.d(TAG, "File Uri: " + uri.getPath());
                    // Get the path
                    /*String path = null;
                    try {
                        path = getPath(getActivity(), uri);
                    } catch (URISyntaxException e) {
                        e.printStackTrace();
                    }*/

                    file_path.setText(uri.toString());
                    List<Inventory> invent = null;

                    /*if (uri.toString().contains(".csv")) {
                        status = "csv";
                        invent = readCsvFile(getActivity(),uri);

                    } else {
                        status = "xls";*/
                    invent = readExcelFileFromAssetsnew(getActivity(), uri);
//                    }
                    jsonString = convertJavaObject2JsonString(invent);
                    System.out.println(jsonString);
                    Gson gson = new Gson();
                    Type collectionType = new TypeToken<Collection<Inventory>>() {
                    }.getType();
                    Collection<Inventory> enums = gson.fromJson(jsonString, collectionType);
                    Toast.makeText(getActivity(), "SuccessFully Import", Toast.LENGTH_SHORT).show();

                    myRef.setValue(enums);

                    Bundle bundle = new Bundle();
                    bundle.putString("status", status);
                    Navigation.findNavController(getActivity(), R.id.nav_host_fragment).navigate(R.id.nav_managebrand, bundle);
                }
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + requestCode);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static String getPath(Context context, Uri uri) throws URISyntaxException {
        if ("content".equalsIgnoreCase(uri.getScheme())) {
            String[] projection = {"_data"};
            Cursor cursor = null;

            try {
                cursor = context.getContentResolver().query(uri, projection, null, null, null);
                int column_index = cursor.getColumnIndexOrThrow("_data");
                if (cursor.moveToFirst()) {
                    return cursor.getString(column_index);
                }
            } catch (Exception e) {
                // Eat it
            }
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }
        return null;
    }

    private static List<Inventory> readCsvFile(Context context, Uri uri) {
        BufferedReader fileReader = null;
        CSVParser csvParser = null;
        List<Inventory> customers = new ArrayList<Inventory>();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                fileReader = new BufferedReader(new InputStreamReader(context.getContentResolver().openInputStream(Uri.parse(uri.toString())), "Cp1252"));
                csvParser = new CSVParser(fileReader,
                        CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());

                Iterable<CSVRecord> csvRecords = csvParser.getRecords();

                for (CSVRecord csvRecord : csvRecords) {
                    /*Inventory inventory = new Inventory(
                            csvRecord.get("artikel"),
                            csvRecord.get("dodatek"),
                            csvRecord.get("polnih"),
                            csvRecord.get("odprtih"),
                            csvRecord.get("teza"),
                            csvRecord.get("dodatkov"),
                            csvRecord.get("dodatna"),
                            csvRecord.get("knjizno"),
                            csvRecord.get("popisano"),
                            csvRecord.get("enota"),
                            csvRecord.get("nabavna")
                    );*/

                    Inventory inventory = new Inventory();
                    inventory.setArtikel(csvRecord.get("Artikel"));
                    inventory.setDodatek(csvRecord.get("Dodatek"));
                    inventory.setPolnih(csvRecord.get("Polnih (kos)"));
                    inventory.setOdprtih(csvRecord.get("Odprtih"));
                    inventory.setTeza(csvRecord.get("Teža"));
                    inventory.setDodatkov(csvRecord.get("Dodatkov"));
                    inventory.setDodatna(csvRecord.get("Dodatna kol. (lit/kg)"));
                    inventory.setKnjizno(csvRecord.get("Knjižno stanje pred inventuro"));
                    inventory.setPopisano(csvRecord.get("Popisano skupaj"));
                    inventory.setEnota(csvRecord.get("Enota"));
                    inventory.setNabavna(csvRecord.get("Nabavna cena"));

                    customers.add(inventory);
                }
            }
        } catch (Exception e) {
            System.out.println("Reading CSV Error!");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
                csvParser.close();
            } catch (IOException e) {
                System.out.println("Closing fileReader/csvParser Error!");
                e.printStackTrace();
            }
        }

        return customers;
    }

    public List<Inventory> readExcelFileFromAssetsnew(Context context, Uri uri) {
        List<Inventory> customers = new ArrayList<Inventory>();
        try {
//            File file = new File(path);
            // Create a POI File System object
            POIFSFileSystem myFileSystem = new POIFSFileSystem(context.getContentResolver().openInputStream(Uri.parse(uri.toString())));
            // Create a workbook using the File System
            HSSFWorkbook workbook = new HSSFWorkbook(myFileSystem);
            // Get the first sheet from workbook
            workbook.setMissingCellPolicy(Row.MissingCellPolicy.RETURN_BLANK_AS_NULL);
            DataFormatter fmt = new DataFormatter();

            for (int sn = 0; sn < workbook.getNumberOfSheets(); sn++) {
                Sheet sheet = workbook.getSheetAt(sn);
                for (int rn = sheet.getFirstRowNum(); rn <= sheet.getLastRowNum(); rn++) {
                    Row row = sheet.getRow(rn);
                    Inventory inventory = new Inventory();
                    if (row == null) {
                        // There is no data in this row, handle as needed
                    } else {
                        // Row "rn" has data
                        for (int colno = 0; colno < row.getLastCellNum(); colno++) {
                            Cell myCell = row.getCell(colno);
                            if (myCell == null) {
                                // This cell is empty/blank/un-used, handle as needed
                            } else {
                                String cellStr = fmt.formatCellValue(myCell);
                                if (colno == 0) {
                                    inventory.setArtikel(cellStr);
                                } else if (colno == 1) {
                                    inventory.setDodatek(cellStr);
                                } else if (colno == 2) {
                                    inventory.setPolnih(cellStr);
                                } else if (colno == 3) {
                                    inventory.setOdprtih(cellStr);
                                } else if (colno == 4) {
                                    inventory.setTeza(cellStr);
                                } else if (colno == 5) {
                                    inventory.setDodatkov(cellStr);
                                } else if (colno == 6) {
                                    inventory.setDodatna(cellStr);
                                } else if (colno == 7) {
                                    inventory.setKnjizno(cellStr);
                                } else if (colno == 8) {
                                    inventory.setPopisano(cellStr);
                                } else if (colno == 9) {
                                    inventory.setEnota(cellStr);
                                } else if (colno == 10) {
                                    inventory.setNabavna(cellStr);
                                }
                            }
                        }
                    }
                    customers.add(inventory);
                }
            }
        } catch (Exception e) {
            Log.e(TAG, "error " + e.toString());
        }
        return customers;
    }

    private static String convertJavaObject2JsonString(List<Inventory> customers) {
        ObjectMapper mapper = new ObjectMapper();
        String jsonString = "";

        try {
            jsonString = mapper.writeValueAsString(customers);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

        return jsonString;
    }


 /*   public String getPathFile(Uri uri)
    {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;
        int column_index =                         cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String s=cursor.getString(column_index);
        cursor.close();
        return s;
    }*/
}