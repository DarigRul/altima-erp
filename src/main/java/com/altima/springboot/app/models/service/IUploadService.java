package com.altima.springboot.app.models.service;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;

import com.cloudinary.Cloudinary;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface IUploadService {

	public Resource load(String filename) throws MalformedURLException;
	
	public String[] copy(MultipartFile file, MultipartFile file2) throws IOException;
	
	public String copy2(MultipartFile file) throws IOException;

	public boolean deletePrenda(String filename);

	public File filePrenda(String filename) ;
	
	public boolean delete(String filename);
	
	public Resource loadTela(String filename) throws MalformedURLException;
	
	public String copyTela(MultipartFile file1) throws IOException;
	
	public boolean deleteTela(String filename);

	public File fileTela(String filename);
	
	
	public Resource loadForro(String filename) throws MalformedURLException;
	
	public String copyForro(MultipartFile file1) throws IOException;
	
	public boolean deleteForro(String filename);

	public File fileForro(String filename);

	public Resource loadfile(String filename,Integer caso) throws MalformedURLException;

	public String copyfile(MultipartFile file,Integer caso) throws IOException;

	public boolean deletefile(String filename);

	public String ruta(Integer caso);
	
	//Material
	public Resource loadMaterial(String filename) throws MalformedURLException;
	
	public String copyMaterial(MultipartFile file1) throws IOException;
	
	public boolean deleteMaterial(String filename);

	public File fileMaterial(String filename);
	 

	
	
	//Clientes
	public Resource loadCliente(String filename) throws MalformedURLException;
	
	public String copyCliente(MultipartFile file1) throws IOException;
	
	public boolean deleteCliente(String filename);

	
	
	//Inventario
			public Resource loadInventario(String filename) throws MalformedURLException;
			
			public String copyInventario(MultipartFile file1) throws IOException;
			
			public boolean deleteInventario(String filename);
	
	
	
	
			//B O R D A D O 
			public Resource loadBordado(String filename) throws MalformedURLException;
			
			public String copyBordado(MultipartFile file1) throws IOException;
			
			public boolean deleteBordado(String filename);
	
	
			// AMP INVENTARIO
	
			public Resource loadInventarioAMP(String filename) throws MalformedURLException;
			
			public String copyInventarioAMP(MultipartFile file1) throws IOException;
			
			public boolean deleteInventarioAMP(String filename);

			public Cloudinary CloudinaryApi();
}
