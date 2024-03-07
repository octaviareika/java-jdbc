package modelDao;

import modelDAOImpl.SellerDaoJDBC;

public class DaoFactory { // classe que instancia a implementação da interface SellerDao

    public static SellerDao createSellerDao(){ // retorna o tipo SellerDaoJDBC
        return new SellerDaoJDBC(); // retorna a implementação

    }
    
}
