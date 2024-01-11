package dz080124.proxy;

public class ServiceImpl implements Service{
    @Override
    public String doJob() {
        System.out.println("Выполняется работа...");
        return "Успех!";
    }
}
