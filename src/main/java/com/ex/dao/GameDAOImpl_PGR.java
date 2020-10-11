package com.ex.dao;

import com.ex.model.Schedule;
import com.ex.service.ConnectionService;
import com.ex.service.PostgreSQLConnection;

import java.sql.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GameDAOImpl_PGR implements GameDAO {
    private ConnectionService connectionSvc;

    public GameDAOImpl_PGR() {
        connectionSvc = new PostgreSQLConnection();
    }

    @Override
    public List<Schedule> getScheduleOnDay(LocalDate day) {
        Connection con = null;
        PreparedStatement stmt = null;
        List<Schedule> schedules = new ArrayList<>();

        try {
            //initialize connection & prepare statement
            con = connectionSvc.getConnection();
            if (con != null) {
                String sql = "SELECT * FROM public.schedule WHERE gameday::date = ?";
                stmt = con.prepareStatement(sql);
//                Date date = Date.from(day.atStartOfDay(ZoneId.systemDefault()).toInstant());
                stmt.setDate(1, java.sql.Date.valueOf(day));
//                System.out.println(stmt);

                ResultSet rs = stmt.executeQuery();
                while(rs.next()) {
                    Schedule tmp = new Schedule(rs.getInt("id"), rs.getTimestamp("gameday").toLocalDateTime(), rs.getString("teamone"),
                            rs.getString("teamtwo"), rs.getInt("scoreteamone"), rs.getInt("scoreteamtwo"), rs.getString("forfeit"));
                    schedules.add(tmp);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        } finally {
            return schedules;
        }
    }

    @Override
    public List<Schedule> getAllSchedules() {
        return null;
    }
}
