public class Main {

    public static void main(String[] args) {

        ConfigurationManager config1 = ConfigurationManager.getInstance();
        ConfigurationManager config2 = ConfigurationManager.getInstance();

        System.out.println("Один и тот же объект? " + (config1 == config2));

        config1.setSetting("app.name", "MyApplication");

        try {
            System.out.println(config2.getSetting("app.name"));
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }


        try {
            config1.loadFromFile("config.txt");
            System.out.println("После загрузки из файла:");
            System.out.println("app.version = " + config1.getSetting("app.version"));

            config1.saveToFile("output.txt");
            System.out.println("Настройки сохранены в output.txt");

        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }


        Runnable task = () -> {
            ConfigurationManager config = ConfigurationManager.getInstance();
            System.out.println(Thread.currentThread().getName() +
                    " HashCode: " + config.hashCode());
        };

        Thread t1 = new Thread(task);
        Thread t2 = new Thread(task);
        Thread t3 = new Thread(task);

        t1.start();
        t2.start();
        t3.start();
    }
}