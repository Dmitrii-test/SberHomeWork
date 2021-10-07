package ru.dmitrii.solid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class SqlHandler {
    private Connection connection;

    /**
     * prepare statement with sql
     * @param departmentId String
     * @param dateFrom LocalDate
     * @param dateTo LocalDate
     * @return PreparedStatement
     * @throws SQLException SQLException
     */
    public PreparedStatement getPreparedStatement(String departmentId, LocalDate dateFrom, LocalDate dateTo) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("select emp.id as emp_id, emp.name as amp_name, sum(salary) as salary from employee emp left join" +
                "salary_payments sp on emp.id = sp.employee_id where emp.department_id = ? and" +
                " sp.date >= ? and sp.date <= ? group by emp.id, emp.name");
        // inject parameters to sql
        ps.setString(0, departmentId);
        ps.setDate(1, new java.sql.Date(dateFrom.toEpochDay()));
        ps.setDate(2, new java.sql.Date(dateTo.toEpochDay()));
        return ps;
    }

    /**
     * create a StringBuilder holding a resulting html
     * @param results ResultSet
     * @return StringBuilder
     * @throws SQLException SQLException
     */
    public StringBuilder getStringBuilder(ResultSet results) throws SQLException {
        StringBuilder resultingHtml = new StringBuilder();
        resultingHtml.append("<html><body><table><tr><td>Employee</td><td>Salary</td></tr>");
        double totals = 0;
        while (results.next()) {
            totals = getTotals(results, resultingHtml, totals);
        }
        resultingHtml.append("<tr><td>Total</td><td>").append(totals).append("</td></tr>");
        resultingHtml.append("</table></body></html>");
        return resultingHtml;
    }


    /**
     * process each row of query results
     * @param results ResultSet
     * @param resultingHtml StringBuilder
     * @param totals double
     * @return double
     * @throws SQLException SQLException
     */
    private double getTotals(ResultSet results, StringBuilder resultingHtml, double totals) throws SQLException {
        resultingHtml.append("<tr>"); // add row start tag
        resultingHtml.append("<td>").append(results.getString("emp_name")).append("</td>"); // appending employee name
        resultingHtml.append("<td>").append(results.getDouble("salary")).append("</td>"); // appending employee salary for period
        resultingHtml.append("</tr>"); // add row end tag
        totals += results.getDouble("salary"); // add salary to totals
        return totals;
    }
}
