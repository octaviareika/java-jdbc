package modelDao;

import db.DB;
import modelDAOImpl.SellerDaoJDBC;

public class DaoFactory { // classe que instancia a implementação da interface SellerDao

    public static SellerDao createSellerDao(){ // retorna o tipo SellerDaoJDBC
        return new SellerDaoJDBC(DB.getConnection()); // retorna a implementação

    }
    
}
