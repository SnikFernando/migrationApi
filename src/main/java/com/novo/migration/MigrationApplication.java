package com.novo.migration;

import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SpringBootApplication
public class MigrationApplication {

	public static void main(String[] args) {
		// C:\\TEMP\\external_risk_evaluator_2.db 2 issuer C:\TEMP\issuer_insert.sql
		if(args.length==4){
			String query = getDataSQLite(args[0],Integer.parseInt(args[1]),args[2]);
			System.out.println(query);
			generateSQLFile(query,args[3]);
		}else {
			throw new IllegalArgumentException("arguments are less than 4");
		}

	}
	public static void generateSQLFile(String query, String rutaArchivoSQL ){
		try {
			File archivoSQL = new File(rutaArchivoSQL);
			FileWriter writer = new FileWriter(archivoSQL);
			writer.write(query);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String getDataSQLite(String dbFilePath, int columnInitialNumber , String tableName){
		try {
			Class.forName("org.sqlite.JDBC");
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbFilePath);
			Statement statement = connection.createStatement();
			ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);
			StringBuilder query = new StringBuilder();
			// Iterar a través de los resultados y generar consultas SQL
			while (resultSet.next()) {
				StringBuilder queryBuilder = new StringBuilder("INSERT INTO " + "["+ tableName + "] (");
				StringBuilder values = new StringBuilder("VALUES (");
				int column = resultSet.getMetaData().getColumnCount();
				for (int i = columnInitialNumber; i <= column; i++) {
					String columnName = resultSet.getMetaData().getColumnName(i);
					queryBuilder.append(columnName);
					Object obj = resultSet.getObject(columnName);
					if(obj instanceof String){
						String response = validateIsDate(obj.toString());
						values.append(response);
					}else{
						values.append(obj);
					}
					if (i<column){
						queryBuilder.append(",");
						values.append(",");
					}else {
						queryBuilder.append(")");
						values.append(")");
					}
				}
				query.append(queryBuilder).append(" ").append(values).append(";\n");
			}
			resultSet.close();
			statement.close();
			connection.close();
			return query.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	public static String validateIsDate(String date) throws ParseException {
		Pattern pattern = Pattern.compile("\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}");
		Pattern pattern2 = Pattern.compile("\\d{4}-\\d{2}-\\d{2}-\\d{2}:\\d{2}:\\d{2}");
		String response =date;
		Matcher matcher = pattern.matcher(date);
		if (matcher.find()) {
			String fechaEncontrada = matcher.group();
			response =convertFormatDate(fechaEncontrada,"yyyy-MM-dd HH:mm:ss","yyyy-MM-dd HH:mm:ss.SSS");
		}
		matcher =pattern2.matcher(date);
		if(matcher.find()) {
			String fechaEncontrada = matcher.group();
			response =convertFormatDate(fechaEncontrada,"yyyy-MM-dd-HH:mm:ss","yyyy-MM-dd HH:mm:ss.SSS");
		}
		response = "'" + response +"'";
		if(date.equals("CURRENT_TIMESTAMP")){
			response ="FORMAT(SYSDATETIME(), 'yyyy-MM-dd HH:mm:ss.fff')";
		}
		return response;

	}
	public static String convertFormatDate(String dateInput, String formatInput, String formatOutput) throws ParseException {
		SimpleDateFormat formatoOriginal = new SimpleDateFormat(formatInput);
		Date date = formatoOriginal.parse(dateInput);
		SimpleDateFormat formatoSalida = new SimpleDateFormat(formatOutput);
		return formatoSalida.format(date);
	}


}
