import org.postgresql.Driver;
import org.postgresql.ds.PGSimpleDataSource;

import javax.sql.DataSource;
import java.sql.SQLException;

public class Database
{
    private static Database INSTANCE;

    private DataSource ds;

    private Database() throws SQLException
    {
        if (!Driver.isRegistered())
        {
            Driver.register();
        }
        final PGSimpleDataSource ds = new PGSimpleDataSource();
        ds.setServerName("192.168.0.100");
        ds.setDatabaseName("postgres");
        ds.setUser("demo");
        ds.setPassword("demo");
        this.ds = ds;
    }

    public static Database getINSTANCE() throws SQLException
    {
        if (Database.INSTANCE == null)
        {
            Database.INSTANCE = new Database();
        }
        return Database.INSTANCE;
    }

    public DataSource getDataSource()
    {
        return this.ds;
    }
}
