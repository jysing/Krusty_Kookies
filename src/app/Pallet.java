package src.app;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Created by Henri_000 on 2017-03-30.
 */
public class Pallet{
    public final int pallet_id;
    public final String cookie_name;
    public final int order_id;
    public final String production_date;
    public final String location;
    public final int is_blocked;

    public Pallet (ResultSet rs) throws SQLException {
        this.pallet_id = rs.getInt("pallet_id");
        this.cookie_name = rs.getString("cookie_name");
        this.order_id = rs.getInt("order_id");
        this.production_date = rs.getString("production_date");
        this.location = rs.getString("location");
        this.is_blocked = rs.getInt("is_blocked");
    }

}