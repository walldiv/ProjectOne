package com.ex.dao;

import com.ex.model.Sponsor;
import com.ex.service.ConnectionService;
import com.ex.service.PostgreSQLConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

public class SponsorDAOImpl_PGR implements SponsorDAO {
    private ConnectionService connectionSvc;

    public SponsorDAOImpl_PGR() {
        connectionSvc = new PostgreSQLConnection();
    }

    /* Registers from website as a sponsor */
    public void registerSponsor(Sponsor sponsor) throws Exception {
        Connection con = null;
        PreparedStatement stmt = null;

        /* Apply some data validation on phone number types - remove the - if any exist */
        String x = sponsor.getPhone();
        x = x.replace("-", "");
        sponsor.setPhone(x);

        try {
            //initialize connection & prepare statement
            con = connectionSvc.getConnection();
            if (con != null) {
                String sql = "INSERT INTO public.sponsors (name, phone, email) VALUES (?, ?, ?)";
                stmt = con.prepareStatement(sql);
                stmt.setString(1, sponsor.getName());
                stmt.setString(2, sponsor.getPhone());
                stmt.setString(3, sponsor.getEmail());
//                System.out.println(stmt);
                if(stmt.executeUpdate()<=0) {
                    throw new Exception("ERROR - NO SPONSOR WAS ADDED");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        try {
            con.close();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
