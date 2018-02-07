package overload.drmed.overload.drmed.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Environment;

import com.microsoft.azure.storage.*;
import com.microsoft.azure.storage.file.*;

import java.io.*;
import java.io.FileOutputStream;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import overload.drmed.CadastroExameActivity;
import overload.drmed.ExamesActivity;
import overload.drmed.PdfActivity;

/**
 * @author Dalbo
 */
public class ConexaoAzure {

    // Configure the connection-string with your values
    private final String storageConnectionString
            = "DefaultEndpointsProtocol=http;"
            + "AccountName=drmed;"
            + "AccountKey=NKiQB+hcFrwb8d9mAGgjG07pwIpI9fqP9Vlz36aE1JWk9WKJap1VUuybV/uQV+mVPYt6OxFeiwzipLWxdRT0Xg==";

    CloudStorageAccount storageAccount;

    // Create the file storage client.
    CloudFileClient fileClient;
    // Get a reference to the file share
    CloudFileShare share;

    Context mContext;

    public boolean ConectarAzure() throws URISyntaxException {
        // Use the CloudStorageAccount object to connect to your storage account
        try {
            storageAccount = CloudStorageAccount.parse(storageConnectionString);

            fileClient = storageAccount.createCloudFileClient();
            share = fileClient.getShareReference("exames");
        } catch (InvalidKeyException invalidKey) {
            return false;
        } catch (StorageException ex) {
            Logger.getLogger(ConexaoAzure.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    public void downloadAzure(String nomeexame) throws URISyntaxException, StorageException, IOException {

        System.out.println("1");
       // File path = new File(Environment.getExternalStorageDirectory() + "/Exames");
        File path = new File(mContext.getFilesDir() + "/Exames");
        System.out.println("2");

        CloudFileDirectory rootDir = share.getRootDirectoryReference();
        CloudFile file = rootDir.getFileReference(nomeexame);

        System.out.println("3");
        OutputStream out = new BufferedOutputStream(new java.io.FileOutputStream(path.getAbsolutePath() + "/" + nomeexame));
        file.download(out);

        System.out.println("4");
        Dialog.dismissOfTheProgressBar();
        Intent intent = new Intent(mContext, PdfActivity.class);
        intent.putExtra("arquivo", path.getAbsolutePath() + "/" + nomeexame);
        mContext.startActivity(intent);


    }

    public void checkArquivo(Context context, String nomeexame) {


        //File path = new File(Environment.getExternalStorageDirectory() + "/Exames");
        File path = new File(context.getFilesDir() + "/Exames");

        mContext = context;
        boolean isPresent = true;
        if (!path.exists()) {
            isPresent = path.mkdir();
            System.out.println("Criou pasta");
        }
        if (isPresent) {
            isPresent = false;
            File[] lista = path.listFiles();
            System.out.println("Listando arquivos");
            for (int a = 0; a < lista.length; a++) {
                try {
                    Thread.sleep(0);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Arquivo: " + lista[a].getName());
                if (lista[a].getName().equals(nomeexame)) {
                    abrirLocal(path, nomeexame);
                    isPresent = true;
                    break;
                }
            }
            if (!isPresent) {
                try {
                    System.out.println("Vai baixar");
                    downloadAzure(nomeexame);
                    System.out.println("Baixou");
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                } catch (StorageException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public void abrirLocal(File path, String nomeexame) {
        Dialog.dismissOfTheProgressBar();

        System.out.println("Abrindo o arquivo local.");
        Intent intent = new Intent(mContext, PdfActivity.class);
        intent.putExtra("arquivo", path.getAbsolutePath() + "/" + nomeexame);

        mContext.startActivity(intent);
    }

}
