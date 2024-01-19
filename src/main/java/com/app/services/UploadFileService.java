package com.app.services;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class UploadFileService {
    private final String folder="/opt/images/";
    private final Path root = Paths.get("images");

    public UploadFileService() {
        if (!Files.exists(root))
            try {
                Files.createDirectory(root);
            } catch (IOException e) {
                throw new RuntimeException("¡No se pudo inicializar la carpeta para cargarla!");
            }
    }

    public String saveImage(MultipartFile file) throws IOException {
        if (!file.isEmpty()){
            byte[] bytes = file.getBytes();
            Path path = Paths.get(folder+file.getOriginalFilename());
            Files.write(path,bytes);
            return file.getOriginalFilename();
        }else {
            File fileSource = new File("/opt/default.jpg");
            File fileDest = new File("/default.jpg");
            InputStream in = new FileInputStream(fileSource);
            Path path = Paths.get(folder+fileDest);
            Files.write(path,in.readAllBytes());
        }

        return "default.jpg";

    }

    public void deleteImage(String nombre){
        if(!nombre.equals("default.jpg")){
            File file = new File(folder+nombre);
            file.delete();
        }
    }
}
