package model.entities;
import java.io.Serializable;
import java.util.*;
public class Department implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String name;

    public Department() {
    }

    public Department(Integer id, String name) {
        this.id = id;
        this.name = name;
    }

    // getter e setter
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /// hashCode e equals

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Department)) return false;
        Department that = (Department) o;
        return this.getId().equals(that.getId()) && this.getName().equals(that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName());// gera um número inteiro para cada objeto
    }

    // toString
    @Override
    public String toString() {
        return "Department{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    // Serializable (objetos transformados em conjunto de bytes para trafegar em rede, salvar em arquivos, etc.)
    // serialVersionUID é um número de versão universal para uma classe Serializable
}
