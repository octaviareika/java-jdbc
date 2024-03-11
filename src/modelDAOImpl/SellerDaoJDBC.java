package modelDAOImpl;

import modelDao.SellerDao;
import model.entities.Department;
import model.entities.Seller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



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
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("INSERT INTO seller " + 
            "(Name, Email, BirthDate, BaseSalary, DepartmentId) " +  
            "VALUES " + 
            "(?, ?, ?, ?, ?)"  
            , Statement.RETURN_GENERATED_KEYS);

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime())); // converte a data do java.util para java.sql
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());

            int linhasAfetadas = st.getUpdateCount(); // retorna o numero de linhas afetadas


            if (linhasAfetadas > 0) {
                ResultSet result = st.getGeneratedKeys(); // retorna o id gerado
                System.out.println("Feito! Linhas afetadas: " + linhasAfetadas);
                if (result.next()){
                    int id = result.getInt(1);
                    obj.setId(id);
                }
            }

            else {
                System.out.println("Nenhuma linha afetada!");
            }

        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    // metodo que atualiza o vendedor
    public void update(Seller obj){
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("UPDATE seller " + //
                                "SET Name = ?, Email = ?, BirthDate = ?, BaseSalary = ?, DepartmentId = ? " + //
                                "WHERE Id = ? "
                                );

            st.setString(1, obj.getName());
            st.setString(2, obj.getEmail());
            st.setDate(3, new java.sql.Date(obj.getBirthDate().getTime())); // converte a data do java.util para java.sql
            st.setDouble(4, obj.getBaseSalary());
            st.setInt(5, obj.getDepartment().getId());
            st.setInt(6, obj.getId());

            st.executeUpdate();


        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
    }

    @Override
    public void deleteById(Integer id){
        PreparedStatement st = null;
        try {
            st = conn.prepareStatement("DELETE FROM seller " + 
            "WHERE Id = ?"  
            );

           st.setInt(1, id);

           int rows = st.executeUpdate();
            if (rows == 0){
                throw new DbException("Id inexistente!");
            }


        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
        }
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

                    Department dep = instanciarDepartment(rs); // instanciar o departamento
                    Seller obj = instanciarSeller(rs, dep); 
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

    private Seller instanciarSeller(ResultSet rs, Department dep) throws SQLException {
        Seller sell =  new Seller();
        sell.setId(rs.getInt("Id"));
        sell.setName(rs.getString("Name"));
        sell.setEmail(rs.getString("Email"));
        sell.setBaseSalary(rs.getDouble("BaseSalary"));
        sell.setBirthDate(rs.getDate("BirthDate"));
        sell.setDepartment(dep); // o departamento montado
        return sell;
    }
    private Department instanciarDepartment(ResultSet rs) throws SQLException{ // metodo auxiliar
        
        Department dep = new Department();
        dep.setId(rs.getInt("DepartmentId"));
        dep.setName(rs.getString("DepName"));
        return dep;
    }
    @Override
    public List<Seller> findAll(){ // metodo que retorna todos os vendedores
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                "SELECT seller.*, departament.Name as DepName " + 
                "FROM seller INNER JOIN department " +
                "ON seller.DepartmentId = department.Id " + 
                "ORDER BY Name"
                    );


            rs = st.executeQuery(); // traz os resultados em formato de tabela
            
            List <Seller> lista = new ArrayList<>();
            Map <Integer, Department> map = new HashMap<>(); // map começa vazio

            while (rs.next()){
                Department dep = map.get(rs.getInt("DepartmentId")); // retorna o departamento do map por meio do id

                if (dep == null){ // se o departamento nao existir, evita reptição
                    dep = instanciarDepartment(rs); // instanciar o departamento
                    map.put(rs.getInt("DepartmentId"), dep); // adicionar o departamento no map
                }

                Seller obj = instanciarSeller(rs, dep);
                lista.add(obj);

            }

            return lista;
        } catch (SQLException e){
            throw new DbException(e.getMessage());
        } finally {
            DB.closeStatement(st);
            DB.closeResultSet(rs);
        }
        
    
    }

    @Override
    public List<Seller> findByDepartmente(Department department) {
        PreparedStatement st = null;
        ResultSet rs = null;

        try {
            st = conn.prepareStatement(
                "SELECT seller.*,department.Name as DepName " + 
                "FROM seller INNER JOIN department " + 
                "ON seller.DepartmentId = department.Id " + 
                "WHERE DepartmentId = ?" + 
                "ORDER BY Name"
                    );

                st.setInt(1, department.getId());
                rs = st.executeQuery(); // traz os resultados em formato de tabela

                List <Seller> lista = new ArrayList<>();
                Map<Integer, Department> map = new HashMap<>(); // map começa vazio

                while (rs.next()){ // se houver resultado, se resultado nao for nulo
                    Department dep = map.get(rs.getInt("DepartmentId")); // retorna o departamento do map por meio do id

                    if (dep == null){ // se o departamento nao existir, evita reptição
                        dep = instanciarDepartment(rs); // instanciar o departamento
                        map.put(rs.getInt("DepartmentId"), dep); // adicionar o departamento no map
                    }
                  
                    Seller obj = instanciarSeller(rs, dep);
                    lista.add(obj);
                }
                return lista;
            } catch (SQLException e){
                    throw new DbException(e.getMessage());
            } finally {
                    DB.closeStatement(st);
                    DB.closeResultSet(rs);
            }
        
    }
}