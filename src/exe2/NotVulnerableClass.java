package exe2;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;
import java.text.Normalizer;

import org.apache.commons.io.IOUtils;

public class NotVulnerableClass {
	
	private final InputStream input;
	
	public NotVulnerableClass(){
		input = System.in;
	}
	
	public NotVulnerableClass(InputStream input){
		this.input = input;
	}
	
	public void notVulnerableMethod(String FILENAME){
		
		
		if(!FILENAME.matches("[A-Za-z0-9\\._]*")){
			String msg = "Caractere inválido presente. Nome do arquivo só pode conter letras "
					+ "maiúsculas e minúsculas, números, ponto e underline.";
			System.out.println(msg);
			throw new IllegalArgumentException(msg);
		}
		
		File file = new File(FILENAME);
		if(!file.exists() || file.isDirectory()){
			String msg = "Arquivo inexistente!";
			System.out.println(msg);
			throw new IllegalArgumentException(msg);
		}
		
		Scanner console = new Scanner(input);
		
		try{
			
			String opr = "";
			
			do {
				System.out.print("Digite a operacao desejada para realizar no arquivo <R para ler um arquivo, "
			    		+ "W para escrever em um arquivo, S para sair>? ");
			    opr= console.nextLine();
				
			    if (opr.equals("R")){
			    	readFromFile(FILENAME);
				}
				
				else if (opr.equals("W")){
					System.out.println("Escreva algo: ");
				    String novaLinha = console.nextLine();
				    if(novaLinha==null)
				    	novaLinha = "";
				    writeToFile(FILENAME, novaLinha);
				}
			    
				else if(!opr.equals("S")){
					System.out.println("Operação inválida! Por favor, escreva R, W ou S.");
				}
			    
			} while(!opr.equals("S"));
			
			System.out.println("Adeus!");
		}
		
		finally{
			console.close();
		}
	}
	
	private void readFromFile(String FILENAME){
		BufferedReader br = null;
		FileReader fr = null;
		
		try {

			fr = new FileReader(FILENAME);
			br = new BufferedReader(fr);

			String sCurrentLine;

			br = new BufferedReader(new FileReader(FILENAME));

			while ((sCurrentLine = br.readLine()) != null) {
				String linha = Normalizer.normalize(sCurrentLine, Normalizer.Form.NFKC);
				System.out.println(linha);
			}

		} catch (IOException e) {

			String msg = "Ocorreu um problema na leitura do arquivo!";
			System.out.println(msg);
			throw new IllegalArgumentException(msg);
		} 
		
		finally{
			IOUtils.closeQuietly(br);
			IOUtils.closeQuietly(fr);
		}
	}
	
	private void writeToFile(String FILENAME, String newLine){
		BufferedWriter buffWrite = null;
		  FileWriter fileWriter = null;
		  
		  try {
			fileWriter = new FileWriter(FILENAME, true);
			buffWrite = new BufferedWriter(fileWriter);
		    
			String linha = Normalizer.normalize(newLine, Normalizer.Form.NFKC);
		    buffWrite.append(linha + "\n");
		     
		} catch (IOException e) {
			String msg = "Ocorreu um problema na escrita do arquivo!";
			System.out.println(msg);
			throw new IllegalArgumentException(msg);
		} 
		  finally{
			IOUtils.closeQuietly(buffWrite);
			IOUtils.closeQuietly(fileWriter);
		  }
	}
}
