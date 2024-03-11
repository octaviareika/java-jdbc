package application;
import model.entities.*;
import modelDao.DaoFactory;
import modelDao.SellerDao;
import java.util.List;
import java.util.*;
public class Program {
    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in); 
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

        System.out.println("Inserir vendedor");
        Seller novoVendedor = new Seller(1, "Greg", "greg@gmail.com", new java.util.Date(), 4000.0, new Department(2, null));
        sellerdao.insert(novoVendedor);
        System.out.println("Vendedor inserido! Novo id: " + novoVendedor.getId());


        System.out.println("Atualizar vendedor");
        seller = sellerdao.findById(1);
        seller.setName("Anna Maria"); 
        sellerdao.update(seller); // atualiza o vendedor
        System.out.println("Atualização completa!");

        System.out.println("Remover vendedor");
        System.out.println("Digite o id do vendedor que deseja remover: ");
        int id = entrada.nextInt();
        sellerdao.deleteById(id);

        
        System.out.println("Vendedor removido!");


        entrada.close();
    }
}
