package application;
import model.entities.*;
import modelDao.DaoFactory;
import modelDao.SellerDao;
import java.util.List;
public class Program {
    public static void main(String[] args) {
        SellerDao sellerdao = DaoFactory.createSellerDao(); // instanciação da interface SellerDao por meio da classe DaoFactory

        System.out.println("Procurar por Id");
        Seller seller = sellerdao.findById(3);
        System.out.println(seller);

        System.out.println("Procurar por departamento");
        Department dep = new Department(2, null);
        List <Seller> sellers = sellerdao.findByDepartmente(dep);
        for (Seller sell : sellers){
            System.out.println(sell);
        }

        System.out.println("Procurar todos os vendedores");
        sellers = sellerdao.findAll();
        for (Seller sell : sellers){
            System.out.println(sell);
        }
    }
}
