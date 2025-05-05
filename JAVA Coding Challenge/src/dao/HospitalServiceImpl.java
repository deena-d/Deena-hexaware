package dao;
import entity.Appointment;
import exception.PatientNumberNotFoundException;
import util.DBConnUtil;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
public class HospitalServiceImpl implements IHospitalService {

    @Override
    public Appointment getAppointmentById(int appointmentId) {
        Appointment appointment = null;
        String query = "SELECT * FROM appointment WHERE appointment_id = ?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, appointmentId);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    appointment = new Appointment(
                            rs.getInt("appointment_id"),
                            rs.getInt("patient_id"),
                            rs.getInt("doctor_id"),
                            rs.getString("appointment_date"),
                            rs.getString("description")
                    );
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointment;
    }

    @Override
    public List<Appointment> getAppointmentsForPatient(int patientId) {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointment WHERE patient_id = ?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, patientId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    appointments.add(new Appointment(
                            rs.getInt("appointment_id"),
                            rs.getInt("patient_id"),
                            rs.getInt("doctor_id"),
                            rs.getString("appointment_date"),
                            rs.getString("description")
                    ));
                }
            }

            if (appointments.isEmpty()) {
                throw new PatientNumberNotFoundException("No appointments found for patient ID: " + patientId);
            }

        } catch (SQLException | PatientNumberNotFoundException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    @Override
    public List<Appointment> getAppointmentsForDoctor(int doctorId) {
        List<Appointment> appointments = new ArrayList<>();
        String query = "SELECT * FROM appointment WHERE doctor_id = ?";
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, doctorId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    appointments.add(new Appointment(
                            rs.getInt("appointment_id"),
                            rs.getInt("patient_id"),
                            rs.getInt("doctor_id"),
                            rs.getString("appointment_date"),
                            rs.getString("description")
                    ));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return appointments;
    }

    @Override
    public boolean scheduleAppointment(Appointment appointment) {
        String query = "INSERT INTO appointment (patient_id, doctor_id, appointment_date, description) VALUES (?, ?, ?, ?)";
        boolean success = false;
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, appointment.getPatientId());
            stmt.setInt(2, appointment.getDoctorId());
            stmt.setString(3, appointment.getAppointmentDate());
            stmt.setString(4, appointment.getDescription());

            int rows = stmt.executeUpdate();
            success = rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public boolean updateAppointment(Appointment appointment) {
        String query = "UPDATE appointment SET patient_id = ?, doctor_id = ?, appointment_date = ?, description = ? WHERE appointment_id = ?";
        boolean success = false;

        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, appointment.getPatientId());
            stmt.setInt(2, appointment.getDoctorId());
            stmt.setString(3, appointment.getAppointmentDate());
            stmt.setString(4, appointment.getDescription());
            stmt.setInt(5, appointment.getAppointmentId());

            int rows = stmt.executeUpdate();
            success = rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }

    @Override
    public boolean cancelAppointment(int appointmentId) {
        String query = "DELETE FROM appointment WHERE appointment_id = ?";
        boolean success = false;
        try (Connection conn = DBConnUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, appointmentId);
            int rows = stmt.executeUpdate();
            success = rows > 0;

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return success;
    }}
