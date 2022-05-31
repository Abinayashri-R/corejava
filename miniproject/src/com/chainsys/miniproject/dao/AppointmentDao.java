package com.chainsys.miniproject.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.chainsys.miniproject.pojo.Appointment;
import com.chainsys.miniproject.pojo.Doctor;

public class AppointmentDao {

	public static Connection getConnection() {
		Connection mycon = null;

		String driver = "oracle.jdbc.driver.OracleDriver";
		String url = "jdbc:oracle:thin:@localhost:1521:xe";
		String username = "system";
		String password = "Chikabi@798999";

		try {
			Class.forName(driver);
			mycon=DriverManager.getConnection(url,username,password);
			} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			   return mycon;
			}

			private static java.sql.Date convertToSql(java.util.Date date){
			java.sql.Date sqlDate= new java.sql.Date(date.getTime());
			return sqlDate ;

			}

			public static int insertAppointment(Appointment newdoc) {

			Connection mycon= getConnection();
			PreparedStatement rs= null;
			String insertquery="insert into APPOINTMENT(APP_ID,APP_DATE,DOCTOR_ID,PATIENT_NAME,FEES_COLLECTED) values (?,?,?,?,?)";
			int rows=0;
			try {
			mycon=getConnection();
			rs=mycon.prepareStatement(insertquery);
			rs.setInt(1, newdoc.getApp_id());
			rs.setDate(2, convertToSql(newdoc.getApp_date()));
			rs.setInt(3, newdoc.getDoctor_id());
			rs.setString(4, newdoc.getPatient_name());
			rs.setFloat(5, newdoc.getFees_collected());
			rows=rs.executeUpdate();
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}finally {
			try {
			rs.close();
			} catch (SQLException e) {

			e.printStackTrace();
			}
			try {
			mycon.close();
			} catch (SQLException e) {
			e.printStackTrace();
			}
			}

			return rows;
			}

			public static int updateAppointment(Appointment newdoc) {
			Connection con=null;
			String updatequery="update Appointment set APP_DATE,DOCTOR_ID=?,PATIENT_NAME=?,FEES_COLLECTED=? where APP_ID=?";
			    int rows =0;
			    PreparedStatement st=null;
			try {
			con=getConnection();
			st =con.prepareStatement(updatequery);

			st.setDate(1, convertToSql(newdoc.getApp_date()));
			st.setInt(5, newdoc.getApp_id());
			st.setInt(2, newdoc.getDoctor_id());
			st.setString(4, newdoc.getPatient_name());
			st.setFloat(5, newdoc.getFees_collected());
			rows=st.executeUpdate();
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}finally {
			try {
			st.close();
			con.close();
			} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			}
			}
			return rows;

			}

			public static  int updateDoctorName(String name,int id) {
			  String updatequery = "update APPOINTMNT set PATINT_NAME=? where APP_ID=?";
			  Connection con = null;
			  int rows = 0;
			  PreparedStatement ps = null;
			  try {
			  con = getConnection();
			  ps = con.prepareStatement(updatequery);
			 
			  ps.setInt(1, id);
			  ps.setString(2, name);
			  ps.executeUpdate();
			  rows = ps.executeUpdate();
			  } catch (SQLException e) {
			  e.printStackTrace();
			  }finally {
			  try {
			  ps.close();
			  } catch (SQLException e) {
			  e.printStackTrace();
			  }
			  try {
			  con.close();
			  } catch (SQLException e) {
			  e.printStackTrace();
			  }
			  }

			  return rows;
			  }

			public static int deleteAppointment(int id) {
			String deletequery = "delete from APPOINTMENT where APP_ID=?";
			Connection con = null;
			int rows = 0;
			PreparedStatement ps = null;

			try {
			con = getConnection();
			ps = con.prepareStatement(deletequery);
			ps.setInt(1, id);

			rows = ps.executeUpdate();
			}catch(SQLException e) {
			e.printStackTrace();
			}finally {
			try {
			ps.close();
			} catch (SQLException e) {
			e.printStackTrace();
			}
			try {
			con.close();
			} catch (SQLException e) {
			e.printStackTrace();
			}
			}
			return rows;
			}
			public static Appointment  getAppointmentById(int id) {
			Appointment  doc =null;
			String selectquery = " select APP_ID,APP_DATE,DOCTOR_ID,PATIENT_NAME,FEES_COLLECTED FROM APPOINTMENT where APP_ID= ? ";
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs  = null;
			try {
			con = getConnection();
			ps = con.prepareStatement(selectquery);
			ps.setInt(1, id);
			rs =ps.executeQuery();
			doc= new Appointment();
			  if(rs.next()) {
			         doc.setApp_id(rs.getInt(1));
			         java.util.Date date= new java.util.Date(rs.getDate(3).getTime());
			         doc.setApp_date(rs.getDate(2));
			         doc.setDoctor_id(rs.getInt(3));
			         doc.setPatient_name(rs.getString(4));
			         doc.setFees_collected(rs.getInt(5));
			       }
			}catch(SQLException e) {
			e.printStackTrace();
			}finally {
			try {
			rs.close();
			} catch (SQLException e1) {
			e1.printStackTrace();
			}
			try {
			ps.close();
			} catch (SQLException e) {
			e.printStackTrace();
			}
			try {
			con.close();
			} catch (SQLException e) {
			e.printStackTrace();
			}
			}
			return doc;

			}

			public static List<Appointment>  getAllAppointments() {
			Appointment  doc =null;
			String selectquery = " select APP_ID,APP_DATE,DOCTOR_ID,PATIENT_NAME,FEES_COLLECTED FROM APPOINTMENT from APPOINTMENT";
			Connection con = null;
			PreparedStatement ps = null;
			ResultSet rs  = null;
			List<Appointment> allappointments=new ArrayList<Appointment>();
			try {
			con = getConnection();
			ps = con.prepareStatement(selectquery);

			rs =ps.executeQuery();
			  while(rs.next()) {
			  doc=new Appointment();
			          doc.setApp_id(rs.getInt(1));
			           java.util.Date date= new java.util.Date(rs.getDate(3).getTime());
			          doc.setApp_date(rs.getDate(2));
			         doc.setDoctor_id(rs.getInt(3));
			         doc.setPatient_name(rs.getString(4));
			         doc.setFees_collected(rs.getInt(5));
			         allappointments.add(doc);
			         
			       }
			}catch(SQLException e) {
			e.printStackTrace();
			}finally {
			try {
			rs.close();
			} catch (SQLException e1) {
			e1.printStackTrace();
			}
			try {
			ps.close();
			} catch (SQLException e) {
			e.printStackTrace();
			}
			try {
			con.close();
			} catch (SQLException e) {
			e.printStackTrace();
			}
			}
			return allappointments;

			}


			}
