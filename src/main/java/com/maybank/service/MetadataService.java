package org.maybank.com.Service;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetadataService {

    @Autowired
    private DataSource dataSource;

    public Set<String> getColumnNames(String tableName) {
        Set<String> columnNames = new HashSet<>();

        try (Connection connection = dataSource.getConnection()) {
            DatabaseMetaData metaData = connection.getMetaData();
            ResultSet resultSet = metaData.getColumns(null, null, tableName, null);

            while (resultSet.next()) {
                columnNames.add(resultSet.getString("COLUMN_NAME").trim());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return columnNames;
    }
}