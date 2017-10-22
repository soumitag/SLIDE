
/**
 *
 * @author Soumita
 */
public class ConfigureSlide_Runner {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println(args[0]);
        System.out.println(args[1]);
        System.out.println(args[2]);
        ConfigureSlide conf = new ConfigureSlide(args[0], args[1], args[2]);
        conf.run();
    }
}
