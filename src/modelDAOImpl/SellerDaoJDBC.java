package modelDAOImpl;

import modelDao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.List;

import db.DB;
import db.DbException;

import java.sql.*;

public class SellerDaoJDBC implements SellerDao{ // sSellerDaoJDBC classe de implementaçãO (interface)
    Connection conn;

    public SellerDaoJDBC(Connection conn){
        this.conn = conn;
    }
    @Override
    public void insert(Seller obj){
        // TODO Auto-generated method stub
    }

    @Override
    public void update(Seller obj){
        // TODO Auto-generated method stub
    }

    @Override
    public void deleteById(Integer id){
        // TODO Auto-generated method stub
    }

    @Override
    public Seller findById(Integer id){
        PreparedStatement st  = null;
        ResultSet rs = null;
        try {
            st = conn.prepareStatement(
                "SELECT seller.*,department.Name as DepName "
                + "FROM seller INNER JOIN department " 
                + " ON seller.DepartmentId = department.Id"
                + "WHERE seller.Id = ?*"
                    );

                st.setInt(1, id);
                rs = st.executeQuery(); // traz os resultados em formato de tabela

                if (rs.next()){ // se houver resultado, se resultado nao for nulo

                    Department dep = new Department();
                    dep.setId(rs.getInt("DepartmentId"));
                    dep.setName(rs.getString("DepName"));
                    Seller obj = new Seller();
                    obj.setId(rs.getInt("Id"));
                    obj.setName(rs.getString("Name"));
                    obj.setEmail(rs.getString("Email"));
                    obj.setBaseSalary(rs.getDouble("BaseSalary"));
                    obj.setBirthDate(rs.getDate("BirthDate"));
                    obj.setDepartment(dep); // o departamento montado
                    return obj;
                }
            } catch (SQLException e){
                    throw new DbException(e.getMessage());
            } finally {
                    DB.closeStatement(st);
                    DB.closeResultSet(rs);
            }

            return null;
        }

    @Override
    public List<Seller> findAll(){
        // TODO Auto-generated method stub
        return null;
    }
}