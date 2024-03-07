package application;
import model.entities.*;
import modelDao.DaoFactory;
import modelDao.SellerDao;
public class Program {
    public static void main(String[] args) {
        Department departamento = new Department(1, "Books");
        System.out.println(departamento);

        SellerDao sellerDao = DaoFactory.createSellerDao(); // instanciação do objeto por meio do metodo estatico da classe DaoFactory
        // ou seja ta isntanciando o objeto SellerDao por meio da interface, sem saber a implementação
    }
}
